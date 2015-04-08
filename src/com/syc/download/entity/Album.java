package com.syc.download.entity;

import java.util.HashMap;
import java.util.Map;

public class Album {
	private String url;
	private String name;
	private String folder;
	private Map<String, String> imgUrlMap = new HashMap<String, String>();
	private Integer currentPage = 0;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getImgUrlMap() {
		return imgUrlMap;
	}

	public void setImgUrlMap(Map<String, String> imgUrlMap) {
		this.imgUrlMap = imgUrlMap;
	}

}
