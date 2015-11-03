package com.unbank.process;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.unbank.mybatis.entity.WebSiteInfo;
import com.unbank.mybatis.entity.WebsiteParser;
import com.unbank.store.WebsiteParserReader;
import com.unbank.worker.WebSiteInfoProductor;

public class WebsiteInfoQuartzByTimeWorkShop extends BaseWorkShop {
	protected LinkedBlockingQueue<Object> webSiteQueue;
	protected Integer task;
	protected Map<Integer, WebsiteParser> websitepaserlist;

	public WebsiteInfoQuartzByTimeWorkShop(
			LinkedBlockingQueue<Object> webSiteQueue, Integer task,
			Map<Integer, WebsiteParser> websitepaserlist) {
		this.webSiteQueue = webSiteQueue;
		this.task = task;
		this.websitepaserlist = websitepaserlist;
	}

	public void fillQueue() {
		List<WebSiteInfo> webSites = new WebSiteInfoProductor()
				.getWebSiteInfoByTask(task);
		for (WebSiteInfo webSiteInfo : webSites) {
			WebsiteParser websiteParser = new WebsiteParserReader()
					.readWebsitePaserById(webSiteInfo.getWebsiteId());
			websitepaserlist.put(webSiteInfo.getWebsiteId(), websiteParser);
			put(webSiteQueue, webSiteInfo);
		}
		webSites.clear();
	}

}
