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
	ZebraDomainDictionary zebraDomainDictionary = (ZebraDomainDictionary)pe.getObject("zebraDomainDictionary");
	String numberFormat = "###";
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
var domainId = "<%=zebraDomainDictionary.getDomainId()%>";
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
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.domaindictionary.header.name"/></th>
			<td class="tdEdit">
				<ui:text name="domainName" value="<%=zebraDomainDictionary.getDomainName()%>" checkName="fwk.domaindictionary.header.name" options="mandatory"/>
			</td>
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.domaindictionary.header.nameAbbrev"/></th>
			<td class="tdEdit">
				<ui:text name="nameAbbreviation" value="<%=zebraDomainDictionary.getNameAbbreviation()%>" checkName="fwk.domaindictionary.header.nameAbbrev" options="mandatory"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit Rt mandatory"><mc:msg key="fwk.domaindictionary.header.dataType"/></th>
			<td class="tdEdit">
				<ui:ccselect name="dataType" codeType="DOMAIN_DATA_TYPE" selectedValue="<%=zebraDomainDictionary.getDataType()%>" options="mandatory" source="framework"/>
			</td>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.dataLength"/></th>
			<td class="tdEdit">
				<ui:ccselect name="dataLength" codeType="DOMAIN_DATA_LENGTH" selectedValue="<%=CommonUtil.toString(zebraDomainDictionary.getDataLength(), numberFormat)%>" caption="==Select==" source="framework"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.dataPrecision"/></th>
			<td class="tdEdit">
				<ui:ccselect name="dataPrecision" codeType="DOMAIN_DATA_PRECISION" selectedValue="<%=CommonUtil.toString(zebraDomainDictionary.getDataPrecision(), numberFormat)%>" caption="==Select==" source="framework"/>
			</td>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.dataScale"/></th>
			<td class="tdEdit">
				<ui:ccselect name="dataScale" codeType="DOMAIN_DATA_SCALE" selectedValue="<%=CommonUtil.toString(zebraDomainDictionary.getDataScale(), numberFormat)%>" caption="==Select==" source="framework"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.desc"/></th>
			<td class="tdEdit" colspan="3" style="height:200px">
				<ui:txa name="description" value="<%=zebraDomainDictionary.getDescription()%>" style="height:200px;"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.insertUser"/></th>
			<td class="tdEdit">
				<ui:text name="insertUser" value="<%=zebraDomainDictionary.getInsertUserName()%>" status="display"/>
			</td>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.insertDate"/></th>
			<td class="tdEdit">
				<ui:text name="insertDate" value="<%=CommonUtil.toViewDateString(zebraDomainDictionary.getInsertDate())%>" status="display"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.updateUser"/></th>
			<td class="tdEdit">
				<ui:text name="updateUser" value="<%=zebraDomainDictionary.getUpdateUserName()%>" status="display"/>
			</td>
			<th class="thEdit Rt"><mc:msg key="fwk.domaindictionary.header.updateDate"/></th>
			<td class="tdEdit">
				<ui:text name="updateDate" value="<%=CommonUtil.toViewDateString(zebraDomainDictionary.getUpdateDate())%>" status="display"/>
			</td>
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
<form id="fmHidden" name="fmHidden" method="post" action="">
</form>
</body>
</html>