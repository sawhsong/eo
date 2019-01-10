package com.es.portal.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class IndexBizImpl extends BaseBiz implements IndexBiz {
	@Autowired
	private WebServiceClientBizService wsClient;

	public ParamEntity index(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity dashboard(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity contactus(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity contactuspop(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity postContactUs(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet post = new DataSet();
		HttpServletRequest request = paramEntity.getRequest();
		HttpSession session = paramEntity.getSession();
		String loginId = CommonUtil.nvl((String)session.getAttribute("LoginIdForAdminTool"), (String)session.getAttribute("LoginId"));
		String portalType = CommonUtil.nvl((String)session.getAttribute("UserPortalTypeForAdminTool"), (String)session.getAttribute("UserPortalType"));
		String result = "";
		String header[] = new String[] {"firstName", "surName", "email", "phone", "userType", "location", "message", "ipAddress", "userPortal", "userName"};

		try {
			post.addName(header);
			post.addRow();
			post.setValue("userName", loginId);
			post.setValue("firstName", dsRequest.getValue("firstName"));
			post.setValue("surName", dsRequest.getValue("surname"));
			post.setValue("email", dsRequest.getValue("email"));
			post.setValue("phone", dsRequest.getValue("phoneNumber"));
			post.setValue("message", dsRequest.getValue("message"));
			post.setValue("ipAddress", request.getRemoteAddr());
			post.setValue("userPortal", portalType);
			post.setValue("userType", "");
			post.setValue("location", "");

			result = wsClient.postContactUs(post);

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
}