'''mermaid
erDiagram
    movies {
        movie_id uuid PK
        title varchar 
        release_year int
        studio varchar
        length int
        total_copies int
    }

    members {
        member_id uuid PK
        first_name varchar
        last_name varchar
        street_address varchar
        city varchar
        state varchar
        postal_code varchar
        dl_number varchar
        dl_state varchar
    }

    checkouts {
        checkout_id uuid PK
        member_id uuid FK
        date datetime
        due_date datetime
        subtotal decimal
        sales_tax decimal
    }

    checkout_items {
        checkout_item_id uuid PK
        checkout_id uuid FK
        movie_id uuid FK
    }
    

    checkout_items }|--|{ movies : contains
    checkouts ||--|{ checkout_items : contains

'''
