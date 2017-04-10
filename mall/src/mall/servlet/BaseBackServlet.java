package mall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
/**
 * 业务servlet类的父类，通过反射调用需要的方法
 * @author xingkong
 *
 */
public abstract class BaseBackServlet extends HttpServlet {

	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;
     
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
		int start=0;
		int count=5;
		String str=request.getParameter("page.start");
		if(str!=null){
		  start=Integer.parseInt(str);
		}
		String ct=request.getParameter("page.count");
		if(ct!=null){
			count=Integer.parseInt(ct);
		}
		Page page=new Page(start, count);
		
		/*
		 * 通过反射调用相应的方法
		 */
		String method=(String)request.getAttribute("method");
		try {
			Method m=this.getClass().getMethod(method, 
				HttpServletRequest.class,HttpServletResponse.class,Page.class);
			String redirect=m.invoke(this, request,response,page).toString();
			
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

	/**
	 * 对上传文件的解析
	 * @param request
	 * @param params
	 * @return
	 */
	 public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
         InputStream is =null;
         try {
             DiskFileItemFactory factory = new DiskFileItemFactory();
             ServletFileUpload upload = new ServletFileUpload(factory);
             // 设置上传文件的大小限制为10M
             factory.setSizeThreshold(1024 * 10240);
             List items = upload.parseRequest(request);
             Iterator iter = items.iterator();
             while (iter.hasNext()) {
                 FileItem item = (FileItem) iter.next();
                 if (!item.isFormField()) {
                     // item.getInputStream() 获取上传文件的输入流
                     is = item.getInputStream();
                 } else {
                     String paramName = item.getFieldName();
                     String paramValue = item.getString();
                     paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                     params.put(paramName, paramValue);
                 }
             }
               
         } catch (Exception e) {
             e.printStackTrace();
         }
         return is;
     }
}
