/**************************************************************************************************
 * Framework Generated DAO Source
 * - HP_PERSON_D - 
 *************************************************************************************************/
package com.es.portal.conf.resource.ormapper.dao.HpPersonD;

import com.es.portal.conf.resource.ormapper.dto.oracle.HpPersonD;
import zebra.base.IDao;

public interface HpPersonDDao extends IDao {
	/**
	 * Get HpPersonD object with given personId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public HpPersonD getPersonByPersonId(String personId) throws Exception;
}