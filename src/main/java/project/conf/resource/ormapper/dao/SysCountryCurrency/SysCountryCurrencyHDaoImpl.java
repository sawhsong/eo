package project.conf.resource.ormapper.dao.SysCountryCurrency;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysCountryCurrency;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.ConfigUtil;

public class SysCountryCurrencyHDaoImpl extends BaseHDao implements SysCountryCurrencyDao {
	public int insert(SysCountryCurrency sysCountryCurrency) throws Exception {
		return insertWithSQLQuery(sysCountryCurrency);
	}

	public int update(String countryCurrencyId, SysCountryCurrency sysCountryCurrency) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("country_currency_id = '"+countryCurrencyId+"'");
		return updateWithSQLQuery(queryAdvisor, sysCountryCurrency);
	}

	public int delete(String countryCurrencyIds[]) throws Exception {
		int result = 0;

		if (!(countryCurrencyIds == null || countryCurrencyIds.length == 0)) {
			for (int i=0; i<countryCurrencyIds.length; i++) {
				result += delete(countryCurrencyIds[i]);
			}
		}
		return result;
	}

	public int delete(String countryCurrencyId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("country_currency_id = '"+countryCurrencyId+"'");
		return deleteWithSQLQuery(queryAdvisor, new SysCountryCurrency());
	}

	public DataSet getCurrencyCodeDataSetForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String inputValue = requestDataSet.getValue("inputValue");

		queryAdvisor.addAutoFillCriteria(inputValue, "lower(currency_alphabetic_code) like lower('"+inputValue+"%')");
		queryAdvisor.addOrderByClause("currency_alphabetic_code");

		return selectAsDataSet(queryAdvisor, "query.SysCountryCurrency.getCurrencyCodeDataSetForAutoCompletion");
	}

	public DataSet getCountryNameDataSetForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String inputValue = requestDataSet.getValue("inputValue");

		queryAdvisor.addAutoFillCriteria(inputValue, "lower(country_name) like lower('"+inputValue+"%')");
		queryAdvisor.addOrderByClause("country_name");

		return selectAsDataSet(queryAdvisor, "query.SysCountryCurrency.getCountryNameDataSetForAutoCompletion");
	}

	public DataSet getCountryCurrencyDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String dateFormat = ConfigUtil.getProperty("format.date.java");
		String countryName = requestDataSet.getValue("countryName");
		String currencyCode = requestDataSet.getValue("currencyCode");

		queryAdvisor.addVariable("dateFormat", dateFormat);
		queryAdvisor.addAutoFillCriteria(countryName, "lower(country_name) like lower('"+countryName+"%')");
		queryAdvisor.addAutoFillCriteria(currencyCode, "lower(currency_alphabetic_code) like lower('"+currencyCode+"%')");
		queryAdvisor.addOrderByClause("currency_name");

		return selectAsDataSet(queryAdvisor, "query.SysCountryCurrency.getCountryCurrencyDataSetBySearchCriteria");
	}

	public SysCountryCurrency getCountryCurrencyByCountryCurrencyId(String countryCurrencyId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("country_currency_id = '"+countryCurrencyId+"'");
		return (SysCountryCurrency)selectAllToDto(queryAdvisor, new SysCountryCurrency());
	}
}