<%/************************************************************************************************
* Description
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
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
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconEO.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCssJs.jsp"%>
<style type="text/css">
#divDataArea * {font-size:12px;}
#divDataArea a {color:#f07031;cursor:pointer;}
#divDataArea a:hover, #divDataArea a:visit, {color:#f07031;}
h1 {font-size:24px !important;}

.areaContainer {padding:30px 150px 30px 150px !important;}
.noBorder {border:0px;box-shadow:0px 0px 0px;}
.caption {padding:14px 8px 14px 8px;border:0px;font-size:13px !important;}

/* Google Maps */
.mapouter{text-align:right;width:100%;height:270px;}
.gmap_canvas {overflow:hidden;background:none!important;width:100%;height:270px;}
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
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/com/es/portal/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/com/es/portal/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight"></div>
</div>
<div id="divAdminToolArea"><%@ include file="/com/es/portal/common/include/bodyAdminToolArea.jsp"%></div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea"></div>
<%/************************************************************************************************
* End of fixed panel
************************************************************************************************/%>
<div class="breaker"></div>
</div>
<div id="divScrollablePanel">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainer">
	<div class="panel panel-default noBorder">
		<div class="panel-body">
			<div class="panel-heading"><h1 class="panel-title">AUSTRALIA and NEW ZEALAND</h1></div>
			<div id="divDataTablePanel2" class="panel-body">
				<table class="tblDefault withPadding">
					<caption class="caption">Melbourne - Global Head Office</caption>
					<colgroup>
						<col width="50%"/>
						<col width="50%"/>
					</colgroup>
					<tr>
						<td class="tdDefault" style="vertical-align:top;">
							<table class="tblDefault withPadding">
								<colgroup>
									<col width="10%"/>
									<col width="20%"/>
									<col width="*"/>
								</colgroup>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Street Address</th>
									<td class="tdDefault">Level 24, 150 Lonsdale Street, Melbourne VIC 3000</td>
								</tr>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Phone</th>
									<td class="tdDefault">+61 39600 0333</td>
								</tr>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Email</th>
									<td class="tdDefault"><a href="mailto:enquiries@entitysolutions.com.au">enquiries@entitysolutions.com.au</a></td>
								</tr>
							</table>
						</td>
						<td class="tdDefault">
							<table class="tblDefault withPadding">
								<colgroup>
									<col width="*"/>
									<col width="10%"/>
								</colgroup>
								<tr>
									<td class="tdDefault">
										<div class="mapouter">
											<div class="gmap_canvas">
												<iframe style="width:100%;height:270px;" id="gmap_canvas_melbourne" src="https://maps.google.com/maps?q=EntitySolutions Level 24, 150 Lonsdale Street, Melbourne VIC 3000&t=&z=15&ie=UTF8&iwloc=&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
											</div>
										</div>
									</td>
									<td class="tdDefault"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<table class="tblDefault withPadding">
					<caption class="caption">Sydney Office</caption>
					<colgroup>
						<col width="50%"/>
						<col width="50%"/>
					</colgroup>
					<tr>
						<td class="tdDefault" style="vertical-align:top;">
							<table class="tblDefault withPadding">
								<colgroup>
									<col width="10%"/>
									<col width="20%"/>
									<col width="*"/>
								</colgroup>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Street Address</th>
									<td class="tdDefault">Level 2, Kyle House 27-31 Macquarie Place, Sydney NSW 2000</td>
								</tr>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Phone</th>
									<td class="tdDefault">+61 29225 8700</td>
								</tr>
								<tr>
									<td class="tdDefault"></td>
									<th class="thDefault">Email</th>
									<td class="tdDefault"><a href="mailto:enquiries@entitysolutions.com.au">enquiries@entitysolutions.com.au</a></td>
								</tr>
							</table>
						</td>
						<td class="tdDefault">
							<table class="tblDefault withPadding">
								<colgroup>
									<col width="*"/>
									<col width="10%"/>
								</colgroup>
								<tr>
									<td class="tdDefault">
										<div class="mapouter">
											<div class="gmap_canvas">
												<iframe style="width:100%;height:270px;" id="gmap_canvas_sysney" src="https://maps.google.com/maps?q=EntitySolutions Level 2, Kyle House 27-31 Macquarie Place, Sydney NSW 2000&t=&z=15&ie=UTF8&iwloc=&output=embed" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
											</div>
										</div>
									</td>
									<td class="tdDefault"></td>
								</tr>
							</table>
						</td>
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
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/com/es/portal/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/com/es/portal/common/include/footer.jsp"%></div>
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