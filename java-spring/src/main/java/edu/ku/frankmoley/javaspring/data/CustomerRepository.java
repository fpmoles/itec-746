package edu.ku.frankmoley.javaspring.data;


import edu.ku.frankmoley.javaspring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
  Customer findByEmailAddress(String emailAddress);
}
