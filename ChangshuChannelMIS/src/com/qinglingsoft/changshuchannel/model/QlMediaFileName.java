package com.qinglingsoft.changshuchannel.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QlMediaFileName {
	private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private String qlbm;
	private Date date;
	private String mediaName;
	private String description;

	public String getQlbm() {
		return qlbm;
	}

	public Date getDate() {
		return date;
	}

	public String getMediaName() {
		return mediaName;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return String.format("%s_%t_%s_%s", qlbm, df.format(date), mediaName,
				description);
	}

	static public QlMediaFileName valueOf(String fileName) {
		String[] parts = fileName.split("_");
		if (parts.length > 4) {
			throw new IllegalArgumentException("文件名被“_”分隔超过4段，无法解析");
		}
		QlMediaFileName result = new QlMediaFileName();
		if (parts[0] == null || parts[0].length() == 0) {
			throw new IllegalArgumentException("桥梁编码部分不可为空");
		}
		result.qlbm = parts[0];
		try {
			if (parts[1] != null && parts[1].length() > 0) {
				result.date = df.parse(parts[1]);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("采集日期部分格式错误", e);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		try {
			result.mediaName = parts[2];
			result.description = parts[3];
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return result;
	}

}
