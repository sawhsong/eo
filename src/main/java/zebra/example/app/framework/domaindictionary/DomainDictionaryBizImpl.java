package zebra.example.app.framework.domaindictionary;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.example.common.extend.BaseBiz;
import zebra.example.common.module.datahelper.ZebraDataHelper;
import zebra.example.conf.resource.ormapper.dao.ZebraDomainDictionary.ZebraDomainDictionaryDao;
import zebra.example.conf.resource.ormapper.dto.oracle.ZebraDomainDictionary;
import zebra.exception.FrameworkException;
import zebra.export.ExportHelper;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.ExportUtil;

public class DomainDictionaryBizImpl extends BaseBiz implements DomainDictionaryBiz {
	@Autowired
	private ZebraDomainDictionaryDao zebraDomainDictionaryDao;

	public ParamEntity getDefault(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getList(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String searchWord = requestDataSet.getValue("searchWord");
		HttpSession session = paramEntity.getSession();

		try {
			queryAdvisor.setPagination(true);
			queryAdvisor.addVariable("dateFormat", ConfigUtil.getProperty("format.date.java"));
			queryAdvisor.addVariable("langCode", (String)session.getAttribute("langCode"));
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(domain_name) like lower('%"+searchWord+"%') or lower(name_abbreviation) like lower('%"+searchWord+"%')");

			paramEntity.setAjaxResponseDataSet(zebraDomainDictionaryDao.getDomainDictionaryDataSet(queryAdvisor));
			paramEntity.setTotalResultRows(queryAdvisor.getTotalResultRows());
			paramEntity.setSuccess(true);
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

	public ParamEntity exeInsert(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		String domainId = CommonUtil.uid();
		int result = -1;
		ZebraDomainDictionary zebraDomainDictionary = new ZebraDomainDictionary();

		try {
			zebraDomainDictionary.setDomainId(domainId);
			zebraDomainDictionary.setDomainName(requestDataSet.getValue("domainName"));
			zebraDomainDictionary.setNameAbbreviation(requestDataSet.getValue("nameAbbreviation"));
			zebraDomainDictionary.setDataType(requestDataSet.getValue("dataType"));
			zebraDomainDictionary.setDataLength(CommonUtil.toDouble(requestDataSet.getValue("dataLength")));
			zebraDomainDictionary.setDataPrecision(CommonUtil.toDouble(requestDataSet.getValue("dataPrecision")));
			zebraDomainDictionary.setDataScale(CommonUtil.toDouble(requestDataSet.getValue("dataScale")));
			zebraDomainDictionary.setDescription(requestDataSet.getValue("description"));
			zebraDomainDictionary.setInsertUserId((String)session.getAttribute("UserId"));

			result = zebraDomainDictionaryDao.insert(zebraDomainDictionary);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity getDetail(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		ZebraDomainDictionary zebraDomainDictionary;

		try {
			zebraDomainDictionary = zebraDomainDictionaryDao.getDomainDictionaryById(requestDataSet.getValue("domainId"));
			zebraDomainDictionary.setInsertUserName(ZebraDataHelper.getUserNameById(zebraDomainDictionary.getInsertUserId()));
			zebraDomainDictionary.setUpdateUserName(ZebraDataHelper.getUserNameById(zebraDomainDictionary.getUpdateUserId()));

			paramEntity.setObject("zebraDomainDictionary", zebraDomainDictionary);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity getUpdate(ParamEntity paramEntity) throws Exception {
		try {
			paramEntity = getDetail(paramEntity);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}

	public ParamEntity exeUpdate(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		HttpSession session = paramEntity.getSession();
		int result = -1;
		ZebraDomainDictionary zebraDomainDictionary = new ZebraDomainDictionary();

		try {
			zebraDomainDictionary.setDomainName(requestDataSet.getValue("domainName"));
			zebraDomainDictionary.setNameAbbreviation(requestDataSet.getValue("nameAbbreviation"));
			zebraDomainDictionary.setDataType(requestDataSet.getValue("dataType"));
			zebraDomainDictionary.setDataLength(CommonUtil.toDouble(requestDataSet.getValue("dataLength")));
			zebraDomainDictionary.setDataPrecision(CommonUtil.toDouble(requestDataSet.getValue("dataPrecision")));
			zebraDomainDictionary.setDataScale(CommonUtil.toDouble(requestDataSet.getValue("dataScale")));
			zebraDomainDictionary.setDescription(requestDataSet.getValue("description"));
			zebraDomainDictionary.setUpdateUserId((String)session.getAttribute("UserId"));
			zebraDomainDictionary.setUpdateDate(CommonUtil.toDate(CommonUtil.getSysdate()));
			zebraDomainDictionary.addUpdateColumnFromField();

			result = zebraDomainDictionaryDao.updateByDomainId(requestDataSet.getValue("domainId"), zebraDomainDictionary);
			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeDelete(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		String domainId = requestDataSet.getValue("domainId");
		String chkForDel = requestDataSet.getValue("chkForDel");
		String[] domainIds = CommonUtil.splitWithTrim(chkForDel, ConfigUtil.getProperty("delimiter.record"));
		int result = -1;

		try {
			if (CommonUtil.isBlank(domainId)) {
				result = zebraDomainDictionaryDao.delete(domainIds);
			} else {
				result = zebraDomainDictionaryDao.delete(domainId);
			}

			if (result <= 0) {
				throw new FrameworkException("E801", getMessage("E801", paramEntity));
			}

			paramEntity.setSuccess(true);
			paramEntity.setMessage("I801", getMessage("I801", paramEntity));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	public ParamEntity exeExport(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		HttpSession session = paramEntity.getSession();
		ExportHelper exportHelper;
		String dataRange = requestDataSet.getValue("dataRange");
		String searchWord = requestDataSet.getValue("searchWord");

		try {
			String pageTitle = "Domain Dictionary List";
			String fileName = "DomainDictionaryList";
			String[] columnHeader = {"domain_id", "domain_name", "name_abbreviation", "data_type", "data_length", "description", "last_update"};

			exportHelper = ExportUtil.getExportHelper(requestDataSet.getValue("fileType"));
			exportHelper.setPageTitle(pageTitle);
			exportHelper.setColumnHeader(columnHeader);
			exportHelper.setFileName(fileName);
			exportHelper.setPdfWidth(1000);

			queryAdvisor.addVariable("dateFormat", ConfigUtil.getProperty("format.date.java"));
			queryAdvisor.addVariable("langCode", (String)session.getAttribute("langCode"));
			queryAdvisor.addAutoFillCriteria(searchWord, "lower(domain_name) like lower('%"+searchWord+"%') or lower(name_abbreviation) like lower('%"+searchWord+"%')");
			if (CommonUtil.containsIgnoreCase(dataRange, "all"))
				queryAdvisor.setPagination(false);
			else {
				queryAdvisor.setPagination(true);
			}

			exportHelper.setSourceDataSet(zebraDomainDictionaryDao.getDomainDictionaryDataSet(queryAdvisor));

			paramEntity.setSuccess(true);
			paramEntity.setFileToExport(exportHelper.createFile());
			paramEntity.setFileNameToExport(exportHelper.getFileName());
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}

		return paramEntity;
	}
}