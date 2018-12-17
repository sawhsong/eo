/**
 * TimesheetDailyDetail.js
 */
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

		$(elem).css("display", "block").appendTo($("#ulCommonCodeDetailHolder"));

		$("#ulCommonCodeDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).attr("index", groupIndex).attr("id", elemId+delimiter+groupIndex);

			$(this).find("i").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find(".dragHandler").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find(".deleteButton").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find("input").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

				if (groupIndex == ($("#ulCommonCodeDetailHolder .dummyDetail").length - 1)) {
					if (name.indexOf("isActiveDetail") != -1) {
						if ($(this).val() == "Y") {$(this).prop("checked", true);}
					}

					if (name.indexOf("sortOrderDetail") != -1) {
						$(this).val(commonJs.lpad((groupIndex+1), 3, "0"));
					}
				}
			});
		});

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	});

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
			var rowIdx = 0;

			$("#btnAdd").trigger("click");
			rowIdx = delimiter+(i-1);

			$("[name=commonCodeDetail"+rowIdx+"]").val(ds.getValue(i, "COMMON_CODE"));
			commonJs.setCheckboxValue("isActiveDetail"+rowIdx, ds.getValue(i, "IS_ACTIVE"));
			$("[name=codeMeaningDetail"+rowIdx+"]").val(ds.getValue(i, "CODE_MEANING"));
			$("[name=descriptionEnDetail"+rowIdx+"]").val(ds.getValue(i, "DESCRIPTION_EN"));
			$("[name=descriptionKoDetail"+rowIdx+"]").val(ds.getValue(i, "DESCRIPTION_KO"));
			$("[name=sortOrderDetail"+rowIdx+"]").val(ds.getValue(i, "SORT_ORDER"));
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
			$("#ulCommonCodeDetailHolder").find(".dummyDetail").each(function(index) {
				if ($(this).attr("index") == $(obj).attr("index")) {
					$(this).remove();

					$("#tblGrid").fixedHeaderTable({
						attachTo:$("#divDataArea")
					});
				}
			});

			$("#ulCommonCodeDetailHolder").find(".dummyDetail").each(function(groupIndex) {
				$(this).find("input[type=text]").each(function(index) {
					var name = $(this).attr("name");
					if (name.indexOf("sortOrderDetail") != -1) {
						$(this).val(commonJs.lpad((groupIndex+1), 3, "0"));
					}
				});
			});
		}
	});

	$(window).load(function() {
		getTimesheetDailyDetailData();
	});
});