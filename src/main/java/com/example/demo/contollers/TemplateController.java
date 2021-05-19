package com.example.demo.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller //TODO: see the diff between @Controller and @RestController. Controller's end-points returns only templates, @RestController's end-points can return anything
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("courses")
    public String getCoursesView(){
        return "courses";
    }
}
