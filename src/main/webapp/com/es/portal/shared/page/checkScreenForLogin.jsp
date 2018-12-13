<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%
	String sessionCheckType = (String)request.getAttribute("sessionCheckType");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%@ include file="/com/es/portal/shared/page/incCssJs.jsp"%>
<script type="text/javascript">
jsconfig.put("noLayoutWindow", true);
var sessionCheckType = "<%=sessionCheckType%>";
$(function() {
	if (parent.$(".nonyPopWinBase").length > 0 || parent.$(".nonyDialogBase").length > 0) {
		if (sessionCheckType == "SessionTimedOut") {
			commonJs.openDialog({
				contents:com.message.W001,
				buttons:[{
					caption:com.caption.ok,
					callback:function() {
						$("#fmDefault").attr("target", "_parent").submit();
					}
				}],
				draggable:false,
				width:330,
				blind:true
			});
		} else {
			$("#fmDefault").attr("target", "_parent").submit();
		}
	} else {
		if (sessionCheckType == "SessionTimedOut") {
			commonJs.openDialog({
				contents:com.message.W001,
				buttons:[{
					caption:com.caption.ok,
					callback:function() {
						$("#fmDefault").submit();
					}
				}],
				draggable:false,
				width:330,
				blind:true
			});
		} else {
			$("#fmDefault").submit();
		}
	}
});
</script>
</head>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="/login/userlogin"></form>
</body>
</html>