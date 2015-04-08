package com.syc.download.utils;

public class CommonUtils {

	public static Integer stringToInteger(String str) {
		Integer result = null;
		if (!isNullStr(str)) {
			result = Integer.parseInt(str.trim());
		}
		return result;
	}

	public static void log(String logInfo) {
		System.out.println(logInfo);
	}

	public static boolean isNullStr(String str) {
		return str == null || "".equals(str);
	}
}
