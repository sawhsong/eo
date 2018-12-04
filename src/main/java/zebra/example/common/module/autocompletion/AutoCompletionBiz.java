package zebra.example.common.module.autocompletion;

import zebra.data.ParamEntity;

public interface AutoCompletionBiz {
	public ParamEntity getDomainDictionaryName(ParamEntity paramEntity) throws Exception;
	public ParamEntity getCommonCodeType(ParamEntity paramEntity) throws Exception;
	public ParamEntity getTableName(ParamEntity paramEntity) throws Exception;
}