CREATE SCHEMA IF NOT EXISTS SHOPPING_CART;

CREATE TABLE IF NOT EXISTS SHOPPING_CART.SHOPPING_CARTS
(
  ID UUID NOT NULL,
  USERNAME VARCHAR(256) NOT NULL,
  STATE VARCHAR(50) NOT NULL,
  CONSTRAINT PK_SHOPPING_CARTS PRIMARY KEY (ID)
);
COMMENT ON TABLE SHOPPING_CART.SHOPPING_CARTS IS 'Содержит информацию о корзинах товаров';
COMMENT ON COLUMN SHOPPING_CART.SHOPPING_CARTS.ID IS 'Уникальный идентификатор';
COMMENT ON COLUMN SHOPPING_CART.SHOPPING_CARTS.USERNAME IS 'Имя пользователя';
COMMENT ON COLUMN SHOPPING_CART.SHOPPING_CARTS.STATE IS 'Статус корзины';

CREATE TABLE IF NOT EXISTS SHOPPING_CART.PRODUCTS
(
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
  SHOPPING_CARTID UUID NOT NULL REFERENCES SHOPPING_CART.SHOPPING_CARTS (ID) ON DELETE CASCADE,
  PRODUCTID UUID NOT NULL,
  QUANTITY INTEGER NOT NULL,
  CONSTRAINT PK_PRODUCTS PRIMARY KEY (ID)
);
COMMENT ON TABLE SHOPPING_CART.PRODUCTS IS 'Содержит информацию товарах в корзине';
COMMENT ON COLUMN SHOPPING_CART.PRODUCTS.ID IS 'Уникальный идентификатор';
COMMENT ON COLUMN SHOPPING_CART.PRODUCTS.SHOPPING_CARTID IS 'Корзина';
COMMENT ON COLUMN SHOPPING_CART.PRODUCTS.PRODUCTID IS 'Товар';
COMMENT ON COLUMN SHOPPING_CART.PRODUCTS.QUANTITY IS 'Количество товара';