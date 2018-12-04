package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import project.common.module.commoncode.CommonCodeManager;
import zebra.base.TaglibSupport;
import zebra.data.DataSet;
import zebra.example.common.module.commoncode.ZebraCommonCodeManager;
import zebra.util.CommonUtil;

public class CommonCodeRadio extends TaglibSupport {
	private String codeType = "";
	private String name = "";
	private String langCode = "";
	private String selectedValue = "";
	private String disabledValue = "";
	private String script = "";
	private String labelClassName = "";
	private String inputClassName = "";
	private String labelStyle = "";
	private String inputStyle = "";
	private String displayType = "";	// inline / block
	private String isBootstrap = "";
	private String status = "";
	private String options = "";	// for data validator
	private String source = "";	// common_code source(framework / project)
	private String attribute = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			StringBuffer html = new StringBuffer();
			DataSet ds = new DataSet();
			String defaultLangCode = "", attrStr = "", attrs[], attr[];

			if (CommonUtil.isNotBlank(source) || CommonUtil.equalsIgnoreCase(source, "framework")) {
				ds = ZebraCommonCodeManager.getCodeDataSetByCodeType(codeType);
			} else {
				ds = CommonCodeManager.getCodeDataSetByCodeType(codeType);
			}

			defaultLangCode = CommonUtil.nvl(langCode, (String)httpSession.getAttribute("langCode"));

			if (CommonUtil.isNotBlank(attribute)) {
				attrs = CommonUtil.split(attribute, ";");
				for (int i=0; i<attrs.length; i++) {
					attr = CommonUtil.split(attrs[i], ":");
					attrStr += " "+attr[0]+"=\""+attr[1]+"\"";
				}
			}

			for (int i=0; i<ds.getRowCnt(); i++) {
				if (CommonUtil.toBoolean(isBootstrap, true)) {
					if (CommonUtil.equalsIgnoreCase(displayType, "block")) {
						html.append("<div class=\"radio");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						html.append("\"><label");

						if (CommonUtil.isNotBlank(labelClassName)) {html.append(" class=\""+labelClassName+"\"");}
						if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
						html.append("><input type=\"radio\" id=\""+name+"_"+i+"\" name=\""+name+"\" value=\""+ds.getValue(i, "COMMON_CODE")+"\"");
						if (CommonUtil.isNotBlank(inputClassName)) {html.append(" class=\""+inputClassName+"\"");}
						if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
						if (CommonUtil.equals(ds.getValue(i, "COMMON_CODE"), selectedValue)) {html.append(" checked");}
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
						if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
						if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

						html.append("/>"+ds.getValue(i, "DESCRIPTION_"+defaultLangCode.toUpperCase())+"</label></div>");
					} else {
						html.append("<label class=\"radio-inline");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
						html.append("\"");
						if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
						html.append("><input type=\"radio\" id=\""+name+"_"+i+"\" name=\""+name+"\" value=\""+ds.getValue(i, "COMMON_CODE")+"\"");
						if (CommonUtil.isNotBlank(inputClassName)) {html.append(" class=\""+inputClassName+"\"");}
						if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
						if (CommonUtil.equals(ds.getValue(i, "COMMON_CODE"), selectedValue)) {html.append(" checked");}
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
						if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
						if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

						html.append("/>"+ds.getValue(i, "DESCRIPTION_"+defaultLangCode.toUpperCase())+"</label>");
					}
				} else {
					if (CommonUtil.equalsIgnoreCase(displayType, "block")) {
						html.append("<label class=\"lblRadio");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
						else {html.append("En");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
//						else {html.append("En");}
						html.append(" block");
						if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
						html.append("\"");
						if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
						html.append("><input type=\"radio\" id=\""+name+"_"+i+"\" name=\""+name+"\" value=\""+ds.getValue(i, "COMMON_CODE")+"\" class=\"rdo");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
						else {html.append("En");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
//						else {html.append("En");}
						if (CommonUtil.isNotBlank(inputClassName)) {html.append(" "+inputClassName+"");}
						html.append("\"");
						if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
						if (CommonUtil.equals(ds.getValue(i, "COMMON_CODE"), selectedValue)) {html.append(" checked");}
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
						if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
						if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

						html.append("/>"+ds.getValue(i, "DESCRIPTION_"+defaultLangCode.toUpperCase())+"</label>");
					} else {
						html.append("<label class=\"lblRadio");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
						else {html.append("En");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
//						else {html.append("En");}
						if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
						html.append("\"");
						if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
						html.append("><input type=\"radio\" id=\""+name+"_"+i+"\" name=\""+name+"\" value=\""+ds.getValue(i, "COMMON_CODE")+"\" class=\"rdo");
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
						else {html.append("En");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append("Dis");}
//						else {html.append("En");}
						if (CommonUtil.isNotBlank(inputClassName)) {html.append(" "+inputClassName+"");}
						html.append("\"");
						if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
						if (CommonUtil.equals(ds.getValue(i, "COMMON_CODE"), selectedValue)) {html.append(" checked");}
						if (CommonUtil.containsIgnoreCase(disabledValue, ds.getValue(i, "COMMON_CODE")) || CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
//						if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" disabled");}
						if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
						if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
						if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

						html.append("/>"+ds.getValue(i, "DESCRIPTION_"+defaultLangCode.toUpperCase())+"</label>");
					}
				}
			}

			jspWriter.print(html.toString());
			initialise();
		} catch (Exception ex) {
			logger.error(ex);
		}

		return SKIP_BODY;
	}
	/*!
	 * getter / setter
	 */
	@SuppressWarnings("rawtypes")
	private void initialise() throws Exception {
		Class cls = getClass();
		Field fields[] = cls.getDeclaredFields();
		for (int i=0; i<fields.length; i++) {
			fields[i].set(this, "");
		}
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDisabledValue() {
		return disabledValue;
	}

	public void setDisabledValue(String disabledValue) {
		this.disabledValue = disabledValue;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getLabelClassName() {
		return labelClassName;
	}

	public void setLabelClassName(String labelClassName) {
		this.labelClassName = labelClassName;
	}

	public String getInputClassName() {
		return inputClassName;
	}

	public void setInputClassName(String inputClassName) {
		this.inputClassName = inputClassName;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getIsBootstrap() {
		return isBootstrap;
	}

	public void setIsBootstrap(String isBootstrap) {
		this.isBootstrap = isBootstrap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}