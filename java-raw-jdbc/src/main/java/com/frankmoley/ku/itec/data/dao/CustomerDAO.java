package com.frankmoley.ku.itec.data.dao;

import com.frankmoley.ku.itec.data.model.CustomerModel;
import java.util.List;

public interface CustomerDAO {

  List<CustomerModel> getAll();
  CustomerModel getByEmailAddress(String emailAddress);
  CustomerModel getById(String customerId);
  CustomerModel addCustomer(CustomerModel model);
  CustomerModel updateCustomer(CustomerModel model);
  void deleteCustomer(String id);
}
