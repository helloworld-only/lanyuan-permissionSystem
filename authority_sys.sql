CREATE DATABASE authority_sys;

USE authority_sys;

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user(user_id INT PRIMARY KEY AUTO_INCREMENT,
		acct CHAR(8) UNIQUE NOT NULL,
		passwd CHAR(32) NOT NULL,
		user_name VARCHAR(10));
										
INSERT INTO t_user(acct, passwd, user_name) VALUES ('20221102','123','admin');
INSERT INTO t_user(acct, passwd, user_name) VALUES('20221107','123','guest');

SELECT * FROM t_user;


DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role(role_id INT PRIMARY KEY AUTO_INCREMENT,
		role_name VARCHAR(10) UNIQUE NOT NULL);
										
SELECT * FROM t_role;
										
INSERT INTO t_role(role_name) VALUES('admin');
INSERT INTO t_role(role_name) VALUES('guest');



										
										
DROP TABLE IF EXISTS user_role;										
CREATE TABLE user_role(id INT PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	FOREIGN KEY(user_id) REFERENCES t_user(user_id));
											
ALTER TABLE user_role ADD FOREIGN KEY(role_id) REFERENCES t_role(role_id);
										


DROP TABLE IF EXISTS t_auth;
CREATE TABLE t_auth(auth_id INT PRIMARY KEY AUTO_INCREMENT,
	auth_name VARCHAR(10) UNIQUE NOT NULL);
										
SELECT * FROM t_auth;

INSERT INTO t_auth(auth_name) VALUES('Login');
INSERT INTO t_auth(auth_name) VALUES('Insert');
INSERT INTO t_auth(auth_name) VALUES('Delete');
INSERT INTO t_auth(auth_name) VALUES('Update');
INSERT INTO t_auth(auth_name) VALUES('Select');

										


DROP TABLE IF EXISTS role_auth;
CREATE TABLE role_auth(id INT PRIMARY KEY AUTO_INCREMENT,
		role_id INT NOT NULL,
		auth_id INT NOT NULL,
		FOREIGN KEY(role_id) REFERENCES t_role(role_id),
		FOREIGN KEY(auth_id) REFERENCES t_auth(auth_id));