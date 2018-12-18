/**
 * MyTimesheet.js
 */
var popup = null;
jsconfig.put("scrollablePanelHeightAdjust", 0);
var delimiter = jsconfig.get("dataDelimiter");

$(function() {
	/*!
	 * event
	 */
	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	$("#btnSearch").click(function() {
		doSearch();
	});

	$("#assignment").change(function() {
		$("#tblGridBody").html("");
		setTimesheetPeriodSelectbox();
	});

	$("#timesheetPeriod").change(function() {
		setTimesheetPeriodInfo();
		doSearch();
	});
	/*!
	 * process
	 */
	setTimesheetPeriodSelectbox = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("tblInform");

		setInformation();
		renderTimesheetPeriodSelectbox();
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

	renderTimesheetPeriodSelectbox = function() {
		$("#timesheetPeriod option").each(function(index) {
			$(this).remove();
		});

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/getTimesheetPeriod",
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
							$("#timesheetPeriod").append("<option value=\""
								+ds.getValue(i, "assignmentId")+"_"
								+ds.getValue(i, "periodStartDate")+"_"
								+ds.getValue(i, "periodEndDate")+"_"
								+ds.getValue(i, "dueDate")+"_"
								+ds.getValue(i, "timesheetStatus")+"_"
								+ds.getValue(i, "timesheetStatusDescription")
								+"\">"
								+ds.getValue(i, "periodStartDate")+" - "
								+ds.getValue(i, "periodEndDate")+" ("
								+ds.getValue(i, "timesheetStatusDescription")+")"
								+"</option>"
							);
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
		$("#timesheetStatus").val(values[4]);
		$("#timesheetPeriodInfo").val(values[1]+" - "+values[2]);

		setTimeout(function() {
			commonJs.hideProcMessageOnElement("tblInform");
		}, 100);
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
				url:"/ipro/timesheet/getTimesheetDayList",
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
							var workDate = dsVals[0], formattedWorkDate = dsVals[1], totalHours = dsVals[2];

							if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
							else {id = "";}

							if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
							else {name = "";}

							$(this).attr("id", id+delimiter+i+delimiter+j).attr("name", name+delimiter+i+delimiter+j);

							if ($(this).prop("type") == "button") {
								$(this).bind("click", function() {
									openPopup({
										paramData:{
											workDate:workDate,
											totalHours:totalHours,
											timesheetUnits:$("#timesheetUnits").val()
										}
									});
								});
							}

							if (commonJs.startsWith(name, "workDate")) {$(this).val(workDate);}
							else if (commonJs.startsWith(name, "formattedWorkDate")) {$(this).val(formattedWorkDate);}
							else if (commonJs.startsWith(name, "totalHours")) {
								$(this).val(totalHours);
								$(this).bind("focus", function() {$(this).select();});
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
		param.url = "/ipro/timesheet/getTimesheetDailyDetail";
		param.header = "Timesheet Detail";
		param.blind = true;
		param.width = width;
		param.height = height;

		popup = commonJs.openPopup(param);
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		setTimesheetPeriodSelectbox();
		setGridTable();
	});
});