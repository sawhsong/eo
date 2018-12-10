package com.es.portal.common.module.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.conf.resource.ormapper.dao.EoMenu.EoMenuDao;
import com.es.portal.conf.resource.ormapper.dao.EoMenuAuthLink.EoMenuAuthLinkDao;
import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class MenuManager extends BaseBiz {
	private static Logger logger = LogManager.getLogger(MenuManager.class);
	private static EoMenuDao eoMenuDao;
	private static EoMenuAuthLinkDao eoMenuAuthLinkDao;

	public static EoMenuDao getEoMenuDao() {
		return eoMenuDao;
	}

	public static void setEoMenuDao(EoMenuDao eoMenuDao) {
		MenuManager.eoMenuDao = eoMenuDao;
	}

	public static EoMenuAuthLinkDao getEoMenuAuthLinkDao() {
		return eoMenuAuthLinkDao;
	}

	public static void setEoMenuAuthLinkDao(EoMenuAuthLinkDao eoMenuAuthLinkDao) {
		MenuManager.eoMenuAuthLinkDao = eoMenuAuthLinkDao;
	}

	public static void loadMenu() throws Exception {
		MemoryBean.set("menuDataSet", MenuManager.getMenuDataSet());
		logger.info("[MemoryBean] - Project Menu has been loaded.");
	}

	public static void reload() throws Exception {
		loadMenu();
	}

	public static DataSet getMenuDataSet() throws Exception {
		DataSet dsMenuAll = eoMenuDao.getAllActiveMenu();
		DataSet dsAuthGroupLink = eoMenuAuthLinkDao.getAllMenuAuthLink();
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
}