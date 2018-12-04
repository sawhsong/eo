package project.conf.resource.ormapper.dao.SysBoard;

import project.conf.resource.ormapper.dto.oracle.SysBoard;
import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;

public interface SysBoardDao extends IDao {
	/**
	 * Insert new SysBoard record
	 * @param sysBoard
	 * @return int
	 * @throws Exception
	 */
	public int insert(SysBoard sysBoard) throws Exception;
	/**
	 * Insert new SysBoard and ZebraBoardFile record
	 * @param sysBoard
	 * @param fileDataSet
	 * @param isSaveFileFlag
	 * @return
	 * @throws Exception
	 */
	public int insert(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception;
	/**
	 * Update SysBoard and Insert/Delete new ZebraBoardFile record
	 * @param sysBoard(This must have PK attribute)
	 * @param fileDataSet
	 * @param isSaveFileFlag
	 * @param fileIds(For file deletion)
	 * @return int
	 * @throws Exception
	 */
	public int update(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception;
	/**
	 * Delete SysBoard record by ArticleId array
	 * @param articleIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] articleIds) throws Exception;
	/**
	 * Delete SysBoard record by ArticleId
	 * @param articleId
	 * @return
	 * @throws Exception
	 */
	public int delete(String articleId) throws Exception;
	/**
	 * Update VisitCount column only by ArticleId
	 * @param articleId
	 * @return int
	 * @throws Exception
	 */
	public int updateVisitCountByArticleId(String articleId) throws Exception;
	/**
	 * Get SysBoard record by ArticleId
	 * @param articleId
	 * @return SysBoard
	 * @throws Exception
	 */
	public SysBoard getBoardByArticleId(String articleId) throws Exception;
	/**
	 * Get NoticeBoard list dataset by search criteria in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getNoticeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception;
	/**
	 * Get FreeBoard list dataset by search criteria in QueryAdvisor
	 * @param queryAdvisor
	 * @return DataSet
	 * @throws Exception
	 */
	public DataSet getFreeBoardDataSetByCriteria(QueryAdvisor queryAdvisor) throws Exception;
}