DELETE from course_student;
DELETE from student;
DELETE from course;
DELETE from professor;

INSERT INTO professor(id,name,university_name) VALUES ('someid','Alex','princeton');
INSERT INTO course(id,university_name,title,professor_id) VALUES ('someid','princeton','emglish','someid');
INSERT INTO student(id,university_name,name) VALUES ('studentId','princeton','Alex');
INSERT INTO course_student(course_id,student_id)VALUES('someid','studentId');