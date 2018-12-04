package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibBodySupport;
import zebra.util.CommonUtil;

public class ButtonGroup extends TaglibBodySupport {
	private String id = "";
	private String groupClass = "";
	private String style = "";

	public int doAfterBody() {
		try {
			bodyContent = getBodyContent();
			JspWriter jspWriter = bodyContent.getEnclosingWriter();
			StringBuffer html = new StringBuffer();

			html.append("<div");

			if (CommonUtil.isNotBlank(id)) {html.append(" id=\""+id+"\"");}

			html.append(" class=\"btn-group");
			if (CommonUtil.isNotBlank(groupClass)) {html.append(" "+groupClass);}
			html.append("\"");

			if (CommonUtil.isNotBlank(style)) {html.append(" style=\""+style+"\"");}

			html.append(">");

			html.append(bodyContent.getString());

			html.append("</div>");

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

	public String getGroupClass() {
		return groupClass;
	}

	public void setGroupClass(String groupClass) {
		this.groupClass = groupClass;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}