/**
 * 
 */
var popup = null;
var searchResultDataCount;

$(function() {
	/*!
	 * event
	 */
	$("#btnSearch").click(function() {
		doSearch();
	});

	$("#btnClear").click(function(event) {
		commonJs.clearSearchCriteria();
	});

	$("#icnFromDate").click(function(event) {
		commonJs.openCalendar(event, "fromDate", {
			adjustX:-80,
			positionX:"right"
		});
	});

	$("#icnToDate").click(function(event) {
		commonJs.openCalendar(event, "toDate");
	});

	$("#btnDelete").click(function(event) {
		doAction({mode:"Delete"});
	});

	$("#icnCheck").click(function(event) {
		commonJs.toggleCheckboxes("chkForAction");
	});

	setActionButtonContextMenu = function() {
		var ctxMenu = [{
			name:"Mark as Payrolled",
			img:"fa-money",
			fun:function() {doAction({mode:"Payrol"});}
		}, {
			name:"Unlock Request",
			img:"fa-unlock-alt",
			fun:function() {doAction({mode:"Unlock"});}
		}, {
			name:"Approve Request",
			img:"fa-check",
			fun:function() {doAction({mode:"Approve"});}
		}, {
			name:"Reject Request",
			img:"fa-reply-all",
			fun:function() {doAction({mode:"Reject"});}
		}, {
			name:"Export",
			img:"fa-download",
			fun:function() {doExport();}
		}];

		$("#btnAction").contextMenu(ctxMenu, {
			classPrefix:com.constants.ctxClassPrefixButton,
			effectDuration:300,
			effect:"slide",
			borderRadius:"bottom 4px",
			displayAround:"trigger",
			position:"bottom",
			horAdjust:-1
		});
	};

	/*!
	 * process
	 */
	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	doSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/employee/leaveadm/getLeaveList",
				formId:"fmDefault",
				dataType:"json",
				data:{
				},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						renderGridTable(result);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 200);
	};

	renderGridTable = function(result) {
		var ds = result.dataSet, html = "";

		searchResultDataCount = ds.getRowCnt();
		$("#tblGridBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				var gridTr = new UiGridTr();

				var uiChk = new UiCheckbox();
				uiChk.setId("chkForAction").setName("chkForAction").setValue(ds.getValue(i, "leaveRequestId"));
				gridTr.addChild(new UiGridTd().addClassName("Ct").addChild(uiChk));

				var uiAnc = new UiAnchor();
				uiAnc.setText(ds.getValue(i, "fullName")).setScript("getDetail('"+ds.getValue(i, "leaveRequestId")+"')");
				gridTr.addChild(new UiGridTd().addClassName("Lt").addChild(uiAnc));

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "leaveTypeDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "leaveCategoryDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "assignmentName")));
				gridTr.addChild(new UiGridTd().addClassName("Rt").setText(ds.getValue(i, "duration")+" "+ds.getValue(i, "durationUnitDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "startDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "endDate")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "statusDesc")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "submittedDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "approveRejectDate")));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:11").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));
		setGridTable();

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	getDetail = function(leaveRequestId) {
		popup = commonJs.openPopup({
			popupId:"leaveDetail",
			url:"/employee/leave/getLeaveDetail",
			paramData:{
				mode:"detail",
				leaveRequestId:leaveRequestId
			},
			header:"Leave Detail",
			width:840,
			height:880
		});
	};

	doAction = function(param) {
		if (commonJs.getCountChecked("chkForAction") <= 0) {
			commonJs.warn("There is no data to proceed.");
			return;
		}

		commonJs.confirm({
			contents:"Are you sure to proceed?",
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					commonJs.ajaxSubmit({
						url:"/employee/leaveadm/doAction",
						formId:"fmDefault",
						dataType:"json",
						data:{
							mode:param.mode
						},
						success:function(data, textStatus) {
							var result = commonJs.parseAjaxResult(data, textStatus, "json");
							if (result.isSuccess == true || result.isSuccess == "true") {
								commonJs.openDialog({
									type:com.message.I000,
									contents:"Job has been successfully completed.",
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
			}]
		});
	};

	doExport = function() {
		if (searchResultDataCount <= 0) {
			commonJs.warn("There is no data to download.");
			return;
		}

		commonJs.confirm({
			contents:com.message.Q003,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					popup = commonJs.openPopup({
						popupId:"exportFile",
						url:"/employee/leaveadm/exeExport",
						paramData:{},
						header:"Download as File",
						blind:false,
						width:200,
						height:100
					});
					setTimeout(function() {popup.close();}, 8000);
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
		setActionButtonContextMenu();

		commonJs.setFieldDateMask("fromDate");
		commonJs.setFieldDateMask("toDate");

		commonJs.setAutoComplete($("#personName"), {
			method:"getPersonByName",
			label:"fullName",
			value:"personId",
			minLength:2,
			focus:function(event, ui) {
				$("#personName").val(ui.item.label);
				$("#personId").val(ui.item.value);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#personName").val())) {
					$("#personId").val("");
					$("#personNumber").val("");
				}
			},
			select:function(event, ui) {
				$("#personName").val(ui.item.label);
				$("#personId").val(ui.item.value);
				doSearch();
				return false;
			}
		});

		commonJs.setAutoComplete($("#personNumber"), {
			method:"getPersonByNumber",
			label:"personNumber",
			value:"personId",
			minLength:2,
			focus:function(event, ui) {
				$("#personNumber").val(ui.item.label);
				$("#personId").val(ui.item.value);
				return false;
			},
			change:function(event, ui) {
				if (commonJs.isEmpty($("#personNumber").val())) {
					$("#personId").val("");
					$("#personName").val("");
				}
			},
			select:function(event, ui) {
				$("#personNumber").val(ui.item.label);
				$("#personId").val(ui.item.value);
				doSearch();
				return false;
			}
		});

		doSearch();
	});
});