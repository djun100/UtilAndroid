package com.cy.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cy.DataStructure.UtilsByte;

/**
 * @author 郑心
 *
 */
public class UtilMD5 {
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
