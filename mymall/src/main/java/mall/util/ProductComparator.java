package mall.util;

import java.util.Comparator;

import mall.entity.Product;
/**
 * 产品排序比较器
 * @author xingkong
 *
 */
public final class ProductComparator {
     public static Comparator<Product> getComparator(String sort){
    	 Comparator<Product> comparator=null;
    	 switch (sort) {
		case "review"://根据回复数量进行排序，将回复数多的放在前面
			comparator=new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					// TODO 自动生成的方法存根
					return o1.getReviewCount()-o2.getReviewCount();
				}
			};
			break;
			
	   case "date"://根据创建日期进行排序，把创建时间晚的放在前面
		   comparator=new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				// TODO 自动生成的方法存根
				return o1.getCreateDate().compareTo(o2.getCreateDate());
			}
		    };
		   break;
		   
	   case "saleCount"://根据销售数量进行排序，把销量高的放在前面
		 comparator=new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				// TODO 自动生成的方法存根
				return o1.getSaleCount()-o2.getSaleCount();
			}
		};
         break;
	   case "price"://根據價格排序，將價格高排在前面
		comparator=new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				// TODO 自动生成的方法存根
				return (int)(o1.getPromotePrice()-o2.getPromotePrice());
			}
		};
		break;
		
	   case "all"://默認綜合排序，根據銷售量乘以留言數多的排在前面
			comparator=new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					// TODO 自动生成的方法存根
					return o1.getSaleCount()*o1.getReviewCount()-o2.getSaleCount()*o2.getReviewCount();
				}
			};
			break;
			
	   default ://默認綜合排序，根據銷售量乘以留言數多的排在前面
			comparator=new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					// TODO 自动生成的方法存根
					return o1.getSaleCount()*o1.getReviewCount()-o2.getSaleCount()*o2.getReviewCount();
				}
			};
			break;
		}
    	 
    	 return comparator;
     }
}
