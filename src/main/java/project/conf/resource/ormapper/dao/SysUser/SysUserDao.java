package project.conf.resource.ormapper.dao.SysUser;

import project.conf.resource.ormapper.dto.oracle.SysUser;
import zebra.base.Dto;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;

public interface SysUserDao extends IDao {
	/**
	 * Insert new user record
	 * @param dto(SysUser)
	 * @return int
	 * @throws Exception
	 */
	public int insert(Dto dto) throws Exception;
	/**
	 * Update SysUser
	 * @param userId
	 * @param dto(SysUser)
	 * @return
	 * @throws Exception
	 */
	public int update(String userId, Dto dto) throws Exception;
	/**
	 * Update AuthGroupId column only by AuthGroupIds array to given code
	 * @param authGroupIds
	 * @param toCode
	 * @return int
	 * @throws Exception
	 */
	public int updateAuthGroupIdByAuthGroupIds(String authGroupIds[], String toCode) throws Exception;
	/**
	 * Update AuthGroupId column only by AuthGroupIds array to given code
	 * @param authGroupIds
	 * @param toCode
	 * @return int
	 * @throws Exception
	 */
	public int updateAuthGroupIdByAuthGroupId(String authGroupId, String toCode) throws Exception;
	/**
	 * Update SysUser by UserIds array by a given SysUser object
	 * @param userIds
	 * @return
	 * @throws Exception
	 */
	public int updateByUserIds(String userIds[], SysUser sysUser) throws Exception;
	/**
	 * Delete SysUser record by Id array
	 * @param userIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] userIds) throws Exception;
	/**
	 * Delete SysUser record by Id
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public int delete(String userId) throws Exception;
	/**
	 * Get SysUser list DataSet with a given LoginId only
	 * @param paramEntity
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getUserInfoDataSetByLoginId(String loginId) throws Exception;
	/**
	 * Get SysUser list DataSet by Search Criteria in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getUserDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get User Name Column only by the condition in QueryAdvisor - for AutoCompletion
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getUserNameDataSetForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Initialise passsword for the user
	 * @param paramEntity
	 * @param dto(SysUser)
	 * @return int
	 * @throws Exception
	 */
	public int initialisePassword(ParamEntity paramEntity, Dto dto) throws Exception;
	/**
	 * Get SysUser object with a given LoginId
	 * @param loginId
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser getUserByLoginId(String loginId) throws Exception;
	/**
	 * Get SysUser object with a given UserId
	 * @param userId
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser getUserByUserId(String userId) throws Exception;
	/**
	 * Get SysUser object with given LoginId and Password - Normal login process
	 * @param loginId
	 * @param password
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUser getUserByLoginIdAndPassword(String loginId, String password) throws Exception;
}