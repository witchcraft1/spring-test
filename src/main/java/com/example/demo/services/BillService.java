package com.example.demo.services;

import com.example.demo.entities.ApplicationUser;
import com.example.demo.entities.Bill;
import com.example.demo.repositories.BillRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BillService {
    private final BillRepo billRepo;

    public BillService(BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    public Bill getBillByUser(ApplicationUser user){
        Optional<Bill> bill = billRepo.findByUser(user);

        if(bill.isEmpty()){
            throw new IllegalArgumentException();
        }

        return bill.get();
    }

    @Transactional
    public void updateBillDetails(
            ApplicationUser user,
            String description
    ){
        getBillByUser(user).setCredit_cart_details(description);
    }
}
