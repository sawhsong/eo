/**
 * commonCodeList.js
 */
var popup = null;
var searchResultDataCount = 0;
var dateFormat = jsconfig.get("dateFormatJs");
var langCode = jsconfig.get("langCode").toUpperCase();

$(function() {
	/*!
	 * event
	 */
	$("#btnNew").click(function(event) {
		openPopup({mode:"New"});
	});

	$("#btnDelete").click(function(event) {
		doDelete();
	});

	$("#btnSearch").click(function(event) {
		doSearch();
	});

	$("#btnClear").click(function(event) {
		commonJs.clearSearchCriteria();
	});

	$("#icnCheck").click(function(event) {
		commonJs.toggleCheckboxes("chkForDel");
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;

			if ($(element).is("[name=searchWord]")) {
				doSearch();
			}
		}
	});

	/*!
	 * process
	 */
	doSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		if (commonJs.doValidate($("#fmDefault"))) {
			setTimeout(function() {
				commonJs.ajaxSubmit({
					formId:"fmDefault",
					url:"/zebra/framework/commoncode/getList.do",
					dataType:"json",
					success:function(data, textStatus) {
						var result = commonJs.parseAjaxResult(data, textStatus, "json");

						if (result.isSuccess == true || result.isSuccess == "true") {
							renderDataGridTable(result);
						}
					}
				});
			}, 200);
		}
	};

	renderDataGridTable = function(result) {
		var dataSet = result.dataSet;
		var html = "";

		searchResultDataCount = dataSet.getRowCnt();

		$("#tblGridBody").html("");

		if (dataSet.getRowCnt() > 0) {
			for (var i=0; i<dataSet.getRowCnt(); i++) {
				var defaultYn = dataSet.getValue(i, "DEFAULT_YN");
				var className = "chkEn", disabledStr = "";
				var uiGridTr = new UiGridTr();

				if ("Y" == defaultYn) {
					className = "chkDis";
					disabledStr = "disabled";
				}

				var uiChk = new UiCheckbox();
				uiChk.setId("chkForDel").setName("chkForDel").removeClassName("chkEn").addClassName(className).setValue(dataSet.getValue(i, "CODE_TYPE")).setOptions(disabledStr);
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").addChild(uiChk));

				var uiAnc = new UiAnchor();
				uiAnc.setText(dataSet.getValue(i, "CODE_TYPE")).setScript("getDetail('"+dataSet.getValue(i, "CODE_TYPE")+"')");
				uiGridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				uiGridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "DESCRIPTION_"+langCode)));
				uiGridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "PROGRAM_CONSTANTS")));
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").setText(dataSet.getValue(i, "USE_YN")));
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").setText(defaultYn));
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").setText(commonJs.getDateTimeMask(dataSet.getValue(i, "INSERT_DATE"), dateFormat)));
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").setText(commonJs.getDateTimeMask(dataSet.getValue(i, "UPDATE_DATE"), dateFormat)));

				var uiTd3 = new UiGridTd(), uiIcon = new UiIcon();
				uiIcon.setId("icnAction").setName("icnAction").addClassName("fa-tasks fa-lg").addAttribute("codeType:"+dataSet.getValue(i, "CODE_TYPE"))
					.addAttribute("defaultYn:"+defaultYn).setScript("doAction(this)");
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").addChild(uiIcon));

				html += uiGridTr.toHtmlString();
			}
		} else {
			var uiGridTr = new UiGridTr();

			uiGridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:9").setText(com.message.I001));
			html += uiGridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea"),
			pagingArea:$("#divPagingArea"),
			isPageable:true,
			isFilter:false,
			filterColumn:[1, 2, 3],
			totalResultRows:result.totalResultRows,
			script:"doSearch"
		});

		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(ctxMenu.commonAction);
		});

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getDetail = function(codeType) {
		openPopup({mode:"Detail", codeType:codeType});
	};

	openPopup = function(param) {
		var url = "", header = "";
		var height = 900;

		if (param.mode == "Detail") {
			url = "/zebra/framework/commoncode/getDetail.do";
			header = framework.header.popHeaderDetail;
		} else if (param.mode == "New") {
			url = "/zebra/framework/commoncode/getInsert.do";
			header = framework.header.popHeaderEdit;
		} else if (param.mode == "Edit") {
			url = "/zebra/framework/commoncode/getUpdate.do";
			header = framework.header.popHeaderEdit;
			height = 754;
		}

		var popParam = {
			popupId:"commonCode"+param.mode,
			url:url,
			paramData:{
				mode:param.mode,
				codeType:commonJs.nvl(param.codeType, "")
			},
			header:header,
			blind:true,
			width:1700,
			height:height
		};

		popup = commonJs.openPopup(popParam);
	};

	doDelete = function() {
		if (commonJs.getCountChecked("chkForDel") == 0) {
			commonJs.warn(com.message.I902);
			return;
		}

		commonJs.confirm({
			contents:com.message.Q002,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					exeDelete();
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}],
			blind:true
		});
	};

	exeDelete = function() {
		commonJs.ajaxSubmit({
			url:"/zebra/framework/commoncode/exeDelete.do",
			dataType:"json",
			formId:"fmDefault",
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
								doSearch();
							}
						}]
					});
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	doAction = function(img) {
		var codeType = $(img).attr("codeType");
		var defaultYn = $(img).attr("defaultYn");

		$("input:checkbox[name=chkForDel]").each(function(index) {
			if (!$(this).is(":disabled") && $(this).val() == codeType) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});

		if (defaultYn == "Y") {
			ctxMenu.commonAction[2].disable = true;
		} else {
			ctxMenu.commonAction[2].disable = false;
		}

		ctxMenu.commonAction[0].fun = function() {getDetail(codeType);};
		ctxMenu.commonAction[1].fun = function() {openPopup({mode:"Edit", codeType:codeType});};
		ctxMenu.commonAction[2].fun = function() {doDelete();};

		$(img).contextMenu(ctxMenu.commonAction, {
			classPrefix:com.constants.ctxClassPrefixGrid,
			displayAround:"trigger",
			position:"bottom",
			horAdjust:0,
			verAdjust:2,
			containment:$("#divScrollablePanel")
		});
	};

	exeExport = function(menuObject) {
		$("[name=fileType]").remove();
		$("[name=dataRange]").remove();

		if (searchResultDataCount <= 0) {
			commonJs.warn(com.message.I001);
			return;
		}

		commonJs.confirm({
			contents:com.message.Q003,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					popup = commonJs.openPopup({
						popupId:"exportFile",
						url:"/zebra/framework/commoncode/exeExport.do",
						paramData:{
							fileType:menuObject.fileType,
							dataRange:menuObject.dataRange
						},
						header:framework.header.fileExport,
						blind:false,
						width:200,
						height:100
					});
					// needs delayed time - sometimes causing the error [getOutputStream() has already been called for this response]
					setTimeout(function() {popup.close();}, 3000);
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}],
			blind:true
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(ctxMenu.commonAction);
		});

		commonJs.setAutoComplete($("#commonCodeType"), {
			url:"/zebra/common/autoCompletion/",
			method:"getCommonCodeType",
			label:"description"+langCode,
			value:"code_type",
			select:function(event, ui) {
				doSearch();
			}
		});

		$("#commonCodeType").focus();

		commonJs.setExportButtonContextMenu($("#btnExport"));

		doSearch();
	});
});