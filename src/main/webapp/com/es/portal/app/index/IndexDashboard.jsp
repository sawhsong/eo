<%/************************************************************************************************
* Description
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity paramEntity = (ParamEntity)request.getAttribute("paramEntity");
	DataSet resultDataSet = (DataSet)paramEntity.getObject("resultDataSet");
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
#hNotice.ui-state-default {background-color:#DFF0D8;padding-top:10px;padding-bottom:10px;}
#hNotice.ui-accordion-header.ui-state-active {background:#DFF0D8;padding-top:10px;padding-bottom:10px;}
#hFreeBoard.ui-state-default {background-color:#DFF0D8;padding-top:10px;padding-bottom:10px;}
#hFreeBoard.ui-accordion-header.ui-state-active {background:#DFF0D8;padding-top:10px;padding-bottom:10px;}
#hIncome.ui-state-default {background-color:#5BC0DE;padding-top:10px;padding-bottom:10px;color:#ffffff;}
#hIncome.ui-accordion-header.ui-state-active {background:#5BC0DE;padding-top:10px;padding-bottom:10px;color:#ffffff;}
#hExpense.ui-state-default {background-color:#5BC0DE;padding-top:10px;padding-bottom:10px;color:#ffffff;}
#hExpense.ui-accordion-header.ui-state-active {background:#5BC0DE;padding-top:10px;padding-bottom:10px;color:#ffffff;}
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
<!-- 	<div style="margin-top:20px;"> -->
<!-- 		<div id="divLeft" class="accordion" style="padding-left:20px;width:49%;float:left"> -->
<!-- 			<div class="accordionGroup"> -->
<!-- 				<h3 id="hNotice">Notice</h3> -->
<!-- 				<div id="divNotice" class="accordionContents"> -->

<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="accordionGroup"> -->
<!-- 				<h3 id="hIncome">Income</h3> -->
<!-- 				<div id="divIncome" class="accordionContents"> -->

<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div id="divRight" class="accordion" style="padding-right:20px;width:49%;float:right"> -->
<!-- 			<div class="accordionGroup"> -->
<!-- 				<h3 id="hFreeBoard">Free Board</h3> -->
<!-- 				<div id="divFreeBoard" class="accordionContents"> -->

<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="accordionGroup"> -->
<!-- 				<h3 id="hExpense">Expense</h3> -->
<!-- 				<div id="divExpense" class="accordionContents"> -->

<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
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