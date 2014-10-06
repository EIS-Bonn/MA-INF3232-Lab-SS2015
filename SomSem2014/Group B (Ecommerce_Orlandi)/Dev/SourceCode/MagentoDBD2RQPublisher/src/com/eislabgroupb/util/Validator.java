/**
 * 
 */
package com.eislabgroupb.util;

public class Validator {

	public static boolean isNumber(String str) {
		try {
			int num = Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
