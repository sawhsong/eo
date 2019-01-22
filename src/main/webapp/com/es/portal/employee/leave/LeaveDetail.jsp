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
	DataSet leaveDetail = (DataSet)pe.getObject("leaveDetail");
	String status = leaveDetail.getValue("status");
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
<%
		if (CommonUtil.isIn(status, "SA", "RE")) {
%>
			<ui:button id="btnEdit" caption="button.com.edit" iconClass="fa-edit"/>
<%
		}
%>
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
			<th class="thEdit rt">Assignment Number</th>
			<td class="tdEdit"><ui:text name="assignmentNumber" value="<%=leaveDetail.getValue(\"assignmentNumber\")%>" status="display"/></td>
			<th class="thEdit rt">Status</th>
			<td class="tdEdit"><ui:text name="status" value="<%=leaveDetail.getValue(\"statusDesc\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Type</th>
			<td class="tdEdit"><ui:text name="type" value="<%=leaveDetail.getValue(\"leaveTypeDesc\")%>" status="display"/></td>
			<th class="thEdit rt">Category</th>
			<td class="tdEdit"><ui:text name="category" value="<%=leaveDetail.getValue(\"leaveCategoryDesc\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Start Date</th>
			<td class="tdEdit"><ui:text name="startDate" value="<%=leaveDetail.getValue(\"startDate\")%>" status="display"/></td>
			<th class="thEdit rt">End Date</th>
			<td class="tdEdit"><ui:text name="endDate" value="<%=leaveDetail.getValue(\"endDate\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Duration</th>
			<td class="tdEdit"><ui:text name="duration" value="<%=leaveDetail.getValue(\"duration\")%>" status="display"/></td>
			<th class="thEdit rt">Units</th>
			<td class="tdEdit"><ui:text name="durationUnits" value="<%=leaveDetail.getValue(\"durationUnitDesc\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="reason" value="<%=leaveDetail.getValue(\"reason\")%>" status="display" style="height:40px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=leaveDetail.getValue(\"submittedDate\")%>" status="display"/></td>
			<th class="thEdit rt">Approver</th>
			<td class="tdEdit"><ui:text name="approver" value="<%=leaveDetail.getValue(\"approverName\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Rejected Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="rejectedReason" value="<%=leaveDetail.getValue(\"rejectedReason\")%>" status="display" style="height:40px;"/></td>
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