package zebra.example.conf.resource.ormapper.dao.ZebraBoard;

import java.util.List;
import java.util.Map;

import zebra.base.Mapper;

public interface ZebraBoardDaoMapper extends Mapper {
	@SuppressWarnings("rawtypes")
	public List getNoticeBoardDataSetByCriteria(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getNoticeBoardDataSetByCriteriaPagination(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getFreeBoardDataSetByCriteria(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public List getFreeBoardDataSetByCriteriaPagination(Map paramMap) throws Exception;
}