/**
 * indexDashboard.js
 */
jsconfig.put("noWest", true);
$(function() {
	/*!
	 * event
	 */
	$("#tblGridNotice .aEn").click(function(event) {
		goMenu('BBS', 'Bulletin Board', '#', 'BBS0202', 'Announcement', '/bbs/0202/getDefault.do');
	});

	$("#tblGridFreeBoard .aEn").click(function(event) {
		goMenu('BBS', 'Bulletin Board', '#', 'BBS0204', 'Free Board', '/bbs/0204/getDefault.do');
	});

	$("#tblGridIncome .aEn").click(function(event) {
		goMenu('RKM', 'Record Keeping', '#', 'RKM0202', 'Sales Income', '/rkm/0202/getDefault.do');
	});

	$("#tblGridExpense .aEn").click(function(event) {
		goMenu('RKM', 'Record Keeping', '#', 'RKM0402', 'General Expense', '/rkm/0402/getDefault.do');
	});
	/*!
	 * process
	 */
	goMenu = function(headerMenuId, headerMenuName, headerMenuUrl, leftMenuId, leftMenuName, leftMenuUrl) {
		$("#hdnHeaderMenuId").val(headerMenuId);
		$("#hdnHeaderMenuName").val(headerMenuName);
		$("#hdnHeaderMenuUrl").val(headerMenuUrl);
		$("#hdnLeftMenuId").val(leftMenuId);
		$("#hdnLeftMenuName").val(leftMenuName);
		$("#hdnLeftMenuUrl").val(leftMenuUrl);

		commonJs.doSubmit({form:$("form:eq(0)"), action:leftMenuUrl});
	}
	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.setAccordion({
			containerClass:"accordion",
			multipleExpand:true,
			expandAll:true,
			icons:null
		});
	});
});