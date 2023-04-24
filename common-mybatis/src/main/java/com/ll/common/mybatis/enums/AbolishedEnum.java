package com.ll.common.mybatis.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum AbolishedEnum implements IEnum<Integer> {

    /**
     * 0:未删除;
     */
    CONTINUE(0, "未删除"),
    /**
     * 1:逻辑删除;
     */
    ABOLISHED(1, "已删除");
    @Override
    public Integer getValue() {
        return this.ordinal();
    }

    AbolishedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name();
    }

    public String getDesc() {
        return desc;
    }

    private Integer code;

    private String desc;




}
