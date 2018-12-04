package zebra.example.app.sample.restwebservice;

import javax.servlet.http.HttpSession;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.example.common.extend.BaseBiz;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraBoard;
import zebra.exception.FrameworkException;
import zebra.wssupport.RestServiceSupport;

public class RestWebServiceClientBizImpl extends BaseBiz implements RestWebServiceClientBiz {
	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getList(ParamEntity paramEntity) throws Exception {
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			paramEntity.setPagination(true);
			result = RestServiceSupport.post("zebraRestNoticeBoard/getList", "application/json", paramEntity);
			paramEntity.setObjectFromJsonString(result);

			if (!paramEntity.isSuccess()) {
				throw new FrameworkException(paramEntity.getMessageCode(), paramEntity.getMessage());
			}
			paramEntity.setAjaxResponseDataSet((DataSet)paramEntity.getObject("resultDataSet"));
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		ZebraBoard zebraBoard = new ZebraBoard();
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			result = RestServiceSupport.post("zebraRestNoticeBoard/getDetail", "application/json", paramEntity);
			paramEntity.setObjectFromJsonString(result);

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
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			result = RestServiceSupport.post("zebraRestNoticeBoard/getAttachedFile", "application/json", paramEntity);
			paramEntity.setObjectFromJsonString(result);
			paramEntity.setAjaxResponseDataSet((DataSet)paramEntity.getObject("fileDataSet"));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeInsert(ParamEntity paramEntity) throws Exception {
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

			result = RestServiceSupport.postAttachment("zebraRestNoticeBoard/exeInsert", "multipart/mixed", "application/json", paramEntity, fileDataSet);
			paramEntity.setObjectFromJsonString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
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

			result = RestServiceSupport.postAttachment("zebraRestNoticeBoard/exeUpdate", "multipart/mixed", "application/json", paramEntity, fileDataSet);
			paramEntity.setObjectFromJsonString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception {
		String result = "";

		try {
			paramEntity.setObject("requestDataSet", paramEntity.getRequestDataSet());
			result = RestServiceSupport.post("zebraRestNoticeBoard/exeDelete", "application/json", paramEntity);
			paramEntity.setObjectFromJsonString(result);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}