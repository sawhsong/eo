package project.conf.resource.ormapper.dao.SysMenuAuthLink;

import project.conf.resource.ormapper.dto.oracle.SysMenuAuthLink;
import zebra.base.IDao;
import zebra.data.DataSet;

public interface SysMenuAuthLinkDao extends IDao {
	/**
	 * Create a new record
	 * @param sysMenuAuthLink
	 * @return
	 * @throws Exception
	 */
	public int insert(SysMenuAuthLink sysMenuAuthLink) throws Exception;
	/**
	 * Delete SysMenuAuthLink record by authGroupIds array
	 * @param groupIds
	 * @return int
	 * @throws Exception
	 */
	public int deleteByAuthGroupIds(String[] authGroupIds) throws Exception;
	/**
	 * Delete SysMenuAuthLink record by authGroupId
	 * @param groupIds
	 * @return int
	 * @throws Exception
	 */
	public int deleteByAuthGroupId(String authGroupId) throws Exception;
	/**
	 * Used by MenuManager
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllMenuAuthLink() throws Exception;
}