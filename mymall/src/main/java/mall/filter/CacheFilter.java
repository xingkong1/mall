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

public class CacheFilter implements Filter{

	private FilterConfig config;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		this.config=filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		//获得用户想获取的资源
		String uri=req.getRequestURI();
		String ext=uri.substring(uri.lastIndexOf(".")+1);
		String time=config.getInitParameter("ext");
		if(time!=null){
			long t=Long.parseLong(time)*3600*1000;
			res.addDateHeader("Expires", t);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

}
