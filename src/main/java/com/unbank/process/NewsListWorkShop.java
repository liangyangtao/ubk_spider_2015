package com.unbank.process;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.entity.NewsInfoMiddleWare;
import com.unbank.mybatis.entity.WebSiteInfo;
import com.unbank.worker.NewsListProducer;

public class NewsListWorkShop extends BaseWorkShop implements Runnable {
	protected LinkedBlockingQueue<Object> webSiteQueue;

	protected LinkedBlockingQueue<Object> newsListQueue;

	public NewsListWorkShop(LinkedBlockingQueue<Object> webSiteQueue,
			LinkedBlockingQueue<Object> newsListQueue) {
		this.webSiteQueue = webSiteQueue;
		this.newsListQueue = newsListQueue;
	}

	public void run() {
		while (true) {
			try {
				if (webSiteQueue.size() > 0) {

					WebSiteInfo webSiteInfo = null;
					webSiteInfo = (WebSiteInfo) take(webSiteQueue);

					if (webSiteInfo != null) {
						fillNewsListQueue(webSiteInfo);
					}
				}
				sleeping(1000);
			} catch (Exception e) {
				logger.info("", e);
				continue;
			}
		}
	}

	private void fillNewsListQueue(WebSiteInfo webSiteInfo) {
		List<NewsInfoMiddleWare> middleWares = new NewsListProducer()
				.fillNewsListQueue(webSiteInfo);
		for (NewsInfoMiddleWare middleWare : middleWares) {
			if (middleWare != null) {
				put(newsListQueue, middleWare);
			}
		}
		middleWares.clear();

	}
}
