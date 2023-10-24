package edu.ku.frankmoley.javaspring.web;


import edu.ku.frankmoley.javaspring.data.CustomerRepository;
import edu.ku.frankmoley.javaspring.model.Customer;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerRepository customerRepository;

  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @GetMapping
  public List<Customer> getAll(@RequestParam(value="emailAddress", required = false)String emailAddress){
    if (StringUtils.isNotEmpty(emailAddress)){
      Customer customer = this.customerRepository.findByEmailAddress(emailAddress);
      List<Customer> customers = new ArrayList<>();
      customers.add(customer);
      return customers;
    }
    return this.customerRepository.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Customer addCustomer(@RequestBody Customer customer){
    customer.setCustomerId(UUID.randomUUID().toString());
    return this.customerRepository.save(customer);
  }

  @GetMapping("/{id}")
  public Customer getCustomer(@PathVariable("id") String id){
    return this.customerRepository.findById(id).get();
  }

  @PutMapping("/{id}")
  public Customer updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer){
    if (!id.equals(customer.getCustomerId())){
      throw new RuntimeException();
    }
    return this.customerRepository.save(customer);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.RESET_CONTENT)
  public void deleteCustomer(@PathVariable("id") String id){
    this.customerRepository.deleteById(id);
  }
}
