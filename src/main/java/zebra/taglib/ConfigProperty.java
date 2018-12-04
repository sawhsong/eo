package zebra.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspPage;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class ConfigProperty extends TaglibSupport {
	private String key = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			JspPage jspPage = (JspPage)pageContext.getAttribute("javax.servlet.jsp.jspPage");
			String langCode = CommonUtil.lowerCase((String)httpSession.getAttribute("langCode"));
			String frameworkName = ConfigUtil.getProperty("name.framework");
			String projectName = ConfigUtil.getProperty("name.project");
			String themeId = CommonUtil.lowerCase((String)httpSession.getAttribute("themeId"));
			String rtnString = "";

			rtnString = ConfigUtil.getProperty(getKey());

			// Replace variable names
			if (CommonUtil.isNotBlank(rtnString)) {
				rtnString = CommonUtil.replace(rtnString, "#DB_VENDOR#", ConfigUtil.getProperty("db.vendor"));
				rtnString = CommonUtil.replace(rtnString, "#THEME_ID#", themeId);
				rtnString = CommonUtil.replace(rtnString, "#LANG_CODE#", langCode);
				rtnString = CommonUtil.replace(rtnString, "#FRAMEWORK_NAME#", frameworkName);
				rtnString = CommonUtil.replace(rtnString, "#PROJECT_NAME#", projectName);
			}

			if (CommonUtil.equalsIgnoreCase(getKey(), "headMainMenuIconColor")) {
				if (CommonUtil.equalsIgnoreCase(themeId, "theme001")) {
					rtnString = "Black";
				} else {
					rtnString = "White";
				}
			}

			if (CommonUtil.equalsIgnoreCase(getKey(), "viewPageName")) {
				String name = jspPage.getClass().getName();
				rtnString = CommonUtil.substringBefore(CommonUtil.substringAfterLast(name, "."), "_");
			}

			if (CommonUtil.equalsIgnoreCase(getKey(), "viewPageJsName")) {
				String removeString = "org/apache/jsp";
				String replaceFrom[] = new String[] {"app", "_jsp"}, replaceTo[] = new String[] {"appjs", ".js"};
				String name = CommonUtil.remove(CommonUtil.replace(jspPage.getClass().getName(), ".", "/"), removeString);

				rtnString = CommonUtil.replaceEach(name, replaceFrom, replaceTo);
			}

			if (CommonUtil.equalsIgnoreCase(getKey(), "commonModuleViewPageJsName")) {
				String removeString = "org/apache/jsp";
				String replaceFrom[] = new String[] {"_jsp"}, replaceTo[] = new String[] {".js"};
				String name = CommonUtil.remove(CommonUtil.replace(jspPage.getClass().getName(), ".", "/"), removeString);

				rtnString = CommonUtil.replaceEach(name, replaceFrom, replaceTo);
			}

			jspWriter.print(rtnString);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return SKIP_BODY;
	}
	/*!
	 * getter / setter
	 */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}