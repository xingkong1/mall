package mall.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import mall.dao.ReviewDAO;
import mall.entity.Review;

@Service
public class ReviewService {

	@Resource
	ReviewDAO reviewDAO;
	
	public void add(Review bean){
		
	};

	public void delete(int id){
		
	};

	public void update(Review bean){
		
	};

	public Review get(int id){;
	return null;
	}

	public List<Review> listByProduct(int pid){
		return reviewDAO.listByProduct(pid, 0, 5);
	};

	public List<Review> listByUser(int uid, int start, int count){
		return null;
	};
	
	public int getTotal(int pid){
		return 0;
	};
}
