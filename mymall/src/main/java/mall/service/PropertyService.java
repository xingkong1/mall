package mall.service;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.PropertyDAO;
import mall.entity.Category;
import mall.entity.Property;
import mall.util.Page;

import org.springframework.stereotype.Service;

@Service("propertyService")
public class PropertyService {

	@Resource
	PropertyDAO propertyDAO;
	
	public List<Property> getByCategory(int cid,Page<Property> page){
		int total=propertyDAO.getTotal(cid);
		page.setTotal(total);
		return propertyDAO.listBycid(cid, page.getStart(), page.getCount());
	}
	
	public void add(String name,int cid){
		Property property=new Property();
		property.setName(name);
		property.setCategory(new Category(cid));
		propertyDAO.add(property);
	}
	
	public void delete(int id){
		propertyDAO.delete(id);
	}
	
	public Property get(int id){
		return propertyDAO.get(id);
	}
	
	public void update(Property property,int cid){
		property.setCategory(new Category(cid));
		propertyDAO.update(property);
	}
	
}
