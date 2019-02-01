/**
 * Disclaimer.js
 */
$(function() {
	$("#btnApprove").click(function(event) {
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
								$.blockUI({
									message:msg,
									css:{
										width:"100%",
										left:"0px",
										top:"50%",
										border:"0px",
										background:"transparent",
										cursor:"default",
										fadeIn:100,
										fadeOut:100,
										"font-weight":"bold",
										"font-size":"1.0em"
									},
									overlayCSS:{
										cursor:"default",
										backgroundColor:"#888888",
										opacity:"0.1"
									}
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

	$(window).load(function() {
	});
});