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
			<ui:button id="btnNew" caption="New Claim" iconClass="fa-money"/>
			<ui:button id="btnSearch" caption="Search" iconClass="fa-search"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divAdminToolArea"><%@ include file="/com/es/portal/common/include/bodyAdminToolArea.jsp"%></div>
<div id="divSearchCriteriaArea" class="areaContainer">
	<table class="tblSearch">
		<caption>Search Criteria</caption>
		<colgroup>
			<col width="30%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<td class="tdSearch">
				<label for="assignment" class="lblEn hor">Search</label>
				<div style="float:left;padding-right:4px;">
				</div>
			</td>
		</tr>
	</table>
</div>
<div id="divInformArea"></div>
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
	<table id="tblGrid" class="tblGrid sort autosort">
		<colgroup>
			<col width="*"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="8%"/>
			<col width="8%"/>
			<col width="8%"/>
			<col width="8%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid">Staff Name</th>
				<th class="thGrid">Department</th>
				<th class="thGrid">Expense Type</th>
				<th class="thGrid">Date of Claim</th>
				<th class="thGrid">Amount</th>
				<th class="thGrid">GST</th>
				<th class="thGrid">Status</th>
				<th class="thGrid">Approved By</th>
				<th class="thGrid">Document Attached ?</th>
				<th class="thGrid">Date Submitted</th>
				<th class="thGrid">Date Processed(by Finance)</th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td class="tdGrid Ct" colspan="11"><mc:msg key="I002"/></td>
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
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>