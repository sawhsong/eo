<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity paramEntity = (ParamEntity)request.getAttribute("paramEntity");
	DataSet dsRequest = (DataSet)paramEntity.getRequestDataSet();
	ZebraBoard freeBoard = (ZebraBoard)paramEntity.getObject("freeBoard");
	DataSet fileDataSet = (DataSet)paramEntity.getObject("fileDataSet");
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
var articleId = "<%=freeBoard.getArticleId()%>";
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/zebra/example/common/include/header.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"><%@ include file="/zebra/example/common/include/bodyLeft.jsp"%></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/zebra/example/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea" class="areaContainer">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight">
		<ui:buttonGroup id="buttonGroup">
			<ui:button id="btnEdit" caption="button.com.edit" iconClass="fa-edit"/>
			<ui:button id="btnReply" caption="button.com.reply" iconClass="fa-reply-all"/>
			<ui:button id="btnDelete" caption="button.com.delete" iconClass="fa-save"/>
			<ui:button id="btnBack" caption="button.com.back" iconClass="fa-arrow-left"/>
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
<div id="divScrollablePanel">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainer">
	<table class="tblEdit">
		<colgroup>
			<col width="12%"/>
			<col width="38%"/>
			<col width="12%"/>
			<col width="38%"/>
		</colgroup>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.writerName"/></th>
			<td class="tdEdit"><%=freeBoard.getWriterName()%>(<%=freeBoard.getWriterId()%>)</td>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.writerEmail"/></th>
			<td class="tdEdit"><%=freeBoard.getWriterEmail()%></td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.updateDate"/></th>
			<td class="tdEdit"><%=CommonUtil.toViewDateString(freeBoard.getUpdateDate())%></td>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.visitCount"/></th>
			<td class="tdEdit"><%=CommonUtil.getNumberMask(freeBoard.getVisitCnt())%></td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.articleSubject"/></th>
			<td class="tdEdit" colspan="3"><%=freeBoard.getArticleSubject()%></td>
		</tr>
		<tr>
			<th class="thEdit Rt"><mc:msg key="fwk.bbs.header.attachedFile"/></th>
			<td class="tdEdit" colspan="3">
				<table class="tblDefault withPadding">
					<tr>
						<td>
<%
				if (fileDataSet.getRowCnt() > 0) {
					for (int i=0; i<fileDataSet.getRowCnt(); i++) {
						String repositoryPath = fileDataSet.getValue(i, "REPOSITORY_PATH");
						String originalName = fileDataSet.getValue(i, "ORIGINAL_NAME");
						String newName = fileDataSet.getValue(i, "NEW_NAME");
						String icon = fileDataSet.getValue(i, "FILE_ICON");
						String delimiter = "";
						double fileSize = CommonUtil.toDouble(fileDataSet.getValue(i, "FILE_SIZE")) / 1024;

						if (i != 0) {
							delimiter = ", &nbsp;";
						}
%>
							<%=delimiter%>
							<img src="<%=icon%>" style="margin-top:-4px;"/>
							<a class="aEn" onclick="exeDownload('<%=repositoryPath%>', '<%=originalName%>', '<%=newName%>')">
								<%=fileDataSet.getValue(i, "ORIGINAL_NAME")%> (<%=CommonUtil.getNumberMask(fileSize)%> KB)
							</a>
<%
					}
				}
%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div class="breaker" style="height:5px"></div>
	<table class="tblDefault" style="width:100%;">
		<tr>
			<td class="tdDefault">
				<ui:txa name="articleContents" style="height:280px;" value="<%=freeBoard.getArticleContents()%>"/>
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
<div id="divBodyRight" class="ui-layout-east"><%@ include file="/zebra/example/common/include/bodyRight.jsp"%></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/zebra/example/common/include/footer.jsp"%></div>
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