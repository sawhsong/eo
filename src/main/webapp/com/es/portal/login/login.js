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

	$("#aForgottenUserId").click(function(event) {
		doProcess({mode:"forgottenUserId"});
	});

	$("#aForgottenPassword").click(function(event) {
		doProcess({mode:"forgottenPassword"});
	});

	$("#aDisclaimer").click(function(event) {
		window.open("/serviceresource/pages/disclaimer");
	});

	$("#aPrivacy").click(function(event) {
		window.open("/serviceresource/pages/privacy");
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
						var actionString = commonJs.nvl(dataSet.getValue(0, "StartupUrl"), "/index/dashboard");

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
			if (params.mode == "forgottenUserId") {
				params = {
					popupId:"forgottenUserId",
					url:"/login/forgottenUserId",
					paramData:{},
					header:"Forgotten User ID",
					blind:false,
					draggable:false,
					width:400,
					height:250
				};
			} else if (params.mode == "forgottenPassword") {
				params = {
					popupId:"forgottenPassword",
					url:"/login/forgottenPassword",
					paramData:{},
					header:"Forgotten Password",
					blind:false,
					draggable:false,
					width:400,
					height:370
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