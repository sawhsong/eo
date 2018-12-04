<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsRequest = (DataSet)pe.getRequestDataSet();
	DataSet dsResult = (DataSet)pe.getObject("resultDataSet");
	String fileName = (String)dsRequest.getValue("fileName");
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
var fileName = "<%=fileName%>";
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
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnEdit" caption="button.com.edit" iconClass="fa-edit"/>
			<ui:button id="btnDelete" caption="button.com.delete" iconClass="fa-save"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblEdit">
		<caption class="captionEdit"><%=fileName%></caption>
		<colgroup>
			<col width="12%"/>
			<col width="30%"/>
			<col width="12%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.tablescript.header.tableName"/></th>
			<td class="tdEdit"><%=dsResult.getValue("TABLE_NAME")%></td>
			<th class="thEdit Rt"><mc:msg key="fwk.tablescript.header.tableDesc"/></th>
			<td class="tdEdit"><%=dsResult.getValue("TABLE_DESCRIPTION")%></td>
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
			<col width="17%"/>
			<col width="7%"/>
			<col width="5%"/>
			<col width="7%"/>
			<col width="5%"/>
			<col width="5%"/>
			<col width="18%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.colName"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.dataType"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.length"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.defaultValue"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.nullable"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.keyType"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.fkRef"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.description"/></th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
<%
		if (dsResult.getRowCnt() > 0) {
			for (int i=0; i<dsResult.getRowCnt(); i++) {
%>
			<tr>
				<td class="tdGrid Lt"><%=dsResult.getValue(i, "COLUMN_NAME")%></td>
				<td class="tdGrid Ct"><%=dsResult.getValue(i, "DATA_TYPE")%></td>
				<td class="tdGrid Ct"><%=dsResult.getValue(i, "DATA_LENGTH")%></td>
				<td class="tdGrid Ct"><%=dsResult.getValue(i, "DEFAULT_VALUE")%></td>
				<td class="tdGrid Ct"><%=dsResult.getValue(i, "NULLABLE")%></td>
				<td class="tdGrid Ct"><%=dsResult.getValue(i, "KEY_TYPE")%></td>
				<td class="tdGrid Lt"><%=dsResult.getValue(i, "FK_TABLE_COLUMN")%></td>
				<td class="tdGrid Lt"><%=CommonUtil.abbreviate(dsResult.getValue(i, "COLUMN_DESCRIPTION"), 100)%></td>
			</tr>
<%
			}
		} else {
%>
			<tr>
				<td class="tdGrid Ct" colspan="8"><mc:msg key="fwk.tablescript.msg.noColumn"/></td>
			</tr>
<%
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
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>