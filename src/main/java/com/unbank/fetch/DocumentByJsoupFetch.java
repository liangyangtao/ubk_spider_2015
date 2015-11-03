package com.unbank.fetch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentByJsoupFetch {
	public static Log logger = LogFactory.getLog(DocumentByJsoupFetch.class);

	public static Document getDocumentByURL(String url) {
		String html = null;
		Document document = null;
		try {
			html = Fetcher.getInstance().getHtmlWithCookie(url);
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (html != null) {
			document = Jsoup.parse(html, url);
		}
		return document;

	}

}
