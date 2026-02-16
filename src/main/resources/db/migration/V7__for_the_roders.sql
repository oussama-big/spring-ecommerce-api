create table orders (
	id bigint not null auto_increment PRIMARY KEY ,
    customer_id bigint not null,
    status varchar(20) default 'PENDING',
    created_at datetime default current_timestamp not null ,
    total_price decimal(10,2),
    constraint fk_customer_Order_id foreign key (customer_id) references users(id) on delete cascade
);

create table order_items(
	id bigint not null auto_increment ,
    order_id bigint not null ,
    product_id bigint not null ,
    unit_price decimal(10,2) not null ,
    quantity int ,
    total_price decimal(10,2),
    constraint pk_order_items PRIMARY KEY(id),
	constraint fk_orderItem_Order_id foreign key (order_id) references orders(id) on delete cascade,
    constraint fk_orderItem_products_id foreign key(product_id) references products(id) on delete restrict
);