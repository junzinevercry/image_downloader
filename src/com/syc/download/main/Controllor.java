package com.syc.download.main;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.syc.download.utils.CommonUtils;

public class Controllor {

	private List<String> rootUrls = new ArrayList<String>();

	public List<String> getRootUrls() {
		return rootUrls;
	}

	public void init(Document doc) {
		CommonUtils.log("begin to explain website");
		Element element = doc.getElementById("name");
		Elements subElements = element.getElementsByAttribute("href");
		for (int i = 0; i < subElements.size(); i++) {
			Element subElement = subElements.get(i);
			String url = subElement.attr("href");
			rootUrls.add(url);
		}
	}

}
