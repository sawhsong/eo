/**
 * TimesheetDailyDetail.js
 */
jsconfig.put("useJqSelectmenu", false);

var delimiter = jsconfig.get("dataDelimiter");

$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		if ($(this).attr("disabled")) {return;}

		var isValid = true;

		$("#liDummy").find("input select").each(function(index) {
			$(this).removeAttr("mandatory");
			$(this).removeAttr("option");
		});

		if (!commonJs.doValidate("fmDefault")) {
			isValid = false;
			return;
		}

		if (!checkValidation()) {
			isValid = false;
			return;
		}

		$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).find(":input").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);
			});
		});

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

	$("#btnAdd").click(function(event) {
		if ($(this).attr("disabled")) {return;}

		var elem = $("#liDummy").clone(), elemId = $(elem).attr("id");

		$(elem).css("display", "block").appendTo($("#ulTimesheetDetailHolder"));

		$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).attr("index", groupIndex).attr("id", elemId+delimiter+groupIndex);

			$(this).find("i").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find(".deleteButton").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find("input, select").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

				if (commonJs.startsWith(name, "startTime") || commonJs.startsWith(name, "endTime") || commonJs.startsWith(name, "nonWorkedTime")) {
					if (timesheetUnits == "HSE") {
						$(this).bind("change", function() {
							calculateTimeWorked($(this));
						});
					}
				}

				if (commonJs.startsWith(name, "hours")) {
					if (timesheetUnits == "HSE") {
						$(this).removeAttr("title");
						$(this).attr("readonly", "readonly");
						$(this).removeClass("txtEn");
						$(this).addClass("txtDis");
					} else {
						$(this).bind("focus", function() {$(this).select();});
					}
				}

				if (commonJs.startsWith(name, "description")) {
					$(this).bind("focus", function() {$(this).select();});
				}

				if ($(this).is("select")) {
					setSelectBoxes($(this));
				}
			});
		});

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	});

	/*!
	 * process
	 */
	setButtonStatus = function() {
		if (!(timesheetStatus == "NS" || timesheetStatus == "SA")) {
			$("#btnSave").attr("disabled", "disabled");
			$("#btnAdd").attr("disabled", "disabled");
		}
	};

	setSelectBoxes = function(jqObj) {
		$(jqObj).selectpicker({
			width:"auto",
			container:"body",
			style:$(jqObj).attr("class")
		});
	};

	hideDeletedRow = function() {
		var rowIdx = 0, isDeleted = false;
		$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(index) {
			rowIdx = delimiter+index;
			isDeleted = commonJs.toBoolean($("[name=deleted"+rowIdx+"]").val());

			$(this).find("input select").each(function(index) {
				$(this).removeAttr("mandatory");
				$(this).removeAttr("option");
			});

			if (isDeleted) {
				$(this).hide();
			}
		});

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	getTimesheetDailyDetailData = function() {
		commonJs.showProcMessageOnElement("divScrollablePanelPopup");

		var data = commonJs.serialiseObject(parent.$("#tblGridBody"));
		data.workDate = workDate;
		data.totalHours = totalHours;
		data.timesheetUnits = timesheetUnits;

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/ipro/timesheet/getDailyDetailData",
				dataType:"json",
				data:data,
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
		var ds = result.dataSet;

		for (var i=0; i<ds.getRowCnt(); i++) {
			var rowIdx = 0,
				startTime = ds.getValue(i, "startTime"),
				endTime = ds.getValue(i, "endTime"),
				nonWorkedTime = ds.getValue(i, "nonWorkedTime");

			$("#btnAdd").trigger("click");
			rowIdx = delimiter+i;

			$("[name=deleted"+rowIdx+"]").val(ds.getValue(i, "deleted"));
			$("[name=preferred"+rowIdx+"]").val(ds.getValue(i, "preferred"));
			$("[name=rowId"+rowIdx+"]").val(ds.getValue(i, "rowId"));
			$("[name=timesheetLineId"+rowIdx+"]").val(ds.getValue(i, "timesheetLineId"));
			$("[name=rates"+rowIdx+"]").selectpicker("val", ds.getValue(i, "rateId"));
			if (!commonJs.isEmpty(startTime)) {
				$("[name=startTimeHH"+rowIdx+"]").selectpicker("val", commonJs.lpad(startTime.split(":")[0], 2, "0"));
				$("[name=startTimeMM"+rowIdx+"]").selectpicker("val", commonJs.lpad(startTime.split(":")[1], 2, "0"));
			}
			if (!commonJs.isEmpty(endTime)) {
				$("[name=endTimeHH"+rowIdx+"]").selectpicker("val", commonJs.lpad(endTime.split(":")[0], 2, "0"));
				$("[name=endTimeMM"+rowIdx+"]").selectpicker("val", commonJs.lpad(endTime.split(":")[1], 2, "0"));
			}
			if (!commonJs.isEmpty(nonWorkedTime)) {
				$("[name=nonWorkedTimeHH"+rowIdx+"]").selectpicker("val", commonJs.lpad(nonWorkedTime.split(":")[0], 2, "0"));
				$("[name=nonWorkedTimeMM"+rowIdx+"]").selectpicker("val", commonJs.lpad(nonWorkedTime.split(":")[1], 2, "0"));
			}

			$("[name=hours"+rowIdx+"]").val(commonJs.getNumberMask(ds.getValue(i, "hours"), "##0.00"));
			$("[name=description"+rowIdx+"]").val(ds.getValue(i, "description"));
		}

		hideDeletedRow();

		commonJs.hideProcMessageOnElement("divScrollablePanelPopup");
	};

	doSave = function() {
		var detailLength = $("#ulTimesheetDetailHolder .dummyDetail").length;

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/updateDailyDetail",
			dataType:"json",
			formId:"fmDefault",
			data:{
				workDate:workDate,
				timesheetUnits:timesheetUnits,
				detailLength:detailLength
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
								parent.popup.close();
								parent.doRefresh();
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
		var rowIdx = 0, isDeleted = false;

		$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(index) {
			rowIdx = delimiter+index;
			isDeleted = commonJs.toBoolean($("[name=deleted"+rowIdx+"]").val());

			var hoursObj = $("[name=hours"+rowIdx+"]"), hoursVal = $(hoursObj).val(),
				ratesObj = $("[name=rates"+rowIdx+"]"), ratesVal = $(ratesObj).val();

			if (!isDeleted) {
				if (!commonJs.isNumber(hoursVal)) {
					commonJs.doValidatorMessage($(hoursObj), "notValid");
					isValid = false;
					return false;
				}

				if (timesheetUnits == "HSE") {
					if (commonJs.isEmpty(hoursVal) || hoursVal < 0 || hoursVal > 24) {
						commonJs.doValidatorMessage($(hoursObj), "notValid");
						isValid = false;
						return false;
					}
				}

				if (timesheetUnits == "DSE") {
					if (commonJs.isEmpty(hoursVal) || hoursVal < 0 || hoursVal > 1) {
						commonJs.doValidatorMessage($(hoursObj), "notValid");
						isValid = false;
						return false;
					}
				}

				if (commonJs.isEmpty(ratesVal)) {
					commonJs.doValidatorMessage($(ratesObj), "notValid");
					isValid = false;
					return false;
				}
			}
		});
		return isValid;
	};

	calculateTimeWorked = function(obj) {
		var ds;
		var name = $(obj).attr("name"), rowIdx = delimiter+name.split(delimiter)[1];
		var hoursObj = $("[name=hours"+rowIdx+"]");

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/calculateTimeWorked",
			dataType:"json",
			data:{
				startTimeHH:$("[name=startTimeHH"+rowIdx+"]").val(),
				startTimeMM:$("[name=startTimeMM"+rowIdx+"]").val(),
				endTimeHH:$("[name=endTimeHH"+rowIdx+"]").val(),
				endTimeMM:$("[name=endTimeMM"+rowIdx+"]").val(),
				nonWorkedTimeHH:$("[name=nonWorkedTimeHH"+rowIdx+"]").val(),
				nonWorkedTimeMM:$("[name=nonWorkedTimeMM"+rowIdx+"]").val()
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					ds = result.dataSet;
					$(hoursObj).val(ds.getValue(0, "hoursWorked"));
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};
	/*!
	 * load event (document / window)
	 */
	$(document).click(function(event) {
		var obj = event.target;

		if ($(obj).hasClass("deleteButton") || ($(obj).is("i") && $(obj).parent("th").hasClass("deleteButton"))) {
			$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(index) {
				if ($(this).attr("index") == $(obj).attr("index")) {
					$(this).find("[type=hidden]").each(function(index) {
						if (commonJs.startsWith($(this).attr("name"), "deleted")) {
							$(this).val("Y");
						}
					});

					$(this).find("input select").each(function(index) {
						$(this).removeAttr("mandatory");
						$(this).removeAttr("option");
					});

					$(this).hide();

					$("#tblGrid").fixedHeaderTable({
						attachTo:$("#divDataArea")
					});
				}
			});
		}
	});

	$(window).load(function() {
		getTimesheetDailyDetailData();
		setButtonStatus();
	});
});