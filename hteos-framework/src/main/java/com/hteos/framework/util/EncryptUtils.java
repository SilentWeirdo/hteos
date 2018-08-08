package com.hteos.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

public class EncryptUtils {

	public static String getMD5(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(str.getBytes());
			return new String(Hex.encodeHex(digest));
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		System.out.println(EncryptUtils.uuid());
	}
}
