package mymall;

import java.util.List;

import javax.annotation.Resource;

import mall.dao.ProductImageDAO;
import mall.entity.Product;
import mall.entity.ProductImage;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件位置
@ContextConfiguration({"classpath:spring/spring-mybatis.xml"})
public class ProductImageDAOTest {
     
	@Resource
	private ProductImageDAO productImageDAO;
	
	
	
	@Test
    public void getTotal(){
    	int total=productImageDAO.getTotal();
    	System.out.println("total="+total);
    	
    	ApplicationContext atc=new ClassPathXmlApplicationContext("spring.xml");
    	atc.getBean(String.class);
    }
	
	@Test
	public void get(){
		ProductImage productImage=productImageDAO.get(680);
		System.out.println(productImage);
		System.out.println(productImage.getProduct());
		System.out.println(productImage.getProduct().getCategory().getName());
	}
	/*
	@Test
	public void listAll(){
		ProductImage productImage=productImageDAO.get(680);
		Product p=productImage.getProduct();
		List<ProductImage> productImages=productImageDAO.listAll(p, "type_detail");
		for(ProductImage pImage:productImages){
			System.out.println(pImage);
		}
	}
	*/
}
