package mall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 通过这个过滤器进行url路径分析，提取出要跳转的servlet类方法
 * @author xingkong
 *
 */
public class BackServletFilter implements Filter {

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
		 if(uri.startsWith("/admin_")){
			 String servletPath=StringUtils.substringBetween(uri, "_", "_")+"Servlet";
			 String method=StringUtils.substringAfterLast(uri, "_");
			 request.setAttribute("method", method);
			 request.getRequestDispatcher("/"+servletPath).forward(request, response);
			 return;
		 }
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

}
