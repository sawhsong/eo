/**
 * 
 */
var popup = null;

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
			height:554
		});
	});

	$("#btnSearch").click(function() {
		doSearch();
	});

	$("#assignment").change(function() {
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
		}, 200);
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

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "personFullName")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "departmentDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "expenseTypeDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "dateOfClaim")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(dataSet.getValue(i, "amount"), "#,##0.00")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(dataSet.getValue(i, "gst"), "#,##0.00")));
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

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getLeaveDetail = function(leaveRequestId) {
		popup = commonJs.openPopup({
			popupId:"leaveDetail",
			url:"/employee/leave/getLeaveDetail",
			paramData:{
				mode:"detail",
				leaveRequestId:leaveRequestId
			},
			header:"Leave Detail",
			width:860,
			height:529
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		doSearch();
	});
});