
INSERT INTO  Locations VALUES ('Denmark', 200, 'DKK');
INSERT INTO  Locations VALUES ('USA', 30, 'USD');
INSERT INTO  Locations VALUES ('China', 122, 'CNY');


INSERT INTO Users VALUES (2, 25, 'Johnny Jensen', 'XXX', 'Denmark');
INSERT INTO Users VALUES (3, 25,'Jonathan Anasta','XXX','USA');
INSERT INTO Users VALUES (4, 25,'Jackie Chan','XXX','China');
INSERT INTO Users VALUES (11, 50,'Daniel Sampi','XXX','USA');
INSERT INTO Users VALUES (12, 50,'Anthony Arena','XXX','China');
INSERT INTO Users VALUES (13, 50,'Casper Caspersen','XXX','Denmark');
INSERT INTO Users VALUES (111, 75,'Xiu Wang','XXX','China');
INSERT INTO Users VALUES (112, 75,'Carl Carlsen','XXX','Denmark');
INSERT INTO Users VALUES (113, 75,'Jesper Hvid','XXX','Denmark');
INSERT INTO Users VALUES (114, 75,'Brian Johnson','XXX','USA');
INSERT INTO Users VALUES (115, 75,'Bo Hund','XXX','Denmark');

INSERT INTO Tasks  VALUES (80001, 2, 'First Task', 'Make a program',to_date('12-MAR-11','DD MON YY'), to_date('12-APR-11','DD MON YY'), 20000, 'N');
INSERT INTO Tasks  VALUES (86501, 2, 'Test Task2', 'description2',to_date('10-MAR-11','DD MON YY'), to_date('13-APR-11','DD MON YY'), 19000, 'N');
INSERT INTO Tasks  VALUES (89001, 4, 'Test Task3', 'Description3',to_date('26-MAR-11','DD MON YY'), to_date('14-APR-11','DD MON YY'), 5800, 'N');
INSERT INTO Tasks  VALUES (57747, 2, 'Test Task4', 'Description4',to_date('13-MAR-11','DD MON YY'), to_date('15-APR-11','DD MON YY'), 45600, 'N');
INSERT INTO Tasks  VALUES (99035, 4, 'Test Task5', 'Description5',to_date('18-MAR-11','DD MON YY'), to_date('16-APR-11','DD MON YY'), 92500, 'N');
INSERT INTO Tasks  VALUES (50001, 3, 'Printing Task', 'Printing out 4 differents output',to_date('24-FEB-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), 5000, 'Y');

INSERT INTO Taskbids  VALUES (040001, 50001, 11, 100, 'N');
INSERT INTO Taskbids  VALUES (040002, 50001, 12, 80, 'N');
INSERT INTO Taskbids  VALUES (040003, 80001, 13, 60, 'Y');
INSERT INTO Taskbids  VALUES (040004, 80001, 11, 120, 'N');

INSERT INTO Subtasks  VALUES (800011, 80001, '1st part', 'Do the first part',20,to_date('24-APR-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), 'N', 'N');
INSERT INTO Subtasks  VALUES (800012, 80001, '2nd part', 'Do the second part',10,to_date('24-APR-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), 'N', 'N');
INSERT INTO Subtasks  VALUES (800013, 80001, '3rd part', 'Do the third part',30,to_date('24-APR-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), 'N', 'N');

INSERT INTO Subtaskbids VALUES (18000111, 800011, 18,'N');
INSERT INTO Subtaskbids VALUES (18000112, 800011, 10,'N');
INSERT INTO Subtaskbids VALUES (18000113, 800011, 12,'N');
INSERT INTO Subtaskbids VALUES (18000131, 800013, 10,'N');
INSERT INTO Subtaskbids VALUES (18000231, 800013, 10,'N');
INSERT INTO Subtaskbids VALUES (18000121, 800012, 17,'N');
INSERT INTO Subtaskbids VALUES (18000221, 800013, 8,'N');
INSERT INTO Subtaskbids VALUES (18000321, 800013, 8,'N');

--1 person group
INSERT INTO Groups VALUES (18000111, 112);
INSERT INTO Groups VALUES (18000112, 113);
INSERT INTO Groups VALUES (18000113, 112);
--2 person group
INSERT INTO Groups VALUES (18000131, 113);
INSERT INTO Groups VALUES (18000131, 115);

INSERT INTO Groups VALUES(18000231, 111);
INSERT INTO Groups VALUES(18000231, 114);

--3 person group
INSERT INTO Groups VALUES(18000121, 111);
INSERT INTO Groups VALUES(18000121, 112);
INSERT INTO Groups VALUES(18000121, 113);

INSERT INTO Conversions VALUES('DKKUSD', 0.1869);
INSERT INTO Conversions VALUES('DKKCNY', 0.1869);
INSERT INTO Conversions VALUES('USDDKK', 0.1869);
INSERT INTO Conversions VALUES('USDCNY', 0.1869);
INSERT INTO Conversions VALUES('CNYUSD', 0.1869);
INSERT INTO Conversions VALUES('CNYDKK', 0.1869);