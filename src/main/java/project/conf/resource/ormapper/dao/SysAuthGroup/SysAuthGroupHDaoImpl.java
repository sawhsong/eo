package project.conf.resource.ormapper.dao.SysAuthGroup;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysAuthGroup;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class SysAuthGroupHDaoImpl extends BaseHDao implements SysAuthGroupDao {
	public int insert(SysAuthGroup sysAuthGroup) throws Exception {
		return insertWithSQLQuery(sysAuthGroup);
	}

	public int update(String groupId, SysAuthGroup sysAuthGroup) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("group_id = '"+groupId+"'");
		return updateWithSQLQuery(queryAdvisor, sysAuthGroup);
	}

	public int delete(String groupIds[]) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		int result = 0;
		String idsForDel = "";

		if (!(groupIds == null || groupIds.length == 0)) {
			for (int i=0; i<groupIds.length; i++) {
				idsForDel += CommonUtil.isBlank(idsForDel) ? "'"+groupIds[i]+"'" : ",'"+groupIds[i]+"'";
			}
			queryAdvisor.addWhereClause("group_id in ("+idsForDel+")");
			result = deleteWithSQLQuery(queryAdvisor, new SysAuthGroup());
		}
		return result;
	}

	public int delete(String groupId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("group_id = '"+groupId+"'");
		return deleteWithSQLQuery(queryAdvisor, new SysAuthGroup());
	}

	public DataSet getAuthGroupDataSetByAuthGroupId(QueryAdvisor queryAdvisor) throws Exception {
		return selectAsDataSet(queryAdvisor, "query.SysAuthGroup.getAuthGroupDataSetByAuthGroupId");
	}

	public DataSet getAuthGroupDataSetBySearchCriteria(QueryAdvisor queryAdvisor) throws Exception {
		String dateFormat = ConfigUtil.getProperty("format.date.java");
		queryAdvisor.addVariable("dateFormat", dateFormat);
		queryAdvisor.addOrderByClause("nvl(update_date, insert_date) desc");
		queryAdvisor.addOrderByClause("group_name");
		return selectAsDataSet(queryAdvisor, "query.SysAuthGroup.getAuthGroupDataSetBySearchCriteria");
	}

	public SysAuthGroup getAuthGroupByGroupId(String groupId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("group_id = '"+groupId+"'");
		return (SysAuthGroup)selectAllToDto(queryAdvisor, new SysAuthGroup());
	}

	public DataSet getAllAuthGroupDataSet(QueryAdvisor queryAdvisor) throws Exception {
		return selectAllAsDataSet(queryAdvisor, new SysAuthGroup());
	}
}