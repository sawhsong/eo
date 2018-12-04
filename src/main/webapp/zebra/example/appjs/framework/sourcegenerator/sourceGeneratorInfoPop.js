/**
 * sourceGeneratorInfoPop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#btnGenerate").click(function(event) {
		commonJs.confirm({
			contents:com.message.Q901,
			width:300,
			height:150,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					$("input[type=checkbox]").each(function(index) {
						$(this).prop("disabled", false);
					});
					exeGenerate($("#fmDefault").serializeArray());
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	exeGenerate = function(paramDataArray) {
		var paramData = {};
		$.each(paramDataArray, function(i, param) {
			paramData[param.name] = param.value;
		});

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
						paramData.menuId = $this.val();
						paramData.menuName = $this.attr("menuName");

						commonJs.ajaxSubmit({
							url:"/zebra/framework/sourcegenerator/exeGenerate.do",
							dataType:"json",
							data:paramData,
							blind:false,
							success:function(data, textStatus) {
								var result = commonJs.parseAjaxResult(data, textStatus, "json");

								if (result.isSuccess == true || result.isSuccess == "true") {
									var dataDelimiter = jsconfig.get("dataDelimiter");
									var menuIdArray = paramData.menuId.split(dataDelimiter);
									var menuId = menuIdArray[1];
									popupProcess.addContents(com.message.I802+" : "+menuId);

									if ((index+1) == parent.commonJs.getCountChecked("chkForGenerate")) {
										parent.commonJs.openDialog({
											type:com.message.I000,
											contents:com.message.I801,
											modal:true,
											width:300,
											buttons:[{
												caption:com.caption.ok, callback:function() {
													try {
														parent.popup.close();
														popupProcess.close();
													} catch(ex) {
													}
												}
											}]
										});
									}
								} else {
									popupProcess.addContents(com.message.E801+" : "+paramData.menuId);
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
		commonJs.setAutoComplete($("#tableName"), {
			url:"/zebra/common/autoCompletion/",
			method:"getTableName",
			label:"table_name",
			value:"table_name",
			focus: function(event, ui) {
				$("#tableName").val(ui.item.label);
				return false;
			},
			select:function(event, ui) {
				$("#tableName").val(ui.item.value);
			},
			open:function(event, ui) {
				var autocomplete = $(".ui-autocomplete");
				var oldTop = autocomplete.offset().top;
				var newTop = oldTop - autocomplete.height()-$("#tableName").height()-16;
				autocomplete.css("top", newTop);
			}
		});
	});
});