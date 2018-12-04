package zebra.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import zebra.base.TaglibSupport;
import zebra.util.CommonUtil;
import zebra.util.ConfigUtil;

public class Pagination extends TaglibSupport {
	private int totalRows = 0;
	private int maxRowsPerPage = 0;
	private int pageNumsPerPage = 0;
	private int currentPage = 0;
	private String formId = "";
	private String formAction = "";
	private String script = "";

	public int doStartTag() {
		try {
			JspWriter jspWriter = pageContext.getOut();
			HttpSession session = pageContext.getSession();
			ServletRequest request = pageContext.getRequest();
			StringBuffer rtnString = new StringBuffer();
			String className = "", readOnly = "", event = "", jsParamString = "";
			String arrMaxRowsPerPageSelect[] = CommonUtil.splitWithTrim(ConfigUtil.getProperty("view.data.maxRowsPerPage"), ConfigUtil.getProperty("delimiter.data"));
			int maxRowsPerPage = (getMaxRowsPerPage() <= 0) ? CommonUtil.toInt(CommonUtil.nvl(request.getParameter("selMaxRowsPerPageSelectForPagenation"))) : getMaxRowsPerPage();
			int pageNumsPerPage = (getPageNumsPerPage() <= 0) ? CommonUtil.toInt(CommonUtil.nvl((String)session.getAttribute("pageNumsPerPage"))) : getPageNumsPerPage();

			if (maxRowsPerPage <= 0) {
				maxRowsPerPage = CommonUtil.toInt(CommonUtil.nvl((String)session.getAttribute("maxRowsPerPage")));
			}

			int viewPageCount = 1;
			int totalPages = ((getTotalRows() - 1) / maxRowsPerPage) + 1;
			int totalPos = 0, currentPos = 0, prevPage = 0, nextPage = 0, temp = 0;

			totalPages = (totalPages == 0) ? 1 : totalPages;
			currentPage = CommonUtil.toInt(CommonUtil.nvl(request.getParameter("txtCurrentPageForPagination"), "1"));
			if (currentPage <= 0) {
				currentPage = 1;
			}
			currentPage = (currentPage > totalPages) ? totalPages : currentPage;

			totalPos = (int)((totalPages - 1) / pageNumsPerPage) + 1;
			currentPos = (int)((currentPage - 1) / pageNumsPerPage) + 1;

			if (currentPos == totalPos) {
				viewPageCount = ((totalPages % pageNumsPerPage) == 0) ? pageNumsPerPage : (totalPages % pageNumsPerPage);
			} else {
				viewPageCount = pageNumsPerPage;
			}

			jsParamString = "'"+CommonUtil.nvl(getFormId())+"', '"+CommonUtil.nvl(getFormAction())+"', '"+CommonUtil.nvl(getScript())+"'";

			rtnString.append("<div id=\"divPagination\">");
			rtnString.append("<table id=\"tblPagination\">");
			rtnString.append("<tr>");
			rtnString.append("<td class=\"tdPaginationLeft\"></td>");
			rtnString.append("<td class=\"tdPaginationCenter\">");
			rtnString.append("<ul id=\"ulPagination\">");
			if (currentPage > 1) {
				rtnString.append("<li class=\"liPaginationButton\">");
				rtnString.append("<a class=\"aPaginationButtonEn\" onclick=\"$.nony.goPageForPagination("+jsParamString+", '1')\" title=\"First Page\">");
				rtnString.append("<i class=\"fa fa-angle-double-left\"></i>");
				rtnString.append("</a>");
				rtnString.append("</li>");
			} else {
//				rtnString.append("<li class=\"liPaginationButton disabled\">");
//				rtnString.append("<a class=\"aPaginationButtonDis\" title=\"First Page\">");
//				rtnString.append("<i class=\"fa fa-angle-double-left\"></i>");
//				rtnString.append("</a>");
//				rtnString.append("</li>");
			}

			prevPage = currentPage - 1;

			if (prevPage >= 1) {
				rtnString.append("<li class=\"liPaginationButton\">");
				rtnString.append("<a class=\"aPaginationButtonEn\" onclick=\"$.nony.goPageForPagination("+jsParamString+", '"+prevPage+"')\" title=\"Previous Page\">");
				rtnString.append("<i class=\"fa fa-angle-left\"></i>");
				rtnString.append("</a>");
				rtnString.append("</li>");
			} else {
//				rtnString.append("<li class=\"liPaginationButton disabled\">");
//				rtnString.append("<a class=\"aPaginationButtonDis\" title=\"Previous Page\">");
//				rtnString.append("<i class=\"fa fa-angle-left\"></i>");
//				rtnString.append("</a>");
//				rtnString.append("</li>");
			}

			for (int i=1; i<=viewPageCount && (currentPos - 1) * pageNumsPerPage + i <= totalPages; i++) {
				temp = (currentPos - 1) * pageNumsPerPage + i;

				if (((currentPos - 1) * pageNumsPerPage + i) == currentPage) {
					rtnString.append("<li class=\"liPaginationButton active\">");
					rtnString.append("<a class=\"aPaginationButtonCurr\">").append(temp).append("</a>");
					rtnString.append("</li>");
				} else {
					rtnString.append("<li class=\"liPaginationButton\">");
					rtnString.append("<a class=\"aPaginationButtonEn\" onclick=\"$.nony.goPageForPagination("+jsParamString+", '"+temp+"')\">").append(temp).append("</a>");
					rtnString.append("</li>");
				}
			}

			nextPage = currentPage + 1;

			if (nextPage <= totalPages) {
				rtnString.append("<li class=\"liPaginationButton\">");
				rtnString.append("<a class=\"aPaginationButtonEn\" onclick=\"$.nony.goPageForPagination("+jsParamString+", '"+nextPage+"')\" title=\"Next Page\">");
				rtnString.append("<i class=\"fa fa-angle-right\"></i>");
				rtnString.append("</a>");
				rtnString.append("</li>");
			} else {
//				rtnString.append("<li class=\"liPaginationButton disabled\">");
//				rtnString.append("<a class=\"aPaginationButtonDis\" title=\"Next Page\">");
//				rtnString.append("<i class=\"fa fa-angle-right\"></i>");
//				rtnString.append("</a>");
//				rtnString.append("</li>");
			}

			if (currentPage < totalPages) {
				rtnString.append("<li class=\"liPaginationButton\">");
				rtnString.append("<a class=\"aPaginationButtonEn\" onclick=\"$.nony.goPageForPagination("+jsParamString+", '"+totalPages+"')\" title=\"Last Page\">");
				rtnString.append("<i class=\"fa fa-angle-double-right\"></i>");
				rtnString.append("</a>");
				rtnString.append("</li>");
			} else {
//				rtnString.append("<li class=\"liPaginationButton disabled\">");
//				rtnString.append("<a class=\"aPaginationButtonDis\" title=\"Last Page\">");
//				rtnString.append("<i class=\"fa fa-angle-double-right\"></i>");
//				rtnString.append("</a>");
//				rtnString.append("</li>");
			}

			int rowCountForDisplay = currentPage * maxRowsPerPage;

			rtnString.append("</ul>");
			rtnString.append("</td>");
			rtnString.append("<td class=\"tdPaginationRight\">");

			if (getTotalRows() <= 0) {
				readOnly = "readonly=\"readonly\"";
				event = "";
			} else {
				readOnly = "";
				event = "onkeypress=\"$.nony.onKeyPressFortxtCurrentPageForPagination(event, "+jsParamString+", this)";
			}
			rtnString.append("<div id=\"divPaginationDescriptor\">");
			rtnString.append("<div class=\"\">");
			rtnString.append("<input type=\"text\" id=\"txtCurrentPageForPagination\" name=\"txtCurrentPageForPagination\" class=\"txtPagination\" value=\""+currentPage+"\" "+readOnly+" "+event+"\"/>");
			rtnString.append("</div>");
			rtnString.append("<div class=\"paginationDescriptor\">");
			rtnString.append("&nbsp; / "+CommonUtil.toString(totalPages, "#,##0")+" Pages");
			rtnString.append("<span class=\"spanPaginationBreaker\"></span>");

			if (getTotalRows() <= 0) {
				className = "disabled";
				readOnly = "disabled";
				event = "";
			} else {
				className = "";
				readOnly = "";
				event = "onchange=\"$.nony.goPageForPagination("+jsParamString+", '"+currentPage+"')\"";
			}
			rtnString.append("</div>");
			rtnString.append("<div class=\"\">");
			rtnString.append("<select id=\"selMaxRowsPerPageSelectForPagenation\" name=\"selMaxRowsPerPageSelectForPagenation\" class=\"selectpicker "+className+"\" "+readOnly+" "+event+">");

			for (int i=0; i<arrMaxRowsPerPageSelect.length; i++) {
				String selected = (maxRowsPerPage == CommonUtil.toInt(arrMaxRowsPerPageSelect[i])) ? "selected" : "";
				rtnString.append("<option value=\""+arrMaxRowsPerPageSelect[i]+"\" "+selected+">"+arrMaxRowsPerPageSelect[i]+"</option>");
			}
			rtnString.append("</select>");
			rtnString.append("</div>");
			rtnString.append("<div class=\"paginationDescriptor\">&nbsp;Rows");
			rtnString.append("<span class=\"spanPaginationBreaker\"></span>");

			if (getTotalRows() <= 0) {
				rtnString.append(CommonUtil.toString(0, "#,##0")+" - ");
			} else {
				rtnString.append(CommonUtil.toString((((currentPage - 1) * maxRowsPerPage) + 1), "#,##0")+" - ");
			}
			rtnString.append(CommonUtil.toString(((rowCountForDisplay > getTotalRows()) ? getTotalRows() : rowCountForDisplay), "#,##0")+" / "+CommonUtil.getNumberMask(getTotalRows(), "#,##0")+" Items");
			rtnString.append("</div>");
			rtnString.append("</div>");
			rtnString.append("</td>");
			rtnString.append("</tr>");
			rtnString.append("</table>");
			rtnString.append("</div>");

			jspWriter.print(rtnString);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return SKIP_BODY;
	}
	/*!
	 * getter / setter
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getMaxRowsPerPage() {
		return maxRowsPerPage;
	}

	public void setMaxRowsPerPage(int maxRowsPerPage) {
		this.maxRowsPerPage = maxRowsPerPage;
	}

	public int getPageNumsPerPage() {
		return pageNumsPerPage;
	}

	public void setPageNumsPerPage(int pageNumsPerPage) {
		this.pageNumsPerPage = pageNumsPerPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}