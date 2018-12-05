/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - HP_PERSON_D - 
 *************************************************************************************************/
package project.conf.resource.ormapper.dao.HpPersonD;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.HpPersonD;
import zebra.data.QueryAdvisor;

public class HpPersonDHDaoImpl extends BaseHDao implements HpPersonDDao {
	public HpPersonD getPersonByPersonId(String personId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("person_id = '"+personId+"'");

		return (HpPersonD)selectAllToDto(queryAdvisor, new HpPersonD());
	}
}