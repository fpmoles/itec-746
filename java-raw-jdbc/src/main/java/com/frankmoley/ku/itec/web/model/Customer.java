package com.frankmoley.ku.itec.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.frankmoley.ku.itec.data.model.CustomerModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
  private String customerId;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;

  public Customer() {
    super();
  }

  public Customer(String customerId, String firstName, String lastName, String emailAddress,
      String phoneNumber) {
    this.customerId = customerId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
  }

  public Customer(CustomerModel dbModel){
    this.customerId = dbModel.getCustomerId();
    this.firstName = dbModel.getFirstName();
    this.lastName = dbModel.getLastName();
    this.emailAddress = dbModel.getEmailAddress();
    this.phoneNumber = dbModel.getPhoneNumber();
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "customerId='" + customerId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", emailAddress='" + emailAddress + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }

  public CustomerModel translateToDbModel(){
    CustomerModel dbModel = new CustomerModel();
    dbModel.setCustomerId(this.customerId);
    dbModel.setFirstName(this.firstName);
    dbModel.setLastName(this.lastName);
    dbModel.setPhoneNumber(this.phoneNumber);
    dbModel.setEmailAddress(this.emailAddress);
    return dbModel;
  }

}
