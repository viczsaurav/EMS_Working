
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

INSERT INTO `USER` VALUES (1,'Y','abhinav','Abhinav','password','student'),(2,'Y','nisith','Nisith','password','student'),(3,'N','dilbert','Dilbert','password','lecturer'),(4,'Y','sharique','Sharique','password','student');

INSERT INTO `COURSEMODULE` VALUES (1,'2014-11-17',3,'MPSH','Object Oriented Analysis and Design','19:00:00'),(2,'2014-11-18',3,'MPSH','Object Oriented Design Patterns','19:00:00'),(3,'2014-11-19',3,'MPSH','Advanced Estimation','19:00:00'),(4,'2014-11-20',3,'MPSH','Entreprise Java','19:00:00'),(5,'2014-11-21',3,'MPSH','Human Computer Interface','19:00:00'),(6,'2014-11-22',3,'MPSH','Software Quality Management','19:00:00');

INSERT INTO `QUESTION` VALUES (1,'ESSAY',2,'What is boiling point of water?','ESSAY',2,NULL),(2,'MULTI_CHOICE',56,'Hwllo worled?','MULTI_CHOICE',3,NULL),(3,'MULTI_CHOICE',56,'Hwllo worled?','MULTI_CHOICE',4,NULL);

INSERT INTO `QUESTIONMULTICHOICE` VALUES (2),(3);

INSERT INTO `QuestionMultiChoice_CHOICES` VALUES (3,'Choice 0'),(3,'Choice 1'),(3,'Choice 2');

INSERT INTO `SUBJECT` VALUES (1,'Physics'),(2,'Chemistry'),(3,'Mathematics');

INSERT INTO `QUESTION_SUBJECT` VALUES (2,1),(1,2),(2,2),(3,2),(1,3),(2,3),(3,3);
