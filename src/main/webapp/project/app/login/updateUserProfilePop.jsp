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
	String maxRowPerPage[] = (String[])pe.getObject("maxRowPerPage");
	String pageNumPerPage[] = (String[])pe.getObject("pageNumPerPage");
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
			<ui:button id="btnSave" caption="button.com.save" iconClass="fa-save"/>
			<ui:button id="btnBack" caption="button.com.back" iconClass="fa-arrow-left"/>
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
			<th class="thEdit"><mc:msg key="login.header.changePhoto"/></th>
			<td class="tdEdit" colspan="3">
				<input type="file" id="filePhotoPath" name="filePhotoPath" class="file" value="" style="width:540px;" checkName="<mc:msg key="login.header.changePhoto"/>"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.userId"/></th>
			<td class="tdEdit">
				<input type="text" id="userId" name="userId" value="<%=sysUser.getUserId()%>" class="txtDpl" readonly/>
			</td>
			<th class="thEdit"><mc:msg key="login.header.loginId"/></th>
			<td class="tdEdit">
				<input type="text" id="loginId" name="loginId" value="<%=sysUser.getLoginId()%>" class="txtEn" checkName="<mc:msg key="login.header.loginId"/>" mandatory/>
			</td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.userName"/></th>
			<td class="tdEdit">
				<input type="text" id="userName" name="userName" value="<%=sysUser.getUserName()%>" class="txtEn" checkName="<mc:msg key="login.header.userName"/>" mandatory/>
			</td>
			<th class="thEdit"><mc:msg key="login.header.password"/></th>
			<td class="tdEdit">
				<input type="text" id="loginPassword" name="loginPassword" value="<%=sysUser.getLoginPassword()%>" class="txtEn" checkName="<mc:msg key="login.header.password"/>" mandatory/>
			</td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.language"/></th>
			<td class="tdEdit">
				<ui:ccselect name="language" codeType="LANGUAGE_TYPE" options="mandatory" selectedValue="<%=sysUser.getLanguage()%>"/>
			</td>
			<th class="thEdit"><mc:msg key="login.header.themeType"/></th>
			<td class="tdEdit">
				<ui:ccselect name="themeType" codeType="USER_THEME_TYPE" options="mandatory" selectedValue="<%=sysUser.getThemeType()%>"/>
			</td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.maxRowsPerPage"/></th>
			<td class="tdEdit">
				<select id="maxRowsPerPage" name="maxRowsPerPage" class="bootstrapSelect">
<%
				for (int i=0; i<maxRowPerPage.length; i++) {
					String selected = (CommonUtil.equals(maxRowPerPage[i], CommonUtil.toString(sysUser.getMaxRowPerPage(), "###"))) ? "selected" : "";
%>
					<option value="<%=maxRowPerPage[i]%>" <%=selected%>><%=maxRowPerPage[i]%></option>
<%
				}
%>
				</select>
			</td>
			<th class="thEdit"><mc:msg key="login.header.pageNumsPerPage"/></th>
			<td class="tdEdit">
				<select id="pageNumsPerPage" name="pageNumsPerPage" class="bootstrapSelect">
<%
				for (int i=0; i<pageNumPerPage.length; i++) {
					String selected = (CommonUtil.equals(pageNumPerPage[i], CommonUtil.toString(sysUser.getPageNumPerPage(), "###"))) ? "selected" : "";
%>
					<option value="<%=pageNumPerPage[i]%>" <%=selected%>><%=pageNumPerPage[i]%></option>
<%
				}
%>
				</select>
			</td>
		</tr>
		<tr>
			<th class="thEdit"><mc:msg key="login.header.email"/></th>
			<td class="tdEdit" colspan="3">
				<input type="text" id="email" name="email" value="<%=sysUser.getEmail()%>" class="txtEn" checkName="<mc:msg key="login.header.email"/>" mandatory option="email"/>
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
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>