package com.unbank.worker;

import java.util.List;

import com.unbank.mybatis.entity.WebSiteInfo;
import com.unbank.store.WebSiteInfoReader;

public class WebSiteInfoProductor {

	public List<WebSiteInfo> getWebSiteInfoByTask(Integer task) {
		return new WebSiteInfoReader().getWebSiteInfoByTask(task);
	}

}
