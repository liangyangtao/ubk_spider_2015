package com.unbank.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.unbank.entity.NewsInfoMiddleWare;

public class WebPagePaser extends BasePaser {
	public static Log logger = LogFactory.getLog(WebPagePaser.class);

	public NewsInfoMiddleWare paser(NewsInfoMiddleWare newsInfoMiddleWare) {
		Document document = getDocumentByURL(newsInfoMiddleWare.getUrl());
		if (document == null) {
			logger.info("获取document失败  " + newsInfoMiddleWare.getWebsiteId()
					+ "============" + newsInfoMiddleWare.getUrl());
			return null;
		}
		boolean success = new TitlePaser().fillCrawlTitle(newsInfoMiddleWare,
				document);
		if (!success) {
			logger.info("获取标题失败" + newsInfoMiddleWare.getWebsiteId()
					+ "============" + newsInfoMiddleWare.getUrl());
			return null;
		}
		success = new NewsDatePaser()
				.fillNewsTime(newsInfoMiddleWare, document);
		if (!success) {
			// logger.info("获取时间失败" + newsInfoMiddleWare.getWebsiteId()
			// + "============" + newsInfoMiddleWare.getUrl());
			return null;
		}
		String content = fillNewsContent(document, newsInfoMiddleWare);
		if (content == null) {
			logger.info("获取内容失败" + newsInfoMiddleWare.getWebsiteId()
					+ "============" + newsInfoMiddleWare.getUrl());
			return null;
		}
		String crawlBrief = extractNewsBrief(content);
		if (crawlBrief == null) {
			logger.info("获取简介失败" + newsInfoMiddleWare.getWebsiteId()
					+ "============" + newsInfoMiddleWare.getUrl());
			return null;
		}
		newsInfoMiddleWare.setCrawlBrief(crawlBrief);
		newsInfoMiddleWare.setText(content);
		return newsInfoMiddleWare;
	}

	private String fillNewsContent(Document document,
			NewsInfoMiddleWare newsInfoMiddleWare) {
		String content = new ContentPaser().extractNewsContent(
				newsInfoMiddleWare, document);
		if (content == null) {
			return null;
		}
		return content;
	}

	// 获取新闻简介
	private String extractNewsBrief(String content) {
		String crawl_brief = Jsoup.parse(content).text();
		if (crawl_brief.length() > 100) {
			crawl_brief = crawl_brief.substring(0, 100);
		} else {
			return null;
		}
		crawl_brief = crawl_brief.trim();
		return crawl_brief;
	}
}
