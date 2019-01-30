package com.es.portal.common.extend;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.es.portal.common.module.commonlookup.CommonLookupManager;
import com.es.portal.common.module.menu.MenuManager;

public class AppLoader extends HttpServlet {
	private Logger logger = LogManager.getLogger(this.getClass());

	public void init(ServletConfig config) throws ServletException {
		try {
			executeWorks();
		} catch (Exception ex) {
			logger.error("Application loading failed.");
			logger.error(ex);
		}
	}

	private void executeWorks() throws Exception {
		MenuManager.loadMenu();
		CommonLookupManager.loadCommonLookup();
	}
}