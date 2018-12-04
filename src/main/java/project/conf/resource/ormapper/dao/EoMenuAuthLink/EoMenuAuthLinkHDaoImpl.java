/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - EO_MENU_AUTH_LINK - Menu - Authority group mapping
 *************************************************************************************************/
package project.conf.resource.ormapper.dao.EoMenuAuthLink;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.EoMenuAuthLink;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public class EoMenuAuthLinkHDaoImpl extends BaseHDao implements EoMenuAuthLinkDao {
	public DataSet getAllMenuAuthLink() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		EoMenuAuthLink eoMenuAuthLink = new EoMenuAuthLink();

		queryAdvisor.addOrderByClause("menu_id");

		return selectAllAsDataSet(queryAdvisor, eoMenuAuthLink);
	}
}