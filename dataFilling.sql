DELETE FROM PRODUCT_CATEGORY;
DELETE FROM CATEGORIES;
DELETE FROM PRODUCTS;
DELETE FROM USERS;

DROP SEQUENCE USER_ID_S;
DROP SEQUENCE PRODUCT_ID_S;
DROP SEQUENCE FOLLOWING_ID_S;
DROP SEQUENCE CATEGORY_ID_S;

CREATE SEQUENCE USER_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE PRODUCT_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE FOLLOWING_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE CATEGORY_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;

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
    TO_DATE('1990/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
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
    USER_ID_S.NEXTVAL,
    '123',
    '202cb962ac59075b964b07152d234b70',--123
    'alex',
    'smith',
    TO_DATE('1990/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'alex.alex@gmail.com',
    '066090000',
    SYSDATE,
    'user'
);

INSERT INTO CATEGORIES(
    ID,--1
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Mobiles'
);

INSERT INTO CATEGORIES(
    ID,--2
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Appliances'
);

INSERT INTO CATEGORIES(
    ID,--3
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Computers'
);

INSERT INTO CATEGORIES(
    ID,--4
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'Android'
);

INSERT INTO CATEGORIES(
    ID,--5
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'Apple'
);

INSERT INTO CATEGORIES(
    ID,--6
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'Microsoft'
);

INSERT INTO CATEGORIES(
    ID,--7
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    'Large appliances'
);

INSERT INTO CATEGORIES(
    ID,--8
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    'Small appliances'
);

INSERT INTO CATEGORIES(
    ID,--9
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Refrigerators'
);

INSERT INTO CATEGORIES(
    ID,--10
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Microwaves'
);

INSERT INTO CATEGORIES(
    ID,--11
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Food processors'
);

INSERT INTO CATEGORIES(
    ID,--12
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Washers'
);

INSERT INTO CATEGORIES(
    ID,--13
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Vacuum cleaners'
);

INSERT INTO CATEGORIES(
    ID,--14
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Scales'
);

INSERT INTO CATEGORIES(
    ID,--15
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Toasters'
);

INSERT INTO CATEGORIES(
    ID,--16
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Mixers'
);

INSERT INTO CATEGORIES(
    ID,--17
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Blenders'
);

INSERT INTO CATEGORIES(
    ID,--18
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Coffee Makers'
);

INSERT INTO CATEGORIES(
    ID,--19
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Hair Dryers'
);

INSERT INTO CATEGORIES(
    ID,--20
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Shavers'
);

INSERT INTO CATEGORIES(
    ID,--21
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Fans'
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
    'iPhone 1',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    1200,
    10000,
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
    'iPhone 2',
    'Old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    2200,
    20000,
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
    'iPhone 3',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    3200,
    30000,
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
    'iPhone 4',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    4200,
    40000,
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
    'iPhone 5',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    5200,
    50000,
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
    'LG g1',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    5200,
    50000,
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
    'LG g1',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    5200,
    50000,
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
    'LG g1',
    'Very old',
    TO_DATE('2015/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    5200,
    50000,
    NULL,
    NULL,
    'active'
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    1,
    5
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    2,
    5
);


INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    3,
    5
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    4,
    5
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    5,
    5
);
    
INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    6,
    4
);
    
INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    7,
    4
);

INSERT INTO PRODUCT_CATEGORY(
    PRODUCT_ID,
    CATEGORY_ID
) VALUES(
    8,
    4
);

commit;