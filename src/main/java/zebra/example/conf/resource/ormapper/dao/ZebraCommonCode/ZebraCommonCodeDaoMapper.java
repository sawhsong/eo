package zebra.example.conf.resource.ormapper.dao.ZebraCommonCode;

import java.util.List;
import java.util.Map;

import zebra.base.Mapper;

public interface ZebraCommonCodeDaoMapper extends Mapper {
	@SuppressWarnings("rawtypes")
	public List getActiveCommonCodeDataSet(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getActiveCommonCodeDataSetPagination(Map paramMap) throws Exception;
}