package com.chd.chd56lc.net.responseCode;

/**
 * author: lefore
 * date: 2017/12/3
 */
public enum ErrorCode {

    /**
     * 不同模块的异常信息写在这里
     */

//    CAPTCHA_SEND_FAIL("验证码下发失败", 10001),
    CAPTCHA_INVALID("验证码错误次数过多，已失效", 10002),
    CAPTCHA_ERROR("验证码错误，请重新输入", 10003),
    // web页面重新授权
    FAIL_WECHAT_AUTH("微信账户登录失败", 10004),
    NOT_EXIST("用户信息不存在", 10008),
    PHONE_NUMBER_USED_BY_OTHER_USER("该手机号码已注册", 10009),

    PHONE_NOT_MATCH_LOGIN("手机号码与登录手机号码不一致", 100010),
    AUTO_LOGIN_FAILED("自动登录失败，请手动登录", 100020),

    INCONSISTENT_GESTURES("两次手势不一致",100021),
    TOUCHID_GESTURE_ONLY_ONE("手势 和 touchID 仅允许开启一个",100022),

    UNIQUE_ADMIN_USERNAME("已存在该账号",200000),
    ROLE_NOT_FOUND("角色不存在",200001),
    INVALID_USERNAME("用户名不可用",200002),
    PASSWORD_NOT_SET("未设置密码",200003),
    ADMIN_NOT_FOUND("该账号不存在",200004),
    COMPANY_NOT_FOUND("企业不存在",200005),
    INFO_AFTER_LOAN_NOT_FOUND("贷后信息未找到",200006),
    COMPANY_PROJECT_NOT_FOUND("项目信息未找到",200007),
    COMPANY_DEPOSIT_NOT_FOUND("开户信息未找到",200008),
    COMPANY_EXIST("该企业已存在",200009),
    COMPANY_OR_SUERTY_NOT_EXIST("担保人或企业不存在",200010),
    DUPLICATE_RECORD("该记录重复",200011),
    RELATION_NOT_EXIST("不存在关联",200012),
    ROLE_NAME_EXIST("角色已存在",200013),

    MENU_DEPT_EXCEED("菜单层级超出限制",200014),
    SURETY_HAS_BID("担保人有标的",200015),


    SURETY_NOT_FOUND("担保人不存在",300001),

    ORIGINAL_PASSWORD_ERROR("密码错误",400001),


    //
//    UPDATE_ERROR("修改失败,请稍后再试", 1014),
//    FIND_ERROR("查询失败,请稍后再试", 1014),
//
//    BOUND_PHONE_SELF("您已经绑定此号码", 1021),
//    BOUND_PHONE_OTHER("此号码已经被其他账户绑定", 1022),
//    NEED_REASON("当处理结果为失败时，失败理由不能为空", 1026),
//
//    WITHDRAW_NOT_EXIST("提现记录不存在", 2001),
//    EXPORT_FAIL("导出失败", 2002),
//    REASON_FOR_REFUSAL_TO_WITHDRAW_CASH_CANNOT_BE_NUL("拒绝提现的理由不能为空", 2003),
//
//    JWT_USER_INFO_OPENID_NULL("用户 openid 为空", 50001),
//
//    PHONE_NUMBER_USED_BY_OTHER_ADMIN_USER("该手机号码已被其他管理员使用，请提供其他未使用手机号码", 60001),
//    NON_EXISTENT_ADMIN_ROLE("不存在指定的管理员角色信息", 60002),
//    NON_EXISTENT_ADMIN_USER("不存在指定的管理员账户信息", 60003),
//    THE_OLD_PASSWORD_IS_INCORRECT("旧密码不正确", 60005),
//    NEW_PASSWORD_CANNOT_BE_EQUAL_TO_OLD_PASSWORD("新密码不能与旧密码相同", 60006),
//    ROLE_NAME_CAN_NOT_BE_REPEATED("角色名称不能重复", 60007),
//    ROLE_INFORMATION_DOES_NOT_EXIST("角色信息不存在", 60008),
//    SU_ADMIN_INFO_CAN_BE_CHANGED_BY_SU_ADMIN("超级管理员账号信息只能由超级管理员更改", 60009),
//    SU_ADMIN_PWD_CAN_BE_CHANGED_BY_SU_ADMIN("超级管理员密码只能由超级管理员更改", 60010),
//    SU_ADMIN_CANNOT_BE_DISABLED("不能禁用超级管理员", 60011),
//    ADMIN_USER_LOGIN_FAIL("账号密码不匹配", 60014);
    INCONSISTENT_PASSWORDS("新密码与确认密码不一致", 60004);


    private String message;
    private Integer code;


    ErrorCode(String message, Integer code) {
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
