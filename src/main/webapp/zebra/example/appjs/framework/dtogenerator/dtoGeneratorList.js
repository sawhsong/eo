/**
 * dtoGeneratorList.js
 */
var popupDetail = null;
var popupInfo = null;
var searchResultDataCount = 0;

$(function() {
	/*!
	 * event
	 */
	$("#btnGenerate").click(function(event) {
		if (commonJs.getCountChecked("chkForGenerate") == 0) {
			commonJs.warn(com.message.I902);
			return;
		}

		popupInfo = commonJs.openPopup({
			popupId:"DTOGeneratorInfo",
			url:"/zebra/framework/dtogenerator/getGeneratorInfo.do",
			header:framework.header.popHeaderGenerator,
			paramData:{dataSource:$("#dataSource").val()},
			blind:true,
			width:1000,
			height:520
		});
	});

	$("#btnSearch").click(function(event) {
		exeSearch();
	});

	$("#dataSource").change(function(event) {
		exeSearch();
	});

	$("#btnClear").click(function(event) {
		commonJs.clearSearchCriteria();
	});

	$("#icnCheck").click(function(event) {
		commonJs.toggleCheckboxes("chkForGenerate");
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;

			if ($(element).is("[name=tableName]")) {
				exeSearch();
				event.preventDefault();
			}
		}
	});

	/*!
	 * process
	 */
	exeSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		if (commonJs.doValidate($("#fmDefault"))) {
			setTimeout(function() {
				commonJs.ajaxSubmit({
					formId:"fmDefault",
					url:"/zebra/framework/dtogenerator/getList.do",
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
				var uiGridTr = new UiGridTr();

				var uiTd0 = new UiGridTd(), uiChk = new UiCheckbox();
				uiChk.setId("chkForGenerate").setName("chkForGenerate").setValue(dataSet.getValue(i, "TABLE_NAME"));
				uiTd0.addClassName("Ct").addChild(uiChk);
				uiGridTr.addChild(uiTd0);

				var uiTd1 = new UiGridTd(), uiAnc = new UiAnchor();
				uiAnc.setText(dataSet.getValue(i, "TABLE_NAME")).setScript("getDetail('"+dataSet.getValue(i, "TABLE_NAME")+"')");
				uiTd1.addClassName("Lt").addChild(uiAnc);
				uiGridTr.addChild(uiTd1);

				var uiTd2 = new UiGridTd();
				uiTd2.addClassName("Lt").setText(dataSet.getValue(i, "COMMENTS"));
				uiGridTr.addChild(uiTd2);

				var uiTd3 = new UiGridTd(), uiIcon = new UiIcon();
				uiIcon.setId("icnAction").setName("icnAction").addClassName("fa-tasks fa-lg").addAttribute("tableName:"+dataSet.getValue(i, "TABLE_NAME"))
					.addAttribute("title:"+com.caption.action).setScript("doAction(this)");
				uiTd3.addClassName("Ct").addChild(uiIcon);
				uiGridTr.addChild(uiTd3);

				html += uiGridTr.toHtmlString();
			}
		} else {
			var uiGridTr = new UiGridTr();

			var uiTd0 = new UiGridTd();
			uiTd0.addClassName("Ct").setAttribute("colspan:4").setText(com.message.I001);
			uiGridTr.addChild(uiTd0);

			html += uiGridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea"),
			pagingArea:$("#divPagingArea"),
			isPageable:false,
			displayRowCount:true,
			isFilter:false,
			filterColumn:[1, 2],
			totalResultRows:result.totalResultRows,
			script:"exeSearch"
		});

		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(ctxMenu.dtoGeneratorAction);
		});

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getDetail = function(tableName) {
		popupDetail = commonJs.openPopup({
			popupId:"TableDetail",
			url:"/zebra/framework/dtogenerator/getDetail.do",
			paramData:{
				tableName:tableName,
				dataSource:$("#dataSource").val()
			},
			header:framework.header.popHeaderTableInfo,
			width:1300,
			height:800
		});
	};

	doAction = function(img) {
		var tableName = $(img).attr("tableName");
		var dataSource = $("#dataSource").val();

		$("input:checkbox[name=chkForGenerate]").each(function(index) {
			if (!$(this).is(":disabled") && $(this).val() == tableName) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});

		ctxMenu.dtoGeneratorAction[0].fun = function() {getDetail(tableName);};
		ctxMenu.dtoGeneratorAction[1].fun = function() {$("#btnGenerate").trigger("click");};

		$(img).contextMenu(ctxMenu.dtoGeneratorAction, {
			classPrefix:com.constants.ctxClassPrefixGrid,
			displayAround:"trigger",
			position:"bottom",
			horAdjust:0,
			verAdjust:2,
			containment:$("#divScrollablePanel")
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(ctxMenu.dtoGeneratorAction);
		});
		exeSearch();
		$("#tableName").focus();
	});
});