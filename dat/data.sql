insert into moley.addresses (address_id, line_1, line_2, city, state, postal_code) VALUES (gen_random_uuid(), '1234 Main St', 'Suite 420', 'Smallville', 'KS', '66223');
insert into moley.addresses (address_id, line_1, line_2, city, state, postal_code) VALUES (gen_random_uuid(), '1776 Freedom St', 'Revolution Room', 'Smallville', 'KS', '66223');
insert into moley.addresses (address_id, line_1, line_2, city, state, postal_code) VALUES (gen_random_uuid(), '1121 Lead Way', 'Sales Division', 'Smallville', 'KS', '66223');
insert into moley.addresses (address_id, line_1, line_2, city, state, postal_code) VALUES (gen_random_uuid(), '556 Sendit Path', 'Sales Division', 'Smallville', 'KS', '66223');

insert into moley.customers (customer_id, first_name, last_name, phone_number, email_address) VALUES (gen_random_uuid(), 'George', 'Washington', '555-515-1234', 'gwashington@freedom.fake.us');
insert into moley.customers (customer_id, first_name, last_name, phone_number, email_address) VALUES (gen_random_uuid(), 'John', 'Adams', '555-515-4567', 'jadams@freedom.fake.us');
insert into moley.customers (customer_id, first_name, last_name, phone_number, email_address) VALUES (gen_random_uuid(), 'Thomas', 'Jefferson', '555-515-1776', 'tjefferson@freedom.fake.us');

insert into moley.vendors(vendor_id, name, address, contact, contact_phone_number, contact_email_address, website) VALUES (gen_random_uuid(), 'Freedom Pills', (select address_id from moley.addresses where line_1 = '1121 Lead Way'), 'Ron Swanson', '555-515-8389', 'rswanson@freedompills.fake.us', 'freedompills.fake.us');
insert into moley.vendors(vendor_id, name, address, contact, contact_phone_number, contact_email_address, website) VALUES (gen_random_uuid(), 'Freedom Dispensers', (select address_id from moley.addresses where line_1 = '556 Sendit Path'), 'John Wayne', '555-515-4893', 'jwayne@freedomdisp.fake.us', 'freedomdisp.fake.us');

insert into moley.products(product_id, vendor_id, name, sku, quantity_on_hand, order_threshold) VALUES (gen_random_uuid(), (select vendor_id from moley.vendors where vendors.name = 'Freedom Pills'), 'Small Pills', '9x19', 10000, 5000);
insert into moley.products(product_id, vendor_id, name, sku, quantity_on_hand, order_threshold) VALUES (gen_random_uuid(), (select vendor_id from moley.vendors where vendors.name = 'Freedom Pills'), 'Medium Pills', '556x45', 20000, 10000);
insert into moley.products(product_id, vendor_id, name, sku, quantity_on_hand, order_threshold) VALUES (gen_random_uuid(), (select vendor_id from moley.vendors where vendors.name = 'Freedom Pills'), 'Large Pills', '716x51', 5000, 2500);
insert into moley.products(product_id, vendor_id, name, sku, quantity_on_hand, order_threshold) VALUES (gen_random_uuid(), (select vendor_id from moley.vendors where vendors.name = 'Freedom Dispensers'), '320 Dispenser', 'P320XC', 10, 3);

insert into moley.customer_addresses (customer_address_id, customer_id, address_id, type) VALUES (gen_random_uuid(), (select customer_id from moley.customers where email_address = 'gwashington@freedom.fake.us'), (select address_id from moley.addresses where line_1 = '1234 Main St'), 'BILLING');

insert into moley.orders (order_id, customer_id, shipping_address_id, billing_address_id, order_date, ship_date, subtotal, tax, shipping, grand_total) VALUES (gen_random_uuid(), (select customer_id from moley.customers where email_address = 'gwashington@freedom.fake.us'), (select address_id from moley.addresses where line_1 = '1234 Main St'), (select address_id from moley.addresses where line_1 = '1234 Main St'), '2023-03-17', '2023-03-18', 200.0, 1.75, 5.0, 206.75);

insert into moley.order_lines (order_line_id, order_id, product_id, quantity, line_total) VALUES (gen_random_uuid(), (select order_id from moley.orders where subtotal = 200.0), (select product_id from moley.products where name = 'Small Pills'), 200, 200);

