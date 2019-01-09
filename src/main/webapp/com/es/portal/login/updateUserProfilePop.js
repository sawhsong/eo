/**
 * updateUserProfilePop.js
 */
$(function() {
	/*!
	 * event
	 */
	$("#icnDateOfBirth").click(function(event) {
		commonJs.openCalendar(event, "dateOfBirth", {
			statusBar:false,
			adjustX:15,
			adjustY:-38,
			positionX:"right"
		});
	});

	$("#btnSave").click(function(event) {
		if (!commonJs.doValidate("fmDefault")) {
			return;
		}

		commonJs.confirm({
			contents:com.message.Q001,
			buttons:[{
				caption:com.caption.yes,
				callback:function() {
					doSave();
				}
			}, {
				caption:com.caption.no,
				callback:function() {
				}
			}]
		});
	});

	$("#btnBack").click(function(event) {
		history.go(-1);
	});

	$("#btnClose").click(function(event) {
		parent.popupUserProfile.close();
	});

	/*!
	 * google address search
	 */
	var autocompleteHome = null;

	initAutoComplete = function() {
		autocompleteHome = new google.maps.places.Autocomplete($("#searchAddress")[0], {types : ["geocode"]});
		autocompleteHome.addListener("place_changed", fillInAddress);
	};

	fillInAddress = function () {
		var addrLine = "", suburb = "", state = "", postCode = "", country = "";
		var place = autocompleteHome.getPlace();
		var placeType = place.types[0];

		for (var i=0; i<place.address_components.length; i++) {
			var addressType = place.address_components[i].types[0];
//console.log("addressType : "+addressType);
//console.log("address_components : "+place.address_components[i]["long_name"]);
			if ("subpremise" == placeType && "subpremise" == addressType) {
				addrLine += place.address_components[i]["long_name"];
			}

			if ("sublocality_level_3" == addressType) {
				addrLine += place.address_components[i]["long_name"];
			}

			if ("street_number" == addressType) {
				addrLine += (addrLine == "") ? place.address_components[i]["long_name"] : "/"+place.address_components[i]["long_name"];
			}

			if ("route" == addressType) {
				addrLine += (addrLine == "") ? place.address_components[i]["long_name"] : " "+place.address_components[i]["long_name"];

				var myAddr = "", regex = RegExp("^(.*)"+addrLine.split(" ",1)[0]), // get all the user entered values before a match with the first word from the Google result
				result = regex.exec($("#searchAddress").val());

				if (Array.isArray(result)) {
					myAddr = result[1]+""+addrLine; // add the street name to the user-entered unit & street number
				}
				addrLine = myAddr;
			}

			if ("postal_town" == addressType || "locality" == addressType || "sublocality_level_1" == addressType || "sublocality_level_2" == addressType) {
				suburb = place.address_components[i]["long_name"];
			}

			if ("administrative_area_level_1" == addressType || "administrative_area_level_2" == addressType) {
				state = place.address_components[i]["long_name"];
			}

			if ("postal_code" == addressType) {
				postCode = place.address_components[i]["long_name"];
			}

			if ("country" == addressType) {
				country = place.address_components[i]["long_name"];
			}
		}

		setAddressValues({
			street:addrLine,
			suburb:suburb,
			state:state,
			postCode:postCode,
			country:country
		});
	};

	setAddressValues = function(param) {
		commonJs.ajaxSubmit({
			url:"/common/comService/getStateCodeFromMeaning",
			dataType:"json",
			data:{
				state:param.state,
				country:param.country
			},
			success:function(data, textStatus) {
				var result = commonJs.parseAjaxResult(data, textStatus, "json");

				if (result.isSuccess == true || result.isSuccess == "true") {
					var ds = result.dataSet;

					$("#street").val(param.street);
					$("#suburb").val(param.suburb);
					$("#state").selectpicker("val", ds.getValue(0, "code"));
					$("#postCode").val(param.postCode);
					$("#country").val(param.country);
				} else {
					commonJs.error(result.message);
				}
			}
		});
	};

	doSave = function() {
		commonJs.ajaxSubmit({
			url:"/login/exeUpdateUserProfile",
			dataType:"json",
			formId:"fmDefault",
			data:{},
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
								commonJs.doSubmit({
									formId:"fmDefault",
									action:"/login/getUserProfile"
								});
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
		commonJs.setFieldDateMask("dateOfBirth");
	});
});