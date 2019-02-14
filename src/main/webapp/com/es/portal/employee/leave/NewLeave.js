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
			statusBar:false,
			weekNumber:false,
			oncloseCallback:calculateDuration
		});
	});

	$("#icnEndDate").click(function(event) {
		commonJs.openCalendar(event, "endDate", {
			statusBar:false,
			weekNumber:false,
			oncloseCallback:calculateDuration
		});
	});

	$("#btnLoadDetail").click(function(event) {
		var startDate = moment($("#startDate").val(), "DD-MM-YYYY"), endDate = moment($("#endDate").val(), "DD-MM-YYYY");
		var dateDiff = endDate.diff(startDate, "days")+1;

		commonJs.showProcMessageOnElement("divPopupWindowHolder");
		$("#ulDetailHolder").html("");
		setTimeout(function() {
			for (var i=0; i<dateDiff; i++) {
				addDetailRows();
			}
		}, 200);
	});

	$(document).keypress(function(event) {
		var element = event.target;
		var name = $(element).attr("name");

		if (event.which == 13) {
		}

		if ($(element).is("#startDate") || $(element).is("#endDate")) {
			calculateDuration();
		}
	});
	/*!
	 * process
	 */
	doSave = function() {
		commonJs.ajaxSubmit({
			url:"/employee/leave/saveLeave",
			dataType:"json",
			formId:"fmDefault",
			data:{
				leaveRequestId:leaveRequestId
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					commonJs.openDialog({
						type:com.message.I000,
						contents:result.message,
						blind:true,
						width:300,
						buttons:[{
							caption:com.caption.ok,
							callback:function() {
								parent.doSearch();
								parent.popup.close();
							}
						}]
					});
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
		var assignmentId = $("#assignment").val();

		if (commonJs.isEmpty(assignmentId)) {return;}

		commonJs.showProcMessageOnElement("divInformArea");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/employee/leave/loadAccrual",
				dataType:"json",
				data:{
					assignmentId:assignmentId
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
		setWindowSize();

		commonJs.hideProcMessageOnElement("divInformArea");
	};

	/*!
	 * Add data grid rows
	 */
	addDetailRows = function() {
		var elem = $("#liDummy").clone(), elemId = $(elem).attr("id");

		$(elem).css("display", "block").appendTo($("#ulDetailHolder"));

		$("#ulDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).attr("index", groupIndex).attr("id", elemId+delimiter+groupIndex);

			$(this).find("input, select").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

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

	calculateDuration = function() {
//		var startDate = moment($("#startDate").val(), "DD-MM-YYYY"), endDate = moment($("#endDate").val(), "DD-MM-YYYY");
//		var dateDiff = endDate.diff(startDate, "days") + 1;
//		$("#duration").val(dateDiff * 8);

		commonJs.ajaxSubmit({
			url:"/employee/leave/calculateDuration",
			dataType:"json",
			data:{
				startDate:$("#startDate").val(),
				endDate:$("#endDate").val()
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;
					$("#duration").val(ds.getValue(0, "duration"));
				} else {
					commonJs.error(result.message);
				}
			}
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
			setWindowSize(2);
			loadAccrual();
		}, 300);

		calculateDuration();
	});
});