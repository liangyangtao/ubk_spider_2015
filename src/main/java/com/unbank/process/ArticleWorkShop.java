package com.unbank.process;

import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.worker.ArticleInfoProducer;

public class ArticleWorkShop extends BaseWorkShop implements Runnable {
	protected LinkedBlockingQueue<Object> newsPageQueue;
	protected LinkedBlockingQueue<Object> articleInfoQueue;

	public ArticleWorkShop(LinkedBlockingQueue<Object> newsPageQueue,
			LinkedBlockingQueue<Object> articleInfoQueue) {
		this.newsPageQueue = newsPageQueue;
		this.articleInfoQueue = articleInfoQueue;

	}

	public void run() {
		while (true) {
			try {
				if (newsPageQueue.size() > 0) {
					NewsInfoMiddleWare newsInfoMiddleWare = null;
					newsInfoMiddleWare = (NewsInfoMiddleWare) take(newsPageQueue);
					if (newsInfoMiddleWare != null) {
						fillArticleInfoQueue(newsInfoMiddleWare);
					}
				}
				sleeping(500);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}

		}
	}

	private void fillArticleInfoQueue(NewsInfoMiddleWare newsInfoMiddleWare) {
		NewsInfoMiddleWare articleInfo = new ArticleInfoProducer()
				.checkNewsInfo(newsInfoMiddleWare);
		if (articleInfo != null) {
			put(articleInfoQueue, articleInfo);
		}

	}

}
