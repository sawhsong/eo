<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCommon.jsp"%>
<%/************************************************************************************************
* Declare objects & variables
************************************************************************************************/%>
<%
%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<link rel="icon" type="image/png" href="<mc:cp key="imgIcon"/>/faviconEO.png">
<title><mc:msg key="main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/com/es/portal/shared/page/incCssJs.jsp"%>
<style type="text/css">
#divDataArea div, #divDataArea li {/*font-family:'Roboto', Helvetica, Arial, sans-serif !important;*/line-height:170%;}
#divDataArea a {color:#f07031;cursor:pointer;}
#divDataArea a:hover, #divDataArea a:visit, {color:#f07031;}

.depth1 {font-size:15px;font-weight:bold;margin-left:20px;}
.depth2 {font-size:13px;font-weight:normal;margin-left:40px;}
ul .depth2 {font-size:13px;font-weight:normal;margin-left:30px;}
.liDepth3 {font-size:13px;font-weight:normal;margin-left:60px;}
.pageHeader {font-size:30px;}
.pageContents {font-size:13px;}
.break {height:15px;}
.marginTop {margin-top:15px;}
.marginLeft {margin-left:20px;}
.bold {font-weight:bold;}
.noListStyle {list-style:none;}
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
<div id="divHeaderHolder" class="ui-layout-north"><%@ include file="/com/es/portal/common/include/headerForServiceResource.jsp"%></div>
<div id="divBodyHolder" class="ui-layout-center">
<div id="divBodyLeft" class="ui-layout-west"></div>
<div id="divBodyCenter" class="ui-layout-center">
<div id="divFixedPanel">
<div id="divLocationPathArea"><%@ include file="/com/es/portal/common/include/bodyLocationPathArea.jsp"%></div>
<%/************************************************************************************************
* Real Contents - fixed panel(tab, button, search, information)
************************************************************************************************/%>
<div id="divTabArea"></div>
<div id="divButtonArea">
	<div id="divButtonAreaLeft"></div>
	<div id="divButtonAreaRight"></div>
</div>
<div id="divAdminToolArea"><%@ include file="/com/es/portal/common/include/bodyAdminToolArea.jsp"%></div>
<div id="divSearchCriteriaArea"></div>
<div id="divInformArea"></div>
<%/************************************************************************************************
* End of fixed panel
************************************************************************************************/%>
<div class="breaker"></div>
</div>
<div id="divScrollablePanel">
<%/************************************************************************************************
* Real Contents - scrollable panel(data, paging)
************************************************************************************************/%>
<div id="divDataArea" class="areaContainerServiceResource">
	<div class="pageHeader marginTop">Terms and conditions</div>
	<div class="pageContents marginTop">Entity Solutions ("ES") maintains Entity Online and Entity Mobile for use by independent contractors, independent professionals and its clients. The terms and conditions set out in this document (<strong>Terms</strong>) apply to all users of Entity Online and Entity Mobile. By using Entity Online and downloading, installing or using Entity Mobile, you (the user of Entity Online/Entity Mobile) accept and agree to these Terms. Please ensure that you read these Terms carefully, especially the disclaimers and limitations of liability in clause 7. If you do not accept any part of these Terms, you must immediately cease using Entity Online/Entity Mobile.</div>
	<div class="pageContents marginTop">ES may change these Terms at any time. When you log in to Entity Online or Entity Mobile, you will be notified of any changes to the Terms since your previous login. Your continued use of Entity Online or Entity Mobile following any notified change to these Terms will be deemed acceptance of those changes.</div>
	<div class="pageContents bold marginTop">Interpretation</div>
	<div class="pageContents marginTop">ES means Entity Solutions Services Pty Ltd (ACN 091 536 364) for engagements in Australia and Entity Solutions (NZ) Ltd (Co 2490922) for engagements in New Zealand.</div>
	<div class="pageContents marginTop">Entity Online means the ES online portal service located at:</div>
	<div class="pageContents marginLeft"><a onclick="window.open('/login/userlogin')">https://portal.entitysolutions.com.au/webcenter/portal/login</a></div>
	<div class="pageContents marginTop">Entity Mobile means the Entity Solutions mobile phone application which will be made available on Apple and Google Play store.</div>
	<div class="break"></div>
	<ol>
		<li class="depth1 marginTop">Use of Entity Online and Entity Mobile
			<ol>
				<li class="depth2">You are responsible for your use and access (or deemed use and access) of Entity Online and Entity Mobile by means of your Login Credentials (as defined in paragraph 2.2 below), including entering into electronic agreements with ES and amending your personal and financial details. By using ES, you are also agreeing to the ES Privacy Policy, as set out at:  <a onclick="window.open('/serviceresource/pages/privacy')">https://portal.entitysolutions.com.au/webcenter/portal/entity/privacy</a></li>
				<li class="depth2">You must not use Entity Online or Entity Mobile for any activities that breach any laws or infringe any person's rights. You must not upload to Entity Online or Entity Mobile any inappropriate or unlawful materials, or information that is misleading or deceptive.</li>
				<li class="depth2">You must not bypass or attempt to bypass any security mechanisms imposed by Entity Online or Entity Mobile.</li>
			</ol>
		</li>
		<li class="depth1 marginTop">Your account and authority
			<ol>
				<li class="depth2">ES will provide you and/or each of your authorised user(s) (Authorised User) with an initial UserID and password prior to your first use of Entity Online. On your first use of Entity Online, you will be required to change your password. You may also be required to provide answers to certain "security questions".</li>
				<li class="depth2">Your UserID, password and answers to the security questions (collectively Login Credentials) will be used by Entity Online and Entity Mobile's logon system to identify you. You must keep the Login Credentials safe and secure, and must not disclose them to anyone else or allow anyone else to use the Login Credentials.</li>
				<li class="depth2">You must ensure that the password that you choose is a "strong password", commensurate with the nature of Entity Online and Entity Mobile and the sensitivity of the information stored on it.</li>
				<li class="depth2">You must immediately notify ES if you know or have reasonable grounds to suspect that your Login Credentials have been compromised, lost or obtained or used by another person. ES may take any action including suspending your access to Entity Online and Entity Mobile at any time without notice if ES suspects or considers that your Login Credentials have been compromised, lost or obtained or used by another person.</li>
				<li class="depth2">You must notify ES in writing, if you need to terminate an Authorised User's access to Entity Online and Entity Mobile.</li>
				<li class="depth2">Except for any unauthorised use by ES' own staff members, you are entirely responsible for any access or use of Entity Online and Entity Mobile by means of your or the Authorised User(s) Login Credentials. In particular any access or use of Entity Online or Entity Mobile by means of your or your Authorised User(s) Login Credentials is deemed to be an authorised access or use of Entity Online or Entity Mobile by you (including all changes made to your personal and financial information stored on Entity Online or Entity Mobile), irrespective of whether or not you in fact accessed or used Entity Online and Entity Mobile. You indemnify Entity Solutions from and against any liability, damage and expense that ES incurs or suffers as a result of any action, inaction or omission on your part in connection with Entity Online and Entity Mobile, or from any use or misuse of your Login Credentials, or the misuse (or deemed misuse) of Entity Online or Entity Mobile by you.</li>
				<li class="depth2">If you have forgotten your password, you may retrieve your password by contacting ES by telephone. Upon request your password will be sent to you securely either to your registered email address or mobile phone by SMS, as nominated by you.</li>
			</ol>
		</li>
		<li class="depth1 marginTop">Your information
			<ol>
				<li class="depth2">You acknowledge that you have separately provided certain information to ES or ES' clients prior to your first use of Entity Online and Entity Mobile (Provided Information). You warrant that all Provided Information is true, complete and accurate. ES may pre-populate Entity Online and Entity Mobile with the Provided Information. While ES will use reasonable endeavours to pre-populate the information accurately, ES makes no representation as to the accuracy of the pre-populated information.</li>
				<li class="depth2">In addition to the Provided Information, Entity Online and Entity Mobile may store various information relating to you (including your name, address and contact details, superannuation, banking and taxation details) (Your Information).</li>
				<li class="depth2">You must verify all of Your Information, whether pre-populated or not, and must ensure that all of the information is true, complete and accurate. If any part of Your Information is incorrect or incomplete:
					<ol>
						<li class="liDepth3">you must complete or correct the information by using the functionality available on Entity Online and Entity Mobile; or</li>
						<li class="liDepth3">if no functionality is available on Entity Online and Entity Mobile to complete or correct the information, you must promptly notify ES.</li>
					</ol>
				</li>
				<li class="depth2">You indemnify ES from and against any liability, damage and expense that ES incurs or suffers as a result of any false, incomplete or inaccurate Information.</li>
			</ol>
		</li>
		<li class="depth1 marginTop">Entity Online and Entity Mobile
			<ol>
				<li class="depth2">Entity Online and Entity Mobile will have such functionality and features as ES determines to provide from time to time. ES may from time to time update or upgrade Entity Online and Entity Mobile without prior consent or notice to you.</li>
				<li class="depth2">Certain functionalities of Entity Online may require your computer system to meet certain prerequisites (such as certain browser configuration). The prerequisites may vary from time to time. You are solely responsible for ensuring that your computer system meets all of the applicable prerequisites.</li>
				<li class="depth2">Entity Mobile is currently available on Android and iOS – the requirements for both systems (and for any additional systems we decide to extend the availability of Entity Mobile to) may change, and you’ll need to download the updates if you want to keep using Entity Mobile. ES does not promise that it will always update Entity Mobile so that it is relevant to you and/or works with the iOS/Android version that you have installed on your device. At some point we may wish to update Entity Mobile. We may also wish to stop providing the Entity Mobile, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using Entity Mobile, and (if needed) delete it from your device.</li>
			</ol>
		</li>
		<li class="depth1 marginTop">Security
			<ul>
				<li class="depth2 noListStyle">You acknowledge that the nature of Internet and electronic means of communication are inherently insecure. While ES will use reasonable endeavours to protect the security of Entity Online and Entity Mobile, ES cannot provide any definitive assurance regarding security of Entity Online and Entity Mobile or the information stored on Entity Online and Entity Mobile. To the maximum extent permitted by law, ES will not be liable in any way in relation to any breach of security or unintended loss or disclosure of information due to unauthorised access to Entity Online and Entity Mobile or its database, or the interception of electronic communication between your computer system and Entity Online and Entity Mobile.</li>
			</ul>
		</li>
		<li class="depth1 marginTop">Electronic transactions and formation of contracts
			<ol>
				<li>Entity Online and Entity Mobile may allow you to enter into agreements, give undertakings or make declarations (collectively Electronic Transactions) with ES or another person (Counterparty).</li>
				<li>You agree and consent that the requirement for a signature of a party for the purposes of entering into an Electronic Transaction is taken to have been met if:
					<ol>
						<li>the Counterparty (through Entity Online or Entity Mobile ) displays the proposed terms of the Electronic Transaction and provides you with a mechanism to indicate your assent to the proposed terms (including by means of an "I accept" type button); and</li>
						<li>you indicate your assent by using the provided mechanism (e.g. by clicking or tapping on the "I accept" button).</li>
					</ol>
				</li>
				<li>You agree and consent that the requirement for a signature of a party for the purposes of varying or extending an Electronic Contract or any other agreement between you and ES (Existing Contract) is taken to have been met if:
					<ol>
						<li>an email is exchanged between ES' authorised email address (noreply@entitysolutions.com.au) and your email address as recorded in Entity Online;</li>
						<li>the content of the email identifies the relevant Electronic Contract or Existing Contract and the applicable variation or extension; and</li>
						<li>the content of the email indicates the approval of both ES and you of the variation or extension.</li>
					</ol>
				</li>
				<li>For the purposes of the Electronic Transactions Act 1999 (Cth) (and equivalent statutes in the States and Territories) for engagements in Australia and Electronic Transactions Act (NZ) for engagements in New Zealand, you agree that the method described in this clause 6 is reliable and appropriate for the purposes of identifying the signing party and to enter into and vary agreements.</li>
			</ol>
		</li>
	</ol>
</div>
<div id="divPagingArea"></div>
<%/************************************************************************************************
* Right & Footer
************************************************************************************************/%>
</div>
</div>
<div id="divBodyRight" class="ui-layout-east"></div>
</div>
<div id="divFooterHolder" class="ui-layout-south"><%@ include file="/com/es/portal/common/include/footer.jsp"%></div>
<%/************************************************************************************************
* Additional Elements
************************************************************************************************/%>
</form>
<%/************************************************************************************************
* Additional Form
************************************************************************************************/%>
<form id="fmHidden" name="fmHidden" method="post" action=""></form>
</body>
</html>