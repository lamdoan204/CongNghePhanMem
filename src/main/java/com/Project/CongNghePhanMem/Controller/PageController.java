package com.Project.CongNghePhanMem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {
    @GetMapping("/home")
    public String Home() {
        return "/views/home" ;
    }
}
