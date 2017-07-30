package mall.entity;

import java.util.Date;
/**
 * 商品评论实体类
 * @author xingkong
 *
 */
public class Review {
	private String content;
	private Date createDate;
	private User user;
	private Product product;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Review [content=" + content + ", createDate=" + createDate
				+ ", user=" + user + ", product=" + product + ", id=" + id
				+ "]";
	}
	
	
}

