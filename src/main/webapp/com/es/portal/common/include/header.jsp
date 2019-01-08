<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	SysUsers sysUserHeaderPage = (SysUsers)session.getAttribute("SysUsers");
	HpPersonD hpPersonDHeaderPage = (HpPersonD)session.getAttribute("HpPersonD");
	String authGroupIdHeaderPage = sysUserHeaderPage.getPortalSecurityRole();
	String startupUrlHeaderPage = (String)session.getAttribute("StartupUrl");

	String userNameHeaderPage = hpPersonDHeaderPage.getFullName();
	String selectedHeaderMenuHeaderPage = (String)session.getAttribute("headerMenuId");

	DataSet dsMenuHeaderPage = MenuManager.getMenu(authGroupIdHeaderPage, "", "1", "1");

	String loginIdForAdminToolHeaderPage = (String)session.getAttribute("LoginIdForAdminTool");
%>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<script type="text/javascript">
var popupUserProfile;
var startupUrl = "<%=startupUrlHeaderPage%>";

$(function() {
	$("#aLogo").click(function(event) {
		$("#hdnHeaderMenuId").val("");
		$("#hdnHeaderMenuName").val("");
		$("#hdnHeaderMenuUrl").val("");

		commonJs.doSubmit({form:$("form:eq(0)"), action:startupUrl});
	});

	doMainMenu = function(menuId, menuName, menuUrl) {
		if (menuUrl == "#" || menuId == "HOME") {
			menuUrl = startupUrl;
		}
		$("#hdnHeaderMenuId").val(menuId);
		$("#hdnHeaderMenuName").val(menuName);
		$("#hdnHeaderMenuUrl").val(menuUrl);

		commonJs.doSubmit({form:$("form:eq(0)"), action:menuUrl});
	};

	setLoginUserContextMenu = function() {
		ctxMenu.loggedInUser[0].fun = function() {getMyProfile();};
		ctxMenu.loggedInUser[1].fun = function() {logout();};
		$("#btnLoggedInUser").contextMenu(ctxMenu.loggedInUser, {
			classPrefix:com.constants.ctxClassPrefixHeader,
			effectDuration:300,
			effect:"slide",
			displayAround:"trigger",
			position:"bottom",
			verAdjust:1,
			onClose:function() {
				$("#divLoggedInUser").removeClass("selected");
			}
		});
	};

	getMyProfile = function() {
		popupUserProfile = commonJs.openPopup({
			popupId:"UserProfile",
			url:"/login/getUserProfile",
			paramData:{},
			header:"User Profile",
			blind:true,
			width:1100,
			height:560
		});
	};

	logout = function() {
		commonJs.doSubmit({form:$("form:eq(0)"), action:"/login/logout"});
	};

	$(window).load(function() {
		setLoginUserContextMenu();
	});
});
</script>
<%/************************************************************************************************
* Real Contents
************************************************************************************************/%>
<div id="divHeaderLeft"></div>
<div id="divHeaderCenter">
	<div id="divGlobalMenuHeaderGroup">
		<div id="divGlobalMenuLeft">
			<div id="divLogoArea">
				<a id="aLogo"></a>
			</div>
		</div>
		<div id="divGlobalMenuRight">
			<div id="divGblMenuArea">
<%
				if (CommonUtil.isNotBlank(loginIdForAdminToolHeaderPage)) {
%>
				<div id="divUsingUserAs" class="headerGblMenus" style="color:#D92E24;cursor:default;top:50%;transform:translateY(50%)">
					User Login ID As : ${sessionScope.LoginIdForAdminTool} / User Name As : ${sessionScope.UserFullNameForAdminTool} / User Emp Org ID As : ${sessionScope.EmpOrgIdForAdminTool}
				</div>
				&nbsp;&nbsp;&nbsp;
<%
				} else {
%>
				<div id="divUsingUserAs" class="headerGblMenus" style="color:#D92E24;cursor:default;"></div>
				&nbsp;&nbsp;&nbsp;
<%
				}
%>
				<ui:button id="btnLoggedInUser" caption="<%=userNameHeaderPage%>" iconClass="fa-user-md"/>
			</div>
		</div>
	</div>
	<div id="divMainMenuHeaderGroup">
		<div id="divMainMenuAreaLeft">
<%
		for (int i=0; i<dsMenuHeaderPage.getRowCnt(); i++) {
			if (CommonUtil.equals(dsMenuHeaderPage.getValue(i, "LEVEL"), "1")) {
				String menuId = dsMenuHeaderPage.getValue(i, "MENU_ID");
				String menuName = dsMenuHeaderPage.getValue(i, "MENU_NAME");
				String menuUrl = dsMenuHeaderPage.getValue(i, "MENU_URL");
				String icon = dsMenuHeaderPage.getValue(i, "MENU_ICON");
				String selected = (CommonUtil.equals(menuId, selectedHeaderMenuHeaderPage)) ? "Selected" : "";
%>
				<div id="div<%=menuId%>" class="headerMainMenus<%=selected%>" onclick="doMainMenu('<%=menuId%>', '<%=menuName%>', '<%=menuUrl%>')">
					<a style="background:url(<mc:cp key="imgThemeCom"/>/<%=icon%>_<mc:cp key="headMainMenuIconColor"/>.png) no-repeat 0px 0px"><%=menuName%></a>
				</div>
<%
			}
		}
%>
		</div>
		<div id="divMainMenuAreaRight"></div>
	</div>
</div>
<div id="divHeaderRight"></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
<input type="hidden" id="hdnHeaderMenuId" name="hdnHeaderMenuId" value="${sessionScope.headerMenuId}"/>
<input type="hidden" id="hdnHeaderMenuName" name="hdnHeaderMenuName" value="${sessionScope.headerMenuName}"/>
<input type="hidden" id="hdnHeaderMenuUrl" name="hdnHeaderMenuUrl" value="${sessionScope.headerMenuUrl}"/>