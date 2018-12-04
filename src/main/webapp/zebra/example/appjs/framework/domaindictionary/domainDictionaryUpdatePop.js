/**
 * domainDictionaryUpdatePop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		if ($("#dataType").val() == "VARCHAR2" && commonJs.isEmpty($("#dataLength").val())) {
			commonJs.openDialog({
				type:com.message.W000,
				contents:"Data Length" + com.message.mandatory,
				buttons:[{
					caption:com.caption.ok,
					callback:function() {
						commonJs.getBootstrapSelectbox("dataLength").addClass("error");
						return;
					}
				}]
			});

			return;
		}

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

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	exeSave = function() {
		commonJs.ajaxSubmit({
			url:"/zebra/framework/domaindictionary/exeUpdate.do",
			dataType:"json",
			formId:"fmDefault",
			data:{
				domainId:domainId
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					commonJs.openDialog({
						type:com.message.I000,
						contents:result.message,
						width:280,
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
	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		parent.popup.setHeader(framework.header.popHeaderEdit);
		$("#domainName").focus();
	});
});