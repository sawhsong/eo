/**
 * popup JavaScript
 */
(function($) {
	$.nony.popup = {
		openDialog : function(params) {
			if ($("#"+params.popupId).length > 0) {return;}

			params.popupId = params.popupId || "popupDialog_"+$.nony.getTimeStamp();
			params.popupMethod = "popupDialog";
			params.type = params.type || com.message.I000;
			params.header = params.header || params.type;
			params.contents = params.contents || "";
			params.width = params.width;
			params.height = params.height;
			params.heightAdjust = params.heightAdjust || 0;
			params.left = params.left || "center";
			params.top = params.top || "center";
			params.effect = params.effect;
			params.draggable = (params.draggable == false) ? false : true;
			params.modal = (params.modal == false) ? false : true;
			params.blind = (params.blind == true) ? true : false;
			params.shadow = (params.shadow == false) ? false : true;
			params.url = params.url || jsconfig.get("shareRoot")+"/page/"+"blankForPopup.jsp";
			params.paramData = params.paramData || {};
			params.iframeName = params.iframeName || "popupDialogIframe_"+$.nony.getTimeStamp();

			if ($.nony.isEmpty(params.buttons)) {
				if (com.message.I000 == params.type) {
					params.buttons = [{caption:com.caption.ok, callback:function() {}}];
				} else if (com.message.Q000 == params.type || "Confirm" == params.type) {
					params.buttons = [{
						caption:com.caption.ok, callback:function() {}
					}, {
						caption:com.caption.cancel, callback:function() {}
					}];
				} else if ("Confirmation" == params.type) {
					params.buttons = [{caption:com.caption.ok, callback:function() {}}];
				} else if (com.message.W000 == params.type) {
					params.buttons = [{caption:com.caption.ok, callback:function() {}}];
				} else if (com.message.E000 == params.type) {
					params.buttons = [{caption:com.caption.ok, callback:function() {}}];
				}
			}

			this._setOptions(params);
			this._createObjects();
			this._setModal();
			this._setBlind();
			this._appendObjects();
			this._setShadow();
			this._setPosition();
			this._setButtons();
			this._bindEvent(params);
			this._setEffect();

			return {
				popupId : this.popupId,
				effect : this.effect,
				popupBase : this.popupBase,
				oncloseCallback : this.oncloseCallback,
				popupHeaderTitle : this.popupHeaderTitle,
				popupIframe : this.popupIframe,
				close : function() {
					var popupBase = this.popupBase;
					var oncloseCallback = this.oncloseCallback;
					var unblockOption;
					var closingFunction = function() {
						$(popupBase).remove();
						if (typeof oncloseCallback == "function") {unblockOption = {fadeOut:100, onUnblock:function() {oncloseCallback();}};}
						else {unblockOption = {fadeOut:100};}

						if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
							$.unblockUI(unblockOption);
						}
					};

					if ("slide" == this.effect) {
						$(this.popupBase).stop().slideUp(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else if ("fade" == this.effect) {
						$(this.popupBase).stop().fadeOut(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else {
						$(this.popupBase).stop().hide(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					}
				},
				resizeTo : function(width, height) {
					var baseWidth, baseHeight, winHolderWidth, winHolderHeight, bodyWidth, bodyHeight, frameWidth, frameHeight;

					baseWidth = $.nony.toNumber($.nony.replace(this.popupBase.css("width"), "px", ""));
					baseHeight = $.nony.toNumber($.nony.replace(this.popupBase.css("height"), "px", ""));
					baseLeft = $.nony.toNumber($.nony.replace(this.popupBase.css("left"), "px", ""));
					baseTop = $.nony.toNumber($.nony.replace(this.popupBase.css("top"), "px", ""));
					winHolderWidth = $.nony.toNumber($.nony.replace(this.popupWinHolder.css("width"), "px", ""));
					winHolderHeight = $.nony.toNumber($.nony.replace(this.popupWinHolder.css("height"), "px", ""));
					bodyWidth = $.nony.toNumber($.nony.replace(this.popupBody.css("width"), "px", ""));
					bodyHeight = $.nony.toNumber($.nony.replace(this.popupBody.css("height"), "px", ""));
					frameWidth = $.nony.toNumber($.nony.replace(this.popupIframe.css("width"), "px", ""));
					frameHeight = $.nony.toNumber($.nony.replace(this.popupIframe.css("height"), "px", ""));

					this.popupBase.animate({width:(baseWidth+width)+"px"});
					this.popupBase.animate({height:(baseHeight+height)+"px"});
					this.popupWinHolder.animate({width:(winHolderWidth+width)+"px"});
					this.popupWinHolder.animate({height:(winHolderHeight+height)+"px"});
					this.popupBody.animate({width:(bodyWidth+width)+"px"});
					this.popupBody.animate({height:(bodyHeight+height)+"px"});
					this.popupIframe.animate({width:(frameWidth+width)+"px"});
					this.popupIframe.animate({height:(frameHeight+height)+"px"});

					$(this.popupBase).animate({"left":(baseLeft-(width/2)) + "px", "top":((baseTop-(height/2))) + "px"}, jsconfig.get("effectDuration"), "swing");
				},
				setHeader : function(header) {
					$(this.popupHeaderTitle).html(header);
				},
				setContents : function(contentsString) {
					var html = "<div style=\"padding:5px 5px\">"+contentsString+"</div>";
					$($(this.popupIframe).contents().find("#divPopupWindowHolder")).html(html);
				},
				addContents : function(contentsString) {
					var html = $.nony.nvl($($(this.popupIframe).contents().find("#divPopupWindowHolder")).html());
					var contentsHtml = $.nony.isEmpty(html) ? "<div style=\"padding:5px 5px\">"+contentsString+"</div>" : html+"<div style=\"padding:5px 5px\">"+contentsString+"</div>";
					$($(this.popupIframe).contents().find("#divPopupWindowHolder")).html(contentsHtml);
				}
			};
		},
		openPopup : function(params) {
			if ($.nony.isEmpty(params) || $.nony.isEmpty(params.popupId)) {throw new Error("Popup Id" + com.message.required);}
			if ($("#"+params.popupId).length > 0) {return;}

			params.popupMethod = "popupWithIframe";
			params.header = params.header || "Popup";
			params.width = params.width || "500";
			params.height = params.height || "350";
			params.left = params.left || "center";
			params.top = params.top || "center";
			params.effect = params.effect || "fade";
			params.draggable = (params.draggable == false) ? false : true;
			params.modal = (params.modal == false) ? false : true;
			params.blind = (params.blind == false) ? false : true;
			params.shadow = (params.shadow == false) ? false : true;
			params.url = params.url || jsconfig.get("shareRoot")+"/page/"+"blankForPopup.jsp";
			params.paramData = params.paramData || {};
			params.iframeName = params.iframeName || "popupIframe_"+$.nony.getTimeStamp();

			this._setOptions(params);
			this._createObjects();
			this._setModal();
			this._setBlind();
			this._appendObjects();
			this._setShadow();
			this._setPosition();
			this._setIframeHeight();
			this._bindEvent(params);
			this._setEffect();

			return {
				popupId : this.popupId,
				effect : this.effect,
				popupBase : this.popupBase,
				popupWinHolder : this.popupWinHolder,
				popupBody : this.popupBody,
				oncloseCallback : this.oncloseCallback,
				popupHeaderTitle : this.popupHeaderTitle,
				popupIframe : this.popupIframe,
				close : function() {
					var popupBase = this.popupBase;
					var oncloseCallback = this.oncloseCallback;
					var unblockOption;
					var closingFunction = function() {
						$(popupBase).remove();
						if (typeof oncloseCallback == "function") {unblockOption = {fadeOut:100, onUnblock:function() {oncloseCallback();}};}
						else {unblockOption = {fadeOut:100};}

						if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
							$.unblockUI(unblockOption);
						}
					};

					if ("slide" == this.effect) {
						$(this.popupBase).stop().slideUp(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else if ("fade" == this.effect) {
						$(this.popupBase).stop().fadeOut(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else {
						$(this.popupBase).stop().hide(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					}
				},
				resizeTo : function(width, height) {
					var baseWidth, baseHeight, winHolderWidth, winHolderHeight, bodyWidth, bodyHeight, frameWidth, frameHeight;

					baseWidth = $.nony.toNumber($.nony.replace(this.popupBase.css("width"), "px", ""));
					baseHeight = $.nony.toNumber($.nony.replace(this.popupBase.css("height"), "px", ""));
					baseLeft = $.nony.toNumber($.nony.replace(this.popupBase.css("left"), "px", ""));
					baseTop = $.nony.toNumber($.nony.replace(this.popupBase.css("top"), "px", ""));
					winHolderWidth = $.nony.toNumber($.nony.replace(this.popupWinHolder.css("width"), "px", ""));
					winHolderHeight = $.nony.toNumber($.nony.replace(this.popupWinHolder.css("height"), "px", ""));
					bodyWidth = $.nony.toNumber($.nony.replace(this.popupBody.css("width"), "px", ""));
					bodyHeight = $.nony.toNumber($.nony.replace(this.popupBody.css("height"), "px", ""));
					frameWidth = $.nony.toNumber($.nony.replace(this.popupIframe.css("width"), "px", ""));
					frameHeight = $.nony.toNumber($.nony.replace(this.popupIframe.css("height"), "px", ""));

					this.popupBase.animate({width:(baseWidth+width)+"px"});
					this.popupBase.animate({height:(baseHeight+height)+"px"});
					this.popupWinHolder.animate({width:(winHolderWidth+width)+"px"});
					this.popupWinHolder.animate({height:(winHolderHeight+height)+"px"});
					this.popupBody.animate({width:(bodyWidth+width)+"px"});
					this.popupBody.animate({height:(bodyHeight+height)+"px"});
					this.popupIframe.animate({width:(frameWidth+width)+"px"});
					this.popupIframe.animate({height:(frameHeight+height)+"px"});

					$(this.popupBase).animate({"left":(baseLeft-(width/2)) + "px", "top":((baseTop-(height/2))) + "px"}, jsconfig.get("effectDuration"), "swing");
				},
				setHeader : function(header) {
					$(this.popupHeaderTitle).html(header);
				},
				setContents : function(contentsString) {
					var html = "<div style=\"padding:5px 5px\">"+contentsString+"</div>";
					$($(this.popupIframe).contents().find("#divPopupWindowHolder")).html(html);
				},
				addContents : function(contentsString) {
					var html = $.nony.nvl($($(this.popupIframe).contents().find("#divPopupWindowHolder")).html());
					var contentsHtml = $.nony.isEmpty(html) ? "<div style=\"padding:5px 5px\">"+contentsString+"</div>" : html+"<div style=\"padding:5px 5px\">"+contentsString+"</div>";
					$($(this.popupIframe).contents().find("#divPopupWindowHolder")).html(contentsHtml);
				}
			};
		},
		/**
		 * Options
		 * @param params
		 */
		_setOptions : function(params) {
			var paramDataString = "?";

			this.params = params;
			this.popupId = params.popupId;								// Required
			this.popupMethod = params.popupMethod;						// Required (popupWithIframe / popupDialog)
			this.type = params.type;									// Dialog type (confirmation / information / question / warning / error, [information])
			this.header = params.header;								// Header message ([Popup Id]) (Possible to set after opening)
			this.contents = params.contents;							// Popup contents (will be filled in the iFrame)
			this.width = params.width;									// Popup width (Popup:[350], Dialog:[250])
			this.height = params.height;								// Popup height (Popup:[200], Dialog:[150])
			this.limitHeightForMax = params.limitHeightForMax = 100;	// Size for height limited (for only Dialog. Not editable)
			this.minWidth = params.minWidth = 250;						// Minimum width of dialog (for only Dialog. Not editable)
			this.maxWidth = params.maxWidth = 800;						// Maximum width of dialog (for only Dialog. Not editable)
			this.minHeight = params.minHeight = 35;						// Minimum height of dialog (for only Dialog. Not editable)
			this.left = params.left;									// Left position ([center])
			this.top = params.top;										// Top position ([middle])
			this.effect = params.effect || "fade";						// Show effect (fade / slide, [fade])
			this.draggable = params.draggable;							// Draggable (true / false, [true])
			this.modal = params.modal;									// Modal (true / false, [true])
			this.blind = params.blind;									// Blind (true / false, [false])
			this.shadow = params.shadow;								// Popup shadow (true / false, [true])
			this.url = params.url;										// Page URL to open ([blankForPopup.jsp])
			this.paramData = params.paramData;							// Request parameters({})
			this.iframeName = params.iframeName;						// IFrame name ([System generates])
			this.onLoad = params.onLoad;								// onLoad callback function
			this.oncloseCallback = params.oncloseCallback;				// onClose callback function
			this.buttons = params.buttons;								// Buttons to add (Array(caption, callback), only for Dialog)

			for (var keys in this.paramData) {
				var items = [];
				items[keys] = this.paramData[keys];
				paramDataString += (paramDataString == "?") ? keys+"="+items[keys] : "&"+keys+"="+items[keys];
			}

			this.url += paramDataString;
		},
		_createObjects : function() {
			var correctionValueForHeight = 0;

			if (this.popupMethod == "popupWithIframe") {
				this.popupBase = $("<div id='"+this.popupId+"' class='nonyPopWinBase' style='display:none'></div>").css("width", this.width).css("height", this.height);
				this.popupWinHolder = $("<div id='divNonyPopupWindowHolder' class='nonyPopWinWindowHolder'></div>");
				this.popupHeaderHolder = $("<div id='divNonyPopupHeaderHolder' class='nonyPopWinHeaderHolder'></div>");
				this.popupHeaderTitle = $("<div id='divNonyPopupHeaderTitle' class='nonyPopWinHeaderTitle'></div>").html(this.header);
				this.popupHeaderCloseButton = $("<div id='divNonyPopupHeaderCloseButton' class='nonyPopWinHeaderCloseButton'><i class='fa fa-times fa-lg icnEn'></i></div>");
				this.popupHeaderBreaker = $("<div id='divNonyPopupHeaderBreaker' class='nonyPopWinHeaderBreaker'></div>");
				this.popupBody = $("<div id='divNonyPopupBody' class='nonyPopWinBody'></div>");
				this.popupIframe = $("<iframe id='"+this.iframeName+"' name='"+this.iframeName+"' src='"+this.url+"' class='iframeInPopup' frameborder='0' marginwidth='0' marginheight='0' scrolling='auto'></iframe>");
				this.popupFooter = $("<div id='divNonyPopupFooter' class='nonyPopWinFooter'></div>");
			} else if (this.popupMethod == "popupDialog") {
				this.popupBase = $("<div id='"+this.popupId+"' class='nonyDialogBase' style='display:none'></div>").css("width", this.width).css("height", this.height);
				this.popupWinHolder = $("<div id='divNonyPopupWindowHolder' class='nonyDialogWindowHolder'></div>");
				this.popupHeaderHolder = $("<div id='divNonyPopupHeaderHolder' class='nonyDialogHeaderHolder'></div>");
				this.popupHeaderTitle = $("<div id='divNonyPopupHeaderTitle' class='nonyDialogHeaderTitle'></div>").html(this.header);
				this.popupHeaderCloseButton = $("<div id='divNonyPopupHeaderCloseButton' class='nonyDialogHeaderCloseButton'><i class='fa fa-times fa-lg icnEn'></i></div>");
				this.popupHeaderBreaker = $("<div id='divNonyPopupHeaderBreaker' class='nonyDialogHeaderBreaker'></div>");
				this.popupBody = $("<div id='divNonyPopupBody' class='nonyDialogBody'></div>");
				this.popupIframe = $("<iframe id='"+this.iframeName+"' name='"+this.iframeName+"' src='"+this.url+"' class='iframeInPopup' frameborder='0' marginwidth='0' marginheight='0' scrolling='auto'></iframe>");
				this.popupFooter = $("<div id='divNonyPopupFooter' class='nonyDialogFooter'></div>");
			}

			if (this.draggable) {
				$(this.popupHeaderHolder).css("cursor", "move");
			}

			$(this.popupIframe).css("height", (this.height - correctionValueForHeight) + "px");

			if (this.popupMethod == "popupWithIframe") {
				$(this.popupIframe).corner("bottom 5px");
			} else if (this.popupMethod == "popupDialog") {
				$(this.popupFooter).corner("bottom 5px");
			}
		},
		_setModal : function() {
			if (this.modal) {
				if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
					$.blockUI({
						message:null,
						overlayCSS:{
							zIndex:"500",
							cursor:"wait",
							background:"#FFFFFF",
							opacity:"0.0",
							fadeIn:100,
							fadeOut:100
						}
					});
				}
			}
		},
		_setBlind : function() {
			if (this.blind) {
				if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
					$.blockUI({
						message:null,
						overlayCSS:{
							zIndex:"500",
							cursor:"wait",
							backgroundColor:"#000000",
							opacity:"0.1",
							fadeIn:100,
							fadeOut:100
						}
					});
				}
			}
		},
		_appendObjects : function() {
			$(this.popupWinHolder).appendTo(this.popupBase);
			$(this.popupHeaderHolder).appendTo(this.popupWinHolder);
			$(this.popupHeaderTitle).appendTo(this.popupHeaderHolder);
			$(this.popupHeaderCloseButton).appendTo(this.popupHeaderHolder);
			$(this.popupHeaderBreaker).appendTo(this.popupWinHolder);
			$(this.popupBody).appendTo(this.popupWinHolder);
			$(this.popupIframe).appendTo(this.popupBody);
			$(this.popupBase).appendTo("body");

			if (this.popupMethod == "popupDialog") {
				$(this.popupFooter).appendTo(this.popupBody).css("margin-top", "-1px");
			}

			this.params.popupBase = $(this.popupBase);
			this.params.popupWinHolder = $(this.popupWinHolder);
			this.params.popupHeaderHolder = $(this.popupHeaderHolder);
			this.params.popupHeaderTitle = $(this.popupHeaderTitle);
			this.params.popupHeaderCloseButton = $(this.popupHeaderCloseButton);
			this.params.popupHeaderBreaker = $(this.popupHeaderBreaker);
			this.params.popupBody = $(this.popupBody);
			this.params.popupIframe = $(this.popupIframe);
			this.params.popupFooter = $(this.popupFooter);
		},
		_setShadow : function() {
			if (this.shadow) {$(this.popupBase).addClass("shadowForDivNonyPopupBase");}
		},
		_setPosition : function() {
			if ("center" == this.left) {$(this.popupBase).css("left", (($(window).innerWidth() / 2) - (this.width / 2)) + "px");}
			else {$(this.popupBase).css("left", this.left);}

			if ("center" == this.top) {$(this.popupBase).css("top", (($(window).innerHeight() / 2) - (this.height / 2)));}
			else {$(this.popupBase).css("top", this.top);}
		},
		_checkContentsHeight : function(params) {
			var html = "", htmlDiv = "", contentMessage = "", table, div, tableOnBody;

			contentMessage = $.nony.replace(params.contents, "\n", "<br/>");

			html += "<table><tr>";
			html += "<td style='vertical-align:top;padding-right:4px;'><img src='"+jsconfig.get("imgThemeCom")+"/"+params.type+".png"+"'/></td>";
			html += "<td style='padding:2px 4px;line-height:16px;white-space:nowrap;font-size:12px'>"+contentMessage+"</td>";
			html += "</tr></table>";

			table = $(html);
			$("body").append($(table));

			if ($.nony.isEmpty(params.width)) {
				if ($(table).outerWidth() < params.minWidth) {
					params.dialogContentsWidth = params.minWidth;
				} else if ($(table).outerWidth() > params.maxWidth) {
					params.dialogContentsWidth = params.maxWidth;
				} else {
					params.dialogContentsWidth = $(table).outerWidth();
				}
			} else {
				params.dialogContentsWidth = (params.width);
			}

			params.dialogContentsHeight = $(table).outerHeight();

			$(table).remove();
		},
		_setEffect : function() {
			var onLoad = this.onLoad;
			var checkContentsHeight = this._checkContentsHeight;
			var setContents = this._setContents;
			var setDialogHeight = this._setDialogHeight;
			var params = this.params;

			if (params.popupMethod == "popupDialog") {
				checkContentsHeight(params);
				setDialogHeight(params);
			}

			if ("slide" == this.effect) {
				$(this.popupBase).delay(200).slideDown(jsconfig.get("effectDuration"), function() {
					if (params.popupMethod == "popupDialog") {setContents(params);}
					if (typeof onLoad == "function") {onLoad();}
				});
			} else if ("fade" == this.effect) {
				$(this.popupBase).delay(200).fadeIn(jsconfig.get("effectDuration"), function() {
					if (params.popupMethod == "popupDialog") {setContents(params);}
					if (typeof onLoad == "function") {onLoad();}
				});
			} else {
				$(this.popupBase).delay(200).show(jsconfig.get("effectDuration"), function() {
					if (params.popupMethod == "popupDialog") {setContents(params);}
					if (typeof onLoad == "function") {onLoad();}
				});
			}
		},
		_setDialogHeight : function(params) {
			var dialogHeight = params.dialogContentsHeight, dialogWidth = params.dialogContentsWidth;
			var popupFooterHeight = $(params.popupFooter).actual("outerHeight"), heightAdjust = 0, heightSum = 0;

			heightSum += $(params.popupHeaderHolder).actual("height");
			heightSum += $(params.popupHeaderBreaker).actual("height");
			heightSum += ($.nony.getCssAttributeNumber($(params.popupBase), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupBase), "padding-top") + $.nony.getCssAttributeNumber($(params.popupBase), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderHolder), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderHolder), "margin-top") + $.nony.getCssAttributeNumber($(params.popupHeaderHolder), "margin-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderHolder), "padding-top") + $.nony.getCssAttributeNumber($(params.popupHeaderHolder), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderBreaker), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderBreaker), "margin-top") + $.nony.getCssAttributeNumber($(params.popupHeaderBreaker), "margin-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupHeaderBreaker), "padding-top") + $.nony.getCssAttributeNumber($(params.popupHeaderBreaker), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupIframe), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(params.popupBody), "border"));

			if ((params.dialogContentsHeight + params.limitHeightForMax) >= $(window).innerHeight()) {
				dialogHeight = $(window).innerHeight() - params.limitHeightForMax;
			}

			if (params.dialogContentsWidth <= params.minWidth) {
				dialogWidth = params.minWidth;
			}

			if (params.dialogContentsHeight <= params.minHeight) {
				dialogHeight = params.minHeight;
			}

			/*!
			 * Adjust width / height here (for dialog)
			 */
			if ($.nony.browser.Chrome) {heightAdjust = params.heightAdjust;}
			else if ($.nony.browser.FireFox) {heightAdjust = (params.heightAdjust + 2);}
			else {heightAdjust = (params.heightAdjust + 2);}

			$(params.popupIframe).height(dialogHeight + "px");
			$(params.popupBase).height((($(params.popupIframe).outerHeight()) + heightSum + popupFooterHeight + heightAdjust) + "px");

			$(params.popupIframe).width(dialogWidth + "px");
			$(params.popupBase).width(dialogWidth + "px");

			$(params.popupBase).css("top", (($(window).innerHeight() / 2) - (($(params.popupBase).outerHeight()) / 2)) + "px");
			$(params.popupBase).css("left", (($(window).innerWidth() / 2) - (($(params.popupBase).outerWidth()) / 2)) + "px");
		},
		_setContents : function(params) {
			var divHolder = $("#"+params.iframeName).contents().find("#divPopupWindowHolder");
			var html = "";

			$(divHolder).addClass("areaContainerPopup");

			html += "<table><tr>";
			html += "<td style='vertical-align:top;padding-right:4px;'><img src='"+jsconfig.get("imgThemeCom")+"/"+params.type+".png"+"'/></td>";
			html += "<td style='padding:2px 4px;line-height:16px;'>"+$.nony.replace(params.contents, "\n", "<br/>")+"</td>";
			html += "</tr></table>";

			$(divHolder).html(html);

			params.popupWindowHolderInIframe = $(divHolder);
			params.dialogWidth = $(divHolder).outerWidth();
			params.dialogHeight = $(divHolder).outerHeight();
		},
		_setButtons : function() {
			var buttonScript = "";

			for (var i=0; i<this.buttons.length; i++) {
				var iconString = "", button = this.buttons[i];

				if ($.nony.isEmpty(button.callback)) {
					throw new Error("The callback function has not been defined!");
					break;
				}

				if (button.caption.toLowerCase().indexOf("yes") != -1 || button.caption.toLowerCase().indexOf("ok") != -1) {
					iconString = "fa fa-check fa-lg";
				} else if (button.caption.toLowerCase().indexOf("no") != -1 || button.caption.toLowerCase().indexOf("cancel") != -1) {
					iconString = "fa fa-times fa-lg";
				} else {
					iconString = "";
				}

				buttonScript += "<a id=\"btnPopupDialog_"+this.popupId+"_"+button.caption+"\" type=\"button\" class=\"btn btn-default\"><i class=\""+iconString+"\"></i>&nbsp;"+button.caption+"</a>";

				if (i != (this.buttons.length - 1)) {buttonScript += " ";}
			}

			$(this.popupFooter).html("<div style='display:inline-block;'>"+buttonScript+"</div>");
		},
		_setIframeHeight : function() {
			var heightAdjust = 0, heightSum = 0;

			heightSum += $(this.popupHeaderHolder).actual("height");
			heightSum += $(this.popupHeaderBreaker).actual("height");
			heightSum += ($.nony.getCssAttributeNumber($(this.popupBase), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupBase), "padding-top") + $.nony.getCssAttributeNumber($(this.popupBase), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderHolder), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderHolder), "margin-top") + $.nony.getCssAttributeNumber($(this.popupHeaderHolder), "margin-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderHolder), "padding-top") + $.nony.getCssAttributeNumber($(this.popupHeaderHolder), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderBreaker), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderBreaker), "margin-top") + $.nony.getCssAttributeNumber($(this.popupHeaderBreaker), "margin-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupHeaderBreaker), "padding-top") + $.nony.getCssAttributeNumber($(this.popupHeaderBreaker), "padding-bottom"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupIframe), "border"));
			heightSum += ($.nony.getCssAttributeNumber($(this.popupBody), "border"));

			/*!
			 * Adjust height here (for popup)
			 */
			if ($.nony.browser.Chrome) {heightAdjust = 2;}
			else if ($.nony.browser.FireFox) {heightAdjust = 10;}
			else {heightAdjust = 10;}

			$(this.popupIframe).css("height", (this.height - (heightSum + heightAdjust))+"px");
		},
		_bindEvent : function(params) {
			params.effect = this.effect;
			params.popupBase = this.popupBase;
			params.oncloseCallback = this.oncloseCallback;
			params.width = this.width;
			params.height = this.height;
			params.popupIframe = this.popupIframe;

			$(this.popupHeaderCloseButton).bind("click", params, this._close);

			if (params.popupMethod == "popupDialog") {
				for (var i=0; i<this.buttons.length; i++) {
					var button = this.buttons[i];
					$("#btnPopupDialog_"+this.popupId+"_"+button.caption).bind("click", params, button.callback);
					$("#btnPopupDialog_"+this.popupId+"_"+button.caption).bind("click", params, this._close);
				}
			}

			if (this.draggable) {
				$(this.popupHeaderTitle).bind("mousedown", params, this._setMousedown);
				$(this.popupHeaderTitle).bind("mouseup", params, this._setMouseup);
			}

			if (this.left == "center" && this.top == "center") {
				$(window).bind("scroll", params, this._reposPopup);
				$(window).bind("resize", params, this._reposPopup);
			}
		},
		_reposPopup : function(event) {
			$(event.data.popupBase).stop().animate({"left":(($(window).innerWidth() / 2) - (event.data.width / 2)) + "px", "top":(($(window).innerHeight() / 2) - (event.data.height / 2)) + "px"}, jsconfig.get("effectDuration"), "swing");
		},
		_close : function(event) {
			var unblockOption;
			var closingFunction = function() {
				$(event.data.popupBase).remove();
				if (typeof event.data.oncloseCallback == "function") {unblockOption = {fadeOut:100, onUnblock:function() {event.data.oncloseCallback();}};}
				else {unblockOption = {fadeOut:100};}

				if (($(".nonyPopWinBase").length + $(".nonyDialogBase").length) == 0) {
					$.unblockUI(unblockOption);
				}
			};

			if ("slide" == event.data.effect) {
				$(event.data.popupBase).stop().slideUp(jsconfig.get("effectDuration"), function() {
					closingFunction();
				});
			} else if ("fade" == event.data.effect) {
				$(event.data.popupBase).stop().fadeOut(jsconfig.get("effectDuration"), function() {
					closingFunction();
				});
			} else {
				$(event.data.popupBase).stop().hide(jsconfig.get("effectDuration"), function() {
					closingFunction();
				});
			}
		},
		_setMousedown : function(event) {
			$(event.data.popupIframe).css("visibility", "hidden");
			$(event.data.popupBase).draggable({
				opacity:0.8,
				containment:"parent"
			});
		},
		_setMouseup : function(event) {
			$(event.data.popupIframe).css("visibility", "visible");
		}
	};
})(jQuery);