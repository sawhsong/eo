package zebra.example.conf.resource.ormapper.dao.ZebraCommonCode;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.base.Dto;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseDao;
import zebra.example.conf.resource.ormapper.mybatis.oracle.ZebraCommonCode.ZebraCommonCodeMapper;

public class ZebraCommonCodeDaoImpl extends BaseDao implements ZebraCommonCodeDao {
	@Autowired
	private ZebraCommonCodeDaoMapper zebraCommonCodeDaoMapper;

	public int insert(Dto dto) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		return zebraCommonCodeMapper.insert(setMapFromDto(queryAdvisor.getQueryAdvisorMap(), dto));
	}

	public int delete(String[] codeTypes) throws Exception {
		int result = 0;

		if (!(codeTypes == null || codeTypes.length == 0)) {
			for (int i=0; i<codeTypes.length; i++) {
				result += delete(codeTypes[i]);
			}
		}

		return result;
	}

	public int delete(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		queryAdvisor.addWhereClause("code_type = '"+codeType+"'");
		return zebraCommonCodeMapper.delete(queryAdvisor.getQueryAdvisorMap());
	}

	public DataSet getAllActiveCommonCode() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addOrderByClause("code_type");
		queryAdvisor.addOrderByClause("sort_order");

		return new DataSet(zebraCommonCodeMapper.selectAll(queryAdvisor.getQueryAdvisorMap()));
	}

	public DataSet getActiveCodeTypeDataSet() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return new DataSet(zebraCommonCodeMapper.selectAll(queryAdvisor.getQueryAdvisorMap()));
	}

	public DataSet getActiveCommonCodeDataSet(QueryAdvisor queryAdvisor) throws Exception {
		DataSet dataSet;

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		dataSet = new DataSet(zebraCommonCodeDaoMapper.getActiveCommonCodeDataSet(queryAdvisor.getQueryAdvisorMap()));

		queryAdvisor.setTotalResultRows(dataSet.getRowCnt());
		if (queryAdvisor.isPagination()) {
			setPagination(queryAdvisor);
			dataSet = new DataSet(zebraCommonCodeDaoMapper.getActiveCommonCodeDataSetPagination(queryAdvisor.getQueryAdvisorMap()));
		}

		return dataSet;
	}

	public DataSet getCommonCodeDataSetByCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		queryAdvisor.addWhereClause("code_type = '"+codeType+"'");
		queryAdvisor.addOrderByClause("sort_order");

		return new DataSet(zebraCommonCodeMapper.selectAll(queryAdvisor.getQueryAdvisorMap()));
	}

	public DataSet getActiveCodeTypeDataSetLikeCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCodeMapper zebraCommonCodeMapper = getSqlSession().getMapper(ZebraCommonCodeMapper.class);

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("lower(code_type) like lower('%"+codeType+"%')");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return new DataSet(zebraCommonCodeMapper.selectAll(queryAdvisor.getQueryAdvisorMap()));
	}
}