package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BMI（体质指数）计算公式
 */
public class BmiCalculateUtils {

	/**
	 * 获取BMI
	 * @param weight (kg)
	 * @param height (m)
	 * @return bmi
	 */
	public static BigDecimal getBmi(BigDecimal weight, BigDecimal height) {
		BigDecimal bmi = BigDecimal.ZERO;
		if (weight != null && height != null && !height.equals(BigDecimal.ZERO)) {
			bmi = weight.divide(height.multiply(height), 2, RoundingMode.HALF_UP);
		}
		return bmi;
	}

	public static void main(String[] args) {
		BigDecimal weight = new BigDecimal("57");
		BigDecimal height = new BigDecimal("1.75");
		BigDecimal bmi = getBmi(weight, height);
		System.out.println("bmi：" + bmi);
	}

}
