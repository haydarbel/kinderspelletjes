insert into orders(ordered, required, shipped, comments, customerId, status)
VALUES ('2021-01-01', '2021-01-01', '2021-01-01', 'test', (select id from customers where name = 'test'), 'DISPUTED');

