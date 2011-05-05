-- Authors: Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer

--Drop tables 
DROP TABLE Groups CASCADE CONSTRAINTS;
DROP TABLE Subtaskbids CASCADE CONSTRAINTS;
DROP TABLE Subtasks CASCADE CONSTRAINTS;
DROP TABLE Taskbids CASCADE CONSTRAINTS;
DROP TABLE Tasks CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Locations CASCADE CONSTRAINTS;
DROP TABLE Conversions CASCADE CONSTRAINTS;

-- Drop sequences
DROP sequence SubtaskIdSeq;
DROP sequence SubtaskBidIdSeq;
DROP sequence TaskIdSeq;
DROP sequence TaskBidIdSeq;
DROP sequence UserIdSeq;

--Create Tables
CREATE TABLE Locations ( 
location VARCHAR2(20) NOT NULL PRIMARY KEY, 
hourlyRate NUMBER(5,2) NOT NULL,
currency CHAR(3) NOT NULL
);


CREATE TABLE Users (
userId NUMBER(11) NOT NULL PRIMARY KEY,
securityLayer NUMBER(2) NOT NULL, 
name VARCHAR2(255) NOT NULL, 
password VARCHAR2(255) NOT NULL,
location VARCHAR2(20) NOT NULL REFERENCES Locations
);

CREATE TABLE Tasks ( 
taskId NUMBER(11) NOT NULL PRIMARY KEY, 
userId NUMBER(11) NOT NULL REFERENCES Users, -- task owner
taskName VARCHAR2(255) NOT NULL, 
taskDescription VARCHAR2(255) NOT NULL, 
taskCreated DATE NOT NULL,
deadlineBid DATE NOT NULL,
taskPrice Number(30,2),
taskAuctionEnded CHAR(1) CHECK (taskAuctionEnded IN ('Y', 'N')) NOT NULL
);

CREATE TABLE Taskbids ( 
taskbidId NUMBER(11) NOT NULL PRIMARY KEY, 
taskId NUMBER(11) NOT NULL REFERENCES Tasks,
userId NUMBER(11) NOT NULL REFERENCES Users, -- task manager
taskBidHour FLOAT(126) NOT NULL, 
taskbidWon CHAR(1) CHECK (taskbidWon IN ('Y', 'N')) NOT NULL
);


CREATE TABLE Subtasks ( 
subtaskId NUMBER(11) NOT NULL PRIMARY KEY, 
taskId NUMBER(11) NOT NULL REFERENCES Tasks, 
subtaskName VARCHAR2(255) NOT NULL, 
subtaskDescription VARCHAR2(255) NOT NULL,
subtaskHour NUMBER(11) NOT NULL,
subtaskCreated DATE NOT NULL,
subtaskDeadline DATE NOT NULL,
subtaskAuctionEnded CHAR(1) CHECK (subtaskAuctionEnded IN ('Y', 'N')) NOT NULL,
subtaskComplete CHAR(1) CHECK (subtaskComplete IN ('Y', 'N')) NOT NULL
);


CREATE TABLE Subtaskbids(
subtaskBidId NUMBER(11) NOT NULL PRIMARY KEY ,
subtaskId NUMBER(11) NOT NULL REFERENCES Subtasks,
subtaskBidHours NUMBER(11) NOT NULL, 
subtaskbidWon CHAR(1) CHECK (subtaskbidWon IN ('Y', 'N')) NOT NULL
);

CREATE TABLE Groups ( 
subtaskBidId NUMBER(11) NOT NULL REFERENCES SubtaskBids,
userId NUMBER(11) NOT NULL REFERENCES Users, -- task solver 
PRIMARY KEY (subtaskBidId, userID)
);

CREATE TABLE Conversions (
Conversion CHAR(6) NOT NULL,
Rate NUMBER(5,5)
);


-- create sequences
create sequence SubtaskIdSeq;
create sequence SubtaskBidIdSeq;
create sequence TaskIdSeq;
create sequence TaskBidIdSeq;
create sequence UserIdSeq;