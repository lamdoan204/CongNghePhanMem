package com.Project.CongNghePhanMem.Controller.Manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.UserService;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.Impl.ManagerService;
import com.Project.CongNghePhanMem.Service.IManagerService;
import com.Project.CongNghePhanMem.dto.ProductDTO;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/manager/manage-products")
public class ProductsController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    IUserService userService = new UserService();
    
    @Autowired
    IManagerService managerService = new ManagerService();

    // Hiển thị danh sách sản phẩm với phân trang
    @GetMapping
    public String listProducts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "") String keyword,
                               Model model) {
    	
    	 User manager = userService.getUserCurentLogged();
         if (manager == null) {
             throw new RuntimeException("Không tìm thấy người dùng");
         }
         
      // Gọi Service để lấy tên thương hiệu
         String brand = managerService.get_DepartmentName(manager);
         int brandId = managerService.get_DepartmentBrandId(manager);
        Page<ProductDTO> products = productService.searchProductsByBrand(brandId, keyword, page, size);
        
       
        model.addAttribute("brand", brand);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("managerName", manager.getFullName());
        return "manager/productmanagement";
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/detail/{productID}")
    public String viewProductDetail(@PathVariable int productID, Model model) {
        try {
        	User manager = userService.getUserCurentLogged();
            if (manager == null) {
                throw new RuntimeException("Không tìm thấy người dùng");
            }
            
         // Gọi Service để lấy tên thương hiệu
            String brand = managerService.get_DepartmentName(manager);
            
            
            ProductDTO productDTO = productService.getProductDetailById(productID);
            model.addAttribute("product", productDTO);
            model.addAttribute("brand", brand);
            model.addAttribute("managerName", manager.getFullName());
            return "manager/product-detail";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/create")
    public String showCreateForm(Model model) {
    	User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        
     // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        
        model.addAttribute("brand", brand);
        model.addAttribute("managerName", manager.getFullName());
        model.addAttribute("product", productService.getNewProductDTOForManager());
        return "manager/product-add";
    }

    // Lưu sản phẩm
    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDTO productDTO,
                              BindingResult result,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
    	User manager = userService.getUserCurentLogged();
    	// Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        int brandId = managerService.get_DepartmentBrandId(manager);
        if (result.hasErrors()) {
        	result.getAllErrors().forEach(error -> {
                System.out.println(error.getDefaultMessage());  // In lỗi ra log
            });
            return "manager/product-add";
        }
        try {
        	productDTO.setBrandId(brandId);
        	productDTO.setBrandName(brand);
            productService.saveProductWithImage(productDTO, imageFile);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu sản phẩm: " + e.getMessage());
        }
        return "redirect:/manager/manage-products";
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{productID}")
    public String showEditForm(@PathVariable int productID, Model model) {
    	
    	User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        
     // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        
        model.addAttribute("brand", brand);
        model.addAttribute("managerName", manager.getFullName());
    	
        model.addAttribute("product", productService.getProductDetailById(productID));
        return "manager/product-edit";
    }

    // Lưu sản phẩm sau khi chỉnh sửa
    @PostMapping("/save/{productID}")
    public String saveEditedProduct(@PathVariable int productID,
                                    @Valid @ModelAttribute("product") ProductDTO productDTO,
                                    BindingResult result,
                                    @RequestParam("productImage") MultipartFile imageFile,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "manager/product-edit";
        }
        try {
            productService.updateProduct(productID, productDTO, imageFile);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được chỉnh sửa thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi chỉnh sửa sản phẩm: " + e.getMessage());
        }
        return "redirect:/manager/manage-products";
    }

 // Xóa sản phẩm
    @GetMapping("/delete/{productID}")
    public String deleteProduct(@PathVariable int productID, RedirectAttributes redirectAttributes) {
        try {
          
            
            // Nếu sản phẩm tồn tại, xóa chi tiết sản phẩm (product detail)
            productService.deleteProductById(productID);
            
            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
        } catch (RuntimeException e) {
            // Thêm thông báo lỗi nếu có
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/manager/manage-products";
    }


    // Xóa các sản phẩm đã chọn
    @PostMapping("/delete-selected")
    public String deleteSelected(@RequestParam List<Integer> selectedItems, RedirectAttributes redirectAttributes) {
        if (selectedItems != null && !selectedItems.isEmpty()) {
            try {
                productService.deleteProducts(selectedItems);
                redirectAttributes.addFlashAttribute("message", "Đã xóa thành công " + selectedItems.size() + " sản phẩm.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi xóa các sản phẩm: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một sản phẩm để xóa.");
        }
        return "redirect:/manager/manage-products";
    }
}
