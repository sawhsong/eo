/**
 * uielementList.js
 */
var popup1, popup2, cal;

$(function() {
	jsconfig.put("customFileObject", "N");

	$("#btnAddFileSelection").click(function(event) {
		commonJs.addFileSelectObject({
			appendToId:"divAttachedFile",
			rowBreak:false
		});
	});

	$("#btnPopupWindowExample1").click(function() {
		popup1 = commonJs.openPopup({
			popupId:"PopupWindowExample",
			effect:"fade",
			oncloseCallback:function() {
				alert("oncloseCallback");
			}
// 			width:600,
// 			height:400
		});
	});

	$("#icnFromDate").click(function(event) {
		commonJs.openCalendar(event, "txtFromDate", {
			weekNumber:false
		});
	});

	$("#icnToDate").click(function(event) {
		var options = {
			displayTime:true,
			statusBar:true,
			effect:"fade",
			returnBoxes:[{time:"txtToTime"}, {DoW:"txtToDay"}, {Week:"txtToWeek"}],
			oncloseCallback:function() {doCloseCalendar();}
		};
		commonJs.openCalendar(event, "txtToDate", options);
	});

	doCloseCalendar = function() {
//		alert("doCloseCalendar");
	};

	$("#btnPopupDialogExample1").click(function() {
		popup1 = commonJs.openDialog({
			type:"confirm",
			contents:"Are you sure to save the data?\nAre you sure to save the data?\nAre you sure to save the data?\nAre you sure to save the data?\nAre you sure to save the data?\nAre you sure to save the data?\n",
			effect:"fade",
			heightAdjust:1,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					alert("Yes clicked.");
				}
			}, {
				caption:com.caption.no,
				callback:function() {
//					alert("No clicked.");
				}
			}],
			blind:true
		});
	});

	$("#btnPrintLog").click(function() {
		commonJs.printLog({message:"This is a Log Test."});
		commonJs.printLog({message:"btnTypeButton has been clicked."});
	});

	$("#btnTestEmail").click(function() {
		testEmail();
	});

	testEmail = function() {
		commonJs.ajaxSubmit({
			url:"/zebra/sample/uielement/testEmail.do",
			dataType:"json",
			formId:"fmDefault",
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					commonJs.openDialog({
						type:com.message.I000,
						contents:result.dataSet.toString(),
						blind:true,
						buttons:[{
							caption:com.caption.ok,
							callback:function() {
							}
						}]
					});
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	$("#icnJQueryDatePicker").click(function(event) {
//		commonJs.openCalendar(event, "fromDate");
		$("#txtJQueryDatePicker").datepicker("show");
	});

	var menu = [ {
		name : 'create',
		fun : function() {
			alert('i am add button')
		}
	}, {
		name : 'update',
		subMenu : [ {
			name : 'merge',
			fun : function() {
				alert('It will merge row')
			}
		}, {
			name : 'replace',
			subMenu : [ {
				name : 'replace top 100',
				fun : function() {
					alert('It will replace top 100 rows');
				}

			}, {
				name : 'replace all',
				fun : function() {
					alert('It will replace all rows');
				}
			} ]
		} ]
	}, {
		name : 'delete',
		subMenu : [ {
			'name' : 'soft delete',
			fun : function() {
				alert('You can recover back');
			}
		}, {
			'name' : 'hard delete',
			fun : function() {
				alert('It will delete permanently');
			}
		} ]
	} ];

	$(window).load(function() {
		commonJs.changeTabSelection($("#tabCategory li:eq(0) a"));

		$("#divDataGrid").height(300);
		$("#tblFixedHeaderTable2").fixedHeaderTable({
			attachTo:$("#divDataGrid"),
			pagingArea:$("#divPagingArea"),
			isPageable:false,
			isFilter:false,
			filterColumn:[1, 2, 3],
			script:"doSearch"
		});

		commonJs.setAccordion({
			containerClass:"accordionFormElements",
			expandAll:false,
//			active:4
		});

		commonJs.setAccordion({
			containerClass:"accordionMiscellaneous",
			expandAll:false,
			active:0,
			multipleExpand:false,
			activate:function(event, ui) {
				var currHeader, currContent;

				if (ui.newHeader[0]) {
					currHeader = ui.newHeader;
					currContent = currHeader.next(".ui-accordion-content");
				} else {
					currHeader = ui.oldHeader;
					currContent = currHeader.next(".ui-accordion-content");
				}

//				commonJs.alert($(currHeader).prop("innerText"));
			}
		});

		$.nony.fileElement.modifyFileSelectObject($("#fileModified"));

		commonJs.setFieldDateMask("txtFromDate");
		commonJs.setFieldDateMask("txtToDate");

		$("#txtJQueryDatePicker").datepicker({
			numberOfMonths:3,
			shwAnim:"fadeIn",
			dateFormat:"dd-mm-yy",
			onSelect:function(date, obj) {
				alert(date);
				alert($(obj).attr("id"));
			}
		});

		$("#divJQueryInlineDatePicker").datepicker({
			numberOfMonths:3,
			dateFormat:"dd-mm-yy",
			onSelect:function(date, obj) {
				alert(date);
				alert($(obj).attr("id"));
			}
		});

		$("#slider").imageSlider({
			width:1000,
			height:500,
			autoSlide:false,
			arrowTheme:1
		});

		$("#btnAnchor").contextMenu(menu, {
// classPrefix:com.constants.ctxClassPrefixButton,
			borderRadius : "4px",
			displayAround : "trigger",
			position : "bottom",
			horAdjust : 0,
			verAdjust : 0
		});
	});
});