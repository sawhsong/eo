<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	String headerMenuId = (String)session.getAttribute("headerMenuId");
	String headerMenuName = (String)session.getAttribute("headerMenuName");
	String leftMenuId = (String)session.getAttribute("leftMenuId");
	String leftMenuName = (String)session.getAttribute("leftMenuName");
	DataSet dsLeftMenu = MenuManager.getMenu(authGroupIdHeaderPage, headerMenuId, "2", "");
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
	$(".leftMenuAccordionGroup h3").click(function() {
		$(".leftMenuAccordionGroup h3").each(function() {
			$(this).removeClass("selected");
		});

		if (!$(this).hasClass("ui-state-active")) {
			$(this).addClass("selected");
		}
	});

	doLeftMenu = function(menuId, menuName, menuUrl) {
		$("#hdnLeftMenuId").val(menuId);
		$("#hdnLeftMenuName").val(menuName);
		$("#hdnLeftMenuUrl").val(menuUrl);

		commonJs.clearSearchCriteria();
		commonJs.clearPaginationValue();
		commonJs.doSubmit({form:$("form:eq(0)"), action:menuUrl});
	};

	setActive = function() {
		var activeMenuIndex = -1;

		$(".leftMenuAccordionGroup").each(function(i) {
			$(this).find("li").each(function(j) {
				if ($(this).hasClass("leftMenusSelected")) {
					activeMenuIndex = i;
					return false;
				}
			});
		});

		$(".leftMenuAccordionGroup").each(function(i) {
			if (i == activeMenuIndex) {
				var header = $(this).find("h3");

				if (!$(header).hasClass("ui-state-active")) {
					$(header).trigger("click");
				}
			}
		});
	};

	$(window).load(function() {
		commonJs.setAccordion({
			containerClass:"leftMenuAccordionHolder",
			expandAll:false,
			active:0,
			icons:null,
			multipleExpand:false
		});

		setActive();
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
<%
if (CommonUtil.isNotBlank(headerMenuId)) {
%>
	<div id="divLeftMenuHeader">
		<a style="background:url(<mc:cp key="imgThemeCom"/>/<%=headerMenuId%>_Black.png) no-repeat 0px 50%;padding:4px 0px 4px 25px;"><%=headerMenuName%></a>
	</div>
<%
	if (dsLeftMenu.getRowCnt() > 0) {
%>
	<div class="leftMenuAccordionHolder">
<%
		for (int i=0; i<dsLeftMenu.getRowCnt(); i++) {
			if (CommonUtil.equals(dsLeftMenu.getValue(i, "LEVEL"), "2")) {
%>
		<div class="leftMenuAccordionGroup">
			<h3 style="background:url(<mc:cp key="imgThemeCom"/>/MenuLevel2_Black.png) no-repeat 14px 50%">
				<%=dsLeftMenu.getValue(i, "MENU_NAME_"+languageCodeHeaderPage)%>
			</h3>
			<div class="leftMenuAccordionContents">
<%
				for (int j=0; j<dsLeftMenu.getRowCnt(); j++) {
					if (CommonUtil.equals(dsLeftMenu.getValue(i, "MENU_ID"), dsLeftMenu.getValue(j, "PARENT_MENU_ID"))) {
						String selected = "", menuId = "", menuName = "", menuUrl = "";

						if (CommonUtil.equals(headerMenuId, dsLeftMenu.getValue(i, "PARENT_MENU_ID")) && CommonUtil.equals(leftMenuId, dsLeftMenu.getValue(j, "MENU_ID"))) {
							selected = "Selected";
						}

						menuId = dsLeftMenu.getValue(j, "MENU_ID");
						menuName = dsLeftMenu.getValue(j, "MENU_NAME_"+languageCodeHeaderPage);
						menuUrl = dsLeftMenu.getValue(j, "MENU_URL");
%>
				<li id="liLeftMenu<%=dsLeftMenu.getValue(j, "MENU_ID")%>" class="leftMenus<%=selected%>" onclick="doLeftMenu('<%=menuId%>', '<%=menuName%>', '<%=menuUrl%>')">
					<span class="glyphicon glyphicon-chevron-right" style="opacity:0.8"></span>&nbsp;&nbsp;<%=menuName%>
				</li>
<%
					}
				}
%>
			</div>
		</div>
<%
			}
		}
%>
	</div>
<%
	}
}
%>
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