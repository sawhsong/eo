/**
 * file elements JavaScript
 */
(function($) {
	$.nony.fileElement = {
		modifyFileSelectObject : function() {
			var fileElements = new Array();

			if (arguments.length == 0) {
				fileElements = $("input[type=file]");
			} else {
				for (var i=0; i<arguments.length; i++) {
					fileElements.push(arguments[i]);
				}
			}

			for (var i=0; i<fileElements.length; i++) {
				if ("Y" == jsconfig.get("customFileObject") || arguments.length != 0) {
					var parent = $(fileElements[i]).parent();
					var sibling = $(fileElements[i]).prev();
					var id = $(fileElements[i]).attr("id");
					var name = $(fileElements[i]).attr("name");
					var width = 0, widthComp = 0;
					var params = {};
					var themeId = jsconfig.get("themeId");

					/*!
					 * Adjust width here
					 */
					if ($.nony.browser.Chrome) {
						widthComp = 42;
					} else if ($.nony.browser.FireFox) {
						widthComp = 42;
					} else {
						widthComp = 42;
					}

					if (themeId == "theme009") {
						widthComp = 43;
					}
					// input type=file element's width must be set through style - not size attribute
					width = ($(fileElements[i]).outerWidth() - widthComp);
					params.widthComp = widthComp;
					params.width = width;

					var value = $(fileElements[i]).attr("value");
					var divFileElementHolder = $("<div id='divFileElementHolder"+id+"' class='divFileElementHolder'></div>");
					var divStyledFileHolder = $("<div id='divStyledFileHolder"+id+"' class='divStyledFileHolder'></div>");
					var txtStyledInput = $("<input type='text' id='txtStyledInput"+id+"' name='txtStyledInput"+name+"' class='txtEnForFileElement' style='float:left;width:"+width+"px' value='"+value+"' readonly='readonly'/>");
					var btnStyledButton = $("<a type='button' id='btnStyledButton"+id+"' class='btn btn-default' style='float:left;margin-left:1px'><i class='fa fa-folder-open fa-lg'></i></a>");

					$(fileElements[i]).attr("class", "fileCustomed");

					if ($(sibling).length > 0) {
						if ($(sibling).get(0).tagName == "INPUT" || $(sibling).get(0).tagName == "input") {
							$(sibling).css("float", "left");
						}
					}

					$(txtStyledInput).appendTo(divStyledFileHolder);
					$(btnStyledButton).appendTo(divStyledFileHolder);
					$(divStyledFileHolder).appendTo(divFileElementHolder);
					$(fileElements[i]).appendTo(divFileElementHolder);

					if ($(sibling).length > 0) {
						$(divFileElementHolder).insertAfter(sibling);
					} else {
						$(divFileElementHolder).appendTo($(parent));
					}

					$(fileElements[i]).bind("change", params, function() {
						// Firfox - when selecting a file width also changed
						if ($.nony.browser.FireFox) {
							$(this).css("width", (params.width + params.widthComp - 0)+"px");
						}
						$("#txtStyledInput"+$(this).attr("id")).val($(this).val());
					});

					$(fileElements[i]).bind("mouseover", function() {
						$("#txtStyledInput"+$(this).attr("id")).attr("class", "txtEnForFileElementHover");
						$("#btnStyledButton"+$(this).attr("id")).attr("class", "btn btn-default-hover");
					});

					$(fileElements[i]).bind("mouseout", function() {
						$("#txtStyledInput"+$(this).attr("id")).attr("class", "txtEnForFileElement");
						$("#btnStyledButton"+$(this).attr("id")).attr("class", "btn btn-default");
					});

					$(fileElements[i]).bind("mousedown", function() {
						$("#btnStyledButton"+$(this).attr("id")).attr("class", "btn btn-default-active");
					});
				}
			}
		},
		addFileSelectObject : function(params) {
			if ($.nony.isEmpty(params.appendToId)) {
				throw new Error("Append to object ID" + com.message.invalid);
				return;
			}

			if ($("#"+params.appendToId).length <= 0) {
				throw new Error("Append to object" + com.message.invalid);
				return;
			}

			// options
			params.appendToId = params.appendToId;														// Target id - Mandatory
			params.elementId = $.nony.nvl(params.elementId)+"_"+$.nony.getUid() || $.nony.getUid();		// File element id and name - optional[uid(system generated)]
			params.rowBreak = (params.rowBreak == true) ? true : false;									// Row creation type - optional[false] - true:new line, false:same line
			params.deleteButton = (params.deleteButton == false) ? false : true;						// Delete button - optional[true]
			params.size = (params.size) ? params.size : 250;											// File element size - optional[20] (does not work in firefox - default:20)

			var idAndName = "file_"+params.elementId;
			var styleWidth = "";
			var $target, $divRow, $file, $btnDel;

			$target = $("#"+params.appendToId);
			$file = $("<input type='file' id='"+idAndName+"' name='"+idAndName+"' class='file' style='width:"+params.size+"px' value=''/>");

			if (params.rowBreak) {styleWidth = "width:100%;";}
			$divRow = $("<div id='div"+idAndName+"' style='padding:0px 4px 2px 0px;display:inline-block;"+styleWidth+"'></div>");

			$file.appendTo($divRow);

			if (params.deleteButton) {
				$btnDel = $("<a type='button' id='btnDel"+idAndName+"' class='btn btn-default' style='margin-top:1px;margin-left:1px' title='Delete file element'><i class='fa fa-times fa-lg'></i></a>");
				$btnDel.appendTo($divRow);

				$btnDel.bind("click", function() {
					$.nony.fileElement.removeFileSelectObject(params.appendToId, idAndName);
				});
			}

			$divRow.appendTo($target);

			$.nony.fileElement.modifyFileSelectObject($file);
		},
		removeFileSelectObject : function(parentId, objId) {
			$("#"+parentId).find("*").each(function(index) {
				var id = $(this).prop("id");
				if (id.indexOf(objId) != -1) {
					$(this).remove();
				}
			});
		}
	};
})(jQuery);