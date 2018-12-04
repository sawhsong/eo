package project.conf.resource.ormapper.dao.SysMenu;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysMenu;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public class SysMenuHDaoImpl extends BaseHDao implements SysMenuDao {
	public int insert(SysMenu sysMenu) throws Exception {
		return insertWithSQLQuery(sysMenu);
	}

	public int update(String sysMenuId, SysMenu sysMenu) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("menu_id = '"+sysMenuId+"'");
		return updateWithSQLQuery(queryAdvisor, sysMenu);
	}

	public int updateSortOrder(String sysMenuId, SysMenu sysMenu) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("menu_id = '"+sysMenuId+"'");
		return updateColumns(queryAdvisor, sysMenu);
	}

	public int delete(String sysMenuIds[]) throws Exception {
		int result = 0;

		if (!(sysMenuIds == null || sysMenuIds.length == 0)) {
			for (int i=0; i<sysMenuIds.length; i++) {
				result += delete(sysMenuIds[i]);
			}
		}
		return result;
	}

	public int delete(String sysMenuId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("menu_id in (select menu_id from sys_menu connect by prior menu_id = parent_menu_id start with menu_id = '"+sysMenuId+"')");
		return deleteWithSQLQuery(queryAdvisor, new SysMenu());
	}

	public DataSet getAllActiveMenu() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		return selectAsDataSet(queryAdvisor, "query.SysMenu.getAllActiveMenu");
	}

	public DataSet getAllActiveQuickMenu() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		return selectAsDataSet(queryAdvisor, "query.SysMenu.getAllActiveQuickMenu");
	}

	public DataSet getAllActiveMenuDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception {
		return selectAsDataSet(queryAdvisor, "query.SysMenu.getAllActiveMenuDataSetBySearchCriteria");
	}

	public DataSet getMenuDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String searchMenu = requestDataSet.getValue("searchMenu");

		queryAdvisor.addAutoFillCriteria(searchMenu, "root = '"+searchMenu+"'");
		return selectAsDataSet(queryAdvisor, "query.SysMenu.getMenuDataSetBySearchCriteria");
	}

	public SysMenu getMenuByMenuId(String menuId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("menu_id = '"+menuId+"'");
		return (SysMenu)selectAllToDto(queryAdvisor, new SysMenu());
	}
}