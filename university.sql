/* SPECIFIC DATA TYPES */

CREATE DOMAIN N_NUMBER AS CHAR(9) CONSTRAINT N_NUMBER_FORMAT CHECK (VALUE LIKE "N[0-9]{8}$");

CREATE DOMAIN SSN AS CHAR(11) CONSTRAINT SSN_FORMAT CHECK (VALUE LIKE "[0-9]{3}-[0-9]{2}-[0-9]{4}$");

CREATE DOMAIN PHONE AS CHAR(14) CONSTRAINT PHONE_FORMAT CHECK (VALUE LIKE "\([0-9]{3}\) [0-9]{3}-[0-9]{4}$");

CREATE DOMAIN COURSE_NUMBER AS CHAR(7) CONSTRAINT COURSE_NUMBER_FORMAT CHECK (VALUE LIKE "[A-Z]{3} [0-9]{4}$");

CREATE DOMAIN DEGREE_PROGRAM AS VARCHAR(4) CONSTRAINT DEGREE_PROGRAM_OPTIONS CHECK (VALUE IN ("B.A.", "B.S.", "B.F.A", "M.A.", "M.S.", "M.B.A.", "Ph.D.", "Ed.D.", "J.D."));

CREATE DOMAIN GENDER AS VARCHAR(4,6) CONSTRAINT GENDER_OPTIONS CHECK (VALUE IN ("Male", "Female"));

CREATE DOMAIN STATE AS VARCHAR(4,13) CONSTRAINT STATE_OPTIONS CHECK (VALUE IN ("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont","Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"));

CREATE DOMAIN DEPARTMENT_CODE AS VARCHAR(1,4) CONSTRAINT DEPARTMENT_CODE_FORMAT CHECK (VALUE LIKE "[A-Z]{1,4}$");

CREATE DOMAIN CLASS AS VARCHAR(6,9) CONSTRAINT CLASS_OPTIONS CHECK (VALUE IN ("Freshman", "Sophomore", "Junior", "Senior", "Graduate"));

CREATE DOMAIN SEMESTER AS VARCHAR(4,8) CONSTRAINT SEMESTER_OPTIONS CHECK (VALUE IN ("Fall", "Spring", "Summer A", "Summer B"));

CREATE DOMAIN YEAR AS DECIMAL(4,0) CONSTRAINT YEAR_RANGE CHECK (VALUE >= 1965 AND VALUE <= 2050);

CREATE DOMAIN GRADE AS VARCHAR(1,2) CONSTRAINT GRADE_OPTIONS CHECK (VALUE IN ("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"));

/* ENTITIES */

CREATE TABLE PERSON ( -- Person Entity
	Nnumber N_NUMBER,
	
	Ssn SSN,
	
	Fname VARCHAR(20),
	
	Minit CHAR,
	
	Lname VARCHAR(20),
	
	Phone PHONE,
	
	Street VARCHAR(30),
	
	City VARCHAR(20),
	
	State STATE,
	
	Zip DECIMAL(5, 0),
	
	Bdate DATE;
	
	Gender GENDER;
	
	CONSTRAINT PERSON_PK
	
	PRIMARY KEY (Nnumber),
	
	CONSTRAINT PERSON_SK
	
	UNIQUE (Ssn);
);

CREATE TABLE DEPARTMENT ( -- Department Entity
	Dcode DEPARTMENT_CODE,
	
	Dname VARCHAR(30),
	
	Dcollege VARCHAR(30),
	
	Onumber DECIMAL(4,0),
	
	Ophone PHONE,
	
	CONSTRAINT DEPARTMENT_PK
	
	PRIMARY KEY (Dcode),
	
	CONSTRAINT DEPARTMENT_SK
	
	UNIQUE (Dname);
);

CREATE TABLE INSTRUCTOR ( -- Instructor Entity
	Nnumber N_NUMBER,
	
	Dept DEPARTMENT_CODE,
	
	Onumber DECIMAL(4,0),
	
	CONSTRAINT INSTRUCTOR_PK
	
	PRIMARY KEY (Nnumber),
	
	CONSTRAINT INSTRUCTOR_PERSON_FK
	
	FOREIGN KEY (Nnumber) REFERENCES PERSON(Nnumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE,
	
	CONSTRAINT INSTRUCTOR_DEPARTMENT_FK
	
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode)
	
	ON DELETE RESTRICT ON UPDATE CASCADE;
);

CREATE TABLE STUDENT ( -- Student Entity
	Nnumber N_NUMBER,
	
	Curr_street VARCHAR(30),
	
	Curr_city VARCHAR(20),
	
	Curr_state STATE,
	
	Curr_zip DECIMAL(5,0),
	
	Class CLASS,
	
	Degree_pgrm DEGREE_PROGRAM,
	
	Major_dept DEPARTMENT_CODE,
	
	Minor_dept DEPARTMENT_CODE,
	
	CONSTRAINT STUDENT_PK
	
	PRIMARY KEY (Nnumber),
	
	CONSTRAINT STUDENT_PERSON_FK
	
	FOREIGN KEY (Nnumber) REFFERENCES PERSON(Nnumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE,
	
	CONSTRAINT STUDENT_MAJOR_FK
	
	FOREIGN KEY (Major_dept) REFFERENCES DEPARTMENT(Dcode)
	
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT STUDENT_MINOR_FK
	
	FOREIGN KEY (Minor_dept) REFFERENCES DEPARTMENT(Dcode)
	
	ON DELETE SET NULL ON UPDATE CASCADE;
);

CREATE TABLE COURSE ( -- Course Entity
	Cnumber COURSE_NUMBER,
	
	Cname VARCHAR(30),
	
	Cdesc VARCHAR(50),
	
	Dept DEPARTMENT_CODE,
	
	Level DECIMAL(4,0),
	
	Hours INT,
	
	CONSTRAINT COURSE_PK
	
	PRIMARY KEY (Cnumber),
	
	CONSTRAINT COURSE_DEPARTMENT_FK
	
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode)
	
	ON DELETE RESTRICT ON UPDATE CASCADE;
);

CREATE TABLE SECTION ( -- Section Entity
	 Course COURSE_NUMBER,
	 
	 Instructor N_NUMBER,
	 
	 Snumber INT,
	 
	 Semester SEMESTER,
	 
	 Year YEAR,
	 
	 CONSTRAINT SECTION_PK
	 
	 PRIMARY KEY (Course, Snumber, Semester, Year),
	 
	 CONSTRAINT SECTION_COURSE_FK
	 
	 FOREIGN KEY (Course) REFERENCES COURSE(Cnumber)
	 
	 ON DELETE CASCADE ON UPDATE CASCADE,
	 
	 CONSTRAINT SECTION_INSTRUCTOR_FK
	 
	 FOREIGN KEY (Instructor) REFERENCES INSTRUCTOR(Nnumber)
	 
	 ON DELETE CASCADE ON UPDATE CASCADE;
);

CREATE TABLE PREREQUISITE ( -- Prerequisite: Course M:N Recurrence Relation
	Cnumber COURSE_NUMBER,
	
	Pnumber COURSE_NUMBER,
	
	CONSTRAINT PREREQUISITE_PK
	
	PRIMARY KEY (Cnumber, Pnumber),
	
	CONSTRAINT PREREQUISITE_CNUMBER_FK
	
	FOREIGN KEY (Cnumber) REFERENCES COURSE(Cnumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE,
	
	CONSTRAINT PREREQUISITE_PNUMBER_FK
	
	FOREIGN KEY (Pnumber) REFERENCES COURSE(Cnumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE,
	
	CONSTRAINT NOT_SELF_PREREQUISITE CHECK (Cnumber <> Pnumber);
);

CREATE TABLE ENROLLED_IN ( -- Enrolled_in: Student M:N Section Relation
	Student N_NUMBER,
	
	Course C_NUMBER,
	
	Semester SEMESTER,
	
	Year YEAR,
	
	Snumber INT,
	
	Grade GRADE,
	
	CONSTRAINT ENROLLED_IN_PK
	
	PRIMARY KEY (Student, Course, Year, Semester, Snumber),
	
	CONSTRAINT ENROLLED_IN_STUDENT_FK
	
	FOREIGN KEY (Student) REFERENCES STUDENT(Nnumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE,
	
	CONSTRAINT ENROLLED_IN_SECTION_FK
	
	FOREIGN KEY (Course, Year, Semester, Snumber) REFERENCES SECTION(Course, Year, Semester, Snumber)
	
	ON DELETE CASCADE ON UPDATE CASCADE;
);

/* TRIGGERS */

CREATE TRIGGER BEFORE_INSERT_SECTION
BEFORE INSERT ON SECTION
FOR EACH ROW
BEGIN
    DECLARE Max_snumber INT;

    SELECT COALESCE(MAX(Snumber), 0) INTO Max_snumber
    FROM SECTION
    WHERE Course = NEW.Course
      AND Semester = NEW.Semester
      AND Year = NEW.Year;

    SET NEW.Snumber = Max_snumber + 1;
END;

/*
INSERT INTO DEPARTMENT VALUES ("CS", "Engineering", "Computer science", 1167, "904-567-1234");

INSERT INTO PERSON VALUES ("N02345678", "345-22-1567", "Alex", None, "Smith", "904-123-6588", "1223 West Drive", "Jacksonville", "FL", 36182, "08/12/2024", "M");
INSERT INTO STUDENT VALUES ("N02345678", "123 West Drive", "Jacksonville", "FL", 36182, "Freshman", "B.S.", "CS", None);

INSERT INTO COURSE VALUES ("COP3503", "Programming II", "Teach Java", "CS", 3000, 3);

INSERT INTO COURSE VALUES ("COP3703", "Databases", "Introduce Databases", "CS", 3000, 3);
INSERT INTO PREREQUISITE VALUES ("COP3703", "COP3503");

INSERT INTO PERSON VALUES ("N04567123", "250-36-7345", "Dan", None, "Peters", "904-321-1250", "125 Souuth Drive", "Jacksonville", "FL", 36182, "01/01/1980", "M");
INSERT INTO INSTRUCTOR VALUES ("N04567123", "CS", 5567);

SELECT Cnumber, Cname, Cdesc FROM COURSE WHERE Dept="CS";

INSERT INTO SECTION (Course, Instructor, Semester, Year) VALUES ("COP3503", "N04567123", "Fall", 2020);
INSERT INTO SECTION (Course, Instructor, Semester, Year) VALUES ("COP3703", "N04567123", "Spring", 2021);

SELECT Course, Semester Year FROM SECTION WHERE Instructor="N04567123";

INSERT INTO ENROLLED_IN VALUES ("N02345678", "COP3503", "Fall", 2020, 1, "A");
INSERT INTO ENROLLED_IN VALUES ("N02345678", "COP3703", "Spring", 2021, 1, "A");

SELECT PREREQUISITE.Pnumber, ENROLLED_IN.Course FROM PREREQUISITE, ENROLLED_IN WHERE PREREQUISITE.Cnumber="COP3703" AND ENROLLED_IN.Course=PREREQUISITE.Pnumber;
*/