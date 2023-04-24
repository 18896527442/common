package com.ll.common.mybatis.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ll.common.mybatis.enums.ConditionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel("QueryBuilder查询构造参数")
public class QueryBuilderDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("是否分页")
	private Boolean pageFlag = true;

	@ApiModelProperty("分页")
	@NotNull
	private Page page;

	@ApiModelProperty("连接条件")
	private String condition = ConditionEnum.AND.getCode();

	@ApiModelProperty("规则集合")
	private List<Rule> rules;

	@ApiModelProperty("默认规则集合")
	private List<Rule> defaultRules;

	public Boolean getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(Boolean pageFlag) {
		this.pageFlag = pageFlag;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<Rule> getRules() {
		if(CollectionUtil.isEmpty(rules)) {
			rules = new ArrayList<Rule>();
		}
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public List<Rule> getDefaultRules() {
		return defaultRules;
	}

	public void setDefaultRules(List<Rule> defaultRules) {
		this.defaultRules = defaultRules;
	}

}