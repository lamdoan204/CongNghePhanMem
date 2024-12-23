package com.Project.CongNghePhanMem.Controller.Manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.Article;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Entity.RevenueStatistic;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IManagerService;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.IStatisticService;
import com.Project.CongNghePhanMem.Service.Impl.ArticleService;
import com.Project.CongNghePhanMem.Service.Impl.DepartmentService;
import com.Project.CongNghePhanMem.Service.Impl.ManagerService;

import com.Project.CongNghePhanMem.Service.Impl.PromotionService;
import com.Project.CongNghePhanMem.Service.Impl.StatisticService;
import com.Project.CongNghePhanMem.Service.Impl.UserService;
import com.Project.CongNghePhanMem.dto.StockReport;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	IStatisticService statisticService = new StatisticService();

	@Autowired
	IProductService productService;

	@Autowired
	private PromotionService promotionService;
	@Autowired
	IManagerService managerService = new ManagerService();
	@Autowired
	IUserService userService = new UserService();
	@Autowired
	DepartmentService departmentService = new DepartmentService();

	@GetMapping("/")
	public String getMethodName(Model model) {

		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}

		String brand = managerService.get_DepartmentName(manager);
		int managerBrandId = managerService.get_DepartmentBrandId(manager);

		long countProduct = productService.getProductCountByBrand(managerBrandId);

		model.addAttribute("brand", brand);
		model.addAttribute("countProduct", countProduct);
		model.addAttribute("managerName", manager.getFullName());

		return "manager/index";
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(@RequestParam("userId") int employeeId, @RequestParam("fullName") String fullName,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("address") String address) {
		User employee = userService.getUserById(employeeId);
		employee.setFullName(fullName);
		employee.setEmail(email);
		employee.setAddress(address);
		employee.setPhone(phone);
		userService.updateUser(employee);
		return "redirect:/manager/employeeManagement";
	}

	@GetMapping("/editEmployee/{id}")
	public String editEployee(@PathVariable("id") int userId, Model model) {
		User employee = userService.getUserById(userId);
		model.addAttribute("employee", employee);
		return "manager/edit-employee";
	}

	private Page<User> convertListToPage(List<User> allEmployees, int page, int size) {
		int start = page * size;
		int end = Math.min((start + size), allEmployees.size());

		List<User> employeesForPage = allEmployees.subList(start, end);

		return new PageImpl<>(employeesForPage, PageRequest.of(page, size), allEmployees.size());
	}

	@GetMapping("/employeeManagement")
	public String employee(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size, Model model) {

		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		// Gọi Service để lấy tên thương hiệu
		String brand = managerService.get_DepartmentName(manager);
		model.addAttribute("brand", brand);
		// lấy danh sách nhân viên
		List<User> employe = managerService.getListEmployee(manager);

		Page<User> employee = convertListToPage(employe, page, size);
		model.addAttribute("employees", employee);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", employee.getTotalPages());
		model.addAttribute("totalItems", employee.getTotalElements());
		model.addAttribute("pageSize", size);
		model.addAttribute("managerName", manager.getFullName());

		return "manager/employeemanagement";
	}

	@PostMapping("/employeeManagement")
	public String postMethodName(Model model, @RequestParam("fullname") String fullname,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("password") String password, @RequestParam("address") String address) {

		if (fullname.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() || address.isBlank()) {
			model.addAttribute("error", "Vui lòng điền đầy đủ thông tin!");
			return "manager/employeeManagement";
		}

		User user = userService.getUserByEmail(email);
		if (user != null) {
			model.addAttribute("error", "Email đã tồn tại");
			return "manager/employeeManagement";
		}
		user = userService.getUserByPhone(phone);
		if (user != null) {
			model.addAttribute("error", "Số điện thoại đã tồn tại");
			return "manager/employeeManagement";
		}
		// thêm nhân viên vào bảng Users
		user = new User();
		user.setFullName(fullname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddress(address);
		user.setPassword(password);
		user.setRole("EMPLOYEE");
		user.setEnabled(true);
		user.setAccounNonLocked(true);
		managerService.add_Employee(user);

		// thêm nhân viên vào Phòng ban
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}

		Department department = departmentService.getDepartmentByManager(manager);
		user = userService.getUserByEmail(email);

		departmentService.addEmployeeToDepartment(department.getId(), user);

		return "redirect:/manager/employeeManagement";
	}

	@PostMapping("/deleteEmployee")
	public String postDeleteEmployee(@RequestParam("userId") Integer userId) {
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}

		User employee = userService.getUserById(userId);

		Department department = departmentService.getDepartmentByManager(manager);

		departmentService.deleteEmployeeToDepartment(department.getId(), employee);
		managerService.delete_Employee(employee);

		return "redirect:/manager/";
	}
	// Phía trên là phần của Lâm

	// Thêm các mapping từ PromotionController
	@GetMapping("/promotions")
	public String showPromotionList(Model model) {
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		String brand = managerService.get_DepartmentName(manager);
		model.addAttribute("brand", brand);
		model.addAttribute("managerName", manager.getFullName());
		model.addAttribute("promotions", promotionService.getAllPromotions());
		return "manager/promotion-list";
	}

	@GetMapping("/promotions/create")
	public String showCreateForm(Model model) {
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		String brand = managerService.get_DepartmentName(manager);
		model.addAttribute("brand", brand);
		model.addAttribute("managerName", manager.getFullName());
		model.addAttribute("promotion", new Promotion());
		return "manager/promotion_form";
	}

	@PostMapping("/promotions/create")
	public String createPromotion(@ModelAttribute Promotion promotion) {
		promotionService.savePromotion(promotion); // Lưu cả thông tin coupon
		return "redirect:/manager/promotions";
	}

	@GetMapping("/promotions/delete/{id}")
	public String deletePromotion(@PathVariable("id") int id) {
		promotionService.deletePromotionById(id);
		return "redirect:/manager/promotions";
	}

	@Autowired
	private ArticleService articleService;

	// Danh sách bài viết
	@GetMapping("/blog")
	public String showBlogList(Model model) {

		List<Article> articles = articleService.getAllArticles();
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		// Gọi Service để lấy tên thương hiệu
		String brand = managerService.get_DepartmentName(manager);
		model.addAttribute("brand", brand);
		model.addAttribute("managerName", manager.getFullName());
		model.addAttribute("articles", articles);
		return "manager/blog-list";
	}

	// Tạo bài viết mới
	@GetMapping("/blog/create")
	public String showCreateBlogForm(Model model) {
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		// Gọi Service để lấy tên thương hiệu
		String brand = managerService.get_DepartmentName(manager);
		model.addAttribute("brand", brand);
		model.addAttribute("managerName", manager.getFullName());
		model.addAttribute("blog", new Article());
		return "manager/blog-form";
	}

	@PostMapping("/blog/create")
	public String createBlog(@ModelAttribute("blog") Article article,
			@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		// Lấy tên file gốc và phần mở rộng
		String originalFilename = imageFile.getOriginalFilename();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));

		// Tạo tên file mới để tránh trùng lặp
		String fileName = System.currentTimeMillis() + "_" + originalFilename;

		// Đường dẫn đến thư mục lưu ảnh
		String uploadDir = "src/main/resources/static/images/";

		// Kiểm tra và tạo thư mục nếu không tồn tại
		File uploadFolder = new File(uploadDir);
		if (!uploadFolder.exists()) {
			uploadFolder.mkdirs();
		}

		// Đường dẫn tệp ảnh sẽ lưu vào
		Path filePath = Paths.get(uploadDir, fileName);
		Files.write(filePath, imageFile.getBytes());

		// Gán đường dẫn ảnh để lưu vào cơ sở dữ liệu (lưu trữ trên server)
		article.setImage("/images/" + fileName); // Đảm bảo là đường dẫn từ thư mục static

		// Gán tác giả (người dùng hiện tại)
		User currentUser = userService.getUserCurentLogged();
		article.setAuthor(currentUser);

		article.setLike(0);
		article.setShare(0);

		articleService.saveArticle(article);

		return "redirect:/manager/blog";
	}

	@PostMapping("/blog/edit")
	public String editBlog(@ModelAttribute("blog") Article article,
			@RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
		if (imageFile != null && !imageFile.isEmpty()) {
			// Delete old image if exists
			if (article.getImage() != null) {
				String oldImagePath = "src/main/resources/static" + article.getImage();
				Files.deleteIfExists(Paths.get(oldImagePath));
			}

			// Save new image
			String uploadDir = "src/main/resources/static/uploads/";
			String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

			File dir = new File(uploadDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			Path path = Paths.get(uploadDir + fileName);
			Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			article.setImage("/uploads/" + fileName);
		}

		// Preserve existing author
		Article existingArticle = articleService.getArticleById(article.getArticleId());
		if (existingArticle != null) {
			article.setAuthor(existingArticle.getAuthor());
			article.setLike(existingArticle.getLikes());
			article.setShare(existingArticle.getShare());
		}

		articleService.saveArticle(article);
		return "redirect:/manager/blog";
	}

	// Xóa bài viết
	@GetMapping("/blog/delete/{id}")
	public String deleteBlog(@PathVariable("id") int id) {
		articleService.deleteArticleById(id);
		return "redirect:/manager/blog";
	}

	@GetMapping("/revenue")
	public String showRevenueManagementPage(Model model) {
		User manager = userService.getUserCurentLogged();
		if (manager == null) {
			throw new RuntimeException("Không tìm thấy người dùng");
		}
		// Gọi Service để lấy tên thương hiệu
		String brand = managerService.get_DepartmentName(manager);

		int managerBrandId = managerService.get_DepartmentBrandId(manager); // Thay bằng cách lấy thực tế

		List<RevenueStatistic> monthlyRevenue = statisticService.getRevenueByMonthAndProduct(managerBrandId);
		List<StockReport> stockReport = statisticService.getStockReport(managerBrandId);

		model.addAttribute("brand", brand);
		model.addAttribute("managerName", manager.getFullName());
		model.addAttribute("stockReport", stockReport);

		// model.addAttribute("weeklyRevenue", weeklyRevenue);
		model.addAttribute("monthlyRevenues", monthlyRevenue);
		// model.addAttribute("quarterlyRevenue", quarterlyRevenue);
		// model.addAttribute("yearlyRevenue", yearlyRevenue);

		return "manager/revenueManagement"; // Đảm bảo tên này khớp với file template
	}

}
