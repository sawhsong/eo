package zebra.example.conf.resource.ormapper.dao.ZebraBoardFile;

import java.io.File;

import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseHDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoardFile;
import zebra.util.CommonUtil;
import zebra.util.FileUtil;

public class ZebraBoardFileHDaoImpl extends BaseHDao implements ZebraBoardFileDao {
	public int insert(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception {
		int result = 0;

		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			ZebraBoardFile zebraBoardFile = new ZebraBoardFile();

			zebraBoardFile.setFileId(CommonUtil.uid());
			zebraBoardFile.setArticleId(zebraBoard.getArticleId());
			zebraBoardFile.setOriginalName(fileDataSet.getValue(i, "ORIGINAL_NAME"));
			zebraBoardFile.setNewName(fileDataSet.getValue(i, "NEW_NAME"));
			zebraBoardFile.setFileType(fileDataSet.getValue(i, "TYPE"));
			zebraBoardFile.setFileIcon(fileDataSet.getValue(i, "ICON"));
			zebraBoardFile.setFileSize(CommonUtil.toDouble(fileDataSet.getValue(i, "SIZE")));
			zebraBoardFile.setRepositoryPath(fileDataSet.getValue(i, "REPOSITORY_PATH"));
			zebraBoardFile.setInsertUserId(zebraBoard.getInsertUserId());
			zebraBoardFile.setInsertDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			zebraBoardFile.setUpdateUserId(zebraBoard.getInsertUserId());
			zebraBoardFile.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));

			result += insertWithDto(zebraBoardFile);
		}

		if (!CommonUtil.equalsIgnoreCase(isSaveFileFlag, "N")) {
			FileUtil.moveFile(fileDataSet);
		}

		return result;
	}

	public int update(ZebraBoard zebraBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception {
		int result = 0;

		result = insert(zebraBoard, fileDataSet, isSaveFileFlag);
		if (result > 0) {
			result = delete(fileIds);
		}
		return result;
	}

	public int delete(String[] fileIds) throws Exception {
		int result = 0;

		if (!(fileIds == null || fileIds.length == 0)) {
			for (int i=0; i<fileIds.length; i++) {
				result += delete(fileIds[i]);
			}
		}

		return result;
	}

	public int delete(String fileId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		ZebraBoardFile zebraBoardFile;
		String filePathPathAndName;
		int result = 0;

		zebraBoardFile = getBoardFileById(fileId);
		filePathPathAndName = zebraBoardFile.getRepositoryPath()+"/"+zebraBoardFile.getNewName();

		queryAdvisor.addWhereClause("file_id = '"+fileId+"'");
		result = deleteWithSQLQuery(queryAdvisor, zebraBoardFile);
		if (result > 0) {
			FileUtil.deleteQuietly(new File(filePathPathAndName));
		}
		return result;
	}

	public int deleteByArticleId(String articleId) throws Exception {
		DataSet boardFileListDataSet = getBoardFileListDataSetByArticleIdForDeletion(articleId);
		int result = 0;

		for (int i=0; i<boardFileListDataSet.getRowCnt(); i++) {
			result += delete(boardFileListDataSet.getValue(i, "FILE_ID"));
		}

		return result;
	}

	public DataSet getBoardFileListDataSetByArticleId(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id = '"+articleId+"'");
		return selectAllAsDataSet(queryAdvisor, new ZebraBoardFile());
	}

	public ZebraBoardFile getBoardFileById(String fileId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("file_id = '"+fileId+"'");
		return (ZebraBoardFile)selectAllToDto(queryAdvisor, new ZebraBoardFile());
	}

	/**
	 * For ZebraBoardFile deletion only
	 * @param articleId
	 * @return DataSet
	 * @throws Exception
	 */
	private DataSet getBoardFileListDataSetByArticleIdForDeletion(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id in (select article_id from zebra_board connect by prior article_id = ref_article_id start with article_id = '"+articleId+"')");
		return selectAllAsDataSet(queryAdvisor, new ZebraBoardFile());
	}
}