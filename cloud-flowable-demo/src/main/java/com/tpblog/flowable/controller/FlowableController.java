package com.tpblog.flowable.controller;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.runtime.Execution;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FlowableController {

    // 可用于启动流程实例
    @Autowired
    private RuntimeService runtimeService;

    // 提供访问访问任务和表单相关的服务
    @Autowired
    private TaskService taskService;

    // 提供正在进行或过去的流程实例的信息
    @Autowired
    private HistoryService historyService;

    // 提供对流程定义和部署仓库的访问
    @Autowired
    private RepositoryService repositoryService;


    @Resource
    private ProcessEngine processEngine;

    @Autowired
    private FormService formService;

    /**
     * 创建流程
     * @param userId
     * @param days
     * @param reason
     * @return
     */

    @GetMapping("create/process")
    public String createFlow(String userId, String days, String reason){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("employee", userId);
        hashMap.put("nrOfHolidays", days);
        hashMap.put("description", reason);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", hashMap);
        Map<String, Object> processVariables = processInstance.getProcessVariables();
        System.out.println(processVariables);
        return "创建流程成功，流程Id为："+processInstance;
    }

    /**
     * 获取流程图
     * @param httpServletResponse
     * @param processId
     */
    @GetMapping("/process/diagram")
    public void lookProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws IOException {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<String>();
        List<String> flows = new ArrayList<String>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png", activityIds, flows, engconf.getActivityFontName(),
                engconf.getLabelFontName(), engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1.0, false);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     * 是否同意
     * @param taskId
     * @param approved
     * @return
     */
    @GetMapping("apply")
    public String apply(String taskId, String approved){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        System.out.println(task);
        if (task==null) {
            return "流程不存在";
        }
        Map<String, Object> var = new HashMap<String, Object>();

        Boolean bool = approved.equals("1")?true:false;
        var.put("approved", bool);

        taskService.complete(taskId, var);
        return "审批是否通过："+approved;
    }

    /**
     * 获取任务列表
     * @param group
     * @return
     */
    @GetMapping("/tasks")
    public String lookTaskList(String group){
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();

        return tasks.toString();
    }

    @GetMapping("/task")
    public List<Task> getTask() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("kermit").list();

        return tasks;
    }

}
