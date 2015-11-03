package com.unbank.process;

import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.worker.ArticleInfoConsumer;

public class StoreWorkShop extends BaseWorkShop implements Runnable {
	LinkedBlockingQueue<Object> articleInfoQueue;

	public StoreWorkShop(LinkedBlockingQueue<Object> articleInfoQueue) {
		this.articleInfoQueue = articleInfoQueue;
	}

	public void run() {
		while (true) {
			try {
				if (articleInfoQueue.size() > 0) {
					NewsInfoMiddleWare newsInfoMiddleWare = null;

					newsInfoMiddleWare = (NewsInfoMiddleWare) take(articleInfoQueue);

					if (newsInfoMiddleWare != null) {
						consumeArticleQueue(newsInfoMiddleWare);
					}
				}
				sleeping(800);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}
		}
	}

	private void consumeArticleQueue(NewsInfoMiddleWare newsInfoMiddleWare) {
		new ArticleInfoConsumer().consumeArticleQueue(newsInfoMiddleWare);
	}

}
