package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class TabList extends TaglibSupport {
	private String caption = "";
	private String title = "";
	private String useAutoScript = "";
	private String script = "";
	private String isActive = "";
	private String isClickable = "";
	private String iconClass = "";
	private String iconPosition = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String className = "", anchorHref = "", iconTag = "", anchorHtml = "";
			String autoAction = "commonJs.changeTabSelection(this);";

			if (CommonUtil.isBlank(isClickable) || CommonUtil.toBoolean(isClickable)) {
				if (CommonUtil.toBoolean(isActive)) {
					className = " active";
				}
				anchorHref = " href=\"#\"";
				script = " "+CommonUtil.nvl(script);
			} else {
				if (CommonUtil.toBoolean(isActive)) {
					className = " active disabled";
				} else {
					className = " disabled";
				}
			}

			if (CommonUtil.equalsIgnoreCase(useAutoScript, "false") || CommonUtil.equalsIgnoreCase(useAutoScript, "no")) {
				autoAction = "";
			}

			caption = (CommonUtil.containsIgnoreCase(caption, ".")) ? getMessage(caption, langCode) : caption;
			title = (CommonUtil.containsIgnoreCase(title, ".")) ? getMessage(title, langCode) : title;

			if (CommonUtil.isNotBlank(getIconClass())) {
				if (CommonUtil.startsWithIgnoreCase(iconClass, "fa-")) {
					iconTag = "<i class=\"fa fa-lg "+iconClass+"\"></i>";
				} else if (CommonUtil.startsWithIgnoreCase(iconClass, "glyphicon-")) {
					iconTag = "<span class=\"glyphicon "+iconClass+"\" style=\"font-size:1.1em\"></span>";
				} else {
					iconTag = "<i class=\"fa-lg "+iconClass+"\"></i>&nbsp;";
				}

				if (CommonUtil.equalsIgnoreCase(CommonUtil.deleteWhitespace(getIconPosition()), "left")) {
					anchorHtml = iconTag+"&nbsp;"+caption;
				} else {
					anchorHtml = caption+"&nbsp;"+iconTag;
				}
			} else {
				anchorHtml = caption;
			}

			html.append("<li id=\"li"+CommonUtil.replace(caption, " ", "")+"\" class=\"tabList"+className+"\">");
			html.append("<a"+anchorHref+" onclick=\""+autoAction+CommonUtil.nvl(script)+"\" title=\""+CommonUtil.nvl(title)+"\">");
			html.append(anchorHtml);
			html.append("</a>");
			html.append("</li>");

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

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUseAutoScript() {
		return useAutoScript;
	}

	public void setUseAutoScript(String useAutoScript) {
		this.useAutoScript = useAutoScript;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsClickable() {
		return isClickable;
	}

	public void setIsClickable(String isClickable) {
		this.isClickable = isClickable;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getIconPosition() {
		return iconPosition;
	}

	public void setIconPosition(String iconPosition) {
		this.iconPosition = iconPosition;
	}
}