package zebra.example.app.framework.dtogenerator;

import zebra.data.ParamEntity;

public interface DtoGeneratorBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception;
	public ParamEntity getList(ParamEntity paramEntity) throws Exception;
	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception;
	public ParamEntity getGeneratorInfo(ParamEntity paramEntity) throws Exception;
	public ParamEntity exeGenerate(ParamEntity paramEntity) throws Exception;
}