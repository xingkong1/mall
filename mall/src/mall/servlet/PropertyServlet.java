package mall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.bean.Category;
import mall.bean.Property;
import mall.util.Page;

public class PropertyServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page) {
		String name=request.getParameter("name");
		int cid=Integer.valueOf(request.getParameter("cid"));
		Category category=categoryDAO.get(cid);
		Property bean=new Property();
		bean.setName(name);
		bean.setCategory(category);
		propertyDAO.add(bean);
		return "@admin_property_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.valueOf(request.getParameter("id"));
		Property property=propertyDAO.get(id);
		propertyDAO.delete(id);
		return "@admin_property_list?cid="+property.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.valueOf(request.getParameter("id"));
		Property property=propertyDAO.get(id);
		request.setAttribute("p", property);
		return "/admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.valueOf(request.getParameter("id"));
		int cid=Integer.valueOf(request.getParameter("cid"));
		String name=request.getParameter("name");
		Category category=categoryDAO.get(cid);
		Property bean=new Property();
		bean.setName(name);
		bean.setId(id);
		bean.setCategory(category);
		propertyDAO.update(bean);
		return "@admin_property_list?cid="+category.getId();
	}

	@Override
	public String list(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		String cid=request.getParameter("cid");
		Category c=categoryDAO.get(Integer.valueOf(cid));
		List<Property> properties=propertyDAO.list(Integer.valueOf(cid),page.getStart(),page.getCount());
		int total=propertyDAO.getTotal(Integer.valueOf(cid));
		page.setTotal(total);
		page.setParam("&cid="+c.getId());
		request.setAttribute("ps", properties);
		request.setAttribute("c", c);
		request.setAttribute("page", page);
		return "/admin/listProperty.jsp";
	}

}
