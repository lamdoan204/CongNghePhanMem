package com.Project.CongNghePhanMem.Controller.Manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Service.IProductService;

@Controller
@RequestMapping("/manager/manage-products")
public class ProductController {
    @Autowired
    private IProductService productService;

    // Hiển thị danh sách sản phẩm với phân trang
    @GetMapping
    public String listProducts(@RequestParam(defaultValue = "0") int page, 
                               @RequestParam(defaultValue = "5") int size, 
                               @RequestParam(defaultValue = "") String keyword, 
                               Model model) {
        Page<Product> products = productService.searchProducts(keyword, PageRequest.of(page, size));
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "manager/productmanagement";
    }
 // Hiển thị chi tiết sản phẩm
    @GetMapping("/detail/{productID}")
    public String viewProductDetail(@PathVariable int productID, Model model) {
        try {
            // Lấy thông tin sản phẩm theo ID
            Product product = productService.findProductById(productID);
            
            if (product == null) {
                model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
                return "error"; // Chuyển đến trang lỗi nếu không tìm thấy sản phẩm
            }

            // Gửi thông tin sản phẩm đến giao diện
            model.addAttribute("product", product);
            return "manager/product-detail"; // Giao diện chi tiết sản phẩm
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi xem chi tiết sản phẩm.");
            return "error"; // Trang lỗi nếu có lỗi xảy ra
        }
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "manager/product-add";
    }

    // Lưu sản phẩm
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productService.saveProduct(product);  // Lưu sản phẩm vào cơ sở dữ liệu
            
            // Thêm thông báo thành công vào RedirectAttributes
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm thành công.");
            
            return "redirect:/manager/manage-products";  // Quay lại trang quản lý sản phẩm
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi ra console
            return "error";  // Gửi người dùng đến trang lỗi nếu có lỗi xảy ra
        }
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{productID}")
    public String showEditForm(@PathVariable int productID, Model model) {
        Product product = productService.findProductById(productID);  // Sử dụng productID thay vì id
        model.addAttribute("product", product);
        return "manager/product-edit";
    }
 // Lưu sản phẩm sau khi chỉnh sửa
    @PostMapping("/save/{productID}")
    public String saveEditedProduct(@PathVariable int productID, @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
    	 try {
    	        // Lấy sản phẩm theo ID để cập nhật
    	        Product existingProduct = productService.findProductById(productID);
    	        
    	        if (existingProduct != null) {
    	            // Cập nhật thông tin sản phẩm
    	            existingProduct.setName(product.getName());
    	            existingProduct.setImage(product.getImage());
    	            existingProduct.setPrice(product.getPrice());
    	            existingProduct.setKind(product.getKind());  // Cập nhật loại sản phẩm
    	            existingProduct.setBrandId(product.getBrandId());  // Cập nhật thương hiệu
    	            existingProduct.setDescription(product.getDescription());  // Cập nhật mô tả

    	            // Lưu lại thông tin đã chỉnh sửa
    	            productService.saveProduct(existingProduct);
    	            
    	            // Thêm thông báo thành công vào RedirectAttributes
    	            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được chỉnh sửa thành công.");
    	        }
    	        
    	        return "redirect:/manager/manage-products";  // Quay lại trang quản lý sản phẩm
    	    } catch (Exception e) {
    	        e.printStackTrace();  // In lỗi ra console
    	        return "error";  // Gửi người dùng đến trang lỗi nếu có lỗi xảy ra
    	    }
    }

    @GetMapping("/delete/{productID}")
    public String deleteProduct(@PathVariable int productID, RedirectAttributes redirectAttributes) {
        // Gọi service để xóa sản phẩm
        productService.deleteProduct(productID);
        
        // Thêm thông báo thành công vào RedirectAttributes
        redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        
        // Chuyển hướng lại trang quản lý sản phẩm
        return "redirect:/manager/manage-products";
    }


 // Phương thức xóa các sản phẩm đã chọn
    @PostMapping("/delete-selected")
    public String deleteSelected(@RequestParam List<Integer> selectedItems, RedirectAttributes redirectAttributes) {
        if (selectedItems != null && !selectedItems.isEmpty()) {
            try {
                System.out.println("Đang xóa các sản phẩm với IDs: " + selectedItems);  // Log ID để kiểm tra
                productService.deleteProductsByIds(selectedItems);  // Gọi service để xóa sản phẩm
                
                // Thêm thông báo thành công vào RedirectAttributes
                redirectAttributes.addFlashAttribute("message", "Đã xóa thành công " + selectedItems.size() + " sản phẩm.");
                return "redirect:/manager/manage-products";  // Quay lại trang quản lý sản phẩm
            } catch (Exception e) {
                e.printStackTrace();  // In lỗi ra console
                return "error";  // Gửi người dùng đến trang lỗi nếu có lỗi xảy ra
            }
        }
        // Nếu không có sản phẩm chọn, quay lại trang
        return "redirect:/manager/manage-products";  
    }

}
