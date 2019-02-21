-- hw8
use newDatabase;

Update ClassStudent
SET SignUpDate = date_add(SignUpDate ,Interval ID HOUR ) Where ID >0;

select * from ClassStudent;

Select * , (Select count(*) 
From ClassStudent cs
Where cs.ClassID = c.ID
group by cs.ClassID) as enroll
from Class c;

Select Class.*
from Class
left join(Select StudentID as SID, Count(*) as c
from ClassStudent
group by StudentID) as enroll on Class.Id = enroll.CID;

-- #1
select Code, count(*) as TotalEnroll from Student
left join ClassStudent on Student.ID = ClassStudent.StudentID
left join Class on Class.ID = ClassStudent.ClassID
group by Code;


select count(FirstName) as TotalEnroll from Student
left join ClassStudent on Student.ID = ClassStudent.StudentID
left join Class on Class.ID = ClassStudent.ClassID;

-- #2 
select Code, count(*) as TotalEnroll from Student
left join ClassStudent on Student.ID = ClassStudent.StudentID
left join Class on Class.ID = ClassStudent.ClassID
group by Code;

select * from Class 
left join(select ClassID as id, count(*) as total from ClassStudent 
group by ClassID) as TotalEnroll
on Class.ID = TotalEnroll.id;

