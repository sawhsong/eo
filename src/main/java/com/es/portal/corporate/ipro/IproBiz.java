package com.es.portal.corporate.ipro;

import zebra.data.ParamEntity;

public interface IproBiz {
	public ParamEntity myIpros(ParamEntity paramEntity) throws Exception;
	public ParamEntity getIproList(ParamEntity paramEntity) throws Exception;
}