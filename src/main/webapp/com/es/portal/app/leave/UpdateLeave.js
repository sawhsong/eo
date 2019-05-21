/**
 * 
 */
var delimiter = jsconfig.get("dataDelimiter");
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		var isValid = true;

		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		if (!checkValidation()) {
			isValid = false;
			return;
		}

		if (!isValid) {return;}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					doSave();
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#assignment").change(function(event) {
		loadAccrual();
	});

	$("#icnStartDate").click(function(event) {
		commonJs.openCalendar(event, "startDate", {
			oncloseCallback:loadDateDetail
		});
	});

	$("#icnEndDate").click(function(event) {
		commonJs.openCalendar(event, "endDate", {
			oncloseCallback:loadDateDetail
		});
	});

	$(document).keypress(function(event) {
		var element = event.target;
		var name = $(element).attr("name");

		if (event.which == 13) {
		}

		if ($(element).is("#startDate") || $(element).is("#endDate")) {
			loadDateDetail();
		}
	});

	/*!
	 * process
	 */
	loadDateDetail = function() {
		var startDate = moment($("#startDate").val(), "DD-MM-YYYY"), endDate = moment($("#endDate").val(), "DD-MM-YYYY");
		var dateDiff = endDate.diff(startDate, "days")+1;

		if (dateDiff < 1) {return;}

		commonJs.showProcMessageOnElement("divPopupWindowHolder");
		$("#ulDetailHolder").html("");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/leave/getDateDetail",
				dataType:"json",
				data:{
					leaveRequestId:leaveRequestId,
					assignmentId:$("#assignment").val(),
					startDate:$("#startDate").val(),
					endDate:$("#endDate").val()
				},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						var ds = result.dataSet;
						addDetailRows(ds);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 200);
	};

	doSave = function() {
		var detailLength = $("#ulDetailHolder .dummyDetail").length;

		commonJs.ajaxSubmit({
			url:"/leave/saveLeave",
			dataType:"json",
			formId:"fmDefault",
			data:{
				detailLength:detailLength,
				leaveRequestId:leaveRequestId
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					setTimeout(function() {
						commonJs.doSubmit({
							formId:"fmDefault",
							action:"/employee/leave/getLeaveDetail",
							data:{
								leaveRequestId:leaveRequestId
							},
						});

						setTimeout(function() {
							parent.doSearch();
						}, 200);
					}, 500);
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	checkValidation = function() {
		var isValid = true;
		var startDate = moment($("#startDate").val(), "DD-MM-YYYY");
		var endDate = moment($("#endDate").val(), "DD-MM-YYYY");
		var duration = $("#duration").val();

		if (endDate.diff(startDate, "days") < 0) {
			commonJs.error("Date range is invalid.");
			isValid = false;
			return false;
		}

		if (duration < 0) {
			commonJs.error("Date range is invalid.");
			isValid = false;
			return false;
		}

		return isValid;
	};

	loadAccrual = function() {
		commonJs.showProcMessageOnElement("divInformArea");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/leave/loadAccrual",
				dataType:"json",
				data:{
					assignmentId:$("#assignment").val()
				},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						renderAccrual(result);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 200);
	};

	renderAccrual = function(result) {
		var ds = result.dataSet, html = "";

		$("#tblInformBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				var gridTr = new UiGridTr();

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "displayName")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(ds.getValue(i, "noOfHours"), "#,###.##")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(commonJs.getNumberMask(ds.getValue(i, "balance"), "#,###.##")));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:3").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblInformBody").append($(html));
		setWindowSize(ds.getRowCnt());

		commonJs.hideProcMessageOnElement("divInformArea");
	};

	/*!
	 * Add data grid rows
	 */
	addDetailRows = function(dataSet) {
		var elem, elemId;

		for (var i=0; i<dataSet.getRowCnt(); i++) {
			elem = $("#liDummy").clone(), elemId = $(elem).attr("id");

			$(elem).css("display", "block").appendTo($("#ulDetailHolder"));
		}

		$("#ulDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).attr("index", groupIndex).attr("id", elemId+delimiter+groupIndex);

			$(this).find("input, select").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");
				var dateType = "";

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

				id = $(this).attr("id");

				dateType = dataSet.getValue(groupIndex, "dateType");

				if (commonJs.startsWith(id, "date")) {$("#"+id).val(dataSet.getValue(groupIndex, "calendarDate"));}
				if (commonJs.startsWith(id, "dateType")) {$("#"+id).val(dataSet.getValue(groupIndex, "dateType"));}
				if (commonJs.startsWith(id, "dayOfWeek")) {
					if (dateType == "PH") {
						$("#"+id).val(dataSet.getValue(groupIndex, "dateTypeDesc"));
					} else {
						$("#"+id).val(dataSet.getValue(groupIndex, "dayName"));
					}
				}
				if (commonJs.startsWith(id, "hours")) {$("#"+id).val(dataSet.getValue(groupIndex, "hours"));}
				if (commonJs.startsWith(id, "description")) {$("#"+id).val(dataSet.getValue(groupIndex, "description"));}

				if (dateType == "WE" || dateType == "PH") {
					if (commonJs.startsWith(id, "date") || commonJs.startsWith(id, "dayOfWeek")) {
						$("#"+id).css("color", "#f07031");
					}

					if (commonJs.startsWith(id, "hours") || commonJs.startsWith(id, "description")) {
						$("#"+id).css("color", "#f07031");
						$("#"+id).css("background", "#feedeb");
						$("#"+id).attr("disabled", "diabled");
						$("#"+id).removeClass("txtEn");
						$("#"+id).addClass("txtDis");
					}
				}

				if (commonJs.startsWith(name, "hours")) {
					$(this).bind("focus", function() {$(this).select();});
					$(this).bind("change", function() {calculateDurationFromDetailList();});
				}

				$(".numeric").number(true, 1);
			});
		});

		calculateDurationFromDetailList();
		setGridTable();
		commonJs.hideProcMessageOnElement("divPopupWindowHolder");
	};

	setWindowSize = function(rowCnt) {
		var informLenth = $("#tblInformBody tr").length;

		if (rowCnt > 0 && informLenth != rowCnt) {
			parent.popup.resizeTo(0, ((rowCnt-1) * 27));
		}
	};

	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	calculateDurationFromDetailList = function() {
		var totHours = 0;

		$("#ulDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).find("input, select").each(function(index) {
				var name = $(this).attr("name");

				if (commonJs.startsWith(name, "hours")) {
					totHours += commonJs.toNumber($(this).val());
				}
			});
		});
		$("#duration").val(totHours);
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.setFieldDateMask("startDate");
		commonJs.setFieldDateMask("endDate");

		$("#duration").number(true, 1);

		setTimeout(function() {
			loadAccrual();
		}, 200);

		setTimeout(function() {
			setWindowSize(2);
		}, 400);

		setTimeout(function() {
			loadDateDetail();
		}, 1500);
	});
});