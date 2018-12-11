/**
 * MyTimesheet.js
 */
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
		setTimesheetPeriodSelectbox();
	});

	$("#timesheetPeriod").change(function() {
		setTimesheetPeriodInfo();
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
						$("#timesheetUnitsDesc").val(ds.getValue(0, "timesheetUnitsDesc"));
						$("#assignmentStartDate").val(ds.getValue(0, "assignmentStartDate"));
						$("#assignmentEndDate").val(ds.getValue(0, "assignmentEndDate"));
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
		$("#timesheetPeriodInfo").val(values[1]+" - "+values[2]);

		setTimeout(function() {
			commonJs.hideProcMessageOnElement("tblInform");
		}, 100);
	};

	doSearch = function() {
		if (commonJs.isEmpty($("#assignment").val())) {
			return;
		}
		commonJs.showProcMessageOnElement("tblInform");

		var values = $("#timesheetPeriod").val().split("_");

		commonJs.ajaxSubmit({
			url:"/ipro/timesheet/getTimesheetDetail",
			dataType:"json",
			data:{
				assignmentId:$("#assignment").val(),
				startDate:values[1],
				endDate:values[2]
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					if (ds.getRowCnt() > 0) {

					}
				} else {
					commonJs.error(result.message);
				}
			}
		});

		setTimeout(function() {
			commonJs.hideProcMessageOnElement("tblInform");
		}, 100);
	};
	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		setTimesheetPeriodSelectbox();

		commonJs.setAccordion({
			containerClass:"accordionInformArea",
			expandAll:false,
			icons:null
		});
	});
});