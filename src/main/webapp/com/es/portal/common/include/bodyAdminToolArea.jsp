<%/************************************************************************************************
* Description
* - Admin Tool Area
************************************************************************************************/%>
<%
	String securityRoleAdminToolArea = (String)session.getAttribute("SecurityRole");
	boolean isVisibleAdminToolArea = CommonUtil.toBoolean((String)session.getAttribute("isVisibleAdminTool"));
%>

<style type="text/css">
</style>
<script type="text/javascript">
$(function() {
	var isVisibleAdminToolArea = commonJs.toBoolean("<%=isVisibleAdminToolArea%>");

	$("#btnReloadUserAdminTool").click(function() {
		setSessionValuesForAdminTool();
	});

	$("#btnReturnUserAdminTool").click(function() {
		removeSessionValuesForAdminTool();
	});

	setSessionValuesForAdminTool = function() {
		if (commonJs.isEmpty($("#loginIdAdminTool").val())) {return;}

		commonJs.ajaxSubmit({
			url:"/login/setSessionValuesForAdminTool",
			dataType:"json",
			data:{userId:$("#loginIdAdminTool").val()},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					$("#orgCategoryAdminTool").val(ds.getValue(0, "org_category"));
					$("#userNameAsAdminTool").val(ds.getValue(0, "user_name"));
					$("#loginIdAsAdminTool").val(ds.getValue(0, "login_id"));
					$("#userIdAdminTool").val(ds.getValue(0, "user_id"));
					$("#orgIdForAdminTool").val(ds.getValue(0, "org_id"));
					$("#orgLegalNameForAdminTool").val(ds.getValue(0, "org_name"));
					$("#orgCategoryDescForAdminTool").val(ds.getValue(0, "org_category_desc"));

					$("#divUsingUserAs").html("User Name As "+ds.getValue(0, "user_name")+" / User Login ID As "+ds.getValue(0, "login_id")+" ("+ds.getValue(0, "org_id")+" / "+ds.getValue(0, "org_name")+" / "+ds.getValue(0, "org_category_desc")+")");
					if ($("#divUsingUserAsBreaker").length <= 0) {
						$("<div id=\"divUsingUserAsBreaker\" class=\"divGblMenuBreak\">&nbsp;</div>").insertAfter($("#divUsingUserAs"));
					}

					setSummaryDataForAdminTool();
					setMainScreenSearchCriteriaForAdminTool();

					try {
						doSearch();
					} catch(e) {}

//					goMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}', '${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}');
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	removeSessionValuesForAdminTool = function() {
		commonJs.ajaxSubmit({
			url:"/login/removeSessionValuesForAdminTool.do",
			dataType:"json",
			data:{userId:""},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					$("#userNameAsAdminTool").val("");
					$("#loginIdAsAdminTool").val("");
					$("#userIdAdminTool").val("");
					$("#orgIdForAdminTool").val("");
					$("#orgLegalNameForAdminTool").val("");
					$("#orgCategoryDescForAdminTool").val("");

					$("#divUsingUserAs").html("");
					$("#divUsingUserAsBreaker").remove();

					setSummaryDataForAdminTool();
					setMainScreenSearchCriteriaForAdminTool();

					try {
						doSearch();
					} catch(e) {}

//					goMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}', '${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}');
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	$(window).load(function() {
		commonJs.setAutoComplete($("#userNameAsAdminTool"), {
			method:"getUserName",
			label:"user_name",
			value:"user_id",
			addValElementNames:["orgCategoryAdminTool"],
			focus:function(event, ui) {
				$("#userNameAsAdminTool").val(ui.item.label);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#userNameAsAdminTool").val()) && commonJs.isEmpty($("#loginIdAsAdminTool").val())) {
					$("#userIdAdminTool").val("");
					$("#orgIdForAdminTool").val("");
					$("#orgLegalNameForAdminTool").val("");
					$("#orgCategoryDescForAdminTool").val("");
				}
			},
			select:function(event, ui) {
				$("#userIdAdminTool").val(ui.item.value);
				$("#userNameAsAdminTool").val(ui.item.label);
				setSessionValuesForAdminTool();
				return false;
			}
		});

		commonJs.setAutoComplete($("#loginIdAsAdminTool"), {
			method:"getLoginId",
			label:"login_id",
			value:"user_id",
			addValElementNames:["orgCategoryAdminTool"],
			focus:function(event, ui) {
				$("#loginIdAsAdminTool").val(ui.item.label);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#userNameAsAdminTool").val()) && commonJs.isEmpty($("#loginIdAsAdminTool").val())) {
					$("#userIdAdminTool").val("");
					$("#orgIdForAdminTool").val("");
					$("#orgLegalNameForAdminTool").val("");
					$("#orgCategoryDescForAdminTool").val("");
				}
			},
			select:function(event, ui) {
				$("#userIdAdminTool").val(ui.item.value);
				$("#loginIdAsAdminTool").val(ui.item.label);
				setSessionValuesForAdminTool();
				return false;
			}
		});
	});
});
</script>

<%
if (CommonUtil.equalsIgnoreCase(securityRoleAdminToolArea, "administrator") && isVisibleAdminToolArea) {
%>
<div id="divAdminToolContainer" class="adminToolContainer" style="overflow-y:hidden;">
	<table class="tblAdminTool">
		<caption>Admin Tool</caption>
		<colgroup>
			<col width="20%"/>
			<col width="37%"/>
			<col width="*"/>
		</colgroup>
		<tr>
			<td class="tdAdminTool Rt" style="vertical-align:top;">
				<table class="tblDefault">
					<colgroup>
						<col width="37%"/>
						<col width="*"/>
					</colgroup>
					<tr>
						<td class="tdDefault Rt" style="padding:0px 0px 2px 0px;" colspan="2">
							<ui:buttonGroup id="buttonGroup">
								<ui:button id="btnReloadUserAdminTool" caption="button.com.reload" iconClass="fa-refresh"/>
								<ui:button id="btnReturnUserAdminTool" caption="button.com.return" iconClass="fa-history"/>
							</ui:buttonGroup>
						</td>
					</tr>
					<tr>
						<th class="thDefault lt" style="padding:2px 2px 1px 0px;"><mc:msg key="page.com.loginUserNameAs"/></th>
						<td class="tdDefault lt" style="padding:2px 0px 1px 2px;">
							<input type="text" id="userNameAsAdminTool" name="userNameAsAdminTool" value="${sessionScope.UserNameForAdminTool}" class="txtEn"/>
						</td>
					</tr>
					<tr>
						<th class="thDefault lt" style="padding:1px 2px 1px 0px;"><mc:msg key="page.com.loginUserLoginIdAs"/></th>
						<td class="tdDefault lt" style="padding:1px 0px 1px 2px;">
							<input type="text" id="loginIdAsAdminTool" name="loginIdAsAdminTool" value="${sessionScope.LoginIdForAdminTool}" class="txtEn"/>
						</td>
					</tr>
					<tr>
						<td class="tdDefault" style="padding:4px 0px 0px 0px;" colspan="2">
							<input type="hidden" id="userIdAdminTool" name="loginIdAdminTool" value="${sessionScope.UserIdForAdminTool}" class="txtDpl"/>
							<input type="text" id="orgIdForAdminTool" name="orgIdForAdminTool" class="txtDpl Rt" value="${sessionScope.OrgIdForAdminTool}" disabled style="padding-top:1px;padding-bottom:1px;color:red;font-weight:bold;"/>
							<input type="text" id="orgLegalNameForAdminTool" name="orgLegalNameForAdminTool" class="txtDpl Rt" value="${sessionScope.OrgLegalNameForAdminTool}" disabled style="padding-top:1px;padding-bottom:1px;color:red;font-weight:bold;"/>
							<input type="text" id="orgCategoryDescForAdminTool" name="orgCategoryDescForAdminTool" class="txtDpl Rt" value="${sessionScope.OrgCategoryDescForAdminTool}" disabled style="padding-top:1px;padding-bottom:1px;color:red;font-weight:bold;"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<%
}
%>