/**
 * Table.td - For Data Grid only
 */
UiGridTd = Class.create();
UiGridTd.prototype = {
	/**
	 * Constructor
	 */
	initialize : function() {
		this.className = "tdGrid";
		this.style = "";
		this.attribute = "";
		this.text = "";
		this.textBeforeChild = "";
		this.textAfterChild = "";
		this.childList= new Array();
	},
	/**
	 * Setter / Getter
	 */
	setClassName : function(className) {this.className = className; return this;},
	setStyle : function(style) {this.style = style; return this;},
	setText : function(text) {this.text = text; return this;},
	setAttribute : function(attributes) {this.attribute = attributes; return this;},
	/**
	 * Method
	 */
	addClassName : function(className) {this.className += ($.nony.isEmpty(this.className)) ? className : " "+className; return this;},
	removeClassName : function(className) {
		if (!$.nony.isEmpty(this.className)) {this.className.replace(className, "");}
		return this;
	},
	addAttribute : function(attribute) {this.attribute += ($.nony.isEmpty(this.attribute)) ? attribute : ";"+attribute; return this;},
	addChild : function(obj) {this.childList.push(obj); return this;},
	addTextBeforeChild : function(text) {this.textBeforeChild = text; return this},
	addTextAfterChild : function(text) {this.textAfterChild = text; return this},
	/**
	 * toString
	 */
	toHtmlString : function() {
		var str = "", attrArray = null, key = "", val = "";

		str += "<td";
		if (!$.nony.isEmpty(this.className)) {str += " class=\""+this.className+"\"";}
		if (!$.nony.isEmpty(this.style)) {str += " style=\""+this.style+"\"";}
		if (!$.nony.isEmpty(this.attribute)) {
			attrArray = this.attribute.split(";");
			for (var i=0; i<attrArray.length; i++) {
				var keyVal = attrArray[i].split(":");
				key = keyVal[0];
				val = keyVal[1];

				str += " "+key+"=\""+val+"\"";
			}
		}
		str += ">";
		if (!$.nony.isEmpty(this.textBeforeChild)) {str += this.textBeforeChild;}
		if (this.childList != null && this.childList.length > 0) {
			for (var i=0; i<this.childList.length; i++) {
				str += this.childList[i].toHtmlString();
			}
		}
		if (!$.nony.isEmpty(this.text)) {str += this.text;}
		if (!$.nony.isEmpty(this.textAfterChild)) {str += this.textAfterChild;}
		str += "</td>";

		return str;
	}
};