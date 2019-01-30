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
	DataSet assignmentList = (DataSet)pe.getObject("assignmentList");
	DataSet leaveDetail = (DataSet)pe.getObject("leaveDetail");
	DataSet accrualList = (DataSet)pe.getObject("accrualList");
	DataSet typeLookup = (DataSet)pe.getObject("leaveTypeLookup");
	DataSet categoryLookup = (DataSet)pe.getObject("leaveCategoryLookup");
	DataSet durationUnitLookup = (DataSet)pe.getObject("durationUnitLookup");
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
var leaveRequestId = "<%=leaveDetail.getValue("leaveRequestId")%>";
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
			<ui:button id="btnSave" caption="button.com.save" iconClass="fa-save"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblInform sort autosort">
		<caption>Accruals</caption>
		<colgroup>
			<col width="*"/>
			<col width="30%"/>
			<col width="30%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thInform Ct">Accrual Name</th>
				<th class="thInform Ct">Number of Hours</th>
				<th class="thInform Ct">Balance</th>
			</tr>
		</thead>
		<tbody id="tblInformBody">
<%
		if (accrualList != null && accrualList.getRowCnt() > 0) {
			for (int i=0; i<accrualList.getRowCnt(); i++) {
%>
			<tr>
				<td class="tdInform Lt"><%=accrualList.getValue(i, "displayName")%></td>
				<td class="tdInform Rt"><%=CommonUtil.getNumberMask(accrualList.getValue(i, "noOfHours"), "#,##0.00")%></td>
				<td class="tdInform Rt"><%=CommonUtil.getNumberMask(accrualList.getValue(i, "balance"), "#,##0.00")%></td>
			</tr>
<%
			}
		} else {
%>
			<tr>
				<td class="tdInform Ct" colspan="3"><mc:msg key="I001"/></td>
			</tr>
<%
		}
%>
		</tbody>
	</table>
</div>
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
			<col width="16%"/>
			<col width="36%"/>
			<col width="15%"/>
			<col width="33%"/>
		</colgroup>
		<tr>
			<th class="thEdit rt mandatory">Assignment</th>
			<td class="tdEdit">
				<ui:select name="assignment" checkName="Assignment" options="mandatory">
<%
				for (int i=0; i<assignmentList.getRowCnt(); i++) {
					String selected = CommonUtil.equals(leaveDetail.getValue("assignmentId"), assignmentList.getValue(i, "assignmentId")) ? "selected" : "";
%>
					<option value="<%=assignmentList.getValue(i, "assignmentId")%>" <%=selected%>><%=assignmentList.getValue(i, "assignmentName")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<th class="thEdit rt">Status</th>
			<td class="tdEdit">
				<ui:hidden name="status" value="<%=leaveDetail.getValue(\"status\")%>"/>
				<ui:text name="statusDesc" value="<%=leaveDetail.getValue(\"statusDesc\")%>" status="display"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Type</th>
			<td class="tdEdit">
				<ui:select name="type" checkName="Leave Type" options="mandatory">
<%
				for (int i=0; i<typeLookup.getRowCnt(); i++) {
					String selected = CommonUtil.equals(leaveDetail.getValue("leaveType"), typeLookup.getValue(i, "code")) ? "selected" : "";
%>
					<option value="<%=typeLookup.getValue(i, "code")%>" <%=selected%>><%=typeLookup.getValue(i, "meaning")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<th class="thEdit rt mandatory">Category</th>
			<td class="tdEdit">
				<ui:select name="category" checkName="Leave Category" options="mandatory">
<%
				for (int i=0; i<categoryLookup.getRowCnt(); i++) {
					String selected = CommonUtil.equals(leaveDetail.getValue("leaveCategory"), categoryLookup.getValue(i, "code")) ? "selected" : "";
%>
					<option value="<%=categoryLookup.getValue(i, "code")%>" <%=selected%>><%=categoryLookup.getValue(i, "meaning")%></option>
<%
				}
%>
				</ui:select>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Start Date</th>
			<td class="tdEdit">
				<ui:text name="startDate" value="<%=leaveDetail.getValue(\"startDate\")%>" className="Ct hor" style="width:100px" checkName="Start Date" options="mandatory" option="date"/>
				<ui:icon id="icnStartDate" className="fa-calendar hor"/>
			</td>
			<th class="thEdit rt mandatory">End Date</th>
			<td class="tdEdit">
				<ui:text name="endDate" value="<%=leaveDetail.getValue(\"endDate\")%>" className="Ct hor" style="width:100px" checkName="End Date" options="mandatory" option="date"/>
				<ui:icon id="icnEndDate" className="fa-calendar hor"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Duration</th>
			<td class="tdEdit"><ui:text name="duration" value="<%=leaveDetail.getValue(\"duration\")%>" status="spinner" checkName="Leave Duration" options="mandatory"/></td>
			<th class="thEdit rt mandatory">Units</th>
			<td class="tdEdit">
				<ui:select name="durationUnits" checkName="Duration Units" options="mandatory">
<%
				for (int i=0; i<durationUnitLookup.getRowCnt(); i++) {
					String selected = CommonUtil.equals(leaveDetail.getValue("durationUnit"), durationUnitLookup.getValue(i, "code")) ? "selected" : "";
%>
					<option value="<%=durationUnitLookup.getValue(i, "code")%>" <%=selected%>><%=durationUnitLookup.getValue(i, "meaning")%></option>
<%
				}
%>
				</ui:select>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="reason" value="<%=leaveDetail.getValue(\"reason\")%>" style="height:60px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=leaveDetail.getValue(\"submittedDate\")%>" status="display"/></td>
			<th class="thEdit rt">Approver</th>
			<td class="tdEdit"><ui:text name="approver" value="<%=leaveDetail.getValue(\"approveRejectPersonFullName\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Rejected Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="rejectedReason" value="<%=leaveDetail.getValue(\"approveRejectComments\")%>" status="display" style="height:60px;"/></td>
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