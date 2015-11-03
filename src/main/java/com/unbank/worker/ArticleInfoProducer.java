package com.unbank.worker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.filter.code.CodingBaseFilter;

public class ArticleInfoProducer {
	public static Log logger = LogFactory.getLog(ArticleInfoProducer.class);

	public NewsInfoMiddleWare checkNewsInfo(
			NewsInfoMiddleWare newsInfoMiddleWare) {

		boolean success = checkTitle(newsInfoMiddleWare.getCrawlTitle());
		if (!success) {
			logger.info("监测文章标题不合格" + newsInfoMiddleWare.getWebsiteId()
					+ "=========" + newsInfoMiddleWare.getUrl());
			return null;
		}
		success = checkContent(newsInfoMiddleWare.getText());
		if (success) {
			logger.info("监测文章内容不合格" + newsInfoMiddleWare.getWebsiteId()
					+ "=========" + newsInfoMiddleWare.getUrl());
			return null;
		}
		return newsInfoMiddleWare;
	}

	private boolean checkContent(String text) {
		return new CodingBaseFilter().checkMessyCode(text);
	}

	private boolean checkTitle(String crawlTitle) {
		return new CodingBaseFilter().checkTitle(crawlTitle);
	}

}
