insert into orderdetails(orderId, productId, ordered, priceEach)
VALUES ((select id from orders where comments = 'test'),
        (select id from products where name = 'test1'), 30, 3000),
       ((select id from orders where comments = 'test'),
        (select id from products where name = 'test2'), 60, 6000);

