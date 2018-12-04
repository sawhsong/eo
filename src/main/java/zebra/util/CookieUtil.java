package zebra.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zebra.crypto.CryptoRun;

public class CookieUtil {
	@SuppressWarnings("unused")
	private static Logger logger = LogManager.getLogger(CookieUtil.class);
	public static String cryptoKey;

	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;
		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					returnCookie = cookies[i];
					break;
				}
			}
		}
		return returnCookie;
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		String value = null;
		if (cookie != null) {
			if ((getCryptoKey() != null) && (!"".equals(getCryptoKey()))) {value = CryptoRun.DecRun(getCryptoKey(), cookie.getValue());}
		}
		return value;
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String domain, int maxAge) {
		String value = cookieValue;
		try {
			if ((getCryptoKey() != null) && (!"".equals(getCryptoKey()))) {
				value = CryptoRun.EncRun(getCryptoKey(), value);
			}

			Cookie cookie = new Cookie(cookieName, value);
			cookie.setDomain(domain);
			cookie.setPath("/");
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (Exception localException) {
		}
	}

	public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response, String domainName, String cookieName) {
		Cookie cookie = getCookie(request, cookieName);
		if (cookie != null) {
			setCookie(request, response, cookieName, "", domainName, 0);
		}
		return true;
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			deleteCookie(request, response, ConfigUtil.getProperty("etc.cookie.domain"), cookies[i].getName());
		}
	}

	public static String getCryptoKey() {
		return ConfigUtil.getProperty("etc.crypto.key");
	}
}