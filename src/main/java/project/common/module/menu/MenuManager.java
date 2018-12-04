package project.common.module.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.common.extend.BaseBiz;
import project.conf.resource.ormapper.dao.SysMenu.SysMenuDao;
import project.conf.resource.ormapper.dao.SysMenuAuthLink.SysMenuAuthLinkDao;
import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class MenuManager extends BaseBiz {
	private static Logger logger = LogManager.getLogger(MenuManager.class);
	private static SysMenuDao sysMenuDao;
	private static SysMenuAuthLinkDao sysMenuAuthLinkDao;

	public static SysMenuDao getSysMenuDao() {
		return sysMenuDao;
	}

	public static void setSysMenuDao(SysMenuDao sysMenuDao) {
		MenuManager.sysMenuDao = sysMenuDao;
	}

	public static SysMenuAuthLinkDao getSysMenuAuthLinkDao() {
		return sysMenuAuthLinkDao;
	}

	public static void setSysMenuAuthLinkDao(SysMenuAuthLinkDao sysMenuAuthLinkDao) {
		MenuManager.sysMenuAuthLinkDao = sysMenuAuthLinkDao;
	}

	public static void loadMenu() throws Exception {
		MemoryBean.set("menuDataSet", MenuManager.getMenuDataSet());
		MemoryBean.set("quickMenuDataSet", MenuManager.getQuickMenuDataSet());
		logger.info("[MemoryBean] - Project Menu has been loaded.");
	}

	public static void reload() throws Exception {
		loadMenu();
	}

	public static DataSet getMenuDataSet() throws Exception {
		DataSet dsMenuAll = sysMenuDao.getAllActiveMenu();
		DataSet dsAuthGroupLink = sysMenuAuthLinkDao.getAllMenuAuthLink();
		String delimiter = ConfigUtil.getProperty("delimiter.data");
		String menuId = "";

		dsMenuAll.addColumn("GROUP_ID");
		for (int i=0; i<dsMenuAll.getRowCnt(); i++) {
			String groupId = "";

			menuId = dsMenuAll.getValue(i, "MENU_ID");
			for (int j=0; j<dsAuthGroupLink.getRowCnt(); j++) {
				if (CommonUtil.equals(menuId, dsAuthGroupLink.getValue(j, "MENU_ID"))) {
					groupId += (CommonUtil.isBlank(groupId)) ? dsAuthGroupLink.getValue(j, "GROUP_ID") : delimiter+dsAuthGroupLink.getValue(j, "GROUP_ID");
				}
			}
			dsMenuAll.setValue(i, "GROUP_ID", groupId);
		}

		return dsMenuAll;
	}

	public static DataSet getMenuDataSetByLevel(int level) throws Exception {
		DataSet dsMenu = (DataSet)MemoryBean.get("menuDataSet");
		DataSet dsRtn = new DataSet(dsMenu.getNames());

		for (int i=0; i<dsMenu.getRowCnt(); i++) {
			int currentLevel = CommonUtil.toInt(dsMenu.getValue(i, "LEVEL"));

			if (currentLevel == level) {
				dsRtn.addRow();
				for (int j=0; j<dsMenu.getColumnCnt(); j++) {
					dsRtn.setValue(dsRtn.getRowCnt()-1, dsRtn.getName(j), dsMenu.getValue(i, j));
				}
			}
		}

		return dsRtn;
	}

	public static DataSet getQuickMenuDataSet() throws Exception {
		return sysMenuDao.getAllActiveQuickMenu();
	}

	public static DataSet getMenu(String authGroupId, String parentMenuId, String startMenuLevel, String endMenuLevel) throws Exception {
		DataSet dsRtn = new DataSet();
		DataSet dsMenu = (DataSet)MemoryBean.get("menuDataSet");

		authGroupId = CommonUtil.nvl(authGroupId);
		startMenuLevel = CommonUtil.nvl(startMenuLevel, "1");
		endMenuLevel = CommonUtil.nvl(endMenuLevel, "100");

		int startLevel = CommonUtil.toInt(startMenuLevel);
		int endLevel = CommonUtil.toInt(endMenuLevel);

		if (CommonUtil.isBlank(authGroupId)) {
			return dsRtn;
		}

		dsRtn.addName(dsMenu.getNames());
		for (int i=0; i<dsMenu.getRowCnt(); i++) {
			int menuLevel = CommonUtil.toInt(dsMenu.getValue(i, "LEVEL"));
			String groupId = dsMenu.getValue(i, "GROUP_ID");

			if (CommonUtil.containsIgnoreCase(groupId, authGroupId) && (menuLevel >= startLevel && menuLevel <= endLevel)) {
				if (CommonUtil.isBlank(parentMenuId)) {
					dsRtn.addRow();
					for (int j=0; j<dsRtn.getColumnCnt(); j++) {
						dsRtn.setValue(dsRtn.getRowCnt()-1, j, dsMenu.getValue(i, j));
					}
				} else {
					if (CommonUtil.containsIgnoreCase(dsMenu.getValue(i, "PARENT_MENU_ID"), parentMenuId)) {
						dsRtn.addRow();
						for (int j=0; j<dsRtn.getColumnCnt(); j++) {
							dsRtn.setValue(dsRtn.getRowCnt()-1, j, dsMenu.getValue(i, j));
						}
					}
				}
			}
		}

		return dsRtn;
	}

	public static DataSet getQuickMenu() throws Exception {
		return (DataSet)MemoryBean.get("quickMenuDataSet");
	}
}