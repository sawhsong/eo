package com.es.portal.serviceresource;

import com.es.portal.common.extend.BaseBiz;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;

public class ServiceResourceBizImpl extends BaseBiz implements ServiceResourceBiz {
	public ParamEntity doDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setObject("resultDataSet", new DataSet());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}
}