/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Sys0406UpdatePop.js
 *************************************************************************************************/
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		var fileValue = $("#photoPath").val();

		if (commonJs.doValidate("fmDefault")) {
			if (!commonJs.isEmpty(fileValue)) {
				fileValue = fileValue.substring(fileValue.lastIndexOf(".")+1);
				if (!(fileValue.toLowerCase() == "png" || fileValue.toLowerCase() == "jpg" || fileValue.toLowerCase() == "gif" || fileValue.toLowerCase() == "jpeg")) {
					commonJs.doValidatorMessage($("#photoPath"), "notUploadable");
					return;
				}
			}

			$("#fmDefault").attr("enctype", "multipart/form-data");

			commonJs.confirm({
				contents:com.message.Q001,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						commonJs.doSubmit({
							form:"fmDefault",
							action:"/sys/0406/exeUpdate.do"
						});
					}
				}, {
					caption:com.caption.no,
					callback:function() {
					}
				}]
			});
		}
	});

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("#photoPath").focus();
	});
});