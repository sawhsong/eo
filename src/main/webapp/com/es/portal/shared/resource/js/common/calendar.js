/**
 * calendar JavaScript
 */
(function($) {
	$.nony.calendar = {
		openCalendar : function(event, txtObjectId, options) {
			var params = (options == null) ? {} : options;
			var calendarId = "nonyFrameworkCalendar";
			var value = "";

			params.calendarId = calendarId;
			if ($("#"+params.calendarId).length > 0) {$("#"+params.calendarId).remove();}

			if (event == null || event == undefined || event == "") {
				throw new Error("Event parameter" + com.message.invalid);
				return;
			}

			if (typeof txtObjectId == "string") {params.dateElement = $("#"+txtObjectId);}
			if (typeof txtObjectId == "object") {params.dateElement = $(txtObjectId);}

			if (params.dateElement == null || $(params.dateElement).length != 1) {
				throw new Error("Date element parameter" + com.message.invalid);
				return;
			}

			value = $(params.dateElement).val();
			if (!$.nony.isEmpty(value)) {
				if (!moment(value, jsconfig.get("dateFormatJs_"+jsconfig.get("langCode"))).isValid()) {
					throw new Error("The value " + com.message.invalid);
					return;
				}
			}

			this._setOptions(event, params);
			this._createObjects();
			this._setObjects();
			this._setPosition(params);
			this._setHeader();
			this._bindEvent(params);
			this._setEffect();

			return {
				calendarId : this.calendarId,
				effect : this.effect,
				calendarBase : this.calendarBase,
				oncloseCallback : this.oncloseCallback,
				close : function() {
					var calendarBase = this.calendarBase;
					var oncloseCallback = this.oncloseCallback;
					var closingFunction = function() {
						$(window).unbind("click");
						$(window).unbind("mousedown");
						$(window).unbind("mouseup");
						$(calendarBase).remove();
						if (oncloseCallback) {oncloseCallback();}
					};

					if ("slide" == this.effect) {
						$(this.calendarBase).stop().slideUp(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else if ("fade" == this.effect) {
						$(this.calendarBase).stop().fadeOut(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					} else {
						$(this.calendarBase).stop().hide(jsconfig.get("effectDuration"), function() {
							closingFunction();
						});
					}
				},
				setHeader : function(header) {
					$(this.calendarHeaderTitle).html(header);
				}
			};
		},
		_setOptions : function(event, params) {
			this.params = params;
			this.dateElement = null;
			this.dateElementValue = "";
			this.timeElement = null;
			this.timeElementValue = "";
			this.returnDateElement = null;
			this.returnTimeElement = null;
			this.returnDayOfWeekElement = null;
			this.returnWeekElement = null;
			this.calendarId = params.calendarId;
			this.language = params.language = jsconfig.get("langCode");
			this.dateFormat = params.dateFormat = (this.language == "ko") ? "YYYY-MM-DD" : jsconfig.get("dateFormatJs");
			this.timeFormat = params.timeFormat = "HH:mm:ss";
			this.clickedImg = params.clickedImg = event.target;
			this.weekNumberName = params.weekNumberName = "Wk";
			this.monthNameShort = params.monthNameShort = framework.header.monthNameShort;
			this.monthNameLong = params.monthNameLong = framework.header.monthNameLong;
			this.dayOfWeekShort = params.dayOfWeekShort = framework.header.dayOfWeekShort;
			this.dayOfWeekLong = params.dayOfWeekLong = framework.header.dayOfWeekLong;
			this.effect = params.effect = params.effect || "fade";															// slide / fade / [size]
			this.displayType = params.displayType = params.displayType || "Short";											// display type of Month (Long / [Short])
			this.returnType = params.returnType = params.returnType || "Long";												// return type of day of week ([Long] / Short)
			this.maxYearMonth = params.maxYearMonth = "500012";																// maximum year and month to display (YYYYMM / [unlimited])
			this.minYearMonth = params.minYearMonth = "100001";																// minimum year and month to display (YYYYMM / [unlimited])
			this.displayTime = params.displayTime = (params.displayTime == true) ? true : false;							// display time ([false] / true)
			this.shadowEffect = params.shadowEffect = (params.shadowEffect == false) ? false : true;						// calendar object shadowEffect ([true] / false)
			this.statusBar = params.statusBar = (params.statusBar == false) ? false : true;									// status bar ([true] / false)
			this.draggable = params.draggable = (params.draggable == false) ? false : true;									// drag ([true] / false)
			this.weekNumber = params.weekNumber = (params.weekNumber == false) ? false : true;								// show week number ([true] / false)
			this.onloadCallback = params.onloadCallback;																	// onload function(function() {alert("onload");};, [none])
			this.oncloseCallback = params.oncloseCallback;																	// onclose function(function() {alert("onclose");};, [none])
			this.firstDayOfWeek = params.firstDayOfWeek = params.firstDayOfWeek || "Sun";									// which day of week will be shown first ([Sunday] / Monday)
			this.elementBoxes = params.elementBoxes = params.elementBoxes || [{"Date":$(params.dateElement).attr("id")}];	// [{"Date":dateElementId},{"Time":timeElementId},{"DoW":dateOfWeekElementId},{"Week":weekElementId}]
			this.returnBoxes = params.returnBoxes = params.returnBoxes || [{"Date":$(params.dateElement).attr("id")}];		// [{"Date":dateElementId},{"Time":timeElementId},{"DoW":dateOfWeekElementId},{"Week":weekElementId}]

			for (var i=0; i<this.elementBoxes.length; i++) {
				var textBox = this.elementBoxes[i];
				var key = ""+Object.keys(textBox);

				if (key.toLowerCase() == "date") {
					if (typeof textBox[key] == "string") {this.dateElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.dateElement = $(textBox[key]);}
				}

				if (key.toLowerCase() == "time") {
					if (typeof textBox[key] == "string") {this.timeElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.timeElement = $(textBox[key]);}

					this.timeElementValue = params.timeElementValue = params.timeElementValue = $(this.timeElement).val();
				}
			}

			if (this.dateElement == null || $(this.dateElement).length != 1) {this.dateElement = params.dateElement;}
			else {params.dateElement = this.dateElement;}

			this.dateElementValue = params.dateElementValue = $(this.dateElement).val();

			for (var i=0; i<this.returnBoxes.length; i++) {
				var textBox = this.returnBoxes[i];
				var key = ""+Object.keys(textBox);

				if (key.toLowerCase() == "date") {
					if (typeof textBox[key] == "string") {this.returnDateElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.returnDateElement = $(textBox[key]);}
				}

				if (key.toLowerCase() == "time") {
					if (typeof textBox[key] == "string") {this.returnTimeElement = params.returnTimeElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.returnTimeElement = params.returnTimeElement = $(textBox[key]);}
				}

				if (key.toLowerCase() == "dow") {
					if (typeof textBox[key] == "string") {this.returnDayOfWeekElement = params.returnDayOfWeekElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.returnDayOfWeekElement = params.returnDayOfWeekElement = $(textBox[key]);}
				}

				if (key.toLowerCase() == "week") {
					if (typeof textBox[key] == "string") {this.returnWeekElement = params.returnWeekElement = $("#"+textBox[key]);}
					if (typeof textBox[key] == "object") {this.returnWeekElement = params.returnWeekElement = $(textBox[key]);}
				}
			}

			if (this.returnDateElement == null || $(this.returnDateElement).length != 1) {this.returnDateElement = params.dateElement;}
			else {params.returnDateElement = this.returnDateElement;}

			this.elementYMD = params.elementYMD = ($.nony.isEmpty(this.dateElementValue)) ? "" : moment(this.dateElementValue, this.dateFormat).format("YYYYMMDD");
			this.elementHMS = params.elementHMS = ($.nony.isEmpty(this.timeElementValue)) ? "" : moment(this.timeElementValue, this.timeFormat).format("HHmmss");
			this.elementYear = params.elementYear = ($.nony.isEmpty(this.elementYMD)) ? "" : this.elementYMD.substring(0, 4);
			this.elementMonth = params.elementMonth = ($.nony.isEmpty(this.elementYMD)) ? "" : this.elementYMD.substring(4, 6);
			this.elementDate = params.elementDate = ($.nony.isEmpty(this.elementYMD)) ? "" : this.elementYMD.substring(6, 8);
			this.elementHour = params.elementHour = ($.nony.isEmpty(this.elementHMS)) ? "" : this.elementHMS.substring(0, 2);
			this.elementMinute = params.elementMinute = ($.nony.isEmpty(this.elementHMS)) ? "" : this.elementHMS.substring(2, 4);
			this.elementSecond = params.elementSecond = ($.nony.isEmpty(this.elementHMS)) ? "" : this.elementHMS.substring(4, 6);
			this.systemYMD = params.systemYMD = moment().format("YYYYMMDD");
			this.systemHMS = params.systemHMS = moment().format("HHmmss");
			this.year = params.year = this.elementYear || this.systemYMD.substring(0, 4);
			this.month = params.month = this.elementMonth || this.systemYMD.substring(4, 6);
			this.date = params.date = this.elementDate || this.systemYMD.substring(6, 8);
			this.hour = params.hour = this.elementHour || this.systemHMS.substring(0, 2);
			this.minute = params.minute = this.elementMinute || this.systemHMS.substring(2, 4);
			this.second = params.second = this.elementSecond || this.systemHMS.substring(4, 6);

			if (this.weekNumber) {
				this.width = params.width = "236";
			} else {
				this.width = params.width = "230";
			}

			/*!
			 * Height by theme id - because of font
			 */
			var themeId = jsconfig.get("themeId");
			if (themeId == "theme000" || themeId == "theme001") {
				this.height = params.height = "236";
			} else if (themeId == "theme002") {
				this.height = params.height = "222";
			} else {
				this.height = params.height = "236";
			}
		},
		_createObjects : function() {
			this.calendarBase = $("<div id='"+this.calendarId+"' class='nonyCalendarBase' style='display:none'></div>").css("width", this.width);
			this.calendarWinHolder = $("<div id='divNonyCalendarWindowHolder' class='divNonyCalendarWindowHolder'></div>");
			this.calendarHeaderHolder = $("<div id='divNonyCalendarHeaderHolder' class='divNonyCalendarHeaderHolder'></div>");
			this.calendarHeaderTitle = $("<div id='divNonyCalendarHeaderTitle' class='divNonyCalendarHeaderTitle'></div>");
			this.calendarHeaderCloseButton = $("<div id='divNonyCalendarHeaderCloseButton' class='divNonyCalendarHeaderCloseButton'><i class='fa fa-times fa-sm icnEn'></i></div>");
			this.calendarBody = $("<div id='divNonyCalendarBody' class='divNonyCalendarBody'></div>");
			this.calendarTimeArea = $("<div id='divNonyCalendarTimeArea' class='divNonyCalendarTimeArea'></div>");
			this.calendarStatusBar = $("<div id='divNonyCalendarStatusBar' class='divNonyCalendarStatusBar'></div>");
		},
		_setObjects : function() {
			$(this.calendarWinHolder).appendTo(this.calendarBase);
			$(this.calendarHeaderHolder).appendTo(this.calendarWinHolder);
			$(this.calendarHeaderTitle).appendTo(this.calendarHeaderHolder);
			$(this.calendarHeaderCloseButton).appendTo(this.calendarHeaderHolder);
			$(this.calendarBody).appendTo(this.calendarWinHolder);

			if (this.draggable) {$(this.calendarHeaderHolder).css("cursor", "move");}
			if (this.displayTime) {$(this.calendarTimeArea).appendTo(this.calendarWinHolder);}
			if (this.statusBar) {$(this.calendarStatusBar).appendTo(this.calendarWinHolder);}
			if (!this.statusBar && this.displayTime) {$(this.calendarTimeArea).addClass("borderRadiusBottom");}
			if (!this.statusBar && !this.displayTime) {$(this.calendarBody).addClass("borderRadiusBottom");}

			$(this.calendarBody).css("height", (this.height) + "px");
			$(this.calendarBase).appendTo("body");

			if (this.shadowEffect) {$(this.calendarBase).addClass("shadowForDivNonyCalendarBase");}
		},
		_setPosition : function(params) {
			var positionX = params.positionX || "left", correctionValueX = params.adjustX || 8, correctionValueY = params.adjustY || 18;

			if (positionX == "right" || positionX == "Right") {
				$(this.calendarBase).css("left", ($(this.clickedImg).offset().left + correctionValueX) + "px");
			} else {
				$(this.calendarBase).css("left", ($(this.clickedImg).offset().left + correctionValueX - this.width) + "px");
			}
			$(this.calendarBase).css("top", ($(this.clickedImg).offset().top + correctionValueY) + "px");
		},
		_setHeader : function(argYear, argMonth) {
			if (argYear && argMonth) {
				if (this.language == "ko") {
					$("#divNonyCalendarHeaderTitle").html(argYear + "년 " + this.monthNameShort[argMonth - 1]);
				} else {
					if (this.displayType.toLowerCase() == "long") {$("#divNonyCalendarHeaderTitle").html(this.monthNameLong[argMonth - 1] + " " + argYear);}
					else {$("#divNonyCalendarHeaderTitle").html(this.monthNameShort[argMonth - 1] + " " + argYear);}
				}
			} else {
				if (this.language == "ko") {
					$(this.calendarHeaderTitle).html(this.year + "년 " + this.monthNameShort[this.month - 1]);
				} else {
					if (this.displayType.toLowerCase() == "long") {$(this.calendarHeaderTitle).html(this.monthNameLong[this.month - 1] + " " + this.year);}
					else {$(this.calendarHeaderTitle).html(this.monthNameShort[this.month - 1] + " " + this.year);}
				}
			}
		},
		_bindEvent : function(params) {
			params.calendarBase = this.calendarBase;

			$(this.calendarHeaderCloseButton).bind("click", params, this._close);

			if (this.draggable) {
				$(this.calendarHeaderTitle).bind("mousedown", params, this._setMousedown);
				$(this.calendarHeaderTitle).bind("mouseup", params, this._setMouseup);
			}

			$(window).bind("click", params, this._setDocumentClick);
		},
		_getParamValueForDrawingCalendar : function() {
			var params = this.params;

			params.calendarBase = this.calendarBase;
			params.calendarWinHolder = this.calendarWinHolder;
			params.calendarHeaderHolder = this.calendarHeaderHolder;
			params.calendarBody = this.calendarBody;
			params.calendarTimeArea = this.calendarTimeArea;
			params.calendarStatusBar = this.calendarStatusBar;
			params.setHeader = this._setHeader;
			params.drawCalendar = this._drawCalendar;

			return params;
		},
		_setEffect : function() {
			var params = this._getParamValueForDrawingCalendar();
			var drawFunction = function() {
				params.drawCalendar(params);
				if (params.onloadCallback) {params.onloadCallback();}
			};

			if ("slide" == this.effect) {
				drawFunction();
				$(this.calendarBase).delay(0).slideDown(jsconfig.get("effectDuration"), function() {
//					drawFunction();
				});
			} else if ("fade" == this.effect) {
				drawFunction();
				$(this.calendarBase).delay(0).fadeIn(jsconfig.get("effectDuration"), function() {
//					drawFunction();
				});
			} else {
				var widthForAnimate, heightForAnimate;

				$(this.calendarBase).css("left", ($(this.clickedImg).offset().left + 5) + "px");
				$(this.calendarBase).css("top", ($(this.clickedImg).offset().top + 20) + "px");

				drawFunction();

				widthForAnimate = $(this.calendarBase).outerWidth();
				heightForAnimate = $(this.calendarBase).outerHeight();

				$(this.calendarBase).stop().css("opacity", 0.0).css("display", "block").css("width", "0px").css("height", "0px").delay(0).animate({
					marginLeft:"-="+(this.width)+"px",
					width:widthForAnimate+"px",
					height:(heightForAnimate)+"px",
					opacity:1.0
				}, jsconfig.get("effectDuration"), function() {
//					drawFunction();
				});
			}
		},
		_drawCalendar : function(params, firstDayOfWeek, argYear, argMonth, argResetTime) {
			if (params == null) {
				params = this._getParamValueForDrawingCalendar();
				if (!$.nony.isEmpty(firstDayOfWeek)) {params.firstDayOfWeek = firstDayOfWeek;}
				params.year = argYear;
				params.month = argMonth;
				params.resetTime = argResetTime;
			}

			var ds = new DataSet();
			var current = new Date();
			var currentYY = parseInt(current.getFullYear(), 10);
			var currentMM = parseInt(current.getMonth(), 10);
			var currentDD = parseInt(current.getDate(), 10);
			var minYear = minMonth = maxYear = maxMonth = -1;
			var yy = parseInt(params.year, 10), mm = parseInt(params.month, 10), dd = parseInt(params.date, 10);
			var currentHH = $.nony.lpad(""+parseInt(current.getHours(), 10), 2, "0");
			var currentMI = $.nony.lpad(""+parseInt(current.getMinutes(), 10), 2, "0");
			var currentSS = $.nony.lpad(""+parseInt(current.getSeconds(), 10), 2, "0");
			var arrDayNum = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
			var html = timeAreaHtml = statusBarHtml = "";
			var numberOfDaysInMonth = firstDayOfWeekInMonth = weekNumber = tempDate = 0;
			var booleanTemp = false;
			var getDayOfWeekName = function(dayOfWeekNum, longShortFlag) {
				if (longShortFlag == "Short") {
					if (dayOfWeekNum == 0) {return params.dayOfWeekShort[6];}
					else {return params.dayOfWeekShort[dayOfWeekNum-1];}
				} else {
					if (dayOfWeekNum == 0) {return params.dayOfWeekLong[6];}
					else {return params.dayOfWeekLong[dayOfWeekNum-1];}
				}

			};

			if (mm == 0) {
				yy = yy - 1;
				mm = 12;
			} else if (mm == 13) {
				yy = yy + 1;
				mm = 1;
			}

			if (params.minYearMonth) {
				if (!moment(params.minYearMonth, "YYYYMM").isValid() || params.minYearMonth.length != 6) {
					throw new Error("Minimum range value " + com.message.invalid);
					return;
				}

				minYear = parseInt(params.minYearMonth.substring(0, 4));
				minMonth = parseInt(params.minYearMonth.substring(4, 6));
			}

			if (params.maxYearMonth) {
				if (!moment(params.maxYearMonth, "YYYYMM").isValid() || params.maxYearMonth.length != 6) {
					throw new Error("Maximum range value " + com.message.invalid);
					return;
				}

				maxYear = parseInt(params.maxYearMonth.substring(0, 4));
				maxMonth = parseInt(params.maxYearMonth.substring(4, 6));
			}

			var getYearSelect = function() {
				var html = "";

				html += "<select id=\"selNonyCalendarYears\" onchange=\"$.nony.calendar._drawCalendar(null, '', this.value, $('#selNonyCalendarMonths').val())\" title=\"Year\">";
				for (var i=-7; i<=7; i++) {
					var displayYear = (yy + i);

					if (displayYear < minYear || displayYear > maxYear) {continue;}

					html += "<option value=\"" + displayYear + "\" ";
					html += ((displayYear) == yy) ? "selected>" : ">";
					if (params.language == "ko") {html += (displayYear) + "</option>";}
					else {html += (displayYear) + "</option>";}
				}
				html += "</select>";

				return html;
			};
			var getMonthSelect = function() {
				var html = "";

				html += "<select id=\"selNonyCalendarMonths\" onchange=\"$.nony.calendar._drawCalendar(null, '', $('#selNonyCalendarYears').val(), this.value)\" title=\"Month\">";
				for (var i=1; i<=12; i++) {

					if ((yy == minYear && i < minMonth) || (yy == maxYear && i > maxMonth)) {continue;}

					html += "<option value=\"" + i + "\" ";
					html += (i == mm) ? "selected>" : ">";
					if (params.language == "ko") {
						html += i + "</option>";
					} else {
						if (params.displayType == "Long") {
							html += params.monthNameLong[i-1] + "</option>";
						} else {
							html += params.monthNameShort[i-1] + "</option>";
						}
					}
				}
				html += "</select>";

				return html;
			};

			params.setHeader(yy, mm);
			if (params.weekNumber) {ds.addName(params.weekNumberName);}

			if (params.firstDayOfWeek == "Mo") {
				for (var i=0; i<params.dayOfWeekShort.length; i++) {
					ds.addName(params.dayOfWeekShort[i]);
				}
			} else {
				ds.addName(params.dayOfWeekShort[6]);
				for (var i=0; i<params.dayOfWeekShort.length-1; i++) {
					ds.addName(params.dayOfWeekShort[i]);
				}
			}

			if (((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0)) {
				arrDayNum[1] = 29;
			}
			numberOfDaysInMonth = arrDayNum[mm-1];

			firstDayOfWeekInMonth = getDayOfWeekName((new Date(yy, mm-1, 1)).getDay(), "Short");
			weekNumber = moment(""+yy+""+$.nony.lpad(mm, 2, "0")+"01", "YYYYMMDD").week();

			ds.addRow();
			for (var i=0; i<ds.getColumnCnt(); i++) {
				if (ds.getName(i) == params.weekNumberName) {ds.setValue(ds.getRowCnt()-1, i, weekNumber);}
				if (booleanTemp) {ds.setValue(ds.getRowCnt()-1, ds.getName(i), ++tempDate);}
				if (firstDayOfWeekInMonth == ds.getName(i)) {
					ds.setValue(ds.getRowCnt()-1, ds.getName(i), ++tempDate);
					booleanTemp = true;
				}
			}

			for (var i=1; i<6; i++) {
				var tempSum = -1;

				ds.addRow();
				for (var j=0; j<ds.getColumnCnt(); j++) {
					var dateForWeekNum = (params.firstDayOfWeek == "Mo") ? moment(""+yy+""+$.nony.lpad(mm, 2, "0")+$.nony.lpad(tempDate, 2, "0"), "YYYYMMDD") : moment(""+yy+""+$.nony.lpad(mm, 2, "0")+$.nony.lpad(tempDate, 2, "0"), "YYYYMMDD").add("days", 1);

					if (ds.getName(j) == params.weekNumberName) {ds.setValue(ds.getRowCnt()-1, j, moment(dateForWeekNum, "YYYYMMDD").week());}

					if (ds.getName(j) != params.weekNumberName && tempDate <= numberOfDaysInMonth) {
						ds.setValue(ds.getRowCnt()-1, j, ++tempDate);
						tempSum++;
					}

					if (tempDate > numberOfDaysInMonth) {ds.setValue(ds.getRowCnt()-1, j, "&nbsp;");}
				}

				if (tempSum == 0) {ds.setValue(ds.getRowCnt()-1, params.weekNumberName, "");}
			}

			html += "<div id=\"divNonyCalendarNavigator\" class=\"nonyCalendarNavigator\"><table id='tblNonyCalendarNavigator' class='tblNonyCalendarNavigator'>";
			html += "<colgroup>";
			html += "<col width='25%'/>";
			html += "<col width='*'/>";
			html += "<col width='25%'/>";
			html += "</colgroup>";
			html += "<tr>";

			html += "<td class=\"tdNonyCalendarNavigatorLt\">";
			html += "<ul id=\"ulNonyCalendarNavigator\" class=\"ulNonyCalendarNavigator\">";
			html += "<li";
			if (yy == minYear) {
				html += " class=\"liNonyCalendarNavigatorButton disabled\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonDis\" title=\"Previous Year\"><i class=\"fa fa-angle-double-left icnNonyCalendarNavigatorButton\"></i></a>";
			} else {
				html += " class=\"liNonyCalendarNavigatorButton\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonEn\" title=\"Previous Year\"";
				html += " onclick=\"$.nony.calendar._drawCalendar(null, '', parseInt($('#selNonyCalendarYears').val(),10)-1, $('#selNonyCalendarMonths').val())\"";
				html += "><i class=\"fa fa-angle-double-left icnNonyCalendarNavigatorButton\"></i></a>";
			}
			html += "</li>";

			html += "<li";
			if (yy == minYear && mm == minMonth) {
				html += " class=\"liNonyCalendarNavigatorButton disabled\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonDis\" title=\"Previous Month\"><i class=\"fa fa-angle-left icnNonyCalendarNavigatorButton\"></i></a>";
			} else {
				html += " class=\"liNonyCalendarNavigatorButton\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonEn\" title=\"Previous Month\"";
				html += " onclick=\"$.nony.calendar._drawCalendar(null, '', $('#selNonyCalendarYears').val(), parseInt($('#selNonyCalendarMonths').val(),10)-1)\"";
				html += "><i class=\"fa fa-angle-left icnNonyCalendarNavigatorButton\"></i></a>";
			}
			html += "</li>";
			html += "</ul>";
			html += "</td>";

			html += "<td class='tdNonyCalendarNavigatorCt'>";
			if (params.language == "ko") {
				html += getYearSelect();
				html += "&nbsp;";
				html += getMonthSelect();
			} else {
				html += getMonthSelect();
				html += "&nbsp;";
				html += getYearSelect();
			}
			html += "</td>";

			html += "<td class='tdNonyCalendarNavigatorRt'>";
			html += "<ul id=\"ulNonyCalendarNavigator\" class=\"ulNonyCalendarNavigator\">";
			html += "<li";
			if (yy == maxYear && mm == maxMonth) {
				html += " class=\"liNonyCalendarNavigatorButton disabled\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonDis\" title=\"Next Month\"><i class=\"fa fa-angle-right icnNonyCalendarNavigatorButton\"></i></a>";
			} else {
				html += " class=\"liNonyCalendarNavigatorButton\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonEn\" title=\"Next Month\"";
				html += " onclick=\"$.nony.calendar._drawCalendar(null, '', $('#selNonyCalendarYears').val(), parseInt($('#selNonyCalendarMonths').val(),10)+1)\"";
				html += "><i class=\"fa fa-angle-right icnNonyCalendarNavigatorButton\"></i></a>";
			}
			html += "</li>";

			html += "<li";
			if (yy == maxYear) {
				html += " class=\"liNonyCalendarNavigatorButton disabled\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonDis\" title=\"Next Year\"><i class=\"fa fa-angle-double-right icnNonyCalendarNavigatorButton\"></i></a>";
			} else {
				html += " class=\"liNonyCalendarNavigatorButton\">";
				html += "<a class=\"aNonyCalendarNavigatorButtonEn\" title=\"Next Year\"";
				html += " onclick=\"$.nony.calendar._drawCalendar(null, '', parseInt($('#selNonyCalendarYears').val(),10)+1, $('#selNonyCalendarMonths').val())\"";
				html += "><i class=\"fa fa-angle-double-right icnNonyCalendarNavigatorButton\"></i></a>";
			}
			html += "</li>";
			html += "</ul>";
			html += "</td>";
			html += "</tr></table></div>";

			html += "<div id=\"divNonyCalendarTable\" class=\"divNonyCalendarTable\">";
			html += "<table id=\"tblNonyCalendarTable\" class=\"tblNonyCalendarTable\">";
			html += "<colgroup>";
			if (ds.getColumnCnt() == 8) {
				html += "<col width='*'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
				html += "<col width='12%'/>";
			} else {
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
				html += "<col width='14%'/>";
			}
			html += "</colgroup>";

			html += "<thead><tr>";
			for (var i=0; i<ds.getColumnCnt(); i++) {
				var classtoAddForWeekend = classtoAddForWeekNum = onclickFunc = "";

				if (ds.getName(i).toLowerCase() == "sa") {classtoAddForWeekend = "sa";}
				else if (ds.getName(i).toLowerCase() == "su") {
					classtoAddForWeekend = "sunHeader";
					onclickFunc = "$.nony.calendar._drawCalendar(null, 'Su', parseInt($('#selNonyCalendarYears').val(),10), $('#selNonyCalendarMonths').val())";
				} else if (ds.getName(i).toLowerCase() == "mo") {
					classtoAddForWeekend = "monHeader";
					onclickFunc = "$.nony.calendar._drawCalendar(null, 'Mo', parseInt($('#selNonyCalendarYears').val(),10), $('#selNonyCalendarMonths').val())";
				} else if (ds.getName(i) == params.weekNumberName) {classtoAddForWeekNum = "weekNumberHeader";}

				html += "<th class=\"thNonyCalendarTable "+classtoAddForWeekend+" "+classtoAddForWeekNum+"\"";
				if (classtoAddForWeekend == "sunHeader") {
					html += "title=\"\"";
				} else if (classtoAddForWeekend == "monHeader") {
					html += "title=\"\"";
				} else if (classtoAddForWeekNum == "weekNumberHeader") {
					html += "title=\"Week\"";
				}
				html += "onclick=\""+onclickFunc+"\" onmouseover=\"$.nony.calendar._onMouseOverCalendar(this)\">"+ds.getName(i)+"</th>";
			}
			html += "</tr></thead>";

			html += "<tbody>";
			for (var i=0; i<ds.getRowCnt(); i++) {
				html += "<tr>";
				for (var j=0; j<ds.getColumnCnt(); j++) {
					var classtoAddForWeekend = classtoAddForWeekNum = classtoAddForDefault = classtoAddForToday = classtoAddForHover = dateForTitle = "";

					var returnYY = ""+yy;
					var returnMM = $.nony.lpad(""+mm, 2, "0");
					var returnDD = $.nony.lpad(""+ds.getValue(i, j), 2, "0");
					var fullDateForDisp = ""+returnYY + returnMM + returnDD;
					var fullDateForToday = ""+currentYY + $.nony.lpad(""+(currentMM+1), 2, "0") + $.nony.lpad(""+currentDD, 2, "0");

					if (ds.getName(j).toLowerCase() == "sa") {classtoAddForWeekend = "sa";}
					else if (ds.getName(j).toLowerCase() == "su") {classtoAddForWeekend = "su";}
					else if (ds.getName(j) == params.weekNumberName) {classtoAddForWeekNum = "weekNumber";}

					if (ds.getName(j) != params.weekNumberName && !(ds.getValue(i, j) == "&nbsp;" || $.nony.isEmpty(ds.getValue(i, j)))) {classtoAddForHover = "selectable";}
					if (ds.getName(j) != params.weekNumberName && returnDD == dd) {classtoAddForDefault = "defaultDay";}
					if (ds.getName(j) != params.weekNumberName && fullDateForDisp == fullDateForToday) {classtoAddForToday = "today";}

					html += "<td class=\"tdNonyCalendarTableCt "+classtoAddForWeekend+" "+classtoAddForWeekNum+" "+classtoAddForDefault+" "+classtoAddForToday+" "+classtoAddForHover+"\" ";
					if (!$.nony.isEmpty(classtoAddForHover)) {
						var dayOfWeekForTitle = getDayOfWeekName(new Date(returnYY, returnMM-1, returnDD).getDay());

						dateForTitle = $.nony.replace(params.dateFormat, "YYYY", returnYY);
						dateForTitle = $.nony.replace(dateForTitle, "MM", returnMM);
						dateForTitle = $.nony.replace(dateForTitle, "DD", returnDD);

						html += "onclick=\"$.nony.calendar._returnValues(event, this, '";
						html += fullDateForDisp + "', '";
						html += currentHH+currentMI+currentSS+"', '";
						html += dayOfWeekForTitle+"', '";
						html += moment(dateForTitle, params.dateFormat).week();
						html += "')\" ";

						if (params.language == "ko") {html += "dateValue=\"" + dateForTitle + " " + dayOfWeekForTitle +"\" ";}
						else {html += "dateValue=\""+dayOfWeekForTitle + " " + dateForTitle+"\" ";}
					}

					if (classtoAddForWeekNum == "weekNumber") {
						if ($.nony.isEmpty(ds.getValue(i, j)) || "&nbsp;" == ds.getValue(i, j)) {
							html += "dateValue=\"Week "+ds.getValue(i, j)+"\" weekNumber=\"weekNumberBlank\"";
						} else {
							html += "dateValue=\"Week "+ds.getValue(i, j)+"\"";
						}
					}

					html += "onmouseover=\"$.nony.calendar._onMouseOverCalendar(this)\"";
					html += ">" + ds.getValue(i, j) + "</td>";
				}
				html += "</tr>";
			}
			html += "</tbody></table>";

			if (params.displayTime) {
				timeAreaHtml += "<div id=\"divNonyCalendarTimeAreaHolder\" class=\"divNonyCalendarTimeAreaHolder\"><table id=\"tblNonyCalendarTimeArea\" class=\"tblNonyCalendarTimeArea\">";
				timeAreaHtml += "<colgroup>";
				timeAreaHtml += "<col width='40%'/>";
				timeAreaHtml += "<col width='20%'/>";
				timeAreaHtml += "<col width='40%'/>";
				timeAreaHtml += "</colgroup>";
				timeAreaHtml += "<tr>";
				timeAreaHtml += "<td class=\"tdNonyCalendarTimeAreaLt\">";
				timeAreaHtml += "<input type=\"text\" id=\"txtNonyCalendarTimeArea\" name=\"txtNonyCalendarTimeArea\" class=\"txtNonyCalendarTimeBoxEn\" value=\"";
				if (params.resetTime) {
					timeAreaHtml += moment(currentHH + currentMI + currentSS, params.timeFormat).format(params.timeFormat);
				} else {
					timeAreaHtml += moment($.nony.lpad(params.hour, 2, "0") + $.nony.lpad(params.minute, 2, "0") + $.nony.lpad(params.second, 2, "0"), params.timeFormat).format(params.timeFormat);
				}
				timeAreaHtml += "\" style=\"width:70px\"/>";
				timeAreaHtml += "</td>";
				timeAreaHtml += "<td class=\"tdNonyCalendarTimeAreaCt\">";
				if (params.statusBar) {
					timeAreaHtml += "";
				} else {
					timeAreaHtml += "<a id=\"aNonyCalendarTimeAreaToday\" class=\"aEn\" onclick=\"$.nony.calendar._drawCalendar(null, '"+params.firstDayOfWeek+"', "+parseInt(currentYY, 10)+", "+parseInt(currentMM+1, 10)+", true)\">Today</a>";
				}
				timeAreaHtml += "</td>";
				timeAreaHtml += "<td class=\"tdNonyCalendarTimeAreaRt\">";
				timeAreaHtml += "<a id=\"aNonyCalendarTimeAreaResetTime\" class=\"aEn\" onclick=\"$.nony.calendar._drawCalendar(null, '', parseInt($('#selNonyCalendarYears').val(),10), $('#selNonyCalendarMonths').val(), true)\">Reset Time</a>";
				timeAreaHtml += "</td>";
				timeAreaHtml += "</tr></table></div>";

				$(params.calendarTimeArea).html(timeAreaHtml);
			}

			if (params.statusBar) {
				statusBarHtml += "<div id=\"divNonyCalendarStatusBarHolder\" class=\"divNonyCalendarStatusBarHolder\"><table id=\"tblNonyCalendarStatusBar\" class=\"tblNonyCalendarStatusBar\">";
				statusBarHtml += "<colgroup>";
				statusBarHtml += "<col width='80%'/>";
				statusBarHtml += "<col width='20%'/>";
				statusBarHtml += "</colgroup>";
				statusBarHtml += "<tr>";
				statusBarHtml += "<td id=\"tdNonyCalendarStatusBarContents\" class=\"tdNonyCalendarStatusBarLt\"></td>";
				statusBarHtml += "<td class=\"tdNonyCalendarStatusBarRt\">";
				statusBarHtml += "<a id=\"aNonyCalendarTimeAreaToday\" class=\"aEn\" onclick=\"$.nony.calendar._drawCalendar(null, '"+params.firstDayOfWeek+"', "+parseInt(currentYY, 10)+", "+parseInt(currentMM+1, 10)+", true)\">Today</a>";
				statusBarHtml += "</td>";
				statusBarHtml += "</tr></table></div>";

				$(params.calendarStatusBar).html(statusBarHtml);
			}

			html += "</div>";

			$(params.calendarBody).html(html);

			$("#txtNonyCalendarTimeArea").mask("99:99:99", {placeholder:"hh:mm:ss"});

			$("#selNonyCalendarYears").selectpicker();
			$("#selNonyCalendarMonths").selectpicker();
		},
		_returnValues : function(event, object, returnDate, returnTime, returnDayOfWeek, returnWeekNumber) {
			$(this.returnDateElement).val(moment(returnDate, "YYYYMMDD").format(this.dateFormat));
			if ($(this.returnTimeElement).length > 0) {
				if ($("#txtNonyCalendarTimeArea").length > 0) {
					$(this.returnTimeElement).val(moment($("#txtNonyCalendarTimeArea").val(), "HHmmss").format(this.timeFormat));
				} else {
					$(this.returnTimeElement).val(moment(returnTime, "HHmmss").format(this.timeFormat));
				}
			}
			if ($(this.returnDayOfWeekElement).length > 0) {$(this.returnDayOfWeekElement).val(returnDayOfWeek);}
			if ($(this.returnWeekElement).length > 0) {$(this.returnWeekElement).val(returnWeekNumber);}

			event.data = this.params;
			this._close(event);
		},
		_onMouseOverCalendar : function(object) {
			if ($("#tblNonyCalendarStatusBar").length > 0) {
				$("#tdNonyCalendarStatusBarContents").html("&nbsp;");
				if ($(object).attr("class").indexOf("monHeader") != -1) {
					$("#tdNonyCalendarStatusBarContents").html("Display Monday first");
				} else if ($(object).attr("class").indexOf("sunHeader") != -1) {
					$("#tdNonyCalendarStatusBarContents").html("Display Sunday first");
				} else if ($(object).attr("class").indexOf("tdNonyCalendarTableCt") != -1) {
					$("#tdNonyCalendarStatusBarContents").html($(object).attr("dateValue"));
				} else if ($(object).attr("class").indexOf("weekNumberHeader") != -1){
					$("#tdNonyCalendarStatusBarContents").html("Week");
				} else if ($(object).attr("class").indexOf("weekNumber") != -1) {
					$("#tdNonyCalendarStatusBarContents").html($(object).attr("dateValue"));
				}
			}
		},
		_close : function(event) {
			var closingFunction = function() {
				$(window).unbind("click");
				$(window).unbind("mousedown");
				$(window).unbind("mouseup");
				$(event.data.calendarBase).remove();
				if (event.data.oncloseCallback) {event.data.oncloseCallback();}
			};

			if ("slide" == event.data.effect) {
				$(event.data.calendarBase).stop().slideUp(jsconfig.get("effectDuration"), function() {closingFunction();});
			} else if ("fade" == event.data.effect) {
				$(event.data.calendarBase).stop().fadeOut(jsconfig.get("effectDuration"), function() {closingFunction();});
			} else {
				$(event.data.calendarBase).stop().hide(jsconfig.get("effectDuration"), function() {closingFunction();});
			}
		},
		_setMousedown : function(event) {
			$(event.data.calendarBase).stop().draggable({
				opacity:0.8,
				containment:"parent"
			});
		},
		_setMouseup : function(event) {
		},
		_setDocumentClick : function(event) {
			var eventTargetId = $(event.target).attr("id");
			var evnetTargetClass = $(event.target).attr("class");
			var selectboxClass = $(event.target).closest("ul").attr("class");
			var valueToCheck = eventTargetId + evnetTargetClass + selectboxClass;

			try {
				if (!(eventTargetId == $(event.data.clickedImg).attr("id"))) {
					if (valueToCheck.toLowerCase().indexOf("nonycalendar") != -1 || valueToCheck.toLowerCase().indexOf("dropdown-menu inner") != -1) {
					} else {$.nony.calendar._close(event);}
				}
			} catch(e) {}
		}
	};
})(jQuery);