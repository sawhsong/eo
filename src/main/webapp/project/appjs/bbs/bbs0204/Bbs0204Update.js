/**************************************************************************************************
 * Framework Generated Javascript Source
 * - Bbs0204Update.js
 *************************************************************************************************/
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		if (commonJs.doValidate("fmDefault")) {
			$("#fmDefault").attr("enctype", "multipart/form-data");

			commonJs.confirm({
				contents:com.message.Q001,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						commonJs.doSubmit({
							form:"fmDefault",
							action:"/bbs/0204/exeUpdate.do",
							data:{
								articleId:articleId
							}
						});
					}
				}, {
					caption:com.caption.no,
					callback:function() {
					}
				}]
			});
		}
	});

	$("#btnBack").click(function(event) {
		history.go(-1);
	});

	$("#btnAddFile").click(function(event) {
		commonJs.addFileSelectObject({
			appendToId:"divAttachedFile",
			rowBreak:false
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
	setEditor = function() {
		$("#articleContents").ckeditor({
			height:340,
			toolbar:com.constants.toolbarDefault
		});
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		setEditor();
	});
});