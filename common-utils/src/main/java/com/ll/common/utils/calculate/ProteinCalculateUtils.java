package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 蛋白质计算
 */
public class ProteinCalculateUtils {

	/**
	 * 获取优质蛋白质构成
	 * @param highQualityProtein 优质蛋白质
	 * @param proteinTotal 蛋白质总和
	 * @return
	 */
	public static BigDecimal getHighQualityProteinComposition(BigDecimal highQualityProtein, BigDecimal proteinTotal) {
		BigDecimal proteinComposition = BigDecimal.ZERO;
		if (highQualityProtein != null && proteinTotal != null && !proteinTotal.equals(BigDecimal.ZERO)) {
			proteinComposition = highQualityProtein.divide(proteinTotal.multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return proteinComposition;
	}

	/**
	 * 获取非优质蛋白质构成
	 * @param notHighQualityProtein 非优质蛋白质
	 * @param proteinTotal 蛋白质总和
	 * @return
	 */
	public static BigDecimal getNotHighQualityProteinComposition(BigDecimal notHighQualityProtein, BigDecimal proteinTotal) {
		BigDecimal notHighQualityProteinComposition = BigDecimal.ZERO;
		if (notHighQualityProtein != null && proteinTotal != null && !proteinTotal.equals(BigDecimal.ZERO)) {
			notHighQualityProteinComposition = notHighQualityProtein.divide(proteinTotal.multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return notHighQualityProteinComposition;
	}
}
