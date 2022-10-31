insert into users (id, active, age, email, first_name, gender, last_name, password)
values ('7ebb1e6f-9769-47a4-b8c4-a27e592701b7', true, 0, 'admin@admin.com', 'admin', 'MALE', 'admin', 'admin');

insert into user_role (user_id, roles)
values ('7ebb1e6f-9769-47a4-b8c4-a27e592701b7', 'ADMIN');