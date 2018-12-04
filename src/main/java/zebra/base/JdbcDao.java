package zebra.base;

import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class JdbcDao extends JdbcDaoSupport {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected MessageSourceAccessor messageSourceAccessor;
	protected DataFieldMaxValueIncrementer incrementer;
	protected SqlSession sqlSession;
	protected boolean isMultipleDatasource = false;
	protected String dataSourceName = "";

	public void init() {
	}

	public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
		this.incrementer = incrementer;
	}

	protected DataFieldMaxValueIncrementer getIncrementer() {
		return this.incrementer;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	protected MessageSourceAccessor getMessageSourceAccessor() {
		return this.messageSourceAccessor;
	}

	protected SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public boolean isMultipleDatasource() {
		return isMultipleDatasource;
	}

	public void setMultipleDatasource(boolean isMultipleDatasource) {
		this.isMultipleDatasource = isMultipleDatasource;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		setMultipleDatasource(true);
		this.dataSourceName = dataSourceName;
	}

	public void resetDataSourceName() {
		setMultipleDatasource(false);
		this.dataSourceName = "";
	}

	/*!
	 * getMessage()
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