package mall.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.entity.User;

import org.apache.commons.lang.StringUtils;

public class LoginFilter implements Filter {
	
	String[] loginSite;

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest res=(HttpServletRequest)request;
		HttpServletResponse rep=(HttpServletResponse)response;
		String uri=res.getRequestURI();
		String method=StringUtils.substringAfterLast(uri, "/fore");
		if(Arrays.asList(loginSite).contains(method)){
			User user=(User)res.getSession().getAttribute("user");
			if(user==null)
				rep.sendRedirect("/login");
			return ;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String sites=config.getInitParameter("site");
		loginSite=sites.split(",");
	}
       
}
