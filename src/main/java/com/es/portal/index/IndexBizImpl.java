package com.es.portal.index;

import javax.servlet.http.HttpSession;

import com.es.portal.common.extend.BaseBiz;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class IndexBizImpl extends BaseBiz implements IndexBiz {
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
		HttpSession session = paramEntity.getSession();
		String loginId = CommonUtil.nvl((String)session.getAttribute("LoginIdForAdminTool"), (String)session.getAttribute("LoginId"));

		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}