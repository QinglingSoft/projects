package com.qinglingsoft.changshuchannel.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class EncryptService {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private String algorithm = "SHA-1";

	public String encrypt(String source) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest alga = MessageDigest.getInstance(algorithm);
			alga.update(source.getBytes("UTF-8"));
			byte[] digesta = alga.digest();
			for (byte b : digesta) {
				String hex = Integer.toHexString(b & 0xFF);
				if (hex.length() == 1) {
					sb.append("0");
				}
				sb.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.severe(String.format("加密算法“%s”不受支持", algorithm));
		} catch (UnsupportedEncodingException e) {
			logger.severe("使用了不支持的字符集");
		}
		return sb.toString();
	}

}
