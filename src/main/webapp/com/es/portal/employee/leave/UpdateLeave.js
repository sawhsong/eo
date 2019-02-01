/**
 * 
 */
var spinner;

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
			adjustY:-10
		});
	});

	$("#icnEndDate").click(function(event) {
		commonJs.openCalendar(event, "endDate", {
			statusBar:false,
			weekNumber:false,
			adjustY:-10
		});
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
								commonJs.doSubmit({
									formId:"fmDefault",
									action:"/employee/leave/getLeaveDetail",
									data:{
										leaveRequestId:leaveRequestId
									},
								});

								parent.doSearch();
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
		var typeCode = $("#type").val(), accrualVal;

		if (endDate.diff(startDate, "days") < 0) {
			commonJs.error("Date values are invalid.");
			isValid = false;
			return false;
		}

		if (typeCode == "A" || typeCode == "P") {
			var duration = $("#duration").val();
			var balance = 0;

			$("#tblInformBody tr").each(function(rowIndex) {
				$(this).find("td").each(function(colIndex) {
					if (colIndex == 2) {
						balance += commonJs.toNumber($(this).html());
					}
				});
			});

			if (duration > balance) {
				commonJs.error("Duration value is invalid.");
				isValid = false;
				return false;
			}
		}

		return isValid;
	};

	loadAccrual = function() {
		commonJs.showProcMessageOnElement("divInformArea");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/employee/leave/loadAccrual",
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

	setWindowSize = function(rowCnt) {
		var informLenth = $("#tblInformBody tr").length;

		if (rowCnt > 0 && informLenth != rowCnt) {
			parent.popup.resizeTo(0, ((rowCnt-1) * 27));
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.setFieldDateMask("startDate");
		commonJs.setFieldDateMask("endDate");
		spinner = $("#duration").spinner({
			step:0.5
		});
		$("#duration").number(true, 1);
	});
});