insert into users (id, active, age, email, first_name, gender, last_name, password)
values ('c08d68bf-7687-4e14-8d44-d08881d4305c', true, 0, 'user@user.com', 'user', 'MALE', 'user', 'user');

insert into user_role (user_id, roles)
values ('c08d68bf-7687-4e14-8d44-d08881d4305c', 'USER');

insert into users (id, active, age, email, first_name, gender, last_name, password)
values ('0317897e-2f2c-465e-b1bc-e3fcde370eb2', true, 0, 'manager@manager.com', 'manager', 'MALE', 'manager', 'manager');

insert into user_role (user_id, roles)
values ('0317897e-2f2c-465e-b1bc-e3fcde370eb2', 'MANAGER');