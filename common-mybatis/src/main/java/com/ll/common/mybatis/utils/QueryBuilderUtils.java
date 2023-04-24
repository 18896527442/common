package com.ll.common.mybatis.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ll.common.core.utils.Assert;
import com.ll.common.mybatis.dto.QueryBuilderDTO;
import com.ll.common.mybatis.dto.Rule;
import com.ll.common.mybatis.enums.ConditionEnum;
import com.ll.common.mybatis.enums.OperatorEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询工具
 * 
 * @author guozhanqi
 *
 */
public class QueryBuilderUtils {


	public static <T>QueryWrapper<T> buildWrapper(QueryBuilderDTO paramObj){

		SmartAssert.notNull(paramObj, "queryWrapperDTO不可为空！");
		// 绑定默认参数
		if(CollectionUtil.isNotEmpty(paramObj.getDefaultRules())){
			buildDefaultParams(paramObj);
		}
		if(CollectionUtil.isEmpty(paramObj.getRules())){
			return new QueryWrapper<T>();
		}
		QueryWrapper<T> wrapper = new QueryWrapper<T>();
		QueryBuilderDTO queryParamObj = new QueryBuilderDTO();
		queryParamObj.setCondition(ConditionEnum.AND.getCode());
		List<Rule> rules = new ArrayList<Rule>();
		Rule myRule = new Rule();
		myRule.setRules(rules);
		switch (paramObj.getCondition()) {
			case "or":
				wrapper.or(i -> { buildRuleOrGroup(i, paramObj.getCondition(), paramObj.getRules()); });
				break;
			default:
				wrapper.and(i -> { buildRuleOrGroup(i, paramObj.getCondition(), paramObj.getRules()); });
				break;
		}
		return wrapper;
	}
	
	/**
	 * 构建查询条件或组
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> void buildRuleOrGroup(QueryWrapper<T> wrapper, String groupCondition, List<Rule> rules) {
		SmartAssert.notEmpty(rules, "rules不可为空！");
		for (Rule rule : rules) {
			if(CollectionUtil.isNotEmpty(rule.getRules())){
				switch (rule.getCondition()) {
					case "or":
						wrapper.or(i->{ buildRuleOrGroup(i, rule.getCondition(), rule.getRules());});
						break;
					default:
						wrapper.and(i->{ buildRuleOrGroup(i, rule.getCondition(), rule.getRules());});
						break;
				}
			}
			buildRule(wrapper, groupCondition, rule);
		}
	}

	private static <T> void  buildRule(QueryWrapper<T> wrapper,String groupCondition, Rule rule) {
		if(null == rule) {
			return;
		}
		String field = rule.getField();
		if(StrUtil.isEmpty(field)){
			return;
		}
		if(OperatorEnum.NONE.toString().equals(rule.getOperator())) {
			return;
		}
		if(null != rule.getCondition()) {
			switch (rule.getCondition()) {
				case "or":
					wrapper.or();
					break;
				default:
					break;
			}
		} else {
			if(null != groupCondition && ConditionEnum.OR.getCode().equals(groupCondition)) {
				wrapper.or();
			}
		}
		String col = field2Column(field);
//		if("date".equals(rule.getType())) {
//			col = String.format("to_char(%s, 'yyyy-mm-dd,hh24:mi:ss')", col);
//		}
		Object value = rule.getValue();
		if(value instanceof String) {
			value = ((String) value).trim();
		}
		// 判断操作符
		switch (rule.getOperator()) {
			case "equal":
				wrapper.eq(col, value);
				break;
			case "notequal":
				wrapper.ne(col, value);
				break;
			case "contains":
				wrapper.like(col, value);
				break;
			case "greaterthan":
				wrapper.gt(col, value);
				break;
			case "greaterthanorequal":
				wrapper.ge(col, value);
				break;
			case "lessthan":
				wrapper.lt(col, value);
				break;
			case "lessthanorequal":
				wrapper.le(col, value);
				break;
			default:
				break;
		}
	}

	/**
	 * field2Column <br/>
	 * field字段转换为数据库column
	 * @author zhanqi.guo
	 * @param str
	 * @return
	 */
	final public static String field2Column(String str) {
		if(StrUtil.isEmpty(str)) {
			return "";
		}
		return str.replaceAll("[A-Z]", "_$0").toUpperCase();
	}

	/**
	 * 构建排序
	 * 
	 * @param <T>
	 * @param wrapper
	 * @param sort
	 * @return
	 */
	public static <T>QueryWrapper<T> buildSort (QueryWrapper<T> wrapper, String sort) {
		SmartAssert.notNull(wrapper, "wrapper不可为空！");
		//TODO
		return wrapper;
	}
	
	/**
	 * 根据参数构建分组
	 * 
	 * @param <T>
	 * @param wrapper
	 * @param order
	 * @return
	 */
	public static <T>QueryWrapper<T> buildOrder (QueryWrapper<T> wrapper, String order) {
		SmartAssert.notNull(wrapper, "wrapper不可为空！");
		//TODO
		return wrapper;
	}

	/**
	 * 绑定默认参数
	 * @param record
	 */
	public static void buildDefaultParams(QueryBuilderDTO record){
		List<Rule> rules = new ArrayList<>();
		Rule rootRule = queryWrapper2RootRule(record);
		rules.add(rootRule);
		rules.addAll(record.getDefaultRules());
		record.setRules(rules);
		record.setCondition(ConditionEnum.AND.getCode());
	}

	public static Rule queryWrapper2RootRule(QueryBuilderDTO record){
		Rule rule = new Rule();
		rule.setCondition(record.getCondition());
		rule.setRules(record.getRules());
		return rule;
	}

}
