package project.app.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import project.common.extend.BaseBiz;
import project.common.module.commoncode.CommonCodeManager;
import project.conf.resource.ormapper.dao.SysUser.SysUserDao;
import project.conf.resource.ormapper.dto.oracle.SysUser;
import zebra.config.MemoryBean;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.FileUtil;

public class LoginBizImpl extends BaseBiz implements LoginBiz {
	@Autowired
	private SysUserDao sysUserDao;
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

	public ParamEntity exeResetPassword(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet userDataSet = new DataSet();
		SysUser sysUser = new SysUser();
		String randomString = CommonUtil.getRandomAlphanumeric(12);
		String loginId = requestDataSet.getValue("loginId");
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

	public ParamEntity exeLogin(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		SysUser sysUser = new SysUser();
		String loginId = requestDataSet.getValue("loginId");
		String password = requestDataSet.getValue("password");

		try {
			// Check with LoginID
			sysUser = sysUserDao.getUserByLoginId(loginId);
			if (sysUser == null || CommonUtil.isBlank(sysUser.getUserId())) {
				throw new FrameworkException("E907", getMessage("E907", paramEntity));
			}

			// Check with LoginID and Password
			sysUser = sysUserDao.getUserByLoginIdAndPassword(loginId, password);
			if (sysUser == null || CommonUtil.isBlank(sysUser.getUserId())) {
				throw new FrameworkException("E908", getMessage("E908", paramEntity));
			}

			paramEntity.setObject("sysUser", sysUser);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I903", getMessage("I903", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getUserProfile(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		SysUser sysUser = new SysUser();
		String userId = requestDataSet.getValue("userId");
		String maxRowPerPage[], pageNumPerPage[];

		try {
			sysUser = sysUserDao.getUserByUserId(userId);

			maxRowPerPage = CommonUtil.split(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"));
			pageNumPerPage = CommonUtil.split(ConfigUtil.getProperty("view.data.pageNumsPerPage"), ConfigUtil.getProperty("delimiter.data"));

			paramEntity.setObject("sysUser", sysUser);
			paramEntity.setObject("maxRowPerPage", maxRowPerPage);
			paramEntity.setObject("pageNumPerPage", pageNumPerPage);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet dsFile = paramEntity.getRequestFileDataSet();
		String userId = requestDataSet.getValue("userId");
		String rootPath = (String)MemoryBean.get("applicationRealPath");
		String pathToSave = ConfigUtil.getProperty("path.image.photo");
		SysUser sysUser = new SysUser();
		HttpSession session = paramEntity.getSession();
		int result = -1;

		try {
			sysUser = sysUserDao.getUserByUserId(userId);

			sysUser.setUserName(requestDataSet.getValue("userName"));
			sysUser.setLoginId(requestDataSet.getValue("loginId"));
			sysUser.setLoginPassword(requestDataSet.getValue("loginPassword"));
			sysUser.setLanguage(requestDataSet.getValue("language"));
			sysUser.setThemeType(requestDataSet.getValue("themeType"));
			sysUser.setMaxRowPerPage(CommonUtil.toDouble(requestDataSet.getValue("maxRowsPerPage")));
			sysUser.setPageNumPerPage(CommonUtil.toDouble(requestDataSet.getValue("pageNumsPerPage")));
			sysUser.setEmail(requestDataSet.getValue("email"));
			sysUser.setUpdateUserId((String)session.getAttribute("UserId"));
			sysUser.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));

			if (dsFile.getRowCnt() > 0) {
				String fileName = dsFile.getValue("NEW_NAME"), fullPath = "";

				fileName = userId+"_"+fileName;
				fullPath = rootPath+pathToSave+"/"+fileName;
				FileUtil.moveFile(dsFile, fullPath);

				sysUser.setPhotoPath(pathToSave+"/"+fileName);
			}

			sysUser.addUpdateColumnFromField();
			result = sysUserDao.update(userId, sysUser);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}