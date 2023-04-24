package com.ll.common.utils.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 能量计算工具类
 */
public class EnergyCalculateUtils {

	/**
	 * 获取能量值（kcal）
	 * @param protein (g)
	 * @param fat (g)
	 * @param cho (g)
	 * @return energyOfKcal(kcal)
	 */
	public static BigDecimal getEnergyOfKcal(BigDecimal protein, BigDecimal fat, BigDecimal cho) {
		BigDecimal energyOfKcal = BigDecimal.ZERO;
		if (protein != null && fat != null && cho != null) {
			BigDecimal four = new BigDecimal("4");
			BigDecimal nine = new BigDecimal("9");
			energyOfKcal = protein.multiply(four).add(fat.multiply(nine)).add(cho.multiply(four));
		}
		return energyOfKcal;
	}

	/**
	 * 获取能量值（KJ）
	 * @param protein (g)
	 * @param fat (g)
	 * @param cho (g)
	 * @return energyOfkj (KJ)
	 */
	public static BigDecimal getEnergyOfKj(BigDecimal protein, BigDecimal fat, BigDecimal cho) {
		BigDecimal energyOfkj = BigDecimal.ZERO;
		if (protein != null && fat != null && cho != null) {
			BigDecimal four = new BigDecimal("4");
			BigDecimal nine = new BigDecimal("9");
			energyOfkj = (protein.multiply(four).add(fat.multiply(nine)).add(cho.multiply(four))).divide(new BigDecimal("4.184"), 2, RoundingMode.HALF_UP);
		}
		return energyOfkj;
	}

	/**
	 * 获取当前蛋白质能量
	 * @param currentProteinValue 蛋白质当前值
	 * @return proteinEnergy
	 */
	public static BigDecimal getProteinEnergy(BigDecimal currentProteinValue) {
		BigDecimal proteinEnergy = BigDecimal.ZERO;
		if (currentProteinValue != null) {
			proteinEnergy = currentProteinValue.multiply(new BigDecimal("16.7"));
		}
		return proteinEnergy;
	}

	/**
	 * 获取当前脂肪能量
	 * @param currentFatValue 脂肪当前值
	 * @return fatEnergy
	 */
	public static BigDecimal getFatEnergy(BigDecimal currentFatValue) {
		BigDecimal fatEnergy = BigDecimal.ZERO;
		if (currentFatValue != null) {
			fatEnergy = currentFatValue.multiply(new BigDecimal("37.6"));
		}
		return fatEnergy;
	}

	/**
	 * 获取当前碳水化合物能量
	 * @param currentChoValue 碳水化合物当前值
	 * @return proteinEnergychoEnergy*/
	public static BigDecimal getChoEnergy(BigDecimal currentChoValue) {
		BigDecimal choEnergy = BigDecimal.ZERO;
		if (currentChoValue != null) {
			choEnergy = currentChoValue.multiply(new BigDecimal("16.7"));
		}
		return choEnergy;
	}


	public static void main(String[] args) {
		BigDecimal three = new BigDecimal("3");
		BigDecimal two = new BigDecimal("2");
		BigDecimal four = new BigDecimal("4");
		BigDecimal energyOfKcal = getEnergyOfKcal(three, two, four);
		BigDecimal energyOfKj = getEnergyOfKj(three, two, four);
		System.out.println("kcal："+energyOfKcal);
		System.out.println("kj："+energyOfKj);
	}
}
