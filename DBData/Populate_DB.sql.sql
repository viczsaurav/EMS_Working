use ems;
CREATE TABLE `USERACCESS` (
  `USERID` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ISFIRSTLOGIN` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `USERACCESS` VALUES ('abhinav','test','N'),('dilbert','password','N'),('nisith','test','N'),('sharique','password','Y');

CREATE TABLE `USERACCESS_GROUPS` (
  `GROUPID` varchar(255) NOT NULL,
  `USERID` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `USERACCESS_GROUPS` VALUES ('student','abhinav'),('student','nisith'),('lecturer','dilbert'),('student','sharique');

INSERT INTO `APPUSER` VALUES (1,'Y','abhinav','Abhinav','password','student'),(2,'Y','nisith','Nisith','password','student'),(3,'N','dilbert','Dilbert','password','lecturer'),(4,'Y','sharique','Sharique','password','student');



INSERT INTO `COURSEMODULE` VALUES (1,'2014-11-17',3,'MPSH','OOP','19:00:00'),
(2,'2014-11-18',3,'MPSH','OODP','19:00:00'),
(3,'2014-11-19',3,'MPSH','HCI','19:00:00'),
(4,'2014-11-20',3,'MPSH','EJAVA','19:00:00'),
(5,'2014-11-21',3,'MPSH','RE','19:00:00'),
(6,'2014-11-22',3,'MPSH','PYTHON','19:00:00');

INSERT INTO `QUESTION` VALUES (1,'ESSAY',2,'What is boiling point of water?','ESSAY',2),(2,'MULTI_CHOICE',56,'Hwllo worled?','MULTI_CHOICE',3),(3,'MULTI_CHOICE',56,'Hwllo worled?','MULTI_CHOICE',4);

INSERT INTO `QuestionMultiChoice_CHOICES` VALUES (3,'Choice 0'),(3,'Choice 1'),(3,'Choice 2');

INSERT INTO `SUBJECT` VALUES (1,'Physics'),(2,'Chemistry'),(3,'Mathematics');

INSERT INTO `QUESTION_SUBJECT` VALUES (2,1),(1,2),(2,2),(3,2),(1,3),(2,3),(3,3);

INSERT INTO `STUDENT` VALUES (1,'Abhinav',1),(2,'Nisith',2),(4,'Sharique',4);

INSERT INTO `STUDENT_COURSEMODULES` VALUES (1,1),(2,1),(4,1),(1,2),(2,2),(4,2),(1,3),(2,3),(4,4);

INSERT INTO `LECTURER` VALUES (1,'Dilbert',3);

INSERT INTO APPUSER VALUES ('5','N','admin','Administrator','admin','admin');

insert into USERACCESS VALUES ('admin','admin','N');

INSERT INTO USERACCESS_GROUPS (GROUPID, USERID) VALUES ('admin', 'admin')