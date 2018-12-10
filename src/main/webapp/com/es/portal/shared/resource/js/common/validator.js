/**
 * validator JavaScript
 */
(function($) {
	$.nony.validator = {
		doValidate : function(object) {
			/*!
			 * available parameter : (form object) or (html element id/name array)
			 */
			var obj = $.nony.getElement(object);
			var objToCheck = [];

			if (object == null || object == "" || object == "undefined") {
				throw new Error("Parameter" + com.message.invalid);
				return;
			}

			if ($(obj)[0].tagName == "FORM" || $(obj)[0].tagName == "form") {objToCheck = $(obj)[0].elements;}
			else {objToCheck = $(obj);}

			for (var i=0; i<objToCheck.length; i++) {
				var elem = $.nony.getElement(objToCheck[i]);
				var maxlength = $(elem).attr("maxlength"), minlength = $(elem).attr("minlength"), match = $(elem).attr("match"), mandatory = $(elem).attr("mandatory"), option = $(elem).attr("option");

				if ($(elem)[0].tagName == "FIELDSET" || $(elem)[0].tagName == "fieldset" || $(elem).prop("type") == "RADIO" || $(elem).prop("type") == "radio") {continue;}

				if (!($(elem).attr("type") == "file" || $(elem).attr("type") == "FILE") && !($(elem)[0].tagName == "FIELDSET" || $(elem)[0].tagName == "fieldset")) {
					$(elem).val(this._rtrim($(elem).val()));
				}

				if (!(mandatory == null || mandatory == undefined)) {
					if ($(elem)[0].tagName.toLowerCase().indexOf("select") != -1 && $.nony.isEmpty($(elem).val())) {
						return this._execError($(elem), this._messages["mandatory_"+jsconfig.get("langCode")], "select");
					}

					if ($.nony.isEmpty($(elem).val())) {
						return this._execError($(elem), this._messages["mandatory_"+jsconfig.get("langCode")], "select");
					}

					if (!$.nony.isEmpty(option) && (option == "Numeric" || option == "numeric") && $.nony.toNumber($(elem).val()) == 0) {
						return this._execError($(elem), this._messages["numeric_"+jsconfig.get("langCode")], "select");
					}
				}

				if (!$.nony.isEmpty(minlength)) {
					var msg = "";

					if (parseInt(this._bytes($(elem).val())) < parseInt(minlength)) {
						if (!$.nony.isEmpty(option) && (option == "IncKor" || option == "incKor")) {
							msg = $.nony.replace(this._messages["minlengthIncKor_"+jsconfig.get("langCode")], "{minlength}", Math.round(minlength/2));
						} else if (!$.nony.isEmpty(option) && (option == "EngOnly" || option == "engOnly")) {
							msg = $.nony.replace(this._messages["minlengthEngOnly_"+jsconfig.get("langCode")], "{minlength}", minlength);
						} else {
							msg = $.nony.replace(this._messages["minlength_"+jsconfig.get("langCode")], "{minlength}", minlength);
						}
						return this._execError($(elem), msg, "select");
					}
				}

				if (!$.nony.isEmpty(maxlength) && !$.nony.isEmpty($(elem).val())) {
					var msg = "";

					if (parseInt(this._bytes($(elem).val())) > parseInt(maxlength)) {
						if (!$.nony.isEmpty(option) && (option == "IncKor" || option == "incKor")) {
							msg = $.nony.replace(this._messages["maxlengthIncKor_"+jsconfig.get("langCode")], "{maxlength}", Math.round(maxlength/2));
						} else if (!$.nony.isEmpty(option) && (option == "EngOnly" || option == "engOnly")) {
							msg = $.nony.replace(this._messages["maxlengthEngOnly_"+jsconfig.get("langCode")], "{maxlength}", maxlength);
						} else {
							msg = $.nony.replace(this._messages["maxlength_"+jsconfig.get("langCode")], "{maxlength}", maxlength);
						}
						return this._execError($(elem), msg, "select");
					}
				}

				if (!$.nony.isEmpty(match) && $(elem).val() != $($.nony.getElement(match)).val()) {
					return this._execError($(elem), this._messages["match_"+jsconfig.get("langCode")], "select");
				}

				if (!$.nony.isEmpty(option) && !$.nony.isEmpty($(elem).val())) {
					if (!this._functions[option]($(elem))) {return false;}
				}
			}
			return true;
		},
		getElementsToCheckValidation : function(formId, elementArray, selectOption) {
			var objForm = $.nony.getElement(formId);
			var arrElem = (elementArray == null) ? new Array() : elementArray;

			for (var i=0; i<$(objForm)[0].elements.length; i++) {
				var element = $(objForm)[0].elements[i];
				var type = $(element).attr("type");
				var checkName = $(element).attr("checkName"), maxlength = $(element).attr("maxlength"), minlength = $(element).attr("minlength"), match = $(element).attr("match");
				var mandatory = $(element).attr("mandatory"), option = $(element).attr("option"), checkFlag = $(element).attr("checkFlag");

				if (!$.nony.isEmpty(type)) {
					if (type == "text" || type == "textarea" || type == "select" || type == "select-one") {
						if (!$.nony.isEmpty(checkName) || !$.nony.isEmpty(maxlength) || !$.nony.isEmpty(minlength) || !$.nony.isEmpty(match) || !$.nony.isEmpty(mandatory) || !$.nony.isEmpty(option)) {
							if (selectOption == "optional") {
								if (checkFlag == "optional") {
									if (!$.nony.isEmpty($(element).attr("name"))) {arrElem.push($(element).attr("name"));}
									else {
										if (!$.nony.isEmpty($(element).attr("id"))) {arrElem.push($(element).attr("id"));}
									}
								}
							} else if (selectOption == "essential") {
								if (checkFlag == "essential") {
									if (!$.nony.isEmpty($(element).attr("name"))) {arrElem.push($(element).attr("name"));}
									else {
										if (!$.nony.isEmpty($(element).attr("id"))) {arrElem.push($(element).attr("id"));}
									}
								}
							} else {
								if (!$.nony.isEmpty($(element).attr("name"))) {arrElem.push($(element).attr("name"));}
								else {
									if (!$.nony.isEmpty($(element).attr("id"))) {arrElem.push($(element).attr("id"));}
								}
							}
						}
					}
				}
			}
			return arrElem;
		},
		doErrorMessage : function(msgType) {
			return $.nony.validator._execErrorMessage($.nony.validator._messages[msgType+"_"+jsconfig.get("langCode")]);
		},
		doValidatorMessage : function(jQueryElement, msgType) {
			return $.nony.validator._execError(jQueryElement, $.nony.validator._messages[msgType+"_"+jsconfig.get("langCode")], "select");
		},
		_checkKor : function(str, tail) {
			return (this._hasFinalConsonant(str)) ? tail.substring(0, 1) : tail.substring(1, 2);
		},
		_trim : function(src) {
			if ($.nony.isEmpty) {return src;}
			else {return src.replace(/\s/g, "");}
		},
		_ltrim : function(src) {
			if ($.nony.isEmpty) {return src;}
			else {return src.replace(/^\s+/, "");}
		},
		_rtrim : function(src) {
			if ($.nony.isEmpty) {return src;}
			else {return src.replace(/\s+$/, "");}
		},
		_hasFinalConsonant : function(src) {
			if ($.nony.isEmpty(src)) {
				return "";
			}
			var temp = src.substr(src.length - 1);
			return ((temp.charCodeAt(0) - 16) % 28 != 0);
		},
		_bytes : function(src) {
			var len = 0;
			for (var i=0; i<src.length; i++) {
				var chr = src.charAt(i);
				len += (chr.charCodeAt() > 128) ? 2 : 1;
			}
			return len;
		},
		_execError : function(element, msg, action) {
			var pattern = /{([a-zA-Z0-9_]+)\+?([가-힣]{2})?}/;
			var name = "";
			var params = {};

			if (!$.nony.isEmpty($(element).attr("checkName"))) {name = $(element).attr("checkName");}
			else {name = $(element).attr("name");}

			pattern.exec(msg);
			var tail = (RegExp.$2) ? this._checkKor(eval(RegExp.$1), RegExp.$2) : "";

			params.type = "Warning";
			params.width = 330;
//			params.height = 200;
			params.contents = msg.replace(pattern, eval(RegExp.$1) + tail);
			$.nony.popup.openDialog(params);

			if (action == "delete") {$(element).val("");}
			else if ($(element).attr("type") == "text" || $(element).attr("type") == "textarea") {
				try {$(element).select();}
				catch(e) {}
			}

			try {$(element).focus();}
			catch(e) {}

			$(element).addClass("error");

			return false;
		},
		_execErrorMessage : function(msg) {
			var pattern = /{([a-zA-Z0-9_]+)\+?([가-힣]{2})?}/;
			var name = "";
			var params = {};

			pattern.exec(msg);
			var tail = (RegExp.$2) ? this._checkKor(eval(RegExp.$1), RegExp.$2) : "";

			params.type = "Warning";
			params.width = 330;
			params.contents = msg.replace(pattern, eval(RegExp.$1) + tail);
			$.nony.popup.openDialog(params);

			return false;
		},
		isValidEmail : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[_a-zA-Z0-9-\.]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["email_"+jsconfig.get("langCode")], "select");
		},
		isValidPhoneNumber : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[0-9]{2,3}[- ]?[0-9]{3,4}[- ]?[0-9]{3,4}$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["phoneNumber_"+jsconfig.get("langCode")], "select");
		},
		isValidMobileNumber : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[0]{1}[0-9]{2,3}[- ]?[0-9]{3,4}[- ]?[0-9]{3,4}$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["mobileNumber_"+jsconfig.get("langCode")], "select");
		},
		isValidUserId : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[a-zA-Z0-9]{4,16}$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["userId_"+jsconfig.get("langCode")], "select");
		},
		isValidPassword : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[a-zA-Z0-9]{4,16}$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["password_"+jsconfig.get("langCode")], "select");
		},
		isValidIncKor : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /[가-힣]/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["incKor_"+jsconfig.get("langCode")], "select");
		},
		isValidEngOnly : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[a-zA-Z]+$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["engOnly_"+jsconfig.get("langCode")], "select");
		},
		isValidNumeric : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^[-+0-9,.]+$/;

			return (pattern.test(value)) ? true : this._execError($(element), this._messages["numeric_"+jsconfig.get("langCode")], "select");
		},
		isValidResNumber : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /^([0-9]{6})-?([0-9]{7})$/;
			var digit = 0;
			var digset = "234567892345";

			if (!pattern.test(value)) {return this._execError($(element), this._messages["resNumber_"+jsconfig.get("langCode")], "select");}

			value = RegExp.$1 + RegExp.$2;

			for (var i=0; i<12; i++) {
				digit += parseInt(value.charAt(i), 10) * parseInt(digset.charAt(i), 10);
			}

			digit = digit % 11;
			digit = 11 - digit;
			digit = digit % 10;

			if (digit != parseInt(value.charAt(12), 10)) {
				return this._execError($(element), this._messages["resNumber_"+jsconfig.get("langCode")], "select");
			}

			return true;
		},
		isValidBizNumber : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/;
			var chkRule = "137137135";
			var corpNum = "";
			var step1 = step2 = step3 = step4 = step5 = step6 = step7 = 0;

			if (!pattern.test(value)) {return this._execError($(element), this._messages["bizNumber_"+jsconfig.get("langCode")], "select");}

			value = RegExp.$1 + RegExp.$2 + RegExp.$3;
			corpNum = value;

			for (var i=0; i<7; i++) {
				step1 = step1 + (corpNum.substring(i, i+1) * chkRule.substring(i, i+1));
			}

			step2 = step1 % 10;
			step3 = (corpNum.substring(7, 8) * chkRule.substring(7, 8)) % 10;
			step4 = corpNum.substring(8, 9) * chkRule.substring(8, 9);
			step5 = Math.round(step4 / 10 - 0.5);
			step6 = step4 - (step5 * 10);
			step7 = (10 - ((step2 + step3 + step5 + step6) % 10)) % 10;

			if (corpNum.substring(9, 10) != step7) {
				return this._execError($(element), this._messages["bizNumber_"+jsconfig.get("langCode")], "select");
			}

			return;
		},
		isValidDomain : function(element, val) {
			var value = val ? val : $(element).val();

			if (value.indexOf("http://") != -1) {return this._execError($(element), this._messages["domain_"+jsconfig.get("langCode")], "select");}

			var pattern = new RegExp("^(http://)?([가-힣a-zA-Z0-9-\.]+\.[a-zA-Z]{2,3}$)", "i");

			if (pattern.test(value)) {
				$(element).val(RegExp.$2);
				return true;
			} else {
				return this._execError($(element), this._messages["domain_"+jsconfig.get("langCode")], "select");
			}
		},
		isValidUploadFile : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /[\w\W]+\.asp|aspx|py|perl|lasso|cfm|cgi|afp|asa|php|phps|php3|php4|js|jsp|jsf|jspx|exe|bat|pl$/i;

			if (pattern.test(value)) {return this._execError($(element), this._messages["notUploadable_"+jsconfig.get("langCode")], "select");}
			else {return true;}
		},
		isValidSpecialCharAll : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /[$]|[%]|[&]|[`]|[=]|[?]|[\]|[_]|[|]|[\']|[\"]/;

			if (pattern.test(value)) {return this._execError($(element), this._messages["specialCharAll_"+jsconfig.get("langCode")], "delete");}
			else {return true;}
		},
		isValidSpecialCharSome : function(element, val) {
			var value = val ? val : $(element).val();
			var pattern = /[`]|[\']|[\"]/;

			if (pattern.test(value)) {return this._execError($(element), this._messages["specialCharSome_"+jsconfig.get("langCode")], "delete");}
			else {return true;}
		},
		isValidDate : function(element, val) {
			var value = val ? val : $(element).val();

			if (!moment(value, jsconfig.get("dateFormatJs")).isValid()) {
				return this._execError($(element), this._messages["date_"+jsconfig.get("langCode")], "select");
			} else {
				return true;
			}
		},
		_functions : {
			email:function(element, value) {return $.nony.validator.isValidEmail(element, value);},
			phoneNumber:function(element, value) {return $.nony.validator.isValidPhoneNumber(element, value);},
			mobileNumber:function(element, value) {return $.nony.validator.isValidMobileNumber(element, value);},
			userId:function(element, value) {return $.nony.validator.isValidUserId(element, value);},
			password:function(element, value) {return $.nony.validator.isValidPassword(element, value);},
			incKor:function(element, value) {return $.nony.validator.isValidIncKor(element, value);},
			engOnly:function(element, value) {return $.nony.validator.isValidEngOnly(element, value);},
			numeric:function(element, value) {return $.nony.validator.isValidNumeric(element, value);},
			resNumber:function(element, value) {return $.nony.validator.isValidResNumber(element, value);},
			bizNumber:function(element, value) {return $.nony.validator.isValidBizNumber(element, value);},
			domain:function(element, value) {return $.nony.validator.isValidDomain(element, value);},
			uploadFile:function(element, value) {return $.nony.validator.isValidUploadFile(element, value);},
			specialCharAll:function(element, value) {return $.nony.validator.isValidSpecialCharAll(element, value);},
			specialCharSome:function(element, value) {return $.nony.validator.isValidSpecialCharSome(element, value);},
			date:function(element, value) {return $.nony.validator.isValidDate(element, value);}
		},
		_messages : {
			mandatory_en:"{name} is a mandatory field.",
			mandatory_ko:"{name+은는} 필수입력 항목입니다.",

			minSelect_en:"Please select at least one item.",
			minSelect_ko:"적어도 한 가지 항목은 선택하여야 합니다.",

			minlength_en:"{name} needs to be filled with minimum {minlength} characters.",
			minlength_ko:"{name+은는} 최소 {minlength}자 이상 입력해야 합니다.",
			minlengthIncKor_en:"{name} needs to be included minimum {minlength} characters of Korean character.",
			minlengthIncKor_ko:"{name+은는} 최소 한글 {minlength}자 이상 입력해야 합니다.",
			minlengthEngOnly_en:"{name} needs to be included minimum {minlength} characters of English alphabet.",
			minlengthEngOnly_ko:"{name+은는} 최소 영문 {minlength}자 이상 입력해야 합니다.",

			maxlength_en:"{name} needs to be filled with maximum {maxlength} characters.",
			maxlength_ko:"{name+은는} 최대 {maxlength}자 이하로 입력해야 합니다.",
			maxlengthIncKor_en:"{name} needs to be included maximum {maxlength} characters of Korean character.",
			maxlengthIncKor_ko:"{name+은는} 최대 한글 {maxlength}자 이하로 입력해야 합니다.",
			maxlengthEngOnly_en:"The {name} needs to be included maximum {maxlength} characters of English alphabet.",
			maxlengthEngOnly_ko:"{name+은는} 최대 영문 {maxlength}자 이하로 입력해야 합니다.",

			match_en:"{name} is not matched.",
			match_ko:"{name+이가} 일치하지 않습니다.",

			notValid_en:"{name} is not valid.",
			notValid_ko:"{name+이가} 올바르지 않습니다.",

			incKor_en:"{name} must include Korean.",
			incKor_ko:"{name+은는} 한글이 포함되어야 합니다.",

			email_en:"{name} is not a valid Email address.",
			email_ko:"{name+은는} 유효한 이메일 주소가 아닙니다.",

			phoneNumber_en:"{name} is not a valid Telephone number.",
			phoneNumber_ko:"{name+은는} 유효한 전화번호가 아닙니다.",

			mobileNumber_en:"{name} is not a valid Mobile Phone number.",
			mobileNumber_ko:"{name+은는} 유효한 휴대 전화번호가 아닙니다.",

			userId_en:"{name} is not a valid User ID.",
			userId_ko:"{name+은는} 유효한 사용자 ID가 아닙니다.",

			password_en:"{name} is not a valid Password.",
			password_ko:"{name+은는} 유효한 비밀번호가 아닙니다.",

			engOnly_en:"{name} needs to be felled with English alphabet only.",
			engOnly_ko:"{name+은는} 반드시 영문으로만 입력해야 합니다.",

			numeric_en:"{name} needs to be filled with numeric.",
			numeric_ko:"{name+은는} 반드시 숫자로만 입력해야 합니다.",

			resNumber_en:"{name} is not a valid Residence number.",
			resNumber_ko:"{name+은는} 유효한 주민번호가 아닙니다.",

			bizNumber_en:"{name} is not a valid Business license number.",
			bizNumber_ko:"{name+은는} 유효한 사업자등록번호가 아닙니다.",

			domain_en:"{name} is not a valid Domain name.",
			domain_ko:"{name+은는} 유효한 도메인명이 아닙니다.",

			notUploadable_en:"File is not uploadable.",
			notUploadable_ko:"업로드 가능한 파일이 아닙니다.",

			specialCharAll_en:"Special characters($ % & ` = ? \\ _ | \' \") are not acceptable.",
			specialCharAll_ko:"특수문자($ % & ` = ? \\ _ | \' \")는 사용하실수 없습니다.",

			specialCharSome_en:"Special characters(` \' \") are not acceptable.",
			specialCharSome_ko:"특수문자(` \' \")는 사용하실수 없습니다.",

			date_en:"{name} is not a valid Date.",
			date_ko:"{name+은는} 유효한 날짜값이 아닙니다."
		}
	};
})(jQuery);