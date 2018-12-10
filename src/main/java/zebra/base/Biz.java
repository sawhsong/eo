package zebra.base;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.support.MessageSourceAccessor;

import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class Biz {
	protected Logger logger = LogManager.getLogger(getClass());

	private SessionFactory sessionFactory;
	private MessageSourceAccessor messageSourceAccessor;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

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
}