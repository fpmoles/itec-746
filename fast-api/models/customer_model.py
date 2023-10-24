from fastapi_camelcase import CamelModel
from typing import Optional


class Customer(CamelModel):
    customer_id: Optional[str] = None
    first_name: Optional[str] = None
    last_name: Optional[str] = None
    phone_number: Optional[str] = None
    email_address: str
