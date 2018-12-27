/**
 * MyTimesheet.js
 */
var popup = null;
var delimiter = jsconfig.get("dataDelimiter");

$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function() {
		if ($(this).attr("disabled")) {return;}

		if (checkValidation()) {
			postTimesheet({status:"SA"});
		}
	});

	$("#btnSubmit").click(function() {
		if ($(this).attr("disabled")) {return;}

		if (checkValidation()) {
			postTimesheet({status:"SU"});
		}
	});

	$("#btnSearch").click(function() {
		doSearch();
	});

	$("#assignment").change(function() {
		$("#tblGridBody").html("");
		setPeriodSelectbox();
	});

	$("#timesheetPeriod").change(function() {
		setTimesheetPeriodInfo();
		doSearch();
	});
	/*!
	 * process
	 */
	postTimesheet = function(param) {
		var status = param.status,
			message = "",
			dayCount = $("#tblGridBody .dummyDetail").length;

		if (dayCount <= 0) {
			commonJs.warn("There is no data to save or submit!");
			return;
		}

		if (status == "SA") {
			message = "Are you sure to save the data?";
		} else if (status == "SU") {
			message = "Are you sure to submit the data?";
		}

		commonJs.confirm({
			contents:message,
			width:250,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					doPostTimesheet(param);
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}],
			blind:true
		});
	};

	doPostTimesheet = function(param) {
		var status = param.status;

		commonJs.showProcMessageOnElement("divScrollablePanel");

		setTimeout(function() {
			var periodValues = $("#timesheetPeriod").val().split("_");

			commonJs.ajaxSubmit({
				url:"/ipro/timesheet/postTimesheet",
				dataType:"json",
				formId:"fmDefault",
				data:{
					status:status
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
									$("#divTimesheet-IPro").trigger("click");
//									doSearch();
								}
							}]
						});
					} else {
//						commonJs.error(result.message);
						commonJs.openDialog({
							type:com.message.E000,
							contents:result.message,
							width:300,
							buttons:[{
								caption:com.caption.ok,
								callback:function() {
									commonJs.hideProcMessageOnElement("divScrollablePanel");
								}
							}]
						});
					}
				}
			});
		}, 200);
	};

	setPeriodSelectbox = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("tblInform");

		setInformation();
		renderPeriodSelectbox();
		commonJs.refreshBootstrapSelectbox("timesheetPeriod");

		setTimeout(function() {
			commonJs.hideProcMessageOnElement("tblInform");
		}, 100);
	};

	setInformation = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/getAssignmentInfo",
			dataType:"json",
			data:{
				assignmentId:$("#assignment").val()
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					if (ds.getRowCnt() > 0) {
						$("#assignmentNumber").val(ds.getValue(0, "assignmentNumber"));
						$("#billingOrganisation").val(ds.getValue(0, "billingOrganisation"));
						$("#assignmentPeriod").val(ds.getValue(0, "assignmentStartDate")+" - "+ds.getValue(0, "assignmentEndDate"));
						$("#timesheetUnits").val(ds.getValue(0, "timesheetUnits"));
						$("#timesheetUnitsDesc").val(ds.getValue(0, "timesheetUnitsDesc"));
					}
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	renderPeriodSelectbox = function() {
		$("#timesheetPeriod option").each(function(index) {
			$(this).remove();
		});

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/getPeriodByAssignmentId",
			dataType:"json",
			data:{
				assignmentId:$("#assignment").val()
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					if (ds.getRowCnt() > 0) {
						for (var i=0; i<ds.getRowCnt(); i++) {
							var selected = "", optValSel = "", periodSel = "",
								optVal = ds.getValue(i, "assignmentId")+"_"+ds.getValue(i, "periodStartDate")+"_"+ds.getValue(i, "periodEndDate")+"_"+
										ds.getValue(i, "dueDate")+"_"+ds.getValue(i, "timesheetStatus")+"_"+ds.getValue(i, "timesheetStatusDescription");
								optText = ds.getValue(i, "periodStartDate")+" - "+ds.getValue(i, "periodEndDate")+" ("+ds.getValue(i, "timesheetStatusDescription")+")";

							optValSel = optVal.split("_")[0]+"_"+optVal.split("_")[1]+"_"+optVal.split("_")[2]+"_"+optVal.split("_")[3];
							periodSel = timesheetPeriod.split("_")[0]+"_"+timesheetPeriod.split("_")[1]+"_"+timesheetPeriod.split("_")[2]+"_"+timesheetPeriod.split("_")[3];

							if (periodSel == optValSel) {
								selected = " selected";
							}

							$("#timesheetPeriod").append("<option value=\""+optVal+"\""+selected+">"+optText+"</option>");
						}

						setTimesheetPeriodInfo();
					}
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	setTimesheetPeriodInfo = function() {
		if (commonJs.isEmpty($("#timesheetPeriod").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("tblInform");

		var values = $("#timesheetPeriod").val().split("_");
		$("#dueDate").val(values[3]);
		$("#timesheetStatus").val(values[4]);
		$("#timesheetPeriodInfo").val(values[1]+" - "+values[2]);

		setButtonStatus(values[4]);

		setTimeout(function() {
			commonJs.hideProcMessageOnElement("tblInform");
		}, 100);
	};

	setButtonStatus = function(timesheetStatus) {
		if (!(timesheetStatus == "NS" || timesheetStatus == "SA")) {
			$("#btnSave").attr("disabled", "disabled");
			$("#btnSubmit").attr("disabled", "disabled");
		} else {
			$("#btnSave").removeAttr("disabled");
			$("#btnSubmit").removeAttr("disabled");
		}
	};

	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	doSearch = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("divScrollablePanel");

		var values = $("#timesheetPeriod").val().split("_");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/ipro/timesheet/getDayListByPeriod",
				dataType:"json",
				data:{
					assignmentId:$("#assignment").val(),
					startDate:values[1],
					endDate:values[2]
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

	doRefresh = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("divScrollablePanel");

		var values = $("#timesheetPeriod").val().split("_");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/ipro/timesheet/refreshDayListByPeriod",
				dataType:"json",
				data:{},
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
		var html = "";

		$("#tblGridBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				var gridTr = $("<tr class=\"noStripe\"></tr>");

				for (var j=0; j<ds.getColumnCnt(); j++) {
					var gridTd = $("<td class=\"ct\"></td>");
					var dsVal = ds.getValue(i, j);

					if (!commonJs.isEmpty(dsVal)) {
						var elem = $("#divDummy").clone(), elemId = $(elem).attr("id");
						var dsVals = dsVal.split(delimiter);

						$(elem).find("input, select, a.btn").each(function(index) {
							var id = $(this).attr("id"), name = $(this).attr("name");
							var workDate = dsVals[0], formattedWorkDate = dsVals[1], totalHours = dsVals[2], numberOfDailyDetail = dsVals[3];
							var timesheetStatus = $("#timesheetStatus").val();
							var timesheetUnits = $("#timesheetUnits").val();

							if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
							else {id = "";}

							if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
							else {name = "";}

							$(this).attr("id", id+delimiter+i+delimiter+j).attr("name", name+delimiter+i+delimiter+j);

							if ($(this).prop("type") == "button") {
								if (!(timesheetStatus == "NS" || timesheetStatus == "SA")) {
									$(this).html("<i class=\"fa fa-lg fa-list\"></i>&nbsp; View");
								}

								$(this).bind("click", function() {
									openPopup({
										paramData:{
											workDate:workDate,
											totalHours:totalHours,
											timesheetUnits:$("#timesheetUnits").val(),
											timesheetStatus:timesheetStatus
										}
									});
								});
							}

							if (commonJs.startsWith(name, "workDate")) {$(this).val(workDate);}
							else if (commonJs.startsWith(name, "formattedWorkDate")) {$(this).val(formattedWorkDate);}
							else if (commonJs.startsWith(name, "totalHours")) {
								$(this).val(totalHours);

								if (timesheetUnits == "HSE" || timesheetUnits == "DSE" || numberOfDailyDetail > 1) {
									$(this).removeAttr("title");
									$(this).attr("readonly", "readonly");
									$(this).removeClass("txtEn");
									$(this).addClass("txtDis");
								} else {
									$(this).attr("title", "Number between 0 and 24");
									$(this).removeAttr("readonly");
									$(this).removeClass("txtDis");
									$(this).addClass("txtEn");

									$(this).bind("focus", function() {$(this).select();});
								}
							}
						});
						$(elem).css("display", "block").appendTo(gridTd);
					}

					gridTd.appendTo(gridTr);
				}

				$("#tblGridBody").append(gridTr);
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:7").setText(com.message.I001));
			html += gridTr.toHtmlString();

			$("#tblGridBody").append($(html));
		}

		$(".numeric").number(true, 0);
		setGridTable();

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	openPopup = function(param) {
		var timesheetUnits = param.paramData.timesheetUnits;
		var width = 850, height = 500;

		if (timesheetUnits == "HSE" || timesheetUnits == "DSE") {
			width = 1100;
		}
		param.popupId = "timesheetDetail";
		param.url = "/ipro/timesheet/getDailyDetailScreen";
		param.header = "Timesheet Detail";
		param.blind = true;
		param.width = width;
		param.height = height;

		popup = commonJs.openPopup(param);
	};

	checkValidation = function() {
		var isValid = true;

		$("#tblGridBody .dummyDetail").find("input").each(function(index) {
			var name = $(this).attr("name"), val = $(this).val();

			if (commonJs.startsWith(name, "totalHours")) {
				if (!commonJs.isNumber(val)) {
					commonJs.doValidatorMessage($(this), "notValid");
					isValid = false;
					return false;
				}

				if (commonJs.isEmpty(val) || val < 0 || val > 24) {
					commonJs.doValidatorMessage($(this), "notValid");
					isValid = false;
					return false;
				}
			}
		});
		return isValid;
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		setPeriodSelectbox();
		setGridTable();

		if (!(commonJs.isEmpty(assignment) || commonJs.isEmpty(timesheetPeriod))) {
			doSearch();
		}
	});
});