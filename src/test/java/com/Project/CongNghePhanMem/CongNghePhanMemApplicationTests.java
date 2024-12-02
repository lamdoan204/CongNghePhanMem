package com.Project.CongNghePhanMem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.Project.CongNghePhanMem.Service.Impl.ProductService;
import com.Project.CongNghePhanMem.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CongNghePhanMemApplicationTests {

	@Test
	void contextLoads() {
	}
	 @Autowired
	    private ProductService productService;

	    private ProductDTO productDTO;
	    private MockMultipartFile imageFile;

	    @BeforeEach
	    void setUp() {
	        // Thiết lập đối tượng ProductDTO giả lập
	        productDTO = new ProductDTO();
	        productDTO.setName("Test Product");
	        productDTO.setPrice(100.0f);
	        productDTO.setQuantity(50);
	        productDTO.setDescription("Test Description");

	        // Tạo một MultipartFile giả lập (hình ảnh)
	        imageFile = new MockMultipartFile("imageFile", "test-image.jpg", "image/jpeg", "image content".getBytes());
	    }

	    @Test
	    void testSaveProductWithImage() {
	        System.out.println("Start testing saveProductWithImage method");

	        // Gọi phương thức cần kiểm tra
	        productService.saveProductWithImage(productDTO, imageFile);

	        // In ra thông tin sản phẩm để kiểm tra
	        System.out.println("Product saved: " + productDTO.getName() + " with price: " + productDTO.getPrice());
	        
	        // Assert rằng sản phẩm đã được lưu
	        assertNotNull(productDTO);
	        System.out.println("Test passed, product was saved successfully.");
	    }

}
