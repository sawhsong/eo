package zebra.util;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.config.MessageManager;
import zebra.data.DataSet;

public class TaglibUtil extends CommonUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(TaglibUtil.class);

	public static String getMccpValue(HttpSession httpSession, String value) {
		String str = "", key = "", confPropValue = "";
		String langCode = CommonUtil.lowerCase((String)httpSession.getAttribute("langCode"));
		String frameworkName = ConfigUtil.getProperty("name.framework");
		String projectName = ConfigUtil.getProperty("name.project");
		String themeId = CommonUtil.lowerCase((String)httpSession.getAttribute("themeId"));

		if (CommonUtil.startsWith(value, "<mc:cp key=")) {
			key = CommonUtil.substringAfter(value, "=");
			key = CommonUtil.substringBefore(key, "/>");

			confPropValue = ConfigUtil.getProperty(key);
			// Replace variable names
			if (CommonUtil.isNotBlank(confPropValue)) {
				confPropValue = CommonUtil.replace(confPropValue, "#DB_VENDOR#", ConfigUtil.getProperty("db.vendor"));
				confPropValue = CommonUtil.replace(confPropValue, "#THEME_ID#", themeId);
				confPropValue = CommonUtil.replace(confPropValue, "#LANG_CODE#", langCode);
				confPropValue = CommonUtil.replace(confPropValue, "#FRAMEWORK_NAME#", frameworkName);
				confPropValue = CommonUtil.replace(confPropValue, "#PROJECT_NAME#", projectName);
			}

			str = CommonUtil.replacePattern(value, "<(.*)>", confPropValue);
		} else {
			str = value;
		}

		return str;
	}

	public static String getMcmsgValue(String value, String langCode) throws Exception {
		String str = "", key = "", msgValue = "";
		DataSet ds = MessageManager.getMessageDataSet(langCode);

		if (CommonUtil.startsWith(value, "<mc:msg key=")) {
			key = CommonUtil.substringAfter(value, "=");
			key = CommonUtil.substringBefore(key, "/>");

			msgValue = ds.getValue(key);
			str = CommonUtil.replacePattern(value, "<(.*)>", msgValue);
		} else {
			str = value;
		}

		return str;
	}
}