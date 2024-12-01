package com.Project.CongNghePhanMem.Controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.Impl.OrderService;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String dangNhap() {
        return "employee/home";
    }

    @GetMapping("/employee")
    public String home() {
        return "employee" + "/";
    }

    @GetMapping("/orderManagement")
    public String ordermanagement(Model model) {
        model.addAttribute("allOrders", orderService.getAllOrders());
        model.addAttribute("pendingOrders", orderService.getPendingOrders());
        model.addAttribute("confirmedOrders", orderService.getConfirmedOrders());
        model.addAttribute("inDeliveryOrders", orderService.getInDeliveryOrders());
        model.addAttribute("deliveredOrders", orderService.getDeliveredOrders());
        model.addAttribute("cancelledOrders", orderService.getCancelledOrders());
        return "employee/orderManagement";
    }

    // Update the order status based on user selection
    @PostMapping("/updateOrderStatus/{orderID}")
    public String updateOrderStatus(@PathVariable("orderID") int orderId, 
                                    @RequestParam("status") int status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            // Set the status using the constants from the Order class
            switch (status) {
                case 0:
                    order.setStatus(Order.PENDING);
                    break;
                case 1:
                    order.setStatus(Order.CONFIRMED);
                    break;
                case 2:
                    order.setStatus(Order.IN_DELIVERY);
                    break;
                case 3:
                    order.setStatus(Order.DELIVERED);
                    break;
                case 4:
                    order.setStatus(Order.CANCELLED);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid status value");
            }
            orderRepository.save(order);
        }
        return "redirect:/employee/orderManagement"; // Redirect back to the orders management page
    }

    @GetMapping("/customerSupport")
    public String customersupport() {
        return "employee/customerSupport";
    }
}
