package com.es.portal.index;

import zebra.data.ParamEntity;

public interface IndexBiz {
	public ParamEntity index(ParamEntity paramEntity) throws Exception;
	public ParamEntity dashboard(ParamEntity paramEntity) throws Exception;
	public ParamEntity contactus(ParamEntity paramEntity) throws Exception;
}