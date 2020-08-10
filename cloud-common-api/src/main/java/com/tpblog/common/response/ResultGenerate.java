package com.tpblog.common.response;

public class ResultGenerate {

    public static Result success(Integer code, String info, Object data){
        return new Result(code,info,data);
    }
}
