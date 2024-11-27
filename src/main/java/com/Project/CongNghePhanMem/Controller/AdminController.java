package com.Project.CongNghePhanMem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {

	
	@GetMapping("/")
	private String home() {
		return "admin/home";
	}
}
