package com.es.portal.employee.leave;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class LeaveBizImpl extends BaseBiz implements LeaveBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity myLeaves(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getLeaveList(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet iproList = new DataSet();
		String orgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));

		try {
			iproList = wsClient.getIproListDataSet(paramEntity, orgId);

			paramEntity.setAjaxResponseDataSet(iproList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity newLeave(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}