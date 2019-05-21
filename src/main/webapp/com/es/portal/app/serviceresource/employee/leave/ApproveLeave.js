/**
 * 
 */
var delimiter = jsconfig.get("dataDelimiter");
jsconfig.put("scrollablePanelHeightAdjust", 30);

$(function() {
	$("#btnApprove").click(function(event) {
		if ($("#btnApprove").attr("disabled") == "disabled") {return false;}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					doSave({mode:"approve"});
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$("#btnReject").click(function(event) {
		if ($("#btnReject").attr("disabled") == "disabled") {return false;}

		if (commonJs.isEmpty($("#approveRejectComments").val())) {
			commonJs.error("Reject Reason is a mandatory field.");
			$("#approveRejectComments").focus();
			return false;
		}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					doSave({mode:"reject"});
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	doSave = function(param) {
		commonJs.ajaxSubmit({
			url:"/serviceresource/employee/leave/approveRejectLeaveRequest",
			dataType:"json",
			formId:"fmDefault",
			data:{
				leaveRequestId:leaveRequestId,
				mode:param.mode
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
								$("#btnApprove").attr("disabled", "disabled");
								$("#btnReject").attr("disabled", "disabled");
							}
						}]
					});
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	loadDateDetail = function() {
		var startDate = moment($("#startDate").val(), "DD-MM-YYYY"), endDate = moment($("#endDate").val(), "DD-MM-YYYY");
		var dateDiff = endDate.diff(startDate, "days")+1;

		if (dateDiff < 1) {return;}

		commonJs.showProcMessageOnElement("divBodyHolder");
		$("#ulDetailHolder").html("");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/serviceresource/employee/leave/getDateDetail",
				dataType:"json",
				data:{
					leaveRequestId:leaveRequestId,
					assignmentId:assignmentId,
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
					}
				}

				$(".numeric").number(true, 1);
			});
		});

		setGridTable();
		commonJs.hideProcMessageOnElement("divBodyHolder");
	};

	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea"),
			attachToHeight:commonJs.toNumber($("#divScrollablePanel").height())
		});
	};

	$(window).load(function() {
		setTimeout(function() {
			loadDateDetail();
		}, 2000);
	});
});