package mall.comparator;

import java.util.Comparator;

import mall.bean.Product;

public class ProductAllComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		return o1.getSaleCount()*o1.getReviewCount()-o2.getSaleCount()*o2.getReviewCount();
	}

}
