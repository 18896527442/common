package com.ll.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 短链生成工具类
 */
public class ShortChainGenerateUtils {

	/**
	 * 生成短链码
	 * @return 短链码
	 */
	public static String getShortChainCode() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		str+= str.toLowerCase();
		str +="0123456789";
		StringBuilder shortChainCode = new StringBuilder(6);
		for (int i = 0; i <6 ; i++) {
			char ch2 = str.charAt(new Random().nextInt(str.length()));
			shortChainCode.append(ch2);
		}
		return shortChainCode.toString();
	}

	/**
	 * 批量生成短链码
	 * @param num 需要生成的数量
	 * @return 短链码集合
	 */
	public static List<String> getShortChainCodes(Integer num) {
		List<String> shortChainCodes = new ArrayList<>();
		for (int i = 0; i <= num; i++) {
			String shortChain = getShortChainCode();
			shortChainCodes.add(shortChain);
		}
		return shortChainCodes;
	}


	public static void main(String[] args)throws IOException {
//		String shortChain = getShortChain();
//		System.out.println(shortChain);

		List<String> shortChains = getShortChainCodes(2600);
		System.out.println(shortChains.size());
		System.out.println(shortChains);
	}

}

