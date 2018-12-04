<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	pageContext.setAttribute("leftMenu", ZebraMenuManager.getLeftMenu((String)session.getAttribute("headerMenuId")));
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<style type="text/css">
</style>
<script type="text/javascript">
$(function() {
	doLeftMenu = function(menuId, menuName, menuUrl) {
		$("#hdnLeftMenuId").val(menuId);
		$("#hdnLeftMenuName").val(menuName);
		$("#hdnLeftMenuUrl").val(menuUrl);

		commonJs.clearSearchCriteria();
		commonJs.clearPaginationValue();
		commonJs.doSubmit({form:$("form:eq(0)"), action:menuUrl});
	};

	$(window).load(function() {
// 		$("#divBodyLeftMenuPanel").accordion({
// 			icons:null
// 		});
	});
});
</script>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<%/************************************************************************************************
* Real Contents
************************************************************************************************/%>
<div id="divBodyLeftMenuPanel">
<c:if test="${sessionScope.headerMenuId != '' && leftMenu.subMenu != null}">
	<div id="divLeftMenuHeader">
		<a style="background:url(<mc:cp key="imgThemeCom"/>/${leftMenu['icon']}_Black.png) no-repeat 0px 50%;padding:4px 0px 4px 25px;">${sessionScope.headerMenuName}</a>
	</div>
	<div id="divLeftMenu">
	<c:set var="str" value="menuName_${sessionScope.langCode}" scope="page"></c:set>
	<c:forEach var="menu" items="${pageScope.leftMenu.subMenu}" varStatus="status">
		<li id="liLeftMenu${menu['menuId']}"
			class="leftMenus<c:if test="${sessionScope.headerMenuId == leftMenu['menuId'] && sessionScope.leftMenuId == menu['menuId']}">Selected</c:if>"
			onclick="doLeftMenu('${menu['menuId']}', '${menu[str]}', '${menu['menuUrl']}')">
			<span class="glyphicon glyphicon-chevron-right" style="opacity:0.8"></span>&nbsp;&nbsp;${menu[str]}
		</li>
	</c:forEach>
	</div>
</c:if>
</div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
<input type="hidden" id="hdnLeftMenuId" name="hdnLeftMenuId" value="${sessionScope.leftMenuId}"/>
<input type="hidden" id="hdnLeftMenuName" name="hdnLeftMenuName" value="${sessionScope.leftMenuName}"/>
<input type="hidden" id="hdnLeftMenuUrl" name="hdnLeftMenuUrl" value="${sessionScope.leftMenuUrl}"/>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>