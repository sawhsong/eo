/**
 * dtoGeneratorInfoPop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnClose").click(function(event) {
		parent.popupInfo.close();
	});

	$("#btnGenerate").click(function(event) {
		var selectedSystem = commonJs.getCheckedValueFromRadio("system");

		if (getCheckedCount(selectedSystem) <= 0) {
			commonJs.warn(com.message.I902);
			return;
		}

		commonJs.confirm({
			contents:com.message.Q901,
			width:300,
			height:150,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					exeGenerate($("#fmDefault").serializeArray());
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$("[name=system]").click(function(event) {
		if ($(this).val() == "Framework") {
			$("#divSystemProject").stop().animate({opacity:"hide"}, "fast");
			$("#divSystemFramework").stop().delay(300).animate({opacity:"show"}, jsconfig.get("effectDuration"));
		} else if ($(this).val() == "Project") {
			$("#divSystemFramework").stop().animate({opacity:"hide"}, "fast");
			$("#divSystemProject").stop().delay(300).animate({opacity:"show"}, jsconfig.get("effectDuration"));
		}
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	getCheckedCount = function(selectedSystem) {
		var checkedCnt = 0;

		$("input:checkbox").each(function(index) {
			var name = $(this).attr("name");
			if (name.indexOf(selectedSystem) != -1 && $(this).is(":checked")) {
				checkedCnt++;
			}
		});
		return checkedCnt;
	};

	exeGenerate = function(paramDataArray) {
		var paramData = {};
		$.each(paramDataArray, function(i, param) {
			paramData[param.name] = param.value;
		});
		paramData.dataSource = dataSource;

		popupProcess = parent.commonJs.openPopup({
			popupId:"ProcessInformation",
			header:framework.header.popHeaderResult,
			width:600,
			height:400,
			blind:false,
			onLoad:function() {
				parent.$("input[name=chkForGenerate]:checked").each(function(index) {
					var $this = $(this);

					setTimeout(function() {
						paramData.tableName = $this.val();

						commonJs.ajaxSubmit({
							url:"/zebra/framework/dtogenerator/exeGenerate.do",
							dataType:"json",
							data:paramData,
							blind:false,
							success:function(data, textStatus) {
								var result = commonJs.parseAjaxResult(data, textStatus, "json");

								if (commonJs.toBoolean(result.isSuccess)) {
									popupProcess.addContents(com.message.I802+" : "+paramData.tableName);

									if ((index+1) == parent.commonJs.getCountChecked("chkForGenerate")) {
										parent.commonJs.openDialog({
											type:com.message.I000,
											contents:com.message.I801,
											modal:true,
											width:300,
											buttons:[{
												caption:com.caption.ok, callback:function() {
													try {
														parent.popupInfo.close();
														popupProcess.close();
													} catch(ex) {
													}
												}
											}]
										});
									}
								} else {
									popupProcess.addContents(com.message.E801+" : "+paramData.tableName);
									popupProcess.addContents("[Error Message : "+data.message+"]");
								}
							}
						});
					}, index * 100);
				});
			}
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
	});
});