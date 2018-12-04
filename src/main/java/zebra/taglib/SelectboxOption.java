package zebra.taglib;

import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;

public class SelectboxOption extends TaglibSupport {
	private String value = "";
	private String text = "";
	private String attributes = "";
	private String isSelected = "";
	private String isDisabled = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			StringBuffer html = new StringBuffer();
			String attrString = "";
			String attrs[], keyVal[];

			if (CommonUtil.isNotBlank(attributes)) {
				attrs = CommonUtil.split(attributes, ";");
				for (int i=0; i<attrs.length; i++) {
					keyVal = CommonUtil.split(attrs[i], ":");
					attrString += " "+keyVal[0]+"=\""+keyVal[1]+"\"";
				}
			}

			html.append("<option value=\""+value+"\"");

			if (CommonUtil.isNotBlank(attrString)) {html.append(" "+attrString+"");}
			if (CommonUtil.toBoolean(isSelected)) {html.append(" selected");}
			if (CommonUtil.toBoolean(isDisabled)) {html.append(" disabled");}

			html.append(">"+text+"");
			html.append("</option>\n");

			jspWriter.print(html.toString());
		} catch (Exception ex) {
			logger.error(ex);
		}

		return SKIP_BODY;
	}
	/*!
	 * getter / setter
	 */
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

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
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
}