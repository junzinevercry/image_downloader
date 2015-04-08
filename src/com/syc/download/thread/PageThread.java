package com.syc.download.thread;

import java.util.Map;
import java.util.Set;

import com.syc.download.entity.Album;
import com.syc.download.entity.Page;

public class PageThread implements Runnable {
	private Page page;

	public PageThread(Page page) {
		this.page = page;
	}

	@Override
	public void run() {
		Map<String, Album> albumMap = this.page.getAlbumMap();
		Set<String> keySet = albumMap.keySet();
		for (String albumKey : keySet) {
			Album album = albumMap.get(albumKey);
			Thread t = new Thread(new AlbumThread(album));
			t.start();
		}
	}

}
