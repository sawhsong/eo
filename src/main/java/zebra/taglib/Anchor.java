package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class Anchor extends TaglibSupport {
	private String id = "";
	private String caption = "";
	private String className = "";
	private String style = "";
	private String script = "";
	private String title = "";
	private String attribute = "";
	private String status = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String classNamePrefix = "", attrStr = "";
			String attrs[], attr[];

			/*!
			 * set vlaues
			 */
			if (CommonUtil.containsIgnoreCase(status, "disabled")) {
				classNamePrefix = "aDis";
			} else {
				classNamePrefix = "aEn";
			}
			className = (CommonUtil.isBlank(className)) ? classNamePrefix : classNamePrefix+" "+className;

			caption = CommonUtil.containsIgnoreCase(caption, ".") ? getMessage(caption, langCode) : caption;
			title = CommonUtil.containsIgnoreCase(title, ".") ? getMessage(title, langCode) : title;

			if (CommonUtil.isNotBlank(attribute)) {
				attrs = CommonUtil.split(attribute, ";");
				for (int i=0; i<attrs.length; i++) {
					attr = CommonUtil.split(attrs[i], ":");
					attrStr += " "+attr[0]+"=\""+attr[1]+"\"";
				}
			}

			/*!
			 * render
			 */
			html.append("<a");

			if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}
			if (CommonUtil.isNotBlank(className)) {html.append(" class=\""+className+"\"");}
			if (CommonUtil.isNotBlank(style)) {html.append(" style=\""+style+"\"");}
			if (CommonUtil.isNotBlank(script)) {
				if (!CommonUtil.containsIgnoreCase(status, "disabled")) {html.append(" onclick=\""+script+"\"");}
			}
			if (CommonUtil.isNotBlank(title)) {html.append(" title=\""+title+"\"");}
			if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}
			if (CommonUtil.isNotBlank(status)) {html.append(" "+status+"");}

			html.append(">");
			html.append(caption);
			html.append("</a>");

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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}