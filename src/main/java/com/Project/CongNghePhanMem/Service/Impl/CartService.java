package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.CartDetailRepository;
import com.Project.CongNghePhanMem.Repository.CartRepository;

@Service
public class CartService {

	@Autowired
    private CartDetailRepository cartDetailRepository;

    // Giảm số lượng sản phẩm
    public void decreaseQuantity(int cartDetailId) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("CartDetail not found"));
        
        if (cartDetail.getQuantity() > 1) { // Đảm bảo không giảm dưới 1
            cartDetail.setQuantity(cartDetail.getQuantity() - 1);
            cartDetailRepository.save(cartDetail);
        }
    }

    // Tăng số lượng sản phẩm
    public void increaseQuantity(int cartDetailId) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("CartDetail not found"));
        
        cartDetail.setQuantity(cartDetail.getQuantity() + 1);
        cartDetailRepository.save(cartDetail);
    }
}

