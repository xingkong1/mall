package mall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.bean.Category;
import mall.bean.Product;
import mall.bean.PropertyValue;
import mall.util.Page;

public class ProductServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response,
			Page page) {
		int cid=Integer.valueOf(request.getParameter("cid"));
		Category category=categoryDAO.get(cid);
		
		String name=request.getParameter("name");
		String subTitle=request.getParameter("subTitle");
		float orignalPrice=Float.valueOf(request.getParameter("orignalPrice"));
		float promotePrice=Float.parseFloat(request.getParameter("promotePrice"));
		int stock=Integer.parseInt(request.getParameter("stock"));
		
		Product product=new Product();
		product.setCategory(category);
		product.setName(name);
		product.setOrignalPrice(orignalPrice);
		product.setPromotePrice(promotePrice);
		product.setSubTitle(subTitle);
		product.setStock(stock);
		
		productDAO.add(product);
		return "@admin_product_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDAO.get(id);
		productDAO.delete(id);
		return "@admin_product_list?cid="+product.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDAO.get(id);
		request.setAttribute("p", product);
		return "/admin/editProduct.jsp";
	}

	@Override
	public String update(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		String name=request.getParameter("name");
		String subTitle=request.getParameter("subTitle");
		float orignalPrice=Float.valueOf(request.getParameter("orignalPrice"));
		float promotePrice=Float.parseFloat(request.getParameter("promotePrice"));
		int stock=Integer.parseInt(request.getParameter("stock"));
		int id=Integer.parseInt(request.getParameter("id"));
		int cid=Integer.parseInt(request.getParameter("cid"));
		Category category=categoryDAO.get(cid);
		Product product=new Product();
		product.setCategory(category);
		product.setName(name);
		product.setOrignalPrice(orignalPrice);
		product.setPromotePrice(promotePrice);
		product.setSubTitle(subTitle);
		product.setStock(stock);
		product.setId(id);
		productDAO.update(product);
		return "@admin_product_list?cid="+cid;
	}

	@Override
	public String list(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int cid=Integer.valueOf(request.getParameter("cid"));
		List<Product> ps=productDAO.list(cid, page.getStart(), page.getCount());
		Category category=categoryDAO.get(cid);
		int total=productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid="+category.getId());
		request.setAttribute("c",category);
		request.setAttribute("ps", ps);
		request.setAttribute("page", page);
		return "/admin/listProduct.jsp";
	}
	
	public String editPropertyValue(HttpServletRequest request,
			HttpServletResponse response, Page page) {
		int id=Integer.parseInt(request.getParameter("id"));
		Product product=productDAO.get(id);
		List<PropertyValue> pvs=propertyValueDAO.list(id);
		request.setAttribute("pvs", pvs);
		request.setAttribute("p", product);
		return "/admin/editProductValue.jsp";
	}
	
	public String updatePropertyValue(HttpServletRequest request,
			HttpServletResponse response, Page page){
		
		int pvid=Integer.parseInt(request.getParameter("pvid"));
		String value=request.getParameter("value");
		PropertyValue pValue=propertyValueDAO.get(pvid);
		pValue.setValue(value);
		propertyValueDAO.update(pValue);
		return "%success";
	}

}
