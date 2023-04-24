package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 脂肪来源
 */
public class FatCalculateUtils {

	/**
	 * 获取植物性构成
	 * @param vegetativeTotal
	 * @param animalTotal
	 * @return
	 */
	public static BigDecimal getVegetativeComposition(BigDecimal vegetativeTotal, BigDecimal animalTotal) {
		BigDecimal vegetativeComposition = BigDecimal.ZERO;
		if (vegetativeTotal != null && animalTotal != null) {
			vegetativeComposition = vegetativeTotal.divide((vegetativeTotal.add(animalTotal)).multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return vegetativeComposition;
	}

	/**
	 * 获取动物性构成
	 * @param vegetativeTotal
	 * @param animalTotal
	 * @return
	 */
	public static BigDecimal getAnimalComposition(BigDecimal vegetativeTotal, BigDecimal animalTotal) {
		BigDecimal animalComposition = BigDecimal.ZERO;
		if (vegetativeTotal != null && animalTotal != null) {
			animalComposition = animalTotal.divide((vegetativeTotal.add(animalTotal)).multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return animalComposition;
	}
}
