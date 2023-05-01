import os
import uuid

import psycopg2
import config
from models.Customer import Customer
from repositories.DBConnection import DBConnection

GET_ALL = "SELECT customer_id, first_name, last_name, phone_number, email_address from moley.customers"
INSERT = "INSERT INTO moley.customers (customer_id, first_name, last_name, phone_number, email_address) VALUES (%s," \
         "%s,%s,%s,%s)"
GET_BY_ID = "SELECT customer_id, first_name, last_name, phone_number, email_address from moley.customers WHERE " \
            "customer_id = %s"
UPDATE = "UPDATE moley.customers set first_name = %s, last_name=%s, phone_number=%s, email_address=%s WHERE " \
         "customer_id = %s"
DELETE = "DELETE FROM moley.customers where customer_id=%s"


def get_all_customers():
    customers = []
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(GET_ALL)
    for row in cur:
        customers.append(Customer(str(row[0]), row[1], row[2], row[3], row[4]))
    return customers


def create_customer(customer):
    db = DBConnection()
    cur = db.get_cursor()
    uid = uuid.uuid4()
    cur.execute(INSERT, (uid, customer.first_name, customer.last_name, customer.phone_number, customer.email_address))
    db.connection.commit()
    return get_customer(uid)


def get_customer(uid):
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(GET_BY_ID, [uid])
    for row in cur:
        return Customer(str(row[0]), row[1], row[2], row[3], row[4])


def update_customer(customer):
    db = DBConnection()
    cur = db.get_cursor()
    uid = uuid.UUID(customer.customer_id)
    cur.execute(UPDATE, (customer.first_name, customer.last_name, customer.phone_number, customer.email_address, uid))
    db.connection.commit()
    return get_customer(uid)


def delete_customer(uid):
    db = DBConnection()
    cur = db.get_cursor()
    cur.execute(DELETE, [uid])
    db.connection.commit()
    return
