package com.ll.common.core.constant;

/**
 * @program: hxtrip-common-parent
 * @description: 发送短信验证码模板code
 * @author: xgy
 * @create: 2019-04-25 11:35
 **/
public class SmsCodeConstants {
    //您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
    public static final String SMSCODE_LOGIN_TEMPLATE_CODE = "login";

    //验证码：${code}，您正通过该验证码重新绑定手机号，如非本人操作请忽略
    public static final String NEWPHONENUMBER_TEMPLATE_CODE = "bind_phone_t2";

    //第一次没有手机号时绑定手机号  验证码：${code}，您正通过该验证码绑定手机号，如非本人操作请忽略。
    public static final String BIND_PHONENUMBER_TEMPLATE_CODE = "bind_phone";

    //原手机号验证 验证码：${code}，您正通过该验证码修改您的账号，如非本人操作请忽略
    public static final String VALIDATE_PHONENUMBER_TEMPLATE_CODE = "modify_phone";

    //尊敬的平台用户，您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
    public static final String REGISTER_TEMPLATE_CODE = "register";

    //尊敬的平台用户，您的动态码为：${code}，您正在进行密码重置操作，如非本人操作，请忽略本短信！
    public static final String RESET_TEMPLATE_CODE = "find_password";

///////////////////////////////////////////////////////////////////////////////////////
    public static final String REFUND_ORDER_SUCCESS="refund_order_success";

    public static final String ORDER_SUCCESS="order_success";

    public static final String TICKET_ORDER_SUCCESS="ticket_order_success";

    public static final String TICKET_ORDER_SUCCESS_T2="ticket_order_success_t2";

    public static final String ORDER_FAIL="order_fail";

    public static final String ORDER_FAIL_T2="order_fail_t2";

    public static final String ORDER_SUCCESS_T2="order_success_t2";

    public static final String BIND_PHONE="bind_phone";

    public static final String NO_LOGIN_ORDER="no_login_order";

    public static final String BIND_PHONE_T2="bind_phone_t2";

    public static final String REFUND_ORDER="refund_order";

    public static final String FIND_PASSWORD="find_password";

    public static final String REGISTER="register";

    public static final String SUPPLIER_AUDIT_FAILURE="supplier_audit_failure";

    public static final String SUPPLIER_AUDIT_SUCCESS="supplier_audit_success";

    public static final String MODIFY_PHONE="modify_phone";

    public static final String ORDER_SUCCESS_T3="order_success_t3";


}
