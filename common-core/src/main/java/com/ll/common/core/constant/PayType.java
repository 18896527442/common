package com.ll.common.core.constant;

/**
 * 支付类型枚举
 *
 * @author zhangheng
 * @date 15:06 2019/2/1
 */
public enum PayType {

    /**
     * 授信支付
     */
    CREDIT_PAY(1, "授信支付"),
    /**
     * 阿里支付
     */
    ALI_PAY(2, "支付宝支付"),
    /**
     * 微信支付
     */
    WX_PAY(3, "微信支付");


    private Integer code;
    private String message;


    PayType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
