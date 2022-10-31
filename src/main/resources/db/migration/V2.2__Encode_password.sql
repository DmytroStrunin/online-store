create extension if not exists pgcrypto;

UPDATE users set password = crypt(password, gen_salt('bf', 8));