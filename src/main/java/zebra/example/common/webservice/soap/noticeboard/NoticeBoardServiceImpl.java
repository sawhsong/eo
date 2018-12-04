package zebra.example.common.webservice.soap.noticeboard;

import java.io.File;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.base.BaseWebService;
import zebra.data.DataSet;
import zebra.data.QueryAdvisor;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.example.conf.resource.ormapper.dao.ZebraBoard.ZebraBoardDao;
import zebra.example.conf.resource.ormapper.dao.ZebraBoardFile.ZebraBoardFileDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;
import zebra.exception.FrameworkException;
import zebra.export.ExportHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.ExportUtil;
import zebra.wssupport.SoapServiceSupport;

@WebService(endpointInterface = "zebra.example.common.webservice.soap.noticeboard.NoticeBoardService")
public class NoticeBoardServiceImpl extends BaseWebService implements NoticeBoardService {
	@Autowired
	private ZebraBoardDao zebraBoardDao;
	@Autowired
	private ZebraBoardFileDao zebraBoardFileDao;

	public String getList(String paramString) throws Exception {
		DataSet requestDataSet;
		QueryAdvisor queryAdvisor;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			queryAdvisor = paramEntity.getQueryAdvisor();

			queryAdvisor.setRequestDataSet(requestDataSet);

			paramEntity.setObject("resultDataSet", zebraBoardDao.getNoticeBoardDataSetByCriteria(queryAdvisor));
			paramEntity.setTotalResultRows(queryAdvisor.getTotalResultRows());
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public String getDetail(String paramString) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String articleId;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			articleId = requestDataSet.getValue("articleId");

			// ParamEntity will change Dto to DataSet - Client has to revert to Dto from DataSet
			paramEntity.setObject("noticeBoard", zebraBoardDao.getBoardByArticleId(articleId));
			paramEntity.setObject("fileDataSet", zebraBoardFileDao.getBoardFileListDataSetByArticleId(articleId));

			zebraBoardDao.updateVisitCountByArticleId(articleId);

			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public String getAttachedFile(String paramString) throws Exception {
		DataSet requestDataSet;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");

			paramEntity.setObject("fileDataSet", zebraBoardFileDao.getBoardFileListDataSetByArticleId(requestDataSet.getValue("articleId")));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public DataHandler exeDownload(String paramString) throws Exception {
		DataHandler dataHandler = null;
		DataSet requestDataSet;
		String repositoryPath, newName;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			repositoryPath = requestDataSet.getValue("repositoryPath");
			newName = requestDataSet.getValue("newName");

			dataHandler = SoapServiceSupport.getFileDataHandler(repositoryPath+"/"+newName);
		} catch (Exception ex) {
		}

		return dataHandler;
	}

	public String exeInsert(String paramString, List<DataHandler> dataHandlerList) throws Exception {
		DataSet requestDataSet;
		DataSet fileDataSet;
		ZebraBoard zebraBoard = new ZebraBoard();
		String uid = CommonUtil.uid();
		String userId;
		int result = 0;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			fileDataSet = (DataSet)paramEntity.getObject("fileDataSet");
			userId = (String)paramEntity.getObject("userId");

			zebraBoard.setArticleId(uid);
			zebraBoard.setBoardType(ZebraCommonCodeManager.getCodeByConstants("BOARD_TYPE_NOTICE"));
			zebraBoard.setWriterId(userId);
			zebraBoard.setWriterName(requestDataSet.getValue("writerName"));
			zebraBoard.setWriterEmail(requestDataSet.getValue("writerEmail"));
			zebraBoard.setWriterIpAddress((String)paramEntity.getObject("ipAddress"));
			zebraBoard.setArticleSubject(requestDataSet.getValue("articleSubject"));
			zebraBoard.setArticleContents(requestDataSet.getValue("articleContents"));
			zebraBoard.setInsertUserId(userId);
			zebraBoard.setInsertDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			zebraBoard.setRefArticleId(CommonUtil.nvl(requestDataSet.getValue("articleId"), "-1"));

			result = zebraBoardDao.insert(zebraBoard, fileDataSet, "N");
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801"));
			}

			saveFileFromDataHandlerList(dataHandlerList, fileDataSet);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public String exeUpdate(String paramString, List<DataHandler> dataHandlerList) throws Exception {
		DataSet requestDataSet;
		DataSet fileDataSet;
		ZebraBoard zebraBoard;
		String userId;
		String chkForDel, articleId;
		String fileIdsToDelete[];
		int result = 0;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			fileDataSet = (DataSet)paramEntity.getObject("fileDataSet");
			userId = (String)paramEntity.getObject("userId");
			chkForDel = requestDataSet.getValue("chkForDel");
			articleId = requestDataSet.getValue("articleId");
			fileIdsToDelete = CommonUtil.splitWithTrim(chkForDel, ConfigUtil.getProperty("delimiter.record"));

			zebraBoard = zebraBoardDao.getBoardByArticleId(articleId);
			zebraBoard.setArticleId(articleId);
			zebraBoard.setWriterId(userId);
			zebraBoard.setWriterName(requestDataSet.getValue("writerName"));
			zebraBoard.setWriterEmail(requestDataSet.getValue("writerEmail"));
			zebraBoard.setWriterIpAddress((String)paramEntity.getObject("ipAddress"));
			zebraBoard.setArticleSubject(requestDataSet.getValue("articleSubject"));
			zebraBoard.setArticleContents(requestDataSet.getValue("articleContents"));
			zebraBoard.setUpdateUserId(userId);
			zebraBoard.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));

			result = zebraBoardDao.update(zebraBoard, fileDataSet, "N", fileIdsToDelete);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			saveFileFromDataHandlerList(dataHandlerList, fileDataSet);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public String exeDelete(String paramString) throws Exception {
		DataSet requestDataSet;
		String articleId, chkForDel;
		String articleIds[];
		int result = 0;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			articleId = requestDataSet.getValue("articleId");
			chkForDel = requestDataSet.getValue("chkForDel");
			articleIds= CommonUtil.splitWithTrim(chkForDel, ConfigUtil.getProperty("delimiter.record"));

			if (CommonUtil.isBlank(articleId)) {
				result = zebraBoardDao.delete(articleIds);
			} else {
				result = zebraBoardDao.delete(articleId);
			}

			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return paramEntity.toXmlString();
	}

	public DataHandler exeExport(String paramString) throws Exception {
		DataHandler dataHandler = null;
		DataSet requestDataSet;
		QueryAdvisor queryAdvisor;
		ExportHelper exportHelper;
		String fileType, dataRange, pageTitle, fileName;
		String columnHeader[];
		File file;

		try {
			paramEntity.setObjectFromXmlString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			queryAdvisor = paramEntity.getQueryAdvisor();
			fileType = requestDataSet.getValue("fileType");
			dataRange = requestDataSet.getValue("dataRange");

			pageTitle = "Notice Board List";
			fileName = "NoticeBoardList";
			columnHeader = new String[]{"article_id", "writer_name", "writer_email", "article_subject", "created_date"};

			exportHelper = ExportUtil.getExportHelper(fileType);
			exportHelper.setPageTitle(pageTitle);
			exportHelper.setColumnHeader(columnHeader);
			exportHelper.setFileName(fileName);
			exportHelper.setPdfWidth(1000);

			queryAdvisor.setRequestDataSet(requestDataSet);
			if (CommonUtil.containsIgnoreCase(dataRange, "all"))
				queryAdvisor.setPagination(false);
			else {
				queryAdvisor.setPagination(true);
			}

			exportHelper.setSourceDataSet(zebraBoardDao.getNoticeBoardDataSetByCriteria(queryAdvisor));

			file = exportHelper.createFile();

			dataHandler = SoapServiceSupport.getFileDataHandler(file);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return dataHandler;
	}
}