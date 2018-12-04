<%/************************************************************************************************
* Description
* - Layout order(Popup page)
*	#divPopupWindowHolder
*	#divFixedPanel
*		#divLocationPathArea
*		#divTabArea
*		#divButtonArea(Left & Right)
*		#divSearchCriteriaArea
*		#divInformArea
*		#breaker
*	#divScrollablePanel
*		#divDataArea
*		#divPagingArea
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet ds = (DataSet)pe.getObject("resultDataSet");
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
		<ui:buttonGroup id="btnGroup">
			<ui:button id="btnRun" caption="fwk.garbageCollection.buttonRunCollector" iconClass="fa-recycle"/>
			<ui:button id="btnReload" caption="button.com.reload" iconClass="fa-refresh"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea" class="areaContainerPopup">
	<table class="tblEdit">
		<colgroup>
			<col width="25%"/>
			<col width="75%"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.garbageCollection.host"/></th>
			<td class="tdEdit"><%=pe.getObject("hostName")%> (<%=pe.getObject("hostAddress")%>)</td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.garbageCollection.measurmentTime"/></th>
			<td class="tdEdit"><%=CommonUtil.nvl((String)pe.getObject("measuredTime"))%></td>
		</tr>
	</table>
</div>
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
			<col width="50%"/>
			<col width="50%"/>
		</colgroup>
		<thead>
			<tr>
				<th class="thGrid Ct" colspan="2"><mc:msg key="fwk.garbageCollection.totalMemory"/> (<%=pe.getObject("totalMemory")%> MB)</th>
			</tr>
			<tr>
				<th class="thGrid Ct"><mc:msg key="fwk.garbageCollection.usingMemory"/> (<%=pe.getObject("usingMemory")%> MB)</th>
				<th class="thGrid Ct"><mc:msg key="fwk.garbageCollection.freeMemory"/> (<%=pe.getObject("freeMemory")%> MB)</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="tdGrid Ct" colspan="2"><img src="<%=pe.getObject("chartFilePath")%>"/></td>
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
<input type="hidden" id="hdnGarbageCollector" name="hdnGarbageCollector" value="" />
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>