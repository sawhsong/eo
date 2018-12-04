/**
 * requestRegisterPop.js
 */
var popup = null;

$(function() {
	/*!
	 * event
	 */
	$(document).keypress(function(event) {
		if (event.which == 13) {
		}
	});

	$("#btnRequest").click(function() {
		doProcess();
	});

	/*!
	 * process
	 */
	doProcess = function() {
		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		if ($("#password").val() != $("#passwordConfirm").val()) {
			commonJs.error(login.message.confirmPassword);
		} else {
			commonJs.ajaxSubmit({
				url:"/login/exeRequestRegister.do",
				dataType:"json",
				formId:"fmDefault",
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						popup = commonJs.openDialog({
							type:com.message.I000,
							contents:result.message,
							blind:true,
							width:300,
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
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("[name=userName]").focus();
	});
});