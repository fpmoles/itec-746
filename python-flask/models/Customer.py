class Customer:
    customer_id = ""
    first_name = ""
    last_name = ""
    phone_number = ""
    email_address = ""

    def __init__(self, customer_id, first_name, last_name, phone_number, email_address):
        self.customer_id = customer_id
        self.first_name = first_name
        self.last_name = last_name
        self.phone_number = phone_number
        self.email_address = email_address

    def serialize(self):
        return {"customerId": self.customer_id,
                "firstName": self.first_name,
                "lastName": self.last_name,
                "phoneNumber": self.phone_number,
                "emailAddress": self.email_address}
