package com.pratap.loans.controller;

import com.pratap.loans.model.Customer;
import com.pratap.loans.model.Loans;
import com.pratap.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoansController {

    @Autowired
    private LoansRepository loansRepository;

    @GetMapping("/myLoans")
    public ResponseEntity<List<Loans>> getLoansDetails(@RequestBody Customer customer) {
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        if (loans != null && !loans.isEmpty())
            return new ResponseEntity<>(loans, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
