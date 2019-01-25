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
	$("#btnReloadUserAdminTool").click(function() {
		setSessionValuesForAdminTool();
	});

	$("#btnReturnUserAdminTool").click(function() {
		removeSessionValuesForAdminTool();
	});

	setSessionValuesForAdminTool = function() {
		if (commonJs.isEmpty($("#loginIdAsAdminTool").val()) && commonJs.isEmpty($("#personIdAsAdminTool").val())) {return;}

		commonJs.ajaxSubmit({
			url:"/login/setSessionValuesForAdminTool",
			dataType:"json",
			data:{
				loginId:$("#loginIdAsAdminTool").val(),
				personId:$("#personIdAsAdminTool").val()
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					$("#loginIdAsAdminTool").val(ds.getValue(0, "login_id"));
					$("#personIdAsAdminTool").val(ds.getValue(0, "person_id"));
					$("#userFullNameAsAdminTool").val(ds.getValue(0, "user_full_name"));
					$("#divUsingUserAs").html("User Login ID As : "+ds.getValue(0, "login_id")+" / User Person ID As : "+ds.getValue(0, "person_id")+" / User Name As : "+ds.getValue(0, "user_full_name")+" / User Emp Org ID As : "+ds.getValue(0, "emp_org_id"));

					try {
						goMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}', '${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}');
					} catch(e) {}
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	removeSessionValuesForAdminTool = function() {
		commonJs.ajaxSubmit({
			url:"/login/removeSessionValuesForAdminTool",
			dataType:"json",
			data:{userId:""},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					$("#loginIdAsAdminTool").val("");
					$("#userFullNameAsAdminTool").val("");
					$("#divUsingUserAs").html("");

					try {
						goMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}', '${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}');
					} catch(e) {}
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	$(window).load(function() {
		commonJs.setAutoComplete($("#loginIdAsAdminTool"), {
			method:"getSysUsersByLoginId",
			label:"user_name",
			value:"user_name",
			minLength:2,
			focus:function(event, ui) {
				$("#personIdAsAdminTool").val("");
				$("#loginIdAsAdminTool").val(ui.item.label);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#loginIdAsAdminTool").val())) {
					$("#personIdAsAdminTool").val("");
					$("#userFullNameAsAdminTool").val("");
				}
			},
			select:function(event, ui) {
				$("#loginIdAsAdminTool").val(ui.item.value);
				setSessionValuesForAdminTool();
				return false;
			}
		});

		commonJs.setAutoComplete($("#personIdAsAdminTool"), {
			method:"getSysUsersByPersonId",
			label:"person_id",
			value:"person_id",
			minLength:2,
			focus:function(event, ui) {
				$("#loginIdAsAdminTool").val("");
				$("#personIdAsAdminTool").val(ui.item.label);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#personIdAsAdminTool").val())) {
					$("#loginIdAsAdminTool").val("");
					$("#userFullNameAsAdminTool").val("");
				}
			},
			select:function(event, ui) {
				$("#personIdAsAdminTool").val(ui.item.value);
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
		<tr>
			<td class="tdAdminTool">
				<table class="tblDefault withPadding">
					<colgroup>
						<col width="70%"/>
						<col width="*"/>
					</colgroup>
					<tr>
						<td class="tdDefault Lt">
							<label for="loginIdAsAdminTool" class="lblEn hor">Login ID</label>
							<div style="float:left;padding-right:4px;">
								<ui:text name="loginIdAsAdminTool" className="hor" value="${sessionScope.LoginIdForAdminTool}" style="width:180px"/>
							</div>
							<div class="horGap20"></div>
							<label for="loginIdAsAdminTool" class="lblEn hor">Person ID</label>
							<div style="float:left;padding-right:4px;">
								<ui:text name="personIdAsAdminTool" className="hor" value="${sessionScope.PersonIdForAdminTool}" style="width:180px"/>
							</div>
							<ui:text name="userFullNameAsAdminTool" className="hor" status="display" value="${sessionScope.UserFullNameForAdminTool}" style="width:350px"/>
						</td>
						<td class="tdDefault Rt">
							<ui:buttonGroup id="buttonGroup">
								<ui:button id="btnReloadUserAdminTool" caption="button.com.reload" iconClass="fa-refresh"/>
								<ui:button id="btnReturnUserAdminTool" caption="button.com.return" iconClass="fa-history"/>
							</ui:buttonGroup>
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