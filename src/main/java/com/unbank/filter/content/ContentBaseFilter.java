package com.unbank.filter.content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.unbank.fetch.Fetcher;
import com.unbank.fetch.ImageFetch;

public class ContentBaseFilter {

	public String formatContent(Document document, String url) {
		Element element = removeNoNeedHtmlElement(url, document);
		removeNoNeedElementsByCssQuery(element);
		formatTagName(element);
		formatElements(element);
		saveImage(element, url);
		String content = element.toString();
		content = replaceStockCode(content);
		content = content.replace("<html><head></head><body>", "");
		content = content.replace("</body></html>", "");
		content = content.replace("'", "");
		content = removeNoNeedChars(content);
		return content;
	}

	private void formatTagName(Element maxTextElement) {
		Elements allElements = maxTextElement.children();
		for (Element element : allElements) {

			if (element.tagName().equals("br")) {
				// 如果是br 和 image 就什么也不做
			} else if (!(element.tagName().equals("img") || element.tagName()
					.equals("table"))) {
				// 去除属性
				removeElementAttr(element);
				// 更换标签
				if (element.tagName().equals("p")
						|| element.tagName().equals("div")) {
					element.tagName("p");
				} else if (element.tagName().equals("b")) {
					element.tagName("strong");
				} else {

					if (element.tagName().equals("br")
							|| element.tagName().equals("img")
							|| element.tagName().equals("table")
							|| element.tagName().equals("th")
							|| element.tagName().equals("tr")
							|| element.tagName().equals("td")
							|| element.tagName().equals("tbody")
							|| element.tagName().equals("strong")
							|| element.tagName().equals("center")) {

					} else {
						element.tagName("v");
					}
				}
				removeNoTextNode(element);

			}
			if (element != null) {
				formatTagName(element);
			}

		}

	}

	public void removeNoTextNode(Element element) {
		if (element != null && element.text().length() == 0) {
			Elements elements = element.getAllElements();
			boolean isRemove = true;
			for (Element element2 : elements) {
				if (element2.tagName().equals("img")) {
					isRemove = false;
					break;
				}
			}
			if (isRemove) {
				element.remove();
			}
		}
	}

	public Element removeNoNeedHtmlElement(String url, Document document) {
		Element element = document.select("div.art_main").first();
		return element;
	}

	public String removeNoNeedChars(String content) {
		String stockCodes[] = new String[] {
				"<!--.[^-]*(?=-->)-->",
				"(?is)<!--.*?-->",
				"\\(\\d{6},\\)",
				"\\(\\d{1,6}[\\.|,|，|；|;|-][ |(&nbsp;)]{0,10}\\w{2}\\)",
				"\\(\\d{1,6}\\.\\w{2,4}[，|,|;|；][\\u4e00-\\u9fa5]{2,10}\\)",
				"\\(\\d{1,6}\\.\\w{2,4}/\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4};\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4}；\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4}、\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4},\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4}，\\d{1,6}\\.\\w{2,4}\\)",
				"\\(\\d{1,6}\\.\\w{2,4},-{0,1}\\d{1,2}\\.\\d{1,4},-{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{2,4}, -{0,1}\\d{1,2}\\.\\d{1,4}, -{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{2,4},(&nbsp;){0,10}-{0,1}\\d{1,2}\\.\\d{1,4},(&nbsp;){0,10}-{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{2,4}，-{0,1}\\d{1,2}\\.\\d{1,4}，-{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{2,4}， -{0,1}\\d{1,2}\\.\\d{1,4}， -{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{2,4}，(&nbsp;){0,10}-{0,1}\\d{1,2}\\.\\d{1,4}，(&nbsp;){0,10}-{0,1}\\d{1,2}\\.\\d{1,2}%\\)",
				"\\(\\d{1,6}\\.\\w{1,4},-{0,1}\\d{1,6}\\.\\w{2,4},-{0,1}\\d{1,2}\\.\\d{1,2}%,实时行情\\)",
				"\\(\\d{1,6}\\.\\w{1,4}，-{0,1}\\d{1,6}\\.\\w{2,4}，-{0,1}\\d{1,2}\\.\\d{1,2}%，实时行情\\)",
				// "\\d{1,6}[\\.|,|，|；|;|-]{0,1}[ |(&nbsp;)]{0,10}\\w{2,4}[\\.|,|，|；|;]{0,1}[ |(&nbsp;)]{0,10}",
				"\\[-{0,1}\\d{1,4}\\.\\d{1,2}% 资金 研报\\]",
				"\\[-{0,1}\\d{1,4}\\.\\d{1,2}%[(&nbsp;)| |	| ]{0,10}资金 研报\\]",

				"摘自\\w{2,5}网", "【[\\u4e00-\\u9fa5]{2,6}网】",
				"\\(([^\\(]*)?\\d{5,6}([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?简称([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?微博([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?基金吧([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?股吧([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?代码([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?记者([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?编辑([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?作者([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?点击([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?访问([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?www\\.([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?http://([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?来源([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?标题([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?微信([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?收盘价([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?客户端([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?交易所([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?行情([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?评论([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?声明([^\\(|\\)]*)?\\)",
				"\\(([^\\(]*)?版权([^\\(|\\)]*)?\\)",

		};
		for (String string : stockCodes) {
			content = content.replaceAll(string, "");
		}
		return content;
	}

	public void saveImage(Element maxTextElement, String url) {
		Elements elements = maxTextElement.select("img");
		for (Element element : elements) {
			if (element.attr("style").contains("display:none;")) {
				element.remove();
				continue;
			}
			String imgSrc = element.absUrl("src");
			if (imgSrc == null || imgSrc.trim().isEmpty()) {
				element.remove();
				continue;
			}
			String imgUrl = new ImageFetch().fetchImage(imgSrc);
			if (imgUrl != null && (!imgUrl.trim().isEmpty())) {
				element.attr("src", imgUrl);
				// 去掉除src 的所有属性
				Attributes attributes = element.attributes();
				for (Attribute attribute : attributes) {
					if (attribute.getKey().isEmpty()) {
						continue;
					} else if (attribute.getKey().equals("src")) {
						continue;
					} else {
						element.removeAttr(attribute.getKey());
					}
				}
			} else {
				element.remove();
			}
		}

	}

	// 去掉不需要的HTML标签
	public void removeNoNeedElementsByCssQuery(Element contentElement) {
		String cssQuerys[] = new String[] { "script", "style", "textarea",
				"select", "noscript", "input", "em" };
		for (String string : cssQuerys) {
			removeNoNeedElement(contentElement, string);
		}
	} // 去掉不想要的html 标签

	public void removeNoNeedElement(Element element, String cssQuery) {
		if (element == null) {
			return;
		}
		Elements elements = element.select(cssQuery);
		for (Element element2 : elements) {
			element2.remove();
		}
	}

	public void formatElements(Element contentElement) {
		// 去重属性
		removeElementAttr(contentElement);
		Elements allElements = contentElement.children();
		for (Element element : allElements) {
			removeElementAttr(element);
			if (element != null) {
				formatElements(element);
			}

		}

	}

	// 移除所有的属性

	public void removeElementAttr(Element element) {
		if (element == null) {
			return;
		}
		Attributes attributes = element.attributes();
		for (Attribute attribute : attributes) {
			if (attribute.getKey().isEmpty()) {
				continue;
			} else if (attribute.getKey().equals("align")
					&& attribute.getValue().equals("center")) {
				continue;
			} else if (attribute.getKey().equals("style")
					&& (attribute.getValue().toLowerCase()
							.contains("text-align: center"))) {
				continue;
			} else if (attribute.getKey().equals("rowspan")
					|| attribute.getKey().equals("colspan")
					|| attribute.getKey().equals("src")
					|| attribute.getKey().equals("data-src")) {
				continue;
			} else {
				element.removeAttr(attribute.getKey());
			}
		}
	}

	public String replaceStockCode(String content) {
		try {
			content = content.replaceAll(">\\s{0,10}", ">");
			content = content.replaceAll(">\\s{0,10}(&nbsp; ){0,10}", ">");
			content = content.replaceAll(">\\s{0,10}(&nbsp;){0,10}", ">");
			content = content.replaceAll(">　{0,10}", ">");
			content = content.replaceAll("\\s{0,10}<", "<");
			content = content.replaceAll("<br>", "</p><p>");
			content = content.replaceAll("<br />", "</p><p>");
			content = content.replaceAll("<br/>", "</p><p>");
			content = content.replace("<v>", "");
			content = content.replace("</v>", "");
			content = content.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return content;
		}
		return content;
	}


	public String getContent(String url, Document document, String title) {

		String content = formatContent(document, url);
		return content;
	}
}
