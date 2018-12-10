/**************************************************************************************************
 * Framework Generated HDAOImpl Source
 * - HP_PERSON_D - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dao.HpPersonD;

import com.es.portal.common.extend.BaseHDao;
import com.es.portal.conf.resource.ormapper.dto.oracle.HpPersonD;
import zebra.data.QueryAdvisor;

public class HpPersonDHDaoImpl extends BaseHDao implements HpPersonDDao {
	public HpPersonD getPersonByPersonId(String personId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("person_id = '"+personId+"'");

		return (HpPersonD)selectAllToDto(queryAdvisor, new HpPersonD());
	}
}