create table categories
(
    category_id varchar(255) not null,
    name        varchar(255),
    primary key (category_id)
);

create table features
(
    category_id varchar(255) not null,
    features    varchar(255)
);

create table manufacturers
(
    manufacturer_id varchar(255) not null,
    country         varchar(255),
    image           varchar(255),
    name            varchar(255),
    primary key (manufacturer_id)
);

create table orders
(
    order_id    varchar(255) not null,
    created     timestamp,
    status varchar(255),
    total_price numeric(19, 2),
    user_id     varchar(255),
    primary key (order_id)
);

create table product_order
(
    product_order_id varchar(255) not null,
    quantity            int4         not null,
    order_id         varchar(255),
    product_id       varchar(255),
    primary key (product_order_id)
);

create table products
(
    product_id      varchar(255) not null,
    description     varchar(255),
    image           varchar(255),
    name            varchar(255),
    price           numeric(19, 2),
    category_id     varchar(255),
    manufacturer_id varchar(255),
    primary key (product_id)
);

create table specifications
(
    product_id varchar(255) not null,
    value      varchar(255),
    feature    varchar(255) not null,
    primary key (product_id, feature)
);

create table user_role
(
    user_id varchar(255) not null,
    roles   varchar(255)
);

create table users
(
    user_id    varchar(255) not null,
    active     boolean      not null,
    age        int4         not null,
    email      varchar(255),
    first_name varchar(255),
    gender     varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    primary key (user_id)
);

alter table if exists features
    add constraint feature_category_fk
        foreign key (category_id) references categories;

alter table if exists orders
    add constraint order_user_fk
        foreign key (user_id) references users;

alter table if exists product_order
    add constraint product_order_order_fk
        foreign key (order_id) references orders;

alter table if exists product_order
    add constraint product_order_product_fk
        foreign key (product_id) references products;

alter table if exists products
    add constraint product_category_fk
        foreign key (category_id) references categories;

alter table if exists products
    add constraint product_manufacturer_fk
        foreign key (manufacturer_id) references manufacturers;

alter table if exists specifications
    add constraint specification_product_fk
        foreign key (product_id) references products;

alter table if exists user_role
    add constraint user_role_user_fk
        foreign key (user_id) references users;
