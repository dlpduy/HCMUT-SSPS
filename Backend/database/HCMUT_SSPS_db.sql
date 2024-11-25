DROP DATABASE IF EXISTS HCMUT_SSPS;

CREATE DATABASE IF NOT EXISTS HCMUT_SSPS;

USE HCMUT_SSPS;

-- DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS users (
	id              INT             AUTO_INCREMENT              PRIMARY KEY,
	full_name       VARCHAR(100)                                NOT NULL,
	username        VARCHAR(100)    UNIQUE                      NOT NULL,
	password 		VARCHAR(65) 								NOT NULL, -- hashed password
    phone			VARCHAR(10),
    address			VARCHAR(100),
    role			ENUM("STUDENT", "SPSO")		default "STUDENT",
	creation_date   DATETIME        DEFAULT CURRENT_TIMESTAMP   NOT NULL
);


-- Bo Student and SPSO table


-- DROP TABLE IF EXISTS File_Types;
CREATE TABLE IF NOT EXISTS file_types ( -- list all accpeted file types in the system (managed by SPSO)
	id				INT					AUTO_INCREMENT			PRIMARY KEY,
    type            VARCHAR(10)  		UNIQUE 					NOT NULL, -- .pdf, .docx,...
	description     VARCHAR(100),
	spso_id			INT					DEFAULT 0				NOT NULL, -- determine which SPSO add this file type, 0 for default file types

	FOREIGN KEY (spso_id) REFERENCES users(id) ON DELETE CASCADE -- if SPSO is deleted, all file types added by that SPSO will be seemed to be default file types
);


-- DROP TABLE IF EXISTS Files;
CREATE TABLE IF NOT EXISTS files (
	id				INT				AUTO_INCREMENT				PRIMARY KEY,
	file_name		VARCHAR(50)									NOT NULL,
	file_type		VARCHAR(10)									NOT	NULL, --   .pdf, .docx,...
    student_id		INT				DEFAULT 0					NOT NULL,

    FOREIGN KEY	(student_id) REFERENCES users(id) ON DELETE CASCADE, -- if student is deleted, all files uploaded by that student will be deleted, too
	FOREIGN KEY (file_type) REFERENCES file_types(type),

	UNIQUE (student_id, file_name, file_type) -- accept student S1 uploads hehe.pdf and hehe.docx and student S2 uploads hehe.pdf
);


-- DROP TABLE IF EXISTS Printers;
CREATE TABLE IF NOT EXISTS printers (
	id              INT             AUTO_INCREMENT              PRIMARY KEY,
	brand			VARCHAR(50)									NOT NULL,
	model			VARCHAR(50)									NOT NULL,
	description		VARCHAR(100),
	enabled         BOOLEAN         DEFAULT FALSE               NOT NULL,
	campus_name     VARCHAR(50)                                 NOT NULL,
	building_name   VARCHAR(10)									NOT NULL,
	room_num		VARCHAR(10)									NOT NULL
);

-- DROP TABLE IF EXISTS Papers;
CREATE TABLE IF NOT EXISTS papers ( -- all existing paper types information (managed by SPSO)
	id				INT				AUTO_INCREMENT				PRIMARY KEY,
	type			VARCHAR(4)		UNIQUE 						NOT NULL, 
	width			INT											NOT NULL, -- in mm				
	height			INT											NOT NULL, -- in mm
	price			DECIMAL(10,2)								NOT NULL,
	spso_id			INT				DEFAULT 0					NOT NULL, -- determine which SPSO add this paper type, 0 for default paper types

	FOREIGN KEY (spso_id) REFERENCES users(id) ON DELETE CASCADE -- if SPSO is deleted, all paper types added by that SPSO will be deleted, too
);
-- INSERT INTO Papers VALUES("A4", 20, 30);
-- INSERT INTO Papers VALUES("A3", 30, 60);


-- DROP TABLE IF EXISTS Printing_Logs;
CREATE TABLE IF NOT EXISTS printing_logs ( -- one record is a service using history of a student
	id				INT				AUTO_INCREMENT				PRIMARY KEY,
	printer_id		INT											NOT NULL,
    file_id			INT											NOT NULL, -- can join with Files table on this field to get student who printed this file  
	num_copy		INT											NOT NULL,
	paper_type		VARCHAR(4)									NOT NULL, -- to know which paper type is used (A4 or A3,...)
	sided			ENUM('Single', 'Double')					NOT NULL,
	printing_pages  VARCHAR(100)								NOT NULL, -- format: "be-en" or "p1, p2, p3,..."
	num_pages		INT											NOT NULL, -- equals 2*num_sheet if double-sided, else num_sheet 
	time			DATETIME		DEFAULT CURRENT_TIMESTAMP,

	FOREIGN KEY (file_id) REFERENCES files(id),
	FOREIGN KEY (printer_id) REFERENCES printers(id), 
	FOREIGN KEY (paper_type) REFERENCES papers(type),
	
	UNIQUE (file_id, time) 
);


-- DROP TABLE IF EXISTS Student_Papers;
CREATE TABLE IF NOT EXISTS student_papers ( -- need to keep track of how many sheets of a paper type a student has in their repository
	id				INT					AUTO_INCREMENT			PRIMARY KEY,
	student_id		INT											NOT NULL,
	paper_type		VARCHAR(4) 									NOT NULL,
	quantity		INT											NOT NULL,

	FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
	FOREIGN KEY (paper_type) REFERENCES papers(type) ON DELETE CASCADE,

	UNIQUE (student_id, paper_type)
);
-- INSERT INTO Student_Papers VALUES(1, 1, 10); -- if A4 paper has id=1, that means student 1 having 10 sheets of A4 
-- INSERT INTO Student_Papers VALUES(1, 2, 5); -- if A3 paper has id=2, that means student 1 having 5 sheets of A3, too



-- DROP TABLE IF EXISTS Orders; -- it's PaymentLogs, but I like to call it Orders :)
CREATE TABLE IF NOT EXISTS orders (
	id				INT				AUTO_INCREMENT				PRIMARY KEY,
	student_id		INT											NOT NULL,
	total_price		DECIMAL(10,2)								NOT NULL,
	time			DATETIME									NOT NULL,

	FOREIGN KEY (student_id) REFERENCES users(id)
);


-- DROP TABLE IF EXISTS order_papers;
CREATE TABLE IF NOT EXISTS order_papers ( -- since one order can have multiple paper types, must have this separate table
	id				INT				AUTO_INCREMENT				PRIMARY KEY,
	order_id		INT											NOT NULL,
	paper_type		VARCHAR(4)									NOT NULL,
	quantity		INT											NOT NULL,

	FOREIGN KEY (order_id) REFERENCES orders(id),
	FOREIGN KEY (paper_type) REFERENCES papers(type),

	UNIQUE (order_id, paper_type)
);
-- INSERT INTO Student_Paper_Orders VALUES(1, 1, 5); -- order 1 has 5 sheets of A4 paper
-- INSERT INTO Student_Paper_Orders VALUES(1, 2, 3); -- and 3 sheets of A3 paper.
-- INSERT INTO Student_Paper_Orders VALUES(2, 1, 10); -- order 2 has 10 sheets of A4 paper.
-- INSERT INTO Student_Paper_Orders VALUES(3, 2, 5); -- order 3 has 5 sheets of A3 paper.