<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity paramEntity = (ParamEntity)request.getAttribute("paramEntity");
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/zebraFavicon.png">
<title><mc:msg key="fwk.main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/shared/page/incCssJs.jsp"%>
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
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/zebra/example/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/zebra/example/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/zebra/example/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainer">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnNew" caption="button.com.new" iconClass="fa-comment"/>
			<ui:button id="btnDelete" caption="button.com.delete" iconClass="fa-trash"/>
			<ui:button id="btnSearch" caption="button.com.search" iconClass="fa-search"/>
			<ui:button id="btnClear" caption="button.com.clear" iconClass="fa-refresh"/>
			<ui:button id="btnExport" caption="button.com.export" iconClass="fa-download"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea" class="areaContainer">
	<div class="panel panel-default">
		<div class="panel-body">
			<table class="tblDefault">
				<colgroup>
					<col width="50%"/>
					<col width="50%"/>
				</colgroup>
				<tr>
					<td class="tdDefault">
						<label for="searchType" class="lblEn hor"><mc:msg key="fwk.notice.searchHeader.searchType"/></label>
						<div style="float:left;padding-right:4px;">
							<ui:ccselect name="searchType" codeType="BOARD_SEARCH_TYPE" caption="==Select==" checkName="Search Type" source="framework"/>
						</div>
						<ui:text name="searchWord" className="hor" style="width:280px"/>
					</td>
					<td class="tdDefault">
						<label for="fromDate" class="lblEn hor"><mc:msg key="fwk.notice.searchHeader.searchPeriod"/></label>
						<ui:text name="fromDate" className="Ct hor" style="width:100px" checkName="From Date" option="date"/>
						<ui:icon id="icnFromDate" className="fa-calendar hor" title="From Date"/>
						<div class="horGap20" style="padding:6px 8px 6px 0px;">-</div>
						<ui:text name="toDate" className="Ct hor" style="width:100px" checkName="To Date" option="date"/>
						<ui:icon id="icnToDate" className="fa-calendar hor" title="To Date"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
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
			<col width="3%"/>
			<col width="*"/>
			<col width="5%"/>
			<col width="15%"/>
			<col width="10%"/>
			<col width="8%"/>
			<col width="5%"/>
		</colgroup>
		<thead>
			<tr class="noBorderHor">
				<th class="thGrid">
					<ui:icon id="icnCheck" className="fa-check-square-o fa-lg" title="fwk.notice.title.selectToDelete"/>
				</th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="fwk.notice.gridHeader.subject"/></th>
				<th class="thGrid"><mc:msg key="fwk.notice.gridHeader.file"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="fwk.notice.gridHeader.writer"/></th>
				<th class="thGrid sortable:date"><mc:msg key="fwk.notice.gridHeader.createdDate"/></th>
				<th class="thGrid sortable:numeric"><mc:msg key="fwk.notice.gridHeader.visitCount"/></th>
				<th class="thGrid"><mc:msg key="page.com.action"/></th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr class="noBorderHor noStripe">
				<td class="tdGrid Ct" colspan="7"><mc:msg key="I002"/></td>
			</tr>
		</tbody>
	</table>
</div>
<div id="divPagingArea" class="areaContainer"></div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
</div>
</div>
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/zebra/example/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/zebra/example/common/include/footer.jsp"%></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action="">
</form>
</body>
</html>