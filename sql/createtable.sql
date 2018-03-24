create database moviedb;
use moviedb;
CREATE TABLE if not exists moviedb.movies (
  id varchar(10) NOT NULL,
  title varchar(100) NOT NULL,
  year int(11) NOT NULL,
  director varchar(100) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE if not exists moviedb.stars (
  id varchar(10) NOT NULL,
  name varchar(100) NOT NULL,
  birthYear integer,
  PRIMARY KEY (id)
); 
create table if not exists moviedb.stars_in_movies (
starID varchar(10) not null ,
movieID varchar(10) not null, 
foreign key (starID)references stars(id) on delete cascade,
foreign key (movieID)references movies(id) on delete cascade

);
create table if not exists moviedb.genres(
id integer not null auto_increment,
name varchar(32) not null,
primary key(id)

);
create table if not exists moviedb.creditcards(
id varchar (20) not null,
firstName varchar (50) not null,
lastName varchar(50) not null,
expiration date,
primary key(id)
);
create table if not exists moviedb.genres_in_movies(
genreId integer not null,
movieId varchar (10) not null,
foreign key(genreID) references genres(id) on delete cascade,
foreign key(movieID) references movies(id) on delete cascade
);
create table if not exists moviedb.customers(
id integer not null auto_increment,
firstName varchar(50) not null,
lastName varchar(50) not null,
ccId varchar(20) not null,
address varchar (200) not null,
email varchar(50) not null,
password varchar(20) not null,
primary key (id),
foreign key (ccId) references creditcards(id) on delete cascade
);


create table if not exists moviedb.ratings(
movieId varchar(10) not null,
rating float not null,
numVotes integer,
foreign key(movieId) references movies(id) on delete cascade

);
create table if not exists moviedb.sales(
id integer not null auto_increment,
customerId integer not null,
movieId varchar(10) not null,
saleDate date not null,
primary key (id),
foreign key(customerId) references moviedb.customers(id) on delete cascade,
foreign key(movieId) references moviedb.movies(id) on delete cascade
);
create table if not exists moviedb.employee (
email varchar(50) primary key,
password varchar(20) not null,
fullname varchar(100)
);


DELIMITER $$
CREATE PROCEDURE add_movie(titl VARCHAR(100),dire VARCHAR(100),g VARCHAR(32), y INT(11),  
sname VARCHAR(100),
byear int(11))
BEGIN
		DECLARE movieid char(10) DEFAULT NULL;
		DECLARE starid char(10) DEFAULT NULL;
		DECLARE genreid INT(11) DEFAULT NULL;
		DECLARE directorname char(100) default NULL;
    if (select id from movies where title = titl and director = dire and y = year) is not null then
		select concat(titl," is already in databse!");

	else

			SET movieid = (select id from movies where title = titl);
			SET starid = (select id from stars where name = sname);
			SET genreid = (select id from genres where name = g);
		
			
			SET movieid = (SELECT  CONCAT('tt0',CAST(CONVERT(SUBSTRING_INDEX(max(id),'t',-1),UNSIGNED INTEGER)+1 as char(11))) AS num FROM movies);
			INSERT INTO movies(id,title,year,director) VALUES (movieid,titl,y,dire);
			
			IF starid IS NOT NULL THEN
				INSERT INTO stars_in_movies VALUES(starid,movieid);
			ELSE
				SET starid = (SELECT CONCAT('nm',CAST(CONVERT(SUBSTRING_INDEX(max(id),'m',-1),UNSIGNED INTEGER)+1 as char(10))) AS num FROM moviedb.stars);
				INSERT INTO stars VALUES(starid,sname,byear);
				INSERT INTO stars_in_movies VALUES(starid,movieid);
			END IF;
			IF genreID IS NOT NULL THEN
				INSERT INTO  genres_in_movies VALUES(genreid,movieid);
			ELSE
				INSERT INTO genres(name) VALUES (g);
				SET genreid = (select id from genres where name = g);
				INSERT INTO genres_in_movies VALUES (genreid,movieid);
			END IF;
		end if;

	
    
END
$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE add_star(fullname varchar(100),byear int (11))
BEGIN
	DECLARE starid char(10) default NULL;
	IF (select id from stars where fullname = name and byear = birthYear) IS NOT NULL THEN
		select concat(fullname, "is already in database!");
	else
		SET starid = (SELECT CONCAT('nm',CAST(CONVERT(SUBSTRING_INDEX(max(id),'m',-1),UNSIGNED INTEGER)+1 as char(10))) AS num FROM moviedb.stars);
		INSERT INTO stars VALUES(starid,fullname,byear);
    END IF;
END
$$



DELIMITER ;


alter table movies
add FULLTEXT(title);
alter table stars
add FULLTEXT(name);





