/**
 * ajax JavaScript
 */
(function($) {
	$.nony.ajax = {
		ajax : function(params) {
			$.ajax(params);
		},
		getFormDataFromSerializedArray : function(formSerializedArray) {
			var paramData = {};
			$.each(formSerializedArray, function(i, param) {
				paramData[param.name] = param.value;
			});
			return paramData;
		},
		/*!
		 * Form Submit - form enctype:multipart/form-data -> does not support
		 */
		ajaxSubmit : function(params) {
			params.url = params.url;
			params.async = (params.async == true) ? true : false;
			params.dataType = params.dataType || "json";
			params.type = params.type || "POST";
			params.formId = params.formId;
			params.data = params.data || {};
			params.blind = (params.blind == true) ? true : false;
			params.blindMessage = params.blindMessage || com.message.loading;
			var paramData = "";

			if (params.blind) {
				if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
					$.nony.showProcMessage(com.message.loading);
				}
			}

			if ($.nony.isEmpty(params.url)) {
				throw new Error("URL" + com.message.required);
				return;
			}

			if (!$.nony.isEmpty(params.formId)) {
				if (typeof(params.formId) == "object") {
					throw new Error("Form Id" + com.message.invalid);
					return;
				}
			}

			if (!$.nony.isEmpty(params.formId)) {
				var arrTemp = $("#"+params.formId).serializeArray();
				$.each(arrTemp, function(i, data) {
					paramData += ($.nony.isEmpty(paramData)) ? data.name+"="+data.value : "&"+data.name+"="+data.value;
				});
			}

			if (params.data != null) {
				for (var keys in params.data) {
					var items = [];
					items[keys] = params.data[keys];

					paramData += ($.nony.isEmpty(paramData)) ? keys+"="+items[keys] : "&"+keys+"="+items[keys];
				}
			}

			$(document).ajaxStart(function(event, xhr, options) {
			});

			$(document).ajaxStop(function() {
			});

			params.headers = {"ajaxDataTypeForFramework":params.dataType};
			params.data = paramData;
			params.error = function(xhr, textStatus, errorThrown) {
				var data, contentsString = "";

				console.log("request.status : "+xhr.status);
				console.log("request.responseText : "+xhr.responseText);
				console.log("textStatus : "+textStatus);
				console.log("errorThrown : "+errorThrown);

				try {
					if (xhr.responseText == "SessionTimedOut" || xhr.responseXML == "SessionTimedOut") {
						commonJs.openDialog({
							contents:com.message.sessionTimeOut,
							buttons:[{
								caption:com.caption.ok,
								callback:function() {
									location.replace("/index/index.do");
								}
							}],
							width:330,
							blind:true
						});

						return;
					}
				} catch(e) {}

				if (params.dataType == "xml") {
					data = xhr.responseXML;
				} else if (params.dataType == "json") {
					data = xhr.responseText;
				} else {
					data = xhr.responseText;
				}

//				contentsString += textStatus + "</br>";
//				contentsString += errorThrown + "</br>";
//				contentsString += data.message + "</br>";

				$.nony.openDialog({
					type:"Error",
					contents:data,
					width:"400",
					height:"400",
					buttons:[{
						caption:com.caption.ok,
						callback:function() {
						}
					}]
				});
			};

			params.complete = function(xhr, textStatus) {
				if (params.blind) {
					if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
						$.nony.hideProcMessage();
					}
				}
			};

			$.nony.ajax.ajax(params);
		},
		/*!
		 * Utilities
		 */
		parseAjaxResult : function(data, textStatus, dataType) {
			var result = {};
			var ds = new DataSet();

			if (textStatus.toLowerCase() == "success" && data != null) {
				if (dataType.toLowerCase() == "xml") {
					var tagElem;

					tagElem = data.getElementsByTagName("isSuccess");
					if (tagElem[0].childNodes[0] == null || tagElem[0].childNodes[0] == "") {
						result.isSuccess = false;
					} else {
						result.isSuccess = tagElem[0].childNodes[0].textContent;
					}

					tagElem = data.getElementsByTagName("messageCode");
					if (tagElem[0].childNodes[0] == null || tagElem[0].childNodes[0] == "") {
						result.messageCode = "";
					} else {
						result.messageCode = tagElem[0].childNodes[0].textContent;
					}

					tagElem = data.getElementsByTagName("message");
					if (tagElem[0].childNodes[0] == null || tagElem[0].childNodes[0] == "") {
						result.message = "";
					} else {
						result.message = tagElem[0].childNodes[0].textContent;
					}

					tagElem = data.getElementsByTagName("totalResultRows");
					if (tagElem[0].childNodes[0] == null || tagElem[0].childNodes[0] == "") {
						result.totalResultRows = "";
					} else {
						result.totalResultRows = tagElem[0].childNodes[0].textContent;
					}

					tagElem = data.getElementsByTagName("dataSet");
					if (tagElem == null || tagElem.length != 1) {return result;}

					tagElem = data.getElementsByTagName("dataSetHeader");
					if (tagElem == null || tagElem.length != 1) {
						result.dataSet = ds;
						return result;
					}

					for (var i=0; i<tagElem[0].childNodes.length; i++) {
						var col = tagElem[0].childNodes[i];
						if (col.nodeType == 1) {ds.addName(col.getAttribute("name"));}
					}

					tagElem = data.getElementsByTagName("dataSetRow");
					if (tagElem == null || tagElem.length != 1) {
						result.dataSet = ds;
						return result;
					}

					for (var i=0; i<tagElem[0].childNodes.length; i++) {
						var row = tagElem[0].childNodes[i];
						if (row.nodeType == 1) {
							ds.addRow();
							for (var j=0; j<row.childNodes.length; j++) {
								var col = row.childNodes[j];
								if (col.nodeType == 1) {ds.setValue(ds.getRowCnt()-1, col.getAttribute("name"), col.getAttribute("value"));}
							}
						}
					}

					result.dataSet = ds;
				} else if (dataType.toLowerCase() == "html") {
					var html, dsTr, dsTd;

					html = $(data).find("#tblAjaxResponseHTML_info");
					if ($.nony.isEmpty(html)) {
						result.isSuccess = false;
						result.messageCode = "";
						result.message = "";
						result.totalResultRows = "";
						return result;
					} else {
						result.isSuccess = $(data).find("#isSuccess").html();
						result.messageCode = $(data).find("#messageCode").html();
						result.message = $(data).find("#message").html();
						result.totalResultRows = $(data).find("#totalResultRows").html();
					}

					html = $(data).find("#tblAjaxResponseHTML_dataSet");
					if ($.nony.isEmpty(html)) {
						return result;
					}

					$(html).find("th").each(function(index) {
						ds.addName($(this).html());
					});

					dsTr = $(html).find("tbody").find("tr");
					for (var i=0; i<dsTr.length; i++) {
						dsTd = $(dsTr[i]).find("td");
						ds.addRow();
						for (var j=0; j<dsTd.length; j++) {
							ds.setValue(ds.getRowCnt()-1, j, $(dsTd[j]).html());
						}
					}

					result.dataSet = ds;
				} else if (dataType.toLowerCase() == "json") {
					result.isSuccess = data.isSuccess;
					result.messageCode = data.messageCode;
					result.message = data.message;
					result.totalResultRows = data.totalResultRows;

					if (data.dataSet != null) {
						var dataSet = data.dataSet;

						for (var i=0; i<dataSet.dataSetHeader.length; i++) {
							ds.addName(dataSet.dataSetHeader[i]);
						}

						for (var key in dataSet.dataSetValue) {
							var items = [];

							ds.addRow();
							items[key] = dataSet.dataSetValue[key];
							for (var i=0; i<items[key].length; i++) {
								ds.setValue(ds.getRowCnt()-1, i, items[key][i]);
							}
						}
					}

					result.dataSet = ds;
				} else {
					var dataDelimiter = jsconfig.get("dataDelimiter");
					var arr;

					if (data.indexOf("\n\n") == -1) {
						arr = data.split("\n");
						arr = arr[1].split(dataDelimiter);

						if (arr != null) {
							result.isSuccess = arr[0];
							result.messageCode = arr[1];
							result.message = arr[2];
							result.totalResultRows = arr[3];
						} else {
							result.isSuccess = false;
							result.messageCode = "";
							result.message = "";
							result.totalResultRows = "";
						}

						result.dataSet = ds;
					} else {
						arr = data.split("\n\n");
						arr = arr[0].split("\n");
						arr = arr[1].split(dataDelimiter);

						if (arr != null) {
							result.isSuccess = arr[0];
							result.messageCode = arr[1];
							result.message = arr[2];
							result.totalResultRows = arr[3];
						} else {
							result.isSuccess = false;
							result.messageCode = "";
							result.message = "";
							result.totalResultRows = "";
						}

						arr = data.split("\n\n");
						arr = arr[1].split("\n");
						ds.addName(arr[0].split(dataDelimiter));
						for (var i=1; i<arr.length; i++) {
							ds.addRow(arr[i].split(dataDelimiter));
						}

						result.dataSet = ds;
					}
				}
			}

			return result;
		}
	};
})(jQuery);