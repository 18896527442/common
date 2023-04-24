package com.ll.common.utils.calculate;

import java.math.BigDecimal;

/**
 * 营养素计算
 */
public class NutrientsCalculateUtils {

	/**
	 * 获取某个营养素的总和(毛重计算)
	 * @param quantity 数量
	 * @param edible 可食部
	 * @param nutrientsValue 营养素值
	 * @return currentNutrients
	 */
	public static BigDecimal getNutrients(BigDecimal quantity, BigDecimal edible, BigDecimal nutrientsValue) {
		BigDecimal currentNutrients = BigDecimal.ZERO;
		if (quantity != null && edible != null && nutrientsValue != null) {
			BigDecimal multiplicand = new BigDecimal("0.01");
			currentNutrients = quantity.multiply(edible).multiply(multiplicand).multiply(nutrientsValue).multiply(multiplicand);
		}
		return currentNutrients;
	}

//	public static BigDecimal getNutrients(BigDecimal quantity, BigDecimal edible, BigDecimal nutrientsValue) {
//		BigDecimal nutrients = BigDecimal.ZERO;
//		if (quantity != null && edible != null && nutrientsValue != null) {
//			BigDecimal multiplicand = new BigDecimal("0.01");
//			nutrients = quantity.multiply(edible).multiply(multiplicand).multiply(nutrientsValue).multiply(multiplicand);
//		}
//		return nutrients;
//	}
}
