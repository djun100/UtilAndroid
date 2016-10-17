package com.cy.DataStructure;

import java.util.ArrayList;


public class UtilString {
	public static String join(final ArrayList<String> array, String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
		/** 将中文转换成unicode编码 */
		public static String gbEncoding(final String gbString) {
			char[] utfBytes = gbString.toCharArray();
			String unicodeBytes = "";
			for (char utfByte : utfBytes) {
				String hexB = Integer.toHexString(utfByte);
				if (hexB.length() <= 2) {
					hexB = "00" + hexB;
				}
				unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
			//System.out.println("unicodeBytes is: " + unicodeBytes);  
			return unicodeBytes;
		}
	
		/** 将unicode编码转换成中文*/
		public static String decodeUnicode(final String dataStr) {
			int start = 0;
			int end = 0;
			final StringBuffer buffer = new StringBuffer();
			while (start > -1) {
				end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
				if (end == -1) {
					charStr = dataStr.substring(start + 2, dataStr.length());
				} else {
					charStr = dataStr.substring(start + 2, end);
				}
				char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串
				buffer.append(new Character(letter).toString());
				start = end;
			}
			//System.out.println(buffer.toString());
			return buffer.toString();
		}
		
		/**
		 * 转换字符串为boolean
		 * 
		 * @param str
		 * @return
		 */
		public static boolean toBoolean(String str) {
			return toBoolean(str, false);
		}

		/**
		 * 转换字符串为boolean
		 * 
		 * @param str
		 * @param def
		 * @return
		 */
		public static boolean toBoolean(String str, boolean def) {
			if (UtilString.isEmpty(str))
				return def;
			if ("false".equalsIgnoreCase(str) || "0".equals(str))
				return false;
			else if ("true".equalsIgnoreCase(str) || "1".equals(str))
				return true;
			else
				return def;
		}

		/**
		 * 转换字符串为float
		 * 
		 * @param str
		 * @return
		 */
		public static float toFloat(String str) {
			return toFloat(str, 0F);
		}

		/**
		 * 转换字符串为float
		 * 
		 * @param str
		 * @param def
		 * @return
		 */
		public static float toFloat(String str, float def) {
			if (UtilString.isEmpty(str))
				return def;
			try {
				return Float.parseFloat(str);
			} catch (NumberFormatException e) {
				return def;
			}
		}

		/**
		 * 转换字符串为long
		 * 
		 * @param str
		 * @return
		 */
		public static long toLong(String str) {
			return toLong(str, 0L);
		}

		/**
		 * 转换字符串为long
		 * 
		 * @param str
		 * @param def
		 * @return
		 */
		public static long toLong(String str, long def) {
			if (UtilString.isEmpty(str))
				return def;
			try {
				return Long.parseLong(str);
			} catch (NumberFormatException e) {
				return def;
			}
		}

		/**
		 * 转换字符串为short
		 * 
		 * @param str
		 * @return
		 */
		public static short toShort(String str) {
			return toShort(str, (short) 0);
		}

		/**
		 * 转换字符串为short
		 * 
		 * @param str
		 * @param def
		 * @return
		 */
		public static short toShort(String str, short def) {
			if (UtilString.isEmpty(str))
				return def;
			try {
				return Short.parseShort(str);
			} catch (NumberFormatException e) {
				return def;
			}
		}

		/**
		 * 转换字符串为int
		 * 
		 * @param str
		 * @return
		 */
		public static int toInt(String str) {
			return toInt(str, 0);
		}

		/**
		 * 转换字符串为int
		 * 
		 * @param str
		 * @param def
		 * @return
		 */
		public static int toInt(String str, int def) {
			if (UtilString.isEmpty(str))
				return def;
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {
				return def;
			}
		}
	public static String nullDeal(String origin){
		if (origin==null){
			return "";
		}else return origin;
	}
}
