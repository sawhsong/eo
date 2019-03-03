/**
 * 
 */
var popup = null;
var attchedFileContextMenu = [];

$(function() {
	/*!
	 * event
	 */
	$("#btnNew").click(function() {
		popup = commonJs.openPopup({
			popupId:"NewExpenseClaim",
			url:"/employee/expense/newExpenseClaim",
			paramData:{
				expenseClaimId:"-1"
			},
			header:"New Expense Claim",
			width:860,
			height:560
		});
	});

	$("#btnSearch").click(function() {
		doSearch();
	});

	/*!
	 * process
	 */
	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	doSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/employee/expense/getExpenseClaimList",
				dataType:"json",
				data:{
				},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						renderGridTable(result);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 300);
	};

	renderGridTable = function(result) {
		var ds = result.dataSet, html = "";

		searchResultDataCount = ds.getRowCnt();
		$("#tblGridBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				var gridTr = new UiGridTr();

				var uiAnc = new UiAnchor();
				uiAnc.setText(ds.getValue(i, "personFullName")).setScript("getDetail('"+ds.getValue(i, "expenseClaimId")+"')");
				gridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "departmentDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "expenseTypeDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "dateOfClaim")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(ds.getValue(i, "amount"), "#,##0.00")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(ds.getValue(i, "gst"), "#,##0.00")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "statusDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "approveRejectPersonName")));

				var gridTd = new UiGridTd();
				gridTd.addClassName("Ct");
				if (ds.getValue(i, "attachmentCount") > 0) {
					var iconAttachFile = new UiIcon();
					iconAttachFile.setId("icnAttachedFile").setName("icnAttachedFile").addClassName("glyphicon-paperclip").addAttribute("expenseClaimId:"+ds.getValue(i, "expenseClaimId"))
						.setScript("getAttachedFile(this)");
					gridTd.addChild(iconAttachFile);
				}
				gridTr.addChild(gridTd);

				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "submittedDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "processedDate")));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:11").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));
		setGridTable();

		$("[name=icnAttachedFile]").each(function(index) {
			$(this).contextMenu(attchedFileContextMenu);
		});

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getAttachedFile = function(img) {
		commonJs.ajaxSubmit({
			url:"/employee/expense/getAttachedFile",
			dataType:"json",
			data:{
				expenseClaimId:$(img).attr("expenseClaimId")
			},
			blind:false,
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					var dataSet = result.dataSet;
					attchedFileContextMenu = [];

					for (var i=0; i<dataSet.getRowCnt(); i++) {
						var documentId = dataSet.getValue(i, "documentId");
						var documentName = dataSet.getValue(i, "documentName");
						var fileIcon = dataSet.getValue(i, "fileIcon");
						var fileSize = dataSet.getValue(i, "fileSize")/1024;

						attchedFileContextMenu.push({
							name:documentName+" ("+commonJs.getNumberMask(fileSize, "0,0")+") KB",
							img:fileIcon,
							documentId:documentId,
							documentName:documentName,
							fun:function() {
								var index = $(this).index();

								downloadFile({
									documentId:attchedFileContextMenu[index].documentId,
									documentName:attchedFileContextMenu[index].documentName
								});
							}
						});
					}

					$(img).contextMenu(attchedFileContextMenu, {
						classPrefix:com.constants.ctxClassPrefixGrid,
						displayAround:"trigger",
						position:"bottom",
						horAdjust:0,
						verAdjust:2
					});
				}
			}
		});
	};

	downloadFile = function(param) {
		popupNotice = commonJs.openPopup({
			popupId:"DownloadFile",
			url:"/restServiceDownloadEO",
			paramData:{
				documentId:param.documentId,
				documentName:param.documentName,
				webServiceUrl:"/expense/downloadattachment"
			},
			header:framework.header.fileDownload,
			blind:false,
			width:300,
			height:150
		});
		popupNotice.close();
	};

	getDetail = function(expenseClaimId) {
		popup = commonJs.openPopup({
			popupId:"expenseDetail",
			url:"/employee/expense/getDetail",
			paramData:{
				mode:"detail",
				expenseClaimId:expenseClaimId
			},
			header:"Expense Detail",
			width:860,
			height:560
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		doSearch();
	});
});