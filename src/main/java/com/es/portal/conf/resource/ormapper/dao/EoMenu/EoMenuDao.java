/**************************************************************************************************
 * Framework Generated DAO Source
 * - EO_MENU - Menu Info
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dao.EoMenu;

import com.es.portal.conf.resource.ormapper.dto.oracle.EoMenu;
import zebra.base.IDao;
import zebra.data.DataSet;

public interface EoMenuDao extends IDao {
	/**
	 * Being used by MenuManager
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllActiveMenu() throws Exception;
	/**
	 * Get SysMenu by Id
	 * @param menuId
	 * @return SysMenu
	 * @throws Exception
	 */
	public EoMenu getMenuByMenuId(String menuId) throws Exception;
}