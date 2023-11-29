-- -----------------------------------------------------
-- Schema GOATrip
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `GOATrip` DEFAULT CHARACTER SET utf8 ;
USE `GOATrip` ;

-- -----------------------------------------------------
-- Table `GOATrip`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GOATrip`.`user` (
  `user_id` VARCHAR(30) NOT NULL primary KEY,
  `user_pw` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(30) NULL,
  `user_email` VARCHAR(45) NULL,
  `user_email_domain` VARCHAR(45) NULL,
  `user_joindate` TIMESTAMP NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GOATrip`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GOATrip`.`schedule` (
  `sch_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(30) NOT NULL,
  `shc_name` VARCHAR(45) NOT NULL,
  `is_important` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`sch_id`),
  FOREIGN KEY (`user_id`) REFERENCES `GOATrip`.`user` (`user_id`)
  )ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GOATrip`.`location_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GOATrip`.`location_type` (
  `location_type` INT NOT NULL AUTO_INCREMENT primary KEY,
  `type_name` VARCHAR(45) NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GOATrip`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GOATrip`.`location` (
  `location_id` INT NOT NULL AUTO_INCREMENT primary KEY,
  `location_type` INT NOT NULL,
  `location_title` VARCHAR(45) NOT NULL,
  `location_content` VARCHAR(300) NULL,
  `location_addr` VARCHAR(100) NULL,
  `location_latitude` DECIMAL(20,17) NULL,
  `location_longitude` DECIMAL(20,17) NULL,
    FOREIGN KEY (`location_type`)
    REFERENCES `GOATrip`.`location_type` (`location_type`)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GOATrip`.`schedule_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GOATrip`.`schedule_content` (
  `sch_content_id` INT NOT NULL AUTO_INCREMENT primary KEY,
  `location_id` INT NOT NULL,
  `sch_id` INT NOT NULL,
  `sch_content_date_end` TIMESTAMP NULL,
  `sch_content_date_start` TIMESTAMP NULL,
    FOREIGN KEY (`sch_id`)
    REFERENCES `GOATrip`.`schedule` (`sch_id`),
    FOREIGN KEY (`location_id`)
    REFERENCES `GOATrip`.`location` (`location_id`)
    )ENGINE = InnoDB;

create table board(
article_id int auto_increment primary key,
title varchar(50) not null,
category int not null,
writer varchar(30) not null,
content varchar(1000),
hit int default 0,
create_date timestamp default now(),
comment_num int default 0
);

CREATE TABLE `jwttoken` (
  `user_id` varchar(30) NOT NULL,
  `token` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `jwttoken_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table schedule(
	sch_id int auto_increment primary key,
    sch_name varchar(45),
    user_id varchar(30),
    create_date timestamp,
    start_lat decimal(20,17),
    start_long decimal(20,17),
	foreign key (user_id) references user(user_id)
);

create table stopover(
	sch_id int,
    content_id int,
    primary key (sch_id, content_id),
	foreign key(sch_id) references schedule(sch_id)
);

select * from user;

insert into user(user_id, user_pw, user_name, user_email, user_email_domain, user_joindate) values('ssafy','ssafy','김싸피','sfyyy','gmail.com',now());
insert into board(title, category,writer,content) values("test1",1,"ssafy","test 작성을 위한 내용입니다.");