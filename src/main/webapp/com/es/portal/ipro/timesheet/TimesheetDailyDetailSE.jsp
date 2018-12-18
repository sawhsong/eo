<%/************************************************************************************************
* Description - Timesheet Daily Detail
* 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsRequest = (DataSet)pe.getRequestDataSet();
	DataSet ratesDataSet = (DataSet)pe.getObject("ratesDataSet");
	Date workDate = CommonUtil.toDate(dsRequest.getValue("workDate"), "dd/MM/yyyy");
	String workDateToDisplay = dsRequest.getValue("workDate")+" ("+CommonUtil.toString(workDate, "EEE")+")";
	String totalHoursToDisplay = CommonUtil.getNumberMask(dsRequest.getValue("totalHours"), "#,###");
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
.deleteButton {cursor:pointer;}
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
var workDate = "<%=dsRequest.getValue("workDate")%>";
var totalHours = "<%=dsRequest.getValue("totalHours")%>";
var timesheetUnits = "<%=dsRequest.getValue("timesheetUnits")%>";
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
	<table class="tblInform">
		<caption>
			For Daily Units with Start Time and End Time set, user should enter Start Time, End Time and Break Time and Time Worked columns in 24 hours format.<br/>
			Time worked will denote units as Daily and the system will not allow to enter more than 1 day of work for any given working day.
		</caption>
		<colgroup>
			<col width="13%"/>
			<col width="18%"/>
			<col width="13%"/>
			<col width="12%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<th class="thInform rt">Date Worked</th>
			<td class="tdInform">
				<ui:text name="workDate" className="ct" status="display" value="<%=workDateToDisplay%>"/>
			</td>
			<th class="thInform rt">Total Hours</th>
			<td class="tdInform"><ui:text name="totalHours" className="ct" status="display" value="<%=totalHoursToDisplay%>"/></td>
			<td class="tdInform"></td>
		</tr>
	</table>
</div>
<div class="breaker" style="height:5px;"></div>
<div class="divButtonArea areaContainerPopup">
	<div class="divButtonAreaLeft"></div>
	<div class="divButtonAreaRight">
		<ui:buttonGroup id="subButtonGroup">
			<ui:button id="btnAdd" caption="button.com.add" iconClass="fa-plus"/>
		</ui:buttonGroup>
	</div>
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
			<col width="3%"/>
			<col width="15%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="11%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid"></th>
				<th class="thGrid">Rates</th>
				<th class="thGrid">Start Time(HH:mm)</th>
				<th class="thGrid">End Time(HH:mm)</th>
				<th class="thGrid">Break Time(HH:mm)</th>
				<th class="thGrid">Time Worked</th>
				<th class="thGrid">Description</th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td colspan="7" style="padding:0px;border-top:0px"><ul id="ulTimesheetDetailHolder"></ul></td>
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
			<col width="3%"/>
			<col width="15%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="11%"/>
			<col width="*"/>
		</colgroup>
		<tr class="noBorderAll">
			<th id="thDeleteButton" class="thGrid deleteButton" title="Click to delete row"><ui:icon id="iDeleteButton" className="fa-lg fa-times"/></th>
			<td class="tdGrid Ct">
				<ui:select name="rates">
					<ui:seloption value="" text="==Select=="/>
<%
				for (int i=0; i<ratesDataSet.getRowCnt(); i++) {
%>
					<option value="<%=ratesDataSet.getValue(i, "rateId")%>"><%=ratesDataSet.getValue(i, "rateName")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<td class="tdGrid Ct">
				<ui:select name="startTimeHH">
<%
				for (int i=0; i<24; i++) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
				:
				<ui:select name="startTimeMM">
<%
				for (int i=0; i<60; i+=15) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<td class="tdGrid Ct">
				<ui:select name="endTimeHH">
<%
				for (int i=0; i<24; i++) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
				:
				<ui:select name="endTimeMM">
<%
				for (int i=0; i<60; i+=15) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<td class="tdGrid Ct">
				<ui:select name="nonWorkedTimeHH">
<%
				for (int i=0; i<24; i++) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
				:
				<ui:select name="nonWorkedTimeMM">
<%
				for (int i=0; i<60; i+=15) {
%>
					<option value="<%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%>"><%=CommonUtil.leftPad(CommonUtil.toString(i), 2, "0")%></option>
<%
				}
%>
				</ui:select>
			</td>
			<td class="tdGrid Ct"><ui:text name="hours" className="ct numeric" option="numeric" maxlength="2"/></td>
			<td class="tdGrid Ct"><ui:text name="description"/></td>
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