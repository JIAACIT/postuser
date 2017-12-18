package filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.AuthMb;

@WebFilter(filterName="authFilter",urlPatterns="*.xhtml")
public class AuthFilter implements Filter{

	@Inject
	private AuthMb authMb;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servReq = (HttpServletRequest) req;
		HttpServletResponse servResp = (HttpServletResponse) resp;
		
		if(!servReq.getRequestURI().equals("/index.xhtml")
				&& !servReq.getRequestURI().equals("/login.xhtml")
				&& !servReq.getRequestURI().equals("/register.xhtml")
				&& !servReq.getRequestURI().equals("/createUser.xhtml")
				&& !servReq.getRequestURI().startsWith("/javax.faces.resource")
				&& !authMb.isLogged()){
			servResp.sendRedirect("index.xhtml");
		}  else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
