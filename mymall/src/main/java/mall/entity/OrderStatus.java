package mall.entity;

public enum OrderStatus {
    WAIT_PAY("waitPay") , WAIT_DELIVERY("waitDelivery") , 
    WAIT_CONFIRM("waitConfirm") , WAIT_REVIEW("waitReview") ,
    FINISH("finish") , DELETE("delete");
    
    private  String state;
    
    private OrderStatus(String state){
    	this.state=state;
    }
    
    public String toString(){
    	return state;
    }
    
}
