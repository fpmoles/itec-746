package com.frankmoley.ku.itec.data.dao.impl;

import com.frankmoley.ku.itec.data.dao.CustomerDAO;
import com.frankmoley.ku.itec.data.model.CustomerModel;
import com.frankmoley.ku.itec.exception.InternalServerException;
import com.frankmoley.ku.itec.exception.NotFoundException;
import com.frankmoley.ku.itec.web.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.xml.SqlXmlFeatureNotImplementedException;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerDAOImpl implements CustomerDAO {

  private final DataSource dataSource;

  private final static Logger LOGGER = LoggerFactory.getLogger(CustomerDAOImpl.class);
  private final static String GET_ALL_QUERY = "select customer_id, first_name, last_name, phone_number, email_address from moley.customers";
  private final static String GET_BY_EMAIL = "select customer_id, first_name, last_name, email_address, phone_number from moley.customers where email_address = ?";
  private final static String GET_BY_ID = "select customer_id, first_name, last_name, email_address, phone_number from moley.customers where customer_id = ?";
  private final static String ADD_CUSTOMER = "insert into moley.customers (customer_id, first_name, last_name, email_address, phone_number) VALUES (?, ?, ?, ?, ?)";
  private final static String UPDATE_CUSTOMER = "update moley.customers set first_name = ?, last_name = ?, email_address = ?, phone_number =? where customer_id = ?";
  private final static String DELETE_CUSTOMER = "delete from moley.customers where customer_id = ?";

  private final static String FOR_UPDATE = " for update";
  public CustomerDAOImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<CustomerModel> getAll() {
    List<CustomerModel> customers = new ArrayList<>();
    try(Connection connection = this.dataSource.getConnection()){
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY);
      while(resultSet.next()){
        CustomerModel model = new CustomerModel();
        model.setCustomerId(resultSet.getString("customer_id"));
        model.setFirstName(resultSet.getString("first_name"));
        model.setLastName(resultSet.getString("last_name"));
        model.setEmailAddress(resultSet.getString("email_address"));
        model.setPhoneNumber(resultSet.getString("phone_number"));
        customers.add(model);
      }
      statement.close();
    }catch(SQLException e){
      String message = "error executing getAll customers";
      LOGGER.error(message, e);
      throw new InternalServerException(message, e);
    }
    return customers;
  }

  @Override
  public CustomerModel getByEmailAddress(String emailAddress) {
    CustomerModel customer = null;
    try(Connection connection = this.dataSource.getConnection()){
      PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL);
      statement.setString(1, emailAddress);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        customer = new CustomerModel();
        customer.setCustomerId(resultSet.getString("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmailAddress(resultSet.getString("email_address"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
      }
      statement.close();
    }catch(SQLException e){
      String message = "error executing getByEmail";
      LOGGER.error(message, e);
      throw new InternalServerException(message, e);
    }
    return customer;

  }

  @Override
  public CustomerModel getById(String customerId) {
    CustomerModel customer = null;
    try(Connection connection = this.dataSource.getConnection()){
      PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
      UUID id = UUID.fromString(customerId);
      statement.setObject(1, id);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        customer = new CustomerModel();
        customer.setCustomerId(resultSet.getString("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmailAddress(resultSet.getString("email_address"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
      }
      statement.close();

    }catch(SQLException e){
      String message = "error executing getById";
      LOGGER.error(message, e);
      throw new InternalServerException(message, e);
    }
    return customer;
  }

  @Override
  public CustomerModel addCustomer(CustomerModel model) {
    CustomerModel customer = null;
    try(Connection connection = this.dataSource.getConnection()){
      connection.setAutoCommit(false);  //starting a transaction
      PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER);
      UUID id = UUID.randomUUID();
      statement.setObject(1, id);
      statement.setString(2, model.getFirstName());
      statement.setString(3, model.getLastName());
      statement.setString(4, model.getEmailAddress());
      statement.setString(5, model.getPhoneNumber());
      int result = statement.executeUpdate();
      if (result != 1) {
        throw new InternalServerException("error creating customer");
      }
      statement = connection.prepareStatement(GET_BY_ID);
      statement.setObject(1, id);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        customer = new CustomerModel();
        customer.setCustomerId(resultSet.getString("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmailAddress(resultSet.getString("email_address"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
      }
      connection.commit();
      statement.close();
    }catch(SQLException e){
      String message = "error creating customer";
      LOGGER.error(message, e);
      throw new InternalServerException(message, e);
    }
    return customer;
  }

  @Override
  public CustomerModel updateCustomer(CustomerModel model) {
    CustomerModel customer = null;
    UUID id = UUID.fromString(model.getCustomerId());
    try(Connection connection = this.dataSource.getConnection()){
      connection.setAutoCommit(false);  //starting a transaction
      // Getting it first for update to lock the row
      PreparedStatement statement = connection.prepareStatement(GET_BY_ID + FOR_UPDATE);
      statement.setObject(1, id);
      ResultSet resultSet = statement.executeQuery();
      while(resultSet.next()){
        customer = new CustomerModel();
        customer.setCustomerId(resultSet.getString("customer_id"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmailAddress(resultSet.getString("email_address"));
        customer.setPhoneNumber(resultSet.getString("phone_number"));
      }
      if (customer == null){
        throw new NotFoundException("cannot update object that doesn't exist");
      }

      statement = connection.prepareStatement(UPDATE_CUSTOMER);
      statement.setString(1, model.getFirstName());
      statement.setString(2, model.getLastName());
      statement.setString(3, model.getEmailAddress());
      statement.setString(4, model.getPhoneNumber());
      statement.setObject(5, id);
      int result = statement.executeUpdate();
      if (result != 1) {
        throw new InternalServerException("error creating customer");
      }
      connection.commit();
      statement.close();
    }catch(SQLException e){
      String message = "error updating customer";
      LOGGER.error(message, e);
      throw new InternalServerException(message, e);
    }
    return model;
  }

  @Override
  public void deleteCustomer(String id) {
    UUID customerId = UUID.fromString(id);
    try(Connection connection = this.dataSource.getConnection()){
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER);
      statement.setObject(1, customerId);
      statement.execute();
      connection.commit();
      statement.close();
    }catch(SQLException e){
    String message = "error deleting customer";
    LOGGER.error(message, e);
    throw new InternalServerException(message, e);
  }
  }

}
