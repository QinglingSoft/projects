package com.qinglingsoft.changshuchannel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.model.QlMediaFileName;

@Service
public class QlMediaImportService {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private ZipService zipService;
	@Resource
	private PropertyJdbcService propertyJdbcService;
	private File qlMediaImportErrorFolder;

	@Resource
	public void setQlMediaImportErrorFolder(String qlMediaImportErrorFolder) {
		this.qlMediaImportErrorFolder = new File(qlMediaImportErrorFolder);
		this.qlMediaImportErrorFolder.mkdirs();
	}

	public File importPack(File packFile) throws QlMediaImportException {
		// 创建解压缩临时文件夹
		File targetFolder;
		try {
			targetFolder = creatUnpackFolder();
		} catch (IOException e) {
			throw new QlMediaImportException("创建临时文件夹失败："
					+ e.getLocalizedMessage());
		}

		// 创建错误转储文件夹
		File errorFolder;
		try {
			errorFolder = createErrorFolder();
		} catch (IOException e) {
			throw new QlMediaImportException("创建错误转储文件夹失败"
					+ e.getLocalizedMessage());
		}

		// 创建错误日志文件
		File errorLogFile = new File(errorFolder, "importError.log");
		try {
			if (errorLogFile.createNewFile()) {
				logger.finer(String.format("错误日志文件%s创建成功",
						errorLogFile.getAbsoluteFile()));
			}
		} catch (IOException e) {
			throw new QlMediaImportException(String.format("错误日志文件%s创建失败：%s",
					errorLogFile.getAbsoluteFile(), e.getLocalizedMessage()));
		}
		PrintWriter errorLogWriter;
		try {
			errorLogWriter = new PrintWriter(new FileOutputStream(errorLogFile));
		} catch (FileNotFoundException e) {
			throw new QlMediaImportException(String.format(
					"无法打开错误日志文件%s，文件不存在", errorLogFile.getAbsolutePath()));
		}

		// 解压缩文件到临时文件夹
		try {
			logger.finer("解压缩媒体文件包到临时文件夹");
			zipService.unzip(packFile, targetFolder);
			logger.finer("解压缩完成");
		} catch (ZipException e) {
			errorLogWriter.close();
			throw new QlMediaImportException(String.format("数据包%s解压缩失败：%s",
					packFile.getAbsoluteFile(), e.getLocalizedMessage()));
		} catch (IOException e) {
			errorLogWriter.close();
			throw new QlMediaImportException(String.format("数据包%s读取错误：%s",
					packFile.getAbsoluteFile(), e.getLocalizedMessage()));
		}

		// 对每个文件进行处理
		for (File mediaFile : targetFolder.listFiles()) {
			File errorFile = new File(errorFolder, mediaFile.getName());
			if (mediaFile.isDirectory()) {
				errorLogWriter.println(mediaFile.getName());
				errorLogWriter.println("数据包内不应含有文件夹结构");
				mediaFile.renameTo(errorFile);
				logger.finer(mediaFile.getName() + "为文件夹，移至错误转储文件夹。");
				continue;
			}
			try {
				importMediaFile(mediaFile);
				mediaFile.delete();
				logger.finer(mediaFile.getName() + "成功导入，删除。");
			} catch (Exception e) {
				logger.warning(String.format("%s导入异常：%s", mediaFile.getName(),
						e.getLocalizedMessage()));
				errorLogWriter.println(mediaFile.getName());
				errorLogWriter.println(e.getLocalizedMessage());
				mediaFile.renameTo(errorFile);
				logger.finer(mediaFile.getName() + "导入失败，移至错误转储文件夹。");
			}
		}

		// 关闭错误日志
		errorLogWriter.close();
		if (errorFolder.list().length <= 1) {
			logger.finer("没有错误");
			return null;
		}

		// 创建错误包文件
		File errorPack;
		try {
			errorPack = File.createTempFile("error", ".zip",
					qlMediaImportErrorFolder);
		} catch (IOException e) {
			throw new QlMediaImportException("错误包文件创建异常："
					+ e.getLocalizedMessage());
		}

		// 压缩错误文件夹到错误包中
		try {
			zipService.zipFolder(errorFolder, errorPack);
			logger.finer(String.format("导入错误的文件及日志压缩到了%s中",
					errorPack.getAbsoluteFile()));
			FileUtils.deleteDirectory(errorFolder);
		} catch (IOException e) {
			throw new QlMediaImportException(String.format(
					"导入错误的文件压缩失败，您可以在%s中找到它们。错误信息：%s", errorFolder,
					e.getLocalizedMessage()));
		}
		return errorPack;
	}

	/**
	 * 导入解压后的单一媒体文件
	 * 
	 * @param mediaFile
	 *            解压后的单一媒体文件
	 * @throws QlMediaImportSingleFileException
	 */
	private void importMediaFile(File mediaFile)
			throws QlMediaImportSingleFileException {
		String mediaFileName = mediaFile.getName();
		String[] parts = mediaFileName.split("\\.");

		// 文件名含有点号的按错误处理
		if (parts.length != 2) {
			String errorMessage = "文件名格式错误，应仅含有一个点号";
			throw new QlMediaImportSingleFileException(errorMessage);
		}

		// 从文件名中解析各字段值
		String fileName = parts[0];
		String extName = parts[1];
		QlMediaFileName parsed;
		try {
			parsed = QlMediaFileName.valueOf(fileName);
		} catch (IllegalArgumentException e) {
			String errorMessage = "文件名格式错误，" + e.getMessage();
			throw new QlMediaImportSingleFileException(errorMessage);
		}

		// 组织参数表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("QLBM", parsed.getQlbm());
		params.put("MName", parsed.getMediaName());
		params.put("MDate", parsed.getDate());
		params.put("Mdesc", parsed.getDescription());
		params.put("Mtype", extName.toLowerCase());
		try {
			params.put("Mcontext", FileUtils.readFileToByteArray(mediaFile));
		} catch (IOException e) {
			throw new QlMediaImportSingleFileException("读取媒体文件错误："
					+ e.getLocalizedMessage());
		}

		// 参数表入库
		try {
			propertyJdbcService.insert("T_QLMedia", params);
		} catch (Exception e) {
			throw new QlMediaImportSingleFileException("媒体文件入库错误："
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * 创建错误转储文件夹
	 * 
	 * @return 错误转储文件夹
	 * @throws IOException
	 *             创建过程中发生IO异常时抛出
	 */
	private File createErrorFolder() throws IOException {
		File errorFolder = File.createTempFile("roadBridge.import", "");
		errorFolder.delete();
		if (errorFolder.mkdirs()) {
			logger.finer(String.format("错误转储文件夹%s创建成功",
					errorFolder.getAbsolutePath()));
		} else {
			logger.finer(String.format("错误转储文件夹%s未创建",
					errorFolder.getAbsolutePath()));
		}

		return errorFolder;
	}

	/**
	 * 创建解压缩临时文件夹
	 * 
	 * @return 解压缩临时文件夹
	 * @throws IOException
	 *             创建过程中发生IO异常时抛出
	 */
	private File creatUnpackFolder() throws IOException {
		File targetFolder = File.createTempFile("roadBridge.import", "");
		targetFolder.delete();
		if (targetFolder.mkdirs()) {
			logger.finer(String.format("解压文件夹%s创建成功",
					targetFolder.getAbsolutePath()));
		} else {
			logger.finer(String.format("解压文件夹%s未创建",
					targetFolder.getAbsolutePath()));
		}

		return targetFolder;
	}
}
