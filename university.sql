/* ENTITIES */

DROP TABLE ENROLLED_IN;
DROP TABLE PREREQUISITE;
DROP TABLE SECTION;
DROP TABLE COURSE;
DROP TABLE STUDENT;
DROP TABLE INSTRUCTOR;
DROP TABLE DEPARTMENT;
DROP TABLE PERSON;

CREATE TABLE PERSON (
	Nnumber CHAR(9),
    
    CONSTRAINT PERSON_N_NUMBER_FORMAT CHECK (Nnumber LIKE 'N[0-9]{8}$'),
	
	Ssn CHAR(11),
    
    CONSTRAINT SSN_FORMAT CHECK (Ssn LIKE '[0-9]{3}-[0-9]{2}-[0-9]{4}$'),
	
	Fname VARCHAR2(20),
	
	Minit CHAR,
	
	Lname VARCHAR2(20),
	
	Phone CHAR(14),
    
    CONSTRAINT PERSON_PHONE_FORMAT CHECK (Phone LIKE '\([0-9]{3}\) [0-9]{3}-[0-9]{4}$'),
	
	Street VARCHAR2(30),
	
	City VARCHAR2(20),
	
	State_ VARCHAR2(13),
    
    CONSTRAINT PERSON_STATE_OPTIONS CHECK (State_ IN ('Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming')),
    
	Zip DECIMAL(5, 0),
	
	Bdate DATE,
	
	Gender VARCHAR2(6),
    
    CONSTRAINT GENDER_OPTIONS CHECK (Gender IN ('Male', 'Female')),
	
	PRIMARY KEY (Nnumber),
	
	UNIQUE (Ssn)
);

CREATE TABLE DEPARTMENT ( -- Department Entity
	Dcode VARCHAR2(4),
    
    CONSTRAINT DEPARTMENT_CODE_FORMAT CHECK (Dcode LIKE '[A-Z]{1,4}$'),
	
	Dname VARCHAR2(30),
	
	Dcollege VARCHAR2(30),
	
	Onumber DECIMAL(4,0),
	
	Ophone CHAR(14),
    
    CONSTRAINT DEPARTMENT_PHONE_FORMAT CHECK (Ophone LIKE '\([0-9]{3}\) [0-9]{3}-[0-9]{4}$'),
	
	CONSTRAINT DEPARTMENT_PK
	
	PRIMARY KEY (Dcode),
	
	CONSTRAINT DEPARTMENT_SK
	
	UNIQUE (Dname)
);

--DROP TABLE INSTRUCTOR;

CREATE TABLE INSTRUCTOR ( -- Instructor Entity
	Nnumber CHAR(9),
    
    CONSTRAINT INSTRUCTOR_N_NUMBER_FORMAT CHECK (Nnumber LIKE 'N[0-9]{8}$'),
	
	Dept VARCHAR(4),
    
    CONSTRAINT INSTRUCTOR_DCODE_FORMAT CHECK (Dept LIKE '[A-Z]{1,4}$'),
	
	Onumber DECIMAL(4,0),
	
	CONSTRAINT INSTRUCTOR_PK
	
	PRIMARY KEY (Nnumber),
	
	CONSTRAINT INSTRUCTOR_PERSON_FK
	
	FOREIGN KEY (Nnumber) REFERENCES PERSON(Nnumber)
	
	ON DELETE CASCADE,
	
	CONSTRAINT INSTRUCTOR_DEPARTMENT_FK
	
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode)
);

CREATE TABLE STUDENT ( -- Student Entity
	Nnumber CHAR(9),
    
    CONSTRAINT STUDENT_N_NUMBER_FORMAT CHECK (Nnumber LIKE 'N[0-9]{8}$'),
	
	Curr_street VARCHAR2(30),
	
	Curr_city VARCHAR2(20),
	
	Curr_state VARCHAR2(13),
    
    CONSTRAINT STUDENT_STATE_OPTIONS CHECK (Curr_state IN ('Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming')),
	
	Curr_zip DECIMAL(5,0),
	
	Class_ VARCHAR2(9),
    
    CONSTRAINT CLASS_OPTIONS CHECK (Class_ IN ('Freshman', 'Sophomore', 'Junior', 'Senior', 'Graduate')),
	
	Degree_pgrm VARCHAR2(4),
    
    CONSTRAINT DEGREE_PROGRAM_OPTIONS CHECK (Degree_pgrm IN ('B.A.', 'B.S.', 'B.F.A', 'M.A.', 'M.S.', 'M.B.A.', 'Ph.D.', 'Ed.D.', 'J.D.')),
	
	Major_dept VARCHAR2(4),
    
    CONSTRAINT MAJOR_DCODE_FORMAT CHECK (Major_dept LIKE '[A-Z]{1,4}$'),
	
	Minor_dept VARCHAR2(4),
    
    CONSTRAINT MINOR_DCODE_FORMAT CHECK (Minor_dept LIKE '[A-Z]{1,4}$'),
	
	CONSTRAINT STUDENT_PK
	
	PRIMARY KEY (Nnumber),
	
	CONSTRAINT STUDENT_PERSON_FK
	
	FOREIGN KEY (Nnumber) REFERENCES PERSON(Nnumber)
	
	ON DELETE CASCADE,
	
	CONSTRAINT STUDENT_MAJOR_FK
	
	FOREIGN KEY (Major_dept) REFERENCES DEPARTMENT(Dcode),
	
	CONSTRAINT STUDENT_MINOR_FK
	
	FOREIGN KEY (Minor_dept) REFERENCES DEPARTMENT(Dcode)
	
	ON DELETE SET NULL
);

CREATE TABLE COURSE ( -- Course Entity
	Cnumber CHAR(7) CONSTRAINT COURSE_NUMBER_FORMAT CHECK (Cnumber LIKE '[A-Z]{3} [0-9]{4}$'),
	
	Cname VARCHAR2(30),
	
	Cdesc VARCHAR2(50),
	
	Dept VARCHAR2(4) CONSTRAINT COURSE_DCODE_FORMAT CHECK (Dept LIKE '[A-Z]{1,4}$'),
	
	Level_ DECIMAL(4,0) CONSTRAINT LEVEL_RANGE CHECK (Level_ >= 1000 AND Level_ <= 7000 AND MOD(Level_, 1000) = 0),
	
	Hours_ INT CONSTRAINT HOURS_RANGE CHECK (Hours_ > 0 AND Hours_ < 6),
	
	CONSTRAINT COURSE_PK
	
	PRIMARY KEY (Cnumber),
	
	CONSTRAINT COURSE_DEPARTMENT_FK
	
	FOREIGN KEY (Dept) REFERENCES DEPARTMENT(Dcode)
);

CREATE TABLE SECTION ( -- Section Entity
	 Course CHAR(7) CONSTRAINT SECTION_CNUMBER_FORMAT CHECK (Course LIKE '[A-Z]{3} [0-9]{4}$'),
	 
	 Instructor CHAR(9) CONSTRAINT INSTRUCTOR_SECTION_N_NUMBER_FORMAT CHECK (Instructor LIKE 'N[0-9]{8}$'),
	 
	 Snumber INT CONSTRAINT SECTION_NUMBER_RANGE CHECK (Snumber > 0),
	 
	 Semester VARCHAR2(8) CONSTRAINT SECTION_SEMESTER_OPTIONS CHECK (Semester IN ('Fall', 'Spring', 'Summer A', 'Summer B')),
	 
	 Year_ DECIMAL(4,0) CONSTRAINT SECTION_YEAR_RANGE CHECK (Year_ >= 1965 AND Year_ <= 2050),
	 
	 CONSTRAINT SECTION_PK
	 
	 PRIMARY KEY (Course, Snumber, Semester, Year_),
	 
	 CONSTRAINT SECTION_COURSE_FK
	 
	 FOREIGN KEY (Course) REFERENCES COURSE(Cnumber)
	 
	 ON DELETE CASCADE,
	 
	 CONSTRAINT SECTION_INSTRUCTOR_FK
	 
	 FOREIGN KEY (Instructor) REFERENCES INSTRUCTOR(Nnumber)
	 
	 ON DELETE CASCADE
);

CREATE TABLE PREREQUISITE ( -- Prerequisite: Course M:N Recurrence Relation
	Cnumber CHAR(7) CONSTRAINT PREREQUISITE_CNUMBER_FORMAT CHECK (Cnumber LIKE '[A-Z]{3} [0-9]{4}$'),
	
	Pnumber CHAR(7) CONSTRAINT PREREQUISITE_PNUMBER_FORMAT CHECK (Pnumber LIKE '[A-Z]{3} [0-9]{4}$'),
	
	CONSTRAINT PREREQUISITE_PK
	
	PRIMARY KEY (Cnumber, Pnumber),
	
	CONSTRAINT PREREQUISITE_CNUMBER_FK
	
	FOREIGN KEY (Cnumber) REFERENCES COURSE(Cnumber)
	
	ON DELETE CASCADE,
	
	CONSTRAINT PREREQUISITE_PNUMBER_FK
	
	FOREIGN KEY (Pnumber) REFERENCES COURSE(Cnumber)
	
	ON DELETE CASCADE,
	
	CONSTRAINT NOT_SELF_PREREQUISITE CHECK (Cnumber <> Pnumber)
);

CREATE TABLE ENROLLED_IN ( -- Enrolled_in: Student M:N Section Relation
	Student CHAR(9) CONSTRAINT STUDENT_ENROLLED_N_NUMBER_FORMAT CHECK (Student LIKE 'N[0-9]{8}$'),
	
	Course CHAR(7) CONSTRAINT ENROLLED_CNUMBER_FORMAT CHECK (Course LIKE '[A-Z]{3} [0-9]{4}$'),
	
	Semester VARCHAR2(8) CONSTRAINT ENROLLED_SEMESTER_OPTIONS CHECK (Semester IN ('Fall', 'Spring', 'Summer A', 'Summer B')),
	
	Year_ DECIMAL(4,0) CONSTRAINT ENROLLED_YEAR_RANGE CHECK (Year_ >= 1965 AND Year_ <= 2050),
	
	Snumber INT CONSTRAINT ENROLLED_SNUMBER_RANGE CHECK (Snumber > 0),
	
	Grade VARCHAR2(2) CONSTRAINT GRADE_OPTIONS CHECK (Grade IN ('A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D-', 'D', 'F')),
	
	CONSTRAINT ENROLLED_IN_PK
	
	PRIMARY KEY (Student, Course, Year_, Semester, Snumber),
	
	CONSTRAINT ENROLLED_IN_STUDENT_FK
	
	FOREIGN KEY (Student) REFERENCES STUDENT(Nnumber)
	
	ON DELETE CASCADE,
	
	CONSTRAINT ENROLLED_IN_SECTION_FK
	
	FOREIGN KEY (Course, Year_, Semester, Snumber) REFERENCES SECTION(Course, Year_, Semester, Snumber)
	
	ON DELETE CASCADE
);

/* TRIGGERS 

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
END;*/
