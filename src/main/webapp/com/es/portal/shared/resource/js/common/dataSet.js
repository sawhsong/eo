/**
 * declare DataSet
 */
DataSet = Class.create();
DataSet.prototype = {
	initialize : function() {
		this.NameIdx = new Array();
		this.Name = new Array();
		this.Value = new Array();
	},
	toString : function() {
		var str = "";
		for (var i=0; i<this.Name.length; i++) {
			str += this.Name[i];
			if ((i+1) < this.Name.length) {str += ",";}
		}
		str += "\n";
		for (var i=0; i<this.Value.length; i++) {
			var row = this.Value[i];
			for (var j=0; j<row.length; j++) {
				str += row[j];
				if ((j+1) < row.length) {str += ",";}
			}
			str += "\n";
		}

		return str;
	},
	getColumnCnt : function() {return this.Name.length;},
	getRowCnt : function() {return this.Value.length;},
	addName : function(name) {
		if (typeof(name) == "string") {
			this.NameIdx[name] = this.Name.length;
			this.Name.push(name);
		} else if (typeof(name) == "object") {
			for (var i=0; i<name.length; i++) {
				this.NameIdx[name[i]] = this.Name.length;
				this.Name.push(name[i]);
			}
		}
	},
	addRow : function(arg) {
		if (arguments.length == 0) {this.addRow(this.Name.length);}
		else {
			if (nony.isNumber(arg)) {
				var val = new Array();
				for (var i=0; i<arg; i++) {val.push("");}
				this.Value[this.Value.length] = val;
			} else {
				if (typeof(arg) == "string") {
					var val = new Array();
					for (var i=0; i<this.Name.length; i++) {val.push(arg);}
					this.Value[this.Value.length] = val;
				} else if (typeof(arg) == "object") {
					this.Value[this.Value.length] = arg;
				}
			}
		}
	},
	addColumn : function(arg) {
		if (arguments.length == 0) {throw new Error("Invalid Arguments");}
		else if (arguments.length == 1) {
			if (arg == null || arg == "") {throw new Error("Invalid Column Name");}
			else {
				this.addName(arg);
				if (typeof(arg) == "string") {
					for (var i=0; i<this.Value.length; i++) {this.Value[i].push("");}
				} else if (typeof(arg) == "object") {
					for (var i=0; i<this.Value.length; i++) {
						var row = this.Value[i];
						for (var j=0; j<arg.length; j++) {row.push("");}
					}
				}
			}
		} else {
			if (arguments[0] == null || arguments[0] == "") {throw new Error("Invalid Column Name");}
			else {
				this.addName(arguments[0]);
				if (typeof(arguments[1]) == "string") {
					for (var i=0; i<this.Value.length; i++) {this.Value[i].push(arguments[1]);}
				} else if (typeof(arguments[1]) == "object") {
					for (var i=0; i<this.Value.length; i++) {
						var row = this.Value[i];
						for (var j=0; j<arguments[1].length; j++) {row.push((arguments[1])[j]);}
					}
				}
			}
		}
	},
	getName : function(idx) {return this.Name[idx];},
	getNames : function() {return this.Name;},
	getValue : function(row, col) {
		if (nony.isNumber(col)) {return $.nony.nvl($.nony.htmlToString(this.Value[row][col]));}
		else {return $.nony.nvl($.nony.htmlToString(this.Value[row][this.NameIdx[col]]));}
	},
	setValue : function(row, col, val) {
		if (nony.isNumber(col)) {this.Value[row][col] = val;}
		else {this.Value[row][this.NameIdx[col]] = val;}
	},
	getRowIndex : function(col, val) {
		if (nony.isEmpty(val)) {
			return -1;
		} else {
			if (this.Value.length <= 0) {
				return -1;
			} else {
				for (var i=0; i<this.Value.length; i++) {
					if (nony.isNumber(col)) {
						if (this.Value[i][col] == val) {return i;}
					} else {
						if (this.Value[i][this.NameIdx[col]] == val) {return i;}
					}
				}
			}
		}
	}
};