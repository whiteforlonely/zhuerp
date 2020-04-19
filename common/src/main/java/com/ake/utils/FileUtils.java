package com.ake.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FileUtils {
	
	private static Set<File> fileList = new HashSet<>();

	/**
	 * 收集指定目录下面的apk文件
	 * @param srcDir
	 * @param desDir
	 */
	public static void collectApks(String srcDir, String desDir){
		File srcFile = new File(srcDir);
		if (!srcFile.exists()) {
			return;
		}
		findApk(srcFile);
		if (fileList.size() <= 0) {
			return;
		}
		
		File desFile = new File(desDir);
		if (!desFile.exists()) {
			desFile.mkdir();
		}
		System.out.println("apk files length: " + fileList.size());
		for (File file : fileList) {
			String newFileName = desDir + File.separator + file.getName();
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				 fis = new FileInputStream(file);
				fos = new FileOutputStream(newFileName);
				int len;
				byte[] buf = new byte[4096];
				while ((len = fis.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
				fos.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != fis) {
						fis.close();
					}
					if (null != fos) {
						fos.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}
	
	private static void findApk(File des){
		if (des.isDirectory()) {
			File[] files = des.listFiles();
			for (File file : files) {
				findApk(file);
			}
		} else {
			if (des.getName().endsWith(".apk")) {
				System.out.println("apk file: " + des.getName());
				fileList.add(des);
			}
		}
	}
	
	public static void main(String[] args) {
		collectApks("E:\\andriodwork\\dianshixin\\apk", "E:\\work\\dianshicai\\APKS");
	}
}
