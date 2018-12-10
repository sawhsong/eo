/**
 * Anchor - For Data Grid only
 */
UiAnchor = Class.create();
UiAnchor.prototype = {
	/**
	 * Constructor
	 */
	initialize : function() {
		this.id = "";
		this.className = "aEn";
		this.style = "";
		this.script = "";
		this.text = ""
	},
	/**
	 * Setter / Getter
	 */
	setId : function(id) {this.id = id; return this;},
	setClassName : function(className) {this.className = className; return this;},
	setStyle : function(style) {this.style = style; return this;},
	setScript : function(script) {this.script = script; return this;},
	setText : function(text) {this.text = text; return this;},
	/**
	 * Method
	 */
	addClassName : function(className) {this.className += ($.nony.isEmpty(this.className)) ? className : " "+className; return this;},
	removeClassName : function(className) {
		if (!$.nony.isEmpty(this.className)) {this.className.replace(className, "");}
		return this;
	},
	/**
	 * toString
	 */
	toHtmlString : function() {
		var str = "";

		str += "<a id=\""+this.id+"\"";
		if (!$.nony.isEmpty(this.className)) {str += " class=\""+this.className+"\"";}
		if (!$.nony.isEmpty(this.style)) {str += " style=\""+this.style+"\"";}
		if (!$.nony.isEmpty(this.script)) {str += " onclick=\""+this.script+"\"";}
		str += ">";
		if (!$.nony.isEmpty(this.text)) {str += this.text;}
		str += "</a>";

		return str;
	}
};