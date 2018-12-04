package zebra.example.conf.resource.ormapper.dao.ZebraDomainDictionary;

import java.util.List;
import java.util.Map;

import zebra.base.Mapper;

public interface ZebraDomainDictionaryDaoMapper extends Mapper {
	@SuppressWarnings("rawtypes")
	public abstract List getDomainDictionaryDataSet(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public abstract List getDomainDictionaryDataSetPagination(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public abstract List getNameDataSetByName(Map paramMap) throws Exception;
}