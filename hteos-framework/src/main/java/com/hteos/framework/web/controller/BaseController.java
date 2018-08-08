package com.hteos.framework.web.controller;

import com.hteos.framework.core.protocol.Result;
import com.hteos.framework.web.support.MultiDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * 基础控制类，提供分页数据封装，结果返回功能
 * @author LIQIU
 * @date 2018-1-3
 */
public class BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new MultiDateFormat(), true));
    }

    /**
     *构建成功结果
     * @param msg
     * @param data
     * @return
     */
    public <T> Result<T> success(String msg,T data) {
        return Result.build(Boolean.TRUE,msg,data );
    }

    /**
     * 构建失败结果
     * @param msg
     * @param data
     * @return
     */
    public <T> Result<T> failure(String msg,T data){
        return Result.buildFailure(msg ,data);
    }
    /**
     * 构建失败结果
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public <T> Result<T> failure(Integer code,String msg,T data){
        return Result.buildFailure(code,msg,data);
    }
    /**
     * 构建失败结果
     * @param code
     * @param data
     * @return
     */
    public <T> Result<T> failure(Integer code,T data){
        return Result.buildFailure(code,null,data);
    }

    /**
     * 构建失败结果
     * @param code
     * @return
     */
    public <T> Result<T> failure(Integer code){
        return Result.buildFailure(code,null,null);
    }
    /**
     * 构建成功结果
     * @return
     */
    public <T> Result<T> success(){
        return success(null,null);
    }

    /**
     * 构建成功结果带信息
     * @param msg
     * @return
     */
    public <T> Result<T> success(String msg){
        return success(msg,null);
    }

    /**
     * 构建成功结果待数据
     * @param data
     * @return
     */
    public  <T> Result<T> success(T data){
        return success(null,data);
    }

    /**
     * 构建失败结果
     * @return
     */
    public <T> Result<T> failure(){
        return failure(0,null,null);
    }

    /**
     * 构建失败结果待数据
     * @param msg
     * @return
     */
    public <T> Result<T> failure(String msg){
        return failure(msg,null);
    }

    /**
     * 构建失败结果带数据
     * @param data
     * @return
     */
    public <T> Result<T> failure(T data){
        return failure(0,null,data);
    }
}
