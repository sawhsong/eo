package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class Hidden extends TaglibSupport {
	private String id = "";
	private String name = "";
	private String className = "";
	private String value = "";
	private String style = "";
	private String checkName = "";
	private String attribute = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String attrStr = "", attrs[], attr[];

			if (CommonUtil.isNotBlank(attribute)) {
				attrs = CommonUtil.split(attribute, ";");
				for (int i=0; i<attrs.length; i++) {
					attr = CommonUtil.split(attrs[i], ":");
					attrStr += " "+attr[0]+"=\""+attr[1]+"\"";
				}
			}

			checkName = CommonUtil.containsIgnoreCase(checkName, ".") ? getMessage(checkName, langCode) : checkName;

			html.append("<input type=\"hidden\"");
			html.append(" id=\""+CommonUtil.nvl(id, name)+"\"");
			html.append(" name=\""+name+"\"");

			if (CommonUtil.isNotBlank(className)) {html.append(" class=\""+className+"\"");}
			if (CommonUtil.isNotBlank(value)) {html.append(" value=\""+value+"\"");}
			if (CommonUtil.isNotBlank(checkName)) {html.append(" checkName=\""+checkName+"\"");}
			if (CommonUtil.isNotBlank(style)) {html.append(" style=\""+style+"\"");}
			if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}

			html.append("/>");

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}