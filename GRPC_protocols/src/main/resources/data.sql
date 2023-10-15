drop table user if exists;

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  gender varchar(50) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);


INSERT INTO user
VALUES
(1,'admin','John Doe','MALE', 28),
(2,'christian','Christian Bale','MALE', 35),
(3,'hugh','Hugh Jackman','MALE', 45),
(4,'ross','Ross Geller','MALE', 35),
(5,'chandler','Chandler Bing','MALE',35),
(6,'monica','Monica Geller','FEMALE',34),
(7,'rachel','Rachel Greene','FEMALE',40),
(8,'pheobe','Pheobe Buffay','FEMALE',40);