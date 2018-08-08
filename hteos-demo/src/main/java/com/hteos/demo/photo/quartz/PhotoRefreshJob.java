package com.hteos.demo.photo.quartz;

import java.io.IOException;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PhotoRefreshJob {

	public static final JSONArray result = new JSONArray();

	@Scheduled(cron = "0 0 2 * * ?")
	public void load() {
		try {
			Document document = Jsoup.connect("http://news.qq.com/photo.shtml").get();
			String html = document.html();
			String startToken = "var data =[";
			html = html.substring(
					html.indexOf(startToken) + startToken.length() - 1,
					html.length());
			html = html.substring(0, html.indexOf("]") + 1);
			html = html.replace("\r\n", "");
			html = html.replace("img1:", "\"img1\":");
			html = html.replace("slink", "\"slink\"");
			html = html.replace("'", "\"");
			result.clear();
			result.addAll(JSONArray.fromObject(html));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
