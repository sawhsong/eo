/**
 * login.js
 */
jsconfig.put("noLayoutWindow", true);
var popup = null;

$(function() {
	/*!
	 * event
	 */
	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;

			if ($(element).is("[name=loginId]") || $(element).is("[name=password]")) {
				doProcess({mode:"login"});
			}
		}
	});

	$("#btnLogin").click(function(event) {
		doProcess({mode:"login"});
	});

	$("#aResetPassword").click(function(event) {
		doProcess({mode:"resetPassword"});
	});

	$("#aRequestRegister").click(function(event) {
		doProcess({mode:"requestRegister"});
	});

	/*!
	 * process
	 */
	doProcess = function(params) {
		if (params.mode == "login") {
			if (!commonJs.doValidate("fmDefault")) {
				return;
			}

			commonJs.ajaxSubmit({
				url:"/login/login",
				dataType:"json",
				formId:"fmDefault",
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						var dataSet = result.dataSet;
						var actionString = commonJs.isEmpty(dataSet.getValue(0, "StartupUrl")) ? "/index/dashboard" : dataSet.getValue(0, "StartupUrl");

						commonJs.openDialog({
							type:com.message.I000,
							contents:result.message+" "+dataSet.getValue(0, "UserName")+" !",
							blind:true,
							draggable:false,
							width:350,
							buttons:[{
								caption:com.caption.ok,
								callback:function() {
									commonJs.doSubmit({
										formId:"fmDefault",
										action:actionString
									});
								}
							}]
						});
					} else {
						commonJs.error(result.message);
					}
				}
			});
		} else {
			if (params.mode == "resetPassword") {
				params = {
					popupId:"ResetPassword",
					url:"/login/resetPassword",
					paramData:{},
					header:login.header.resetPassword,
					blind:false,
					draggable:false,
					width:400,
					height:266
				};
			} else if (params.mode == "requestRegister") {
				params = {
					popupId:"Request Register",
					url:"/login/requestRegister",
					paramData:{},
					header:login.header.requestRegister,
					blind:false,
					draggable:false,
					width:400,
					height:406
				};
			}

			popup = commonJs.openPopup(params);
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("[name=loginId]").focus();
	});
});