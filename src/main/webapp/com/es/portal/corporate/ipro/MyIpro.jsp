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
	DataSet dsRequest = pe.getRequestDataSet();
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
ul {margin-left:20px}
li {margin-top:6px;line-height:170%;}
li:first-child {margin-top:0px;}
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
			<ui:button id="btnNew" caption="New IPro" iconClass="fa-user-circle"/>
			<ui:button id="btnExport" caption="Download CSV" iconClass="fa-download"/>
			<ui:button id="btnSearch" caption="Search Existing IPros" iconClass="fa-search"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divAdminToolArea"><%@ include file="/com/es/portal/common/include/bodyAdminToolArea.jsp"%></div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainer">
	<table id="tblInform" class="tblInform">
		<caption>View and download the details of all IPros who have held an active contract assignment with your organisation and register new IPros commencing a contract assignment with your organisation</caption>
		<tr>
			<td class="tdInform">
				<ul>
					<li><font style="font-weight:bold">Existing IPros</font> - View the details of IPros with active contracts within a nominated date range. You can view specific details about each IPro such as their contact details, recent contract period, historical record of all contracts held with your organisation and their recent invoice and payment dates.</li>
					<li><font style="font-weight:bold">New IPro</font> - Register the details of a new IPro before they commence a contract assignment with your organisation. Once you have filled in and submitted the required information, an Entity Solutions' Representative will make initial contact to introduce our services and set them up within our system.</li>
				</ul>
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
	<table id="tblGrid" class="tblGrid sort autosort">
		<colgroup>
			<col width="13%"/>
			<col width="13%"/>
			<col width="14%"/>
			<col width="7%"/>
			<col width="7%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="*"/>
			<col width="11%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid sortable:alphanumeric">IPro Name</th>
				<th class="thGrid">Position Title</th>
				<th class="thGrid">Email Address</th>
				<th class="thGrid">Last Invoice Date</th>
				<th class="thGrid">Last Pay Date</th>
				<th class="thGrid sortable:date">Assignment Start Date</th>
				<th class="thGrid sortable:date">Assignment End Date</th>
				<th class="thGrid sortable:alphanumeric">End User Organisation</th>
				<th class="thGrid">Working State</th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td class="tdGrid Ct" colspan="9"><mc:msg key="I002"/></td>
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