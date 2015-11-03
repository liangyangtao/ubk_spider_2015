package com.unbank.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.unbank.mybatis.dao.MyBatisConnectionFactory;
import com.unbank.mybatis.dao.WebSiteInfoMapper;
import com.unbank.mybatis.entity.WebSiteInfo;
import com.unbank.mybatis.entity.WebSiteInfoExample;

public class WebSiteInfoReader {

	private final Logger logger = Logger.getLogger(WebSiteInfoReader.class);

	private WebSiteInfoExample getTaskConditionExample(Integer num) {
		WebSiteInfoExample webSiteInfoExample = new WebSiteInfoExample();
		if (num != 0) {
			webSiteInfoExample.or().andIstaskEqualTo(num)
					.andWebsiteIdGreaterThan(5744);
		}
		return webSiteInfoExample;
	}

	public List<WebSiteInfo> getWebSiteInfoByTask(Integer task) {
		SqlSession sqlSession = MyBatisConnectionFactory
				.getInstanceSessionFactory().openSession();
		WebSiteInfoMapper webSiteInfoMapper = sqlSession
				.getMapper(WebSiteInfoMapper.class);
		WebSiteInfoExample webSiteInfoExample = getTaskConditionExample(task);
		List<WebSiteInfo> webSiteInfos = null;
		try {
			webSiteInfos = webSiteInfoMapper
					.selectByExample(webSiteInfoExample);
		} catch (Exception e) {
			logger.info("", e);

		} finally {
			sqlSession.close();
		}
		return webSiteInfos;

	}

}
