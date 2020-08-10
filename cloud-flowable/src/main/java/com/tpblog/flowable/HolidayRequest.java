package com.tpblog.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class HolidayRequest {
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcDriver("org.postgresql.Driver")
                .setJdbcUsername("tp")
                .setJdbcPassword("123456")
                .setJdbcUrl("jdbc:postgresql://localhost/testdb");

        ProcessEngine engine = cfg.buildProcessEngine();
    }
}
