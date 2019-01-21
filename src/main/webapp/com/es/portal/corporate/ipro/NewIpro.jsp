<%/************************************************************************************************
* Description - Timesheet Daily Detail
* 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsRequest = (DataSet)pe.getRequestDataSet();
	String clientOrgId = (String)session.getAttribute("ClientOrgId");
	String clientOrgName = (String)session.getAttribute("ClientOrgName");
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
#divLeft {float:left;width:50%;}
#divRight {float:right;width:49%;}
.panel-body {padding:6px 6px;}
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAbo3Ogz4PpHbVSIhpExPrwe0H7zU82vWo&libraries=places&callback=initAutoComplete" async defer></script>
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
	<div id="divLeft">
		<div class="panel panel-success">
			<div class="panel-heading"><h3 class="panel-title">IPro Details</h3></div>
			<div class="panel-body">
				<table class="tblEdit">
					<caption class="captionEdit">Personal Details</caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt mandatory">First Name</th>
						<td class="tdEdit"><ui:text name="firstName"/></td>
						<th class="thEdit Rt mandatory">Surname</th>
						<td class="tdEdit"><ui:text name="surname"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<caption class="captionEdit">Contact Details</caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">Mobile</th>
						<td class="tdEdit"><ui:text name="mobile" maxlength="10"/></td>
						<th class="thEdit Rt">Land Line</th>
						<td class="tdEdit"><ui:text name="landLine" maxlength="15"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Email</th>
						<td class="tdEdit" colspan="3"><ui:text name="email" option="email"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<caption class="captionEdit">Contract Details</caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">Start Date</th>
						<td class="tdEdit">
							<ui:text name="startDate" className="Ct hor" style="width:100px" checkName="Start Date" option="date"/>
							<ui:icon id="icnStartDate" className="fa-calendar hor"/>
						</td>
						<th class="thEdit Rt">End Date</th>
						<td class="tdEdit">
							<ui:text name="endDate" className="Ct hor" style="width:100px" checkName="End Date" option="date"/>
							<ui:icon id="icnEndDate" className="fa-calendar hor"/>
						</td>
					</tr>
					<tr>
						<th class="thEdit Rt">Termination Notice Period</th>
						<td class="tdEdit" colspan="3">
							<ui:text name="terminationPeriod" className="Ct inline" maxlength="3" style="width:100px"/>
							<ui:select name="terminationPeriodUnit" className="hor">
								<ui:seloption value="" text="==Select=="/>
								<ui:seloption value="D" text="Day(s)"/>
								<ui:seloption value="W" text="Week(s)"/>
								<ui:seloption value="M" text="Month(s)"/>
							</ui:select>
						</td>
					</tr>
					<tr>
						<th class="thEdit Rt mandatory">Overseas Travel</th>
						<td class="tdEdit" colspan="3">
							<ui:radio name="overseasTravel" value="Y" text="Yes"/>
							<ui:radio name="overseasTravel" value="N" text="No" isSelected="true"/>
						</td>
					</tr>
					<tr>
						<th class="thEdit Rt">IPro Rate<br/>(inc Super)</th>
						<td class="tdEdit" colspan="3">
							<ui:select name="iproRateUnit" className="hor">
								<ui:seloption value="" text="==Select=="/>
								<ui:seloption value="H" text="Hourly"/>
								<ui:seloption value="D" text="Daily"/>
								<ui:seloption value="W" text="Weekly"/>
								<ui:seloption value="F" text="Fortnightly"/>
								<ui:seloption value="M" text="Monthly"/>
							</ui:select>
							<ui:text name="iproRate" className="rt inline" style="width:100px"/>
						</td>
					</tr>
					<tr>
						<th class="thEdit Rt">Postion Title</th>
						<td class="tdEdit" colspan="3"><ui:text name="positionTitle"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Restraint</th>
						<td class="tdEdit" colspan="3"><ui:text name="restraint"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<caption class="captionEdit">Optional Details</caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">Management Fee</th>
						<td class="tdEdit"><ui:text name="managementFee" className="rt inline" style="width:100px"/> %</td>
						<th class="thEdit Rt">Payroll Tax</th>
						<td class="tdEdit"><ui:text name="payrollTax" className="rt inline" style="width:100px"/> %</td>
					</tr>
					<tr>
						<th class="thEdit Rt">Work Cover</th>
						<td class="tdEdit"><ui:text name="workCover" className="rt inline" style="width:100px"/> %</td>
						<th class="thEdit Rt">Total Billable</th>
						<td class="tdEdit"><ui:text name="totalBillable" className="rt" style="width:100px"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Award</th>
						<td class="tdEdit" colspan="3"><ui:text name="award"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Classification</th>
						<td class="tdEdit" colspan="3"><ui:text name="classification"/></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="divRight">
		<div class="panel panel-success">
			<div class="panel-heading"><h3 class="panel-title">Client Details</h3></div>
			<div class="panel-body">
				<table class="tblInform">
					<colgroup>
						<col width="20%"/>
						<col width="*"/>
					</colgroup>
					<tr>
						<th class="thInform rt">Client Name</th>
						<td class="tdInform"><ui:text name="clientName" value="<%=clientOrgName%>" status="display"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<caption class="captionEdit">Reporting Manager</caption>
					<colgroup>
						<col width="17%"/>
						<col width="33%"/>
						<col width="17%"/>
						<col width="33%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">Name</th>
						<td class="tdEdit"><ui:text name="primaryManagerName"/></td>
						<th class="thEdit Rt">Phone</th>
						<td class="tdEdit"><ui:text name="primaryManagerPhone" maxlength="15"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Email</th>
						<td class="tdEdit" colspan="3"><ui:text name="primaryManagerEmail"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<caption class="captionEdit">Secondary Manager</caption>
					<colgroup>
						<col width="17%"/>
						<col width="33%"/>
						<col width="17%"/>
						<col width="33%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">Name</th>
						<td class="tdEdit"><ui:text name="secondaryManagerName"/></td>
						<th class="thEdit Rt">Phone</th>
						<td class="tdEdit"><ui:text name="secondaryManagerPhone" maxlength="15"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Email</th>
						<td class="tdEdit" colspan="3"><ui:text name="secondaryManagerEmail"/></td>
					</tr>
				</table>
				<div class="verGap6"></div>
				<table class="tblEdit">
					<colgroup>
						<col width="17%"/>
						<col width="33%"/>
						<col width="17%"/>
						<col width="33%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">PO Number</th>
						<td class="tdEdit" colspan="3"><ui:text name="poNumber" className="ct"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Other Relevant Information</th>
						<td class="tdEdit" colspan="3"><ui:txa name="otherClientInformation" style="height:40px"/></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading"><h3 class="panel-title">Work Site Information</h3></div>
			<div class="panel-body">
				<table class="tblEdit">
					<caption class="captionEdit">IPro Physical Work Location</caption>
					<colgroup>
						<col width="17%"/>
						<col width="33%"/>
						<col width="17%"/>
						<col width="33%"/>
					</colgroup>
					<tr>
						<th class="thEdit Rt">End User Name</th>
						<td class="tdEdit" colspan="3"><ui:text name="endUserName"/></td>
					</tr>
					<tr>
						<th class="thEdit rt"><font style="color:#f07031;">Search Address</font></th>
						<td class="tdEdit" colspan="3"><ui:text name="searchAddress"/></td>
					</tr>
					<tr>
						<th class="thEdit rt">Street</th>
						<td class="tdEdit" colspan="3"><ui:text name="street"/></td>
					</tr>
					<tr>
						<th class="thEdit rt">Suburb</th>
						<td class="tdEdit"><ui:text name="suburb"/></td>
						<th class="thEdit rt">State</th>
						<td class="tdEdit">
							<ui:select name="state">
								<ui:seloption value="" text="==Select=="/>
								<ui:seloption value="ACT_02" text="Australian Capital Territory"/>
								<ui:seloption value="NSW_02" text="New South Wales"/>
								<ui:seloption value="NT_08" text="Northern Territory"/>
								<ui:seloption value="QLD_07" text="Queensland"/>
								<ui:seloption value="SA_08" text="South Australia"/>
								<ui:seloption value="TAS_03" text="Tasmania"/>
								<ui:seloption value="VIC_03" text="Victoria"/>
								<ui:seloption value="WA_08" text="Western Australia"/>
								<ui:seloption value="OS_01" text="Overseas Address"/>
							</ui:select>
						</td>
					</tr>
					<tr>
						<th class="thEdit rt">Postcode</th>
						<td class="tdEdit"><ui:text name="postCode"/></td>
						<th class="thEdit rt">Country</th>
						<td class="tdEdit"><ui:text name="country"/></td>
					</tr>
					<tr>
						<th class="thEdit Rt">Other Relevant Information</th>
						<td class="tdEdit" colspan="3"><ui:txa name="otherWorkSiteInformation" style="height:40px"/></td>
					</tr>
				</table>
			</div>
		</div>
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