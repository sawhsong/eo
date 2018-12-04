/**************************************************************************************************
 * Framework Generated DAO Source
 * - EO_MENU_AUTH_LINK - Menu - Authority group mapping
 *************************************************************************************************/
package project.conf.resource.ormapper.dao.EoMenuAuthLink;

import zebra.base.IDao;
import zebra.data.DataSet;

public interface EoMenuAuthLinkDao extends IDao {
	/**
	 * Used by MenuManager
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllMenuAuthLink() throws Exception;
}