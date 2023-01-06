CREATE DATABASE education_crm;
CREATE USER db_admin WITH encrypted password 'qwerty';
GRANT ALL PRIVILEGES ON SCHEMA public TO db_admin;