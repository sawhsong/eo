package zebra.example.conf.resource.ormapper.dao.ZebraDomainDictionary;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.base.Dto;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraDomainDictionary;
import zebra.example.conf.resource.ormapper.mybatis.oracle.ZebraDomainDictionary.ZebraDomainDictionaryMapper;
import zebra.util.CommonUtil;

public class ZebraDomainDictionaryDaoImpl extends BaseDao implements ZebraDomainDictionaryDao {
	@Autowired
	private ZebraDomainDictionaryDaoMapper zebraDomainDictionaryDaoMapper;

	public int insert(Dto dto) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraDomainDictionaryMapper zebraDomainDictionaryMapper = getSqlSession().getMapper(ZebraDomainDictionaryMapper.class);
		return zebraDomainDictionaryMapper.insert(setMapFromDto(queryAdvisor.getQueryAdvisorMap(), dto));
	}

	public int updateByDomainId(String domainId, Dto dto) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraDomainDictionaryMapper zebraDomainDictionaryMapper = getSqlSession().getMapper(ZebraDomainDictionaryMapper.class);

		queryAdvisor.addWhereClause("domain_id = '" + domainId + "'");
		return zebraDomainDictionaryMapper.update(setMapFromDto(queryAdvisor.getQueryAdvisorMap(), dto));
	}

	public int delete(String[] domainIds) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraDomainDictionaryMapper zebraDomainDictionaryMapper = getSqlSession().getMapper(ZebraDomainDictionaryMapper.class);
		String domainIdsToDelete = "";
		int result = 0;

		if (!(domainIds == null || domainIds.length == 0)) {
			for (int i=0; i<domainIds.length; i++) {
				domainIdsToDelete += CommonUtil.isBlank(domainIdsToDelete) ? "'"+domainIds[i]+"'" : ",'"+domainIds[i]+"'";
			}

			queryAdvisor.addWhereClause("domain_id in ("+domainIdsToDelete+")");
			result = zebraDomainDictionaryMapper.delete(queryAdvisor.getQueryAdvisorMap());
		}

		return result;
	}

	public int delete(String domainId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraDomainDictionaryMapper zebraDomainDictionaryMapper = getSqlSession().getMapper(ZebraDomainDictionaryMapper.class);

		queryAdvisor.addWhereClause("domain_id = '"+domainId+"'");
		return zebraDomainDictionaryMapper.delete(queryAdvisor.getQueryAdvisorMap());
	}

	public DataSet getDomainDictionaryDataSet(QueryAdvisor queryAdvisor) throws Exception {
		DataSet dataSet;

		dataSet = new DataSet(zebraDomainDictionaryDaoMapper.getDomainDictionaryDataSet(queryAdvisor.getQueryAdvisorMap()));

		queryAdvisor.setTotalResultRows(dataSet.getRowCnt());
		if (queryAdvisor.isPagination()) {
			setPagination(queryAdvisor);
			dataSet = new DataSet(zebraDomainDictionaryDaoMapper.getDomainDictionaryDataSetPagination(queryAdvisor.getQueryAdvisorMap()));
		}

		return dataSet;
	}

	public DataSet getNameDataSetByName(QueryAdvisor queryAdvisor) throws Exception {
		return new DataSet(zebraDomainDictionaryDaoMapper.getNameDataSetByName(queryAdvisor.getQueryAdvisorMap()));
	}

	public ZebraDomainDictionary getDomainDictionaryById(String domainDictionaryId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraDomainDictionaryMapper zebraDomainDictionaryMapper = getSqlSession().getMapper(ZebraDomainDictionaryMapper.class);
		ZebraDomainDictionary zebraDomainDictionary = new ZebraDomainDictionary();

		queryAdvisor.addWhereClause("domain_id = '"+domainDictionaryId+"'");

		zebraDomainDictionary.setValues(new DataSet(zebraDomainDictionaryMapper.selectAll(queryAdvisor.getQueryAdvisorMap())));
		return zebraDomainDictionary;
	}
}