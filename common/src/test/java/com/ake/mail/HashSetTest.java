package com.ake.mail;

import java.util.HashSet;

/**
 * @author Saturday
 * @date 2020-4-9
 * here is just want to test reference of one object. 
 */
public class HashSetTest {
	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<>();
		set.add(1);
		HashSet<Integer> set2 = set;
		set = null;	// here just set set object to null 
		System.out.println(set2);
	}
}
