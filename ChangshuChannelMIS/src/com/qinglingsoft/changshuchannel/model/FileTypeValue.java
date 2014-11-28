package com.qinglingsoft.changshuchannel.model;

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
		return fileName;
	}

	public int getContentLength() {
		if (content == null) {
			return 0;
		}
		return content.length;
	}
}
