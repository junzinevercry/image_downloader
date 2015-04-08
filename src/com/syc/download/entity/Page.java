package com.syc.download.entity;

import java.util.HashMap;
import java.util.Map;

public class Page {
	private Map<String, Album> albumMap = new HashMap<String, Album>();
	private String currentUrl = "";
	private String nextUrl = "";

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public Map<String, Album> getAlbumMap() {
		return albumMap;
	}

	public void setAlbumMap(Map<String, Album> albumMap) {
		this.albumMap = albumMap;
	}

}
