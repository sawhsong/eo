/**************************************************************************************************
 * Message
 * Description
 * - Get message from jsp page with message key (JSP Page use only)
 *************************************************************************************************/
package zebra.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.config.MessageManager;
import zebra.data.DataSet;
import zebra.util.CommonUtil;

public class Message extends TaglibSupport {
	private String key = "";
	private String langCode = "";
	private String defaultMsg = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String rtnString = "";
			String defaultLangCode = CommonUtil.nvl(getLangCode(), (String)httpSession.getAttribute("langCode"));
			DataSet ds = MessageManager.getMessageDataSet(defaultLangCode);

			rtnString = ds.getValue(getKey());

			if (CommonUtil.isEmpty(rtnString) && CommonUtil.isNotEmpty(getDefaultMsg())) {
				rtnString = getDefaultMsg();
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

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public void setDefaultMsg(String defaultMsg) {
		this.defaultMsg = defaultMsg;
	}
}