package zebra.example.conf.resource.ormapper.dao.ZebraCommonCode;

import zebra.base.Dto;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public interface ZebraCommonCodeDao extends IDao {
	/**
	 * Insert new CommonCode record
	 * @param dto
	 * @return int
	 * @throws Exception
	 */
	public int insert(Dto dto) throws Exception;
	/**
	 * Delete CommonCode record by CodeType array
	 * @param codeTypes
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] codeTypes) throws Exception;
	/**
	 * Delete CommonCode record by CodeType
	 * @param codeType
	 * @return
	 * @throws Exception
	 */
	public int delete(String codeType) throws Exception;
	/**
	 * Get all active common code
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllActiveCommonCode() throws Exception;
	/**
	 * Get all active code type
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getActiveCodeTypeDataSet() throws Exception;
	/**
	 * Get all active common code list excluding code_type
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getActiveCommonCodeDataSet(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get common code list by CommonCodeType
	 * @param codeType
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getCommonCodeDataSetByCodeType(String codeType) throws Exception;
	/**
	 * Get all active code type
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getActiveCodeTypeDataSetLikeCodeType(String codeType) throws Exception;
}