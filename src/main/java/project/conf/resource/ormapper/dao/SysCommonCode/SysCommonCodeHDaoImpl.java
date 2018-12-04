package project.conf.resource.ormapper.dao.SysCommonCode;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysCommonCode;
import zebra.base.Dto;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public class SysCommonCodeHDaoImpl extends BaseHDao implements SysCommonCodeDao {
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
		return deleteWithSQLQuery(queryAdvisor, new SysCommonCode());
	}

	public DataSet getAllActiveCommonCode() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysCommonCode sysCommonCode = new SysCommonCode();

		queryAdvisor.addWhereClause("is_active = 'Y'");
		queryAdvisor.addOrderByClause("code_type");
		queryAdvisor.addOrderByClause("sort_order");

		return selectAllAsDataSet(queryAdvisor, sysCommonCode);
	}

	public DataSet getActiveCodeTypeDataSet() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysCommonCode sysCommonCode = new SysCommonCode();

		queryAdvisor.addWhereClause("is_active = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAllAsDataSet(queryAdvisor, sysCommonCode);
	}

	public DataSet getActiveCommonCodeDataSet(QueryAdvisor queryAdvisor) throws Exception {
		queryAdvisor.addWhereClause("is_active = 'Y'");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAsDataSet(queryAdvisor, "query.SysCommonCode.getActiveCommonCodeDataSet");
	}

	public DataSet getCommonCodeDataSetByCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysCommonCode sysCommonCode = new SysCommonCode();

		queryAdvisor.addWhereClause("code_type = '"+codeType+"'");
		queryAdvisor.addOrderByClause("sort_order");

		return selectAllAsDataSet(queryAdvisor, sysCommonCode);
	}

	public DataSet getActiveCodeTypeDataSetLikeCodeType(String codeType) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysCommonCode sysCommonCode = new SysCommonCode();

		queryAdvisor.addWhereClause("is_active = 'Y'");
		queryAdvisor.addWhereClause("lower(code_type) like lower('%"+codeType+"%')");
		queryAdvisor.addWhereClause("common_code = '0000000000'");
		queryAdvisor.addOrderByClause("code_type");

		return selectAllAsDataSet(queryAdvisor, sysCommonCode);
	}
}