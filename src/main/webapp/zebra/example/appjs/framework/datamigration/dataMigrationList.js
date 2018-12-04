/**
 * dataMigration.js
 */
var popup = null;

$(function() {
	/*!
	 * event
	 */
	$("#btnSearch").click(function(event) {
		doSourceDataSearch();
		doTargetDataSearch();
	});

	$("#icnCheckSourceData").click(function(event) {
		commonJs.toggleCheckboxes("chkSourceData");
	});

	$("#btnGenerate").click(function(event) {
		if (commonJs.getCountChecked("chkSourceData") == 0) {
			commonJs.warn(com.message.I902);
			return;
		}

		commonJs.confirm({
			contents:com.message.Q902,
			width:300,
			height:150,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					exeGenerate();
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
	doSourceDataSearch = function() {
		commonJs.showProcMessageOnElement("tblSourceData");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/zebra/framework/datamigration/getTableList.do",
				dataType:"json",
				data:{dataSource:$("#sourceDb").val()},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");

					if (result.isSuccess == true || result.isSuccess == "true") {
						renderSourceDataTable(result);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 200);
	};

	renderSourceDataTable = function(result) {
		var dataSet = result.dataSet;
		var html = "";

		$("#tblSourceDataBody").html("");

		if (dataSet.getRowCnt() > 0) {
			for (var i=0; i<dataSet.getRowCnt(); i++) {
				var uiGridTr = new UiGridTr();

				var uiChk = new UiCheckbox();
				uiChk.setId("chkSourceData").setName("chkSourceData").setValue(dataSet.getValue(i, "TABLE_NAME"));
				uiGridTr.addChild(new UiGridTd().addClassName("Ct").addChild(uiChk));

				var uiAnc = new UiAnchor();
				uiAnc.setText(dataSet.getValue(i, "TABLE_NAME")).setScript("getDetail('"+$("#sourceDb").val()+"', '"+dataSet.getValue(i, "TABLE_NAME")+"')");
				uiGridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				uiGridTr.addChild(new UiGridTd().addClassName("Lt").setText(commonJs.abbreviate(dataSet.getValue(i, "COMMENTS"), 75)));

				html += uiGridTr.toHtmlString();
			}
		} else {
			var uiGridTr = new UiGridTr();

			uiGridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:3").setText(com.message.I001));
			html += uiGridTr.toHtmlString();
		}

		$("#tblSourceDataBody").append($(html));

		$("#tblSourceData").fixedHeaderTable({
			attachTo:$("#divSourceDataTable")
		});

		commonJs.hideProcMessageOnElement("tblSourceData");
	};

	doTargetDataSearch = function() {
		commonJs.showProcMessageOnElement("tblTargetData");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/zebra/framework/datamigration/getTableList.do",
				dataType:"json",
				data:{dataSource:$("#targetDb").val()},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");

					if (result.isSuccess == true || result.isSuccess == "true") {
						renderTargetDataTable(result);
					}
				}
			});
		}, 200);
	};

	renderTargetDataTable = function(result) {
		var dataSet = result.dataSet;
		var html = "";

		$("#tblTargetDataBody").html("");

		if (dataSet.getRowCnt() > 0) {
			for (var i=0; i<dataSet.getRowCnt(); i++) {
				var uiGridTr = new UiGridTr();

				var uiAnc = new UiAnchor();
				uiAnc.setText(dataSet.getValue(i, "TABLE_NAME")).setScript("getDetail('"+$("#targetDb").val()+"', '"+dataSet.getValue(i, "TABLE_NAME")+"')");
				uiGridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				uiGridTr.addChild(new UiGridTd().addClassName("Lt").setText(commonJs.abbreviate(dataSet.getValue(i, "COMMENTS"), 75)));

				html += uiGridTr.toHtmlString();
			}
		} else {
			var uiGridTr = new UiGridTr();

			uiGridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:2").setText(com.message.I001));
			html += uiGridTr.toHtmlString();
		}

		$("#tblTargetDataBody").append($(html));

		$("#tblTargetData").fixedHeaderTable({
			attachTo:$("#divTargetDataTable")
		});

		commonJs.hideProcMessageOnElement("tblTargetData");
	};

	getDetail = function(dbFlag, tableName) {
		popup = commonJs.openPopup({
			popupId:"TableDetail",
			url:"/zebra/framework/datamigration/getDetail.do",
			paramData:{
				tableName:tableName,
				dataSource:dbFlag
			},
			header:framework.header.popHeaderDetail,
			width:1300,
			height:800
		});
	};

	exeGenerate = function() {
		var param = {};

		param.sourceDb = $("#sourceDb").val();
		param.targetDb = $("#targetDb").val();

		popup = commonJs.openPopup({
			popupId:"ProcessInformation",
			header:framework.header.popHeaderResult,
			width:500,
			height:300,
			blind:false,
			onLoad:function() {
				$("input[name=chkSourceData]:checked").each(function(index) {
					var $this = $(this);

					setTimeout(function() {
						param.tableName = $this.val();

						commonJs.ajaxSubmit({
							url:"/zebra/framework/datamigration/doMigration.do",
							dataType:"json",
							data:param,
							blind:false,
							success:function(data, textStatus) {
								var result = commonJs.parseAjaxResult(data, textStatus, "json");

								if (result.isSuccess == true || result.isSuccess == "true") {
									popup.addContents(com.message.I802+" : "+param.tableName);

									if ((index+1) == commonJs.getCountChecked("chkSourceData")) {
										commonJs.openDialog({
											type:com.message.I000,
											contents:com.message.I801,
											modal:true,
											width:300,
											buttons:[{
												caption:com.caption.ok, callback:function() {
													try {
														popup.close();
														doTargetDataSearch();
													} catch(ex) {
													}
												}
											}]
										});
									}
								} else {
									popup.addContents(com.message.E801+" : "+param.tableName);
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
	setGridSize = function() {
		$("#divScrollablePanel").height($("#divScrollablePanel").outerHeight()-6);
	};

	$(window).load(function() {
		setGridSize();
		doSourceDataSearch();
		doTargetDataSearch();
	});
});