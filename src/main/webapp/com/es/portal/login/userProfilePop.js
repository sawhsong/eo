/**
 * userProfilePop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnEdit").click(function(event) {
		parent.popupUserProfile.resizeTo(0, 140);

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