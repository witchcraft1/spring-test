package com.example.demo.contollers;


import com.example.demo.dtos.UserDto;
import com.example.demo.entities.ApplicationUser;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegistrationController {
    private final ApplicationUserService applicationUserService;

    @Autowired
    public RegistrationController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @ModelAttribute("user")
    public UserDto userDtoAttr(){
        return new UserDto();
    }

    @GetMapping("/registration")
    public String getRegPage(){
//        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String doRegister(/*@PathVariable("login") String login,
                             @PathVariable("password") String pass*/
            @ModelAttribute("user") UserDto userDto,
            Model model){
        try {
            applicationUserService.registerNewApplicationUser(userDto.getUsername(), userDto.getPassword());
            model.addAttribute("registrationSuccessful", "Registration is successful");
        }catch (UserAlreadyExistsException e){
            model.addAttribute("userExists", "User already exists");
            return getRegPage();
        }
        return "registration";
    }
}
