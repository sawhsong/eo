package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibBodySupport;
import zebra.util.CommonUtil;

public class Selectbox extends TaglibBodySupport {
	private String name = "";
	private String id = "";
	private String className = "";
	private String status = "";
	private String script = "";
	private String style = "";
	private String isMultiple = "";
	private String isBootstrap = "";
	private String checkName = "";
	private String options = "";	// for data validator
	private String attribute = "";

	public int doAfterBody() {
		try {
			bodyContent = getBodyContent();
			JspWriter jspWriter = bodyContent.getEnclosingWriter();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String classString = "", attrStr = "", attrs[], attr[];

			checkName = CommonUtil.containsIgnoreCase(checkName, ".") ? getMessage(checkName, langCode) : checkName;

			if (CommonUtil.isNotBlank(attribute)) {
				attrs = CommonUtil.split(attribute, ";");
				for (int i=0; i<attrs.length; i++) {
					attr = CommonUtil.split(attrs[i], ":");
					attrStr += " "+attr[0]+"=\""+attr[1]+"\"";
				}
			}

			html.append("<select");
			html.append(" id=\""+CommonUtil.nvl(id, name)+"\"");
			html.append(" name=\""+name+"\"");

			if (CommonUtil.isNotBlank(style)) {html.append(" style=\""+style+"\"");}
			if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}
			if (CommonUtil.isNotBlank(script)) {html.append(" onchange=\""+script+"\"");}
			if (CommonUtil.toBoolean(isMultiple)) {html.append(" multiple=\"multiple\"");}
			if (CommonUtil.equalsIgnoreCase(status, "disabled")) {html.append(" "+status);}
			if (CommonUtil.isNotBlank(checkName)) {html.append(" checkName=\""+checkName+"\"");}
			if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}
			// css class
			if (!CommonUtil.toBoolean(isBootstrap, true)) {
				if (CommonUtil.toBoolean(isMultiple)) {classString = "selMulti";}
				else {classString = "selSingle";}

				if (CommonUtil.equalsIgnoreCase(status, "disabled")) {classString += "Dis";}
				else {classString += "En";}
			} else {
				classString = "bootstrapSelect";
			}

			html.append(" class=\""+classString+" "+CommonUtil.nvl(className)+"\"");
			html.append(">\n");

			html.append(bodyContent.getString());

			html.append("</select>\n");

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	public String getIsBootstrap() {
		return isBootstrap;
	}

	public void setIsBootstrap(String isBootstrap) {
		this.isBootstrap = isBootstrap;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
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