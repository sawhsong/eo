/**
 * resetPasswordPop.js
 */
var popup = null;

$(function() {
	/*!
	 * event
	 */
	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;

			if ($(element).is("[name=loginId]") || $(element).is("[name=email]")) {
				doProcess();
			}
		}
	});

	$("#btnReset").click(function() {
		doProcess();
	});

	/*!
	 * process
	 */
	doProcess = function() {
		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		commonJs.ajaxSubmit({
			url:"/login/exeResetPassword.do",
			dataType:"json",
			formId:"fmDefault",
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");
				if (result.isSuccess == true || result.isSuccess == "true") {
					commonJs.openDialog({
						type:com.message.I000,
						contents:result.message,
						blind:true,
						buttons:[{
							caption:com.caption.ok,
							callback:function() {
								parent.popup.close();
							}
						}]
					});
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("[name=loginId]").focus();
	});
});