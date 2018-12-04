/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Sys0402InsertPop.js
 *************************************************************************************************/
$(function() {
	/*!
	 * event
	 */
	$("#menuLevel").change(function(event) {
		var value = $(this).val();

		if (value == 2) {
			$("#divLevel1").css("display", "block");
			$("#divLevel2").css("display", "none");
		} else if (value == 3) {
			$("#divLevel1").css("display", "block");
			$("#divLevel2").css("display", "block");
		} else {
			$("#divLevel1").css("display", "none");
			$("#divLevel2").css("display", "none");
		}

		setLevel2Selectbox();
		setFieldValue();
	});

	$("#level1").change(function(event) {
		setLevel2Selectbox();
		setFieldValue();
	});

	$("#level2").change(function(event) {
		setFieldValue();
	});

	$("#btnSave").click(function(event) {
		if (!commonJs.doValidate("fmDefault")) {return;}

		if (!doValidate()) {return;}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					commonJs.ajaxSubmit({
						url:"/sys/0402/exeInsert.do",
						dataType:"json",
						formId:"fmDefault",
						data:{
						},
						success:function(data, textStatus) {
							var result = commonJs.parseAjaxResult(data, textStatus, "json");

							if (result.isSuccess == true || result.isSuccess == "true") {
								commonJs.openDialog({
									type:com.message.I000,
									contents:result.message,
									blind:true,
									width:300,
									buttons:[{
										caption:com.caption.ok,
										callback:function() {
											parent.popup.close();
											parent.doSearch();
										}
									}]
								});
							} else {
								commonJs.error(result.message);
							}
						}
					});
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
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
	setLevel2Selectbox = function() {
		var level1MenuId = $("#level1").val();

		$("#level2").find("option").remove();
		for (var i=0; i<dsMenu2.getRowCnt(); i++) {
			var parentMenu = dsMenu2.getValue(i, "PARENT_MENU_ID");
			if (parentMenu == level1MenuId) {
				$("#level2").append("<option value=\""+dsMenu2.getValue(i, "MENU_ID")+"\">"+dsMenu2.getValue(i, "MENU_ID")+"</option>");
			}
		}
		$("#level2").selectpicker("refresh");
	};

	setFieldValue = function() {
		var menuLevel = $("#menuLevel").val();
		var level1MenuId = $("#level1").val();
		var level2MenuId = $("#level2").val();
		var sortOrder = getSortOrder(menuLevel);

		$("#menuId").val("");
		$("#menuUrl").val("");
		$("#sortOrder").val("");

		if (menuLevel == 1) {
			$("#menuUrl").val("#");
			$("#sortOrder").val(sortOrder);
		} else if (menuLevel == 2) {
			if (!commonJs.isEmpty(level1MenuId)) {
				$("#menuId").val(commonJs.getFormatString(level1MenuId, "???"));
			}
			$("#menuUrl").val("#");
			$("#sortOrder").val(sortOrder);
		} else if (menuLevel == 3) {
			if (!commonJs.isEmpty(level2MenuId)) {
				$("#menuId").val(commonJs.getFormatString(level2MenuId, "???????"));
				$("#menuUrl").val("/"+commonJs.getFormatString(level2MenuId.toLowerCase(), "???/????/")+"getDefault.do");
			}
			$("#sortOrder").val(sortOrder);
		}
	};

	getSortOrder = function() {
		var menuLevel = $("#menuLevel").val();
		var level1MenuId = $("#level1").val();
		var level2MenuId = $("#level2").val();
		var sortOrder = 0;

		for (var i=0; i<dsMenu.getRowCnt(); i++) {
			if (menuLevel == 1) {
				if (menuLevel == dsMenu.getValue(i, "LEVEL")) {
					sortOrder = dsMenu.getValue(i, "SORT_ORDER");
				}
			} else if (menuLevel == 2) {
				if (menuLevel == dsMenu.getValue(i, "LEVEL") && level1MenuId == dsMenu.getValue(i, "ROOT")) {
					sortOrder = dsMenu.getValue(i, "SORT_ORDER");
				}
			} else if (menuLevel == 3) {
				if (menuLevel == dsMenu.getValue(i, "LEVEL") && level2MenuId == dsMenu.getValue(i, "PARENT_MENU_ID")) {
					sortOrder = dsMenu.getValue(i, "SORT_ORDER");
				}
			}
		}

		if (sortOrder == 0) {
			for (var i=0; i<dsMenu.getRowCnt(); i++) {
				if (menuLevel == 1) {
				} else if (menuLevel == 2) {
					if (dsMenu.getValue(i, "LEVEL") == "1" && level1MenuId == dsMenu.getValue(i, "MENU_ID")) {
						sortOrder = dsMenu.getValue(i, "SORT_ORDER");
					}
				} else if (menuLevel == 3) {
					if (dsMenu.getValue(i, "LEVEL") == "2" && level2MenuId == dsMenu.getValue(i, "MENU_ID")) {
						sortOrder = dsMenu.getValue(i, "SORT_ORDER");
					}
				}
			}
		}

		return sortOrder;
	};

	doValidate = function() {
		var menuLevel = $("#menuLevel").val();
		var level1MenuId = $("#level1").val();
		var level2MenuId = $("#level2").val();
		var menuId = $("#menuId").val();
		var menuUrl = $("#menuUrl").val();
		var sortOrder = $("#sortOrder").val();

		if (menuLevel == 1) {
			if (commonJs.isNumber(menuId) || menuId.length != 3) {return commonJs.doValidatorMessage($("#menuId"), "notValid");}
			if (menuUrl != "#") {return commonJs.doValidatorMessage($("#menuUrl"), "notValid");}
		} else if (menuLevel == 2) {
			if (!commonJs.startsWith(menuId, level1MenuId) || menuId.length != 7) {return commonJs.doValidatorMessage($("#menuId"), "notValid");}
		} else if (menuLevel == 3) {
			level2MenuId = commonJs.getFormatString(level2MenuId, "?????");
			if (commonJs.getFormatString(menuId, "?????") != level2MenuId || menuId.length != 7) {return commonJs.doValidatorMessage($("#menuId"), "notValid");}
			if (menuUrl == "#" || menuUrl.indexOf("/") == -1) {return commonJs.doValidatorMessage($("#menuUrl"), "notValid");}
		}

		if (sortOrder.length != 6) {return commonJs.doValidatorMessage($("#sortOrder"), "notValid");}

		return true;
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("#level1").selectpicker({width:"80px"}).selectpicker("refresh");
		$("#level2").selectpicker({width:"90px"}).selectpicker("refresh");
		commonJs.getBootstrapSelectbox("menuLevel").focus();
		setLevel2Selectbox();
		setFieldValue();
	});
});