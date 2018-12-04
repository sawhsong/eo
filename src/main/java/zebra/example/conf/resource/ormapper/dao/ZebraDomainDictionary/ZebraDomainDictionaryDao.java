package zebra.example.conf.resource.ormapper.dao.ZebraDomainDictionary;

import zebra.base.Dto;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraDomainDictionary;

public interface ZebraDomainDictionaryDao extends IDao {
	/**
	 * Insert new DomainDictionary record
	 * @param dto
	 * @return int
	 * @throws Exception
	 */
	public int insert(Dto dto) throws Exception;
	/**
	 * Update DomainDictionary record by domain_id
	 * @param domainId
	 * @param dto
	 * @return int
	 * @throws Exception
	 */
	public int updateByDomainId(String domainId, Dto dto) throws Exception;
	/**
	 * Delete DomainDictionary by DomainId array
	 * @param domainIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] domainIds) throws Exception;
	/**
	 * Delete DomainDictionary by DomainId array
	 * @param domainId
	 * @return int
	 * @throws Exception
	 */
	public int delete(String domainId) throws Exception;
	/**
	 * Get all DomainDictionary records by the condition defined in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getDomainDictionaryDataSet(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get DomainDictionary name column by the condition defined in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getNameDataSetByName(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get ZebraDomainDictionary object by Id
	 * @param domainDictionaryId
	 * @return ZebraDomainDictionary
	 * @throws Exception
	 */
	public ZebraDomainDictionary getDomainDictionaryById(String domainDictionaryId) throws Exception;
}