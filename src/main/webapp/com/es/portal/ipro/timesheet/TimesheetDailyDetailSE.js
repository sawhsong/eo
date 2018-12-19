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
		alert("save");
	});

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#btnAdd").click(function(event) {
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

				if ($(this).is("select")) {
					setSelectBoxes($(this));
				}
			});
		});

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	});

	setSelectBoxes = function(jqObj) {
		$(jqObj).selectpicker({
			width:"auto",
			container:"body",
			style:$(jqObj).attr("class")
		});
	};

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	getTimesheetDailyDetailData = function() {
		commonJs.showProcMessageOnElement("divScrollablePanelPopup");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/ipro/timesheet/getTimesheetDailyDetailData",
				dataType:"json",
				data:{
					workDate:workDate,
					totalHours:totalHours,
					timesheetUnits:timesheetUnits
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

		for (var i=0; i<ds.getRowCnt(); i++) {
			var rowIdx = 0,
				startTime = ds.getValue(i, "startTime"),
				endTime = ds.getValue(i, "endTime"),
				nonWorkedTime = ds.getValue(i, "nonWorkedTime");

			$("#btnAdd").trigger("click");
			rowIdx = delimiter+i;

			$("[name=rowId"+rowIdx+"]").val(ds.getValue(i, "rowId"));
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
			$("[name=hours"+rowIdx+"]").val(ds.getValue(i, "hours"));
			$("[name=description"+rowIdx+"]").val(ds.getValue(i, "description"));
		}

		commonJs.hideProcMessageOnElement("divScrollablePanelPopup");
	};

	exeSave = function() {
	};
	/*!
	 * load event (document / window)
	 */
	$(document).click(function(event) {
		var obj = event.target;

		if ($(obj).hasClass("deleteButton") || ($(obj).is("i") && $(obj).parent("th").hasClass("deleteButton"))) {
			$("#ulTimesheetDetailHolder").find(".dummyDetail").each(function(index) {
				if ($(this).attr("index") == $(obj).attr("index")) {
					$(this).remove();

					$("#tblGrid").fixedHeaderTable({
						attachTo:$("#divDataArea")
					});
				}
			});
		}
	});

	$(window).load(function() {
		getTimesheetDailyDetailData();
	});
});