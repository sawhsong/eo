/**************************************************************************************************
 * Framework Generated DAO Source
 * - SYS_USERS - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dao.SysUsers;

import com.es.portal.conf.resource.ormapper.dto.oracle.SysUsers;
import zebra.base.IDao;

public interface SysUsersDao extends IDao {
	/**
	 * Get SysUsers object with a given LoginId
	 * @param loginId
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUsers getUserByLoginId(String loginId) throws Exception;
	/**
	 * Get SysUsers object with given LoginId and Password - Normal login process
	 * @param loginId
	 * @param password
	 * @return SysUser
	 * @throws Exception
	 */
	public SysUsers getUserByLoginIdAndPassword(String loginId, String password) throws Exception;
}