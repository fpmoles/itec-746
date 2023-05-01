import json
import uuid

from flask import Flask, jsonify, request

from models.Customer import Customer
from repositories import customer_dao

app = Flask(__name__)


@app.route("/customers", methods=('GET', 'POST'))
def col_customers():
    if request.method == 'GET':
        customers = customer_dao.get_all_customers()
        return jsonify([c.serialize() for c in customers])
    else:
        body = request.json
        customer = Customer('', body['firstName'], body['lastName'], body['phoneNumber'],
                            body['emailAddress'])
        customer = customer_dao.create_customer(customer)
        return jsonify(customer.serialize()), 201


@app.route("/customers/<id>", methods=('GET', 'PUT', 'DELETE'))
def sin_customer(id):
    uid = uuid.UUID(id)
    if request.method == 'GET':
        customer = customer_dao.get_customer(uid)
        if customer is None:
            return "", 404
        return jsonify(customer.serialize())
    elif request.method == 'PUT':
        body = request.json
        if id != body['customerId']:
            return "Bad Request, path id must match body id", 400
        customer = Customer(body['customerId'], body['firstName'], body['lastName'], body['phoneNumber'],
                            body['emailAddress'])
        customer_dao.update_customer(customer)
        return jsonify(customer.serialize())
    else:
        customer_dao.delete_customer(uid)
        return "", 205
