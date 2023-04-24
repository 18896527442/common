package com.ll.common.mybatis.enums;

public enum ConditionEnum {

    AND("and"),
    OR("or");

    ConditionEnum(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Integer getValue(){
        return this.ordinal();
    }

}
