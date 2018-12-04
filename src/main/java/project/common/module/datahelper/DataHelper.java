package project.common.module.datahelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.common.extend.BaseBiz;
import project.conf.resource.ormapper.dao.SysAuthGroup.SysAuthGroupDao;
import project.conf.resource.ormapper.dao.SysUser.SysUserDao;
import project.conf.resource.ormapper.dto.oracle.SysAuthGroup;
import project.conf.resource.ormapper.dto.oracle.SysUser;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;

public class DataHelper extends BaseBiz {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(DataHelper.class);
	private static SysUserDao sysUserDao;
	private static SysAuthGroupDao sysAuthGroupDao;

	public static SysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public static void setSysUserDao(SysUserDao sysUserDao) {
		DataHelper.sysUserDao = sysUserDao;
	}

	public static SysAuthGroupDao getSysAuthGroupDao() {
		return sysAuthGroupDao;
	}

	public static void setSysAuthGroupDao(SysAuthGroupDao sysAuthGroupDao) {
		DataHelper.sysAuthGroupDao = sysAuthGroupDao;
	}

	/*!
	 * SysUser
	 */
	public static SysUser getUserByUserId(String userId) throws Exception {
		if (CommonUtil.isBlank(userId)) {return new SysUser();}
		return sysUserDao.getUserByUserId(userId);
	}

	public static String getUserNameById(String userId) throws Exception {
		if (CommonUtil.isBlank(userId)) {return "";}
		return getUserByUserId(userId).getUserName();
	}

	/*!
	 * SysAuthGroup
	 */
	public static DataSet getAuthGroupDataSetById(String authGroupId) throws Exception {
		if (CommonUtil.isBlank(authGroupId)) {return new DataSet();}

		QueryAdvisor qa = new QueryAdvisor();
		qa.addVariable("authGroupId", authGroupId);
		return sysAuthGroupDao.getAuthGroupDataSetByAuthGroupId(qa);
	}

	public static SysAuthGroup getSysAuthGroupById(String authGroupId) throws Exception {
		SysAuthGroup sysAuthGroup = new SysAuthGroup();

		if (CommonUtil.isBlank(authGroupId)) {return sysAuthGroup;}

		sysAuthGroup.setValues(getAuthGroupDataSetById(authGroupId));
		return sysAuthGroup;
	}

	public static String getAuthGroupNameById(String authGroupId) throws Exception {
		if (CommonUtil.isBlank(authGroupId)) {return "";}

		DataSet ds = getAuthGroupDataSetById(authGroupId);
		return CommonUtil.nvl(ds.getValue("GROUP_NAME"));
	}
}