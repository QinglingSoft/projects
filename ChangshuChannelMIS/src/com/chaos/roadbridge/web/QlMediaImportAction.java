package com.chaos.roadbridge.web;

import java.io.File;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.service.QlMediaImportException;
import com.chaos.roadbridge.service.QlMediaImportService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class QlMediaImportAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private File qlMediaPack;
	private String qlMediaPackFileName;
	private String qlMediaPackContentType;
	private String message;
	@Resource
	private QlMediaImportService qlMediaImportService;

	public void setQlMediaPack(File qlMediaPack) {
		this.qlMediaPack = qlMediaPack;
	}

	public void setQlMediaPackFileName(String qlMediaPackFileName) {
		this.qlMediaPackFileName = qlMediaPackFileName;
	}

	public void setQlMediaPackContentType(String qlMediaPackContentType) {
		this.qlMediaPackContentType = qlMediaPackContentType;
	}

	public String getMessage() {
		return message;
	}

	public String execute() {
		if (!qlMediaPackContentType.equals("application/x-zip-compressed")) {
			logger.warning(String.format("上传文件ContentType为%s，不是zip包。",
					qlMediaPackContentType));
			message = "文件不是zip包";
			return Action.ERROR;
		}

		try {
			File errorFile = qlMediaImportService.importPack(qlMediaPack);
			if (errorFile != null) {
				message = qlMediaPackFileName + "已导入完成，" + "导入失败的文件保存在"
						+ errorFile.getAbsolutePath();
			} else {
				message = qlMediaPackFileName + "已导入完成。";
			}
			return Action.SUCCESS;
		} catch (QlMediaImportException e) {
			message = qlMediaPackFileName + "导入失败，" + e.getLocalizedMessage();
			logger.warning(message);
			return Action.ERROR;
		}
	}
}
