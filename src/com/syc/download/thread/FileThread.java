package com.syc.download.thread;

import com.syc.download.utils.CommonUtils;
import com.syc.download.utils.FileUtils;

public class FileThread implements Runnable {

	private String imgUrl;
	private String fileName;

	public FileThread(String imgUrl, String fileName) {
		this.imgUrl = imgUrl;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		// download
		boolean result = FileUtils.downloadFile(imgUrl, fileName);
		if (result) {
			CommonUtils.log("download : " + fileName);
		}
	}

}
