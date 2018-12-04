package project.conf.resource.ormapper.dao.SysMenu;

import project.conf.resource.ormapper.dto.oracle.SysMenu;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public interface SysMenuDao extends IDao {
	/**
	 * Create new SysMenu entry
	 * @param sysMenu
	 * @return int
	 * @throws Exception
	 */
	public int insert(SysMenu sysMenu) throws Exception;
	/**
	 * Update SysMenu by Id
	 * @param sysMenuId
	 * @param sysMenu
	 * @return int
	 * @throws Exception
	 */
	public int update(String sysMenuId, SysMenu sysMenu) throws Exception;
	/**
	 * Update menu sort order
	 * @param sysMenuId
	 * @param sysMenu
	 * @return
	 * @throws Exception
	 */
	public int updateSortOrder(String sysMenuId, SysMenu sysMenu) throws Exception;
	/**
	 * Delete SysMenu record by Id array
	 * @param sysMenuIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] sysMenuIds) throws Exception;
	/**
	 * Delete SysMenu record by Id
	 * @param sysMenuId
	 * @return
	 * @throws Exception
	 */
	public int delete(String sysMenuId) throws Exception;
	/**
	 * Being used by MenuManager
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllActiveMenu() throws Exception;
	/**
	 * Being used by MenuManager
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllActiveQuickMenu() throws Exception;
	/**
	 * Get all active menu by criteria except QuickMenu - used by SourceGenerator
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getAllActiveMenuDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get all menu dataset by criteria
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getMenuDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get SysMenu by Id
	 * @param menuId
	 * @return SysMenu
	 * @throws Exception
	 */
	public SysMenu getMenuByMenuId(String menuId) throws Exception;
}