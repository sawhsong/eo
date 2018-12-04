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
	DataSet requestDataSet = (DataSet)paramEntity.getRequestDataSet();
	DataSet resultDataSet = (DataSet)paramEntity.getObject("resultDataSet");
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
<div id="divPopupWindowHolder">
<div id="divFixedPanelPopup">
<div id="divLocationPathArea"><%@ include file="/zebra/example/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainerPopup">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblEdit">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.dtogenerator.gridHeader.tableName"/></th>
			<td class="tdEdit"><%=requestDataSet.getValue("tableName")%></td>
			<th class="thEdit Rt"><mc:msg key="fwk.dtogenerator.gridHeader.tableDesc"/></th>
			<td class="tdEdit"><%=resultDataSet.getValue("TABLE_DESCRIPTION")%></td>
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
	<table id="tblGrid" class="tblGrid sort autosort">
		<colgroup>
			<col width="22%"/>
			<col width="9%"/>
			<col width="10%"/>
			<col width="8%"/>
			<col width="9%"/>
			<col width="9%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.columnName"/></th>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.dataType"/></th>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.defaultValue"/></th>
				<th class="thGrid sortable:numeric"><mc:msg key="fwk.dtogenerator.header.length"/></th>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.nullable"/></th>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.keyType"/></th>
				<th class="thGrid sortable:string"><mc:msg key="fwk.dtogenerator.header.description"/></th>
			</tr>
		</thead>
		<tbody>
<%
		if (resultDataSet.getRowCnt() > 0) {
			for (int i=0; i<resultDataSet.getRowCnt(); i++) {
%>
			<tr>
				<td class="tdGrid"><%=resultDataSet.getValue(i, "COLUMN_NAME")%></td>
				<td class="tdGrid Ct"><%=resultDataSet.getValue(i, "DATA_TYPE")%></td>
				<td class="tdGrid Ct"><%=resultDataSet.getValue(i, "DATA_DEFAULT")%></td>
				<td class="tdGrid Ct"><%=resultDataSet.getValue(i, "DATA_LENGTH")%></td>
				<td class="tdGrid Ct"><%=resultDataSet.getValue(i, "NULLABLE")%></td>
				<td class="tdGrid Ct"><%=resultDataSet.getValue(i, "CONSTRAINT_TYPE")%></td>
				<td class="tdGrid" title="<%=resultDataSet.getValue(i, "COMMENTS")%>"><%=CommonUtil.abbreviate(resultDataSet.getValue(i, "COMMENTS"), 40)%></td>
			</tr>
<%
			}
		}
%>
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
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action="">
</form>
</body>
</html>