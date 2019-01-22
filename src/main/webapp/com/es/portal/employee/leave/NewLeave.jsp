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
	DataSet typeLookup = (DataSet)pe.getObject("leaveTypeLookup");
	DataSet categoryLookup = (DataSet)pe.getObject("leaveCategoryLookup");
	DataSet durationUnitLookup = (DataSet)pe.getObject("durationUnitLookup");
	String dateFormat = ConfigUtil.getProperty("format.date.java");
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
var leaveRequestId = "-1";
var assignmentId = "-1";
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
			<col width="18%"/>
			<col width="32%"/>
			<col width="18%"/>
			<col width="32%"/>
		</colgroup>
		<tr>
			<th class="thEdit rt">Assignment Number</th>
			<td class="tdEdit"><ui:text name="assignmentNumber" status="display"/></td>
			<th class="thEdit rt">Status</th>
			<td class="tdEdit"><ui:text name="status" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Type</th>
			<td class="tdEdit">
				<ui:select name="type" checkName="Leave Type" options="mandatory">
<%
				for (int i=0; i<typeLookup.getRowCnt(); i++) {
%>
					<option value="<%=typeLookup.getValue(i, "code")%>"><%=typeLookup.getValue(i, "meaning")%></option>
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
%>
					<option value="<%=categoryLookup.getValue(i, "code")%>"><%=categoryLookup.getValue(i, "meaning")%></option>
<%
				}
%>
				</ui:select>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Start Date</th>
			<td class="tdEdit">
				<ui:text name="startDate" value="<%=defaultDate%>" className="Ct hor" style="width:100px" checkName="Start Date" options="mandatory" option="date"/>
				<ui:icon id="icnStartDate" className="fa-calendar hor"/>
			</td>
			<th class="thEdit rt mandatory">End Date</th>
			<td class="tdEdit">
				<ui:text name="endDate" value="<%=defaultDate%>" className="Ct hor" style="width:100px" checkName="End Date" options="mandatory" option="date"/>
				<ui:icon id="icnEndDate" className="fa-calendar hor"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt mandatory">Duration</th>
			<td class="tdEdit"><ui:text name="duration" status="spinner" checkName="Leave Duration" options="mandatory"/></td>
			<th class="thEdit rt mandatory">Units</th>
			<td class="tdEdit">
				<ui:select name="durationUnits" checkName="Duration Units" options="mandatory">
					<option value="D">Day(s)</option>
<%
				for (int i=0; i<durationUnitLookup.getRowCnt(); i++) {
%>
					<option value="<%=durationUnitLookup.getValue(i, "code")%>"><%=durationUnitLookup.getValue(i, "meaning")%></option>
<%
				}
%>
				</ui:select>
			</td>
		</tr>
		<tr>
			<th class="thEdit rt">Reason</th>
			<td class="tdEdit" colspan="3"><ui:txa name="reason" style="height:40px;"/></td>
		</tr>
		<tr>
			<th class="thEdit rt">Submitted Date</th>
			<td class="tdEdit"><ui:text name="submittedDate" value="<%=defaultDateTime%>" status="display"/></td>
			<th class="thEdit rt"></th>
			<td class="tdEdit"></td>
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