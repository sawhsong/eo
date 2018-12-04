/**
 * Predefined context menu items
 */
var ctxMenu = {
	// LoggedIn global menu
	loggedInUser : [{
		name:com.caption.ctxMyProfile,
		img:jsconfig.get("imgThemeCom")+"/icnUser-MyProfile_Black.png",
//		title:"My Profile",
		fun:function() {}
	}, {
		name:com.caption.ctxLogOut,
		img:jsconfig.get("imgThemeCom")+"/icnUser-LogOut_Black.png",
//		title:"Log Out",
		fun:function() {}
	}],

	// Common action context menu
	commonAction : [{
		name:com.caption.ctxDetail,
		img:"fa-list-alt",
		fun:function() {}
	}, {
		name:com.caption.ctxEdit,
		img:"fa-edit",
		fun:function() {}
	}, {
		name:com.caption.ctxDelete,
		img:"fa-times",
		fun:function() {}
	}],

	// Export context menu
	commonExport : [{
		name:com.caption.ctxExportExcelAll,
		fileType:"Excel",
		dataRange:"All",
		img:"fa-file-excel-o",
		fun:function() {}
	}, {
		name:com.caption.ctxExportExcelCurrentPage,
		fileType:"Excel",
		dataRange:"Current",
		img:"fa-file-excel-o",
		fun:function() {}
	}, {
		name:com.caption.ctxExportPdfAll,
		fileType:"PDF",
		dataRange:"All",
		img:"fa-file-pdf-o",
		fun:function() {}
	}, {
		name:com.caption.ctxExportPdfCurrentPage,
		fileType:"PDF",
		dataRange:"Current",
		img:"fa-file-pdf-o",
		fun:function() {}
	}, {
		name:com.caption.ctxExportHtmlAll,
		fileType:"HTML",
		dataRange:"All",
		img:"fa-file-code-o",
		fun:function() {}
	}, {
		name:com.caption.ctxExportHtmlCurrentPage,
		fileType:"HTML",
		dataRange:"Current",
		img:"fa-file-code-o",
		fun:function() {}
	}],

	// Board(Notice, BBS) Action context menu
	boardAction : [{
		name:com.caption.ctxDetail,
		img:"fa-list-alt",
		fun:function() {}
	}, {
		name:com.caption.ctxEdit,
		img:"fa-edit",
		fun:function() {}
	}, {
		name:com.caption.ctxReply,
		img:"fa-reply-all",
		fun:function() {}
	}, {
		name:com.caption.ctxDelete,
		img:"fa-times",
		fun:function() {}
	}],

	// DTOGenerator Action context menu
	dtoGeneratorAction : [{
		name:com.caption.ctxDetail,
		img:"fa-list-alt",
		fun:function() {}
	}, {
		name:com.caption.ctxGenerate,
		img:"fa-gears",
		fun:function() {}
	}]
};