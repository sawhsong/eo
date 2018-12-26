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

		$("#liDummy").find(":input").each(function(index) {
			$(this).removeAttr("mandatory");
			$(this).removeAttr("option");
		});

		if (!commonJs.doValidate("fmDefault")) {
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

				if (commonJs.containsIgnoreCase(name, "rates") && commonJs.isEmpty($(this).val())) {
					isValid = false;
					commonJs.doValidatorMessage($(this), "mandatory");
				}
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
			var rowIdx = 0;

			$("#btnAdd").trigger("click");
			rowIdx = delimiter+i;

			$("[name=deleted"+rowIdx+"]").val(ds.getValue(i, "deleted"));
			$("[name=endTime"+rowIdx+"]").val(ds.getValue(i, "endTime"));
			$("[name=nonWorkedTime"+rowIdx+"]").val(ds.getValue(i, "nonWorkedTime"));
			$("[name=preferred"+rowIdx+"]").val(ds.getValue(i, "preferred"));
			$("[name=rowId"+rowIdx+"]").val(ds.getValue(i, "rowId"));
			$("[name=startTime"+rowIdx+"]").val(ds.getValue(i, "startTime"));
			$("[name=timesheetLineId"+rowIdx+"]").val(ds.getValue(i, "timesheetLineId"));
			$("[name=rates"+rowIdx+"]").selectpicker("val", ds.getValue(i, "rateId"));
			$("[name=hours"+rowIdx+"]").val(ds.getValue(i, "hours"));
			$("[name=description"+rowIdx+"]").val(ds.getValue(i, "description"));
		}

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