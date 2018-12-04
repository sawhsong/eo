/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Sys0402List.js
 *************************************************************************************************/
jsconfig.put("scrollablePanelHeightAdjust", 4);

var popup = null;
var searchResultDataCount = 0;
var langCode = commonJs.upperCase(jsconfig.get("langCode"));
var delimiter = jsconfig.get("dataDelimiter");

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

	$("#btnSetSort").click(function(event) {
		openPopup({mode:"SetSort"});
	});

	$("#searchMenu").change(function() {
		doSearch();
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * context menus
	 */

	/*!
	 * process
	 */
	doSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		if (commonJs.doValidate($("#fmDefault"))) {
			setTimeout(function() {
				commonJs.ajaxSubmit({
					url:"/sys/0402/getList.do",
					dataType:"json",
					formId:"fmDefault",
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
				var gridTr = new UiGridTr();
				var menuPath = dataSet.getValue(i, "PATH");
				var menuId = dataSet.getValue(i, "MENU_ID");
				var menuName = dataSet.getValue(i, "MENU_NAME_"+langCode);
				var deletable = dataSet.getValue(i, "DELETABLE");
				var className = "chkEn", disabledStr = "";
				var space = "", style = "", paramValue = "";
				var iLevel = parseInt(dataSet.getValue(i, "MENU_LEVEL")) - 1;

				style = (iLevel == 0 || iLevel == 1) ? "font-weight:bold;" : "";
				for (var j=0; j<iLevel; j++) {
					space += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}

				if (!commonJs.toBoolean(deletable)) {
					className = "chkDis";
					disabledStr = "disabled";
				}

				paramValue = dataSet.getValue(i, "MENU_LEVEL")+delimiter+menuPath+delimiter+deletable;

				var uiChk = new UiCheckbox();
				uiChk.setId("chkForDel").setName("chkForDel").setValue(menuId).addAttribute("paramValue:"+paramValue).addOptions(disabledStr);
				gridTr.addChild(new UiGridTd().addClassName("Ct").addChild(uiChk));

				var uiAnc = new UiAnchor();
				uiAnc.setText(menuId).setScript("getDetail('"+menuId+"', '"+paramValue+"')");
				gridTr.addChild(new UiGridTd().addClassName("Lt").setStyle(style).addTextBeforeChild(space).addChild(uiAnc));

				gridTr.addChild(new UiGridTd().addClassName("Lt").setStyle(style).setText(menuName));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "MENU_URL")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(dataSet.getValue(i, "SORT_ORDER")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "DESCRIPTION")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(dataSet.getValue(i, "IS_ACTIVE")));

				var iconAction = new UiIcon();
				iconAction.setId("icnAction").setName("icnAction").addClassName("fa-tasks fa-lg").addAttribute("menuId:"+menuId).addAttribute("paramValue:"+paramValue).setScript("doAction(this)");
				gridTr.addChild(new UiGridTd().addClassName("Ct").addChild(iconAction));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:8").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea"),
			pagingArea:$("#divPagingArea"),
			isPageable:false,
			isFilter:false,
			filterColumn:[],
			totalResultRows:result.totalResultRows,
			script:"doSearch"
		});

		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(ctxMenu.commonAction);
		});

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getDetail = function(menuId, paramValue) {
		openPopup({
			mode:"Detail",
			menuId:menuId,
			paramValue:paramValue
		});
	};

	openPopup = function(param) {
		var url = "", header = "";
		var width = 700, height = 0;

		if (param.mode == "Detail") {
			url = "/sys/0402/getDetail.do";
			header = com.header.popHeaderDetail;
			height = 300;
		} else if (param.mode == "New") {
			url = "/sys/0402/getInsert.do";
			header = com.header.popHeaderEdit;
			height = 388;
		} else if (param.mode == "Edit") {
			url = "/sys/0402/getUpdate.do";
			header = com.header.popHeaderEdit;
			height = 374;
		} else if (param.mode == "SetSort") {
			url = "/sys/0402/getUpdateSortOrder.do";
			header = sys.sys0402.header.popHeaderSort;
			width = 900;
			height = 710;
		}

		var popParam = {
			popupId:"menu"+param.mode,
			url:url,
			paramData:{
				mode:param.mode,
				menuId:param.menuId,
				paramValue:param.paramValue
			},
			header:header,
			blind:true,
			width:width,
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
					commonJs.ajaxSubmit({
						url:"/sys/0402/exeDelete.do",
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
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}],
			blind:true
		});
	};

	doAction = function(img) {
		var menuId = $(img).attr("menuId"), paramValue = $(img).attr("paramValue");
		var paramValues = paramValue.split(delimiter);
		var deletable = paramValues[2];

		$("input:checkbox[name=chkForDel]").each(function(index) {
			if (!$(this).is(":disabled") && $(this).val() == menuId) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});

		if (deletable == "true") {
			ctxMenu.commonAction[2].disable = false;
		} else {
			ctxMenu.commonAction[2].disable = true;
		}

		ctxMenu.commonAction[0].fun = function() {getDetail(menuId, paramValue);};
		ctxMenu.commonAction[1].fun = function() {openPopup({mode:"Edit", menuId:menuId, paramValue:paramValue});};
		ctxMenu.commonAction[2].fun = function() {doDelete();};

		$(img).contextMenu(ctxMenu.commonAction, {
			classPrefix:com.constants.ctxClassPrefixGrid,
			displayAround:"trigger",
			position:"bottom",
			horAdjust:0,
			verAdjust:2
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
						url:"/sys/0402/exeExport.do",
						paramData:{
							fileType:menuObject.fileType,
							dataRange:menuObject.dataRange
						},
						header:"exportFile",
						blind:false,
						width:200,
						height:100
					});
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
		$("#searchWord").focus();
		commonJs.setExportButtonContextMenu($("#btnExport"));
		doSearch();
	});
});