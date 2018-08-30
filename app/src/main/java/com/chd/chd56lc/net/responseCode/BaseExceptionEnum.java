package com.chd.chd56lc.net.responseCode;

/**
 * Created by JasonY on 17/5/8.
 */
public enum BaseExceptionEnum {

    SUCCESS("成功", 200),

    SYSTEM_BUSY("系统繁忙，请稍候再试", 500),
    SYSTEM_ERROR("系统异常，请稍候再试", 510),

    INVALID_PARAMEGER("参数不符合要求", 501),

    REFLECTION_GET_FAILED("GlobalExceptionEnum.reflectionGetFailed", 502),
    UNSUPPORTED_ENCODING("GlobalExceptionEnum.unsupportedEncodingException", 503),

    INVALID_ACCOUNT_PASSWORD("账号或密码不正确", 504),
    ACCESS_DENIED("无访问权限", 505),
    ANNOYMOUS("您处于登出状态，请重新登录", 506),
    ACCOUNT_LOCKED("账号已被锁定", 507),

    QINIU_INVALID_CALLBACK("非法请求", 508),
    TOKEN_EXPIRED("会话已失效，请退出应用后重新打开", 509),
    ALREADY_LOGIN("您已处于登录状态", 510)
    ;

    private String message;
    private Integer code;

    private BaseExceptionEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
