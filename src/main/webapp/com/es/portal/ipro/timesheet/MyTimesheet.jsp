<%/************************************************************************************************
* Description - My Timesheet
* 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet assignmentList = (DataSet)pe.getObject("assignmentList");
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
.tblGrid tr:not(.default):not(.active):not(.info):not(.success):not(.warning):not(.danger):hover td {background:#FFFFFF;}
#divDummy {display:none;}
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
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/com/es/portal/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/com/es/portal/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainer">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnSearch" caption="button.com.search" iconClass="fa-search"/>
			<ui:button id="btnClear" caption="button.com.clear" iconClass="fa-refresh"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea" class="areaContainer">
	<table class="tblSearch">
		<caption>Please select a contract assignment, then a timesheet period and click 'Search' button to view a timesheet</caption>
		<colgroup>
			<col width="30%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<td class="tdSearch">
				<label for="assignment" class="lblEn hor mandatory">Contract Assignment</label>
				<div style="float:left;padding-right:4px;">
					<ui:select name="assignment">
<%
					for (int i=0; i<assignmentList.getRowCnt(); i++) {
%>
						<ui:seloption value="<%=assignmentList.getValue(i, \"assignmentId\")%>" text="<%=assignmentList.getValue(i, \"assignmentName\")%>"/>
<%
					}
%>
					</ui:select>
				</div>
			</td>
			<td class="tdSearch">
				<label for="timesheetPeriod" class="lblEn hor mandatory">Timesheet Period</label>
				<div style="float:left;padding-right:4px;">
					<ui:select name="timesheetPeriod">
					</ui:select>
				</div>
			</td>
		</tr>
	</table>
</div>
<div id="divInformArea" class="areaContainer">
	<table id="tblInform" class="tblInform">
		<caption>Selected Contract Assignment</caption>
		<colgroup>
			<col width="9%"/>
			<col width="15%"/>
			<col width="6%"/>
			<col width="18%"/>
			<col width="8%"/>
			<col width="9%"/>
			<col width="7%"/>
			<col width="12%"/>
			<col width="7%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<th class="thInform rt">Contract Assignment Id</th>
			<td class="tdInform"><ui:text name="assignmentNumber" status="display"/></td>
			<th class="thInform rt">Organisation</th>
			<td class="tdInform"><ui:text name="billingOrganisation" status="display"/></td>
			<th class="thInform rt">Assignment Period</th>
			<td class="tdInform"><ui:text name="assignmentPeriod" status="display"/></td>
			<th class="thInform rt">Timesheet Units</th>
			<td class="tdInform">
				<ui:hidden name="timesheetUnits"/>
				<ui:text name="timesheetUnitsDesc" status="display"/>
			</td>
			<th class="thInform rt">Timesheet Period</th>
			<td class="tdInform">
				<ui:hidden name="timesheetStatus"/>
				<ui:text name="timesheetPeriodInfo" status="display"/>
			</td>
		</tr>
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
<div id="divDataArea" class="areaContainer">
	<table id="tblGrid" class="tblGrid">
		<colgroup>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="14%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid">Mon</th>
				<th class="thGrid">Tue</th>
				<th class="thGrid">Wed</th>
				<th class="thGrid">Thu</th>
				<th class="thGrid">Fri</th>
				<th class="thGrid">Sat</th>
				<th class="thGrid">Sun</th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td class="tdGrid Ct" colspan="7"><mc:msg key="I002"/></td>
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
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/com/es/portal/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/com/es/portal/common/include/footer.jsp"%></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
<div id="divDummy" class="dummyDetail">
	<ui:hidden name="workDate" className="ct hor"/>
	<ui:text name="formattedWorkDate" style="width:90px" className="ct hor" status="display"/>
	<ui:text name="totalHours" style="width:90px" className="ct hor numeric" checkName="Total Hours" options="mandatory" option="numeric" maxlength="2" title="Number between 0 and 24"/>
	<ui:button id="btnEdit" caption="Edit" iconClass="fa-edit"/>
</div>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>