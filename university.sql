CREATE DOMAIN N_NUMBER AS DECIMAL(8,0) NOT NULL;
CREATE DOMAIN SSN AS DECIMAL(9,0) NOT NULL;
CREATE DOMAIN PHONE AS DECIMAL(10,0);
CREATE DOMAIN C_NUMBER AS VARCHAR(7) NOT NULL;
CREATE DOMAIN DEGREE_PROGRAM AS VARCHAR(4) NOT NULL;


CREATE TABLE PERSON ( #Person entity
	Nnumber N_NUMBER,
	Ssn SSN,
	Fname VARCHAR(20) NOT NULL,
	Minit CHAR,
	Lname VARCHAR(20) NOT NULL,
	Phone PHONE,
	Street VARCHAR(30),
	City VARCHAR(20),
	State CHAR(2),
	Zip DECIMAL(5, 0),
	Bdate DATE NOT NULL;
	Sex CHAR NOT NULL;
	PRIMARY KEY (Nnumber),
	UNIQUE (Ssn);
);

CREATE TABLE DEPARTMENT ( #Department entity
	Dcode VARCHAR(4),
	Dname VARCHAR(30) NOT NULL,
	Dcollege VARCHAR(30) NOT NULL,
	Onumber DECIMAL(4,0) NOT NULL,
	Ophone PHONE NOT NULL,
	PRIMARY KEY (Dcode),
	UNIQUE (Dname);
);

CREATE TABLE INSTRUCTOR ( #Instructor entity
	Nnumber N_NUMBER,
	Dept VARCHAR(4) NOT NULL,
	Onumber DECIMAL(4,0) NOT NULL,
	PRIMARY KEY (Nnumber),
	FOREIGN KEY (Nnumber) REFERENCES PERSON(Nnumber),
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode);
);

CREATE TABLE STUDENT ( #Student entity
	Nnumber N_NUMBER,
	Curr_street VARCHAR(30),
	Curr_city VARCHAR(20),
	Curr_state CHAR(2),
	Curr_zip DECIMAL(5,0),
	Class VARCHAR(15),
	Degree_pgrm DEGREE_PROGRAM,
	Major_dept VARCHAR(4) NOT NULL,
	Minor_dept VARCHAR(4),
	PRIMARY KEY (Nnumber),
	FOREIGN KEY (Nnumber) REFFERENCES PERSON(Nnumber),
	FOREIGN KEY (Major_dept) REFFERENCES DEPARTMENT(Dcode),
	FOREIGN KEY (Minor_dept) REFFERENCES DEPARTMENT(Dcode);
);

CREATE TABLE COURSE ( #Course entity
	Cnumber C_NUMBER,
	Cname VARCHAR(30) NOT NULL,
	Cdesc VARCHAR(50),
	Dept VARCHAR(4) NOT NULL,
	Level DECIMAL(4,0) NOT NULL,
	Hours INT NOT NULL,
	PRIMARY KEY (Cnumber),
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode);
);

CREATE TABLE SECTION ( #Section entity
	 Course C_NUMBER,
	 Instructor CHAR(9) NOT NULL,
	 Snumber INT NOT NULL DEFAULT 1,
	 Semester VARCHAR(6),
	 Year YEAR,
	 PRIMARY KEY (Course, Snumber, Semester, Year),
	 FOREIGN KEY (Course) REFERENCES COURSE(Cnumber),
	 FOREIGN KEY (Instructor) REFERENCES INSTRUCTOR(Nnumber);
);

CREATE TABLE PREREQUISITE ( #Prerequisite entity
	Cnumber C_NUMBER,
	Pnumber C_NUMBER,
	PRIMARY KEY (Cnumber, Pnumber),
	FOREIGN KEY (Cnumber) REFERENCES COURSE(Cnumber) ON DELETE CASCADE,
	FOREIGN KEY (Pnumber) REFERENCES COURSE(Cnumber) ON DELETE CASCADE,
	CONSTRAINT NotSelfPrerequisite CHECK (Cnumber <> Pnumber);
);

CREATE TABLE ENROLLED_IN ( #Enrolled_In relationship
	Student N_NUMBER,
	Course C_NUMBER,
	Semester VARCHAR(6),
	Year YEAR,
	Snumber INT,
	Grade CHAR,
	PRIMARY KEY (Student, Course, Year, Semester, Snumber),
	FOREIGN KEY (Student) REFERENCES STUDENT(Nnumber);
);

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

SELECT Course, Semester, Year FROM SECTION WHERE Instructor="N04567123";

INSERT INTO ENROLLED_IN VALUES ("N02345678", "COP3503", "Fall", 2020, 1, "A");
INSERT INTO ENROLLED_IN VALUES ("N02345678", "COP3703", "Spring", 2021, 1, "A");

SELECT PREREQUISITE.Pnumber, ENROLLED_IN.Course FROM PREREQUISITE, ENROLLED_IN WHERE PREREQUISITE.Cnumber="COP3703" AND ENROLLED_IN.Course=PREREQUISITE.Pnumber;
