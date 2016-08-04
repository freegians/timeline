DROP TABLE IF EXISTS `FOLLOWER`;

CREATE  TABLE `FOLLOWER` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '팔로우 인덱스',
  `USER_ID` bigint(20) unsigned NOT NULL COMMENT '유저 아이디',
  `FOLLOWER_ID` bigint(20) unsigned NOT NULL COMMENT '팔로워 아이디',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `FOLLOWER` WRITE;
INSERT INTO `FOLLOWER` (`USER_ID`, `FOLLOWER_ID`) VALUES (1,2);
INSERT INTO `FOLLOWER` (`USER_ID`, `FOLLOWER_ID`) VALUES (1,3);
UNLOCK TABLES;

DROP TABLE IF EXISTS `TIMELINE`;

CREATE TABLE `TIMELINE` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '타임라인 인덱스',
  `USER_ID` bigint(20) unsigned NOT NULL COMMENT '타임라인 주인',
  `WRITER_ID` bigint(20) unsigned NOT NULL COMMENT '글 작성자 인덱스',
  `WRITER_NAME` varchar(20) NOT NULL DEFAULT '' COMMENT '글 작성자 이름',
  `TIMELINE_TEXT` longtext NOT NULL COMMENT '타임라인 글',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '글 작성 시간',
  `ORIGINAL` tinyint(1) NOT NULL DEFAULT '1' COMMENT '원본글',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `TIMELINE` WRITE;

INSERT INTO `TIMELINE` (`USER_ID`, `WRITER_ID`, `WRITER_NAME`, `TIMELINE_TEXT`, `CREATED_DATE`, `ORIGINAL`)
VALUES
	(1,1,'freegians','first posting\n',CURRENT_TIMESTAMP,1),
	(1,1,'freegians','second posting\n',CURRENT_TIMESTAMP,1),
	(2,2,'user1','first posting by user1\n',CURRENT_TIMESTAMP,1),
	(2,2,'user1','second posting by user1\n',CURRENT_TIMESTAMP,1),
	(2,1,'freegians','first posting\n',CURRENT_TIMESTAMP,0),
	(2,1,'freegians','second posting\n',CURRENT_TIMESTAMP,0),
	(3,3,'user2','first posting by user2\n',CURRENT_TIMESTAMP,1),
	(3,3,'user2','second posting by user2\n',CURRENT_TIMESTAMP,1),
	(3,1,'freegians','first posting\n',CURRENT_TIMESTAMP,0),
	(3,1,'freegians','second posting\n',CURRENT_TIMESTAMP,0),
	(1,1,'freegians','aaaaaa\naaaa\naaa\naaaaa\n',CURRENT_TIMESTAMP,1);

UNLOCK TABLES;


DROP TABLE IF EXISTS `USER_ROLE`;

CREATE TABLE `USER_ROLE` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '인덱스',
  `USER_ID` bigint(20) unsigned NOT NULL COMMENT '유저 아이디',
  `ROLE_NAME` varchar(20) NOT NULL DEFAULT 'USER' COMMENT '유저 롤',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `USER_ROLE` WRITE;

INSERT INTO `USER_ROLE` (`USER_ID`, `ROLE_NAME`)
VALUES
	(1,'ROLE_USER'),
	(2,'ROLE_USER'),
	(3,'ROLE_USER');

UNLOCK TABLES;



DROP TABLE IF EXISTS `USERS`;

CREATE TABLE `USERS` (
  `USER_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '사용자 고유 키',
  `USER_NAME` varchar(20) DEFAULT NULL COMMENT '사용자 이름',
  `PASSWORD` varchar(255) NOT NULL DEFAULT '' COMMENT '사용자 비밀번호',
  `CREATED_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 추가 일시',
  `LAST_UPDATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 수정 일시',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `USERS` WRITE;

INSERT INTO `USERS` (`USER_NAME`, `PASSWORD`, `CREATED_DATE`, `LAST_UPDATE`)
VALUES
	('freegians','freegians',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('user1','user1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
	('user2','user2',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

UNLOCK TABLES;



DROP TABLE IF EXISTS `POST_Q`;

CREATE TABLE `POST_Q` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '인덱스',
  `TIMELINE_ID` bigint(20) NOT NULL COMMENT '타임라인 원본 아이디',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
