package com.syc.download.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FileUtils {

	public static boolean createDir(String dir) {
		boolean result = true;
		File folder = new File(dir);
		if (!folder.exists()) {
			result = folder.mkdirs();
		}
		return result;
	}

	public static void write(List<String> content, String fileName) {
		if (content == null || content.size() == 0) {
			return;
		}
		// FileOutputStream out = null;
		// FileOutputStream outSTr = null;
		// BufferedOutputStream buff=null;
		FileWriter fw = null;
		try {
			// out = new FileOutputStream(new File(fileName));
			// long begin = System.currentTimeMillis();
			// for (int i = 0; i < count; i++) {
			// out.write("测试java 文件操作\r\n".getBytes());
			// }
			// out.close();
			// long end = System.currentTimeMillis();
			// System.out.println("FileOutputStream执行耗时:" + (end - begin) +
			// " 豪秒");
			// outSTr = new FileOutputStream(new File("C:/add0.txt"));
			// buff=new BufferedOutputStream(outSTr);
			// long begin0 = System.currentTimeMillis();
			// for (int i = 0; i < count; i++) {
			// buff.write("测试java 文件操作\r\n".getBytes());
			// }
			// buff.flush();
			// buff.close();
			// long end0 = System.currentTimeMillis();
			// System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0)
			// + " 豪秒");
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = new FileWriter(fileName);
			for (int i = 0; i < content.size(); i++) {
				fw.write(content.get(i) + "\r\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
				// buff.close();
				// outSTr.close();
				// out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean downloadFile(String imgUrl, String fileName) {
		boolean result = false;
		File file = new File(fileName);
		if (file.exists()) {
			return result;
		}
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(fileName);
		try {
			urlfile = new URL(imgUrl);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			httpUrl.disconnect();
			result = true;
		} catch (Exception e) {
			CommonUtils.log("pull in error : " + fileName + " ********** "
					+ imgUrl);
			ErrorUtils.error(imgUrl + "=====" + fileName);
			result = false;
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}
}
