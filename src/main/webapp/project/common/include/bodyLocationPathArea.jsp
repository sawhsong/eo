<%/************************************************************************************************
* Description
* - Location Path Area
************************************************************************************************/%>
<%
	SysUser sysUserLocationPathArea = (SysUser)session.getAttribute("SysUser");
	String authGroupIdLocationPath = sysUserLocationPathArea.getAuthGroupId();
	String headerMenuIdLocationPath = (String)session.getAttribute("headerMenuId");
	boolean isVisibleAdminToolLocationPath = CommonUtil.toBoolean((String)session.getAttribute("isVisibleAdminTool"));
%>

<script type="text/javascript">
$(function() {
	doControlAdminTool = function(flag) {
		commonJs.ajaxSubmit({
			url:"/login/controlAdminTool.do",
			dataType:"json",
			data:{flag:flag},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					goMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}', '${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}');
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	goMenu = function(headerMenuId, headerMenuName, headerMenuUrl, leftMenuId, leftMenuName, leftMenuUrl) {
		$("#hdnHeaderMenuId").val(headerMenuId);
		$("#hdnHeaderMenuName").val(headerMenuName);
		$("#hdnHeaderMenuUrl").val(headerMenuUrl);
		$("#hdnLeftMenuId").val(leftMenuId);
		$("#hdnLeftMenuName").val(leftMenuName);
		$("#hdnLeftMenuUrl").val(leftMenuUrl);

		commonJs.doSubmit({form:$("form:eq(0)"), action:leftMenuUrl});
	};
});
</script>

<c:if test="${!(sessionScope.headerMenuId == null || sessionScope.headerMenuId == '')}">
<div id="divLocationPathContainer" class="locationPathContainer">
	<span class="fa fa-home fa-lg locationPathContainerIcon"></span>&nbsp;
	<a onclick="doMainMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}')">${sessionScope.headerMenuName}</a>

	<c:if test="${!(sessionScope.leftMenuId == null || sessionScope.leftMenuId == '')}">
		&nbsp;<span class="fa fa-angle-right fa-lg locationPathContainerIcon"></span>&nbsp;
		<a onclick="doLeftMenu('${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}')">${sessionScope.leftMenuName}</a>
	</c:if>

<%
	if ((CommonUtil.equalsIgnoreCase(headerMenuIdLocationPath, "RKM") || CommonUtil.equalsIgnoreCase(headerMenuIdLocationPath, "CST") ||
		CommonUtil.equalsIgnoreCase(headerMenuIdLocationPath, "RPT")) && (CommonUtil.equalsIgnoreCase(authGroupIdLocationPath, "0"))) {
		if (isVisibleAdminToolLocationPath) {
%>
	<div style="float:right;">
		<a onclick="doControlAdminTool('false')" style="color:blue;"><mc:msg key="page.com.hideAdminTool"/></a>
	</div>
<%
		} else {
%>
	<div style="float:right;">
		<a onclick="doControlAdminTool('true')" style="color:blue;"><mc:msg key="page.com.showAdminTool"/></a>
	</div>
<%
		}
	}
%>
</div>
</c:if>