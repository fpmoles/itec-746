import uuid

from fastapi import FastAPI, HTTPException
from typing import List

from models.customer_model import Customer
from repositories.customer_repository import get_all_customers, create_customer, get_customer, update_customer, delete_customer

app = FastAPI()


@app.get("/customers")
def get_all_customers_api() -> List[Customer]:
    return get_all_customers()


@app.post("/customers")
def create_customer_api(customer: Customer) -> Customer:
    return create_customer(customer)


@app.get("/customers/{customer_id}")
def get_customer_api(customer_id: str) -> Customer:
    cid = uuid.UUID(customer_id)
    customer = get_customer(cid)
    return customer


@app.put("/customers/{customer_id}")
def update_customer_api(customer_id: str, customer: Customer) -> Customer:
    if customer_id != customer.customer_id:
        raise HTTPException(status_code=400, detail="path id and object id must match")
    return update_customer(customer)


@app.delete("/customers/{customer_id}")
def delete_customer_api(customer_id: str):
    cid = uuid.UUID(customer_id)
    delete_customer(cid)
