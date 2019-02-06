/**
 * Disclaimer.js
 */
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
		var msg = (param.mode == "reject") ? "Rejected" : "Approved";
		commonJs.ajaxSubmit({
			url:"/serviceresource/employee/leave/approveRejectLeaveRequest",
			dataType:"json",
			formId:"fmDefault",
			data:{
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

	$(window).load(function() {
	});
});