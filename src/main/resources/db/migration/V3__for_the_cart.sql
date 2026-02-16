create table carts 
(
	id binary(16) default (uuid_to_bin(uuid())) not null primary key,
    date_created date not null default (curdate())
);



create table cart_item
(
	id bigint auto_increment primary key,
    cart_id binary(16) not null,
    product_id bigint not null,
    quantity int not null default 1 ,
    constraint cart_item_cart_id_fk foreign key (cart_id) references carts(id) on delete cascade,
    constraint cart_item_products_id_fk foreign key(product_id) references products(id) on delete cascade
);