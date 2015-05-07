package com.qinglingsoft.changshuchannel.model;

import java.io.UnsupportedEncodingException;

public class FileTypeValue {
	private byte[] content;
	private String contentType;
	private String fileName;

	public FileTypeValue(String fileName, String contentType, byte[] content) {
		this.content = content;
		this.contentType = contentType;
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public int getContentLength() {
		if (content == null) {
			return 0;
		}
		return content.length;
	}
}
