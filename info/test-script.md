# Test Script

## Requirements
* httpie - https://httpie.io or convert to cURL if preferred

## Script

### GET_ALL

#### Execution

  `$ http :8080/customers`

#### Results

  ```
  [
    {
        "customerId": "8254434f-9eb2-4b8e-993b-166705a25acf",
        "emailAddress": "gwashington@freedom.fake.us",
        "firstName": "George",
        "lastName": "Washington",
        "phoneNumber": "555-515-1234"
    },
    {
        "customerId": "24e1b70a-5f95-44db-9953-42579ced5a20",
        "emailAddress": "jadams@freedom.fake.us",
        "firstName": "John",
        "lastName": "Adams",
        "phoneNumber": "555-515-4567"
    },
    {
        "customerId": "51e00648-f45d-4245-9af8-22dc3fd99253",
        "emailAddress": "tjefferson@freedom.fake.us",
        "firstName": "Thomas",
        "lastName": "Jefferson",
        "phoneNumber": "555-515-1776"
    }
  ]
  ```
### GET_BY_EMAIL

#### Execution

  `$ http :8080/customers emailAddress=="tjefferson@freedom.fake.us"`

#### Results

  ```
  [
    {
        "customerId": "51e00648-f45d-4245-9af8-22dc3fd99253",
        "emailAddress": "tjefferson@freedom.fake.us",
        "firstName": "Thomas",
        "lastName": "Jefferson",
        "phoneNumber": "555-515-1776"
    }
  ]
  ```

### ADD_CUSTOMER

#### Execution

  `$ http :8080/customers firstName=John lastName=Doe emailAddress=jdoe@fake.com phoneNumber=555-515-9283`

#### Results

_Note the ID will change_

  ```
  {
    "customerId": "853c93e6-52e9-4b98-a706-b11cee9eddef",
    "emailAddress": "jdoe@fake.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "555-515-9283"
  }
  ```
  
### GET_BY_ID

#### Execution

  `$ http :8080/customers/51e00648-f45d-4245-9af8-22dc3fd99253`

#### Results
  ```
  {
    "customerId": "51e00648-f45d-4245-9af8-22dc3fd99253",
    "emailAddress": "tjefferson@freedom.fake.us",
    "firstName": "Thomas",
    "lastName": "Jefferson",
    "phoneNumber": "555-515-1776"
  }
  ```

### UPDATE_CUSTOMER

#### Execution

  `$ http PUT :8080/customers/853c93e6-52e9-4b98-a706-b11cee9eddef customerId=853c93e6-52e9-4b98-a706-b11cee9eddef firstName=John lastName=Doe emailAddress=jdoe@fake.com phoneNumber=555-515-9100
  `

#### Results

  ```
  {
    "customerId": "853c93e6-52e9-4b98-a706-b11cee9eddef",
    "emailAddress": "jdoe@fake.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "555-515-9100"
  }
  ```

### DELETE_CUSTOMER

#### Execution

  `$ http DELETE :8080/customers/853c93e6-52e9-4b98-a706-b11cee9eddef`

#### Results

  ```
  HTTP/1.1 205 
  Connection: keep-alive
  Content-Length: 0
  Date: Mon, 27 Mar 2023 02:16:08 GMT
  Keep-Alive: timeout=60
  ```