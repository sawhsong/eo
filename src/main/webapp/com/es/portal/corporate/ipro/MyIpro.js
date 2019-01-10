/**
 * 
 */
var popup = null;

$(function() {
	/*!
	 * event
	 */
	$("#btnSearch").click(function() {
		doSearch();
	});
	/*!
	 * process
	 */
	setGridTable = function() {
		$("#tblGrid").fixedHeaderTable({
			attachTo:$("#divDataArea")
		});
	};

	doSearch = function() {
		commonJs.showProcMessageOnElement("divScrollablePanel");

		setTimeout(function() {
			commonJs.ajaxSubmit({
				url:"/corporate/ipro/getIproList",
				dataType:"json",
				data:{},
				success:function(data, textStatus) {
					var result = commonJs.parseAjaxResult(data, textStatus, "json");
					if (result.isSuccess == true || result.isSuccess == "true") {
						renderGridTable(result);
					} else {
						commonJs.error(result.message);
					}
				}
			});
		}, 200);
	};

	renderGridTable = function(result) {
		var ds = result.dataSet, html = "";

		$("#tblGridBody").html("");

		if (ds.getRowCnt() > 0) {
			for (var i=0; i<ds.getRowCnt(); i++) {
				var gridTr = new UiGridTr();

				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "personFullName")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "jobTitle")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "email")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "lastInvoiceDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "lastPayDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "startDate")));
				gridTr.addChild(new UiGridTd().addClassName("Ct").setText(ds.getValue(i, "endDate")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "endUserOrganisationName")));
				gridTr.addChild(new UiGridTd().addClassName("Lt").setText(ds.getValue(i, "workingState")));

				html += gridTr.toHtmlString();
			}
		} else {
			var gridTr = new UiGridTr();

			gridTr.addChild(new UiGridTd().addClassName("Ct").setAttribute("colspan:9").setText(com.message.I001));
			html += gridTr.toHtmlString();
		}

		$("#tblGridBody").append($(html));
		setGridTable();

		commonJs.hideProcMessageOnElement("divScrollablePanel");
	};

	/*!
	 * load event (document / window)
	 */
	$(window).load(function() {
		doSearch();
	});
});