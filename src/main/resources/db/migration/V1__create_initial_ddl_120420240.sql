CREATE TABLE author (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE book (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    price DECIMAL(10, 2)
);

CREATE TABLE book_author (
    book_id UUID,
    author_id UUID,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE genre (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE book_genre (
    book_id UUID,
    genre_id BIGSERIAL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE user_account (
    id UUID PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE customer_order (
    id UUID PRIMARY KEY,
    user_account_id UUID,
    order_date TIMESTAMP,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id)
);

CREATE TABLE order_books (
    order_id UUID,
    book_id UUID,
    PRIMARY KEY (order_id, book_id),
    FOREIGN KEY (order_id) REFERENCES customer_order(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE inventory (
    id UUID PRIMARY KEY,
    book_id UUID,
    quantity INT,
    FOREIGN KEY (book_id) REFERENCES book(id)
);

CREATE TABLE order_history (
    id UUID PRIMARY KEY,
    user_account_id UUID,
    customer_order_id UUID,
    order_date TIMESTAMP,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id),
    FOREIGN KEY (customer_order_id) REFERENCES customer_order(id)
);

CREATE TABLE payment (
    id UUID PRIMARY KEY,
    user_account_id UUID,
    customer_order_id UUID,
    amount DECIMAL(10, 2),
    payment_date TIMESTAMP,
    payment_method VARCHAR(255),
    FOREIGN KEY (user_account_id) REFERENCES user_account(id),
    FOREIGN KEY (customer_order_id) REFERENCES customer_order(id)
);

CREATE TABLE shopping_cart (
    id UUID PRIMARY KEY,
    user_account_id UUID,
    FOREIGN KEY (user_account_id) REFERENCES user_account(id)
);

CREATE TABLE shopping_cart_books (
    shopping_cart_id UUID,
    book_id UUID,
    PRIMARY KEY (shopping_cart_id, book_id),
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);