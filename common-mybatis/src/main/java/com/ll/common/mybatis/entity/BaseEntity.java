package com.ll.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 租户code
     */
    private String tencentCode;

    /**
     * 商店租户code
     */
    private String merchantCode;

    /**
     * 创建者code
     */
    private String creatorCode;

    /**
     * 修改者code
     */
    private String modifierCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否可用
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer isEnabled;

    /**
     * 是否删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

    /**
     * 排序字段
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer displaySort;
}
