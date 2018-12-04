package zebra.example.conf.resource.ormapper.dao.ZebraDomainDictionary;

import zebra.base.Dto;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseHDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraDomainDictionary;
import zebra.util.CommonUtil;

public class ZebraDomainDictionaryHDaoImpl extends BaseHDao implements ZebraDomainDictionaryDao {
	public int insert(Dto dto) throws Exception {
		return insertWithDto(dto);
	}

	public int updateByDomainId(String domainId, Dto dto) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("domain_id = '" + domainId + "'");

		return updateColumns(queryAdvisor, dto);
	}

	public int delete(String[] domainIds) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		String domainIdsToDelete = "";
		int result = 0;

		if (!(domainIds == null || domainIds.length == 0)) {
			for (int i=0; i<domainIds.length; i++) {
				domainIdsToDelete += CommonUtil.isBlank(domainIdsToDelete) ? "'"+domainIds[i]+"'" : ",'"+domainIds[i]+"'";
			}

			queryAdvisor.addWhereClause("domain_id in ("+domainIdsToDelete+")");
			result = deleteWithSQLQuery(queryAdvisor, new ZebraDomainDictionary());
		}

		return result;
	}

	public int delete(String domainId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("domain_id = '" + domainId + "'");
		return deleteWithSQLQuery(queryAdvisor, new ZebraDomainDictionary());
	}

	public DataSet getDomainDictionaryDataSet(QueryAdvisor queryAdvisor) throws Exception {
		return selectAsDataSet(queryAdvisor, "query.zebra.ZebraDomainDictionary.getDomainDictionaryDataSet");
	}

	public DataSet getNameDataSetByName(QueryAdvisor queryAdvisor) throws Exception {
		return selectAsDataSet(queryAdvisor, "query.zebra.ZebraDomainDictionary.getNameDataSetByName");
	}

	public ZebraDomainDictionary getDomainDictionaryById(String domainDictionaryId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();

		queryAdvisor.addWhereClause("domain_id = '"+domainDictionaryId+"'");
		return (ZebraDomainDictionary)selectAllToDto(queryAdvisor, new ZebraDomainDictionary());
	}
}