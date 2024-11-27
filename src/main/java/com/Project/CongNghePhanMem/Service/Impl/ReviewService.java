package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.Review;
import com.Project.CongNghePhanMem.Repository.ProductRepository;
import com.Project.CongNghePhanMem.Repository.ReviewRepository;
import com.Project.CongNghePhanMem.Service.IReviewService;

@Service  // Đánh dấu lớp này là một Bean Spring
public class ReviewService implements IReviewService{
	
	@Autowired
    private ReviewRepository reviewRepository; // Tiêm ReviewRepository vào
	
	@Autowired
	private ProductRepository productRepository; // Tiêm ProductRepository

	@Override
    public List<Review> getReviewsByProductId(int productId) {
        // Lấy đối tượng Product từ cơ sở dữ liệu dựa trên productId
        Product product = productRepository.findById(productId).orElse(null); // Lấy sản phẩm

        if (product == null) {
            // Nếu không tìm thấy sản phẩm, trả về danh sách rỗng hoặc xử lý theo yêu cầu
            return List.of();
        }

        // Trả về danh sách các đánh giá của sản phẩm
        return reviewRepository.findByProduct(product);
    }
}