CREATE TABLE testing.persons
(
  person_id  INTEGER NOT NULL,
  last_name  VARCHAR(255),
  first_name VARCHAR(255),
  address    VARCHAR(255),
  city       VARCHAR(255),
  CONSTRAINT persons_pk PRIMARY KEY (person_id)
);

CREATE TABLE testing.orders
(
  order_id          INTEGER NOT NULL,
  order_no          INTEGER NOT NULL,
  order_date        DATE    NOT NULL,
  shipping_datetime TIMESTAMP,
  shipped           BOOLEAN NOT NULL,
  person_id         INTEGER,
  CONSTRAINT orders_pk PRIMARY KEY (order_id),
  CONSTRAINT persons_fk FOREIGN KEY (person_id) REFERENCES persons (person_id)
);

CREATE TABLE testing.items
(
  item_id    INTEGER        NOT NULL,
  item_name  VARCHAR(255)   NOT NULL,
  item_price NUMERIC(10, 4) NOT NULL,
  CONSTRAINT items_pk PRIMARY KEY (item_id)
);

CREATE TABLE testing.order_items(
  order_id INTEGER NOT NULL,
  item_id  INTEGER NOT NULL,
  qty      INTEGER NOT NULL,
  CONSTRAINT order_items_pk PRIMARY KEY (order_id, item_id),
  CONSTRAINT order_fk FOREIGN KEY (order_id) REFERENCES orders (order_id),
  CONSTRAINT items_fk FOREIGN KEY (item_id) REFERENCES items (item_id)
);

