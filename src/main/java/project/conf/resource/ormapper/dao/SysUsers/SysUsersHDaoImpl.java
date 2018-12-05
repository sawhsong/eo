/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - SYS_USERS - 
 *************************************************************************************************/
package project.conf.resource.ormapper.dao.SysUsers;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysUsers;
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
}