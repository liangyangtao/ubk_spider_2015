package com.unbank.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.dao.ArticleContentMapper;
import com.unbank.mybatis.dao.ArticleInfoMapper;
import com.unbank.mybatis.dao.EveryDayNumMapper;
import com.unbank.mybatis.dao.EveryWebDayNumMapper;
import com.unbank.mybatis.dao.MyBatisConnectionFactory;
import com.unbank.mybatis.entity.ArticleContent;
import com.unbank.mybatis.entity.ArticleInfo;
import com.unbank.mybatis.entity.EveryWebDayNum;

public class ArticleInfoStore {
	private static Log logger = LogFactory.getLog(ArticleInfoStore.class);

	public void saveArticleInfo(ArticleInfo articleInfo,
			ArticleContent articleContent) {
		SqlSession sqlSession = MyBatisConnectionFactory
				.getInstanceSessionFactory().openSession();
		try {
			int crawlId = saveArticleInfo(articleInfo, sqlSession);
			saveArticleContent(articleContent, sqlSession, crawlId);
			saveEveryDayNum(sqlSession);
			saveEveryWebDayNum(sqlSession, articleInfo);
			sqlSession.commit();
		} catch (Exception e) {
			if (e instanceof org.apache.ibatis.exceptions.PersistenceException) {
				logger.info("保存新闻失败", e);
			} else {
				logger.info("保存新闻失败", e);
			}
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}

	}

	private void saveEveryWebDayNum(SqlSession sqlSession,
			ArticleInfo articleInfo) {
		EveryWebDayNumMapper everyWebDayNumMapper = sqlSession
				.getMapper(EveryWebDayNumMapper.class);
		EveryWebDayNum everyWebDayNum = new EveryWebDayNum();
		everyWebDayNum.setWebsiteId(articleInfo.getWebsiteId());
		everyWebDayNumMapper.insert(everyWebDayNum);
	}

	private void saveEveryDayNum(SqlSession sqlSession) {

		EveryDayNumMapper everyDayNumMapper = sqlSession
				.getMapper(EveryDayNumMapper.class);
		everyDayNumMapper.insert(null);

	}

	private void saveArticleContent(ArticleContent articleContent,
			SqlSession sqlSession, int crawlId) {
		ArticleContentMapper articleContentMapper = sqlSession
				.getMapper(ArticleContentMapper.class);
		articleContent.setCrawlId(crawlId);
		articleContentMapper.insertSelective(articleContent);
	}

	private int saveArticleInfo(ArticleInfo articleInfo, SqlSession sqlSession) {
		ArticleInfoMapper articleInfoMapper = sqlSession
				.getMapper(ArticleInfoMapper.class);
		articleInfoMapper.insertSelective(articleInfo);
		int crawlId = articleInfo.getCrawlId();
		return crawlId;
	}
}
