/**
 * 
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnEdit").click(function(event) {
		commonJs.doSubmit({
			form:"fmDefault",
			action:"/employee/leave/getUpdateLeaveRequest",
			data:{
				leaveRequestId:leaveRequestId
			}
		});
	});

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	/*!
	 * process
	 */
	setWindowSize = function() {
		if (mode == "detail" && accrualListCnt > 0) {
			parent.popup.resizeTo(0, ((accrualListCnt-1) * 27));
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
//		setWindowSize();
	});
});