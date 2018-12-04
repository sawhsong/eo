/**
 * sourceGeneratorList.js
 */
var popup;
var searchResultDataCount = 0;
var langCode = commonJs.upperCase(jsconfig.get("langCode"));

var contextMenu = [{
	name:"Generate",
	img:"fa-gears",
	fun:function() {}
}];
$(function() {
	/*!
	 * event
	 */
	$("#btnGenerate").click(function(event) {
		if (commonJs.getCountChecked("chkForGenerate") == 0) {
			commonJs.warn(com.message.I902);
			return;
		}

		popup = commonJs.openPopup({
			popupId:"SourceGeneratorInfo",
			url:"/zebra/framework/sourcegenerator/getGeneratorInfo.do",
			header:framework.header.popHeaderGenerator,
			paramData:{},
			blind:false,
			width:760,
			height:584
		});
	});

	$("#btnSearch").click(function(event) {
		exeSearch();
	});

	$("#btnClear").click(function(event) {
		commonJs.clearSearchCriteria();
	});

	$("#searchMenu").change(function() {
		exeSearch();
	});

	$("#icnCheck").click(function(event) {
		commonJs.toggleCheckboxes("chkForGenerate");
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
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
					url:"/zebra/framework/sourcegenerator/getList.do",
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
				var space = "", style = "", menuId = "";
				var delimiter = jsconfig.get("dataDelimiter");
				var isLeaf = commonJs.toNumber(dataSet.getValue(i, "IS_LEAF"));
				var iLevel = commonJs.toNumber(dataSet.getValue(i, "MENU_LEVEL")) - 1;
				var isActive = commonJs.toBoolean(dataSet.getValue(i, "IS_ACTIVE"));

				for (var j=0; j<iLevel; j++) {
					space += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}

				menuId = dataSet.getValue(i, "ROOT")+delimiter+dataSet.getValue(i, "MENU_ID");
				style += (isLeaf != 1) ? "font-weight:bold;" : "";

				var tdSelect = new UiGridTd();
				tdSelect.addClassName("Ct");
				if (isActive) {
					var uiChk = new UiCheckbox();
					uiChk.setId("chkForGenerate").setName("chkForGenerate").setValue(menuId).addAttribute("menuName:"+dataSet.getValue(i, "MENU_NAME_"+langCode));
					tdSelect.addChild(uiChk);
				}
				gridTr.addChild(tdSelect);

				gridTr.addChild(new UiGridTd().addClassName("Lt").setStyle(style).addTextBeforeChild(space).setText(dataSet.getValue(i, "MENU_ID")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setStyle(style).setText(dataSet.getValue(i, "MENU_NAME_"+langCode)));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "MENU_URL")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(dataSet.getValue(i, "DESCRIPTION")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(dataSet.getValue(i, "CREATION_DATE")));

				var tdAction = new UiGridTd();
				tdAction.addClassName("Ct");
				if (isActive) {
					var iconAction = new UiIcon();
					iconAction.setId("icnAction").setName("icnAction").addClassName("fa-tasks fa-lg").addAttribute("menuId:"+menuId)
					.setScript("doAction(this)").addAttribute("title:"+com.header.action);
					tdAction.addChild(iconAction);
				}
				gridTr.addChild(tdAction);

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:7").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));

		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea"),
			pagingArea:$("#divPagingArea"),
			isPageable:false,
			totalResultRows:result.totalResultRows,
			script:"exeSearch"
		});

		$("[name=icnAction]").each(function(index) {
			$(this).contextMenu(contextMenu);
		});

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	doAction = function(img) {
		var menuId = $(img).attr("menuId");

		$("input:checkbox[name=chkForGenerate]").each(function(index) {
			if (!$(this).is(":disabled") && $(this).val() == menuId) {
				$(this).prop("checked", true);
			} else {
				$(this).prop("checked", false);
			}
		});

		contextMenu[0].fun = function() {$("#btnGenerate").trigger("click");};

		$(img).contextMenu(contextMenu, {
			classPrefix:com.constants.ctxClassPrefixGrid,
			displayAround:"trigger",
			position:"bottom",
			horAdjust:0,
			verAdjust:2
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.getBootstrapSelectbox("searchMenu").focus();

		exeSearch();
	});
});