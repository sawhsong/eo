<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<c:if test="${!(sessionScope.headerMenuId == null || sessionScope.headerMenuId == '')}">
	<div id="divLocationPathContainer" class="locationPathContainer">
		<span class="fa fa-home fa-lg locationPathContainerIcon"></span>&nbsp;
		<a onclick="doMainMenu('${sessionScope.headerMenuId}', '${sessionScope.headerMenuName}', '${sessionScope.headerMenuUrl}')">${sessionScope.headerMenuName}</a>

		<c:if test="${!(sessionScope.leftMenuId == null || sessionScope.leftMenuId == '')}">
			&nbsp;<span class="fa fa-angle-right fa-lg locationPathContainerIcon"></span>&nbsp;
			<a onclick="doLeftMenu('${sessionScope.leftMenuId}', '${sessionScope.leftMenuName}', '${sessionScope.leftMenuUrl}')">${sessionScope.leftMenuName}</a>
		</c:if>
	</div>
</c:if>