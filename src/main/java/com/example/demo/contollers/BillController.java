package com.example.demo.contollers;


import com.example.demo.dtos.BillDto;
import com.example.demo.entities.ApplicationUser;
import com.example.demo.services.BillService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bill")
@Controller
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @ModelAttribute("billDto")
    public BillDto billDto(){
        return new BillDto();
    }

    @GetMapping
    public String getBillPage(
            @AuthenticationPrincipal ApplicationUser user,
            Model model
    ){
        model.addAttribute("billDeatils", billService.getBillByUser(user));
        return "bill";
    }

    @PutMapping("/update")
    public String updateBillDetails(
            @AuthenticationPrincipal ApplicationUser user,
            @ModelAttribute("billDto") BillDto billDto,
            Model model
    ){
        billService.updateBillDetails(user, billDto.getDescription());
        return getBillPage(user,model);
    }
}
