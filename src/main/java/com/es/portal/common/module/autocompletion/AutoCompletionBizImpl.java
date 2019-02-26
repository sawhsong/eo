package com.es.portal.common.module.autocompletion;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;
import com.es.portal.conf.resource.ormapper.dao.SysUsers.SysUsersDao;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class AutoCompletionBizImpl extends BaseBiz implements AutoCompletionBiz {
	@Autowired
	private SysUsersDao sysUsersDao;
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity getSysUsersByLoginId(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String inputValue = requestDataSet.getValue("inputValue");

		try {
			queryAdvisor.addAutoFillCriteria(inputValue, "lower(user_name) like lower('"+inputValue+"%')");
			queryAdvisor.addOrderByClause("user_name");
			paramEntity.setAjaxResponseDataSet(sysUsersDao.getSysUsersDataSetByLoginIdForAutoCompletion(queryAdvisor));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getSysUsersByPersonId(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String inputValue = requestDataSet.getValue("inputValue");

		try {
			queryAdvisor.addAutoFillCriteria(inputValue, "lower(person_id) like lower('"+inputValue+"%')");
			queryAdvisor.addOrderByClause("person_id");
			paramEntity.setAjaxResponseDataSet(sysUsersDao.getSysUsersDataSetByPersonIdForAutoCompletion(queryAdvisor));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getPersonByName(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet personList = new DataSet();
		String loggedInUserEmpOrgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));
		
		String fullName = requestDataSet.getValue("inputValue");

		try {
			personList = wsClient.getPersonByName(loggedInUserEmpOrgId, fullName);

			paramEntity.setAjaxResponseDataSet(personList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getPersonByNumber(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		DataSet personList = new DataSet();
		String personNumber = requestDataSet.getValue("inputValue");
		String loggedInUserEmpOrgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));

		try {
			personList = wsClient.getPersonByNumber(loggedInUserEmpOrgId, personNumber);

			paramEntity.setAjaxResponseDataSet(personList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}