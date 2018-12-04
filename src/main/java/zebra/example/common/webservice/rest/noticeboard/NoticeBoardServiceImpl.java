package zebra.example.common.webservice.rest.noticeboard;

import java.io.File;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
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
import zebra.wssupport.RestServiceSupport;

public class NoticeBoardServiceImpl extends BaseWebService implements NoticeBoardService {
	@Autowired
	private ZebraBoardDao zebraBoardDao;
	@Autowired
	private ZebraBoardFileDao zebraBoardFileDao;

	/*!
	 * Internal use
	 */
	public Response getList(String paramString) throws Exception {
		DataSet requestDataSet;
		QueryAdvisor queryAdvisor;

		try {
			paramEntity.setObjectFromJsonString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			queryAdvisor = paramEntity.getQueryAdvisor();

			queryAdvisor.setRequestDataSet(requestDataSet);

			paramEntity.setObject("resultDataSet", zebraBoardDao.getNoticeBoardDataSetByCriteria(queryAdvisor));
			paramEntity.setTotalResultRows(queryAdvisor.getTotalResultRows());
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public Response getDetail(String paramString) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String articleId;

		try {
			paramEntity.setObjectFromJsonString(paramString);

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

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public Response getAttachedFile(String paramString) throws Exception {
		DataSet requestDataSet;

		try {
			paramEntity.setObjectFromJsonString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");

			paramEntity.setObject("fileDataSet", zebraBoardFileDao.getBoardFileListDataSetByArticleId(requestDataSet.getValue("articleId")));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public MultipartBody download(String paramString) throws Exception {
		MultipartBody multipartBody = null;
		String repositoryPath, newName;

		try {
			paramEntity.setObjectFromJsonString(paramString);

			repositoryPath = (String)paramEntity.getObject("repositoryPath");
			newName = (String)paramEntity.getObject("newName");

			multipartBody = RestServiceSupport.getMultipartBody(paramEntity, repositoryPath+"/"+newName, newName);
		} catch (Exception ex) {
		}

		return multipartBody;
	}

	public Response exeInsert(MultipartBody multipartBody) throws Exception {
		DataSet requestDataSet;
		DataSet fileDataSet;
		ZebraBoard zebraBoard = new ZebraBoard();
		String uid = CommonUtil.uid();
		String userId;
		int result = 0;

		try {
			paramEntity.setObjectFromJsonString(RestServiceSupport.getParamEntityFromMultipartBody(multipartBody));

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

			saveFileFromMultipartBody(multipartBody, fileDataSet);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public Response exeUpdate(MultipartBody multipartBody) throws Exception {
		DataSet requestDataSet;
		DataSet fileDataSet;
		ZebraBoard zebraBoard;
		String userId;
		String chkForDel, articleId;
		String fileIdsToDelete[];
		int result = 0;

		try {
			paramEntity.setObjectFromJsonString(RestServiceSupport.getParamEntityFromMultipartBody(multipartBody));

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

			saveFileFromMultipartBody(multipartBody, fileDataSet);

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801"));
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public Response exeDelete(String paramString) throws Exception {
		DataSet requestDataSet;
		String articleId, chkForDel;
		String articleIds[];
		int result = 0;

		try {
			paramEntity.setObjectFromJsonString(paramString);

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

		return Response.ok(paramEntity.toJsonString()).build();
	}

	public MultipartBody exeExport(String paramString) throws Exception {
		MultipartBody multipartBody = null;
		DataSet requestDataSet;
		QueryAdvisor queryAdvisor;
		ExportHelper exportHelper;
		String fileType, dataRange, pageTitle, fileName;
		String columnHeader[];
		File file;

		try {
			paramEntity.setObjectFromJsonString(paramString);

			requestDataSet = (DataSet)paramEntity.getObject("requestDataSet");
			queryAdvisor = paramEntity.getQueryAdvisor();
			fileType = (String)paramEntity.getObject("fileType");
			dataRange = (String)paramEntity.getObject("dataRange");

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

			paramEntity.setSuccess(true);
			paramEntity.setFileNameToExport(exportHelper.getFileName());

			multipartBody = RestServiceSupport.getMultipartBody(paramEntity, file, exportHelper.getFileName());
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
		}

		return multipartBody;
	}


	/*!
	 * External use
	 */
/*
	public Response getList() throws Exception {
		DataSet resultDataSet = new DataSet();
		QueryAdvisor queryAdvisor;

		try {
			queryAdvisor = paramEntity.getQueryAdvisor();

			resultDataSet = zebraBoardDao.getNoticeBoardDataSetByCriteria(queryAdvisor);
		} catch (Exception ex) {
			setWebserviceException(paramEntity, "E801", ex);
			throw new WebApplicationException(ex.getMessage());
		}

		return Response.ok(resultDataSet.toJsonString()).build();
	}
*/
}