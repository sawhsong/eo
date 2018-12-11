package com.es.portal.ipro.timesheet;

import java.util.Iterator;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.es.portal.common.extend.BaseBiz;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.data.QueryAdvisor;
import zebra.exception.FrameworkException;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class TimesheetBizImpl extends BaseBiz implements TimesheetBiz {
	public ParamEntity mytimesheets(ParamEntity paramEntity) throws Exception {
		DataSet requestDataSet = paramEntity.getRequestDataSet();
		QueryAdvisor queryAdvisor = paramEntity.getQueryAdvisor();
		String providerUrl = ConfigUtil.getProperty("webService.perci.url");
		String result = "";
		String orgId = requestDataSet.getValue("orgId");
		String header[] = new String[] {"billableAmount", "contractorPaidDate", "customerPaidDate", "dueDate", "endDate", "groupInvoiceNumber", "invoiceDate", "invoiceNumber", "invoiceStatus", "startDate"};

		try {
			queryAdvisor.addVariable(variableName, variableValue);

			Response wsResponse = webClient.path("corporate/"+orgId+"/invoices")
					.query("dateFrom", searchDateFrom)
					.query("dateTo", searchDateTo)
					.accept(new String[] {"application/json"}).get();
			result = (String)wsResponse.readEntity(String.class);

			paramEntity.setObjectFromJsonString(result);
			paramEntity.setSuccess(true);

			if (!paramEntity.isSuccess()) {
				throw new FrameworkException(paramEntity.getMessageCode(), paramEntity.getMessage());
			}
			paramEntity.setAjaxResponseDataSet(getDataSet(paramEntity, "corporateInvoiceList", header));
		} catch (Exception ex) {
			throw new FrameworkException(paramEntity, ex);
		}
		return paramEntity;
	}

	@SuppressWarnings({ "unchecked" })
	private DataSet getDataSet(ParamEntity paramEntity, String objName, String[] header) throws Exception {
		DataSet ds = new DataSet();
		JSONArray jsonArray = (JSONArray)JSONSerializer.toJSON(paramEntity.getObject(objName));

		ds.addName(header);
		for (Iterator<String> iter = jsonArray.iterator(); iter.hasNext();) {
			ds.addRow();
			JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(iter.next());
			for (Object keys : jsonObject.keySet()) {
				String key = (String)keys;
				Object value = jsonObject.get(key);
				ds.setValue(ds.getRowCnt()-1, key, value);
			}
		}
		return ds;
	}
}