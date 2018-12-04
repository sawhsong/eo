package zebra.example.conf.resource.ormapper.dao.ZebraBoard;

import zebra.base.IDao;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;

public interface ZebraBoardDao extends IDao {
	/**
	 * Insert new ZebraBoard record
	 * @param zebraBoard
	 * @return int
	 * @throws Exception
	 */
	public int insert(ZebraBoard zebraBoard) throws Exception;
	/**
	 * Insert new ZebraBoard and ZebraBoardFile record
	 * @param zebraBoard
	 * @param fileDataSet
	 * @param isSaveFileFlag
	 * @return
	 * @throws Exception
	 */
	public int insert(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception;
	/**
	 * Update ZebraBoard and Insert/Delete new ZebraBoardFile record
	 * @param zebraBoard(This must have PK attribute)
	 * @param fileDataSet
	 * @param isSaveFileFlag
	 * @param fileIds(For file deletion)
	 * @return int
	 * @throws Exception
	 */
	public int update(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception;
	/**
	 * Delete ZebraBoard record by ArticleId array
	 * @param articleIds
	 * @return int
	 * @throws Exception
	 */
	public int delete(String[] articleIds) throws Exception;
	/**
	 * Delete ZebraBoard record by ArticleId
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
	 * Get ZebraBoard record by ArticleId
	 * @param articleId
	 * @return ZebraBoard
	 * @throws Exception
	 */
	public ZebraBoard getBoardByArticleId(String articleId) throws Exception;
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