package mall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.Dao.CategoryDAO;
import mall.Dao.OrderDAO;
import mall.Dao.OrderItemDAO;
import mall.Dao.ProductDAO;
import mall.Dao.ProductImageDAO;
import mall.Dao.PropertyDAO;
import mall.Dao.PropertyValueDAO;
import mall.Dao.ReviewDAO;
import mall.Dao.UserDAO;
import mall.util.Page;

public abstract class BaseForeServlet extends HttpServlet {

	protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO=new UserDAO();
    
    /**
     * 进行方法的映射
     */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 获取分页的信息
		 */
		/*
		int start=0;
		int count=10;
		String str=request.getParameter("page.start");
		if(str!=null){
		  start=Integer.parseInt(str);
		}
		String ct=request.getParameter("page.count");
		if(ct!=null){
			count=Integer.parseInt(ct);
		}
		Page page=new Page(start, count);
		*/
		/*
		 * 通过反射调用相应的方法
		 */
		String method=(String)request.getAttribute("method");
		try {
			Method m=this.getClass().getMethod(method, 
				HttpServletRequest.class,HttpServletResponse.class);
			String redirect=m.invoke(this, request,response).toString();
			
			//通过返回的跳转路径进行客户端、服务端跳转
			if(redirect.startsWith("@")){
			  response.sendRedirect(redirect.substring(1));	
			}else if(redirect.startsWith("%")){
			   response.getWriter().print(redirect.substring(1)); 
			}else{
			  request.getRequestDispatcher(redirect.substring(1)).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
}
