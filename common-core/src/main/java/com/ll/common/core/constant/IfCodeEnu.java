package com.ll.common.core.constant;

public enum IfCodeEnu {
    wxmp(1, "微信"),
    ali(2, "支付宝"),
    wxcp(3, "企业微信");

    private Integer code;
    private String desc;

    IfCodeEnu(Integer code, String desc) {

    }

    public static IfCodeEnu getByCode(String val) {
        for (IfCodeEnu value : values()) {
            if (value.name().equals(val)) {
                return value;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
