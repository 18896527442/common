package com.ll.common.mybatis.enums;

public enum OperatorEnum {

    NONE("none"),
    EQUAL("equal"),
    NOT_EQUAL("notequal"),
    CONTAINS("contains"),
    GRANTER_THAN("greaterthan"),
    GRANTER_THAN_EQUAL("greaterthanorequal"),
    LESS_THAN("lessthan"),
    LESS_THAN_EQUAL("lessthanorequal");

    OperatorEnum(String code) {
        this.code = code;
    }

    private String code;

    public Integer getValue() {
        return this.ordinal();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
