package com.es.portal.common.extend;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.base.Action;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class BaseAction extends Action {
	protected Logger logger = LogManager.getLogger(getClass());

	@SuppressWarnings({ "rawtypes" })
	public void setSession(Map sessionMap) {
		try {
			session.setAttribute("headerMenuId", requestDataSet.getValue("hdnHeaderMenuId"));
			session.setAttribute("headerMenuName", requestDataSet.getValue("hdnHeaderMenuName"));
			session.setAttribute("headerMenuUrl", requestDataSet.getValue("hdnHeaderMenuUrl"));

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
		String userAgent = request.getHeader("user-agent");
		String device = (CommonUtil.containsIgnoreCase(userAgent, "Android") || CommonUtil.containsIgnoreCase(userAgent, "iPhone")) ? "m" : "w";

		try {
			languageCode = CommonUtil.lowerCase(CommonUtil.nvl(language, ConfigUtil.getProperty("etc.default.language")));
			themeId = CommonUtil.lowerCase(CommonUtil.nvl(themeId, ConfigUtil.getProperty("view.theme.default")));
			maxRowsPerPage = CommonUtil.nvl(maxRowsPerPage, CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"))[2]);
			pageNumsPerPage = CommonUtil.nvl(pageNumsPerPage, CommonUtil.split(ConfigUtil.getProperty("view.data.pageNumsPerPage"), ConfigUtil.getProperty("delimiter.data"))[0]);

			session.setAttribute("frameworkName", ConfigUtil.getProperty("name.framework"));
			session.setAttribute("projectName", ConfigUtil.getProperty("name.project"));
			session.setAttribute("langCode", languageCode);
			session.setAttribute("themeId", themeId);
			session.setAttribute("device", device);
			session.setAttribute("maxRowsPerPage", maxRowsPerPage);
			session.setAttribute("pageNumsPerPage", pageNumsPerPage);
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	private void setPersonalisedSessionAttributes() {
		String themeId = CommonUtil.lowerCase((String)request.getParameter("themeId"));

		if (CommonUtil.isNotBlank(themeId)) {
			try {
				session.setAttribute("themeId", themeId);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}
}