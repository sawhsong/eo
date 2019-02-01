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

.header {font-size:30px;margin-top:15px;}
.reviewDate {font-size:13px;text-align:right;}

.depth1 {font-size:15px;font-weight:bold;margin-top:15px;margin-left:30px;}
.depth2 {font-size:13px;font-weight:normal;margin-top:15px;margin-left:20px;}
.depth2:first-child {margin-top:0px;}
ul>li {list-style:none;}
.depth3 {font-size:13px;font-weight:normal;margin-left:60px;}

.break {height:15px;clear:both;}
.bold {font-weight:bold;}
.alphaList {list-style-type:lower-alpha;}
.dotList {list-style-type:square;}
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
	<div class="header">Privacy Policy - Australia</div>
	<ol>
		<li class="depth1">Introduction
			<ul>
				<li class="depth2">We respect the personal information of individuals (employees, Independent Professionals (IPros) and/or contractors), clients and prospective client's contractors and their right to privacy. Protecting privacy when handling personal information is very important to Entity Solutions and is fundamental to the way the company services everyone.</li>
				<li class="depth2">This Privacy Policy has been developed to outline how Entity Solutions manages personal information. It describes the information that may be collected by Entity Solutions, the choices an individual can make to access and correct their personal information and how Entity Solutions protects and discloses this information.</li>
				<li class="depth2">Personal information is described as information Entity Solutions holds about individuals (employees, IPros and/or contractors), clients and prospective clients contractors from which an identity is either apparent or can be reasonably determined.</li>
				<li class="depth2">Entity Solutions has adopted the 13 Australian Privacy Principles (APPs) contained in the Privacy Act 1988 (Cth). The APPs govern the way in which the company collects, uses, discloses, stores, secures and disposes of personal information. A copy of the APPs may be obtained from the website of The Office of the Australian Information Commissioner at <a onclick="window.open('http://www.oaic.gov.au')">http://www.oaic.gov.au/</a>.</li>
				<li class="depth2">This Privacy Policy applies to online websites operated by Entity Solutions, which is used to1 provide access to information about our services and products; access to 'Entity Online' (online Portal service) and 'Entity Mobile' (mobile phone application) which allows users to provide information and conduct certain transactions using electronic means; and internal information management of everyone.</li>
				<li class="depth2">By visiting the website, Entity Online or Entity Mobile of Entity Solutions or otherwise providing us with your personal information, you are accepting the practices of Entity Solutions with respect to the collection, storage and use of personal information.</li>
			</ul>
		</li>
		<li class="depth1">Privacy collection statement
			<ul>
				<li class="depth2">Personal information is collected in accordance with the Privacy Act 1988 (Cth) and may be sought from you when you:
					<ol>
						<li class="depth3">apply for an available position</li>
						<li class="depth3">are requested during an interview</li>
						<li class="depth3">completing forms</li>
						<li class="depth3">request information from Entity Solutions</li>
						<li class="depth3">download certain documents and information</li>
						<li class="depth3">register for a demonstration of certain products or services</li>
						<li class="depth3">sign up or subscribe to a newsletter; or</li>
						<li class="depth3">various forms of business transactions.</li>
					</ol>
				</li>
				<li class="depth2">Examples of personal information that the company collects, include: names, addresses, email addresses, phone and facsimile numbers, emergency contacts, bank details, superannuation and all information related to remuneration including bonuses and salary sacrificing.</li>
				<li class="depth2">Entity Solutions may also collect statistical information regarding the use of the website or the portal. The information collected includes the visitor's computer Internet Protocol (IP) address, the date and time of the visit, previous site visited and browser type. This information is collected to provide Entity Solutions with information about the use to help maintain and develop to provide better information to users of the website, Entity Online or Entity Mobile.</li>
				<li class="depth2">Entity Solutions' website and Entity Online use cookies for the purposes of session tracking. However, Entity Solutions does not collect any personal information from the cookies, including any habits when accessing the website or Entity Online.</li>
				<li class="depth2">The personal information collected by Entity Solutions is for the primary purpose of providing services, and product information to individuals (employees, IPros and/or contractors), clients and prospective client's contractors. The personal information may also be used for the following purposes:
					<ol>
						<li class="depth3">To discharge any statutory or reporting obligations;</li>
						<li class="depth3">To provide information to you about Entity Solutions' products and/or services;</li>
						<li class="depth3">To contact you and respond to your queries;</li>
						<li class="depth3">To enforce rights and to fulfil obligations under any agreement between Entity Solutions and its clients;</li>
						<li class="depth3">To enforce rights and to fulfil obligations under any agreement between you and Entity Solutions;</li>
						<li class="depth3">To record statistical data for marketing analysis; and</li>
						<li class="depth3">Any other purpose which are permitted under any agreement between you and Entity Solutions.</li>
					</ol>
				</li>
				<li class="depth2">While Entity Solutions will endeavour to collect personal information directly from you, Entity Solutions may collect some of the personal information indirectly from others, such as the clients or prospective clients of Entity Solutions, or your employer who may be located Australia or internationally.</li>
				<li class="depth2">If at any time you provide personal information about another individual to Entity Solutions you must ensure that the individual has read and understood this policy and separately consented to that personal information being used and disclosed by Entity Solutions for the purpose set out in this policy.</li>
			</ul>
		</li>
		<li class="depth1">Disclosure
			<ul>
				<li class="depth2">Entity Solutions recognises the trust with you to provide personal information, and such personal information will not be disclosed within Australia or internationally except as follows:
					<ol>
						<li class="depth3">To the clients of Entity Solutions (as part of an engagement or prospective engagement in which you are involved or likely to be involved);</li>
						<li class="depth3">On a confidential basis to agents and service providers that Entity Solutions uses in the ordinary operation of its business;</li>
						<li class="depth3">Personal Information that is "Sensitive Information" will not be disclosed without your consent;</li>
						<li class="depth3">To third parties where you consent to the use or disclosure; or</li>
						<li class="depth3">Where required or authorised by law.</li>
					</ol>
				</li>
				<li class="depth2">Entity Solutions may also use this information for secondary purposes closely related to the primary purpose, in circumstances where it would be reasonable to expect such use or disclosure.</li>
			</ul>
		</li>
		<li class="depth1">Spam Act
			<ul>
				<li class="depth2">When communicating electronically with individuals (employees, IPros and/or contractors), clients and prospective clients contractors Entity Solutions will follow three key elements in order to comply with the Spam Act 2003:
					<ol>
						<li class="depth3 alphaList">Consent
							<ul>
								<li>The company will only send commercial electronic messages with the addressee's consent - either express or inferred consent.</li>
							</ul>
						</li>
						<li class="depth3 alphaList">Identify
							<ul>
								<li>The company will include clear and accurate information about the person or business that is responsible for sending the commercial electronic message.</li>
							</ul>
						</li>
						<li class="depth3 alphaList">Unsubscribe
							<ul>
								<li>The company will ensure a functional unsubscribe facility is included in all commercial electronic messages and deal with unsubscribe requests promptly. Individuals are able to unsubscribe from mailing/marketing lists at any time; by advising Entity Solutions (see 'Contact the company' details on page 8 Section 12 of this document), or by following the steps outlined in the email communication.</li>
							</ul>
						</li>
					</ol>
				</li>
			</ul>
		</li>
		<li class="depth1">Anonymity
			<ul>
				<li class="depth2">Subject to an agreement to the contrary, you are not required to provide Entity Solutions with your personal information. However, the services and products offered by Entity Solutions rely on such information. If you do not provide your personal information, then Entity Solutions may not be able to engage you or provide services to you.</li>
			</ul>
		</li>
		<li class="depth1">Security of Personal Information
			<ul>
				<li class="depth2">Personal information is stored in a manner that reasonably protects it from misuse, loss, unauthorised access, modification or disclosure. The information is stored on secure servers, at a data warehouse, onsite, or at an eternal archiving facility. Entity Solutions employees are obliged to respect the confidentiality of any personal information held by Entity Solutions.</li>
				<li class="depth2">However, security of communications cannot be guaranteed, and therefore absolute assurance that information will be secure at all times cannot be given. To the extent permitted by law, Entity Solutions does not accept responsibility for events arising from unauthorised access to personal information.</li>
				<li class="depth2">When personal information is no longer needed for the purpose for which it was obtained, the company will take reasonable steps to destroy or permanently de-identify personal information. However, most of the personal information is or will be stored in files which will be kept by the company for a minimum of seven (7) years.</li>
				<li class="depth2 bold">External Service Provider
					<ul class="depth3">
						<li>The websites, Entity Online and Entity Mobile of Entity Solutions and its electronic mail servers may be managed by a third party hosting service provider. When you access the websites, portals or mobile application of Entity Solutions or transmit electronic mail to Entity Solutions, you may be communicating with a third party's server that maybe located outside of Australia.</li>
					</ul>
				</li>
				<li class="depth2 bold">Entity Solutions and links to other websites
					<ul class="depth3">
						<li>The Entity Solutions' website may contain links to other websites. These link sites are not under the control of Entity Solutions. Entity Solutions is not responsible for the information handling practices, conduct or the content of those sites.</li>
					</ul>
				</li>
				<li class="depth2 bold">Unsolicited personal information
					<ul class="depth3">
						<li>If Entity Solutions receives personal information that the company did not solicit the company will as soon as possible but only if lawful to do so, destroy the information or ensure that the information is de-identified.</li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="depth1">Access to Personal Information
			<ul>
				<li class="depth2">Entity Solutions will, upon request, and subject to applicable privacy laws, provide individuals (employees, IPros and/or contractors), clients and prospective client's contractor's access to personal information held by Entity Solutions. If making a request, we ask that the type/s of personal information requested is clearly listed in writing and sent to your relevant Entity Solutions representative or by contacting the Entity Solutions' Data Protection Officer (see 'Contact the company' details on page 9 Section 13 of this document).</li>
				<li class="depth2">The company will deal with the request in a reasonable timeframe and in most instances within 30 days of receipt of a request. In order to protect personal information, Entity Solutions will require identification before releasing such information. Personal information sought by spouses or other family members requires written authority by the individual the information is being sought for prior to making such a request. If the evidence is insufficient Entity Solutions may deny the request.</li>
				<li class="depth2">Entity Solutions will not charge for lodging a request, but may recover reasonable costs incurred in supplying the information.</li>
				<li class="depth2" style="font-style:italic">Exceptions:</li>
				<li class="depth2">The right to access personal information is not absolute. In certain circumstances, the law allows the company to refuse a request for information, such as circumstances where:
					<ol>
						<li class="depth3 dotList">Access would pose a serious threat to the life or health of any individual;</li>
						<li class="depth3 dotList">Access would have an unreasonable impact on the privacy of others;</li>
						<li class="depth3 dotList">The request is frivolous or vexatious;</li>
						<li class="depth3 dotList">The information relates to a commercially sensitive decision making process;</li>
						<li class="depth3 dotList">Access would be unlawful; or</li>
						<li class="depth3 dotList">Access may prejudice enforcement activities, a security function or commercial; negotiations</li>
					</ol>
				</li>
			</ul>
		</li>
		<li class="depth1">Freedom of Information Laws
			<ul>
				<li class="depth2">In addition to Privacy Laws, individuals may have rights to access their personal information contained in certain company documents. Details on how to apply for access to these documents are contained in the Freedom of Information Act 1982 (FOI Act).</li>
			</ul>
		</li>
		<li class="depth1">Quality of Personal Information
			<ul>
				<li class="depth2">It is an important part of providing services to our clients that personal information is up to date. The APP provides guidance that the company takes reasonable steps to make sure that personal information is accurate, complete and up-to-date.</li>
				<li class="depth2">It is important that individuals advise Entity Solutions at the earliest opportunity, of any changes to personal information so that the Entity Solutions' records can be updated. This can be done by contacting your relevant Entity Solutions representative or by contacting Entity Solutions' Data Protection Officer (see 'Contact the company' details on page 8 Section 12 of this document).</li>
			</ul>
		</li>
		<li class="depth1">Consequences of Breach of Privacy and Personal/Sensitive Information
			<ul>
				<li class="depth2">Individuals (employees, IPros and/or contractors) are expected to act a manner consistent with the matters set out in this policy where they have responsibility for personal information. Failures to do so may be subject to disciplinary action, and may result in warning letter, demotion or termination depending on the level or seriousness of the breach.</li>
				<li class="depth2">If anyone suspects or is aware of a breach of privacy, you have an obligation to report this breach to the Entity Solutions' Data Protection Officer so immediate action can be taken to eradicate the situation. Contact details of the Privacy Officer are provided in Section 13 of this document.</li>
			</ul>
		</li>
		<li class="depth1">Privacy/Data Protection Obligations
			<ul>
				<li class="depth2">Individuals (employees, IPros and/or contractors) are expected to act a manner consistent with the matters set out in this policy, where they have responsibility for personal information.</li>
				<li class="depth2">If an Individual suspects or becomes aware of a breach, they also have an obligation to report this immediately to Entity Solutions' Privacy/Data Protection Officer. Failure to comply will be subject to disciplinary action, and may result in warning letter, demotion or termination depending on the level or seriousness of the breach.</li>
			</ul>
		</li>
		<li class="depth1">Procedure
			<ul>
				<li class="depth2">If Individuals (employees and/or contractors) become aware of a breach of personal data, they have an obligation to report this breach to the ES Privacy/Data Protection Officer immediately.</li>
				<li class="depth2">An Individual can also make a complaint if they feel their personal data has been handled inappropriately by ES and is in breach of its obligations under the Act. In the first instance, any concern or complaints must be directed to ES Data Protection Officer in writing.</li>
				<li class="depth2">ES will investigate the complaint and/or concern and take necessary steps to rectify the matter.</li>
				<li class="depth2">Please contact the ES Data Protection Officer via the following means:
					<ul>
						<li class="depth3">Emailing: <a href="mailto:compliance@entitysolutions.com.au">compliance@entitysolutions.com.au</a> or</li>
						<li class="depth3">Calling: +61 (03) 9600-0333; or</li>
					</ul>
					<div class="break"></div>
					<ul>
						<li class="depth3">Writing:</li>
						<li class="depth3">Legal &amp; Compliance</li>
						<li class="depth3">Entity Solutions Group</li>
						<li class="depth3">Level 24, 150 Lonsdale Street</li>
						<li class="depth3">Melbourne VIC 3000</li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="depth1">Policy Updates
			<ul>
				<li class="depth2">This Privacy Policy may change from time to time. Entity Solutions' Privacy Policy is available at the Entity Solutions offices, intranet, portal or website. Entity Solutions reserves the right to make changes to this Privacy Policy from time to time as business or technical needs require.</li>
			</ul>
		</li>
	</ol>
	<div class="break"></div>
	<div class="break"></div>
	<div class="header">Privacy Policy - New Zealand</div>
	<ol>
		<li class="depth1">Introduction
			<ul>
				<li class="depth2">We respect the personal information of employees, IPros and/or contractors and their right to privacy. Protecting privacy when handling personal information is very important to Entity Solutions and is fundamental to the way Entity Solutions services its employees IPros, and/or contractors. This Policy has been developed to outline how Entity Solutions manages personal information.</li>
				<li class="depth2">This Privacy Policy applies to the management of staff, services and products offered online and websites operated by Entity Solutions. Entity Solutions also uses the internet to provide access to 'Entity Online' (online Portal service) and 'Entity Mobile' (mobile phone application) to allow users to provide information and conduct certain transactions using electronic means.</li>
				<li class="depth2">Entity Solutions has adopted the 12 Privacy Principles (PPs) contained in the Privacy Act 1993 The PPs govern the way in which Entity Solutions collects, uses, discloses, stores, secures and disposes of personal information.</li>
				<li class="depth2">A copy of the PPs may be obtained from the website of the Office of the Privacy Commissioner at <a onclick="window.open('www.privacy.org.nz')">www.privacy.org.nz</a></li>
				<li class="depth2">By visiting the website or portals of Entity Solutions or otherwise providing us with your personal information, you are accepting the practices of, Entity Solutions with respect to the collection, storage and use of personal information.</li>
			</ul>
		</li>
		<li class="depth1">What personal information and how is it collected?
			<ul>
				<li class="depth2">Personal information means information Entity Solutions holds about an identifiable individual. Examples of personal information that Entity Solutions collects, include: names, addresses, email addresses, phone, facsimile numbers, emergency contacts, bank details, superannuation and all information related to remuneration including bonuses and salary sacrificing.</li>
				<li class="depth2">This information may be sought from you when you:
					<ol>
						<li class="depth3">Attend interviews;</li>
						<li class="depth3">Complete forms;</li>
						<li class="depth3">Request Information from Entity Solutions;</li>
						<li class="depth3">Download certain documents and information;</li>
						<li class="depth3">Register for a demonstration of certain products and services; and</li>
						<li class="depth3">Sign up or subscribe to a newsletter.</li>
					</ol>
				</li>
				<li class="depth2">While Entity Solutions will endeavour to collect personal information directly from you, Entity Solutions may collect some of the personal information indirectly from others, such as the clients or prospective clients of Entity Solutions, or your employer.</li>
				<li class="depth2">If at any time you provide personal information about another individual to Entity Solutions then you must ensure that the individual has read and understood this policy and separately consented to that personal information being used and disclosed by Entity Solutions for the purpose set out in this policy.</li>
				<li class="depth2 bold">Website, Entity Online and Entity Mobile</li>
				<li class="depth2">Entity Solutions may collect statistical information regarding the use of the website Entity Online and Entity Mobile. The information collected includes the visitor's computer Internet Protocol (IP) address, the date and time of the visit, previous site visited and browser type. This information is collected to provide Entity Solutions with information about the use, to help it maintain and develop the website Entity Online and Entity Mobile to provide better information to users.</li>
				<li class="depth2">Entity Solutions' website Entity Online and Entity Mobile use cookies for the purposes of session tracking. However, Entity Solutions does not collect any personal information from the cookies, including any habits when accessing the website, portal or Entity Online.</li>
			</ul>
		</li>
		<li class="depth1">Use and disclosure of personal information
			<ul>
				<li class="depth2">Entity Solutions collects personal information for the primary purpose of providing services, and in managing Employees, and IPros and or contractors. This includes the following:
					<ol>
						<li class="depth3">To discharge any statutory or reporting obligations;</li>
						<li class="depth3">To provide information to you about Entity Solutions' products and /or services;</li>
						<li class="depth3">To contact you and respond to your queries;</li>
						<li class="depth3">To enforce rights and to fulfil obligations under any agreement between Entity Solutions and its clients;</li>
						<li class="depth3">To enforce rights and to fulfil obligations under any agreement between you and Entity Solutions;</li>
						<li class="depth3">To record statistical data for marketing analysis; and</li>
						<li class="depth3">Any other purpose which are permitted under any agreement between you and Entity Solutions.</li>
					</ol>
				</li>
				<li class="depth2">Entity Solutions recognises the trust with which you and provide personal information, and such personal information will not be disclosed except as follows:
					<ol>
						<li class="depth3">To the clients of Entity Solutions (as part of an engagement or prospective engagement in which you are involved or likely to be involved);</li>
						<li class="depth3">On a confidential basis to agents and service providers that Entity Solutions uses in the ordinary operation of its business;</li>
						<li class="depth3">To third parties where you consent to the use or disclosure; and</li>
						<li class="depth3">Where required or authorised by law.</li>
					</ol>
				</li>
				<li class="depth2">Entity Solutions may also use this information for secondary purposes closely related to the primary purpose, in circumstances where it would be reasonable to expect such use or disclosure.</li>
				<li class="depth2">Employees, IPros, and/or contractors are able to unsubscribe from mailing/marketing lists at any time; by advising Entity Solutions (see "Contact Entity Solutions" details in Part 12 of this document), or by following the steps outlined in the communication.</li>
			</ul>
		</li>
		<li class="depth1">Unsolicited Electronic Messages Act 2007
			<ul>
				<li class="depth2">When communicating electronically with Employees, IPros and/or contractors, Entity Solutions will follow three key elements in order to comply with the Unsolicited Electronic Messages Act 2007:
					<ol>
						<li class="depth3 alphaList">Consent
							<ul>
								<li>Entity Solutions will only send commercial electronic messages with the addressee's consent. Consent may be either express or inferred consent.</li>
							</ul>
						</li>
						<li class="depth3 alphaList">Identify
							<ul>
								<li>Entity Solutions will include clear and accurate information about the person or business that is responsible for sending the commercial electronic message.</li>
							</ul>
						</li>
						<li class="depth3 alphaList">Unsubscribe
							<ul>
								<li>Entity Solutions will ensure a functional unsubscribe facility is included in all commercial electronic messages, and deal with unsubscribe requests promptly.</li>
							</ul>
						</li>
					</ol>
				</li>
			</ul>
		</li>
		<li class="depth1">Anonymity
			<ul>
				<li class="depth2">Subject to an agreement to the contrary, you are not required to provide Entity Solutions with your personal information. However, the services and products offered by Entity Solutions rely on such information. If you do not provide your personal information, then Entity Solutions may not be able to engage you or provide services to you.</li>
			</ul>
		</li>
		<li class="depth1">Security of Personal Information
			<ul>
				<li class="depth2">Personal Information is stored in a manner that reasonably protects it from misuse and loss and from unauthorised access, modification or disclosure. The information is stored on secure servers. Entity Solutions employees are obliged to respect the confidentiality of any personal information held by Entity Solutions.</li>
				<li class="depth2">However, security of communications cannot be guaranteed, and therefore absolute assurance that information will be secure at all times cannot be given. To the extent permitted by law, Entity Solutions does not accept responsibility for events arising from unauthorised access to personal information.</li>
				<li class="depth2">When Personal Information is no longer needed for the purpose for which it was obtained, Entity Solutions will take reasonable steps to destroy or permanently de-identify Personal Information. However, most of the Personal Information is or will be stored in files which will be kept by Entity Solutions for a minimum of seven (7) years.
				<li class="depth2 bold">External Service Provider
					<ul class="depth3">
						<li>The websites and portals of Entity Solutions and its electronic mail servers may be managed by a third party hosting service provider. When you access the websites or portals of Entity Solutions or transmit electronic mail to Entity Solutions, you may be communicating with a third party's server that maybe located outside of New Zealand or Australia. Entity Solutions and links to other websites.</li>
						<li>The Entity Solutions website may contain links to other websites. These link sites are not under the control of Entity Solutions. Entity Solutions is not responsible for the information handling practices, conduct or the content of those sites. Entity Solutions is not responsible for the information handling practices, conduct or the content of those sites.</li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="depth1">Access to Personal Information
			<ul>
				<li class="depth2">Entity Solutions will, upon request, and subject to applicable privacy laws, provide employees IPros and/or contractors access to personal information held by Entity Solutions. If making a request, we ask that the type/s of information requested is clearly listed.If information is urgently required a request should state this together with the reasons for urgency.</li>
				<li class="depth2">Entity Solutions will deal with the request in a reasonable timeframe, and in most instances within 20 working days of receipt of a request. In order to protect personal information, Entity Solutions may require identification before releasing such information.</li>
				<li class="depth2">Entity Solutions will not charge for lodging a request, but may recover reasonable costs incurred in supplying the information.</li>
				<li class="depth2" style="font-style:italic">Exceptions:</li>
				<li class="depth2">The right to access personal information is not absolute. In certain circumstances, the law allows Entity Solutions to refuse a request for information, such as circumstances where:
					<ol>
						<li class="depth3 dotList">Access would pose a serious threat to the life or health of any individual;</li>
						<li class="depth3 dotList">Access would have an unreasonable impact on the privacy of others;</li>
						<li class="depth3 dotList">The request is frivolous or vexatious;</li>
						<li class="depth3 dotList">The information relates to a commercially sensitive decision making process;</li>
						<li class="depth3 dotList">Access would be unlawful; or</li>
						<li class="depth3 dotList">Access may prejudice enforcement activities, a security function or commercial; negotiations</li>
					</ol>
				</li>
			</ul>
		</li>
		<li class="depth1">Maintaining the quality of personal information
			<ul>
				<li class="depth2">It is an important part of providing services to our employees, IPros and/or contractors, that Personal Information is up to date.</li>
				<li class="depth2">It is important that Employees and IPros advise Entity Solutions at the earliest opportunity, of any changes to Personal Information so that Entity Solutions' records can be updated.</li>
			</ul>
		</li>
		<li class="depth1">Consequences of breach of privacy and personal/sensitive information
			<ul>
				<li class="depth2">Employees, IPro's and/or contractors are expected to act a manner consistent with the matters set out in this policy where they have responsibility for personal information. Failures to do so may be subject to disciplinary action, and may result in termination depending on the level or seriousness of the breach.</li>
				<li class="depth2">If anyone suspects or is aware of a breach of Security/Privacy information, you have an obligation to report this breach to the Data Protection Officer so immediate action can be taken to eradicate the situation. Contact details of the Data Protection Officer are provided in Section 11 on page 15 of this document.</li>
			</ul>
		</li>
		<li class="depth1">Privacy/Data Protection Obligations
			<ul>
				<li class="depth2">Individuals (employees, IPros and/or contractors) are expected to act a manner consistent with the matters set out in this policy, where they have responsibility for personal information.</li>
				<li class="depth2">If an Individual suspects or becomes aware of a breach, they also have an obligation to report this immediately to Entity Solutions' Privacy/Data Protection Officer. Failure to comply will be subject to disciplinary action, and may result in warning letter, demotion or termination depending on the level or seriousness of the breach.</li>
			</ul>
		</li>
		<li class="depth1">Procedure
			<ul>
				<li class="depth2">If Individuals (employees and/or contractors) become aware of a breach of personal data, they have an obligation to report this breach to the ES Privacy/Data Protection Officer immediately.</li>
				<li class="depth2">An Individual can also make a complaint if they feel their personal data has been handled inappropriately by ES and is in breach of its obligations under the Act. In the first instance, any concern or complaints must be directed to ES Data Protection Officer in writing.</li>
				<li class="depth2">ES will investigate the complaint and/or concern and take necessary steps to rectify the matter.</li>
				<li class="depth2">Please contact the ES Data Protection Officer via the following means:
					<ul>
						<li class="depth3">Emailing: <a href="mailto:compliance@entitysolutions.com.au">compliance@entitysolutions.com.au</a> or</li>
						<li class="depth3">Calling: +61 (03) 9600-0333; or</li>
					</ul>
					<div class="break"></div>
					<ul>
						<li class="depth3">Writing:</li>
						<li class="depth3">Legal &amp; Compliance</li>
						<li class="depth3">Entity Solutions Group</li>
						<li class="depth3">Level 24, 150 Lonsdale Street</li>
						<li class="depth3">Melbourne VIC 3000</li>
					</ul>
				</li>
			</ul>
		</li>
		<li class="depth1">Policy Updates
			<ul>
				<li class="depth2">This Privacy Policy may change from time to time. Entity Solutions' Privacy Policy is available at the Entity Solutions offices or on the Entity Solutions Intranet. Entity Solutions reserves the right to make changes to this privacy policy from time to time as business or technical needs require. The amended policy will be posted on Entity Solutions' intranet.</li>
			</ul>
		</li>
	</ol>
	<div class="break"></div>
	<div class="reviewDate bold">Reviewed Date: 01.06.2018</div>
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