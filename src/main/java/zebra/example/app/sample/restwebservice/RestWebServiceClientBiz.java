package zebra.example.app.sample.restwebservice;

import zebra.data.ParamEntity;

public abstract interface RestWebServiceClientBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity getInsert(ParamEntity paramEntity) throws Exception;
	public ParamEntity getUpdate(ParamEntity paramEntity) throws Exception;
	public ParamEntity getAttachedFile(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeInsert(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception;
}