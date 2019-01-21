package com.es.portal.common.module.comservice;

import com.es.portal.common.extend.BaseBiz;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;

public class ComServiceBizImpl extends BaseBiz implements ComServiceBiz {
	public ParamEntity getStateCodeFromMeaning(ParamEntity paramEntity) throws Exception {
		DataSet dsRequest = paramEntity.getRequestDataSet();
		DataSet result = new DataSet(new String[] {"code", "meaning"});
		String stateMeaning = dsRequest.getValue("state");
		String country = dsRequest.getValue("country");
		String flag = CommonUtil.nvl(dsRequest.getValue("flag"));

		try {
			result.addRow();
			result.setValue("code", getStateCode(stateMeaning, country, flag));
			result.setValue("meaning", stateMeaning);

			paramEntity.setAjaxResponseDataSet(result);
			paramEntity.setSuccess(true);
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	private String getStateCode(String meaning, String country, String flag) throws Exception {
		String rtn = "";

		if (CommonUtil.equalsIgnoreCase(meaning, "New South Wales")) {
			rtn = "NSW_02";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Australian Capital Territory")) {
			rtn = "ACT_02";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Tasmania")) {
			rtn = "TAS_03";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Victoria")) {
			rtn = "VIC_03";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Queensland")) {
			rtn = "QLD_07";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Northern Territory")) {
			rtn = "NT_08";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "Western Australia")) {
			rtn = "WA_08";
		} else if (CommonUtil.equalsIgnoreCase(meaning, "South Australia")) {
			rtn = "SA_08";
		} else {
			if (CommonUtil.equalsAnyIgnoreCase(flag, "AUONLY")) {
				rtn = "OS_01";
			} else {
				if (CommonUtil.equalsIgnoreCase(country, "New Zealand")) {
					rtn = "NZ_01";
				} else if (CommonUtil.equalsIgnoreCase(country, "Singapore")) {
					rtn = "SG_09";
				} else if (CommonUtil.equalsIgnoreCase(country, "Malaysia")) {
					rtn = "MY_09";
				} else if (CommonUtil.equalsIgnoreCase(country, "Hong Kong")) {
					rtn = "HK_09";
				} else if (CommonUtil.equalsIgnoreCase(country, "United Kingdom")) {
					rtn = "UK_09";
				} else {
					rtn = "OS_01";
				}
			}
		}

		return rtn;
	}
}