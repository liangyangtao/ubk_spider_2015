package com.unbank.filter.nextPage;

import java.util.TreeMap;

import org.jsoup.nodes.Document;

public class NextPageBaseFilter implements NextPageFilter {

	public Integer extractMaxPageNum(Document document) {

		return null;
	}

	public TreeMap<Integer, String> extractNextPageUrlMap(Document document,
			String baseUrl) {

		return new TreeMap<Integer, String>();
	}

}
