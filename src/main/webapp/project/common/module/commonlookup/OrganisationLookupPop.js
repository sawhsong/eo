/**
 * OrganisationLookupPop.js
 */
var popupObject = eval(popupName);

$(function() {
	/*!
	 * event
	 */
	/*!
	 * process
	 */
	doSearch = function() {
		commonJs.showProcMessageOnElement("tblGrid");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/common/lookup/getOrganisationLookup.do",
				dataType:"json",
				formId:"fmDefault",
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");

					if (result.isSuccess == true || result.isSuccess == "true") {
						renderDataGridTable(result);
					}
				}
			});
		}, 200);
	};

	renderDataGridTable = function(result) {
		var ds = result.dataSet;
		var html = "";

		searchResultDataCount = ds.getRowCnt();
		$("#tblGridBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				html += "<tr>";
				html += "<td class=\"tdGridCt\">"+ds.getValue(i, "ORG_ID")+"</td>";
				html += "<td class=\"tdGrid\"><a onclick=\"setValue('"+ds.getValue(i, "ORG_ID")+"', '"+ds.getValue(i, "LEGAL_NAME")+"')\" class=\"aEn\">"+commonJs.abbreviate(ds.getValue(i, "LEGAL_NAME"), 60)+"</a></td>";
				html += "<td class=\"tdGridCt\">"+commonJs.getFormatString(ds.getValue(i, "ABN"), "?? ??? ??? ???")+"</td>";
				html += "<td class=\"tdGrid\">"+commonJs.abbreviate(ds.getValue(i, "POSTAL_ADDRESS"), 60)+"</td>";
				html += "</tr>";
			}
		} else {
			html += "<tr>";
			html += "<td class=\"tdGridCt\" colspan=\"4\">com.message.I001</td>";
			html += "</tr>";
		}

		$("#tblGridBody").append($(html));
		if (commonJs.browser.FireFox) {
			gridWidthAdjust = -34;
		} else {
			gridWidthAdjust = -38;
		}

		$("#tblGrid").fixedHeaderTable({
			baseDivElement:"divScrollablePanelPopup",
			attachedPagingArea:true,
			blockElementId:"tblGrid",
			pagingAreaId:"divPagingArea",
			totalResultRows:result.totalResultRows,
			script:"doSearch",
			widthAdjust:gridWidthAdjust
		});

		commonJs.hideProcMessageOnElement("tblGrid");
	};

	setValue = function(id, name) {
		var targetDocument, keyField, valueField;

		if (popupToSetValue != null) {
			targetDocument = $(popupToSetValue.popupIframe).contents();
			keyField = $(targetDocument).find("#"+keyFieldId);
			valueField = $(targetDocument).find("#"+valueFieldId);

			$(keyField).val(id);
			$(valueField).val(name);
		} else {
		}

		popupObject.close();
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		$("#orgName").val(lookupValue);
		$("#orgName").focus();

		commonJs.setAutoComplete($("#orgName"), {
			method:"getOrgName",
			label:"org_name",
			value:"org_name",
			focus: function(event, ui) {
				$("#orgName").val(ui.item.label);
				return false;
			},
			select:function(event, ui) {
				doSearch();
			}
		});

		commonJs.setAutoComplete($("#abn"), {
			method:"getAbn",
			label:"abn",
			value:"abn",
			focus: function(event, ui) {
				$("#abn").val(ui.item.label);
				return false;
			},
			select:function(event, ui) {
				doSearch();
			}
		});

		setTimeout(function() {
			doSearch();
		}, 400);
	});
});