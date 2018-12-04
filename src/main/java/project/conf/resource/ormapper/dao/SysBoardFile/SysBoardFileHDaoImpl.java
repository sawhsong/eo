package project.conf.resource.ormapper.dao.SysBoardFile;

import java.io.File;

import project.common.extend.BaseHDao;
import project.conf.resource.ormapper.dto.oracle.SysBoard;
import project.conf.resource.ormapper.dto.oracle.SysBoardFile;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.util.CommonUtil;
import zebra.util.FileUtil;

public class SysBoardFileHDaoImpl extends BaseHDao implements SysBoardFileDao {
	public int insert(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag) throws Exception {
		int result = 0;

		for (int i=0; i<fileDataSet.getRowCnt(); i++) {
			SysBoardFile sysBoardFile = new SysBoardFile();

			sysBoardFile.setFileId(CommonUtil.uid());
			sysBoardFile.setArticleId(sysBoard.getArticleId());
			sysBoardFile.setOriginalName(fileDataSet.getValue(i, "ORIGINAL_NAME"));
			sysBoardFile.setNewName(fileDataSet.getValue(i, "NEW_NAME"));
			sysBoardFile.setFileType(fileDataSet.getValue(i, "TYPE"));
			sysBoardFile.setFileIcon(fileDataSet.getValue(i, "ICON"));
			sysBoardFile.setFileSize(CommonUtil.toDouble(fileDataSet.getValue(i, "SIZE")));
			sysBoardFile.setRepositoryPath(fileDataSet.getValue(i, "REPOSITORY_PATH"));
			sysBoardFile.setInsertUserId(sysBoard.getInsertUserId());
			sysBoardFile.setInsertDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			sysBoardFile.setUpdateUserId(sysBoard.getInsertUserId());
			sysBoardFile.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));

			result += insertWithDto(sysBoardFile);
		}

		if (!CommonUtil.equalsIgnoreCase(isSaveFileFlag, "N")) {
			FileUtil.moveFile(fileDataSet);
		}

		return result;
	}

	public int update(SysBoard sysBoard, DataSet fileDataSet, String isSaveFileFlag, String[] fileIds) throws Exception {
		int result = 0;

		result = insert(sysBoard, fileDataSet, isSaveFileFlag);
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
		SysBoardFile sysBoardFile;
		String filePathPathAndName;
		int result = 0;

		sysBoardFile = getBoardFileById(fileId);
		filePathPathAndName = sysBoardFile.getRepositoryPath()+"/"+sysBoardFile.getNewName();

		queryAdvisor.addWhereClause("file_id = '"+fileId+"'");
		result = deleteWithSQLQuery(queryAdvisor, sysBoardFile);
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
		return selectAllAsDataSet(queryAdvisor, new SysBoardFile());
	}

	public SysBoardFile getBoardFileById(String fileId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("file_id = '"+fileId+"'");
		return (SysBoardFile)selectAllToDto(queryAdvisor, new SysBoardFile());
	}

	/**
	 * For SysBoardFile deletion only
	 * @param articleId
	 * @return DataSet
	 * @throws Exception
	 */
	private DataSet getBoardFileListDataSetByArticleIdForDeletion(String articleId) throws Exception {
		QueryAdvisor queryAdvisor = new QueryAdvisor();
		queryAdvisor.addWhereClause("article_id in (select article_id from sys_board connect by prior article_id = parent_article_id start with article_id = '"+articleId+"')");
		return selectAllAsDataSet(queryAdvisor, new SysBoardFile());
	}
}