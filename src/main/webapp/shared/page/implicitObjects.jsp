<%/************************************************************************************************
* Description
* - 
************************************************************************************************/%>
<%@ include file="/shared/page/incCommon.jsp"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.*, zebra.util.WebUtil, zebra.util.CommonUtil"%>
<%/************************************************************************************************
* HTML
************************************************************************************************/%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<title><mc:msg key="zebra.main.system.title"/></title>
<%/************************************************************************************************
* Stylesheet & Javascript
************************************************************************************************/%>
<%@ include file="/shared/page/incCssJs.jsp"%>
<style type="text/css">
body {
	padding:10px 10px 10px 10px;
	font:normal normal normal 12px/1.3 "Consolas", "Verdana", "Malgun Gothic", "Sans-serif";
}
.messageSections {
	margin:0px 0px 0px 0px;
	padding:0px 0px 0px 0px;
}
.sectionLabel1 {
	margin:0px 0px 0px 0px;
	padding:0px 0px 0px 0px;
	display:inline;
	font-weight:bold;
	font-size:14px;
	color:#5050C7;
	cursor:pointer;
}
.sectionLabel2 {
	margin:0px 0px 0px 0px;
	padding:0px 0px 0px 40px;
	display:inline;
	font-weight:bold;
	font-size:12px;
	color:#008080;
	cursor:pointer;
}
.sectionContents {
	margin:0px 0px 0px 0px;
	padding:0px 0px 0px 80px;
}
.attributeLabel {
	display:inline;
	color:#D14424;
}
.txtareaContents {
	margin:0px 0px 0px 0px;
	padding:3px 3px 3px 3px;
	border:1px solid #ADADAD;
	border-radius:3px;
	width:100%;
	height:200px;
}
.prePrintStackTrace {
	font-family:Consolas;
}
</style>
<script type="text/javascript">
jsconfig.put("noLayoutWindow", true);

$(function() {
	$(document).ready(function() {
		$(".sectionContents").hide();
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
<div id="divErrorPage">
<%/************************************************************************************************
* Real Contents
************************************************************************************************/%>
<div class="messageSections">
	<div class="sectionLabel1">javax.servlet.http.HttpServletRequest</div>
	<div class="messageSections">
		<div class="sectionLabel2">request</div>
		<div class="sectionContents">
			<div class="attributeLabel">request</div> : <%=request%><br/>
			<div class="attributeLabel">request.getAuthType()</div> : <%=request.getAuthType()%><br/>
			<div class="attributeLabel">request.getAuthType()</div> : <%=request.getAuthType()%><br/>
			<div class="attributeLabel">request.getCharacterEncoding()</div> : <%=request.getCharacterEncoding()%><br/>
			<div class="attributeLabel">request.getContentLength()</div> : <%=request.getContentLength()%><br/>
			<div class="attributeLabel">request.getContentType()</div> : <%=request.getContentType()%><br/>
			<div class="attributeLabel">request.getContextPath()</div> : <%=request.getContextPath()%><br/>
			<div class="attributeLabel">request.getLocalAddr()</div> : <%=request.getLocalAddr()%><br/>
			<div class="attributeLabel">request.getLocalName()</div> : <%=request.getLocalName()%><br/>
			<div class="attributeLabel">request.getLocalPort()</div> : <%=request.getLocalPort()%><br/>
			<div class="attributeLabel">request.getMethod()</div> : <%=request.getMethod()%><br/>
			<div class="attributeLabel">request.getPathInfo()</div> : <%=request.getPathInfo()%><br/>
			<div class="attributeLabel">request.getPathTranslated()</div> : <%=request.getPathTranslated()%><br/>
			<div class="attributeLabel">request.getProtocol()</div> : <%=request.getProtocol()%><br/>
			<div class="attributeLabel">request.getQueryString()</div> : <%=request.getQueryString()%><br/>
			<div class="attributeLabel">request.getRemoteAddr()</div> : <%=request.getRemoteAddr()%><br/>
			<div class="attributeLabel">request.getRemoteHost()</div> : <%=request.getRemoteHost()%><br/>
			<div class="attributeLabel">request.getRemotePort()</div> : <%=request.getRemotePort()%><br/>
			<div class="attributeLabel">request.getRemoteUser()</div> : <%=request.getRemoteUser()%><br/>
			<div class="attributeLabel">request.getRequestedSessionId()</div> : <%=request.getRequestedSessionId()%><br/>
			<div class="attributeLabel">request.getRequestURI()</div> : <%=request.getRequestURI()%><br/>
			<div class="attributeLabel">request.getScheme()</div> : <%=request.getScheme()%><br/>
			<div class="attributeLabel">request.getServerName()</div> : <%=request.getServerName()%><br/>
			<div class="attributeLabel">request.getServerPort()</div> : <%=request.getServerPort()%><br/>
			<div class="attributeLabel">request.getServletPath()</div> : <%=request.getServletPath()%><br/>
			<div class="attributeLabel">request.isSecure()</div> : <%=request.isSecure()%><br/>
			<div class="attributeLabel">request.getLocale()</div> : <%=request.getLocale()%><br/>
			<div class="attributeLabel">request.getRequestURL()</div> : <%=request.getRequestURL()%><br/>
			<div class="attributeLabel">request.getUserPrincipal()</div> : <%=request.getUserPrincipal()%><br/>
<%
			for (Enumeration requestHeader = request.getHeaderNames(); requestHeader.hasMoreElements();) {
				String s = (String)requestHeader.nextElement();
				out.println("<div class='attributeLabel'>request.getHeader('"+s+"')</div> : "+request.getHeader(s)+"<br/>");
			}

			for (Enumeration requestParam = request.getParameterNames(); requestParam.hasMoreElements();) {
				String s = (String)requestParam.nextElement();
				out.println("<div class='attributeLabel'>request.getParameter('"+s+"')</div> : "+request.getParameter(s)+"<br/>");
			}

			for (Enumeration requestAttr = request.getAttributeNames(); requestAttr.hasMoreElements();) {
				String s = (String)requestAttr.nextElement();
				out.println("<div class='attributeLabel'>request.getAttribute('"+s+"')</div> : "+request.getAttribute(s)+"<br/>");
			}
%>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">requestScope</div>
		<div class="sectionContents">
			<div class="attributeLabel">requestScope</div> : ${requestScope}<br/>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">header</div>
		<div class="sectionContents">
			<div class="attributeLabel">header</div> : ${header}<br/>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">headerValues</div>
		<div class="sectionContents">
			<div class="attributeLabel">headerValues</div> : ${headerValues}<br/>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">param</div>
		<div class="sectionContents">
			<div class="attributeLabel">param</div> : ${param}<br/>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">paramValues</div>
		<div class="sectionContents">
			<div class="attributeLabel">paramValues</div> : ${paramValues}<br/>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">cookie</div>
		<div class="sectionContents">
			<div class="attributeLabel">cookie</div> : ${cookie}<br/>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.http.HttpServletResponse</div>
	<div class="messageSections">
		<div class="sectionLabel2">response</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>response</div> : "+response+"<br/>");
		out.println("<div class='attributeLabel'>response.getBufferSize()</div> : "+response.getBufferSize()+"<br/>");
		out.println("<div class='attributeLabel'>response.getCharacterEncoding()</div> : "+response.getCharacterEncoding()+"<br/>");
		out.println("<div class='attributeLabel'>response.getContentType()</div> : "+response.getContentType()+"<br/>");
		out.println("<div class='attributeLabel'>response.getStatus()</div> : "+response.getStatus()+"<br/>");
		out.println("<div class='attributeLabel'>response.getLocale()</div> : "+response.getLocale()+"<br/>");
		out.println("<div class='attributeLabel'>response.isCommitted()</div> : "+response.isCommitted()+"<br/>");

		Collection responseHeader = response.getHeaderNames();
		for (Iterator iterator = responseHeader.iterator(); iterator.hasNext();) {
			String s = (String)iterator.next();
			out.println("<div class='attributeLabel'>response.getHeader('"+s+"')</div> : "+response.getHeader(s)+"<br/>");
		}
%>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.http.HttpSession</div>
	<div class="messageSections">
		<div class="sectionLabel2">session</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>session</div> : "+session+"<br/>");
		out.println("<div class='attributeLabel'>session.getCreationTime()</div> : "+CommonUtil.getDateFromLong(session.getCreationTime(), "yyyy-MM-dd HH:mm:ss")+"<br/>");
		out.println("<div class='attributeLabel'>session.getId()</div> : "+session.getId()+"<br/>");
		out.println("<div class='attributeLabel'>session.getLastAccessedTime()</div> : "+CommonUtil.getDateFromLong(session.getLastAccessedTime(), "yyyy-MM-dd HH:mm:ss")+"<br/>");
		out.println("<div class='attributeLabel'>session.getMaxInactiveInterval()</div> : "+session.getMaxInactiveInterval()+"<br/>");
		out.println("<div class='attributeLabel'>session.isNew()</div> : "+session.isNew()+"<br/>");

		for (Enumeration sessionAttr = session.getAttributeNames(); sessionAttr.hasMoreElements();) {
			String s = (String)sessionAttr.nextElement();
			out.println("<div class='attributeLabel'>session.getAttribute["+s+"]</div> : "+session.getAttribute(s)+"<br/>");
		}
%>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">sessionScope</div>
		<div class="sectionContents">
			<div class="attributeLabel">sessionScope</div> : ${sessionScope}<br/>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.jsp.PageContext</div>
	<div class="messageSections">
		<div class="sectionLabel2">pageContext</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>pageContext</div> : "+pageContext+"<br/>");
%>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">pageScope</div>
		<div class="sectionContents">
			<div class="attributeLabel">pageScope</div> : ${pageScope}<br/>
			<c:forEach var="attr" items="${pageScope}" varStatus="status">
				<div class="attributeLabel">${attr.key}</div> : ${attr.value}<br/>
			</c:forEach>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.ServletConfig</div>
	<div class="messageSections">
		<div class="sectionLabel2">config</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>config</div> : "+config+"<br/>");
		out.println("<div class='attributeLabel'>config.getServletName()</div> : "+config.getServletName()+"<br/>");

		Enumeration configParam = config.getInitParameterNames();
		while (configParam.hasMoreElements()) {
			String s = (String)configParam.nextElement();
			out.println("<div class='attributeLabel'>config.getInitParameter["+s+"]</div> : "+config.getInitParameter(s)+"<br/>");
		}
%>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.ServletContext</div>
	<div class="messageSections">
		<div class="sectionLabel2">application</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>application.getContextPath()</div> : "+application.getContextPath()+"<br/>");
		out.println("<div class='attributeLabel'>application.getEffectiveMajorVersion()</div> : "+application.getEffectiveMajorVersion()+"<br/>");
		out.println("<div class='attributeLabel'>application.getEffectiveMinorVersion()</div> : "+application.getEffectiveMinorVersion()+"<br/>");
		out.println("<div class='attributeLabel'>application.getMajorVersion()</div> : "+application.getMajorVersion()+"<br/>");
		out.println("<div class='attributeLabel'>application.getMinorVersion()</div> : "+application.getMinorVersion()+"<br/>");
		out.println("<div class='attributeLabel'>application.getServerInfo()</div> : "+application.getServerInfo()+"<br/>");
		out.println("<div class='attributeLabel'>application.getServletContextName()</div> : "+application.getServletContextName()+"<br/>");

		Enumeration applicationAttr = application.getAttributeNames();
		while (applicationAttr.hasMoreElements()) {
			String s = (String)applicationAttr.nextElement();
			if (s.indexOf("MergedWebXml") != -1 || s.indexOf("jsp_classpath") != -1) {
				out.println("<div class='attributeLabel'>application.getAttribute('"+s+"')</div> : <textarea class='txtareaContents'>"+application.getAttribute(s)+"</textarea><br/>");
			} else {
				out.println("<div class='attributeLabel'>application.getAttribute('"+s+"')</div> : "+application.getAttribute(s)+"<br/>");
			}
		}
%>
		</div>
		<div class="breaker"></div>
		<div class="sectionLabel2">initParam</div>
		<div class="sectionContents">
			<div class="attributeLabel">initParam</div> : ${initParam}<br/>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">java.lang.Throwable</div>
	<div class="messageSections">
		<div class="sectionLabel2">exception</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>exception</div> : "+exception+"<br/>");
		if (exception != null) {
			out.println("<div class='attributeLabel'>exception.getCause()</div> : "+exception.getCause()+"<br/>");
			out.println("<div class='attributeLabel'>exception.getMessage()</div> : "+exception.getMessage()+"<br/>");
			out.println("<div class='attributeLabel'>exception.printStackTrace()</div> : <pre class='prePrintStackTrace'>");
			exception.printStackTrace(new PrintWriter(out));
			out.println("</pre><br/>");
		}
%>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">java.lang.Object</div>
	<div class="messageSections">
		<div class="breaker"></div>
		<div class="sectionLabel2">page</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>page</div> : "+page+"<br/>");
%>
		</div>
	</div>
	<div class="breaker"></div>
	<div class="sectionLabel1">javax.servlet.jsp.JspWriter</div>
	<div class="messageSections">
		<div class="sectionLabel2">out</div>
		<div class="sectionContents">
<%
		out.println("<div class='attributeLabel'>out</div> : "+out+"<br/>");
		out.println("<div class='attributeLabel'>out.getBufferSize()</div> : "+out.getBufferSize()+" bytes<br/>");
		out.println("<div class='attributeLabel'>out.getRemaining()</div> : "+out.getRemaining()+" bytes<br/>");
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