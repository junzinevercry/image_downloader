package com.syc.download.thread;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.syc.download.entity.Album;
import com.syc.download.entity.Page;
import com.syc.download.utils.CommonUtils;
import com.syc.download.utils.Constant;

public class WebThread implements Runnable {
	private String rootUrl = "";

	public WebThread(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
	public void run() {
		Map<String, Page> pageMap = this.initPage();
		Set<String> keys = pageMap.keySet();
		for (String key : keys) {
			Page page = pageMap.get(key);
			PageThread pt = new PageThread(page);
			Thread t = new Thread(pt);
			t.start();
		}
	}

	private Map<String, Page> initPage() {

		CommonUtils.log("begin to explain : " + rootUrl);

		Map<String, Page> resultMap = new HashMap<String, Page>();
		recyclePages(this.rootUrl, resultMap);
		return resultMap;
	}

	private void recyclePages(String currentUrl, Map<String, Page> pageMap) {

		Document subDoc;
		try {
			subDoc = Jsoup.connect(currentUrl).get();

			Page page = new Page();
			// current page album
			Map<String, Album> albumMap = new HashMap<String, Album>();
			Elements albumUrlElements = subDoc
					.getElementsByClass("entry_title");

			CommonUtils.log("page started : " + currentUrl);

			for (Element albumUrlElement : albumUrlElements) {

				CommonUtils.log("album started : " + currentUrl);

				Album album = new Album();
				Elements elementsByAttribute = albumUrlElement
						.getElementsByAttribute("href");
				String albumUrl = elementsByAttribute.attr("href");
				album.setUrl(albumUrl);
				String[] parts = albumUrl.split("/");
				String folderName = parts[parts.length - 2];
				String innerText = elementsByAttribute.text();
				album.setName(innerText);
				album.setFolder(Constant.DIR_ROOT + File.separator + folderName
						+ File.separator + innerText);

				album.setImgUrlMap(this.getImgUrlMap(albumUrl));
				albumMap.put(currentUrl + innerText, album);

				CommonUtils.log("album finished : " + currentUrl);
			}
			page.setAlbumMap(albumMap);
			// Page footer
			this.setCurrentUrl(subDoc, page);
			page.setCurrentUrl(currentUrl);
			pageMap.put(currentUrl, page);

			CommonUtils.log("page finished : " + currentUrl);

			// next
			if (!CommonUtils.isNullStr(page.getNextUrl())) {
				recyclePages(page.getNextUrl(), pageMap);
			}
		} catch (IOException e1) {

			CommonUtils.log("retry page : " + currentUrl);

			recyclePages(currentUrl, pageMap);
		}
	}

	private Map<String, String> getImgUrlMap(String albumUrl) {
		Map<String, String> imgUrlMap = new HashMap<String, String>();
		this.recycleImgs(albumUrl, imgUrlMap);
		return imgUrlMap;
	}

	private void recycleImgs(String currentUrl, Map<String, String> imgUrlMap) {

		CommonUtils.log("image started : " + currentUrl);

		Document subDoc;
		try {
			subDoc = Jsoup.connect(currentUrl).get();

			// current album img
			Elements imgUrlElements = subDoc.getElementsByClass("highslide");
			for (Element imgUrlElement : imgUrlElements) {
				Elements imgElements = imgUrlElement.getElementsByTag("img");
				for (Element imgElement : imgElements) {
					String imgNameKey = imgElement.attr("alt");
					imgUrlMap.put(imgNameKey, Constant.URL_WEBSITE
							+ imgUrlElement.attr("href"));
				}
			}
			// Page footer
			String nextPage = getNextPage(subDoc);
			// next
			if (!CommonUtils.isNullStr(nextPage)) {
				recycleImgs(nextPage, imgUrlMap);
			}
		} catch (IOException e1) {

			CommonUtils.log("retry album : " + currentUrl);

			recycleImgs(currentUrl, imgUrlMap);
		}

		CommonUtils.log("image finished : " + currentUrl);
	}

	private String getNextPage(Document subDoc) {
		Elements nexts = subDoc.getElementsByClass("next");
		String result = "";
		for (Element next : nexts) {
			result = next.attr("href");
			break;
		}
		return result;
	}

	private void setCurrentUrl(Document subDoc, Page page) {
		Map<String, String> urlMap = new HashMap<String, String>();

		Elements subFooter = subDoc.getElementsByClass("page-navigator");
		String nextUrl = "";
		Iterator<Element> iterator = subFooter.iterator();
		if (iterator.hasNext()) {
			Element e = iterator.next();
			Elements footerElements = e.getElementsByAttribute("href");

			for (int i = 0; i < footerElements.size(); i++) {
				Element footerElement = footerElements.get(i);
				String innerText = footerElement.text();
				if ("下一页".equals(innerText)) {
					nextUrl = footerElement.attr("href");
				} else {
					urlMap.put(footerElement.attr("href"), footerElement.text());
				}
			}
		}
		String nextText = urlMap.get(nextUrl);
		Integer nextInt = CommonUtils.stringToInteger(nextText);
		if (nextInt != null) {
			nextInt -= 1;
		}
		page.setNextUrl(nextUrl);
	}
}
