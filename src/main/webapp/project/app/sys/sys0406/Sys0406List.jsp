<%/************************************************************************************************
* Description - Sys0406 - User
*	- Generated by Source Generator
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity paramEntity = (ParamEntity)request.getAttribute("paramEntity");
	DataSet authGroupDataSet = (DataSet)paramEntity.getObject("authGroupDataSet");
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconPerci.png">
<title><mc:msg key="main.system.title"/></title>
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
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/project/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/project/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/project/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainer">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnAction" caption="button.com.action" iconClass="fa-caret-down"/>
			<ui:button id="btnNew" caption="button.com.new" iconClass="fa-plus-square"/>
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
			<table class="tblDefault withPadding">
				<colgroup>
					<col width="10%"/>
					<col width="23%"/>
					<col width="10%"/>
					<col width="23%"/>
					<col width="10%"/>
					<col width="24%"/>
				</colgroup>
				<tr>
					<th class="thDefault rt"><mc:msg key="sys0406.search.name"/></th>
					<td class="tdDefault"><ui:text name="userName"/></td>
					<th class="thDefault rt"><mc:msg key="sys0406.search.id"/></th>
					<td class="tdDefault"><ui:text name="loginId" style="width:200px"/></td>
					<th class="thDefault rt"><mc:msg key="sys0406.search.auth"/></th>
					<td class="tdDefault">
						<ui:select name="authGroup">
							<option value="">==Select==</option>
<%
						for (int i=0; i<authGroupDataSet.getRowCnt(); i++) {
%>
							<option value="<%=authGroupDataSet.getValue(i, "GROUP_ID")%>"><%=authGroupDataSet.getValue(i, "GROUP_NAME")%></option>
<%
						}
%>
						</ui:select>
					</td>
				</tr>
				<tr>
					<th class="thDefault rt"><mc:msg key="sys0406.search.type"/></th>
					<td class="tdDefault"><ui:ccselect name="userType" codeType="USER_TYPE" caption="==Select=="/></td>
					<th class="thDefault rt"><mc:msg key="sys0406.search.status"/></th>
					<td class="tdDefault"><ui:ccselect name="userStatus" codeType="USER_STATUS" caption="==Select=="/></td>
					<th class="thDefault rt"><mc:msg key="sys0406.search.active"/></th>
					<td class="tdDefault"><ui:ccselect name="isActive" codeType="IS_ACTIVE" caption="==Select=="/></td>
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
			<col width="2%"/>
			<col width="11%"/>
			<col width="11%"/>
			<col width="20%"/>
			<col width="8%"/>
			<col width="8%"/>
			<col width="*"/>
			<col width="5%"/>
			<col width="7%"/>
			<col width="3%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid"><ui:icon id="icnCheck" className="fa-check-square-o fa-lg" title="page.com.selectToDelete"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="sys0406.grid.userName"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="sys0406.grid.loginId"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="sys0406.grid.authGroup"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="sys0406.grid.type"/></th>
				<th class="thGrid sortable:alphanumeric"><mc:msg key="sys0406.grid.status"/></th>
				<th class="thGrid"><mc:msg key="sys0406.grid.email"/></th>
				<th class="thGrid"><mc:msg key="sys0406.grid.active"/></th>
				<th class="thGrid sortable:date"><mc:msg key="sys0406.grid.date"/></th>
				<th class="thGrid"><mc:msg key="page.com.action"/></th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td class="tdGridCt" colspan="10"><mc:msg key="I002"/></td>
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
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/project/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/project/common/include/footer.jsp"%></div>
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