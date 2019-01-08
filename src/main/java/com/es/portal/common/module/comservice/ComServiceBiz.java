package com.es.portal.common.module.comservice;

import zebra.data.ParamEntity;

public interface ComServiceBiz {
	public ParamEntity getStateCodeFromMeaning(ParamEntity paramEntity) throws Exception;
}