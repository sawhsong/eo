package zebra.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class SecurityFilter extends AbstractAuthenticationProcessingFilter {
	private static final String DEFAULT_FILTER_PROCESSES_URL = "/authenticationFilter";
	private static final String POST = "POST";

	public SecurityFilter() {
		super(DEFAULT_FILTER_PROCESSES_URL);
System.out.println("SecurityFilter.SecurityFilter()");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException, BadCredentialsException {
		Authentication authentication = new UsernamePasswordAuthenticationToken(request.getParameter("loginId").toString(), request.getParameter("password").toString());

		authentication = getAuthenticationManager().authenticate(authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);
System.out.println("SecurityFilter.attemptAuthentication()");
		return authentication;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
System.out.println("request.getContextPath() : "+request.getRequestURI());
System.out.println("SecurityFilter.doFilter()");
		if (request.getMethod().equals(POST)) {
			// If the incoming request is a POST, then we send it up
			// to the AbstractAuthenticationProcessingFilter.
			super.doFilter(request, response, chain);
		} else {
			// If it's a GET, we ignore this request and send it
			// to the next filter in the chain. In this case, that
			// pretty much means the request will hit the /login
			// controller which will process the request to show the
			// login page.
			super.doFilter(request, response, chain);
		}
	}
}