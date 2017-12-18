create database if not exists PostUser;
create user if not exists postuser_admin identified by 'Jpa2017*';
grant all privileges on postuser2.* to 'postuser_admin'@'localhost' identified by 'Jpa2017*';

-- mysql -u root -p < C:\create_database.sql