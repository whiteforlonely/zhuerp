package com.ake.utils;

public class StringUtils {

	public static boolean isEmpty(String s){
		
		if (null == s || "".equals(s)) {
			return true;
		}
		
		return false;
	}
}
