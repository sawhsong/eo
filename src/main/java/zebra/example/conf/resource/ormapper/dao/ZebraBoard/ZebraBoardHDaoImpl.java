package zebra.example.conf.resource.ormapper.dao.ZebraBoard;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseHDao;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.example.conf.resource.ormapper.dao.ZebraBoardFile.ZebraBoardFileDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class ZebraBoardHDaoImpl extends BaseHDao implements ZebraBoardDao {
	@Autowired
	private ZebraBoardFileDao zebraBoardFileDao;

	public int insert(ZebraBoard zebraBoard) throws Exception {
		return insertWithDto(zebraBoard);
	}

	public int insert(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception {
		try {
			insert(zebraBoard);
			zebraBoardFileDao.setDataSourceName(getDataSourceName());
			zebraBoardFileDao.insert(zebraBoard, fileDataSet, isSaveFileFlag);
			return 1;
		} catch (Exception ex) {
			return -1;
		}
	}

	public int update(ZebraBoard zebraBoard) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id = '" + zebraBoard.getArticleId() + "'");
		return updateWithDto(queryAdvisor, zebraBoard);
	}

	public int update(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception {
		try {
			update(zebraBoard);
			zebraBoardFileDao.setDataSourceName(getDataSourceName());
			zebraBoardFileDao.update(zebraBoard, fileDataSet, isSaveFileFlag, fileIds);
			return 1;
		} catch (Exception ex) {
			return -1;
		}
	}

	public int delete(String[] articleIds) throws Exception {
		int result = 0;

		if (!(articleIds == null || articleIds.length == 0)) {
			zebraBoardFileDao.setDataSourceName(getDataSourceName());
			for (int i=0; i<articleIds.length; i++) {
				zebraBoardFileDao.deleteByArticleId(articleIds[i]);
				result += delete(articleIds[i]);
			}
		}

		return result;
	}

	public int delete(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		zebraBoardFileDao.setDataSourceName(getDataSourceName());
		zebraBoardFileDao.deleteByArticleId(articleId);
		queryAdvisor.addWhereClause("article_id in (select article_id from zebra_board connect by prior article_id = ref_article_id start with article_id = '"+articleId+"')");
		return deleteWithSQLQuery(queryAdvisor, new ZebraBoard());
	}

	public int updateVisitCountByArticleId(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraBoard zebraBoard = new ZebraBoard();

		queryAdvisor.addWhereClause("article_id = '"+articleId+"'");
		zebraBoard.addUpdateColumn("visit_cnt", "visit_cnt + 1");

		return updateColumns(queryAdvisor, zebraBoard);
	}

	public ZebraBoard getBoardByArticleId(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id = '"+articleId+"'");
		return (ZebraBoard)selectAllToDto(queryAdvisor, new ZebraBoard());
	}

	public DataSet getNoticeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String dateFormat = ConfigUtil.getProperty("format.date.java");
		String searchType = requestDataSet.getValue("searchType");
		String searchWord = requestDataSet.getValue("searchWord");
		String searchDateFrom = requestDataSet.getValue("fromDate");
		String searchDateTo = requestDataSet.getValue("toDate");

		if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_SUBJECT"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_subject) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_WRITER"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(writer_name) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_CONTENTS"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_contents) like lower('%"+searchWord+"%')");
		}

		queryAdvisor.addAutoFillCriteria(searchDateFrom, "trunc(insert_date) >= trunc(to_date('"+searchDateFrom+"', '"+dateFormat+"'))");
		queryAdvisor.addAutoFillCriteria(searchDateTo, "trunc(insert_date) <= trunc(to_date('"+searchDateTo+"', '"+dateFormat+"'))");
		queryAdvisor.addVariable("dateFormat", dateFormat);
		queryAdvisor.addVariable("boardType", ZebraCommonCodeManager.getCodeByConstants("BOARD_TYPE_NOTICE"));

		return selectAsDataSet(queryAdvisor, "query.zebra.ZebraBoard.getNoticeBoardDataSetByCriteria");
	}

	public DataSet getFreeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception {
		DataSet requestDataSet = queryAdvisor.getRequestDataSet();
		String dataFormat = ConfigUtil.getProperty("format.date.java");
		String searchType = requestDataSet.getValue("searchType");
		String searchWord = requestDataSet.getValue("searchWord");
		String searchDateFrom = requestDataSet.getValue("fromDate");
		String searchDateTo = requestDataSet.getValue("toDate");

		if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_SUBJECT"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_subject) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_WRITER"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(writer_name) like lower('%"+searchWord+"%')");
		} else if (CommonUtil.equalsIgnoreCase(searchType, ZebraCommonCodeManager.getCodeByConstants("BOARD_SEARCH_TYPE_CONTENTS"))) {
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(article_contents) like lower('%"+searchWord+"%')");
		}

		queryAdvisor.addAutoFillCriteria(searchDateFrom, "trunc(insert_date) >= trunc(to_date('"+searchDateFrom+"', '"+dataFormat+"'))");
		queryAdvisor.addAutoFillCriteria(searchDateTo, "trunc(insert_date) <= trunc(to_date('"+searchDateTo+"', '"+dataFormat+"'))");
		queryAdvisor.addVariable("dateFormat", dataFormat);
		queryAdvisor.addVariable("boardType", ZebraCommonCodeManager.getCodeByConstants("BOARD_TYPE_FREE"));

		return selectAsDataSet(queryAdvisor, "query.zebra.ZebraBoard.getFreeBoardDataSetByCriteria");
	}
}