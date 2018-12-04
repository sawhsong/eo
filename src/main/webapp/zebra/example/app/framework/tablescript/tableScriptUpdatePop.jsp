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
	String tableName = dsResult.getValue("TABLE_NAME");
	String tableDesc = dsResult.getValue("TABLE_DESCRIPTION");
	String system = (CommonUtil.containsIgnoreCase(fileName, "ZEBRA")) ? "Framework" : "Project";
	String delimiter = ConfigUtil.getProperty("dataDelimiter");
	String selectFramework = "", selectProject = "";
	if (CommonUtil.equalsIgnoreCase(system, "Project")) {
		selectFramework = "false";
		selectProject = "true";
	} else {
		selectFramework = "true";
		selectProject = "false";
	}
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
.thGrid {border-bottom:0px;}
.tblGrid tr:not(.default):not(.active):not(.info):not(.success):not(.warning):not(.danger):hover td {background:#FFFFFF;}
#liDummy {display:none;}
#divDataArea.areaContainerPopup {padding-top:0px;}
.dummyDetail {list-style:none;}
.dragHandler, .dragHandler > i {cursor:move;}
.deleteButton {cursor:pointer;}
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
var fileName = "<%=dsRequest.getValue("fileName")%>";
var ds = commonJs.getDataSetFromJavaDataSet("<%=dsResult.toStringForJs()%>");
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
			<ui:button id="btnSave" caption="button.com.save" iconClass="fa-save"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblEdit">
		<colgroup>
			<col width="9%"/>
			<col width="12%"/>
			<col width="8%"/>
			<col width="21%"/>
			<col width="10%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.tablescript.header.system"/></th>
			<td class="tdEdit">
				<ui:radio name="system" inputClassName="inTbl" value="Project" text="fwk.tablescript.header.project" isSelected="<%=selectProject%>"/>
				<ui:radio name="system" inputClassName="inTbl" value="Framework" text="fwk.tablescript.header.framework" isSelected="<%=selectFramework%>"/>
			</td>
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.tablescript.header.tableName"/></th>
			<td class="tdEdit">
				<ui:text name="tableName" value="<%=tableName%>" style="text-transform:uppercase" checkName="fwk.tablescript.header.tableName" options="mandatory" maxlength="30"/>
			</td>
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.tablescript.header.tableDesc"/></th>
			<td class="tdEdit">
				<ui:text name="tableDescription" value="<%=tableDesc%>" checkName="fwk.tablescript.header.tableDesc" options="mandatory"/>
			</td>
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
			<col width="2%"/>
			<col width="2%"/>
			<col width="16%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="7%"/>
			<col width="18%"/>
			<col width="*"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid"></th>
				<th class="thGrid"></th>
				<th class="thGrid mandatory"><mc:msg key="fwk.tablescript.header.colName"/></th>
				<th class="thGrid "><mc:msg key="fwk.tablescript.header.dataType"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.length"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.defaultValue"/></th>
				<th class="thGrid mandatory"><mc:msg key="fwk.tablescript.header.nullable"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.keyType"/></th>
				<th class="thGrid"><mc:msg key="fwk.tablescript.header.fkRef"/></th>
				<th class="thGrid mandatory"><mc:msg key="fwk.tablescript.header.description"/></th>
			</tr>
		</thead>
		<tbody id="tblGridBody">
			<tr>
				<td colspan="10" style="padding:0px;border-top:0px"><ul id="ulColumnDetailHolder"></ul></td>
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
			<col width="2%"/>
			<col width="2%"/>
			<col width="16%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="6%"/>
			<col width="7%"/>
			<col width="18%"/>
			<col width="*"/>
		</colgroup>
		<tr class="noBorderAll">
			<th id="thDragHander" class="thGrid dragHandler" title="<mc:msg key="fwk.commoncode.msg.drag"/>"><i id="iDragHandler" class="fa fa-lg fa-sort"></i></th>
			<th id="thDeleteButton" class="thGrid deleteButton" title="<mc:msg key="fwk.commoncode.msg.delete"/>"><i id="iDeleteButton" class="fa fa-lg fa-times"></i></th>
			<td class="tdGrid Ct"><ui:text name="columnName" style="text-transform:uppercase" checkName="fwk.tablescript.header.colName" options="mandatory" script="onchange:validate(this)"/></td>
			<td class="tdGrid Ct"><ui:ccselect name="dataType" codeType="DOMAIN_DATA_TYPE" options="mandatory" source="framework" script="onchange:validate(this)"/></td>
			<td class="tdGrid Ct">
				<ui:ccselect name="dataLength" codeType="DOMAIN_DATA_LENGTH" caption="=Select=" source="framework" script="onchange:validate(this)"/>
				<ui:text name="dataLengthNumber" className="Ct" checkName="fwk.tablescript.header.length" script="onchange:validate(this)" style="display:none"/>
			</td>
			<td class="tdGrid Ct"><ui:text name="defaultValue" style="text-transform:uppercase" checkName="fwk.tablescript.header.defaultValue" script="onchange:validate(this)"/></td>
			<td class="tdGrid Ct"><ui:radio name="nullable" value="Y" text="Y" displayType="inline" isSelected="true"/><ui:radio name="nullable" value="N" text="N" displayType="inline" script="onclick:validate(this)"/></td>
			<td class="tdGrid Ct"><ui:ccselect name="keyType" codeType="CONSTRAINT_TYPE" caption="=Select=" source="framework" script="onchange:validate(this)"/></td>
			<td class="tdGrid Ct"><ui:text name="fkRef" style="text-transform:uppercase" checkName="fwk.tablescript.header.fkRef" status="disabled" script="onchange:validate(this)"/></td>
			<td class="tdGrid Ct">
				<ui:text name="description" checkName="fwk.tablescript.header.description" options="mandatory" script="onchange:validate(this)"/>
			</td>
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