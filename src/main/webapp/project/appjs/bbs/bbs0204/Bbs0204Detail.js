/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Bbs0204Detail.js
 *************************************************************************************************/
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

	$("#btnBack").click(function(event) {
		history.go(-1);
	});

	$(document).keypress(function(event) {
		if (event.which == 13) {
			var element = event.target;
		}
	});

	/*!
	 * process
	 */
	setEditor = function() {
		$("#articleContents").ckeditor({
			height:500,
			toolbar:com.constants.toolbarDefault,
			readOnly:true
		});
	};

	exeDownload = function(repositoryPath, originalName, newName) {
		commonJs.doSubmit({
			form:"fmDefault",
			action:"/download.do",
			data:{
				repositoryPath:repositoryPath,
				originalName:originalName,
				newName:newName
			}
		});
	};

	doProcessByButton = function(param) {
		var action = "";
		var params = {};

		if (param.mode == "Update") {
			action = "/bbs/0204/getUpdate.do";
		} else if (param.mode == "Reply") {
			action = "/bbs/0204/getInsert.do";
		} else if (param.mode == "Delete") {
			action = "/bbs/0204/exeDelete.do";
		}

		params = {
			form:"fmDefault",
			action:action,
			data:{
				mode:param.mode,
				articleId:articleId
			}
		};

		if (param.mode == "Delete") {
			commonJs.confirm({
				contents:com.message.Q002,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						commonJs.ajaxSubmit({
							url:action,
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
										width:300,
										buttons:[{
											caption:com.caption.ok,
											callback:function() {
												commonJs.doSubmit({action:"/bbs/0204/getDefault.do"});
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
		} else {
			commonJs.doSubmit(params);
		}
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		setEditor();
	});
});