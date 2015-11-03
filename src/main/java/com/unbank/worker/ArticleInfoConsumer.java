package com.unbank.worker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.mybatis.entity.ArticleContent;
import com.unbank.mybatis.entity.ArticleInfo;
import com.unbank.store.ArticleInfoStore;

public class ArticleInfoConsumer {
	private static Log logger = LogFactory.getLog(ArticleInfoConsumer.class);

	public void consumeArticleQueue(NewsInfoMiddleWare newsInfoMiddleWare) {
		ArticleInfo articleInfo = fillArticleInfo(newsInfoMiddleWare);
		ArticleContent articleContent = fillArticleContent(newsInfoMiddleWare);
		saveArticleInfo(articleInfo, articleContent);
	}

	private void saveArticleInfo(ArticleInfo articleInfo,
			ArticleContent articleContent) {
		new ArticleInfoStore().saveArticleInfo(articleInfo, articleContent);
	}

	private ArticleContent fillArticleContent(
			NewsInfoMiddleWare newsInfoMiddleWare) {
		ArticleContent articleContent = new ArticleContent();
		articleContent.setText(newsInfoMiddleWare.getText());
		return articleContent;
	}

	private ArticleInfo fillArticleInfo(NewsInfoMiddleWare newsInfoMiddleWare) {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.setWebName(newsInfoMiddleWare.getWebName());
		articleInfo.setWebsiteId(newsInfoMiddleWare.getWebsiteId());
		articleInfo.setUrl(newsInfoMiddleWare.getUrl());
		articleInfo.setCrawlTitle(newsInfoMiddleWare.getCrawlTitle());
		articleInfo.setFileIndex(newsInfoMiddleWare.getFileIndex());
		articleInfo.setCrawlTime(newsInfoMiddleWare.getCrawlTime());
		articleInfo.setTask(newsInfoMiddleWare.getTask());
		articleInfo.setCrawlBrief(newsInfoMiddleWare.getCrawlBrief());
		articleInfo.setNewsTime(newsInfoMiddleWare.getNewsTime());
		return articleInfo;

	}
}
