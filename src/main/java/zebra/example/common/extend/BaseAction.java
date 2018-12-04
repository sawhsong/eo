package zebra.example.common.extend;

import java.util.Map;

import zebra.base.Action;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class BaseAction extends Action {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setSession(Map sessionMap) {
		try {
			sessionMap.put("headerMenuId", requestDataSet.getValue("hdnHeaderMenuId"));
			sessionMap.put("headerMenuName", requestDataSet.getValue("hdnHeaderMenuName"));
			sessionMap.put("headerMenuUrl", requestDataSet.getValue("hdnHeaderMenuUrl"));
			sessionMap.put("leftMenuId", requestDataSet.getValue("hdnLeftMenuId"));
			sessionMap.put("leftMenuName", requestDataSet.getValue("hdnLeftMenuName"));
			sessionMap.put("leftMenuUrl", requestDataSet.getValue("hdnLeftMenuUrl"));

			setAdditionalSessionAttributes();
			setPersonalisedSessionAttributes();

			super.setSession(sessionMap);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*!
	 * private methods - addtional setting
	 */
	private void setAdditionalSessionAttributes() {
		String language = CommonUtil.lowerCase((String)session.getAttribute("langCode"));
		String themeId = CommonUtil.lowerCase((String)session.getAttribute("themeId"));
		String maxRowsPerPage = (String)session.getAttribute("maxRowsPerPage");
		String pageNumsPerPage = (String)session.getAttribute("pageNumsPerPage");

		try {
			languageCode = CommonUtil.lowerCase(CommonUtil.nvl(language, ConfigUtil.getProperty("etc.default.language")));
			themeId = CommonUtil.lowerCase(CommonUtil.nvl(themeId, ConfigUtil.getProperty("view.theme.default")));
			maxRowsPerPage = CommonUtil.nvl(maxRowsPerPage, CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"))[2]);
			pageNumsPerPage = CommonUtil.nvl(pageNumsPerPage, CommonUtil.split(ConfigUtil.getProperty("view.data.pageNumsPerPage"), ConfigUtil.getProperty("delimiter.data"))[0]);

			session.setAttribute("frameworkName", ConfigUtil.getProperty("name.framework"));
			session.setAttribute("projectName", ConfigUtil.getProperty("name.project"));
			session.setAttribute("langCode", languageCode);
			session.setAttribute("themeId", themeId);
			session.setAttribute("themeName", ZebraCommonCodeManager.getCodeDescription("USER_THEME_TYPE", CommonUtil.upperCase(themeId)));
			session.setAttribute("maxRowsPerPage", maxRowsPerPage);
			session.setAttribute("pageNumsPerPage", pageNumsPerPage);
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	private void setPersonalisedSessionAttributes() {
		String themeId = CommonUtil.lowerCase((String)request.getParameter("themeId"));

		// Theme selectbox & theme attributes
		if (CommonUtil.isNotBlank(themeId)) {
			try {
				session.setAttribute("themeId", themeId);
				session.setAttribute("themeName", ZebraCommonCodeManager.getCodeDescription("USER_THEME_TYPE", CommonUtil.upperCase(themeId)));
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}
}