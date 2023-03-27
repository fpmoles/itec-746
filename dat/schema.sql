create schema if not exists moley;

create table if not exists moley.addresses(
    address_id uuid primary key,
    line_1 varchar,
    line_2 varchar,
    city varchar,
    state varchar,
    postal_code varchar
);
comment on table moley.addresses is 'Table to store all addresses in the system regardless of purpose or type';

create table if not exists moley.customers (
    customer_id uuid primary key,
    first_name varchar,
    last_name varchar,
    phone_number varchar,
    email_address varchar unique not null
);
comment on table moley.customers is 'Table to store all customer records in the system';

create table if not exists moley.customer_addresses (
    customer_address_id uuid primary key,
    customer_id uuid references moley.customers(customer_id),
    address_id uuid references moley.addresses(address_id),
    type varchar not null
);

comment on table moley.customer_addresses is 'A joining table to allow customers to have multiple addresses of various types';

create table if not exists moley.orders (
    order_id uuid primary key,
    customer_id uuid references moley.customers(customer_id),
    shipping_address_id uuid references moley.addresses(address_id),
    billing_address_id uuid references moley.addresses(address_id),
    order_date timestamp,
    ship_date timestamp,
    subtotal numeric(12,2),
    tax numeric(12,2),
    shipping numeric(12,2),
    grand_total numeric(12,2)
);

comment on table moley.orders is 'A table to store the unique orders';

create table if not exists moley.vendors (
    vendor_id uuid primary key,
    name varchar not null,
    address uuid references moley.addresses(address_id),
    contact varchar,
    contact_phone_number varchar,
    contact_email_address varchar,
    website varchar
);

comment on table moley.vendors is 'A table to store the vendors of products';

create table if not exists moley.products(
    product_id uuid primary key,
    vendor_id uuid references moley.vendors(vendor_id),
    name varchar not null,
    sku varchar unique not null,
    quantity_on_hand int,
    order_threshold int
);

comment on table moley.products is 'A table to store all of the unique products for sale';

create table if not exists moley.order_lines (
    order_line_id uuid primary key,
    order_id uuid references moley.orders(order_id),
    product_id uuid references moley.products(product_id),
    quantity int,
    line_total numeric(12,2)
);

comment on table moley.order_lines is 'A joining table allowing an order to contain multiple products';

create index if not exists idx_customer_last_name on moley.customers(last_name);
create index if not exists idx_cust_address_type on moley.customer_addresses (customer_id, type);
create index if not exists idx_order_date on moley.orders(order_date);
create index if not exists idx_vendor_name on moley.vendors(name);
create index if not exists idx_product_name on moley.products(name);


