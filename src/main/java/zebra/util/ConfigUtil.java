package zebra.util;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.util.CommonUtil;

public class ConfigUtil {
	private Logger logger = LogManager.getLogger(this.getClass());

	private final String fileName = "/config.properties";
	private Properties prop;
	private static ConfigUtil instance;

	static {
		instance = new ConfigUtil();
	}

	private ConfigUtil() {
		prop = new Properties();

		try {
			prop.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static ConfigUtil getInstance() {
		return instance;
	}

	public static String getProperty(String key) {
		String rtnString = "";

		// Get attributes by name from config.properties
		rtnString = (String)ConfigUtil.getInstance().prop.getProperty(key);

		// Web Resource Paths
		if (CommonUtil.isBlank(rtnString)) {
			if (CommonUtil.equalsIgnoreCase(key, "sharedRoot")) {rtnString = ConfigUtil.getProperty("path.shared.root");}
			else if (CommonUtil.equalsIgnoreCase(key, "sharedPage")) {rtnString = ConfigUtil.getProperty("path.shared.page");}
			else if (CommonUtil.equalsIgnoreCase(key, "sharedCss")) {rtnString = ConfigUtil.getProperty("path.shared.css");}
			else if (CommonUtil.equalsIgnoreCase(key, "sharedImg")) {rtnString = ConfigUtil.getProperty("path.shared.img");}
			else if (CommonUtil.equalsIgnoreCase(key, "sharedJs")) {rtnString = ConfigUtil.getProperty("path.shared.js");}

			else if (CommonUtil.equalsIgnoreCase(key, "cssJqUi")) {rtnString = ConfigUtil.getProperty("path.css.jquery.ui");}
			else if (CommonUtil.equalsIgnoreCase(key, "cssBootstrap")) {rtnString = ConfigUtil.getProperty("path.css.bootstrap");}

			else if (CommonUtil.equalsIgnoreCase(key, "imgIcon")) {rtnString = ConfigUtil.getProperty("path.image.icon");}
			else if (CommonUtil.equalsIgnoreCase(key, "imgSortableTable")) {rtnString = ConfigUtil.getProperty("path.image.sortableTable");}

			else if (CommonUtil.equalsIgnoreCase(key, "jsApp")) {rtnString = ConfigUtil.getProperty("path.web.appJs");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsAppFwk")) {rtnString = ConfigUtil.getProperty("path.web.fwkAppJs");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsCommon")) {rtnString = ConfigUtil.getProperty("path.js.common");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsJq")) {rtnString = ConfigUtil.getProperty("path.js.jquery");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsNg")) {rtnString = ConfigUtil.getProperty("path.js.angular");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsJqPlugin")) {rtnString = ConfigUtil.getProperty("path.js.jquery.plugin");}
			else if (CommonUtil.equalsIgnoreCase(key, "jsMessage")) {rtnString = ConfigUtil.getProperty("path.js.message");}

			else if (CommonUtil.equalsIgnoreCase(key, "cssTheme")) {rtnString = ConfigUtil.getProperty("path.css.theme");}
			else if (CommonUtil.equalsIgnoreCase(key, "imgTheme")) {rtnString = ConfigUtil.getProperty("path.image.theme");}
			else if (CommonUtil.equalsIgnoreCase(key, "imgThemeCom")) {rtnString = ConfigUtil.getProperty("path.image.theme.common");}
			else if (CommonUtil.equalsIgnoreCase(key, "imgThemeCal")) {rtnString = ConfigUtil.getProperty("path.image.theme.calendar");}
			else if (CommonUtil.equalsIgnoreCase(key, "imgThemePage")) {rtnString = ConfigUtil.getProperty("path.image.theme.page");}

			else if (CommonUtil.equalsIgnoreCase(key, "ckEditor")) {rtnString = ConfigUtil.getProperty("path.plugin.ckeditor");}
			else if (CommonUtil.equalsIgnoreCase(key, "slider")) {rtnString = ConfigUtil.getProperty("path.plugin.jssorSlider");}
			else if (CommonUtil.equalsIgnoreCase(key, "fontas")) {rtnString = ConfigUtil.getProperty("path.plugin.fontawesome");}

			else if (CommonUtil.equalsIgnoreCase(key, "defaultDateTime")) {rtnString = ConfigUtil.getProperty("format.default.dateTime");}
			else if (CommonUtil.equalsIgnoreCase(key, "defaultDate")) {rtnString = ConfigUtil.getProperty("format.default.date");}
			else if (CommonUtil.equalsIgnoreCase(key, "dateFormatJs")) {rtnString = ConfigUtil.getProperty("format.date.js");}
			else if (CommonUtil.equalsIgnoreCase(key, "dateFormatJs_en")) {rtnString = ConfigUtil.getProperty("format.date.js_en");}
			else if (CommonUtil.equalsIgnoreCase(key, "dateFormatJs_ko")) {rtnString = ConfigUtil.getProperty("format.date.js_ko");}
			else if (CommonUtil.equalsIgnoreCase(key, "dateFormatJava")) {rtnString = ConfigUtil.getProperty("format.date.java");}

			else if (CommonUtil.equalsIgnoreCase(key, "submitEffect")) {rtnString = ConfigUtil.getProperty("view.effect.submit");}
			else if (CommonUtil.equalsIgnoreCase(key, "customFileObject")) {rtnString = ConfigUtil.getProperty("view.object.customisedFileSelect");}
			else if (CommonUtil.equalsIgnoreCase(key, "recordDelimiter")) {rtnString = ConfigUtil.getProperty("delimiter.record");}
			else if (CommonUtil.equalsIgnoreCase(key, "dataDelimiter")) {rtnString = ConfigUtil.getProperty("delimiter.data");}
			else if (CommonUtil.equalsIgnoreCase(key, "dataSetHeaderDelimiter")) {rtnString = ConfigUtil.getProperty("delimiter.header.dataset");}
			else if (CommonUtil.equalsIgnoreCase(key, "pagehandlerActionType")) {rtnString = ConfigUtil.getProperty("pagehandler.actionType");}

			else if (CommonUtil.equalsIgnoreCase(key, "autoSetSearchCriteria")) {rtnString = ConfigUtil.getProperty("view.object.autoSetSearchCriteria");}
			else if (CommonUtil.equalsIgnoreCase(key, "searchCriteriaElementSuffix")) {rtnString = ConfigUtil.getProperty("etc.formElement.searchCriteriaSuffix");}
		}

		return CommonUtil.nvl(rtnString, "");
	}

	public static String getProperty(String key, String defaultValue) {
		return CommonUtil.nvl(getProperty(key), defaultValue);
	}
}