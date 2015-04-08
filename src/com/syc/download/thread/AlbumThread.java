package com.syc.download.thread;

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.syc.download.entity.Album;
import com.syc.download.utils.CommonUtils;
import com.syc.download.utils.FileUtils;

public class AlbumThread implements Runnable {
	private Album album;

	public AlbumThread(Album album) {
		this.album = album;
	}

	@Override
	public void run() {
		FileUtils.createDir(this.album.getFolder());
		Map<String, String> imgUrlMap = this.album.getImgUrlMap();
		Set<String> imgs = imgUrlMap.keySet();
		for (String imgKey : imgs) {
			String imgUrl = imgUrlMap.get(imgKey);
			String fileName = this.album.getFolder() + File.separator + imgKey
					+ ".jpg";
			// Thread t = new Thread(new FileThread(imgUrl, fileName));
			// t.start();
			// download
			boolean result = FileUtils.downloadFile(imgUrl, fileName);
			if (result) {
				CommonUtils.log("download : " + fileName);
			}
		}
	}

}
