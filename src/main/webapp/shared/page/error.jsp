<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.util.*, java.io.*, zebra.util.WebUtil, zebra.util.CommonUtil"%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Error</title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<script type="text/javascript" src="/shared/resource/js/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/shared/resource/js/angularjs/angular.js"></script>
<style type="text/css">
body {
	padding:10px 10px;
	font:12px/1.2 "Consolas", "Malgun Gothic", "Sans-serif";
}
.errorCodeBox {
	margin:0px auto;
	padding:10px 0px 10px 0px;
	text-align:center;
	font-weight:bold;
	color:#CD0A0A;
	width:200px;
	border:1px solid #CD0A0A;
	border-radius:5px;
	background:-webkit-linear-gradient(top, #FFF7F4 50%, #FEF1EC 20%, #FFF9F7);
	background:-moz-linear-gradient(top, #FFF7F4 50%, #FEF1EC 20%, #FFF9F7);
	background:-ms-linear-gradient(top, #FFF7F4 50%, #FEF1EC 20%, #FFF9F7);
	background:linear-gradient(top, #FFF7F4 50%, #FEF1EC 20%, #FFF9F7);
	box-shadow:1px 1px 2px rgba(0,0,0,.2);
}
.errorIcon {
	margin:7px 0px 0px 14px;
	float:left;
}
.errorTitle {
	line-height:1.5;
	color:#CD0A0A;
}
.buttonBox {
	margin:0px auto;
	padding:10px 0px 10px 0px;
	text-align:center;
	font-weight:bold;
}
.messageSections {
}
.sectionLabel1 {
	display:inline;
	font-weight:bold;
	color:#5050C7;
	cursor:pointer;
}
.sectionLabel2 {
	padding:0px 0px 0px 30px;
	display:inline;
	font-weight:bold;
	color:#008080;
	cursor:pointer;
}
.sectionContents {
	padding:0px 0px 0px 60px;
}
.attributeLabel {
	display:inline;
	color:#D14424;
}
.prePrintStackTrace {
	font-family:"Consolas";
}
.button {
	display:inline-block;
	outline:none;
	cursor:pointer;
	text-align:center;
	text-decoration:none;
	font:12px/100% Arial, Helvetica, sans-serif;
	font-weight:bold;
	padding:.4em .8em;
	text-shadow:0 1px 1px rgba(0,0,0,.3);
	border-radius:.3em;
	box-shadow:0 1px 2px rgba(0,0,0,.2);
}
.button:hover {
	text-decoration:none;
}
.button:active {
	position:relative;
	top:1px;
}
.red {
	color:#ffffff;
	border:solid 1px #980c10;
	background:#d81b21;
	background:-webkit-gradient(linear, left top, left bottom, from(#ed1c24), to(#aa1317));
	background:-moz-linear-gradient(top,  #ed1c24,  #aa1317);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ed1c24', endColorstr='#aa1317');
}
.red:hover {
	background:#b61318;
	background:-webkit-gradient(linear, left top, left bottom, from(#c9151b), to(#a11115));
	background:-moz-linear-gradient(top,  #c9151b,  #a11115);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#c9151b', endColorstr='#a11115');
}
.red:active {
	color:#ffffff;
	background:-webkit-gradient(linear, left top, left bottom, from(#aa1317), to(#ed1c24));
	background:-moz-linear-gradient(top,  #aa1317,  #ed1c24);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#aa1317', endColorstr='#ed1c24');
}
</style>
<script type="text/javascript">
$(function() {
	clickButton = function() {
		history.go(-1);
	};

	$(document).ready(function() {
		$(".sectionContents").hide();
		$(".sectionContents:first").toggle("blind");
	});

	$(document).click(function(event) {
		var className = $(event.target).attr("class");
		if (className == "sectionLabel1" || className == "sectionLabel2") {
			$(event.target).next().toggle("blind");
		}
	});
});
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="errorPage">
<%/************************************************************************************************
* Real Contents
************************************************************************************************/%>
<div class="errorCodeBox">
	<div class="errorIcon"><img src="/shared/resource/image/icon/error.png"/></div>
	<div class="errorTitle">
		Exception occurred!<br/>
		[Error Code : ${requestScope["javax.servlet.error.status_code"]}]
	</div>
</div>
<div class="buttonBox">
	<a class="button red" onclick="clickButton()">Back</a>
</div>
<div style="clear:both;height:5px;"></div>
<div class="messageSections">
	<div class="sectionLabel1">Error Message</div>
	<div class="sectionContents" style="color:#D14424">
		<div>jspException : ${requestScope["javax.servlet.jsp.jspException"]}</div>
		<div>message : ${requestScope["javax.servlet.error.message"]}</div>
	</div>
</div>
<div class="messageSections">
	<div class="sectionLabel1">java.lang.Throwable</div>
	<div class="messageSections">
		<div class="sectionLabel2">Exception</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>Exception</div> : "+exception+"<br/>");
		if (exception != null) {
			out.println("<div class='attributeLabel'>Cause</div> : "+exception.getCause()+"<br/>");
			out.println("<div class='attributeLabel'>Message</div> : "+exception.getMessage()+"<br/>");
			out.println("<div class='attributeLabel'>printStackTrace()</div> : <pre class='prePrintStackTrace'>");
			exception.printStackTrace(new PrintWriter(out));
			out.println("</pre>");
		}
%>
		</div>
	</div>
</div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
</div>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
</body>
</html>