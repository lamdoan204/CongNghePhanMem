package com.Project.CongNghePhanMem.Controller.employee;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Service.Impl.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/employee/orders")
public class OrderController {

	
    @Autowired
    private OrderService orderService;

    // Xác nhận đơn hàng
    @PutMapping("/confirm/{orderID}")
    public Order confirmOrder(@PathVariable int orderID) {
        return orderService.confirmOrder(orderID);
    }
//    @PutMapping("/confirm")
//    public void confirmOrder(@RequestBody Order order) {
//    	orderService.confirmOrder(order);
//    }

    // Hủy đơn hàng
    @PutMapping("/cancel/{orderID}")
    public Order cancelOrder(@PathVariable int orderID) {
        return orderService.cancelOrder(orderID);
    }

    // Lấy danh sách đơn hàng theo trạng thái
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable int status) {
        return orderService.getOrdersByStatus(status);
    }
    
    @GetMapping
    public String showOrders(Model model) {
        // Lấy danh sách các hóa đơn theo trạng thái
        model.addAttribute("pendingOrders", orderService.getOrdersByStatus(0));
        model.addAttribute("confirmedOrders", orderService.getOrdersByStatus(1));
        model.addAttribute("cancelledOrders", orderService.getOrdersByStatus(2));

        return "employee/orders"; // Trả về trang orders.jsp
    }
}
