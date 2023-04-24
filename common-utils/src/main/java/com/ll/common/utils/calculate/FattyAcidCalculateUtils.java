package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 脂肪酸计算
 */
public class FattyAcidCalculateUtils {

	/**
	 * 获取单不饱和构成
	 * @param fattyAcidMufa  脂肪酸（单不饱和）
	 * @param fattyAcidSfa   脂肪酸（饱和）
	 * @param fattyAcidPufa  脂肪酸（多不饱和）
	 * @return 单不饱和构成
	 */
	public static BigDecimal getFattyAcidMufaConstitute(BigDecimal fattyAcidMufa, BigDecimal fattyAcidSfa, BigDecimal fattyAcidPufa) {
		BigDecimal fattyAcidMufaConstitute = BigDecimal.ZERO;
		if (fattyAcidMufa != null && fattyAcidSfa != null && fattyAcidPufa != null && !fattyAcidMufa.equals(BigDecimal.ZERO)) {
			fattyAcidMufaConstitute = fattyAcidMufa.divide((fattyAcidMufa.add(fattyAcidSfa).add(fattyAcidPufa)).multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return fattyAcidMufaConstitute;
	}

	/**
	 * 获取多不饱和构成
	 * @param fattyAcidMufa  脂肪酸（单不饱和）
	 * @param fattyAcidSfa   脂肪酸（饱和）
	 * @param fattyAcidPufa  脂肪酸（多不饱和）
	 * @return 多不饱和构成
	 */
	public static BigDecimal getFattyAcidPufaConstitute(BigDecimal fattyAcidMufa, BigDecimal fattyAcidSfa, BigDecimal fattyAcidPufa) {
		BigDecimal fattyAcidPufaConstitute = BigDecimal.ZERO;
		if (fattyAcidMufa != null && fattyAcidSfa != null && fattyAcidPufa != null && !fattyAcidMufa.equals(BigDecimal.ZERO)) {
			fattyAcidPufaConstitute = fattyAcidPufa.divide((fattyAcidMufa.add(fattyAcidSfa).add(fattyAcidPufa)).multiply(new BigDecimal("100")), 2, RoundingMode.HALF_UP);
		}
		return fattyAcidPufaConstitute;
	}

}
