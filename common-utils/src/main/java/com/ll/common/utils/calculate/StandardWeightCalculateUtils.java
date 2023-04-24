package com.ll.common.utils.calculate;

import java.math.BigDecimal;

/**
 * 成人标准体重计算工具类
 */
public class StandardWeightCalculateUtils {

	/**
	 * 获取标准体重
	 * @param height (cm)
	 * @return standardWeight（kg）
	 */
	public static BigDecimal getStandardWeight(BigDecimal height) {
		BigDecimal standardWeight = BigDecimal.ZERO;
		if (height != null) {
			standardWeight = height.subtract(new BigDecimal("105"));
		}
		return standardWeight;
	}

	public static void main(String[] args) {
		BigDecimal height = new BigDecimal("175");
		BigDecimal standardWeight = getStandardWeight(height);
		System.out.println("standardWeight：" + standardWeight);
	}
}
