package com.unbank.filter.content;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.mybatis.entity.WebsiteParser;
import com.unbank.spider.UnbankQuartzByTimeCrawler;

public class ContentBySQLFilter extends ContentBaseFilter {

	private WebsiteParser websiteParser;

	public ContentBySQLFilter(NewsInfoMiddleWare newsInfoMiddleWare) {
		this.websiteParser = UnbankQuartzByTimeCrawler.websitepaserlist
				.get(newsInfoMiddleWare.getWebsiteId());

	}

	public Element removeNoNeedHtmlElement(String url, Document document) {
		String contentPath = websiteParser.getNewscontentPath();
		Element element = document.select(contentPath.trim()).first();
		String needlessPath = websiteParser.getNeedlesselementsPath();
		if (needlessPath != null && (!needlessPath.isEmpty())) {
			Elements elements = element.select(needlessPath.trim());
			for (Element element2 : elements) {
				element2.remove();
			}
		}
		return element;

	}

	public String removeNoNeedChars(String content) {
		content = super.removeNoNeedChars(content);
		String needlesscharsPath = websiteParser.getNeedlesscharsPath();
		if (needlesscharsPath == null || needlesscharsPath.trim().isEmpty()) {
			// return content;
		} else {
			String needlesschars[] = needlesscharsPath.split("\\|\\|");
			for (String string : needlesschars) {
				content = content.replace(string, "");
			}
		}
		String needlesstailsPath = websiteParser.getNeedlesstailsPath();
		if (needlesstailsPath == null || needlesstailsPath.trim().isEmpty()) {

		} else {
			String spechars[] = needlesstailsPath.split("\\|\\|");
			for (String string : spechars) {
				int index = content.indexOf(string);
				if (index > 0) {
					content = content.substring(0, index);
				}
			}
		}
		return content;
	}
}
