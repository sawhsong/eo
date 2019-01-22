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

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
	});
});