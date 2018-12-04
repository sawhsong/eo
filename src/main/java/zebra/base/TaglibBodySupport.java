package zebra.base;

import java.util.Locale;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;

import zebra.data.ParamEntity;
import zebra.util.BeanHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class TaglibBodySupport extends BodyTagSupport {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected MessageSourceAccessor messageSourceAccessor = (MessageSourceAccessor)BeanHelper.getBean("messageSourceAccessor");
	protected BodyContent bodyContent;

	/*!
	 * getMessage() - being called by sub classes
	 */
	protected String getMessage(String messageCode) {
		return messageSourceAccessor.getMessage(messageCode, new Locale(CommonUtil.lowerCase(ConfigUtil.getProperty("etc.default.language"))));
	}

	protected String getMessage(String messageCode, Locale locale) {
		return messageSourceAccessor.getMessage(messageCode, locale);
	}

	protected String getMessage(String messageCode, String languageCode) {
		return messageSourceAccessor.getMessage(messageCode, new Locale(languageCode));
	}

	protected String getMessage(String messageCode, ParamEntity paramEntity) {
		String lang = (String)paramEntity.getSession().getAttribute("langCode");
		return messageSourceAccessor.getMessage(messageCode, new Locale(CommonUtil.nvl(lang, CommonUtil.lowerCase(ConfigUtil.getProperty("etc.default.language")))));
	}

	/*!
	 * getter / setter
	 */
	public BodyContent getBodyContent() {
		return bodyContent;
	}

	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}
}