package mall.comparator;

import java.util.Comparator;

import mall.bean.Product;

public class ProductPriceComparator implements Comparator<Product>{

	@Override
	public int compare(Product o1, Product o2) {
		// TODO 自动生成的方法存根
		return (int)(o1.getPromotePrice()-o2.getPromotePrice());
	}

}
