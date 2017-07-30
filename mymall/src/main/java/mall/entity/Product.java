package mall.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
/**
 * 商品实体类
 * @author xingkong
 *
 */
public class Product {
	@NotNull(message="name can't be empty")
	@Length(min=5, max=20, message="用户名长度必须在5-20之间")  
	private String name;
	@NotNull(message="subTitle can't be empty")
	private String subTitle;
	@NotNull(message="orignalPrice can't be empty")
	@Range(min=1,max=100000,message="orignalPrice must in 1~100000")
	private float orignalPrice;
	@NotNull(message="promotePrice can't be empty")
	private float promotePrice;
	@NotNull(message="stock can't be empty")
	private int stock;
	private Date createDate;
	private Category category;
	private int id;
	private ProductImage firstProductImage;
	private List<ProductImage> productImages;
	private List<ProductImage> productSingleImages;
	private List<ProductImage> productDetailImages;
	private int reviewCount;
	private int saleCount;
	
	public Product(){
		
	}
	
	public Product(int pid){
		this.id=pid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public float getOrignalPrice() {
		return orignalPrice;
	}
	public void setOrignalPrice(float orignalPrice) {
		this.orignalPrice = orignalPrice;
	}
	public float getPromotePrice() {
		return promotePrice;
	}
	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		return name;
	}
	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}
	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	
	
	
}

