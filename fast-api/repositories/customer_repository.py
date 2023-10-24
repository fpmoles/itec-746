import uuid
from typing import List

from models.customer_model import Customer
from repositories.db_connection import DBConnection

GET_ALL = "SELECT customer_id, first_name, last_name, phone_number, email_address from customers"
INSERT = "INSERT INTO customers (customer_id, first_name, last_name, phone_number, email_address) VALUES (%s,%s,%s,%s,%s)"
GET_BY_ID = "SELECT customer_id, first_name, last_name, phone_number, email_address from customers WHERE customer_id = %s"
UPDATE = "UPDATE customers set first_name = %s, last_name=%s, phone_number=%s, email_address=%s WHERE customer_id = %s"
DELETE = "DELETE FROM customers where customer_id=%s"


def get_all_customers() -> List[Customer]:
    customers = []
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(GET_ALL)
    for row in cur:
        customers.append(Customer(customerId=str(row[0]), firstName=row[1], lastName=row[2], phoneNumber=row[3], emailAddress=row[4]))
    return customers


def create_customer(customer: Customer) -> Customer:
    db = DBConnection()
    cur = db.get_cursor()
    uid = uuid.uuid4()
    cur.execute(INSERT, (
        uid,
        customer.first_name,
        customer.last_name,
        customer.phone_number,
        customer.email_address
    ))
    db.connection.commit()
    return get_customer(uid)


def get_customer(uid: uuid) -> Customer:
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(GET_BY_ID, [uid])
    for row in cur:
        customer = Customer(customerId=str(row[0]), firstName=row[1], lastName=row[2], phoneNumber=row[3], emailAddress=row[4])
        return customer


def update_customer(customer: Customer) -> Customer:
    db = DBConnection()
    cur = db.get_cursor()
    uid = uuid.UUID(customer.customer_id)
    cur.execute(UPDATE, (customer.first_name, customer.last_name, customer.phone_number, customer.email_address, uid))
    db.connection.commit()
    return get_customer(uid)


def delete_customer(uid: uuid):
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(DELETE, [uid])
    db.connection.commit()
    return