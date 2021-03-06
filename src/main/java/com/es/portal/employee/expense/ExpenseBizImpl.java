package com.es.portal.employee.expense;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import net.sf.json.JSONArray;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.JsonUtil;

public class ExpenseBizImpl extends BaseBiz implements ExpenseBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getExpenseClaimList(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet expenseList = new DataSet();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));

		try {
			expenseList = wsClient.getExpenseClaimListDataSet(paramEntity, personId);

			paramEntity.setAjaxResponseDataSet(expenseList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet expenseClaimDetail = new DataSet();
		String expenseClaimId = dsRequest.getValue("expenseClaimId");
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String personName = CommonUtil.nvl((String)session.getAttribute("UserFullNameForAdminTool"), (String)session.getAttribute("UserFullName"));
		String header[] = new String[] {"expenseClaimId", "personId", "personFullName", "department", "departmentDesc", "expenseType", "expenseTypeDesc", "dateOfClaim",
				"bsb", "accountName", "accountNumber", "amount", "gst", "description", "submittedDate", "processedDate", "status", "statusDesc",
				"approveRejectPersonId", "approveRejectPersonName", "approveRejectDate", "approveRejectComments", "attachmentCount"};

		try {
			expenseClaimDetail.addName(header);

			wsClient.getExpenseClaimDetailService(paramEntity, expenseClaimId);
			paramEntity.setDataSetValueFromJsonResultset(expenseClaimDetail);

			paramEntity.setObject("personId", personId);
			paramEntity.setObject("personName", personName);
			paramEntity.setObject("expenseClaimDetail", expenseClaimDetail);
			paramEntity.setObject("attachmentList", JsonUtil.getDataSetFromJsonArray((JSONArray)paramEntity.getObject("attachmentList")));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity saveExpenseClaim(ParamEntity paramEntity) throws Exception {
		HttpSession session = paramEntity.getSession();
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet dsFile = paramEntity.getRequestFileDataSet();
		String personId = CommonUtil.nvl((String)session.getAttribute("PersonIdForAdminTool"), (String)session.getAttribute("PersonId"));
		String result = "";

		try {
			dsRequest.addColumn("personId", personId);
			result = wsClient.postExpenseClaim(dsRequest, dsFile);

			if (!CommonUtil.startsWith(result, "2")) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getAttachedFile(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet attachmentList;
		String expenseClaimId = "";

		try {
			expenseClaimId = dsRequest.getValue("expenseClaimId");
			attachmentList = wsClient.getAttachedFile(paramEntity, expenseClaimId);

			if (attachmentList != null && attachmentList.getRowCnt() > 0) {
				for (int i=0; i<attachmentList.getRowCnt(); i++) {
					attachmentList.setValue(i, "fileIcon", CommonUtil.getIconNameByFileType(attachmentList.getValue(i, "fileIcon")));
				}
			}

			paramEntity.setAjaxResponseDataSet(attachmentList);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}