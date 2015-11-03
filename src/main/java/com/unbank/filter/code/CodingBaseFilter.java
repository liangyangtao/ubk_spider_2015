package com.unbank.filter.code;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CodingBaseFilter {
	private static Log logger = LogFactory.getLog(CodingBaseFilter.class);

	public boolean checkMessyCode(String source) {
		if (source.contains("�") || source.contains("熶")
				|| source.contains("銆") || source.contains("為")
				|| source.contains("鹫") || source.contains("")
				|| source.contains("???")) {
			return true;
		}
		return false;

	}

	private boolean checkNoNeedTitle(String string) {
		if (string.length() < 5 || string.contains("@")
				|| string.contains("首页") || string.length() > 50) {
			return true;
		}
		return false;
	}

	public boolean checkTitle(String string) {
		boolean success = checkMessyCode(string);
		if (success) {
			return false;
		}
		success = checkNoNeedTitle(string);
		if (success) {
			return false;
		}
		return true;

	}

}
