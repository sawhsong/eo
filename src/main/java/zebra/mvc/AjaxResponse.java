package zebra.mvc;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.result.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

import zebra.data.DataSet;
import zebra.data.ParamEntity;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;
import zebra.util.HtmlUtil;

public class AjaxResponse extends StrutsResultSupport {
	@SuppressWarnings("rawtypes")
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		ParamEntity paramEntity = new ParamEntity();
		DataSet dataSet = new DataSet();
		String ajaxDataType = request.getHeader("ajaxDataTypeForFramework");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"), true);

		response.addHeader("ajaxDataTypeForFramework", ajaxDataType);

		Enumeration attNames = request.getAttributeNames();
		while (attNames.hasMoreElements()) {
			String name = (String)attNames.nextElement();

			if (request.getAttribute(name) instanceof ParamEntity) {
				paramEntity = (ParamEntity)request.getAttribute(name);
				dataSet = paramEntity.getAjaxResponseDataSet();
				break;
			}
		}

		if (dataSet == null) {
			if ("XML".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/xml;charset=utf-8");
				out.println(getResponseXML(paramEntity));
			} else if ("HTML".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/html;charset=utf-8");
				out.print(getResponseHTML(paramEntity));
			} else if ("JSON".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/plain;charset=utf-8");
				out.print(getResponseJSON(paramEntity));
			} else {
				response.setContentType("text/plain;charset=utf-8");
				out.print(getResponseTEXT(paramEntity));
			}
		} else {
			if ("XML".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/xml;charset=utf-8");
				out.println(getResponseXML(paramEntity, dataSet));
			} else if ("HTML".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/html;charset=utf-8");
				out.print(getResponseHTML(paramEntity, dataSet));
			} else if ("JSON".equalsIgnoreCase(ajaxDataType)) {
				response.setContentType("text/plain;charset=utf-8");
				out.print(getResponseJSON(paramEntity, dataSet));
			} else {
				response.setContentType("text/plain;charset=utf-8");
				out.print(getResponseTEXT(paramEntity, dataSet));
			}
		}

		out.flush();
	}

	private String getResponseXML(ParamEntity paramEntity) {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<ajaxResponse>\n");
		sb.append("<isSuccess>"+paramEntity.isSuccess()+"</isSuccess>\n");
		sb.append("<messageCode>"+paramEntity.getMessageCode()+"</messageCode>\n");
		sb.append("<message>"+HtmlUtil.stringToHtml(paramEntity.getMessage())+"</message>\n");
		sb.append("<totalResultRows>"+paramEntity.getTotalResultRows()+"</totalResultRows>\n");
		sb.append("</ajaxResponse>");

		return sb.toString();
	}

	private String getResponseXML(ParamEntity paramEntity, DataSet ds) {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<ajaxResponse>\n");
		sb.append("<isSuccess>"+paramEntity.isSuccess()+"</isSuccess>\n");
		sb.append("<messageCode>"+paramEntity.getMessageCode()+"</messageCode>\n");
		sb.append("<message>"+HtmlUtil.stringToHtml(paramEntity.getMessage())+"</message>\n");
		sb.append("<totalResultRows>"+paramEntity.getTotalResultRows()+"</totalResultRows>\n");
		sb.append("<dataSet>\n");
		sb.append("\t<dataSetHeader>\n");
		for (int i=0; i<ds.getColumnCnt(); i++) {
			sb.append("\t\t<header name=\"").append(ds.getName(i)).append("\" />\n");
		}
		sb.append("\t</dataSetHeader>\n");
		sb.append("\t<dataSetRow>\n");
		for (int i=0; i<ds.getRowCnt(); i++) {
			sb.append("\t\t<dataSetRowItems>\n");
			for (int j=0; j<ds.getColumnCnt(); j++) {
				sb.append("\t\t\t<rowItem name=\"").append(ds.getName(j)).append("\" value=\"").append(HtmlUtil.stringToHtml(ds.getValue(i, j))).append("\" />\n");
			}
			sb.append("\t\t</dataSetRowItems>\n");
		}
		sb.append("\t</dataSetRow>\n");
		sb.append("</dataSet>\n");
		sb.append("</ajaxResponse>");

		return sb.toString();
	}

	private String getResponseHTML(ParamEntity paramEntity) {
		StringBuffer sb = new StringBuffer();

		sb.append("<div id=\"divAjaxResponseHTML\">\n");
		sb.append("\t<table id=\"tblAjaxResponseHTML_info\" class=\"tblBlank\">\n");
		sb.append("\t\t<thead>\n");
		sb.append("\t\t\t<tr>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">isSuccess</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">messageCode</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">message</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">totalResultRows</th>\n");
		sb.append("\t\t\t</tr>\n");
		sb.append("\t\t</thead>\n");
		sb.append("\t\t<tbody>\n");
		sb.append("\t\t\t<tr>\n");
		sb.append("\t\t\t\t<td id=\"isSuccess\" class=\"tdBlank\">"+paramEntity.isSuccess()+"</td>\n");
		sb.append("\t\t\t\t<td id=\"messageCode\" class=\"tdBlank\">"+paramEntity.getMessageCode()+"</td>\n");
		sb.append("\t\t\t\t<td id=\"message\" class=\"tdBlank\">"+HtmlUtil.stringToHtml(paramEntity.getMessage())+"</td>\n");
		sb.append("\t\t\t\t<td id=\"totalResultRows\" class=\"tdBlank\">"+paramEntity.getTotalResultRows()+"</td>\n");
		sb.append("\t\t\t</tr>\n");
		sb.append("\t\t</tbody>\n");
		sb.append("\t</table>\n");
		sb.append("</div>\n");

		return sb.toString();
	}

	private String getResponseHTML(ParamEntity paramEntity, DataSet ds) {
		StringBuffer sb = new StringBuffer();

		sb.append("<div id=\"divAjaxResponseHTML\">\n");
		sb.append("\t<table id=\"tblAjaxResponseHTML_info\" class=\"tblBlank\">\n");
		sb.append("\t\t<thead>\n");
		sb.append("\t\t\t<tr>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">isSuccess</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">messageCode</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">message</th>\n");
		sb.append("\t\t\t\t<th class=\"thBlank\">totalResultRows</th>\n");
		sb.append("\t\t\t</tr>\n");
		sb.append("\t\t</thead>\n");
		sb.append("\t\t<tbody>\n");
		sb.append("\t\t\t<tr>\n");
		sb.append("\t\t\t\t<td id=\"isSuccess\" class=\"tdBlank\">"+paramEntity.isSuccess()+"</td>\n");
		sb.append("\t\t\t\t<td id=\"messageCode\" class=\"tdBlank\">"+paramEntity.getMessageCode()+"</td>\n");
		sb.append("\t\t\t\t<td id=\"message\" class=\"tdBlank\">"+HtmlUtil.stringToHtml(paramEntity.getMessage())+"</td>\n");
		sb.append("\t\t\t\t<td id=\"totalResultRows\" class=\"tdBlank\">"+paramEntity.getTotalResultRows()+"</td>\n");
		sb.append("\t\t\t</tr>\n");
		sb.append("\t\t</tbody>\n");
		sb.append("\t</table>\n");
		sb.append("\t<table id=\"tblAjaxResponseHTML_dataSet\" class=\"tblBlank\">\n");
		sb.append("\t\t<thead>\n");
		sb.append("\t\t\t<tr>\n");
		for (int i=0; i<ds.getColumnCnt(); i++) {
			sb.append("\t\t\t\t<th class=\"thBlank\">").append(ds.getName(i)).append("</th>\n");
		}
		sb.append("\t\t\t</tr>\n");
		sb.append("\t\t</thead>\n");
		sb.append("\t\t<tbody>\n");
		for (int i=0; i<ds.getRowCnt(); i++) {
			sb.append("\t\t\t<tr>\n");
			for (int j=0; j<ds.getColumnCnt(); j++) {
				sb.append("\t\t\t\t<td class=\"tdBlank\">").append(HtmlUtil.stringToHtml(ds.getValue(i, j))).append("</td>\n");
			}
			sb.append("\t\t\t</tr>\n");
		}
		sb.append("\t\t</tbody>\n");
		sb.append("\t</table>\n");
		sb.append("</div>\n");

		return sb.toString();
	}

	private String getResponseJSON(ParamEntity paramEntity) {
		StringBuffer sb = new StringBuffer();

		sb.append("{");
		sb.append("\"isSuccess\":\""+paramEntity.isSuccess()+"\",");
		sb.append("\"messageCode\":\""+paramEntity.getMessageCode()+"\",");
		sb.append("\"message\":\""+HtmlUtil.stringToHtml(paramEntity.getMessage())+"\",");
		sb.append("\"totalResultRows\":\""+paramEntity.getTotalResultRows()+"\"");
		sb.append("}");

		return sb.toString();
	}

	private String getResponseJSON(ParamEntity paramEntity, DataSet ds) {
		StringBuffer sb = new StringBuffer();

		sb.append("{");
		sb.append("\"isSuccess\":\""+paramEntity.isSuccess()+"\",");
		sb.append("\"messageCode\":\""+paramEntity.getMessageCode()+"\",");
		sb.append("\"message\":\""+HtmlUtil.stringToHtml(paramEntity.getMessage())+"\",");
		sb.append("\"totalResultRows\":\""+paramEntity.getTotalResultRows()+"\",");
		sb.append("\"dataSet\":{");
		sb.append("\"dataSetHeader\":[");
		for (int i=0; i<ds.getColumnCnt(); i++) {
			sb.append("\"").append(ds.getName(i)).append("\"");
			if (i < ds.getColumnCnt()-1) {
				sb.append(",");
			}
		}
		sb.append("],");
		sb.append("\"dataSetValue\":{");
		for (int i=0; i<ds.getRowCnt(); i++) {
			sb.append("\"dataSetValueRow").append(i).append("\":[");
			for (int j=0; j<ds.getColumnCnt(); j++) {
				sb.append("\"").append(HtmlUtil.stringToHtml(ds.getValue(i, j))).append("\"");
				if (j < ds.getColumnCnt()-1) {
					sb.append(",");
				}
			}
			sb.append("]");
			if (i < ds.getRowCnt()-1) {
				sb.append(",");
			} else {
				sb.append("");
			}
		}
		sb.append("}");
		sb.append("}");
		sb.append("}");

		return sb.toString();
	}

	private String getResponseTEXT(ParamEntity paramEntity) {
		StringBuffer sb = new StringBuffer();
		String delimiter = ConfigUtil.getProperty("delimiter.data");

		sb.append("isSuccess"+delimiter+"messageCode"+delimiter+"message"+delimiter+"totalResultRows").append("\n");
		sb.append(paramEntity.isSuccess()+delimiter+paramEntity.getMessageCode()+delimiter+HtmlUtil.stringToHtml(paramEntity.getMessage())+delimiter+paramEntity.getTotalResultRows());

		return sb.toString();
	}

	private String getResponseTEXT(ParamEntity paramEntity, DataSet ds) {
		StringBuffer sb = new StringBuffer();
		String delimiter = ConfigUtil.getProperty("delimiter.data");

		sb.append("isSuccess"+delimiter+"messageCode"+delimiter+"message"+delimiter+"totalResultRows").append("\n");
		sb.append(paramEntity.isSuccess()+delimiter+paramEntity.getMessageCode()+delimiter+HtmlUtil.stringToHtml(paramEntity.getMessage())+delimiter+paramEntity.getTotalResultRows()).append("\n\n");
		sb.append(CommonUtil.toString(ds.getNames(), delimiter)).append("\n");
		for (int i=0; i<ds.getRowCnt(); i++) {
			for (int j=0; j<ds.getColumnCnt(); j++) {
				sb.append(HtmlUtil.stringToHtml(ds.getValue(i, j)));
				if (j < ds.getColumnCnt()-1) {
					sb.append(delimiter);
				}
			}

			if (i < ds.getRowCnt()-1) {
				sb.append("\n");
			}
		}

		return sb.toString();
	}
}