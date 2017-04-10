
package mall.bean;

import java.util.List;
/**
 * 商品分类实体类
 * @author xingkong
 *
 */
public class Category {

	private String name;
	private int id;
	//种类和商品类是一对多的关系
	List<Product> products;
	//为了前端页面多行展示商品方便
	List<List<Product>> productsByRow;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}

	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}

}

