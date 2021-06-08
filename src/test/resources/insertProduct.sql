insert into products (name, scale, description, inStock, inOrder, price, productlineId)
values ('test1', 'test', 'test', 100, 50, 1000, (select id from productlines where name = 'test')),
       ('test2', 'test', 'test', 20,10, 2000, (select id from productlines where name = 'test'));
