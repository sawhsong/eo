/**
 * garbageCollection.js
 */
$(function() {
	$(document).bind("click", function(event) {
		if ("btnRun" == event.target.id || "btnReload" == event.target.id) {
			if ("btnRun" == event.target.id) {
				$("#hdnGarbageCollector").val("Run");
			}

			commonJs.doSubmit({
				formId:"fmDefault",
				action:"/zebra/main/getGarbageColletion.do"
			});
		}

		if ("btnClose" == event.target.id) {
			parent.garbageCollectorPopup.close();
		}
	});
});