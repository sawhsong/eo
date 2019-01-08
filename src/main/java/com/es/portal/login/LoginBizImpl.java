package com.es.portal.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.userprofile.UserProfileBizService;
import com.es.portal.conf.resource.ormapper.dao.HpPersonD.HpPersonDDao;
import com.es.portal.conf.resource.ormapper.dao.SysUsers.SysUsersDao;
import com.es.portal.conf.resource.ormapper.dto.oracle.HpPersonD;
import com.es.portal.conf.resource.ormapper.dto.oracle.SysUsers;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.JsonUtil;

public class LoginBizImpl extends BaseBiz implements LoginBiz {
	@Autowired
	private SysUsersDao sysUsersDao;
	@Autowired
	private HpPersonDDao hpPersonDDao;
	@Autowired
	private UserProfileBizService userProfileBizService;
	@Autowired
	private LoginMessageSender loginMessageSender;

	public ParamEntity index(ParamEntity paramEntity) throws Exception {
		HttpServletRequest request = paramEntity.getRequest();
		HttpSession session = paramEntity.getSession();
		String language = request.getLocale().getLanguage();

		try {
			session.setAttribute("langCode", language);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
/*
	public ParamEntity exeSendUserId(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet userDataSet = new DataSet();
		SysUsers sysUsers = new SysUsers();
		String email = requestDataSet.getValue("email");
		int result = -1;

		try {
			// Check if the user exists
			userDataSet = sysUserDao.getUserInfoDataSetByLoginId(loginId);
			if (userDataSet.getRowCnt() <= 0) {
				throw new FrameworkException("E907", getMessage("E907", paramEntity));
			}

			// Initailise the password
			sysUser.addUpdateColumn("login_password", randomString);
			result = sysUserDao.initialisePassword(paramEntity, sysUser);
			if (result <= 0) {
				throw new FrameworkException("E904", getMessage("E904", paramEntity));
			}

			// Select SysUser
			sysUser = sysUserDao.getUserByLoginId(loginId);

			loginMessageSender.sendResetPasswordMessage(sysUser);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeRequestRegister(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		SysUser sysUser = new SysUser();
		String uid = CommonUtil.uid();
		String loginId = requestDataSet.getValue("loginId");
		String password = requestDataSet.getValue("password");
		String email = requestDataSet.getValue("email");
		String isSendEmail = CommonUtil.nvl(requestDataSet.getValue("sendEmail"), "N");
		String photoPathName = ConfigUtil.getProperty("path.image.photo")+"/"+"DefaultUser_128_Black.png";
		int result = -1;

		try {
			// Check if the user already exists with the login id and password
			sysUser = sysUserDao.getUserByLoginIdAndPassword(loginId, password);
			if (!(sysUser == null || CommonUtil.isEmpty(sysUser.getUserId()))) {
				throw new FrameworkException("E912", getMessage("E912", paramEntity));
			}

			// Sets SysUser object and save it
			sysUser = new SysUser();
			sysUser.setUserId(uid);
			sysUser.setUserName(requestDataSet.getValue("userName"));
			sysUser.setLoginId(requestDataSet.getValue("loginId"));
			sysUser.setLoginPassword(requestDataSet.getValue("password"));
			sysUser.setAuthGroupId("Z"); //SysAuthGroup.GroupId(Not Selected)
			sysUser.setLanguage(ConfigUtil.getProperty("etc.default.language"));
			sysUser.setThemeType(ConfigUtil.getProperty("view.theme.default"));
			sysUser.setMaxRowPerPage(CommonUtil.toDouble(CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"))[2]));
			sysUser.setPageNumPerPage(CommonUtil.toDouble(CommonUtil.split(ConfigUtil.getProperty("view.data.pageNumsPerPage"), ConfigUtil.getProperty("delimiter.data"))[0]));
			sysUser.setUserType(CommonCodeManager.getCodeByConstants("USER_TYPE_INTERNAL"));
			sysUser.setEmail(email);
			sysUser.setUserStatus(CommonCodeManager.getCodeByConstants("USER_STATUS_RR"));
			sysUser.setPhotoPath(photoPathName);
			sysUser.setIsActive(CommonCodeManager.getCodeByConstants("IS_ACTIVE_N"));
			sysUser.setInsertUserId("0");

			result = sysUserDao.insert(sysUser);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			// Sends email to the user
			if (CommonUtil.equals(isSendEmail, "Y")) {
				loginMessageSender.sendRequestRegisterMessage(sysUser, email);
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
*/
	public ParamEntity exeLogin(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String loginId = requestDataSet.getValue("loginId");
		String password = requestDataSet.getValue("password");
		SysUsers sysUsers = new SysUsers();
		HpPersonD hpPersonD = new HpPersonD();
		DataSet resultDataset = new DataSet();

		try {
			// Check with LoginID - loginId = userName
			sysUsers = sysUsersDao.getUserByLoginId(loginId);
			if (sysUsers == null || CommonUtil.isBlank(sysUsers.getUserName())) {
				paramEntity.setSuccess(false);
				paramEntity.setMessage("E907", getMessage("E907", paramEntity));
				return paramEntity;
			}

			// Check with LoginID and Password
			sysUsers = sysUsersDao.getUserByLoginIdAndPassword(loginId, password);
			if (sysUsers == null || CommonUtil.isBlank(sysUsers.getUserName())) {
				paramEntity.setSuccess(false);
				paramEntity.setMessage("E908", getMessage("E908", paramEntity));
				return paramEntity;
			}

			hpPersonD = hpPersonDDao.getPersonByPersonId(CommonUtil.toString(sysUsers.getPersonId(), "#"));
			resultDataset.addName(new String[] {"LoginId", "UserName", "StartupUrl"});
			resultDataset.addRow();
			resultDataset.setValue("LoginId", sysUsers.getUserName());
			resultDataset.setValue("UserName", hpPersonD.getFirstName());
			resultDataset.setValue("StartupUrl", sysUsers.getStartupUrl());

			paramEntity.setObject("sysUsers", sysUsers);
			paramEntity.setObject("hpPersonD", hpPersonD);
			paramEntity.setObject("resultDataset", resultDataset);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I903", getMessage("I903", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getUserProfile(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet userDetails = new DataSet();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String header[] = new String[] {"personId", "prefixCode", "prefix", "surName", "firstName", "middleName", "preferredName", "dateOfBirth", "email",
				"stateCode", "street", "suburb", "state", "postCode", "mobile", "landLine", "", "country",
				"emergencyContactEmail", "emergencyContactName", "emergencyContactPhone", "emergencyContactRelationship"};

		try {
			userDetails.addName(header);

			userProfileBizService.getPersonProfileService(paramEntity, personId);
			paramEntity.setDataSetValueFromJsonResultset(userDetails);

			paramEntity.setObject("prefixLookupList", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("prefixLookupList")));
			paramEntity.setObject("stateLookupList", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("stateLookupList")));
			paramEntity.setObject("userDetails", userDetails);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeUpdateUserProfile(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String result = "";

		try {
			result = userProfileBizService.postUserProfile(personId, dsRequest);

			if (!CommonUtil.startsWith(result, "2")) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity setSessionValuesForAdminTool(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		SysUsers sysUsers = new SysUsers();
		HpPersonD hpPersonD = new HpPersonD();
		String loginId = requestDataSet.getValue("loginId");
		DataSet resultDataSet = new DataSet();

		try {
			sysUsers = sysUsersDao.getUserByLoginId(loginId);
			hpPersonD = hpPersonDDao.getPersonByPersonId(CommonUtil.toString(sysUsers.getPersonId(), "#"));

			paramEntity.setObject("sysUsersForAdminTool", sysUsers);
			paramEntity.setObject("hpPersonDForAdminTool", hpPersonD);

			resultDataSet.addName(new String[] {"user_id", "login_id", "user_full_name", "emp_org_id"});
			resultDataSet.addRow();
			resultDataSet.setValue("user_id", sysUsers.getUserId());
			resultDataSet.setValue("login_id", sysUsers.getUserName()); // LoginId = UserName
			resultDataSet.setValue("user_full_name", hpPersonD.getFullName());
			resultDataSet.setValue("emp_org_id", CommonUtil.toString(hpPersonD.getEmploymentCompanyOrgId(), "#"));

			paramEntity.setAjaxResponseDataSet(resultDataSet);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}