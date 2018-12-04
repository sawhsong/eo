package zebra.example.conf.resource.ormapper.dao.ZebraCommonCode;

import zebra.base.Dto;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseHDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraCommonCode;

public class ZebraCommonCodeHDaoImpl extends BaseHDao implements ZebraCommonCodeDao {
	public int insert(Dto dto) throws Exception {
		return insertWithSQLQuery(dto);
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
		queryAdvisor.addWhereClause("code_type = '"+codeType+"'");
		return deleteWithSQLQuery(queryAdvisor, new ZebraCommonCode());
	}

	public DataSet getAllActiveCommonCode() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCode zebraCommonCode = new ZebraCommonCode();

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addOrderByClause("code_type");
		queryAdvisor.addOrderByClause("sort_order");

		return selectAllAsDataSet(queryAdvisor, zebraCommonCode);
	}

	public DataSet getActiveCodeTypeDataSet() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCode zebraCommonCode = new ZebraCommonCode();

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAllAsDataSet(queryAdvisor, zebraCommonCode);
	}

	public DataSet getActiveCommonCodeDataSet(QueryAdvisor queryAdvisor) throws Exception {
		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAsDataSet(queryAdvisor, "query.zebra.ZebraCommonCode.getActiveCommonCodeDataSet");
	}

	public DataSet getCommonCodeDataSetByCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCode zebraCommonCode = new ZebraCommonCode();

		queryAdvisor.addWhereClause("code_type = '"+codeType+"'");
		queryAdvisor.addOrderByClause("sort_order");

		return selectAllAsDataSet(queryAdvisor, zebraCommonCode);
	}

	public DataSet getActiveCodeTypeDataSetLikeCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraCommonCode zebraCommonCode = new ZebraCommonCode();

		queryAdvisor.addWhereClause("use_yn = 'Y'");
		queryAdvisor.addWhereClause("lower(code_type) like lower('%"+codeType+"%')");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAllAsDataSet(queryAdvisor, zebraCommonCode);
	}
}