-- select a winning taskBid
update taskbids set taskbidWon = 'Y' where taskbidId = 040001;
update tasks set taskAuctionEnded = 'Y' where taskId = 50001;

-- show the tasks that are not ended (auction still going)
select * from Tasks where Tasks.taskAuctionEnded = 'N';

-- show just one task
select * from Tasks where taskId = 50001;

-- show jsut one taskBid
select * from taskBids where taskBidId = 040001;

-- create task
INSERT INTO Tasks  VALUES (80001, 1, 'First Task', 'Make a program',
to_date('12-MAR-11','DD MON YY'), to_date('12-APR-11','DD MON YY'), 20000, 'N');

-- create taskBid
INSERT INTO Taskbids  VALUES (040001, 50001, 11, 100, 'N');

-- select subtaskBid winner
update SUBTASKBIDS set subtaskBidWon = 'Y' where subtaskBidId = 18000111;
update subtasks set subtaskAuctionEnded = 'Y' where subtaskId = 800011;

-- show the subtasks that are not ended (auciton still going)
select * from subtasks where subtaskAuctionEnded = 'N';

-- show subtask by id (just one)
select * from subtask where subtaskId = 800011;

-- show subtaskBids by Id
select * from subtaskBids where subtaskBidId = 18000111;

-- create users
INSERT INTO Users VALUES (1, 25, 'Johnny Jensen', 'XXX', 'Denmark');

-- get user by taskId
select u.userId, u.securityLayer, u.name, u.password, u.location
from Users u, Tasks t where t.userId = u.userId and t.taskId = 50001;



--create subtask
INSERT INTO Subtasks  VALUES (800011, 80001, '1st part', 'Do the first part',
20,to_date('24-APR-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), 'N', 'N');

-- create subtaskBid
INSERT INTO Subtaskbids VALUES (18000111, 800011, 18,'N');

-- add user to subtaskBid
INSERT INTO Groups VALUES ('Denmark', 18000111, 112);

-- show the group
select Groups.userId, Groups.subtaskBidId, Users.name, 
Groups.location, SubtaskBids.subtaskId
from Groups, Users, SubtaskBids where Groups.subtaskBidId = 18000121
and Groups.userId = Users.userId
and Groups.subtaskBidId = SubtaskBids.subtaskBidId;

-- edit task
update Tasks set taskName = 'changed!', taskDescription = 'something else',
deadlineBid = to_date('24-APR-11','DD-MON-YY'), taskPrice = 30000.50
where taskId = 50001;

-- edit subtask
update Subtasks where subtaskName = 'PWNED!', subtaskDescription = 'may the noobness be with you!',
subtaskHour = 17, subtaskDeadline = to_date('24-APR-11','DD-MON-YY')
where subtaskId = 800011;

-- edit conversions
update Conversions set Rate = 0.55555 where Conversion = 'DKKUSD';

-- create conversions
INSERT INTO Conversions VALUES('DKKUSD', 0.1869);

-- edit Users
update Users set securityLayer = 25, name = 'Bobby Darin',
password = 'varchar-pass', location = 'USA'
where userId = 111;

-- register subtask as solved
update subtasks set subtaskComplete = 'Y' where subtaskId = 800011;

-- display task with location and conversion rates
--select Tasks.taskId, Users.location, Tasks.taskPrice, Locations.currency, Conversions.rate
--from Tasks, Conversions, Users, Locations
--where Tasks.taskId = 50001, Tasks.userId = Users.userId,
--Users.location = Locations.location,
--(Conversions.conversion = 'CNYDKK' or Conversions.conversion = 'USDDKK')
--group by Users.location, Tasks.taskId, Tasks.taskPrice, Location.currency;


select u.userId, u.securityLayer, u.name, u.password, u.location
from Users u, TaskBids tb, Subtasks s 
where tb.userId = u.userId and tb.taskbidWon = 'Y' and tb.taskId = s.taskId and s.subtaskId = 800012;