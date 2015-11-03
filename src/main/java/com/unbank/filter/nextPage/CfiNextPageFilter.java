package com.unbank.filter.nextPage;

import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class CfiNextPageFilter extends NextPageBaseFilter {

	private String domain[] = new String[] { "industry.cfi.cn" };

	public CfiNextPageFilter() {
		for (int i = 0; i < domain.length; i++) {
			NextPageFilterLocator.getInstance().register(domain[i], this);
		}

	}

	@Override
	public Integer extractMaxPageNum(Document document) {
		Elements scriptElements = document.body().select("#turnpage1");
		Element element = scriptElements.last();
		if (element == null) {
			return 0;
		}
		String countPageString = element.select("a").last().text().trim();
		countPageString = StringUtils.substringBetween(countPageString, "第",
				"页");
		if (StringUtils.isNumeric(countPageString)) {
			return Integer.parseInt(countPageString);
		}
		return 0;

	}

	@Override
	public TreeMap<Integer, String> extractNextPageUrlMap(Document document,
			String baseUrl) {
		int maxPage = extractMaxPageNum(document);
		// http://industry.cfi.cn/p20140927000472.html
		// http://industry.cfi.cn/newspage.aspx?id=20140928000040&p=1
		String temp = StringUtils.substringBetween(baseUrl,
				"http://industry.cfi.cn/p", ".html");
		String preUrl = "http://industry.cfi.cn/newspage.aspx?id=";
		TreeMap<Integer, String> nextPageUrlMap = new TreeMap<Integer, String>();
		for (int i = 1; i < maxPage; i++) {
			nextPageUrlMap.put(i, preUrl + temp + "&p=" + i);
		}
		return nextPageUrlMap;
	}

}
