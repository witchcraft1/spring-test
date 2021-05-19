package com.example.demo.services;

import com.example.demo.dtos.OrderDto;
import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.Bill;
import com.example.demo.entities.OrderStatus;
import com.example.demo.entities.RepairOrder;
import com.example.demo.repositories.BillRepo;
import com.example.demo.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final BillRepo billRepo;
    private final BillService billService;

    @Autowired
    public OrderService(OrderRepo orderRepo, BillRepo billRepo, BillService billService) {
        this.orderRepo = orderRepo;
        this.billRepo = billRepo;
        this.billService = billService;
    }

    public List<RepairOrder> getAllOrdersByUser(ApplicationUser user){
        return orderRepo.findAllByUser(user);
    }

    public void createOrder(OrderDto dto, ApplicationUser user){
        RepairOrder order = new RepairOrder();

        order.setOrderStatus(OrderStatus.WAITING_FOR_MASTER);
        order.setRegisterDate(LocalDate.now());
        order.setDescription(dto.getDescription());
        order.setUser(user);

        orderRepo.save(order);
    }

    @Transactional
    public void orderPaid(Long order_id, ApplicationUser user){
        RepairOrder order = orderRepo.getOne(order_id);
        Bill bill = billService.getBillByUser(user);
        long cashRemains = bill.getCashAvailable() - order.getWorkPrice();

        if(cashRemains >= 0) {
            bill.setCashAvailable(cashRemains);
            order.setOrderStatus(OrderStatus.BILLED);
        }
    }
}
