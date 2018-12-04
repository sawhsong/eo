package zebra.example.app.sample.soapwebservice;

import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.http.HttpSession;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.example.common.extend.BaseBiz;
import zebra.example.common.webservice.soap.noticeboard.NoticeBoardService;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;
import zebra.exception.FrameworkException;
import zebra.wssupport.SoapServiceSupport;

public class SoapWebServiceClientBizImpl extends BaseBiz implements SoapWebServiceClientBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getList(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			paramEntity.setPagination(true);

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			result = noticeBoardService.getList(paramEntity.toXmlString());

			paramEntity.setObjectFromXmlString(result);
			if (!paramEntity.isSuccess()) {
				throw new FrameworkException(paramEntity.getMessageCode(), paramEntity.getMessage());
			}
			paramEntity.setAjaxResponseDataSet((DataSet)paramEntity.getObject("resultDataSet"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		ZebraBoard zebraBoard = new ZebraBoard();
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			result = noticeBoardService.getDetail(paramEntity.toXmlString());

			paramEntity.setObjectFromXmlString(result);

			// ParamEntity will always return DataSet even if the object is Dto
			zebraBoard.setValues((DataSet)paramEntity.getObject("noticeBoard"));
			paramEntity.setObject("noticeBoard", zebraBoard);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getInsert(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getUpdate(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity = getDetail(paramEntity);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getAttachedFile(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			result = noticeBoardService.getAttachedFile(paramEntity.toXmlString());

			paramEntity.setObjectFromXmlString(result);
			paramEntity.setAjaxResponseDataSet((DataSet)paramEntity.getObject("fileDataSet"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeDownload(ParamEntity paramEntity) throws Exception {
		DataHandler dataHandler = null;
		NoticeBoardService noticeBoardService;

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			dataHandler = noticeBoardService.exeDownload(paramEntity.toXmlString());

			paramEntity.setObject("dataHandler", dataHandler);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeInsert(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		List<DataHandler> dataHandlerList;
		DataSet fileDataSet = paramEntity.getRequestFileDataSet();
		HttpSession session = paramEntity.getSession();
		String result = "";
		String remoteIp = paramEntity.getRequest().getRemoteAddr();
		String userId = (String)session.getAttribute("UserId");

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			paramEntity.setObject("fileDataSet", fileDataSet);
			paramEntity.setObject("userId", userId);
			paramEntity.setObject("ipAddress", remoteIp);

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			dataHandlerList = SoapServiceSupport.getFileDataHandler(fileDataSet);
			result = noticeBoardService.exeInsert(paramEntity.toXmlString(), dataHandlerList);

			paramEntity.setObjectFromXmlString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		List<DataHandler> dataHandlerList;
		DataSet fileDataSet = paramEntity.getRequestFileDataSet();
		HttpSession session = paramEntity.getSession();
		String result = "";
		String remoteIp = paramEntity.getRequest().getRemoteAddr();
		String userId = (String)session.getAttribute("UserId");

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			paramEntity.setObject("fileDataSet", fileDataSet);
			paramEntity.setObject("userId", userId);
			paramEntity.setObject("ipAddress", remoteIp);

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			dataHandlerList = SoapServiceSupport.getFileDataHandler(fileDataSet);
			result = noticeBoardService.exeUpdate(paramEntity.toXmlString(), dataHandlerList);

			paramEntity.setObjectFromXmlString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception {
		NoticeBoardService noticeBoardService;
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			result = noticeBoardService.exeDelete(paramEntity.toXmlString());

			paramEntity.setObjectFromXmlString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception {
		DataHandler dataHandler = null;
		NoticeBoardService noticeBoardService;

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());

			noticeBoardService = (NoticeBoardService)SoapServiceSupport.getProxyFactory("zebraSoapNoticeBoard", NoticeBoardService.class).create();
			dataHandler = noticeBoardService.exeExport(paramEntity.toXmlString());

			paramEntity.setObject("dataHandler", dataHandler);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}