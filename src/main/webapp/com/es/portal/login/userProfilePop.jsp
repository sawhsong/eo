<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsUser = (DataSet)pe.getObject("userDetails");
	String dateOfBirth = CommonUtil.replace(dsUser.getValue("dateOfBirth"), "/", "-");
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
ul {margin-left:20px}
li {margin-top:6px;line-height:170%;}
li:first-child {margin-top:0px;}
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
			<ui:button id="btnEdit" caption="button.com.edit" iconClass="fa-edit"/>
			<ui:button id="btnClose" caption="button.com.close" iconClass="fa-times"/>
		</ui:buttonGroup>
	</div>
</div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea" class="areaContainerPopup">
	<table class="tblInform">
		<caption>Note</caption>
		<tr>
			<td class="tdInform">
				<ul>
					<li>Please note that you cannot make changes to your profile while a task flow that requests your personal details is pending completion. Updates submitted via the task flow will supersede changes made to your profile.</li>
					<li>Changes to your First, Middle or Surname must be supported by proper documentation. Please contact your Customer Relationship Manager to report a change in your name.</li>
					<li>One contact number is mandatory. The email address provided will be your Entity Online email address, and can be separate from the email your payslips are sent to.</li>
				</ul>
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
	<div style="float:left;width:49%;">
		<table class="tblEdit">
			<caption>Personal Details</caption>
			<colgroup>
				<col width="26%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="thEdit rt">Prefix</th>
				<td class="tdEdit"><ui:text name="prefix" value="<%=dsUser.getValue(\"prefix\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">First Name</th>
				<td class="tdEdit"><ui:text name="firstName" value="<%=dsUser.getValue(\"firstName\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Middle Name</th>
				<td class="tdEdit"><ui:text name="middleName" value="<%=dsUser.getValue(\"middleName\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Surname</th>
				<td class="tdEdit"><ui:text name="surname" value="<%=dsUser.getValue(\"surName\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Preferred Name</th>
				<td class="tdEdit"><ui:text name="preferredName" value="<%=dsUser.getValue(\"preferredName\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Date of Birth</th>
				<td class="tdEdit"><ui:text name="dateOfBirth" value="<%=dateOfBirth%>" status="display"/></td>
			</tr>
		</table>
	</div>
	<div style="float:right;width:50%;">
		<table class="tblEdit">
			<caption>Address</caption>
			<colgroup>
				<col width="26%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="thEdit rt">Street</th>
				<td class="tdEdit"><ui:text name="street" value="<%=dsUser.getValue(\"street\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Suburb</th>
				<td class="tdEdit"><ui:text name="suburb" value="<%=dsUser.getValue(\"suburb\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">State</th>
				<td class="tdEdit"><ui:text name="state" value="<%=dsUser.getValue(\"state\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Postcode</th>
				<td class="tdEdit"><ui:text name="postCode" value="<%=dsUser.getValue(\"postCode\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Country</th>
				<td class="tdEdit"><ui:text name="country" value="<%=dsUser.getValue(\"country\")%>" status="display"/></td>
			</tr>
		</table>
	</div>
	<div class="verGap20"></div>
	<div style="float:left;width:49%;">
		<table class="tblEdit">
			<caption>Contact Details</caption>
			<colgroup>
				<col width="26%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="thEdit rt">Mobile</th>
				<td class="tdEdit"><ui:text name="mobile" value="<%=dsUser.getValue(\"mobile\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Landline</th>
				<td class="tdEdit"><ui:text name="landLine" value="<%=dsUser.getValue(\"landLine\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Email</th>
				<td class="tdEdit"><ui:text name="email" value="<%=dsUser.getValue(\"email\")%>" status="display"/></td>
			</tr>
		</table>
	</div>
	<div style="float:right;width:50%;">
		<table class="tblEdit">
			<caption>Emergency Contact Details</caption>
			<colgroup>
				<col width="26%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th class="thEdit rt">Name</th>
				<td class="tdEdit"><ui:text name="emergencyContactName" value="<%=dsUser.getValue(\"emergencyContactName\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Relationship</th>
				<td class="tdEdit"><ui:text name="emergencyContactRelationship" value="<%=dsUser.getValue(\"emergencyContactRelationship\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Phone</th>
				<td class="tdEdit"><ui:text name="emergencyContactPhone" value="<%=dsUser.getValue(\"emergencyContactPhone\")%>" status="display"/></td>
			</tr>
			<tr>
				<th class="thEdit rt">Email</th>
				<td class="tdEdit"><ui:text name="emergencyContactEmail" value="<%=dsUser.getValue(\"emergencyContactEmail\")%>" status="display"/></td>
			</tr>
		</table>
	</div>
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