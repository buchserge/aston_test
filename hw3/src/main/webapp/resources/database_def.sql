CREATE TABLE professor (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE course (
  id_course bigint NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  professor_id bigint DEFAULT NULL,
  PRIMARY KEY (id_course),
  KEY fk_prof (professor_id),
  CONSTRAINT fk_prof FOREIGN KEY (professor_id) REFERENCES professor (id)
);

CREATE TABLE student (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE course_student (
  course_id bigint NOT NULL,
  student_id bigint NOT NULL,
  PRIMARY KEY (course_id,student_id),
  KEY student_id (student_id),
  CONSTRAINT course_student_fk_1 FOREIGN KEY (course_id) REFERENCES course (id_course),
  CONSTRAINT course_student_fk_2 FOREIGN KEY (student_id) REFERENCES student (id)
);