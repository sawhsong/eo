/**
 * 
 */
var popup = null;
jsconfig.put("scrollablePanelHeightAdjust", 8);

$(function() {
	/*!
	 * event
	 */
	$("#btnNew").click(function() {
		popup = commonJs.openPopup({
			popupId:"NewLeave",
			url:"/leave/newLeave",
			paramData:{
				leaveRequestId:"-1"
			},
			header:"New Leave",
			width:840,
			height:880
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
				url:"/leave/getLeaveList",
				dataType:"json",
				data:{
					assignmentId:$("#assignment").val()
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

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "leaveTypeDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "leaveCategoryDesc")));

				var uiAnc = new UiAnchor();
				uiAnc.setText(ds.getValue(i, "assignmentName")).setScript("getLeaveDetail('"+ds.getValue(i, "leaveRequestId")+"')");
				gridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "duration")+" "+ds.getValue(i, "durationUnitDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "startDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "endDate")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "statusDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "submittedDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "approveRejectDate")));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:9").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));
		setGridTable();

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getLeaveDetail = function(leaveRequestId) {
		popup = commonJs.openPopup({
			popupId:"leaveDetail",
			url:"/leave/getLeaveDetail",
			paramData:{
				mode:"detail",
				leaveRequestId:leaveRequestId
			},
			header:"Leave Detail",
			width:840,
			height:880
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		doSearch();
	});
});