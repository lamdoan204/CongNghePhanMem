package com.Project.CongNghePhanMem.Controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Notification;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.Review;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.INotificationService;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.Impl.CartService;
import com.Project.CongNghePhanMem.Service.Impl.ProductDetailService;
import com.Project.CongNghePhanMem.Service.Impl.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class ProductController {

	@Autowired
	private IProductService productService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductDetailService productDetailService;

	@Autowired
    private ReviewService reviewService;
	
	@Autowired
	private  INotificationService notificationService;
	
	@PostMapping("/add-product-to-cart/{id}")
	public String handleAddProductToCart(@PathVariable int id, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession(true);

		// Kiểm tra đăng nhập
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) {
			return "redirect:/login";
		}

		// Lấy thông tin sản phẩm
		Product product = productService.findProductById(id);
		if (product == null) {
			return "redirect:/user/";
		}

		// Kiểm tra số lượng tồn kho
		if (!productDetailService.isInStock(product)) {
			redirectAttributes.addFlashAttribute("error", "Sản phẩm đã hết hàng!");
			return "redirect:/user/";
		}

		// Kiểm tra số lượng có đủ không
		if (!productDetailService.checkAvailableQuantity(product, 1)) {
			redirectAttributes.addFlashAttribute("error", "Không đủ số lượng trong kho!");
			return "redirect:/user/";
		}

		try {
			// Lấy giỏ hàng hiện tại
			Cart cart = cartService.getCurrentCart(session);

			// Thêm sản phẩm vào giỏ với số lượng mặc định là 1
			cartService.addProductToCart(cart, product, 1);

			redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");

			return "redirect:/user/";
		} catch (Exception e) {
			// Log error
			return "redirect:/user/";
		}
	}
	
	@GetMapping("/shopdetail/{id}")
    public String shopDetail(@PathVariable("id") int id, Model model) {
        // Lấy thông tin chi tiết sản phẩm từ database
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        
        List<Review> reviews = reviewService.getReviewsByProductId(id);
        model.addAttribute("reviews", reviews);

        System.out.println("Reviews: " + reviews);
        
     // Tính trung bình rating từ danh sách reviews
        double averageRating = 0.0;
        if (!reviews.isEmpty()) {
            averageRating = reviews.stream()
                                   .mapToDouble(Review::getRating)
                                   .average()
                                   .orElse(0.0);
        }

        // Truyền giá trị trung bình vào model
        model.addAttribute("averageRating", averageRating);

        return "user/shopdetail"; // Trả về tên file view (ví dụ: shopDetail.html hoặc shopDetail.jsp)
    }

	@GetMapping("/search")
	public String search(@RequestParam("query") String query, Model model) {
		// Gửi yêu cầu tìm kiếm đến Service
        List<Product> SearchProducts = productService.productSearch(query);

        if (SearchProducts.isEmpty()) {
            // Thêm thông báo lỗi vào model
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm nào với từ khóa: " + query);
        } else {
            // Nếu tìm thấy sản phẩm, thêm danh sách sản phẩm vào model
            model.addAttribute("SearchProducts", SearchProducts);
        }

        // Luôn thêm query vào model để hiển thị lại từ khóa trong ô input
        model.addAttribute("query", query);

	    return "user/search";
	}

	
	@GetMapping("/category/{categoryName}")
	public String getProductsByCategory(@PathVariable("categoryName") String categoryName, Model model) {
	    // Chuyển đổi danh mục từ URL thành danh sách 'kinds' (có thể tách ra từ categoryName nếu cần)
	    List<String> kinds = Arrays.asList(categoryName.split(", ")); // Nếu danh mục chứa nhiều phần tử

	    // Lấy sản phẩm từ service dựa trên kind
	    List<Product> products = productService.findProductsByKinds(kinds);

	    // Thêm sản phẩm vào model
	    model.addAttribute("products", products);

	    // Thêm tên danh mục vào model để hiển thị trên trang
	    model.addAttribute("categoryName", categoryName);

	    return "user/category"; // Trả về view tương ứng (category.html hoặc category.jsp)
	}


	@GetMapping("/cart")
	public String getCartPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/user/login";
		}

		// Lấy giỏ hàng hiện tại
		Cart cart = cartService.getCurrentCart(session);
	
			List<CartDetail> cartDetails = cart.getCartDetails();
			float totalPrice = cartService.calculateTotalPrice(cart);

			// Kiểm tra số lượng tồn cho mỗi sản phẩm
			for (CartDetail detail : cartDetails) {
				boolean inStock = productDetailService.isInStock(detail.getProduct());
				boolean hasEnoughQuantity = productDetailService.checkAvailableQuantity(detail.getProduct(),
						detail.getQuantity());
				detail.setInStock(inStock);
				detail.setHasEnoughQuantity(hasEnoughQuantity);
			}

			model.addAttribute("cartDetails", cartDetails);
			model.addAttribute("totalPrice",  totalPrice);
		
		return "user/shoppingCart";
	}

	@PostMapping("/delete-cart-product/{id}")
	public String deleteCartDetail(@PathVariable int id, HttpServletRequest request) {
		try {
			cartService.removeCartDetail(id);
			return "redirect:/user/cart";
		} catch (Exception e) {
			// Log error
			return "redirect:/user/cart";
		}
	}

	@PostMapping("/update-cart/{type}/{id}")
	public ResponseEntity<?> updateCart(@PathVariable String type, @PathVariable int id) {
		System.out.println("Received request - Type: " + type + ", ID: " + id);
		try {
			CartDetail detail = cartService.getCartDetail(id);
			if (detail == null) {
				return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng");
			}

			if ("add".equals(type)) {
				if (!productDetailService.checkAvailableQuantity(detail.getProduct(), detail.getQuantity() + 1)) {
					return ResponseEntity.badRequest().body("Không đủ số lượng trong kho!");
				}
				cartService.increaseQuantity(id);
				System.out.println("Received request - Type: " + type + ", ID: " + id);
			} else if ("remove".equals(type)) {
				cartService.decreaseQuantity(id);
				System.out.println("Successfully decreased quantity");
			}

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			System.err.println("Error updating cart: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
