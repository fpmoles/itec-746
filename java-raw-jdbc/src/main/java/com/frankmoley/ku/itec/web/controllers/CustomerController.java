package com.frankmoley.ku.itec.web.controllers;

import com.frankmoley.ku.itec.data.dao.CustomerDAO;
import com.frankmoley.ku.itec.data.model.CustomerModel;
import com.frankmoley.ku.itec.exception.BadRequestException;
import com.frankmoley.ku.itec.exception.NotFoundException;
import com.frankmoley.ku.itec.web.model.Customer;
import io.micrometer.common.util.StringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerDAO customerDAO;

  public CustomerController(CustomerDAO customerDAO) {
    this.customerDAO = customerDAO;
  }

  @GetMapping
  public List<Customer> getCustomers(@RequestParam(value = "emailAddress", required = false)String emailAddress){
    List<Customer> customers = new ArrayList<>();
    if(StringUtils.isNotBlank(emailAddress)){
      CustomerModel dbModel = this.customerDAO.getByEmailAddress(emailAddress);
      customers.add(new Customer(dbModel));
    }else{
      List<CustomerModel> dbModels = this.customerDAO.getAll();
      dbModels.forEach(dbModel -> customers.add(new Customer(dbModel)));
    }
    return customers;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer addCustomer(@RequestBody Customer customer){
    CustomerModel result = this.customerDAO.addCustomer(customer.translateToDbModel());
    return new Customer(result);
  }

  @GetMapping("/{id}")
  public Customer getCustomer(@PathVariable("id")String customerId){
    if(StringUtils.isBlank(customerId)){
      throw new BadRequestException("id is required");
    }
    CustomerModel model = this.customerDAO.getById(customerId);
    if(model == null){
      throw new NotFoundException("no customer found with id " + customerId);
    }
    return new Customer(model);
  }

  @PutMapping("/{id}")
  public Customer updateCustomer(@PathVariable("id") String customerId, @RequestBody Customer model){
    if(StringUtils.isBlank(customerId) || model == null || !model.getCustomerId().equals(customerId)){
      throw new BadRequestException("both id and body must be populated and the ids much match");
    }
    CustomerModel result = this.customerDAO.updateCustomer(model.translateToDbModel());
    return new Customer(result);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteCustomer(@PathVariable("id")String customerId){
    this.customerDAO.deleteCustomer(customerId);
  }
}
