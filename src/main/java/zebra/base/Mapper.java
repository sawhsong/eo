package zebra.base;

import java.util.List;
import java.util.Map;

public interface Mapper {
	@SuppressWarnings("rawtypes")
	public List selectAll(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public int insert(Map paramMap) throws Exception;
	public int insertWithDto(Dto dto) throws Exception;
	@SuppressWarnings("rawtypes")
	public int update(Map paramMap) throws Exception;
	public int updateWithDto(Dto dto) throws Exception;
	@SuppressWarnings("rawtypes")
	public int updateColumns(Map paramMap) throws Exception;
	@SuppressWarnings("rawtypes")
	public int delete(Map paramMap) throws Exception;
}