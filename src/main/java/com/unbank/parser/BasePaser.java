package com.unbank.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;

import com.unbank.fetch.DocumentByJsoupFetch;

public class BasePaser {

	public static Log logger = LogFactory.getLog(BasePaser.class);

	public Document getDocumentByURL(String url) {
		Document document = DocumentByJsoupFetch.getDocumentByURL(url);
		return document;
	}

	public String removeSingleQuotes(String source) {
		if (source == null) {
			return null;
		}
		source = source.replace("'", "");
		return source;
	}
}
