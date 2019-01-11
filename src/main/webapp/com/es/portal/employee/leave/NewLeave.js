/**
 * 
 */
var spinner;

$(function() {
	/*!
	 * event
	 */
	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#icnStartDate").click(function(event) {
		commonJs.openCalendar(event, "startDate", {
			statusBar:false,
			weekNumber:false
		});
	});

	$("#icnEndDate").click(function(event) {
		commonJs.openCalendar(event, "endDate", {
			statusBar:false,
			weekNumber:false
		});
	});

	/*!
	 * process
	 */

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.setFieldDateMask("startDate");
		commonJs.setFieldDateMask("endDate");
		spinner = $("#duration").spinner();
	});
});