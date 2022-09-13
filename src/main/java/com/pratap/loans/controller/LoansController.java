package com.pratap.loans.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pratap.loans.config.LoansServiceConfig;
import com.pratap.loans.model.Loans;
import com.pratap.loans.model.Properties;
import com.pratap.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoansController {

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private LoansServiceConfig loansServiceConfig;

    @GetMapping("/myLoans")
    public ResponseEntity<List<Loans>> getLoansDetails(@RequestParam int customerId) {
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customerId);
        if (loans != null && !loans.isEmpty())
            return new ResponseEntity<>(loans, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/properties")
    public String getPropertiesDetails() throws JsonProcessingException {

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansServiceConfig.getMsg(), loansServiceConfig.getBuildVersion(),
                loansServiceConfig.getMailDetails(), loansServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);
    }
}
