package zebra.example.app.main;

import zebra.data.ParamEntity;

public interface MainBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getGarbageColletion(ParamEntity paramEntity) throws Exception;
}