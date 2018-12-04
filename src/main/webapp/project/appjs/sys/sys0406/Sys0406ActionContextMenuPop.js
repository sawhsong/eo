/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Sys0406UpdatePop.js
 *************************************************************************************************/
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		if (commonJs.doValidate("fmDefault")) {
			commonJs.confirm({
				contents:com.message.Q001,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						parent.exeActionContextMenu({
							mode:mode,
							authGroup:commonJs.getCheckedValueFromRadio("authGroup"),
							userType:commonJs.getCheckedValueFromRadio("userType"),
							userStatus:commonJs.getCheckedValueFromRadio("userStatus"),
							activeStatus:commonJs.getCheckedValueFromRadio("activeStatus")
						});

						parent.popup.close();
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
	showSectionByMode = function() {
		var divId = "";

		$("#divDataArea > div").each(function(index) {
			divId = "div"+mode;

			if ($(this).attr("id") == divId) {
				$(this).css("display", "block");
			} else {
				$(this).css("display", "none");
			}
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		showSectionByMode();
	});
});