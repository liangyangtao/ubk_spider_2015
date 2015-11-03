package com.unbank.filter.title;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.mybatis.entity.WebsiteParser;
import com.unbank.spider.UnbankQuartzByTimeCrawler;

public class TitleBySqlFilter {
	private NewsInfoMiddleWare articleInfo;

	public TitleBySqlFilter(NewsInfoMiddleWare articleInfo) {
		this.articleInfo = articleInfo;
	}

	public String getTitle(Document document, String Alternativetitle) {
		WebsiteParser websiteParser = UnbankQuartzByTimeCrawler.websitepaserlist
				.get(articleInfo.getWebsiteId());
		String titlePath = websiteParser.getNewstitlePath();
		Element element = document.select(titlePath.trim()).first();
		String needlessPath = websiteParser.getNewstitleNeedlesselementsPath();
		if (needlessPath != null && (!needlessPath.isEmpty())) {
			Elements elements = element.select(needlessPath.trim());
			for (Element element2 : elements) {
				element2.remove();
			}
		}
		return element.text();
	}
}
