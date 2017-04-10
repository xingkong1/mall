package mall.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import mall.Dao.CategoryDAO;
import mall.Dao.OrderItemDAO;
import mall.bean.Category;
import mall.bean.OrderItem;
import mall.bean.User;

public class ForeServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		String contentPath=request.getServletContext().getContextPath();
		request.getServletContext().setAttribute("contentPath", contentPath);
		
		User user=(User)request.getSession().getAttribute("user");
		int cartTotalItemNumber=0;
		if(user!=null){
		   List<OrderItem> oItems=new OrderItemDAO().listByUser(user.getId());
		   for(OrderItem item:oItems){
			   cartTotalItemNumber+=item.getNumber();
		   }
		}
		request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		List<Category> cs=(List<Category>)request.getAttribute("cs");
		if(cs==null){
			cs=new CategoryDAO().list();
			request.setAttribute("cs", cs);
		}
		
		 String uri=request.getRequestURI();
		uri=StringUtils.remove(uri, contentPath);
		if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")){
			String method=StringUtils.substringAfterLast(uri, "/fore");
			request.setAttribute("method", method);
			request.getRequestDispatcher("/foreServlet").forward(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

}
