package com.ake.system;

import com.ake.utils.StringUtils;

public class Log4jConfig {

	public static void init(){
		String log4jConfigPath = System.getProperty("log4j.configuration");
		if (StringUtils.isEmpty(log4jConfigPath)) {
			log4jConfigPath = "file:/E:/workspace/log4j.properties";
		}
		System.out.println("log4j config path: "+log4jConfigPath);
		System.setProperty("log4j.configuration", log4jConfigPath);
	}
}
