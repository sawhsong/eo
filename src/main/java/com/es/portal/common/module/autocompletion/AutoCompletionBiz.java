package com.es.portal.common.module.autocompletion;

import zebra.data.ParamEntity;

public interface AutoCompletionBiz {
	public ParamEntity getSysUsersByLoginId(ParamEntity paramEntity) throws Exception;
}