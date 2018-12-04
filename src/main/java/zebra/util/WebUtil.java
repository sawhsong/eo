package zebra.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(WebUtil.class);

	public static String getServletPath(HttpServletRequest request) {
		return request.getAttribute("javax.servlet.include.servlet_path") == null ? request.getServletPath() : (String)request.getAttribute("javax.servlet.include.servlet_path");
	}

	public static HttpServletRequest getRequest(PageContext page) {
		return (HttpServletRequest)page.getRequest();
	}

	public static HttpServletResponse getResponse(PageContext page) {
		return (HttpServletResponse)page.getResponse();
	}

	public static Object findObject(PageContext page, String scope, String name) {
		Object result = null;
		if ("request".equalsIgnoreCase(scope)) {
			result = page.getAttribute(name, 2);
		} else if ("session".equalsIgnoreCase(scope)) {
			result = page.getAttribute(name, 3);
		} else if ("application".equalsIgnoreCase(scope)) {
			result = page.getAttribute(name, 4);
		}
		return result;
	}

	public static void saveObject(PageContext page, String scope, String name, Object value) {
		if ("request".equalsIgnoreCase(scope)) {
			page.setAttribute(name, value, 2);
		} else if ("session".equalsIgnoreCase(scope)) {
			page.setAttribute(name, value, 3);
		} else if ("application".equalsIgnoreCase(scope)) {
			page.setAttribute(name, value, 4);
		}
	}

	public static String getContextPath(PageContext page) {
		return ((HttpServletRequest)page.getRequest()).getContextPath();
	}

	@SuppressWarnings("rawtypes")
	public static String getSessionMessage(HttpSession session) {
		if (session == null) {
			return "";
		}
		StringBuffer message = new StringBuffer();
		for (Iterator it = getOrderedEnumeration(session.getAttributeNames()).iterator(); it.hasNext();) {
			String name = (String)it.next();
			Object value = session.getAttribute(name);
			message.append(name + "=" + value + "\n");
		}
		return message.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String getRequestMessage(ServletRequest request) {
		StringBuffer message = new StringBuffer();
		String mode = request.getParameter("mode");

		if ((mode != null) && (mode.trim().length() > 0)) {
			message.append("mode=" + mode + "\n");
		}

		for (Iterator it = getOrderedEnumeration(request.getParameterNames()).iterator(); it.hasNext();) {
			String name = (String)it.next();
			if (!"mode".equals(name)) {
				String[] values = request.getParameterValues(name);
				for (int i = 0; i < values.length; i++) {
					message.append(name + "=" + values[i] + "\n");
				}
			}
		}
		return message.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String getRequestAttributeMessage(HttpServletRequest request) {
		StringBuffer message = new StringBuffer();
		for (Iterator it = getOrderedEnumeration(request.getAttributeNames()).iterator(); it.hasNext();) {
			String name = (String)it.next();
			Object value = request.getAttribute(name);
			message.append(name + "=" + value + "\n");
		}
		return message.toString();
	}

	public static String getRequestAttributeMessage(HttpServletRequest request, String attributeName) {
		if (null == request.getAttribute(attributeName)) {
			return "";
		} else {
			return request.getAttribute(attributeName).toString();
		}
	}

	@SuppressWarnings("rawtypes")
	public static String getApplicationAttributeMessage(ServletContext context) {
		StringBuffer message = new StringBuffer();
		for (Iterator it = getOrderedEnumeration(context.getAttributeNames()).iterator(); it.hasNext();) {
			String name = (String)it.next();
			Object value = context.getAttribute(name);
			message.append(name + "=" + value + "\n");
		}
		return message.toString();
	}

	public static String getApplicationAttributeMessage(ServletContext context, String name) {
		StringBuffer message = new StringBuffer();
		Object value = context.getAttribute(name);
		message.append(value + "\n");

		return message.toString();
	}

	@SuppressWarnings("rawtypes")
	public static String getRequestHeaderMessage(HttpServletRequest request) {
		StringBuffer message = new StringBuffer();
		for (Iterator it = getOrderedEnumeration(request.getHeaderNames()).iterator(); it.hasNext();) {
			String name = (String)it.next();
			Object value = request.getHeader(name);
			message.append(name + "=" + value + "\n");
		}
		return message.toString();
	}

	public static String getRequestCookieMessage(HttpServletRequest request) {
		StringBuffer message = new StringBuffer();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; (cookies != null) && (i < cookies.length); i++) {
			Cookie cookie = cookies[i];
			message.append(cookie.getName() + "=" + cookie.getValue() + "\n");
		}
		return message.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List getOrderedEnumeration(Enumeration names) {
		return Collections.list(names);
	}
}