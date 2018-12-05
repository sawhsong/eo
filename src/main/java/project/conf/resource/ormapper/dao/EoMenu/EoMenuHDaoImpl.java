/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - EO_MENU - Menu Info
 *************************************************************************************************/
package project.conf.resource.ormapper.dao.EoMenu;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.EoMenu;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public class EoMenuHDaoImpl extends BaseHDao implements EoMenuDao {
	public DataSet getAllActiveMenu() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		return selectAsDataSet(queryAdvisor, "query.EoMenu.getAllActiveMenu");
	}

	public EoMenu getMenuByMenuId(String menuId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("menu_id = '"+menuId+"'");
		return (EoMenu)selectAllToDto(queryAdvisor, new EoMenu());
	}
}