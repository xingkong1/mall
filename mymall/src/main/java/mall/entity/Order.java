package mall.entity;

import java.util.Date;
import java.util.List;

/**
 * 订单信息实体类
 * @author xingkong
 *
 */
public class Order {
	private String orderCode;
	private String address;
	private String post;
	private String receiver;
	private String mobile;
	private String userMessage;
	private Date createDate;
	private Date payDate;
	private Date deliveryDate;
	private Date confirmDate;
	private User user;
	private int id;
	//一条订单信息对应着多条订单项
	private List<OrderItem> orderItems;
	private float total;
	private int totalNumber;
	private String status;
	
	
	public String getStatusDesc(){
		String desc ="未知";
		switch(status){
		case "waitPay":
				desc="待付款";
				break;
			case "waitDelivery":
				desc="待发货";
				break;
			case "waitConfirm":
				desc="待收货";
				break;
			case "waitReview":
				desc="等评价";
				break;
			case "finish":
				desc="完成";
				break;
			case "delete":
				desc="刪除";
				break;
			default:
				desc="未知";
		}
		return desc;
	}
	
	public Order(){
		
	}
	
	public Order(int id){
		this.id=id;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}

	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}


	@Override
	public String toString() {
		return "Order [orderCode=" + orderCode + ", address=" + address
				+ ", post=" + post + ", receiver=" + receiver + ", mobile="
				+ mobile + ", userMessage=" + userMessage + ", createDate="
				+ createDate + ", payDate=" + payDate + ", deliveryDate="
				+ deliveryDate + ", confirmDate=" + confirmDate + ", user="
				+ user + ", id=" + id + ", orderItems=" + orderItems
				+ ", total=" + total + ", totalNumber=" + totalNumber
				+ ", status=" + status + "]";
	}
	
	
	
}

