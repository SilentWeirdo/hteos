package com.hteos.framework.core.constant;

/**
 * @author LIQIU
 * @date 2018-1-12
 **/
public class ErrorStatus {


    // 1xx Informational

    /**
     * 系统内部错误
     */
    public static final ErrorStatus INTERNAL_SERVER_ERROR = new ErrorStatus(10000, "系统错误");
    /**
     * 参数错误
     */
    public static final ErrorStatus ILLEGAL_ARGUMENT = new ErrorStatus(10001, "参数错误");
    /**
     * 业务错误
     */
    public static final ErrorStatus  SERVICE_EXCEPTION = new ErrorStatus(10002, "业务错误");
    /**
     * 非法的数据格式，参数没有经过校验
     */
    public static final ErrorStatus  ILLEGAL_DATA = new ErrorStatus(10003, "数据错误");

    public static final ErrorStatus  MULTIPART_TOO_LARGE = new ErrorStatus(1004,"文件太大");
    /**
     * 非法状态
     */
    public static final ErrorStatus  ILLEGAL_STATE = new ErrorStatus(10005, "非法状态");
    /**
     * 缺少参数
     */
    public static final ErrorStatus  MISSING_ARGUMENT = new ErrorStatus(10006, "缺少参数");
    /**
     * 非法访问
     */
    public static final ErrorStatus  ILLEGAL_ACCESS = new ErrorStatus(10007, "非法访问,没有认证");
    /**
     * 权限不足
     */
    public static final ErrorStatus  UNAUTHORIZED = new ErrorStatus(10008, "权限不足");

    /**
     * 错误的请求
     */
    public static final ErrorStatus  METHOD_NOT_ALLOWED = ErrorStatus.of(10009, "不支持的方法");


    /**
     * 参数错误
     */
    public static final ErrorStatus ILLEGAL_ARGUMENT_TYPE = ErrorStatus.of(10010, "参数类型错误");

    /**
     * 地区信息错误
     */
    public static final ErrorStatus  ILLEGAL_ADDRESS = ErrorStatus.of(100011,"地区信息错误");

    private final int value;

    private final String message;


    ErrorStatus(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static ErrorStatus of(int value,String message){
        return new ErrorStatus(value,message);
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getMessage() {
        return this.message;
    }

}
