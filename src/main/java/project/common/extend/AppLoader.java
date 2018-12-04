package project.common.extend;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.common.module.commoncode.CommonCodeManager;
import project.common.module.menu.MenuManager;

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

	/*!
	 * important : execution order
	 * MemonyBean
	 */
	private void executeWorks() throws Exception {
		// Project
		MenuManager.loadMenu();
		CommonCodeManager.loadCommonCode();
	}
}