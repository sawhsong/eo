<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	pageContext.setAttribute("globalMenu", ZebraMenuManager.getGlobalMenu());
	pageContext.setAttribute("mainMenu", ZebraMenuManager.getMainMenu());

	SysUsers sysUsers = (SysUsers)session.getAttribute("SysUsers");
	DataSet themeType = ZebraCommonCodeManager.getCodeDataSetByCodeType("USER_THEME_TYPE");

	String userName = sysUser.getUserName();
	String userId = sysUser.getUserId();
%>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<style type="text/css">
</style>
<script type="text/javascript">
var garbageCollectorPopup = null;

$(function() {
	$("#aLoggedInUser").click(function() {
		$("#divLoggedInUser").addClass("selected");
		$("#divLoggedInUser").trigger("click");
	});

	$("#aLogoFramework").click(function(event) {
		$("#hdnHeaderMenuId").val("");
		$("#hdnHeaderMenuName").val("");
		$("#hdnHeaderMenuUrl").val("");
		$("#hdnLeftMenuId").val("");
		$("#hdnLeftMenuName").val("");
		$("#hdnLeftMenuUrl").val("");

		commonJs.doSubmit({form:$("form:eq(0)"), action:"/zebra/main/getDefault.do"});
	});

	doGlobalMenu = function(menuId, menuName, menuUrl) {
		if (menuUrl.toLowerCase().indexOf("garbage") != -1) {
			garbageCollectorPopup = commonJs.openPopup({
				popupId:"garbageCollector",
				url:menuUrl,
				header:framework.header.garbageCollection,
				width:640,
				height:460,
				draggable:true,
				modal:true,
				blind:true
			});
		} else {
			$("#hdnHeaderMenuId").val("");
			$("#hdnHeaderMenuName").val("");
			$("#hdnHeaderMenuUrl").val("");
			$("#hdnLeftMenuId").val("");
			$("#hdnLeftMenuName").val("");
			$("#hdnLeftMenuUrl").val("");

			commonJs.doSubmit({form:$("form:eq(0)"), action:menuUrl});
		}
	};

	doMainMenu = function(menuId, menuName, menuUrl) {
		$("#hdnHeaderMenuId").val(menuId);
		$("#hdnHeaderMenuName").val(menuName);
		$("#hdnHeaderMenuUrl").val(menuUrl);
		$("#hdnLeftMenuId").val("");
		$("#hdnLeftMenuName").val("");
		$("#hdnLeftMenuUrl").val("");

		commonJs.doSubmit({form:$("form:eq(0)"), action:menuUrl});
	};

	setThemeSelectorContextMenu = function() {
		var theme = commonJs.getDataSetFromJavaDataSet("<%=themeType.toStringForJs()%>");
		var themeMenu = [];

		for (var i=0; i<theme.getRowCnt(); i++) {
			themeMenu.push({
				name:theme.getValue(i, "DESCRIPTION_EN"),
				img:"<mc:cp key="imgIcon"/>/"+commonJs.lowerCase(theme.getValue(i, "COMMON_CODE"))+".png",
				themeId:commonJs.lowerCase(theme.getValue(i, "COMMON_CODE")),
				fun:function() {
					var index = $(this).index();

					commonJs.doSubmit({
						data:{
							themeId:themeMenu[index].themeId
						},
						action:"/zebra/main/getDefault.do"
					});
				}
			});
		}

		$("#aThemeSelector").contextMenu(themeMenu, {
			classPrefix:com.constants.ctxClassPrefixTheme,
			effectDuration:300,
			effect:"slide",
			borderRadius:"bottom 5px",
			displayAround:"trigger",
			position:"bottom",
			heightAdjust:5,
			verAdjust:-3
		});
	};

	setLoginUserContextMenu = function() {
		ctxMenu.loggedInUser[0].fun = function() {getMyProfile("<%=userId%>");};
		ctxMenu.loggedInUser[1].fun = function() {logout();};
		$("#divLoggedInUser").contextMenu(ctxMenu.loggedInUser, {
			classPrefix:com.constants.ctxClassPrefixHeader,
			effectDuration:300,
			effect:"slide",
			borderRadius:"bottom 3px",
			displayAround:"trigger",
			position:"bottom",
			verAdjust:0,
			onClose:function() {
				$("#divLoggedInUser").removeClass("selected");
			}
		});
	};

	getMyProfile = function(userId) {
		popupUserProfile = commonJs.openPopup({
			popupId:"UserProfile",
			url:"/login/getUserProfile.do",
			paramData:{
				userId:userId
			},
			header:"User Profile Detail",
			blind:true,
			width:720,
			height:340
		});
	};

	logout = function() {
		commonJs.doSubmit({form:$("form:eq(0)"), action:"/login/logout.do"});
	};

	$(window).load(function() {
		setLoginUserContextMenu();
		setThemeSelectorContextMenu();
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
				<a id="aLogoFramework">ZEBRA [SAWH Java Framework]</a>
			</div>
		</div>
		<div id="divGlobalMenuRight">
			<div id="divGblMenuAreaFramework">
				<div id="divThemeSelector" class="headerGblMenus">
					<a id="aThemeSelector">${sessionScope.themeName}</a>
				</div>
				<div class="divGblMenuBreak"></div>
				<c:forEach var="menu" items="${pageScope.globalMenu}" varStatus="status">
				<div id="${menu.value['menuId']}" class="headerGblMenus">
					<c:set var="menuName" value="menuName_${sessionScope.langCode}" scope="page"></c:set>
					<a onclick="doGlobalMenu('${menu.value['menuId']}', '${menu.value[menuName]}', '${menu.value['menuUrl']}')">${menu.value[menuName]}</a>
				</div>
				<c:if test="${!status.last}"><div class="divGblMenuBreak"></div></c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	<div id="divMainMenuHeaderGroup">
		<div id="divMainMenuAreaLeft">
		<c:forEach var="menu" items="${pageScope.mainMenu}" varStatus="status">
			<c:if test="${menu.value['menuLevel'] eq '1'}">
				<c:set var="menuName" value="menuName_${sessionScope.langCode}" scope="page"/>
				<c:set var="active" value="${(menu.value['menuId'] eq sessionScope.headerMenuId) ? 'Selected' : ''}" />
				<div id="div${menu.value['menuId']}" class="headerMainMenus${active}" onclick="doMainMenu('${menu.value['menuId']}', '${menu.value[menuName]}', '${menu.value['menuUrl']}')">
					<a style="background:url(<mc:cp key="imgThemeCom"/>/${menu.value['icon']}_<mc:cp key="headMainMenuIconColor"/>.png) no-repeat 0px 50%;padding:4px 0px 4px 25px;">
						${menu.value[menuName]}
					</a>
				</div>
			</c:if>
		</c:forEach>
		</div>
		<div id="divMainMenuAreaRight">
			<div id="divLoggedInUser" class="headerMainMenus">
				<a id="aLoggedInUser"><%=userName%></a>
			</div>
		</div>
	</div>
</div>
<div id="divHeaderRight"></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
<input type="hidden" id="hdnHeaderMenuId" name="hdnHeaderMenuId" value="${sessionScope.headerMenuId}"/>
<input type="hidden" id="hdnHeaderMenuName" name="hdnHeaderMenuName" value="${sessionScope.headerMenuName}"/>
<input type="hidden" id="hdnHeaderMenuUrl" name="hdnHeaderMenuUrl" value="${sessionScope.headerMenuUrl}"/>