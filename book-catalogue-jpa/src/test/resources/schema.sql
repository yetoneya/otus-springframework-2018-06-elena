DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(id BIGINT(20) NOT NULL AUTO_INCREMENT,authorname VARCHAR(20),book_id BIGINT(20),PRIMARY KEY(id));
CREATE TABLE BOOK(id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(50),genre VARCHAR(20),PRIMARY KEY(id));