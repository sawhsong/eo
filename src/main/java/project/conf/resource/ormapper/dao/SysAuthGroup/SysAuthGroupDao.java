package project.conf.resource.ormapper.dao.SysAuthGroup;

import project.conf.resource.ormapper.dto.oracle.SysAuthGroup;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public interface SysAuthGroupDao extends IDao {
	/**
	 * Create a new SysAuthGroup record
	 * @param sysAuthGroup
	 * @return int
	 * @throws Exception
	 */
	public int insert(SysAuthGroup sysAuthGroup) throws Exception;
	/**
	 * Update SysAuthGroup by Id
	 * @param groupId
	 * @param sysAuthGroup
	 * @return int
	 * @throws Exception
	 */
	public int update(String groupId, SysAuthGroup sysAuthGroup) throws Exception;
	/**
	 * Delete SysAuthGroup record by Id array
	 * @param groupIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] groupIds) throws Exception;
	/**
	 * Delete SysAuthGroup record by Id
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public int delete(String groupId) throws Exception;
	/**
	 * Get AuthGroup DataSet by Id - used by DataHelper
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAuthGroupDataSetByAuthGroupId(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get AuthGroup DataSet by search crigeria
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAuthGroupDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get SysAuthGroup by Id
	 * @param groupId
	 * @return SysAuthGroup
	 * @throws Exception
	 */
	public SysAuthGroup getAuthGroupByGroupId(String groupId) throws Exception;
	/**
	 * Get all AuthGroup DataSet with no condition
	 * @param queryAdvisor
	 * @return
	 * @throws Exception
	 */
	public DataSet getAllAuthGroupDataSet(QueryAdvisor queryAdvisor) throws Exception;
}