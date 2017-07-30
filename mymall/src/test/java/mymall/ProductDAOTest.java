package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.ProductDAO;
import mall.entity.Product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class ProductDAOTest {

	@Resource
	ProductDAO productDAO;
	
	@Test
	public void test(){
		//Product product=productDAO.get(150);
		List<Product> products=productDAO.listProducts(0, Short.MAX_VALUE);
		for(Product product:products){
		System.out.println(product);
		System.out.println(product.getCategory());
		System.out.println(product.getFirstProductImage());
		}
	}
}
