/**
 * tableScriptInsertPop.js
 */
jsconfig.put("useJqTooltip", false);
jsconfig.put("useJqSelectmenu", false);

var delimiter = jsconfig.get("dataDelimiter");

$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		var isValid = true;

		$("#liDummy").find(":input").each(function(index) {
			$(this).removeAttr("mandatory");
			$(this).removeAttr("option");
		});

		if (!commonJs.doValidate("fmDefault")) {
			isValid = false;
			return;
		}

		$("#ulColumnDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).find(":input").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

				if (commonJs.containsIgnoreCase(name, "columnName") && commonJs.isEmpty($(this).val())) {
					isValid = false;
					commonJs.doValidatorMessage($(this), "mandatory");
				}

				if (!isValid) {return;}

				if (commonJs.containsIgnoreCase(name, "description") && commonJs.isEmpty($(this).val())) {
					isValid = false;
					commonJs.doValidatorMessage($(this), "mandatory");
				}

				if (!isValid) {return;}
			});
		});

		if (!isValid) {return;}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					exeSave();
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

	$("#btnAdd").click(function(event) {
		var elem = $("#liDummy").clone(), elemId = $(elem).attr("id");

		$(elem).css("display", "block").appendTo($("#ulColumnDetailHolder"));

		$("#ulColumnDetailHolder").find(".dummyDetail").each(function(groupIndex) {
			$(this).attr("index", groupIndex).attr("id", elemId+delimiter+groupIndex);

			$(this).find("i").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find(".dragHandler").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find(".deleteButton").each(function(index) {
				var id = $(this).attr("id"), id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;
				$(this).attr("index", groupIndex).attr("id", id+delimiter+groupIndex);
			});

			$(this).find("input, select").each(function(index) {
				var id = $(this).attr("id"), name = $(this).attr("name");

				if (!commonJs.isEmpty(id)) {id = (id.indexOf(delimiter) != -1) ? id.substring(0, id.indexOf(delimiter)) : id;}
				else {id = "";}

				if (!commonJs.isEmpty(name)) {name = (name.indexOf(delimiter) != -1) ? name.substring(0, name.indexOf(delimiter)) : name;}
				else {name = "";}

				$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);

				if (groupIndex == ($("#ulColumnDetailHolder .dummyDetail").length - 1)) {
					if (name.indexOf("nullable") != -1) {
						if ($(this).val() == "Y") {$(this).prop("checked", true);}
					}
				}

				if ($(this).is("select")) {
					setSelectBoxes($(this));
				}
			});
		});

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
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
	exeSave = function() {
		var detailLength = $("#ulColumnDetailHolder .dummyDetail").length;

		commonJs.ajaxSubmit({
			url:"/zebra/framework/tablescript/exeInsert.do",
			dataType:"json",
			formId:"fmDefault",
			data:{
				detailLength:detailLength
			},
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
								parent.doSearch();
							}
						}]
					});
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	setSortable = function() {
		$("#ulColumnDetailHolder").sortable({
			axis:"y",
			handle:".dragHandler",
			stop:function() {
				$("#ulColumnDetailHolder").find(".dummyDetail").each(function(groupIndex) {
					$(this).find("input").each(function(index) {
						var id = $(this).attr("id"), name = $(this).attr("name");

						$(this).attr("id", id+delimiter+groupIndex).attr("name", name+delimiter+groupIndex);
					});
				});
			}
		});

		$("#ulColumnDetailHolder").disableSelection();
	};

	setSelectBoxes = function(jqObj) {
		$(jqObj).selectpicker({
			width:"auto",
			container:"body",
			style:$(jqObj).attr("class")
		});
	};

	validate = function(obj) {
		var objName = $(obj).attr("name"), currRowIdx = objName.split(delimiter)[1];
		var nameSuffix = delimiter+currRowIdx;

		if (commonJs.containsIgnoreCase(objName, "dataType")) {
			if ($("#dataType"+nameSuffix).val() == "CLOB") {
				$("#dataLength"+nameSuffix).selectpicker("show");
				$("#dataLength"+nameSuffix).selectpicker("val", "4000");
				$("#dataLength"+nameSuffix).prop("disabled", true);
				$("#dataLength"+nameSuffix).selectpicker("refresh");

				$("#defaultValue"+nameSuffix).val("EMPTY_CLOB()");
			} else if ($("#dataType"+nameSuffix).val() == "NUMBER") {
				$("#dataLength"+nameSuffix).selectpicker("hide");

				$("#dataLengthNumber"+nameSuffix).css("display", "block");
			} else if ($("#dataType"+nameSuffix).val() == "DATE") {
				$("#dataLength"+nameSuffix).selectpicker("show");
				$("#dataLength"+nameSuffix).prop("disabled", true);
				$("#dataLength"+nameSuffix).selectpicker("refresh");

				$("#defaultValue"+nameSuffix).val("");
				$("#dataLengthNumber"+nameSuffix).css("display", "none");
			} else {
				$("#dataLength"+nameSuffix).selectpicker("show");
				$("#defaultValue"+nameSuffix).val("");
				$("#dataLength"+nameSuffix).prop("disabled", false);
				$("#dataLength"+nameSuffix).selectpicker("refresh");

				$("#dataLengthNumber"+nameSuffix).css("display", "none");
			}
		}

		if (commonJs.containsIgnoreCase(objName, "keyType")) {
			if (!commonJs.isEmpty($("#keyType"+nameSuffix).val())) {
				$("[name = nullable"+nameSuffix+"]").each(function() {
					if ($(this).val() == "N") {
						$(this).prop("checked", true);
					}
				});
			}

			if ($("#keyType"+nameSuffix).val() == "FK") {
				$("#fkRef"+nameSuffix).removeClass("txtDis").addClass("txtEn").removeAttr("readonly");
			} else {
				$("#fkRef"+nameSuffix).removeClass("txtEn").addClass("txtDis").attr("readonly", "readonly").val("");
			}
		}

		if (commonJs.containsIgnoreCase(objName, "fkRef") && !commonJs.isEmpty($("#fkRef"+nameSuffix).val())) {
			if (!commonJs.contains($("#fkRef"+nameSuffix).val(), ".")) {
				commonJs.doValidatorMessage($("#fkRef"+nameSuffix), "notValid");
			}
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(document).click(function(event) {
		var obj = event.target;

		if ($(obj).hasClass("deleteButton") || ($(obj).is("i") && $(obj).parent("th").hasClass("deleteButton"))) {
			$("#ulColumnDetailHolder").find(".dummyDetail").each(function(index) {
				if ($(this).attr("index") == $(obj).attr("index")) {
					$(this).remove();

					$("#tblGrid").fixedHeaderTable({
						attachTo:$("#divDataArea")
					});
				}
			});
		}
	});

	$(window).load(function() {
		$("#tableName").focus();

		setSortable();

		setTimeout(function() {
			$("#tblGrid").fixedHeaderTable({
				attachTo:$("#divDataArea")
			});
		}, 500);
	});
});