/**
 * 
 */
var spinner;

$(function() {
	/*!
	 * event
	 */
	$("#btnSave").click(function(event) {
		var isValid = true;

		if (commonJs.doValidate("fmDefault")) {
			if (!checkValidation()) {
				isValid = false;
				return;
			}

			if (!isValid) {return;}

			$("#fmDefault").attr("enctype", "multipart/form-data");

			commonJs.confirm({
				contents:com.message.Q001,
				buttons:[{
					caption:com.caption.yes,
					callback:function() {
						commonJs.doSubmit({
							form:"fmDefault",
							action:"/expense/saveExpenseClaim",
							data:{
								expenseClaimId:expenseClaimId
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

	$("#btnClose").click(function(event) {
		parent.popup.close();
	});

	$("#icnDateOfClaim").click(function(event) {
		commonJs.openCalendar(event, "dateOfClaim", {
			statusBar:false,
			weekNumber:false
		});
	});

	$("#btnAddFile").click(function(event) {
		commonJs.addFileSelectObject({
			appendToId:"divAttachedFile",
			rowBreak:false
		});
	});

	/*!
	 * process
	 */
	checkValidation = function() {
		var isValid = true;

		return isValid;
	};

	setNumberFormat = function() {
		$("#bsb").number(true, 0, "", "");
		$("#accountNumber").number(true, 0, "", "");

		$("#amount").number(true, 2);
		$("#gst").number(true, 2);
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		commonJs.setFieldDateMask("dateOfClaim");
		setNumberFormat();
	});
});