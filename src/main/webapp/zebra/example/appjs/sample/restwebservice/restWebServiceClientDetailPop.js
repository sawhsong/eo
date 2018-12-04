/**
 * restWebServiceClientDetailPop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnEdit").click(function(event) {
		doProcessByButton({mode:"Update"});
	});

	$("#btnReply").click(function(event) {
		doProcessByButton({mode:"Reply"});
	});

	$("#btnDelete").click(function(event) {
		doProcessByButton({mode:"Delete"});
	});

	$("#btnClose").click(function(event) {
		parent.popupNotice.close();
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	exeDownload = function(repositoryPath, originalName, newName) {
		var popup = commonJs.openPopup({
			popupId:"downloadFile",
			url:"/restServiceDownload.do",
			paramData:{
				repositoryPath:repositoryPath,
				originalName:originalName,
				newName:newName,
				webServiceUrl:"zebraRestNoticeBoard/download"
			},
			header:framework.header.fileDownload,
			blind:false,
			width:300,
			height:150
		});
		popup.close();
	};

	doProcessByButton = function(param) {
		var actionString = "";
		var params = {};

		if (param.mode == "Update") {
			actionString = "/zebra/sample/restwebservice/getUpdate.do";
		} else if (param.mode == "Reply") {
			actionString = "/zebra/sample/restwebservice/getInsert.do";
		} else if (param.mode == "Delete") {
			actionString = "/zebra/sample/restwebservice/exeDelete.do";
		}

		params = {
			form:"fmDefault",
			action:actionString,
			data:{
				mode:param.mode,
				articleId:articleId
			}
		};

		if (param.mode == "Update") {
			parent.popupNotice.resizeTo(0, 124);
		}

		if (param.mode == "Delete") {
			commonJs.confirm({
				contents:com.message.Q002,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						exeDelete(params);
					}
				}, {
					caption:com.caption.no,
					callback:function() {
					}
				}]
			});
		} else {
			commonJs.doSubmit(params);
		}
	};

	exeDelete = function(params) {
		commonJs.ajaxSubmit({
			url:"/zebra/sample/restwebservice/exeDelete.do",
			dataType:"json",
			formId:"fmDefault",
			data:{
				articleId:params.data.articleId
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
								parent.popupNotice.close();
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
	});
});