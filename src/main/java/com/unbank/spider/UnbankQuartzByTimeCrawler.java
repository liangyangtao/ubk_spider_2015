package com.unbank.spider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.mybatis.entity.WebsiteParser;
import com.unbank.process.ArticleWorkShop;
import com.unbank.process.NewsListWorkShop;
import com.unbank.process.StoreWorkShop;
import com.unbank.process.WebPageWorkShop;
import com.unbank.process.WebsiteInfoQuartzByTimeWorkShop;

public class UnbankQuartzByTimeCrawler {

	private static Log logger = LogFactory
			.getLog(UnbankQuartzByTimeCrawler.class);
	public static Integer task;
	public static String imageUrl;
	public static Map<Integer, WebsiteParser> websitepaserlist = new HashMap<Integer, WebsiteParser>();;
	LinkedBlockingQueue<Object> webSiteInfoQueue = new LinkedBlockingQueue<Object>();;
	LinkedBlockingQueue<Object> newsListQueue = new LinkedBlockingQueue<Object>();;
	LinkedBlockingQueue<Object> newsPageQueue = new LinkedBlockingQueue<Object>();;
	LinkedBlockingQueue<Object> articleInfoQueue = new LinkedBlockingQueue<Object>();;

	public void init() {

		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 32; i++) {
			executor.execute(new NewsListWorkShop(webSiteInfoQueue,
					newsListQueue));
		}
		for (int i = 0; i < 128; i++) {
			executor.execute(new WebPageWorkShop(newsListQueue, newsPageQueue));
		}
		for (int i = 0; i < 4; i++) {
			executor.execute(new ArticleWorkShop(newsPageQueue,
					articleInfoQueue));
		}
		for (int i = 0; i < 4; i++) {
			executor.execute(new StoreWorkShop(articleInfoQueue));
		}
		executor.shutdown();
	}

	public void start() {
		new WebsiteInfoQuartzByTimeWorkShop(webSiteInfoQueue, task,websitepaserlist).fillQueue();
	}

	public Integer getTask() {
		return task;
	}

	public void setTask(Integer task) {
		this.task = task;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LinkedBlockingQueue<Object> getWebSiteInfoQueue() {
		return webSiteInfoQueue;
	}

	public void setWebSiteInfoQueue(LinkedBlockingQueue<Object> webSiteInfoQueue) {
		this.webSiteInfoQueue = webSiteInfoQueue;
	}

	public LinkedBlockingQueue<Object> getNewsListQueue() {
		return newsListQueue;
	}

	public void setNewsListQueue(LinkedBlockingQueue<Object> newsListQueue) {
		this.newsListQueue = newsListQueue;
	}

	public LinkedBlockingQueue<Object> getNewsPageQueue() {
		return newsPageQueue;
	}

	public void setNewsPageQueue(LinkedBlockingQueue<Object> newsPageQueue) {
		this.newsPageQueue = newsPageQueue;
	}

	public LinkedBlockingQueue<Object> getArticleInfoQueue() {
		return articleInfoQueue;
	}

	public void setArticleInfoQueue(LinkedBlockingQueue<Object> articleInfoQueue) {
		this.articleInfoQueue = articleInfoQueue;
	}

}
