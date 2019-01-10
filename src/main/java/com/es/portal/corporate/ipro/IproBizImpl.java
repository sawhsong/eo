package com.es.portal.corporate.ipro;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class IproBizImpl extends BaseBiz implements IproBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity myIpros(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet iproList = new DataSet(), orgInfo = new DataSet(new String[] {"organisationId", "organisationName"});
		String orgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));

		try {
			iproList = wsClient.getIproListDataSet(paramEntity, orgId);
			paramEntity.setDataSetValueFromJsonResultset(orgInfo);

			paramEntity.setObject("iproList", iproList);
			paramEntity.setObject("orgInfo", orgInfo);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}