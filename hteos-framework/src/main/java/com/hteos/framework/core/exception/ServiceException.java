package com.hteos.framework.core.exception;


import com.hteos.framework.core.constant.ErrorStatus;

/**
 * 平台服务层异常，主要是在业务数据或者状态异常时使用
 * @author LIQIU
 * @date 2017.12.25
 */
public class ServiceException extends RuntimeException{

    private Integer code;

    public ServiceException(ErrorStatus errorStatus){
        super(errorStatus.getMessage());
        this.code = errorStatus.value();
    }

    public ServiceException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
