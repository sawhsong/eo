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
	SysUser sysUser = (SysUser)pe.getObject("sysUser");
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconEO.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/shared/page/incCssJs.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
var userId = "<%=sysUser.getUserId()%>";
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divPopupWindowHolder">
<div id="divFixedPanelPopup">
<div id="divLocationPathArea"><%@ include file="/project/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainerPopup">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnEdit" caption="button.com.edit" iconClass="fa-edit"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea">
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
	<div class="panel panel-default" style="width:120px;height:110px;">
		<div class="panel-body">
			<table class="tblDefault">
				<tr>
					<td class="tdDefaultCt">
						<img id="img<%=sysUser.getUserId()%>" src="<%=sysUser.getPhotoPath()%>" class="imgDis" style="width:90px;height:90px;" title="<%=sysUser.getUserName()%>"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<table class="tblEdit">
		<colgroup>
			<col width="22%"/>
			<col width="28%"/>
			<col width="22%"/>
			<col width="28%"/>
		</colgroup>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.userId"/></th>
			<td class="tdEdit"><%=sysUser.getUserId()%></td>
			<th class="thEdit"><mc:msg key="login.header.loginId"/></th>
			<td class="tdEdit"><%=sysUser.getLoginId()%></td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.userName"/></th>
			<td class="tdEdit"><%=sysUser.getUserName()%></td>
			<th class="thEdit"><mc:msg key="login.header.password"/></th>
			<td class="tdEdit"><%=sysUser.getLoginPassword()%></td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.language"/></th>
			<td class="tdEdit"><%=CommonCodeManager.getCodeDescription("LANGUAGE_TYPE", sysUser.getLanguage())%></td>
			<th class="thEdit"><mc:msg key="login.header.themeType"/></th>
			<td class="tdEdit"><%=CommonCodeManager.getCodeDescription("USER_THEME_TYPE", sysUser.getThemeType())%></td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.maxRowsPerPage"/></th>
			<td class="tdEdit"><%=CommonUtil.toString(sysUser.getMaxRowPerPage(), "#,###")%></td>
			<th class="thEdit"><mc:msg key="login.header.pageNumsPerPage"/></th>
			<td class="tdEdit"><%=CommonUtil.toString(sysUser.getPageNumPerPage(), "#,###")%></td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.email"/></th>
			<td class="tdEdit" colspan="3"><%=sysUser.getEmail()%></td>
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