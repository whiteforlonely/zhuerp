package com.ake.utils;

import io.netty.buffer.ByteBuf;
/**
 * ByteBuf 工具类
 * @author Saturday
 * @date 2017-8-1 下午4:41:17
 * @date ServerDemo
 */
public class ByteBufUtils {

	public static byte[] getBytes(ByteBuf buf){
		if (null == buf) {
			return null;
		}
		
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		return result;
	}
	
	public static String getString(ByteBuf buf){
		byte[] resultBytes = getBytes(buf);
		if (null != resultBytes) {
			return new String(resultBytes);
		}
		return null;
	}
}
