package com.cy.security;

import com.cy.DataStructure.UtilsByte;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 郑心
 *
 */
public class UtilMD5 {

	/**from wxapi
	 * @param buffer
	 * @return
	 */
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String generateMD5(String s) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		digest.update(s.getBytes());
		return UtilsByte.byteArray2HexString(digest.digest());
	}
}
