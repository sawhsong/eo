package project.conf.resource.ormapper.dao.SysBoard;

import org.springframework.beans.factory.annotation.Autowired;

import project.common.extend.BaseHDao;
import project.common.module.commoncode.CommonCodeManager;
import project.conf.resource.ormapper.dao.SysBoardFile.SysBoardFileDao;
import project.conf.resource.ormapper.dto.oracle.SysBoard;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class SysBoardHDaoImpl extends BaseHDao implements SysBoardDao {
	@Autowired
	private SysBoardFileDao sysBoardFileDao;

	public int insert(SysBoard sysBoard) throws Exception {
		return insertWithDto(sysBoard);
	}

	public int insert(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception {
		try {
			insert(sysBoard);
			sysBoardFileDao.setDataSourceName(getDataSourceName());
			sysBoardFileDao.insert(sysBoard, fileDataSet, isSaveFileFlag);
			return 1;
		} catch (Exception ex) {
			return -1;
		}
	}

	public int update(SysBoard sysBoard) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id = '" + sysBoard.getArticleId() + "'");
		return updateWithDto(queryAdvisor, sysBoard);
	}

	public int update(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception {
		try {
			update(sysBoard);
			sysBoardFileDao.setDataSourceName(getDataSourceName());
			sysBoardFileDao.update(sysBoard, fileDataSet, isSaveFileFlag, fileIds);
			return 1;
		} catch (Exception ex) {
			return -1;
		}
	}

	public int delete(String[] articleIds) throws Exception {
		int result = 0;

		if (!(articleIds == null || articleIds.length == 0)) {
			sysBoardFileDao.setDataSourceName(getDataSourceName());
			for (int i=0; i<articleIds.length; i++) {
				sysBoardFileDao.deleteByArticleId(articleIds[i]);
				result += delete(articleIds[i]);
			}
		}

		return result;
	}

	public int delete(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		sysBoardFileDao.setDataSourceName(getDataSourceName());
		sysBoardFileDao.deleteByArticleId(articleId);
		queryAdvisor.addWhereClause("article_id in (select article_id from sys_board connect by prior article_id = parent_article_id start with article_id = '"+articleId+"')");
		return deleteWithSQLQuery(queryAdvisor, new SysBoard());
	}

	public int updateVisitCountByArticleId(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		SysBoard sysBoard = new SysBoard();

		queryAdvisor.addWhereClause("article_id = '"+articleId+"'");
		sysBoard.addUpdateColumn("hit_cnt", "hit_cnt + 1");

		return updateColumns(queryAdvisor, sysBoard);
	}

	public SysBoard getBoardByArticleId(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id = '"+articleId+"'");
		return (SysBoard)selectAllToDto(queryAdvisor, new SysBoard());
	}

	public DataSet getNoticeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String dateFormat = ConfigUtil.getProperty("format.date.java");
		String searchType = requestDataSet.getValue("searchType");
		String searchWord = requestDataSet.getValue("searchWord");
		String searchDateFrom = requestDataSet.getValue("fromDate");
		String searchDateTo = requestDataSet.getValue("toDate");

		if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_SUBJECT"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_subject) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_WRITER"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(writer_name) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_CONTENTS"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_contents) like lower('%"+searchWord+"%')");
		}

		queryAdvisor.addAutoFillCriteria(searchDateFrom, "trunc(insert_date) >= trunc(to_date('"+searchDateFrom+"', '"+dateFormat+"'))");
		queryAdvisor.addAutoFillCriteria(searchDateTo, "trunc(insert_date) <= trunc(to_date('"+searchDateTo+"', '"+dateFormat+"'))");
		queryAdvisor.addVariable("dateFormat", dateFormat);
		queryAdvisor.addVariable("boardType", CommonCodeManager.getCodeByConstants("BOARD_TYPE_NOTICE"));

		return selectAsDataSet(queryAdvisor, "query.SysBoard.getNoticeBoardDataSetByCriteria");
	}

	public DataSet getFreeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String dataFormat = ConfigUtil.getProperty("format.date.java");
		String searchType = requestDataSet.getValue("searchType");
		String searchWord = requestDataSet.getValue("searchWord");
		String searchDateFrom = requestDataSet.getValue("fromDate");
		String searchDateTo = requestDataSet.getValue("toDate");

		if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_SUBJECT"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_subject) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_WRITER"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(writer_name) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, CommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_CONTENTS"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_contents) like lower('%"+searchWord+"%')");
		}

		queryAdvisor.addAutoFillCriteria(searchDateFrom, "trunc(insert_date) >= trunc(to_date('"+searchDateFrom+"', '"+dataFormat+"'))");
		queryAdvisor.addAutoFillCriteria(searchDateTo, "trunc(insert_date) <= trunc(to_date('"+searchDateTo+"', '"+dataFormat+"'))");
		queryAdvisor.addVariable("dateFormat", dataFormat);
		queryAdvisor.addVariable("boardType", CommonCodeManager.getCodeByConstants("BOARD_TYPE_FREE"));

		return selectAsDataSet(queryAdvisor, "query.SysBoard.getFreeBoardDataSetByCriteria");
	}
}