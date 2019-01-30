package com.es.portal.common.module.commonlookup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.es.portal.common.extend.BaseBiz;
import com.es.portal.common.module.bizservice.webserviceclient.WebServiceClientBizService;

import zebra.config.MemoryBean;
import zebra.data.DataSet;

public class CommonLookupManager extends BaseBiz implements CommonLookupManagerBiz {
	private static Logger logger = LogManager.getLogger(CommonLookupManager.class);
	private static WebServiceClientBizService wsClient;

	public static void setWebServiceClientBizService(WebServiceClientBizService webServiceClientBizService) {
		wsClient = webServiceClientBizService;
	}

	public static WebServiceClientBizService getWebServiceClientBizService() {
		return wsClient;
	}

	public static void loadCommonLookup() throws Exception {
		MemoryBean.set("CommonLookup_PREFIX", getConvertedDataSet("PREFIX", wsClient.getCommonLookupDataSet("PREFIX")));
		MemoryBean.set("CommonLookup_STATES", getConvertedDataSet("STATES", wsClient.getCommonLookupDataSet("STATES")));
		MemoryBean.set("CommonLookup_ASG_TERM_NOTICE_UNIT", getConvertedDataSet("ASG_TERM_NOTICE_UNIT", wsClient.getCommonLookupDataSet("ASG_TERM_NOTICE_UNIT")));
		MemoryBean.set("CommonLookup_LEAVE_TYPE", getConvertedDataSet("LEAVE_TYPE", wsClient.getCommonLookupDataSet("LEAVE_TYPE")));
		MemoryBean.set("CommonLookup_LEAVE_CATEGORY", getConvertedDataSet("LEAVE_CATEGORY", wsClient.getCommonLookupDataSet("LEAVE_CATEGORY")));
		MemoryBean.set("CommonLookup_LEAVE_DURATION", getConvertedDataSet("LEAVE_DURATION", wsClient.getCommonLookupDataSet("LEAVE_DURATION")));

		logger.info("[MemoryBean] - Project Common Code has been loaded.");
	}

	public static void reload() throws Exception {
		loadCommonLookup();
	}

	public static DataSet getCodeDataSetByLookupType(String lookupType) throws Exception {
		return (DataSet)MemoryBean.get("CommonLookup_"+lookupType);
	}

	public static String getLookupCodeMeaning(String lookupType, String lookupCode) throws Exception {
		DataSet ds = getCodeDataSetByLookupType(lookupType);
		String str = "";

		if (ds != null && ds.getRowCnt() > 0) {
			str = ds.getValue(ds.getRowIndex("LOOKUP_CODE", lookupCode), "MEANING");
		}

		return str;
	}

	private static DataSet getConvertedDataSet(String lookupType, DataSet ds) throws Exception {
		DataSet rtn = new DataSet(new String[] {"LOOKUP_TYPE", "LOOKUP_CODE", "MEANING"});

		if (ds != null && ds.getRowCnt() > 0) {
			for (int i=0; i<ds.getRowCnt(); i++) {
				rtn.addRow();
				rtn.setValue(rtn.getRowCnt()-1, "LOOKUP_TYPE", lookupType);
				rtn.setValue(rtn.getRowCnt()-1, "LOOKUP_CODE", ds.getValue(i, "code"));
				rtn.setValue(rtn.getRowCnt()-1, "MEANING", ds.getValue(i, "meaning"));
			}
		}

		return rtn;
	}
}