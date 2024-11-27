package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.Review;

public interface IReviewService {
	
	 List<Review> getReviewsByProductId(int productId);
}
