package com.Project.CongNghePhanMem.Controller.Manager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.BrandRepository;
import com.Project.CongNghePhanMem.Repository.DepartmentRepository;
import com.Project.CongNghePhanMem.Service.IManagerService;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.ManagerService;
import com.Project.CongNghePhanMem.Service.Impl.UserService;

@Controller
@RequestMapping("/manager/manage-products")
public class ProductsController {
    @Autowired
    private IProductService productService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    IManagerService managerService = new ManagerService();
    @Autowired
    IUserService userService = new UserService();

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
        
     // Lấy danh sách sản phẩm theo brandId của quản trị viên
        Page<Product> products = productService.searchProductsByBrand(brandId ,keyword, PageRequest.of(page, size));
        
        model.addAttribute("managerBrandId",brandId);
        model.addAttribute("brand", brand);
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

             User manager = userService.getUserCurentLogged();
         	// Gọi Service để lấy tên thương hiệu
             String brand = managerService.get_DepartmentName(manager);

             // Gửi thông tin sản phẩm và thương hiệu đến giao diện
             model.addAttribute("product", product);
             model.addAttribute("brand", brand);
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
    	User manager = userService.getUserCurentLogged();
    	// Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        int brandId = managerService.get_DepartmentBrandId(manager);

        model.addAttribute("brandId", brandId);
        model.addAttribute("brand", brand);
        model.addAttribute("product", new Product());

        return "manager/product-add";
    }

    // Lưu sản phẩm
    @PostMapping("/save")
    public String saveProduct(
        @ModelAttribute("product") Product product,
        @RequestParam("imageFile") MultipartFile imageFile,
        RedirectAttributes redirectAttributes) {
    	User manager = userService.getUserCurentLogged();
    	// Gọi Service để lấy tên thương hiệu
       int brandId = managerService.get_DepartmentBrandId(manager);

    	try {
	        if (imageFile != null && !imageFile.isEmpty()) {
	            // Upload ảnh
	            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
	            
	            // Sử dụng đường dẫn tuyệt đối
	            String uploadDir = "src/mainresources/static/images/products";
	            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
	            
	            // Log để debug
	            System.out.println("Upload Path: " + uploadPath);
	            System.out.println("File Name: " + fileName);
	            
	            // Tạo thư mục nếu không tồn tại
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	                System.out.println("Directory created: " + uploadPath);
	            }
	            
	            // Copy file với full path
	            Path targetLocation = uploadPath.resolve(fileName);
	            System.out.println("Target Location: " + targetLocation);
	            
	            Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("File saved successfully");
	            
	            // Lưu tên file vào entity
	            product.setImage(fileName);
	            product.setBrandId(brandId);
	        }

            // Lưu product vào database (sử dụng repository)
            productService.saveProduct(product);

            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được lưu thành công!");
            return "redirect:/manager/manage-products";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu sản phẩm: " + e.getMessage());
            return "redirect:/manager/product-add";
        }
    }

    // Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{productID}")
    public String showEditForm(@PathVariable int productID, Model model) {
    	User manager = userService.getUserCurentLogged();
    	// Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        
    	Product product = productService.findProductById(productID);  // Sử dụng productID thay vì id
    	model.addAttribute("brand", brand);
        model.addAttribute("product", product);
        return "manager/product-edit";
    }
 // Lưu sản phẩm sau khi chỉnh sửa
    @PostMapping("/save/{productID}")
    public String saveEditedProduct(@PathVariable int productID, @ModelAttribute Product product,@RequestParam("productImage") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
    	 try {
             Product existingProduct = productService.findProductById(productID);
             if (existingProduct != null) {
                 // Nếu có ảnh mới được tải lên
                 if (!imageFile.isEmpty()) {
                     // Xóa ảnh cũ nếu tồn tại
                     if (existingProduct.getImage() != null) {
                         String uploadDir = "src/main/resources/static/images/products";
                         Path oldImagePath = Paths.get(uploadDir).resolve(existingProduct.getImage());
                         Files.deleteIfExists(oldImagePath); // Xóa ảnh cũ
                     }

                     // Upload ảnh mới
                     String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                     String uploadDir = "src/main/resources/static/images/products";
                     Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

                     // Tạo thư mục nếu không tồn tại
                     if (!Files.exists(uploadPath)) {
                         Files.createDirectories(uploadPath);
                     }

                     // Tạo đường dẫn lưu ảnh mới
                     Path targetLocation = uploadPath.resolve(fileName);
                     Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                     existingProduct.setImage(fileName); // Cập nhật hình ảnh mới
                 }

                 // Cập nhật các thông tin khác của sản phẩm
                 existingProduct.setName(product.getName());
                 existingProduct.setPrice(product.getPrice());
                 existingProduct.setKind(product.getKind());
                 existingProduct.setDescription(product.getDescription());

                 // Lưu sản phẩm đã chỉnh sửa
                 productService.saveProduct(existingProduct);

                 redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được chỉnh sửa thành công.");
             }
             return "redirect:/manager/manage-products";
         } catch (Exception e) {
             e.printStackTrace();
             redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi chỉnh sửa sản phẩm.");
             return "redirect:/manager/manage-products/edit/" + productID;
         }
    }

    @GetMapping("/delete/{productID}")
    public String deleteProduct(@PathVariable int productID, RedirectAttributes redirectAttributes) {
    	try {
            Product product = productService.findProductById(productID);
            if (product != null) {
                // Xóa ảnh nếu tồn tại
                if (product.getImage() != null) {
                    String uploadDir = "src/main/resources/static/images/products";
                    Path imagePath = Paths.get(uploadDir).resolve(product.getImage());
                    Files.deleteIfExists(imagePath);  // Xóa file ảnh
                }
                

                productService.deleteProduct(productID);
                redirectAttributes.addFlashAttribute("message", "Xóa sản phẩm thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi xóa sản phẩm.");
        }
        return "redirect:/manager/manage-products";
    }


 // Phương thức xóa các sản phẩm đã chọn
    @PostMapping("/delete-selected")
    public String deleteSelected(@RequestParam List<Integer> selectedItems, RedirectAttributes redirectAttributes) {
    	 if (selectedItems != null && !selectedItems.isEmpty()) {
             try {
                 System.out.println("Đang xóa các sản phẩm với IDs: " + selectedItems);

                 // Xóa ảnh của từng sản phẩm trước khi xóa dữ liệu
                 String uploadDir = "src/main/resources/static/images/products";
                 for (int productID : selectedItems) {
                     Product product = productService.findProductById(productID);
                     if (product != null && product.getImage() != null) {
                         Path imagePath = Paths.get(uploadDir).resolve(product.getImage());
                         Files.deleteIfExists(imagePath); // Xóa ảnh
                     }
                 }

                 // Xóa các sản phẩm đã chọn
                 productService.deleteProductsByIds(selectedItems);

                 // Thêm thông báo thành công vào RedirectAttributes
                 redirectAttributes.addFlashAttribute("message", "Đã xóa thành công " + selectedItems.size() + " sản phẩm.");
                 return "redirect:/manager/manage-products";
             } catch (Exception e) {
                 e.printStackTrace(); // In lỗi ra console
                 return "error"; // Gửi người dùng đến trang lỗi nếu có lỗi xảy ra
             }
         }
         return "redirect:/manager/manage-products"; // Nếu không có sản phẩm chọn, quay lại trang
    }

}
