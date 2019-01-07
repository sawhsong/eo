<%/************************************************************************************************
* Description
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	SysUsers sysUsers = (SysUsers)session.getAttribute("SysUsersForAdminTool");
	if (sysUsers == null) {
		sysUsers = (SysUsers)session.getAttribute("SysUsers");
	}
	String userEmail = sysUsers.getEmail();
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
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainerPopup">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnSubmit" caption="Submit" iconClass="fa-envelope-o"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblInform">
		<caption>My Contacts</caption>
		<colgroup>
			<col width="25%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<th class="thInform rt">Customer Delivery Manager</th>
			<td class="tdInform">
				<table class="tblDefault withPadding">
					<tr>
						<td id="myContacts" class="tdDefault">Fatima Rizwi (<a href="mailto:jknowles@entitysolutions.com.au">jknowles@entitysolutions.com.au</a>)</td>
					</tr>
				</table>
			</td>
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
	<table class="tblEdit">
		<caption>Contact Us</caption>
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt mandatory">First Name</th>
			<td class="tdEdit"><ui:text name="firstName" placeHolder="First Name" checkName="First Name" options="mandatory"/></td>
			<th class="thEdit Rt mandatory">Surname</th>
			<td class="tdEdit"><ui:text name="surname" placeHolder="Surname" checkName="Surname" options="mandatory"/></td>
		</tr>
		<tr>
			<th class="thEdit Rt">Phone Number</th>
			<td class="tdEdit"><ui:text name="phoneNumber" placeHolder="Phone Number" checkName="Phone Number" option="numeric"/></td>
			<th class="thEdit Rt">Your Email</th>
			<td class="tdEdit"><ui:text name="email" value="<%=userEmail%>" status="display"/></td>
		</tr>
		<tr>
			<th class="thEdit Rt mandatory">Your Message</th>
			<td class="tdEdit" colspan="3">
				<ui:txa name="message" style="height:314px;" checkName="Message" options="mandatory"/>
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