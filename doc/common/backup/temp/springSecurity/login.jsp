<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
	ParamEntity pe = (ParamEntity)request.getAttribute("paramEntity");
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconEO.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCssJs.jsp"%>
<style type="text/css">
html, body {height:100%;background:#FFFFFF;}
.form-control {padding:6px 12px;}
.logoImage {margin:0px auto;width:100%;text-align:center;}
.logoImage img {margin-top:10%;}
.loginPanel {margin:0px auto;width:100%;text-align:center;}
.panelLogin {margin-top:10px;display:inline-block;width:360px;border:1px solid #D1D1D1;box-shadow:0px 0px 10px rgba(0, 0, 0, .2);}
.panel-title {padding-top:4px;padding-left:36px;text-align:left;font-size:14px;height:23px;}
.loginBoxtTitle {background:url(<mc:cp key="imgIcon"/>/login.png) no-repeat 0px 0px;}
.panel-body {padding:25px 25px 20px 25px;}
.addonIcon {width:16px;}
.input-group {padding-bottom:8px;}
.buttonDiv {padding-top:16px;padding-bottom:0px;}
.additionalLink {padding-top:20px;font-size:13px;}
.passwordLink {float:left;width:50%;text-align:left;}
.registerLink {float:right;width:50%;text-align:right;}

.divLoginFooter {position:absolute;bottom:0px;width:100%;background:#eeeeee;padding:20px 60px 20px 60px;color:#000000;font-weight:bold;font-size:11px;}
.divLoginFooterLeft {float:left;width:33%;}
.divLoginFooterCenter {float:left;width:34%;}
.divLoginFooterRight {float:right;width:33%;}
.divLoginFooterLeft div {float:left;}
.divLoginFooterRight div {float:right;}

.clickablItems {cursor:pointer;padding:0px 10px 0px 10px;}
.clickablItems a, .clickablItems a:link, .clickablItems a:visited {text-decoration:none;color:#000000;}
.clickablItems a:hover, .clickablItems i:hover {text-decoration:none;color:#000000;transition:background .2s;text-shadow:1px 1px 2px rgba(0, 0, 0, 0.2);}
.clickablItemsBreak {margin:1px 10px;height:12px;font-size:1px;border-left:2px solid #000000;background-color:#000000;}
.clickablItems i {font-weight:bold;font-size:15px;}
</style>
<script type="text/javascript" src="<mc:cp key="viewPageJsName"/>"></script>
<script type="text/javascript">
</script>
</head>
<%/************************************************************************************************
* Page & Header
************************************************************************************************/%>
<body>
<form id="fmDefault" name="fmDefault" method="post" action="">
<div id="divLogo" class="logoImage">
	<ui:img id="imgLogo" src="<mc:cp key=imgIcon/>/logoEntitySolutions.png" style="width:120px;height:66px;" status="display"/>
</div>
<div id="divLoginPanel" class="loginPanel">
	<div class="panel panel-default panelLogin">
		<div class="panel-heading">
			<h3 class="panel-title loginBoxtTitle"><mc:msg key="login.header.main"/></h3>
		</div>
		<div class="panel-body">
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-user fa-lg addonIcon" status="display"/></div>
				<ui:text name="loginId" className="form-control" placeHolder="login.header.loginId" checkName="login.header.loginId" options="mandatory"/>
			</div>
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-lock fa-lg addonIcon" status="display"/></div>
				<ui:password name="password" className="form-control" placeHolder="login.header.password" checkName="login.header.password" options="mandatory"/>
			</div>
			<div class="buttonDiv">
				<ui:button id="btnLogin" type="success" caption="login.button.login" iconClass="fa-key" buttonStyle="padding-top:8px;width:100%;height:40px;font-size:14px;"/>
			</div>
			<div class="additionalLink">
				<div class="passwordLink">
					<ui:anchor id="aResetPassword" caption="login.button.resetPassword"/>
				</div>
				<div class="registerLink">
					<ui:anchor id="aRequestRegister" caption="login.button.requestRegister"/>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="divLoginFooter">
	<div class="divLoginFooterLeft">
		<div class="clickablItems"><a onclick="window.open('http://10.14.35.40:7777/webcenter/portal/entity/disclaimer')">Disclaimer</a></div>
		<div class="clickablItemsBreak"></div>
		<div class="clickablItems"><a onclick="window.open('http://10.14.35.40:7777/webcenter/portal/entity/privacy')">Privacy</a></div>
	</div>
	<div class="divLoginFooterCenter"><div style="width:100%;text-align:center;">&copy; Entity Solutions 2018</div></div>
	<div class="divLoginFooterRight">
		<div class="clickablItems"><ui:icon className="fa-google-plus" status="display" script="window.open('https://plus.google.com/100110685160025735918')"/></div>
		<div class="horGap10"></div>
		<div class="clickablItems"><ui:icon className="fa-linkedin" status="display" script="window.open('https://www.linkedin.com/company/entity-solutions')"/></div>
		<div class="horGap10"></div>
		<div class="clickablItems"><ui:icon className="fa-twitter" status="display" script="window.open('https://twitter.com/entityworld')"/></div>
		<div class="horGap10"></div>
		<div class="clickablItems"><ui:icon className="fa-facebook" status="display" script="window.open('https://www.facebook.com/entitysolutions1')"/></div>
	</div>
</div>
</form>
</body>
</html>