package com.unbank.parser;

import org.jsoup.nodes.Document;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.filter.title.TitleBySqlFilter;

/**
 * 提取新闻标题
 * 
 * @author 梁擎宇
 * 
 */
public class TitlePaser extends BasePaser {

	public boolean fillCrawlTitle(NewsInfoMiddleWare articleInfo,
			Document document) {
		String title = null;
		try {
			TitleBySqlFilter baseFilter = new TitleBySqlFilter(articleInfo);
			title = baseFilter.getTitle(document, articleInfo.getCrawlTitle());
		} catch (Exception e) {
			logger.info("从数据库获取标题失败   " + articleInfo.getUrl() + "    "
					+ articleInfo.getWebsiteId());
			return false;
		}
		title = removeSingleQuotes(title);
		title = dispose(title, articleInfo.getUrl());
		articleInfo.setCrawlTitle(title.trim());
		return true;

	}

	public String dispose(String str, String url) {
		String title = "";
		if (url.contains("jrj.com.cn")) {
			title = str.replace("减少字号默认字号加大字号", "");
		}

		return title == "" ? str : title;
	}
}
