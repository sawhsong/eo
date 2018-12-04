/**
 * updateUserProfilePop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		var fileValue = $("#filePhotoPath").val();

		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		if (!commonJs.isEmpty(fileValue)) {
			fileValue = fileValue.substring(fileValue.lastIndexOf(".")+1);
			if (!(fileValue.toLowerCase() == "png" || fileValue.toLowerCase() == "jpg" || fileValue.toLowerCase() == "gif" || fileValue.toLowerCase() == "jpeg")) {
				commonJs.doValidatorMessage($("#filePhotoPath"), "notUploadable");
				return;
			}
		}

		$("#fmDefault").attr("enctype", "multipart/form-data");

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					commonJs.doSubmit({
						form:"fmDefault",
						action:"/login/exeUpdate.do"
					});
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$("#btnBack").click(function(event) {
		parent.popupUserProfile.resizeTo(0, -110);
		history.go(-1);
	});

	$("#btnClose").click(function(event) {
		parent.popupUserProfile.close();
	});

	/*!
	 * process
	 */

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("#maxRowsPerPage").selectpicker({width:"90px"}).selectpicker("refresh");
		$("#userName").focus();
	});
});