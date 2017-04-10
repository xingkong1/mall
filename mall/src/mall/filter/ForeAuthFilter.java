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

import mall.bean.User;

import org.apache.commons.lang.StringUtils;

public class ForeAuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		String contextPath=request.getServletContext().getContextPath();
		String uri=request.getRequestURI();
		uri=StringUtils.remove(uri, contextPath);
		String[] noNeedAuthPage = new String[]{
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
		
		if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
			String method=StringUtils.substringAfter(uri, "/fore");
			if(!Arrays.asList(noNeedAuthPage).contains(method)){
				User user=(User)request.getSession().getAttribute("user");
				if(user==null){
				response.sendRedirect("login.jsp");
				return ;
				}
			
			}
		}
		arg2.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

}
