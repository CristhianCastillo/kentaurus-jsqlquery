package com.kentaurus.jsqlquery.model;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
	private String password;

	public Encrypt(String clave) {
		this.password = clave;
	}

	public String encryptString(String message) throws Exception {
		byte[] rpta = encrypt(message);
		StringBuilder messageStr = new StringBuilder();
		for (int i = 0; i < rpta.length; i++) {
			String cad = Integer.toHexString(rpta[i]).toUpperCase();
			if (i != 0) {
				messageStr.append("-");
			}
			if (cad.length() < 2) {
				messageStr.append("0");
				messageStr.append(cad);
			} else if (cad.length() > 2) {
				messageStr.append(cad.substring(6, 8));
			} else {
				messageStr.append(cad);
			}
		}
		return messageStr.toString();
	}

	public byte[] encrypt(String message) throws Exception {
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(password.getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			final byte[] plainTextBytes = message.getBytes("utf-8");
			final byte[] cipherText = cipher.doFinal(plainTextBytes);
			return cipherText;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public String decryptedString(String string) throws Exception {
		String[] sBytes = string.split("-");
		byte[] bytes = new byte[sBytes.length];
		for (int i = 0; i < sBytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(sBytes[i], 16);
		}
		return decrypted(bytes);
	}

	public String decrypted(byte[] message) throws Exception {
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(password.getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, key, iv);
			final byte[] plainText = decipher.doFinal(message);
			return new String(plainText, "UTF-8");
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
}