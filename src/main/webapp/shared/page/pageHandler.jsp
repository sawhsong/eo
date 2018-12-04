<%/************************************************************************************************
* Description
* - pageHandler
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%@ page import="zebra.data.DataSet, zebra.data.ParamEntity, zebra.util.CommonUtil"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/shared/page/incCssJs.jsp"%>
<%
	ParamEntity paramEntity = (ParamEntity)request.getAttribute("paramEntity");
	DataSet requestDataSet = (DataSet)paramEntity.getRequestDataSet();
	DataSet searchCriteriaDataSet = (DataSet)paramEntity.getSearchCriteriaDataSet();

	String action = CommonUtil.nvl((String)paramEntity.getObject("action"));
	String script = CommonUtil.nvl((String)paramEntity.getObject("script"));
	String messageCode = CommonUtil.nvl((String)paramEntity.getObject("messageCode"));
	String message = CommonUtil.nvl((String)paramEntity.getObject("message"));
	String target = CommonUtil.nvl((String)paramEntity.getObject("target"));

	String pagehandlerActionType = ConfigUtil.getProperty("pagehandler.actionType");
%>
<script type="text/javascript">
jsconfig.put("noLayoutWindow", true);

$(function() {
	var pagehandlerActionType = jsconfig.get("pagehandlerActionType");
	var action = "<%=action%>";
	var target = "<%=target%>";
	var messageCode = "<%=messageCode%>";
	var message = "<%=message%>";
	var dialogType = "";

	doProcess = function() {
		<%=script%>

		if (!$.nony.isEmpty(target)) {
			$("#fmDefault").attr("target", target);
		}

		if (!$.nony.isEmpty(action)) {
			$("#fmDefault").attr("action", action).submit();
		}
	};

	$(window).load(function() {
		if ($.nony.isEmpty(messageCode) || messageCode.indexOf("I") != -1) {
			dialogType = com.message.I000;
		} else if (messageCode.indexOf("E") != -1) {
			dialogType = "error";
		} else {
			dialogType = com.message.I000;
		}

		if (pagehandlerActionType == "popup") {
			if (!$.nony.isEmpty(message)) {
				var param = {
					type:dialogType,
					header:"Process Result",
					contents:"["+messageCode+"] "+message,
					effect:"fade",
					draggable:false,
					modal:true,
					blind:false,
					width:330,
					oncloseCallback:function() {
						doProcess();
					}
				};
				$.nony.openDialog(param);
			} else {
				doProcess();
			}
		} else if (pagehandlerActionType == "message") {
			if (!$.nony.isEmpty(message)) {
				$.nony.printLog({
					message:message,
					onClose:function() {
						doProcess();
					}
				});
			}
		}
	});
});
</script>
</head>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="" target="" style="overflow:none;">
<%
if (requestDataSet != null && requestDataSet.getRowCnt() > 0) {
	for (int i=0; i<requestDataSet.getColumnCnt(); i++) {
%>
	<input type="hidden" id="<%=requestDataSet.getName(i)%>" name="<%=requestDataSet.getName(i)%>" class="txtRead" value="<%=requestDataSet.getValue(i)%>" readonly="readonly"/>
<%
	}
}
%>
<%
if (searchCriteriaDataSet != null && searchCriteriaDataSet.getRowCnt() > 0) {
	for (int i=0; i<searchCriteriaDataSet.getColumnCnt(); i++) {
%>
	<input type="hidden" id="<%=searchCriteriaDataSet.getName(i)%>" name="<%=searchCriteriaDataSet.getName(i)%>" class="txtRead" value="<%=searchCriteriaDataSet.getValue(i)%>" readonly="readonly"/>
<%
	}
}
%>
<%
if (CommonUtil.equalsIgnoreCase(pagehandlerActionType, "message") && CommonUtil.isNotBlank(message)) {
%>
	<input type="hidden" id="processMessageForFramework_AutoSearchCriteria" name="processMessageForFramework_AutoSearchCriteria" class="txtRead" value="<%=message%>" readonly="readonly"/>
<%
}
%>
</form>
</body>
</html>