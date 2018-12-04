package project.conf.resource.ormapper.dao.SysCountryCurrency;

import project.conf.resource.ormapper.dto.oracle.SysCountryCurrency;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public interface SysCountryCurrencyDao extends IDao {
	/**
	 * Insert new SysCountryCurrency record
	 * @param sysCountryCurrency
	 * @return int
	 * @throws Exception
	 */
	public int insert(SysCountryCurrency sysCountryCurrency) throws Exception;
	/**
	 * Update new SysCountryCurrency record
	 * @param sysCountryCurrency
	 * @return int
	 * @throws Exception
	 */
	public int update(String countryCurrencyId, SysCountryCurrency sysCountryCurrency) throws Exception;
	/**
	 * Delete SysCountryCurrency record by Id array
	 * @param countryCurrencyIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] countryCurrencyIds) throws Exception;
	/**
	 * Delete SysCountryCurrency record by Id
	 * @param countryCurrencyId
	 * @return
	 * @throws Exception
	 */
	public int delete(String countryCurrencyId) throws Exception;
	/**
	 * Get CurrencyCode dataset by search criteria(AutoCompletion)
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getCurrencyCodeDataSetForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get CountryName dataset by search criteria(AutoCompletion)
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getCountryNameDataSetForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get CountryCurrency list dataset by search criteria in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getCountryCurrencyDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get SysCountryCurrency by Id
	 * @param countryCurrencyId
	 * @return SysCountryCurrency
	 * @throws Exception
	 */
	public SysCountryCurrency getCountryCurrencyByCountryCurrencyId(String countryCurrencyId) throws Exception;
}