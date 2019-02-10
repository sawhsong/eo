<%/************************************************************************************************
* Description
* 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	String dateFormat = ConfigUtil.getProperty("format.date.java");
	String personName = (String)pe.getObject("personName");
	String timeFormat = "HH:mm:ss";
	String defaultDate = CommonUtil.getSysdate(dateFormat);
	String defaultDateTime = CommonUtil.getSysdate(dateFormat+" "+timeFormat);
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconEO.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCssJs.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
var expenseClaimId = "-1";
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divPopupWindowHolder">
<div id="divFixedPanelPopup">
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainerPopup">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnSave" caption="Apply" iconClass="fa-save"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea"></div>
<%/************************************************************************************************
* End of fixed panel
************************************************************************************************/%>
<div class="breaker"></div>
</div>
<div id="divScrollablePanelPopup">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainerPopup">
	<table class="tblEdit">
		<colgroup>
			<col width="18%"/>
			<col width="32%"/>
			<col width="18%"/>
			<col width="32%"/>
		</colgroup>
		<tr>
			<th class="thEdit rt mandatory">Date</th>
			<td class="tdEdit">
				<ui:text name="dateOfClaim" value="<%=defaultDate%>" className="Ct hor" style="width:100px" checkName="Date" options="mandatory" option="date"/>
				<ui:icon id="icnDateOfClaim" className="fa-calendar hor"/>
			</td>
			<th class="thEdit rt mandatory">Status</th>
			<td class="tdEdit">
				<ui:hidden name="status" value="SA"/>
				<ui:text name="statusDesc" value="In Progress" status="display"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Department</th>
			<td class="tdEdit"><ui:ccselect name="department" codeType="INTERNAL_DEPARTMENT" checkName="Department" options="mandatory"/></td>
			<th class="thEdit rt mandatory">Expense Type</th>
			<td class="tdEdit"><ui:ccselect name="expenseType" codeType="EXPENSE_TYPE" checkName="Expense Type" options="mandatory"/></td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Account Name</th>
			<td class="tdEdit" colspan="3"><ui:text name="accountName" checkName="Account Name" options="mandatory" value="test"/></td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">BSB</th>
			<td class="tdEdit"><ui:text name="bsb" checkName="BSB" attribute="maxlength:6" options="mandatory" value="123456"/></td>
			<th class="thEdit rt mandatory">Account Number</th>
			<td class="tdEdit"><ui:text name="accountNumber" checkName="Account Number" options="mandatory" value="123456789"/></td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Amount</th>
			<td class="tdEdit"><ui:text name="amount" className="Rt numeric" checkName="Amount" options="mandatory" value="5000"/></td>
			<th class="thEdit rt">GST</th>
			<td class="tdEdit"><ui:text name="gst" className="Rt numeric" value="500"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Description</th>
			<td class="tdEdit" colspan="3"><ui:txa name="description" style="height:60px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=defaultDateTime%>" status="display"/></td>
			<th class="thEdit rt">Processed Date</th>
			<td class="tdEdit"><ui:text name="processedDate" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Person Name</th>
			<td class="tdEdit" colspan="3"><ui:text name="personName" value="<%=personName%>" checkName="Person Name" options="mandatory" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">
				Attachment<br/><br/>
				<div id="divButtonAreaRight">
					<ui:button id="btnAddFile" caption="button.com.add" iconClass="fa-plus"/>
				</div>
			</th>
			<td class="tdEdit" colspan="3">
				<div id="divAttachedFile" style="width:100%;height:94px;overflow-y:auto;"></div>
			</td>
		</tr>
	</table>
</div>
<div id="divPagingArea"></div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
</div>
</div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>