package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class Radio extends TaglibSupport {
	private String name = "";
	private String value = "";
	private String text = "";
	private String id = "";
	private String isSelected = "";
	private String isDisabled = "";
	private String script = "";
	private String labelClassName = "";
	private String inputClassName = "";
	private String labelStyle = "";
	private String inputStyle = "";
	private String displayType = "";	// inline / block
	private String isBootstrap = "";
	private String status = "";
	private String options = "";	// for data validator
	private String attribute = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String disabledString = "", classSuffix = "", attrStr = "", attrs[], attr[];

			text = CommonUtil.containsIgnoreCase(text, ".") ? getMessage(text, langCode) : text;

			if (CommonUtil.isNotBlank(attribute)) {
				attrs = CommonUtil.split(attribute, ";");
				for (int i=0; i<attrs.length; i++) {
					attr = CommonUtil.split(attrs[i], ":");
					attrStr += " "+attr[0]+"=\""+attr[1]+"\"";
				}
			}

			if (CommonUtil.toBoolean(isBootstrap, true)) {
				if (CommonUtil.toBoolean(isDisabled) || CommonUtil.equalsIgnoreCase(status, "disabled")) {
					disabledString = " disabled";
				}

				if (CommonUtil.equalsIgnoreCase(displayType, "block")) {
					html.append("<div class=\"radio"+disabledString+"\"><label");

					if (CommonUtil.isNotBlank(labelClassName)) {html.append(" class=\""+labelClassName+"\"");}
					if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
					html.append("><input type=\"radio\" name=\""+name+"\" value=\""+value+"\"");
					if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}
					if (CommonUtil.isNotBlank(disabledString)) {html.append(" "+disabledString);}
					if (CommonUtil.isNotBlank(inputClassName)) {html.append(" class=\""+inputClassName+"\"");}
					if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
					if (CommonUtil.toBoolean(isSelected)) {html.append(" checked");}
					if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
					if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
					if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

					html.append("/>"+text+"</label></div>");
				} else {
					html.append("<label class=\"radio-inline"+disabledString);
					if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
					html.append("\"");
					if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
					html.append("><input type=\"radio\" name=\""+name+"\" value=\""+value+"\"");
					if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}
					if (CommonUtil.isNotBlank(disabledString)) {html.append(" "+disabledString);}
					if (CommonUtil.isNotBlank(inputClassName)) {html.append(" class=\""+inputClassName+"\"");}
					if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
					if (CommonUtil.toBoolean(isSelected)) {html.append(" checked");}
					if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
					if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
					if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

					html.append("/>"+text+"</label>");
				}
			} else {
				if (CommonUtil.toBoolean(isDisabled) || CommonUtil.equalsIgnoreCase(status, "disabled")) {
					classSuffix = "Dis";
					disabledString = " disabled";
				} else {
					classSuffix = "En";
				}

				if (CommonUtil.equalsIgnoreCase(displayType, "block")) {
					html.append("<label class=\"lblRadio"+classSuffix);
					html.append(" block");
					if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
					html.append("\"");
					if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
					html.append("><input type=\"radio\" name=\""+name+"\" value=\""+value+"\" class=\"rdo"+classSuffix);
					if (CommonUtil.isNotBlank(inputClassName)) {html.append(" "+inputClassName+"");}
					html.append("\"");
					if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}
					if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
					if (CommonUtil.isNotBlank(disabledString)) {html.append(" "+disabledString);}
					if (CommonUtil.toBoolean(isSelected)) {html.append(" checked");}
					if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
					if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
					if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

					html.append("/>"+text+"</label>");
				} else {
					html.append("<label class=\"lblRadio"+classSuffix);
					if (CommonUtil.isNotBlank(labelClassName)) {html.append(" "+labelClassName+"");}
					html.append("\"");
					if (CommonUtil.isNotBlank(labelStyle)) {html.append(" style=\""+labelStyle+"\"");}
					html.append("><input type=\"radio\" name=\""+name+"\" value=\""+value+"\" class=\"rdo"+classSuffix);
					if (CommonUtil.isNotBlank(inputClassName)) {html.append(" "+inputClassName+"");}
					html.append("\"");
					if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}
					if (CommonUtil.isNotBlank(inputStyle)) {html.append(" style=\""+inputStyle+"\"");}
					if (CommonUtil.isNotBlank(disabledString)) {html.append(" "+disabledString);}
					if (CommonUtil.toBoolean(isSelected)) {html.append(" checked");}
					if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
					if (CommonUtil.isNotBlank(script)) {html.append(" onclick=\""+script+"\"");}
					if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

					html.append("/>"+text+"</label>");
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}