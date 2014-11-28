package com.qinglingsoft.changshuchannel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ZipService {
	private static final String encoding = "GB18030";

	/**
	 * 解压缩文件
	 * 
	 * @param source
	 *            压缩文件
	 * @param targetFolder
	 *            目标文件夹
	 * @throws ZipException
	 *             解压缩错误时抛出
	 * @throws IOException
	 *             IO错误时抛出
	 */
	public void unzip(File source, File targetFolder) throws IOException {
		targetFolder.mkdirs();
		ZipFile zipFile = new ZipFile(source, encoding);
		Enumeration<? extends ZipEntry> entries = zipFile.getEntries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				new File(targetFolder, entry.getName()).mkdirs();
				continue;
			}
			InputStream in = zipFile.getInputStream(entry);
			File targetFile = new File(targetFolder, entry.getName());
			targetFile.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(targetFile);
			IOUtils.copy(in, out);
			out.close();
		}
		zipFile.close();
	}

	public void zipFolder(File sourceFolder, File targetFile)
			throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				targetFile));
		out.setEncoding(encoding);
		compressDirectory(sourceFolder, out, "");
		out.close();
	}

	private void compressDirectory(File dir, ZipOutputStream zipOut,
			String basedir) throws IOException {
		if (!dir.exists()) {
			return;
		}
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				String path = basedir + file.getName() + "/";
				ZipEntry entry = new ZipEntry(path);
				zipOut.putNextEntry(entry);
				zipOut.closeEntry();
				compressDirectory(file, zipOut, path);
			} else {
				compressFile(file, zipOut, basedir);
			}
		}
	}

	private void compressFile(File file, ZipOutputStream zipOut, String basedir)
			throws IOException {
		if (!file.exists()) {
			return;
		}
		InputStream in = new FileInputStream(file);
		String entryName = basedir + file.getName();
		ZipEntry entry = new ZipEntry(entryName);
		zipOut.putNextEntry(entry);
		IOUtils.copy(in, zipOut);
		in.close();
		zipOut.closeEntry();
	}
}
