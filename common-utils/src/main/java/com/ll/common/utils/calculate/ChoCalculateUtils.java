package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 碳水化合物计算工具类
 */
public class ChoCalculateUtils {

	/**
	 * 获取碳水化合物
	 * @param energyKcal (kcal)
	 * @param protein (g)
	 * @param fat (g)
	 * @return cho （g）
	 */
	public static BigDecimal getCho(BigDecimal energyKcal, BigDecimal protein, BigDecimal fat) {
		BigDecimal cho = BigDecimal.ZERO;
		if (energyKcal != null && protein != null && fat != null) {
			BigDecimal four = new BigDecimal("4");
			BigDecimal nine = new BigDecimal("9");
			cho = (energyKcal.subtract(protein.multiply(four)).subtract(fat.multiply(nine))).divide(new BigDecimal("4"), 2, RoundingMode.HALF_UP);
		}
		return cho;
	}

	public static void main(String[] args) {
		BigDecimal twenty = new BigDecimal("20");
		BigDecimal two = new BigDecimal("2");
		BigDecimal four = new BigDecimal("1");
		BigDecimal cho = getCho(twenty, two, four);
		System.out.println("cho："+cho);
	}
}
