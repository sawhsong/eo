package com.es.portal.common.module.autocompletion;

import zebra.data.ParamEntity;

public interface AutoCompletionBiz {
	public ParamEntity getSysUsersByLoginId(ParamEntity paramEntity) throws Exception;
	public ParamEntity getSysUsersByPersonId(ParamEntity paramEntity) throws Exception;
	public ParamEntity getPersonByName(ParamEntity paramEntity) throws Exception;
	public ParamEntity getPersonByNumber(ParamEntity paramEntity) throws Exception;
}