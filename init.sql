DROP TABLE PRODUCT_CATEGORY;
DROP TABLE TRANSACTIONS;
DROP TABLE CATEGORIES;
DROP TABLE PICTURES;
DROP TABLE FOLLOWINGS;
DROP TABLE PRODUCTS;
DROP TABLE USERS;

DROP SEQUENCE USER_ID_S;
DROP SEQUENCE PRODUCT_ID_S;
DROP SEQUENCE FOLLOWING_ID_S;
DROP SEQUENCE PICTURE_ID_S;
DROP SEQUENCE CATEGORY_ID_S;
DROP SEQUENCE TRANSACTION_ID_S;

CREATE SEQUENCE USER_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE PRODUCT_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE FOLLOWING_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE PICTURE_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE CATEGORY_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE TRANSACTION_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;

CREATE TABLE USERS 
(
  ID INT NOT NULL PRIMARY KEY,
  LOGIN VARCHAR2(30) NOT NULL UNIQUE,
  PASSWORD VARCHAR2(128) NOT NULL,
  NAME VARCHAR2(50) NOT NULL,
  SECOND_NAME VARCHAR2(50) NOT NULL,
  BIRTH DATE NOT NULL,
  EMAIL VARCHAR2(50) NOT NULL UNIQUE,
  PHONE VARCHAR2(20),
  REGISTRATION_DATE DATE NOT NULL,
  STATUS VARCHAR2(5) CHECK (STATUS IN ('user', 'admin', 'unactivated', 'banned')) NOT NULL
);


CREATE TABLE PRODUCTS
(
  ID INT NOT NULL PRIMARY KEY,
  SELLER_ID INT NOT NULL,
  NAME VARCHAR2(100) NOT NULL,
  DESCRIPTION VARCHAR2(1500),
  START_DATE DATE NOT NULL,
  END_DATE DATE NOT NULL,
  START_PRICE NUMBER NOT NULL,
  BUYOUT_PRICE NUMBER,
  CURRENT_PRICE NUMBER,
  CURRENT_BUYER_ID INT,
  IS_ACTIVE VARCHAR2(10) CHECK (IS_ACTIVE IN ('active', 'disactive')),
  CONSTRAINT SELLER_FK FOREIGN KEY(SELLER_ID) REFERENCES USERS(ID),
  CONSTRAINT BUYER_FK FOREIGN KEY(CURRENT_BUYER_ID) REFERENCES USERS(ID)
);

CREATE TABLE TRANSACTIONS 
(
  ID INT NOT NULL PRIMARY KEY,
  BUYER_ID INT NOT NULL,
  SELLER_ID INT NOT NULL,
  PRODUCT_ID INT NOT NULL,
  PRICE NUMBER NOT NULL,
  SALE_DATE DATE NOT NULL,
  CONSTRAINT BUYER_TRANS_FK FOREIGN KEY(BUYER_ID) REFERENCES USERS(ID),
  CONSTRAINT SELLER_TRANS_FK FOREIGN KEY(SELLER_ID) REFERENCES USERS(ID),
  CONSTRAINT PRODUCT_TRANS_FK FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCTS(ID)
);

CREATE TABLE FOLLOWINGS 
(
  ID INT NOT NULL PRIMARY KEY,
  FOLLOWER_ID INT NOT NULL,
  PRODUCT_ID INT NOT NULL,
  CONSTRAINT FOLLOWER_FK FOREIGN KEY(FOLLOWER_ID) REFERENCES USERS(ID),
  CONSTRAINT PRODUCT_FK FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCTS(ID)
);

CREATE TABLE PICTURES 
(
  ID INT NOT NULL PRIMARY KEY,
  PRODUCT_ID INT NOT NULL,
  URL VARCHAR2(100) NOT NULL,
  CONSTRAINT PRODUCT_PIC_FK FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCTS(ID)
);

CREATE TABLE CATEGORIES 
(
  ID INT NOT NULL PRIMARY KEY,
  PARENT_ID INT,
  NAME VARCHAR2(100) NOT NULL,
  CONSTRAINT PARENT_CATEGORY_FK FOREIGN KEY(PARENT_ID) REFERENCES CATEGORIES(ID)
);

CREATE TABLE PRODUCT_CATEGORY
(
  PRODUCT_ID INT NOT NULL,
  CATEGORY_ID INT NOT NULL,
  CONSTRAINT PRODUCT_ID_FK FOREIGN KEY(PRODUCT_ID) REFERENCES PRODUCTS(ID),
  CONSTRAINT CATEGORY_ID_FK FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORIES(ID)
);

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    USER_ID_S.NEXTVAL,
    'innova',
    '75e0793dd6d0c2bea73c9453b82101d0',
    'bohdan',
    'pedchenko',
    TO_DATE('1975/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'bogdan.innova@gmail.com',
    '0660915033',
    SYSDATE,
    'user'
);

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    0,
    'admin',
    '73acd9a5972130b75066c82595a1fae3',
    'admin',
    'admin',
    TO_DATE('2000/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'admin@gmail.com',
    '00000000',
    SYSDATE,
    'admin'
);

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    USER_ID_S.NEXTVAL,
    '123',
    '202cb962ac59075b964b07152d234b70',
    'alex',
    'smith',
    TO_DATE('2000/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'alex.alex@gmail.com',
    '066090000',
    SYSDATE,
    'user'
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
)VALUES(
    PRODUCT_ID_S.NEXTVAL,
    1,
    'Iron',
    'Good iron',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    120,
    1000,
    NULL,
    NULL,
    'active'
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
)VALUES(
    PRODUCT_ID_S.NEXTVAL,
    1,
    'Dildo',
    'Long dildo',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    100,
    1000,
    NULL,
    NULL,
    'active'
);


INSERT INTO TRANSACTIONS(
    ID,
    BUYER_ID,
    SELLER_ID,
    PRODUCT_ID,
    PRICE,
    SALE_DATE
)VALUES(
    TRANSACTION_ID_S.NEXTVAL,
    2,
    1,
    1,
    125,
    TO_DATE('2016/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')
);

INSERT INTO FOLLOWINGS(
    ID,
    FOLLOWER_ID,
    PRODUCT_ID
)VALUES(
    FOLLOWING_ID_S.NEXTVAL,
    2,
    1
);

INSERT INTO PICTURES(
    ID,
    PRODUCT_ID,
    URL
)VALUES(
    PICTURE_ID_S.NEXTVAL,
    1,
    'http\\address'
);

INSERT INTO CATEGORIES(
    ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    '1'
);

INSERT INTO CATEGORIES(
    ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    '2'
);

INSERT INTO CATEGORIES(
    ID,
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    '2.1'
);

INSERT INTO CATEGORIES(
    ID,
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    '2.2'
);

INSERT INTO CATEGORIES(
    ID,
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    '2.3'
);

INSERT INTO CATEGORIES(
    ID,
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    4,
    '2.2.1'
);


INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    1,
    2
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    1,
    3
);


INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    1,
    1
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    2,
    1
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    2,
    2
);

commit;