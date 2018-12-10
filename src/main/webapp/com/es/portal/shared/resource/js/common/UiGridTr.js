/**
 * Table.tr - For Data Grid only
 */
UiGridTr = Class.create();
UiGridTr.prototype = {
	/**
	 * Constructor
	 */
	initialize : function() {
		this.className = "";
		this.style = "";
		this.childList= new Array();
	},
	/**
	 * Setter / Getter
	 */
	setClassName : function(className) {this.className = className; return this;},
	setStyle : function(style) {this.style = style; return this;},
	/**
	 * Method
	 */
	addClassName : function(className) {this.className += ($.nony.isEmpty(this.className)) ? className : " "+className; return this;},
	removeClassName : function(className) {
		if (!$.nony.isEmpty(this.className)) {this.className.replace(className, "");}
		return this;
	},
	addChild : function(obj) {this.childList.push(obj); return this;},
	/**
	 * toString
	 */
	toHtmlString : function() {
		var str = "";

		str += "<tr";
		if (!$.nony.isEmpty(this.className)) {str += " class=\""+this.className+"\"";}
		if (!$.nony.isEmpty(this.style)) {str += " style=\""+this.style+"\"";}
		str += ">";
		if (this.childList != null && this.childList.length > 0) {
			for (var i=0; i<this.childList.length; i++) {
				str += this.childList[i].toHtmlString();
			}
		}
		str += "</tr>";

		return str;
	}
};