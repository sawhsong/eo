package project.conf.resource.ormapper.dao.SysMenuAuthLink;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysMenuAuthLink;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;

public class SysMenuAuthLinkHDaoImpl extends BaseHDao implements SysMenuAuthLinkDao {
	public int insert(SysMenuAuthLink sysMenuAuthLink) throws Exception {
		return insertWithSQLQuery(sysMenuAuthLink);
	}

	public int deleteByAuthGroupIds(String authGroupIds[]) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		int result = 0;
		String idsForDel = "";

		if (!(authGroupIds == null || authGroupIds.length == 0)) {
			for (int i=0; i<authGroupIds.length; i++) {
				idsForDel += CommonUtil.isBlank(idsForDel) ? "'"+authGroupIds[i]+"'" : ",'"+authGroupIds[i]+"'";
			}
			queryAdvisor.addWhereClause("group_id in ("+idsForDel+")");
			result = deleteWithSQLQuery(queryAdvisor, new SysMenuAuthLink());
		}
		return result;
	}

	public int deleteByAuthGroupId(String authGroupId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("group_id = '"+authGroupId+"'");
		return deleteWithSQLQuery(queryAdvisor, new SysMenuAuthLink());
	}

	public DataSet getAllMenuAuthLink() throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysMenuAuthLink sysMenuAuthLink = new SysMenuAuthLink();

		queryAdvisor.addOrderByClause("menu_id");

		return selectAllAsDataSet(queryAdvisor, sysMenuAuthLink);
	}
}