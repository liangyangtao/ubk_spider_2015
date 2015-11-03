package com.unbank.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.fetch.DocumentByJsoupFetch;
import com.unbank.mybatis.entity.WebSiteInfo;
import com.unbank.mybatis.entity.WebsiteParser;
import com.unbank.spider.UnbankQuartzByTimeCrawler;

public class NewsListProducer {
	public static Log logger = LogFactory.getLog(NewsListProducer.class);

	public List<NewsInfoMiddleWare> fillNewsListQueue(WebSiteInfo webSiteInfo) {
		List<NewsInfoMiddleWare> middleWares = new ArrayList<NewsInfoMiddleWare>();
		List<NewsInfoMiddleWare> temp = readTitleList(webSiteInfo,
				webSiteInfo.getUrlHome());
		if (temp != null) {
			middleWares.addAll(temp);
			temp.clear();
		}
		return middleWares;
	}

	private List<NewsInfoMiddleWare> readTitleList(WebSiteInfo webSiteInfo,
			String string) {
		Document document = DocumentByJsoupFetch.getDocumentByURL(string);
		if (document != null) {
			Elements maxElements = null;
			try {
				maxElements = getTitleListElementBySQL(webSiteInfo, document);
			} catch (Exception e) {
				logger.info("通过数据库获取list失败" + webSiteInfo.getWebsiteId(), e);
			}
			if (maxElements == null || maxElements.size() == 0) {
				logger.info("通过数据库获取list失败" + webSiteInfo.getWebsiteId());
				return null;
			}
			return putQueue(webSiteInfo, maxElements);
		}
		return null;
	}

	private Elements getTitleListElementBySQL(WebSiteInfo webSiteInfo,
			Document document) {
		WebsiteParser websiteParser = UnbankQuartzByTimeCrawler.websitepaserlist
				.get(webSiteInfo.getWebsiteId());
		String listPath = websiteParser.getListPath();
		Elements listElements = document.select(listPath.trim());
		String ListNeedlesselementsPath = websiteParser
				.getListNeedlesselementsPath();
		// 去除不需要的元素
		if (ListNeedlesselementsPath != null
				&& (!ListNeedlesselementsPath.isEmpty())) {
			for (Element listElement : listElements) {
				Elements noNeedsElements = listElement
						.select(ListNeedlesselementsPath.trim());
				for (Element noNeedelement : noNeedsElements) {
					noNeedelement.remove();
				}
			}

		}
		Elements maxElements = listElements;
		return maxElements;

	}

	private List<NewsInfoMiddleWare> putQueue(WebSiteInfo webSiteInfo,
			Elements maxElements) {
		List<NewsInfoMiddleWare> middleWares = new ArrayList<NewsInfoMiddleWare>();
		for (Element element : maxElements) {
			NewsInfoMiddleWare middleWare = fillArticleInfo(webSiteInfo,
					element);
			middleWares.add(middleWare);
		}
		maxElements.clear();
		return middleWares;
	}

	private NewsInfoMiddleWare fillArticleInfo(WebSiteInfo webSiteInfo,
			Element element) {
		NewsInfoMiddleWare middleWare = new NewsInfoMiddleWare();
		middleWare.setWebName(webSiteInfo.getWebName());
		middleWare.setWebsiteId(webSiteInfo.getWebsiteId());
		middleWare.setUrl(element.absUrl("href"));
		middleWare.setCrawlTitle(findCrawlTitle(element));
		middleWare.setFileIndex((byte) 7);
		middleWare.setCrawlTime(new Date());
		middleWare.setTask(Byte.parseByte(webSiteInfo.getIstask() + ""));
		return middleWare;

	}

	private String findCrawlTitle(Element possibleNewsElement) {
		String crawlTitle = possibleNewsElement.text();
		Elements elements = possibleNewsElement.getAllElements();
		for (Element element : elements) {
			if (element.tagName().startsWith("h")) {
				crawlTitle = element.text();
				break;
			}
		}
		elements.clear();
		return crawlTitle;
	}

}
