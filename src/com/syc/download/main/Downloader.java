package com.syc.download.main;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.syc.download.thread.WebThread;
import com.syc.download.utils.CommonUtils;
import com.syc.download.utils.Constant;
import com.syc.download.utils.ErrorUtils;

public class Downloader {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect(Constant.URL_WEBSITE).get();

		Controllor c = new Controllor();
		// web collection
		c.init(doc);
		List<String> rootUrls = c.getRootUrls();

		for (String rootUrl : rootUrls) {
			WebThread wc = new WebThread(rootUrl);
			// wc.run();
			Thread t = new Thread(wc);
			t.start();
		}
		CommonUtils.log("prepare for error...");
		ErrorUtils.errorOut();
		CommonUtils.log("error finished...");
	}
}
