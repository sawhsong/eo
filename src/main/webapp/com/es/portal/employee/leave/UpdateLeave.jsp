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
	String durationUnit = (String)pe.getObject("durationUnit");
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
.thGrid {border-bottom:0px;}
.tblGrid tr:not(.default):not(.active):not(.info):not(.success):not(.warning):not(.danger):hover td {background:#FFFFFF;}
#liDummy {display:none;}
#divDataArea.areaContainerPopup {padding-top:0px;}
.dummyDetail {list-style:none;}
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
			<ui:button id="btnSave" caption="Apply" iconClass="fa-save"/>
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

		</tbody>
	</table>
</div>
<div id="divMaster" class="areaContainerPopup">
	<table class="tblEdit">
		<colgroup>
			<col width="15%"/>
			<col width="40%"/>
			<col width="15%"/>
			<col width="30%"/>
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
			<th class="thEdit rt mandatory">Duration</th>
			<td class="tdEdit">
				<ui:text name="duration" value="<%=leaveDetail.getValue(\"duration\")%>" className="Ct hor" status="disabled" checkName="Leave Duration" options="mandatory" style="width:70px;font-weight:bold;"/>
				<div class="horGap10" style="padding:6px 8px 6px 0px;font-weight:bold;"><%=durationUnit%></div>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Type</th>
			<td class="tdEdit">
				<ui:ccselect codeType="LEAVE_TYPE" name="type" selectedValue="<%=leaveDetail.getValue(\"leaveType\")%>" checkName="Leave Type" options="mandatory"/>
			</td>
			<th class="thEdit rt mandatory">Category</th>
			<td class="tdEdit">
				<ui:ccselect codeType="LEAVE_CATEGORY" name="category" selectedValue="<%=leaveDetail.getValue(\"leaveCategory\")%>" checkName="Leave Category" options="mandatory"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Period</th>
			<td class="tdEdit">
				<ui:text name="startDate" value="<%=leaveDetail.getValue(\"startDate\")%>" className="Ct hor" style="width:100px" checkName="Start Date" options="mandatory" option="date"/>
				<ui:icon id="icnStartDate" className="fa-calendar hor"/>
				<div class="horGap10" style="padding:6px 8px 6px 0px;">-</div>
				<ui:text name="endDate" value="<%=leaveDetail.getValue(\"endDate\")%>" className="Ct hor" style="width:100px" checkName="End Date" options="mandatory" option="date"/>
				<ui:icon id="icnEndDate" className="fa-calendar hor"/>
			</td>
			<th class="thEdit rt">Status</th>
			<td class="tdEdit">
				<ui:hidden name="status" value="<%=leaveDetail.getValue(\"status\")%>"/>
				<ui:text name="statusDesc" value="<%=leaveDetail.getValue(\"statusDesc\")%>" status="display"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="reason" value="<%=leaveDetail.getValue(\"reason\")%>" style="height:40px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=leaveDetail.getValue(\"submittedDate\")%>" status="display"/></td>
			<th class="thEdit rt">Approver</th>
			<td class="tdEdit"><ui:text name="approver" value="<%=leaveDetail.getValue(\"approveRejectPersonFullName\")%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Rejected Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="rejectedReason" value="<%=leaveDetail.getValue(\"approveRejectComments\")%>" status="display" style="height:40px;"/></td>
		</tr>
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
	<table id="tblGrid" class="tblGrid">
		<colgroup>
			<col width="20%"/>
			<col width="20%"/>
			<col width="20%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid">Date</th>
				<th class="thGrid">Type</th>
				<th class="thGrid">Hours</th>
				<th class="thGrid">Description</th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td colspan="4" style="padding:0px;border-top:0px"><ul id="ulDetailHolder"></ul></td>
			</tr>
		</tbody>
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
<li id="liDummy" class="dummyDetail">
	<table class="tblGrid" style="border:0px">
		<colgroup>
			<col width="20%"/>
			<col width="20%"/>
			<col width="20%"/>
			<col width="*"/>
		</colgroup>
		<tr class="noBorderAll">
			<td class="tdGrid Ct">
				<ui:text name="date" className="Ct" status="display"/>
				<ui:hidden name="dateType"/>
			</td>
			<td class="tdGrid Ct"><ui:text name="dayOfWeek" className="Lt" status="display"/></td>
			<td class="tdGrid Ct"><ui:text name="hours" className="Ct numeric"/></td>
			<td class="tdGrid Lt"><ui:text name="description" className="Lt"/></td>
		</tr>
	</table>
</li>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>