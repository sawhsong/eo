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
	String dateFormat = ConfigUtil.getProperty("format.date.java");
	String timeFormat = "HH:mm:ss";
	String requestDate = CommonUtil.getSysdate(dateFormat);
	String requestTime = CommonUtil.getSysdate(timeFormat);
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
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th class="thEdit rt">Type</th>
			<td class="tdEdit"><ui:text name="type"/></td>
			<th class="thEdit rt">Category</th>
			<td class="tdEdit"><ui:text name="category"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Start Date</th>
			<td class="tdEdit">
				<ui:text name="startDate" className="Ct hor" style="width:100px" checkName="Start Date" option="date"/>
				<ui:icon id="icnStartDate" className="fa-calendar hor"/>
			</td>
			<th class="thEdit rt">End Date</th>
			<td class="tdEdit">
				<ui:text name="endDate" className="Ct hor" style="width:100px" checkName="End Date" option="date"/>
				<ui:icon id="icnEndDate" className="fa-calendar hor"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt">Duration</th>
			<td class="tdEdit"><ui:text name="duration" status="spinner"/></td>
			<th class="thEdit rt">Units</th>
			<td class="tdEdit"><ui:text name="units"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:text name="reason"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Request Date</th>
			<td class="tdEdit"><ui:text name="requestDate" value="<%=requestDate%>" status="display"/></td>
			<th class="thEdit rt">Request Time</th>
			<td class="tdEdit"><ui:text name="requestTime" value="<%=requestTime%>" status="display"/></td>
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