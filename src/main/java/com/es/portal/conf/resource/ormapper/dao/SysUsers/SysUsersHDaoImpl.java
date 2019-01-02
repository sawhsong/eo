/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - SYS_USERS - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dao.SysUsers;

import com.es.portal.common.extend.BaseHDao;
import com.es.portal.conf.resource.ormapper.dto.oracle.SysUsers;

import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public class SysUsersHDaoImpl extends BaseHDao implements SysUsersDao {
	public SysUsers getUserByLoginId(String loginId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("user_name = '"+loginId+"'");

		return (SysUsers)selectAllToDto(queryAdvisor, new SysUsers());
	}

	public SysUsers getUserByLoginIdAndPassword(String loginId, String password) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("user_name = '"+loginId+"'");
		queryAdvisor.addWhereClause("password = '"+password+"'");

		return (SysUsers)selectAllToDto(queryAdvisor, new SysUsers());
	}

	public DataSet getSysUsersDataSetByLoginIdForAutoCompletion(QueryAdvisor queryAdvisor) throws Exception {
		return selectAsDataSet(queryAdvisor, "query.SysUsers.getSysUsersDataSetByLoginIdForAutoCompletion");
	}
}