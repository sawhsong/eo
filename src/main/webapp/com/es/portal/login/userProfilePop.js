/**
 * userProfilePop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnEdit").click(function(event) {
		commonJs.doSubmit({
			form:"fmDefault",
			action:"/login/getUpdateUserProfile",
			data:{}
		});
	});

	$("#btnClose").click(function(event) {
		parent.popupUserProfile.close();
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