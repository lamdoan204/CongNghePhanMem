package com.Project.CongNghePhanMem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@GetMapping("/home")
    public String getMethodName() {
        return "manager/index";
    }
	 @GetMapping("/manage-employees")
	    public String getMethodEmployeeManagerment() {
	        return "/manager/employeemanagement";
	    }
	    
	    

}
