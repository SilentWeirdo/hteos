package com.hteos.framework.core.protocol;


import com.hteos.framework.core.constant.ErrorStatus;

/**
 * 返回结果类
 * @author LIQIU
 */
public class Result<T> {

    // 返回是否成功
    private Boolean success = false;

    // 返回信息
    private String message = "操作成功";

    /**
     * 返回消息类型,用于复杂返回信息,0代表普通返回信息,1代表特殊返回信息
     */
    private Integer code = 0;

    private T data;


    public Result() {
    }

    private Result(Boolean success, String message, Integer code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    /**
     * 构建返回结果
     * @param success
     * @param msg
     * @param code
     * @param data
     * @return
     */
	public static <T> Result<T> build(Boolean success, String msg, Integer code,T data){
        return new Result<>(success,msg,code,data);
    }

    /**
     * 构建返回结果，code默认值为0
     * @param success
     * @param msg
     * @param data
     * @return
     */
    public static <T> Result<T> build(Boolean success,String msg,T data){
        return build(success,msg,0,data);
    }

    /**
     *构建成功结果
     * @param msg
     * @param data
     * @return
     */
    public static  <T> Result<T> buildSuccess(String msg,T data) {
        return build(Boolean.TRUE,msg,data );
    }

    /**
     * 构建失败结果
     * @param msg
     * @param data
     * @return
     */
    public static <T> Result<T> buildFailure(Integer code,String msg,T data){
        return build(Boolean.FALSE,msg ,code,data);
    }

    /**
     * 构建失败结果
     * @param msg
     * @param data
     * @return
     */
    public static  <T> Result<T>  buildFailure(String msg,T data){
        return build(Boolean.FALSE,msg ,data);
    }

    /**
     * 构建成功结果带信息
     * @return
     */
    public static <T> Result<T> buildSuccess(){
        return buildSuccess(null,null);
    }

    /**
     * 构建成功结果带信息
     * @param msg
     * @return
     */
    public static <T> Result<T> buildSuccess(String msg){
        return buildSuccess(msg,null);
    }

    /**
     * 构建成功结果待数据
     * @param data
     * @return
     */
    public static  <T> Result buildSuccess(T data){
        return buildSuccess(null,data);
    }

    /**
     * 构建失败结果待数据
     * @param msg
     * @return
     */
    public static Result buildFailure(String msg){
        return buildFailure(msg,null);
    }

    /**
     * 构建失败结果待数据
     * @param code
     * @param msg
     * @return
     */
    public static <T> Result<T> buildFailure(Integer code,String msg){
        return build(Boolean.FALSE,msg,code,null);
    }

    /**
     * 构建失败结果待数据
     * @param status
     * @return
     */
    public static <T> Result<T> buildFailure(ErrorStatus status){
        return buildFailure(status.value(),status.getMessage());
    }

    /**
     * 构建失败结果待数据
     * @param status
     * @return
     */
    public static <T> Result<T> buildFailure(ErrorStatus status, T data){
        return buildFailure(status.value(),status.getMessage(),data);
    }

    /**
     * 构建失败结果带数据
     * @param data
     * @return
     */
    public static  <T> Result buildFailure(T data){
        return buildFailure("",data);
    }
}
