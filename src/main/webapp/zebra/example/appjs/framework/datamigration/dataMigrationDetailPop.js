/**
 * dataMigrationDetailPop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnGenerate").click(function(event) {
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
	exeGenerate = function() {
		var param = {};

		param.sourceDb = parent.$("#sourceDb").val();
		param.targetDb = parent.$("#targetDb").val();

		popup = commonJs.openPopup({
			popupId:"ProcessInformation",
			header:framework.header.popHeaderResult,
			width:500,
			height:300,
			blind:false,
			onLoad:function() {
				setTimeout(function() {
					param.tableName = tableName;

					commonJs.ajaxSubmit({
						url:"/zebra/framework/datamigration/doMigration.do",
						dataType:"json",
						data:param,
						blind:false,
						success:function(data, textStatus) {
							var result = commonJs.parseAjaxResult(data, textStatus, "json");

							if (result.isSuccess == true || result.isSuccess == "true") {
								popup.addContents(com.message.I802+" : "+param.tableName);

								commonJs.openDialog({
									type:com.message.I000,
									contents:com.message.I801,
									modal:true,
									width:300,
									buttons:[{
										caption:com.caption.ok, callback:function() {
											try {
												popup.close();
												parent.popup.close();
												parent.doTargetDataSearch();
											} catch(ex) {
											}
										}
									}]
								});
							} else {
								popup.addContents(com.message.E801+" : "+param.tableName);
							}
						}
					});
				}, 200);
			}
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).ready(function() {
		setTimeout(function() {
			$("#tblGrid").fixedHeaderTable({
				attachTo:$("#divDataArea")
			});
		}, 500);
	});
});