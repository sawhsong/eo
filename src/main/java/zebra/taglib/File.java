package zebra.taglib;

import java.lang.reflect.Field;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class File extends TaglibSupport {
	private String id = "";
	private String name = "";
	private String className = "";
	private String value = "";
	private String style = "";
	private String script = "";
	private String title = "";
	private String placeHolder = "";
	private String checkName = "";
	private String maxlength = "";
	private String minlength = "";
	private String checkFlag = "";
	private String option = "";
	private String options = "";
	private String attribute = "";
	private String status = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession httpSession = pageContext.getSession();
			String langCode = (String)httpSession.getAttribute("langCode");
			StringBuffer html = new StringBuffer();
			String classNamePrefix = "file", scriptStr = "", attrStr = "";
			String scripts[], eventFunc[], attrs[], attr[];

			/*!
			 * set vlaues
			 */
			if (CommonUtil.containsIgnoreCase(status, "disabled")) {
				options += (CommonUtil.isBlank(options)) ? "readonly" : " readonly";
			} else if (CommonUtil.containsIgnoreCase(status, "display")) {
				options += (CommonUtil.isBlank(options)) ? "readonly" : " readonly";
			} else {
			}
			className = (CommonUtil.isBlank(className)) ? classNamePrefix : classNamePrefix+" "+className;

			title = CommonUtil.containsIgnoreCase(title, ".") ? getMessage(title, langCode) : title;
			placeHolder = CommonUtil.containsIgnoreCase(placeHolder, ".") ? getMessage(placeHolder, langCode) : placeHolder;
			checkName = CommonUtil.containsIgnoreCase(checkName, ".") ? getMessage(checkName, langCode) : checkName;

			if (CommonUtil.isNotBlank(script)) {
				scripts = CommonUtil.split(script, ";");
				for (int i=0; i<scripts.length; i++) {
					eventFunc = CommonUtil.split(scripts[i], ":");
					scriptStr += " "+eventFunc[0]+"=\""+eventFunc[1]+"\"";
				}
			}

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
			html.append("<input type=\"file\"");
			html.append(" id=\""+CommonUtil.nvl(id, name)+"\"");
			html.append(" name=\""+name+"\"");
			html.append(" value=\""+value+"\"");

			if (CommonUtil.isNotBlank(className)) {html.append(" class=\""+className+"\"");}
			if (CommonUtil.isNotBlank(style)) {html.append(" style=\""+style+"\"");}
			if (CommonUtil.isNotBlank(scriptStr)) {html.append(" "+scriptStr+"");}
			if (CommonUtil.isNotBlank(title)) {html.append(" title=\""+title+"\"");}
			if (CommonUtil.isNotBlank(placeHolder)) {html.append(" placeholder=\""+placeHolder+"\"");}
			if (CommonUtil.isNotBlank(checkName)) {html.append(" checkName=\""+checkName+"\"");}
			if (CommonUtil.isNotBlank(maxlength)) {html.append(" maxlength=\""+maxlength+"\"");}
			if (CommonUtil.isNotBlank(minlength)) {html.append(" minlength=\""+minlength+"\"");}
			if (CommonUtil.isNotBlank(checkFlag)) {html.append(" checkFlag=\""+checkFlag+"\"");}
			if (CommonUtil.isNotBlank(option)) {html.append(" option=\""+option+"\"");}
			if (CommonUtil.isNotBlank(attrStr)) {html.append(" "+attrStr+"");}
			if (CommonUtil.isNotBlank(options)) {html.append(" "+options);}

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

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getMinlength() {
		return minlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}