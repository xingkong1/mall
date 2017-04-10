package mall.comparator;

import java.util.Comparator;

import mall.bean.Product;

public class ProductSaleCountComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		// TODO 自动生成的方法存根
		return o1.getSaleCount()-o2.getSaleCount();
	}

}
