package com.qinglingsoft.changshuchannel.service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SystemMaintainService {
	private boolean maintaining = false;
	private File backupFolder;
	private JdbcTemplate jdbcTemplate;

	public boolean isMaintaining() {
		return maintaining;
	}

	@Resource
	public void setBackupFolder(String backupFolder) {
		this.backupFolder = new File(backupFolder);
		this.backupFolder.mkdirs();
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public synchronized String backup() {
		try {
			this.maintaining = true;
			File backupFile = buildBackupFile();
			String sql = String.format(
					"backup database ChangshuChannel to Disk='%s'",
					backupFile.getAbsolutePath());
			jdbcTemplate.execute(sql);
			return backupFile.getAbsolutePath();
		} finally {
			this.maintaining = false;
		}
	}

	private File buildBackupFile() {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		String backupFileName = df.format(now) + ".bak";
		File backupFile = new File(backupFolder, backupFileName);
		return backupFile;
	}

}
