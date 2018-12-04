<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
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
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconPerci.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/shared/page/incCssJs.jsp"%>
<style type="text/css">
.form-control {padding:6px 12px;}
.panelHolder {margin:0px auto;width:100%;text-align:center;}
.panel {margin-top:20px;display:inline-block;width:360px;}
.panel-title {padding-top:4px;padding-left:36px;text-align:left;font-size:14px;height:23px;background:url(<mc:cp key="imgIcon"/>/login.png) no-repeat 0px 0px;}
.panel-body {padding:25px 25px 20px 25px;}
.addonIcon {width:16px;}
.input-group {padding-bottom:4px;}
.buttonDiv {padding-top:18px;padding-bottom:0px;}
.lblCheckEn {margin-top:6px;margin-left:12px;font-size:13px;}
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
<div id="divPopupWindowHolder" class="panelHolder">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-credit-card fa-lg addonIcon" status="display"/></div>
				<ui:text name="userName" className="form-control" placeHolder="login.header.userName" checkName="login.header.userName" options="mandatory"/>
			</div>
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-user fa-lg addonIcon" status="display"/></div>
				<ui:text name="loginId" className="form-control" placeHolder="login.header.loginId" checkName="login.header.loginId" options="mandatory"/>
			</div>
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-lock fa-lg addonIcon" status="display"/></div>
				<ui:password name="password" className="form-control" placeHolder="login.header.password" checkName="login.header.password" options="mandatory"/>
			</div>
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-lock fa-lg addonIcon" status="display"/></div>
				<ui:password id="passwordConfirm" name="passwordConfirm" className="form-control" placeHolder="Confirm Password" checkName="login.header.password" options="mandatory"/>
			</div>
			<div class="input-group">
				<div class="input-group-addon"><ui:icon className="fa-envelope fa-lg addonIcon" status="display"/></div>
				<ui:text name="email" className="form-control" placeHolder="login.header.email" checkName="login.header.email" options="mandatory" option="email"/>
			</div>
			<ui:check name="sendEmail" value="Y" text="login.label.sendMail" displayType="block" isChecked="true"/>
			<div class="buttonDiv">
				<ui:button id="btnRequest" type="primary" caption="login.button.requestRegister" iconClass="fa-bullhorn" buttonStyle="padding-top:8px;width:100%;height:40px;font-size:14px;"/>
			</div>
		</div>
	</div>
</div>
</form>
</body>
</html>