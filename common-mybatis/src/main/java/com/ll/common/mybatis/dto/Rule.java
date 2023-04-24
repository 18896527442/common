package com.ll.common.mybatis.dto;


import com.ll.common.mybatis.enums.OperatorEnum;

import java.io.Serializable;
import java.util.List;

public class Rule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String condition;
	
	private String field;
	
	private String label;
	
	private String type = "string";
	
	private String operator = OperatorEnum.NONE.getCode();
	
	private Object value;

	private List<Rule> rules;

	public Rule() {
	}

	public Rule(String condition, String field, String label, String type, String operator, Object value) {
		this.condition = condition;
		this.field = field;
		this.label = label;
		this.type = type;
		this.operator = operator;
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
}
