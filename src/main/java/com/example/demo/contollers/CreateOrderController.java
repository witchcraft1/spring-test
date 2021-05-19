package com.example.demo.contollers;

import com.example.demo.dtos.OrderDto;
import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.RepairOrder;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/orders")
@Controller
public class CreateOrderController {
    private final OrderService orderService;

    @Autowired
    public CreateOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("orderDto")
    private OrderDto getOrderDto(){
        return new OrderDto();
    }

    @GetMapping
    public String getOrdersByUser(
            @AuthenticationPrincipal ApplicationUser user,
            Model model){
        List<RepairOrder> orders = orderService.getAllOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/create")
    public String createPage(){
        return "create-order";
    }

    @PostMapping("/create")
    public String createOrder(
            @AuthenticationPrincipal ApplicationUser user,
            @ModelAttribute("orderDto") OrderDto orderDto
    ){
        orderService.createOrder(orderDto, user);
        return createPage();
    }

    @PutMapping("/pay/{id}")
    public String payForTheOrder(
            @AuthenticationPrincipal ApplicationUser user,
            @PathVariable("id") Long id,
            Model model
    ){
        orderService.orderPaid(id,user);
        return getOrdersByUser(user,model);
    }

}
