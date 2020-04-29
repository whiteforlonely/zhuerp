package com.ake.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.ake.demo.Demo;

/**
 * 有时候会遇到这种情况，不知道proto内容，但是有protoc转换后的java文件，
 * 这个时候就想要知道proto的内容，然后前后端都可以一起使用，所以就自己写了
 * 一个工具类，但是这边没有全部包含类型，后面遇到的话，会继续更新。
 * @author Saturday
 * @date 2020-4-27
 */
public class JavaToProto {

	public static void analysis(Class<?> clazz, String syntax) {
		
		String javaPackage = clazz.getPackage().getName();
		String outputClassName = clazz.getSimpleName();
		try {
			File protoFile = new File(outputClassName + ".proto");
			if (protoFile.exists()) {
				protoFile.delete();
			}
			protoFile.createNewFile();
			
			FileWriter fw = new FileWriter(protoFile);
			
			fw.append("syntax = \"" +syntax+ "\";\n");
			fw.append("option java_package = \"" + javaPackage + "\";\n");
			fw.append("option java_outer_classname = \"" + outputClassName + "\";\n\n\n");
			
			//获取所有的内部类，也就是proto类的消息体。
			Class<?>[] types = clazz.getDeclaredClasses();
			if (null != types && types.length > 0) {
				for (Class<?> c: types) {
					// 忽略冗余内部类
					if (c.getName().contains("OrBuilder")) {
						continue;
					}
					
					//忽略外部类的调用
					System.out.println("type: " + c.getName());
					if (c.getName().indexOf("$") == -1) {
						continue;
					}
					
					String msgName = c.getName().substring(c.getName().indexOf("$") + 1);
					fw.append("message ").append(msgName).append("{\n");
			
					Field[] fields = c.getDeclaredFields();
					int index = 1;
					if (null != fields && fields.length > 0) {
						for (Field field : fields) {
							String fieldName = field.getName();
							if (fieldName.contains("FIELD_NUMBER") || fieldName.equals("PARSER") || fieldName.contains("DEFAULT_INSTANCE") || fieldName.contains("serialVersionUID") || fieldName.equals("memoizedIsInitialized") || fieldName.contains("bitField0")) {
								continue;
							}
							System.out.println("field: " + field.getName().replace("_", "") + ", type: " + field.getType());
							Class<?> typeC = field.getType();
							String typeName = typeC.getName();
							if (typeName.equals("int")) {
								typeName = "int32";
							} else if (typeName.equals("long")) {
								typeName = "int64";
							} else if (typeName.equals("boolean")) {
								typeName = "bool";
							} else if (typeName.contains("java.util.List")) {
								Type genericType = field.getGenericType();
								if(genericType == null) continue;  
								// 如果是泛型参数的类型   
								if(genericType instanceof ParameterizedType){   
									ParameterizedType pt = (ParameterizedType) genericType;
									//得到泛型里的class类型对象  
									Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0]; 
									typeName = "repeated " + genericClazz.getSimpleName();
								}
							} else if (typeName.contains("$")) {
								typeName = typeName.substring(typeName.indexOf("$") + 1);
							} else if (typeName.contains("Object")) {
								typeName = "string";
							} else if (typeName.contains("com.google.protobuf.LazyStringList")) {
								typeName = "repeated string";
							}
							
							fw.append("\t").append(typeName).append(" ").append(field.getName().replace("_", "")).append(" = ").append(String.valueOf(index)).append(";\n");
							index ++;
						}
					}
					System.out.println("\n");
					fw.append("}\n\n");
					
				}
				fw.flush();
				fw.close();
			} else {
				System.out.println("there is no any class found!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		analysis(Demo.class, "proto3");
	}
}
