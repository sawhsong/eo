package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class Button extends TaglibSupport {
	private String id = "";
	private String caption = "";
	private String type = "";
	private String title = "";
	private String status = "";
	private String script = "";
	private String buttonClass = "";
	private String buttonStyle = "";
	private String iconClass = "";
	private String iconStyle = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String btnClassName = "", iconTag = "";

			// caption & title
			caption = CommonUtil.containsIgnoreCase(caption, ".") ? getMessage(caption, langCode) : caption;
			title = CommonUtil.containsIgnoreCase(title, ".") ? getMessage(title, langCode) : title;
			// type
			if (CommonUtil.containsIgnoreCase(type, "primary")) {btnClassName = "btn btn-primary";}
			else if (CommonUtil.containsIgnoreCase(type, "success")) {btnClassName = "btn btn-success";}
			else if (CommonUtil.containsIgnoreCase(type, "info")) {btnClassName = "btn btn-info";}
			else if (CommonUtil.containsIgnoreCase(type, "warning")) {btnClassName = "btn btn-warning";}
			else if (CommonUtil.containsIgnoreCase(type, "danger")) {btnClassName = "btn btn-danger";}
			else {btnClassName = "btn btn-default";}
			// buttonClass
			if (CommonUtil.isNotBlank(buttonClass)) {btnClassName += " "+buttonClass;}
			// buttonStyle
			if (CommonUtil.isNotBlank(buttonStyle)) {buttonStyle = " style=\""+buttonStyle+"\"";}
			else {buttonStyle = "";}
			// icon
			if (CommonUtil.isNotBlank(iconClass)) {
				if (CommonUtil.isNotBlank(iconStyle)) {iconStyle = " style=\""+iconStyle+"\"";}
				else {iconStyle = "";}

				if (CommonUtil.startsWithIgnoreCase(iconClass, "fa-")) {
					iconTag = "<i class=\"fa fa-lg "+iconClass+"\""+iconStyle+"></i>&nbsp;";
				} else if (CommonUtil.startsWithIgnoreCase(iconClass, "glyphicon-")) {
					iconTag = "<span class=\"glyphicon "+iconClass+"\""+iconStyle+" style=\"font-size:1.1em\"></span>&nbsp;";
				} else {
					iconTag = "<i class=\"fa-lg "+iconClass+"\""+iconStyle+"></i>&nbsp;";
				}
			}

			html.append("<a type=\"button\" id=\""+id+"\" class=\""+btnClassName+"\""+CommonUtil.nvl(buttonStyle)+"");

			if (CommonUtil.isNotBlank(title)) {html.append(" title=\""+title+"\"");}
			if (CommonUtil.isNotBlank(status)) {html.append(" "+status+"");}
			if (CommonUtil.isNotBlank(script)) {
				if (!CommonUtil.containsIgnoreCase(status, "disabled")) {html.append(" onclick=\""+script+"\"");}
			}

			html.append(">");

			if (CommonUtil.isNotBlank(iconTag)) {html.append(iconTag);}

			html.append(" "+caption+"");
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getButtonClass() {
		return buttonClass;
	}

	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}

	public String getButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getIconStyle() {
		return iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}
}