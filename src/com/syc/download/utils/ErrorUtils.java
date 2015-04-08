package com.syc.download.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ErrorUtils {
	private static List<String> ERROR_LIST = new ArrayList<String>();

	public static void error(String errorInfo) {
		ERROR_LIST.add(errorInfo);
	}

	public static void errorOut() {
		FileUtils.write(ERROR_LIST, "./error_"
				+ Calendar.getInstance().getTimeInMillis());
	}
}
