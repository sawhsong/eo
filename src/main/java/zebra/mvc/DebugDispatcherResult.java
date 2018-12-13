package zebra.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.result.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class DebugDispatcherResult extends StrutsResultSupport {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		RequestDispatcher dispatcher = request.getRequestDispatcher(finalLocation);
		HttpSession session = request.getSession();
		String device = CommonUtil.nvl((String)session.getAttribute("device"));

		if (CommonUtil.equalsIgnoreCase(ConfigUtil.getProperty("log.dispatcher.result"), "Y")) {
			logger.debug("Execution Result : " + invocation.getAction().getClass().getName() + " => " + invocation.getResultCode() + " => " + finalLocation);
			logger.debug("device : "+session.getAttribute("device"));
		}

		if (CommonUtil.toBoolean(ConfigUtil.getProperty("isMobileUse")) && CommonUtil.equalsAnyIgnoreCase(device, "m")) {
			finalLocation = CommonUtil.substringBefore(finalLocation, ".")+device+".jsp";
			dispatcher = request.getRequestDispatcher(finalLocation);
		}

		dispatcher.forward(request, response);
	}
}