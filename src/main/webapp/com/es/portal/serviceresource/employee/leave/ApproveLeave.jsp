<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsRequest = pe.getRequestDataSet();
	DataSet leaveDetail = (DataSet)pe.getObject("leaveDetail");
	DataSet accrualList = (DataSet)pe.getObject("accrualList");
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
#divBodyCenter {padding:30px 100px 30px 100px;}
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/com/es/portal/common/include/headerForServiceResource.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight"></div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerServiceResource">
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
		<tbody>
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
<div id="divScrollablePanel">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainerServiceResource">
	<table class="tblEdit">
		<colgroup>
			<col width="18%"/>
			<col width="32%"/>
			<col width="18%"/>
			<col width="32%"/>
		</colgroup>
		<tr>
			<th class="thEdit rt">Assignment Name</th>
			<td class="tdEdit">
				<ui:hidden name="leaveRequestId" value="<%=leaveDetail.getValue(\"leaveRequestId\")%>"/>
				<ui:text name="assignmentName" value="<%=leaveDetail.getValue(\"assignmentName\")%>" status="display"/>
			</td>
			<th class="thEdit rt">Status</th>
			<td class="tdEdit"><ui:text name="statusDesc" value="<%=leaveDetail.getValue(\"statusDesc\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Type</th>
			<td class="tdEdit"><ui:text name="typeDesc" value="<%=leaveDetail.getValue(\"leaveTypeDesc\")%>" status="display"/></td>
			<th class="thEdit rt">Category</th>
			<td class="tdEdit"><ui:text name="categoryDesc" value="<%=leaveDetail.getValue(\"leaveCategoryDesc\")%>" status="display"/></td>
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
			<td class="tdEdit"><ui:text name="durationUnitDesc" value="<%=leaveDetail.getValue(\"durationUnitDesc\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="reason" value="<%=leaveDetail.getValue(\"reason\")%>" status="display" style="height:60px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=leaveDetail.getValue(\"submittedDate\")%>" status="display"/></td>
			<th class="thEdit rt">Approver</th>
			<td class="tdEdit">
				<ui:hidden name="approverId" value="<%=leaveDetail.getValue(\"approveRejectPersonId\")%>"/>
				<ui:text name="approverName" value="<%=leaveDetail.getValue(\"approveRejectPersonFullName\")%>" status="display"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt">Rejected Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="approveRejectComments" value="<%=leaveDetail.getValue(\"approveRejectComments\")%>" style="height:60px;"/></td>
		</tr>
	</table>
</div>
<div id="divPagingArea"></div>
<div id="divButtonAreaBottom" class="areaContainerServiceResource">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaCenter">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnApprove" caption="Approve" iconClass="fa-check"/>
			<ui:button id="btnReject" caption="Reject" iconClass="fa-reply-all"/>
		</ui:buttonGroup>
	</div>
	<div id="divButtonAreaRight"></div>
</div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
</div>
</div>
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/com/es/portal/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/com/es/portal/common/include/footer.jsp"%></div>
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