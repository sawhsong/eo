/**
 * 
 */
var spinner;

$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

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

	$("#btnBack").click(function(event) {
		history.go(-1);
	});

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#icnStartDate").click(function(event) {
		commonJs.openCalendar(event, "startDate", {
			statusBar:false,
			weekNumber:false,
			adjustX:15,
			adjustY:-50,
			positionX:"right"
		});
	});

	$("#icnEndDate").click(function(event) {
		commonJs.openCalendar(event, "endDate", {
			statusBar:false,
			weekNumber:false,
			adjustX:-1,
			adjustY:-50,
			positionX:"left"
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
				leaveRequestId:leaveRequestId,
				assignmentId:assignmentId
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
	$(window).load(function() {
		commonJs.setFieldDateMask("startDate");
		commonJs.setFieldDateMask("endDate");
		spinner = $("#duration").spinner();
		$("#duration").number(true, 0, "", "");
	});
});