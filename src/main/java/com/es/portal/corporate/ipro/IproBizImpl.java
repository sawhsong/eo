package com.es.portal.corporate.ipro;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.export.ExportHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.ExportUtil;

public class IproBizImpl extends BaseBiz implements IproBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity myIpros(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getIproList(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet iproList = new DataSet(), myOrgInfo = new DataSet(new String[] {"organisationId", "organisationName"});
		String orgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));

		try {
			iproList = wsClient.getIproListDataSet(paramEntity, orgId);

			paramEntity.setDataSetValueFromJsonResultset(myOrgInfo);
			session.setAttribute("ClientOrgId", myOrgInfo.getValue("organisationId"));
			session.setAttribute("ClientOrgName", myOrgInfo.getValue("organisationName"));

			paramEntity.setAjaxResponseDataSet(iproList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity newIpro(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet iproList = new DataSet();
		String orgId = CommonUtil.nvl((String)session.getAttribute("EmpOrgIdForAdminTool"), (String)session.getAttribute("EmploymentOrgId"));
		ExportHelper exportHelper;
		String columnHeader[], fileHeader[];
		String pageTitle, fileName;

		try {
			pageTitle = "MyIPros";
			fileName = "MyIPros-"+CommonUtil.getSysdate(ConfigUtil.getProperty("format.date.java"));
			columnHeader = new String[]{"personFullName", "jobTitle", "email", "lastInvoiceDate", "lastPayDate", "startDate", "endDate", "endUserOrganisationName", "workingState"};
			fileHeader = new String[]{"IPro Name", "Position Title", "Email Address", "Last Invoice Date", "Last Pay Date", "Assignment Start Date", "Assignment End Date", "End User Organisation", "Working State"};

			iproList = wsClient.getIproListDataSet(paramEntity, orgId);

			exportHelper = ExportUtil.getExportHelper("Excel");
			exportHelper.setPageTitle(pageTitle);
			exportHelper.setFileName(fileName);
			exportHelper.setColumnHeader(columnHeader);
			exportHelper.setFileHeader(fileHeader);
			exportHelper.setSourceDataSet(iproList);

			paramEntity.setSuccess(true);
			paramEntity.setFileToExport(exportHelper.createFile());
			paramEntity.setFileNameToExport(exportHelper.getFileName());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}