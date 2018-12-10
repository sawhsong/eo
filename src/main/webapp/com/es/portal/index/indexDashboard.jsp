<%/************************************************************************************************
* Description
* - Layout order(Normal page)
*	#divHeaderHolder
*	#divBodyHolder
*		#divBodyLeft
*		#divBodyCenter - Each 'Area' in Body Center must set this class('areaContainer') when it has its contents
*			#divFixedPanel
*				#divLocationPathArea
*				#divTabArea
*				#divButtonArea(Left & Right)
*				#divSearchCriteriaArea
*				#divInformArea
*				#breaker
*			#divScrollablePanel
*				#divDataArea
*				#divPagingArea
*		#divBodyRight
*	#divFooterHolder
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
	<div style="margin-top:20px;">
		<div id="divLeft" class="accordion" style="padding-left:20px;width:49%;float:left">
			<div class="accordionGroup">
				<h3 id="hNotice">Notice</h3>
				<div id="divNotice" class="accordionContents">
					<table id="tblGridNotice" class="tblGrid">
						<colgroup>
							<col width="*"/>
							<col width="15%"/>
							<col width="10%"/>
						</colgroup>
						<tbody>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The system has been upgraded. Please check your data entered in the past.</a></td>
								<td class="tdGridCt">Administrator</td>
								<td class="tdGridRt">10-07-2017</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3 id="hIncome">Income</h3>
				<div id="divIncome" class="accordionContents">
					<table id="tblGridIncome" class="tblGrid">
						<colgroup>
							<col width="*"/>
							<col width="12%"/>
							<col width="12%"/>
							<col width="12%"/>
							<col width="12%"/>
							<col width="12%"/>
							<col width="12%"/>
							<col width="12%"/>
						</colgroup>
						<thead>
							<tr class="noBorderHor noStripe">
								<th class="thGrid">Date</th>
								<th class="thGrid">Card, Etc</th>
								<th class="thGrid">Cash</th>
								<th class="thGrid">Gross</th>
								<th class="thGrid">GST Free</th>
								<th class="thGrid">GST</th>
								<th class="thGrid">Net</th>
								<th class="thGrid">Particular</th>
							</tr>
						</thead>
						<tbody>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">30-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">29-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">28-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">27-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">26-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">25-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">24-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">23-06-2017</a></td>
								<td class="tdGridRt">1,000.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">22-06-2017</a></td>
								<td class="tdGridRt">2,500.99</td>
								<td class="tdGridRt">1,234.99</td>
								<td class="tdGridRt">1,500.99</td>
								<td class="tdGridRt">900.99</td>
								<td class="tdGridRt">2,080.99</td>
								<td class="tdGridRt">3,560.99</td>
								<td class="tdGridRt">600.99</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGridCt"><a class="aEn">21-06-2017</a></td>
								<td class="tdGridRt">1,030.50</td>
								<td class="tdGridRt">1,234.10</td>
								<td class="tdGridRt">1,500.05</td>
								<td class="tdGridRt">900.30</td>
								<td class="tdGridRt">2,080.50</td>
								<td class="tdGridRt">3,560.06</td>
								<td class="tdGridRt">600.02</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="divRight" class="accordion" style="padding-right:20px;width:49%;float:right">
			<div class="accordionGroup">
				<h3 id="hFreeBoard">Free Board</h3>
				<div id="divFreeBoard" class="accordionContents">
					<table id="tblGridFreeBoard" class="tblGrid">
						<colgroup>
							<col width="*"/>
							<col width="15%"/>
							<col width="10%"/>
						</colgroup>
						<tbody>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
							<tr class="noBorderAll noStripe">
								<td class="tdGrid"><a class="aEn">The new system opened. Please check your account details.</a></td>
								<td class="tdGridCt">Seun Jin Lee</td>
								<td class="tdGridRt">20-07-2017</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="accordionGroup">
				<h3 id="hExpense">Expense</h3>
				<div id="divExpense" class="accordionContents">
					<table id="tblGridExpense" class="tblGrid">
						<colgroup>
							<col width="*"/>
							<col width="20%"/>
							<col width="6%"/>
							<col width="10%"/>
							<col width="7%"/>
							<col width="7%"/>
							<col width="7%"/>
							<col width="20%"/>
						</colgroup>
						<thead>
							<tr class="noBorderHor noStripe">
								<th class="thGrid">Main Type</th>
								<th class="thGrid">Sub Type</th>
								<th class="thGrid">Code</th>
								<th class="thGrid">Date</th>
								<th class="thGrid">Gross</th>
								<th class="thGrid">GST</th>
								<th class="thGrid">Net</th>
								<th class="thGrid">Particular</th>
							</tr>
						</thead>
						<tbody>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">25-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">24-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">23-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">22-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">21-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">20-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">19-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">18-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">17-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
							<tr class="noBorderHor noStripe">
								<td class="tdGrid">Client Related Expenses</td>
								<td class="tdGrid">Commission Discount</td>
								<td class="tdGridCt">345</td>
								<td class="tdGridCt"><a class="aEn">16-06-2017</a></td>
								<td class="tdGridRt">176.04</td>
								<td class="tdGridRt">16.00</td>
								<td class="tdGridRt">160.04</td>
								<td class="tdGrid">IELI_Seo Yeol YANG</td>
							</tr>
						</tbody>
					</table>
				</div>
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