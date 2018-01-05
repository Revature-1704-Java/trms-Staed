DROP USER trmsDev CASCADE;

CREATE USER trmsDev
IDENTIFIED BY w3798Staed
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT CONNECT TO trmsDev;
GRANT RESOURCE TO trmsDev;
GRANT CREATE SESSION TO trmsDev;
GRANT CREATE TABLE TO trmsDev;
GRANT CREATE VIEW TO trmsDev;

conn trmsDev/w3798Staed


ALTER SESSION SET PLSCOPE_SETTINGS = 'IDENTIFIERS:NONE';

-- TABLES
CREATE TABLE EMPLOYEETYPE (
    EmployeeTypeId      NUMBER NOT NULL,
    EmployeeTypeName    VARCHAR(30),
    EmployeeTypePower   NUMBER DEFAULT 0,
    
    CONSTRAINT PK_EmployeeType PRIMARY KEY (EmployeeTypeId),
    CONSTRAINT NNL_EmployeeTypePower CHECK (EmployeeTypePower IS NOT NULL)
);

-- The employees at the company
CREATE TABLE EMPLOYEE (
    Email               VARCHAR2(40) NOT NULL,
    EmpPass    VARCHAR2(20) NOT NULL,
    EmployeeName        VARCHAR2(50),
    EmployeeTypeId      NUMBER DEFAULT 1,
    
    Super               VARCHAR2(40),
    Head                VARCHAR2(40),
    BenCo               VARCHAR2(40),
    
    CONSTRAINT PK_Employee PRIMARY KEY (Email),
    CONSTRAINT FK_EmployeeType FOREIGN KEY (EmployeeTypeId)
        REFERENCES EMPLOYEETYPE (EmployeeTypeId),
    CONSTRAINT FK_Super FOREIGN KEY (Super)
        REFERENCES EMPLOYEE (Email),
    CONSTRAINT FK_Head FOREIGN KEY (Head)
        REFERENCES EMPLOYEE (Email),
    CONSTRAINT FK_BenCo FOREIGN KEY (BenCo)
        REFERENCES EMPLOYEE (Email)
);

-- The amount of reimbursement you can get for that type
CREATE TABLE EVENTTYPE (
    EventTypeId         NUMBER NOT NULL,
    EventTypeName       VARCHAR2(40) UNIQUE,
    Compensation        NUMBER DEFAULT 30,
    CONSTRAINT PK_EventType PRIMARY KEY (EventTypeId)
);

-- The grading method
CREATE TABLE GRADINGFORMAT (
    GradingFormatId     NUMBER NOT NULL,
    FormatType          VARCHAR2(30) UNIQUE,
    Cutoff              NUMBER DEFAULT 70,
    CONSTRAINT PK_GradingFormat PRIMARY KEY (GradingFormatId)
);

CREATE TABLE REQUEST (
    RequestId           NUMBER NOT NULL,
    Email               VARCHAR2(40) NOT NULL,
    EventTypeId         NUMBER NOT NULL,
    GradingFormatId     NUMBER DEFAULT 1,
    State               NUMBER DEFAULT 0,
    RequestCost         NUMBER NOT NULL,
    EventDate           DATE NOT NULL,
    WorkMissed          VARCHAR2(40),
    LastReviewed        DATE,
    
    CONSTRAINT PK_Request PRIMARY KEY (RequestId),
    CONSTRAINT FK_Employee FOREIGN KEY (Email) 
        REFERENCES EMPLOYEE (Email),
    CONSTRAINT FK_EventType FOREIGN KEY (EventTypeId)
        REFERENCES EVENTTYPE (EventTypeId),
    CONSTRAINT FK_GradingFormat FOREIGN KEY (GradingFormatId)
        REFERENCES GRADINGFORMAT (GradingFormatId)
);

CREATE TABLE INFO (
    RequestId           NUMBER NOT NULL UNIQUE,
    InfoDesc            VARCHAR2(80),
    InfoLocation        VARCHAR2(80),
    Justification       VARCHAR2(80),
    CONSTRAINT FK_Request FOREIGN KEY (RequestId)
        REFERENCES REQUEST (RequestId)
);

CREATE TABLE ATTACHMENT (
    Filename            VARCHAR2(80) NOT NULL,
    RequestId           NUMBER NOT NULL,
    ApprovedAtState     NUMBER DEFAULT 0,
    AttachmentDesc      VARCHAR2(80),
    CONSTRAINT PK_Attachment PRIMARY KEY (Filename),
    CONSTRAINT FK_AttachRequest FOREIGN KEY (RequestId)
        REFERENCES REQUEST (RequestId)
);

CREATE TABLE NOTE (
    NoteId              NUMBER NOT NULL,
    RequestId           NUMBER NOT NULL,
    ManagerEmail        VARCHAR2(40) NOT NULL,
    TimeActedOn         DATE NOT NULL,
    NewAmount           NUMBER,
    Reason              VARCHAR2(80),
    CONSTRAINT PK_Note PRIMARY KEY (NoteId),
    CONSTRAINT FK_NoteRequest FOREIGN KEY (RequestId)
        REFERENCES REQUEST (RequestId),
    CONSTRAINT FK_Manager FOREIGN KEY (ManagerEmail)
        REFERENCES EMPLOYEE (Email)
);

-- SEQUENCES
CREATE SEQUENCE SQ_EMPLOYEETYPE
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SQ_EVENTTYPE
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SQ_GRADINGFORMAT
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SQ_REQUEST
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE SQ_NOTE
START WITH 1
INCREMENT BY 1;


-- TRIGGERS
CREATE OR REPLACE TRIGGER TR_INSERT_EMPLOYEETYPE
BEFORE INSERT ON EMPLOYEETYPE
FOR EACH ROW
BEGIN
    SELECT SQ_EMPLOYEETYPE.NEXTVAL
    INTO :NEW.EmployeeTypeId FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER TR_INSERT_EVENTTYPE
BEFORE INSERT ON EVENTTYPE
FOR EACH ROW
BEGIN
    SELECT SQ_EVENTTYPE.NEXTVAL
    INTO :NEW.EventTypeId FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER TR_INSERT_GRADINGFORMAT
BEFORE INSERT ON GRADINGFORMAT
FOR EACH ROW
BEGIN
    SELECT SQ_GRADINGFORMAT.NEXTVAL
    INTO :NEW.GradingFormatId FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER TR_INSERT_REQUEST
BEFORE INSERT ON REQUEST
FOR EACH ROW
DECLARE
    week FLOAT := 7.0/31.0;
BEGIN
    IF MONTHS_BETWEEN(:NEW.EventDate, SYSDATE) < week
    THEN
        RAISE_APPLICATION_ERROR(-20101, 'Invalid Event Date: The EventDate must be at least a week from the current date.');
    ELSE
        :NEW.RequestId := SQ_REQUEST.NEXTVAL;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER TR_INSERT_NOTE
BEFORE INSERT ON NOTE
FOR EACH ROW
BEGIN
    SELECT SQ_NOTE.NEXTVAL
    INTO :NEW.NoteId FROM DUAL;
END;
/

-- FUNCTIONS / PROCEDURES / VIEWS
CREATE OR REPLACE FUNCTION Avalible_Reimbursement (E_Email IN VARCHAR2)
    RETURN NUMBER IS AVALIBLE NUMBER;
    Total NUMBER;
    AwardPend NUMBER := 0;
    Temp NUMBER;
    
    CURSOR S IS
        SELECT RequestCost
        FROM REQUEST
        WHERE Email = E_Email
        AND State < 4
        AND EXTRACT(YEAR FROM EventDate) = EXTRACT(YEAR FROM CURRENT_DATE);
BEGIN
    OPEN S;
    LOOP
        FETCH S INTO Temp;
        AwardPend := AwardPend + Temp;
    END LOOP;
    CLOSE S;
    
    Avalible := 1000 - AwardPend;
    RETURN Avalible;
END Avalible_Reimbursement;
/

-- Populate
INSERT INTO EMPLOYEETYPE (EmployeeTypeName, EmployeeTypePower) VALUES ('Associate', 0);
INSERT INTO EMPLOYEETYPE (EmployeeTypeName, EmployeeTypePower) VALUES ('Supervisor', 1);
INSERT INTO EMPLOYEETYPE (EmployeeTypeName, EmployeeTypePower) VALUES ('Department Head', 2);
INSERT INTO EMPLOYEETYPE (EmployeeTypeName, EmployeeTypePower) VALUES ('Benefits Coordinator', 3);

INSERT INTO EMPLOYEE(EMAIL, EmpPass, EMPLOYEENAME, EMPLOYEETYPEID)
    VALUES ('down@smash.com', 'smashing', 'Walk-Up Slowly', 4);
INSERT INTO EMPLOYEE(EMAIL, EmpPass, EMPLOYEENAME, EMPLOYEETYPEID, BENCO)
    VALUES ('lure@them.all', 'thisIsbait', 'Lure Master', 3, 'down@smash.com');
INSERT INTO EMPLOYEE(EMAIL, EmpPass, EMPLOYEENAME, EMPLOYEETYPEID, SUPER, HEAD, BENCO)
    VALUES ('woof@gmail.com', '#notmydog', 'Doge McDogFace', 2, 'lure@them.all', 'lure@them.all', 'down@smash.com');
INSERT INTO EMPLOYEE(EMAIL, EmpPass, EMPLOYEENAME, EMPLOYEETYPEID, SUPER, HEAD, BENCO)
    VALUES ('johncena@trumpets.com', 'trumpets4life', 'John Cena', 1, 'woof@gmail.com', 'lure@them.all', 'down@smash.com');




INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('University Course', 80);
INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('Seminar', 60);
INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('Certification Preparation Course', 75);
INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('Certification', 100);
INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('Technical Training', 90);
INSERT INTO EVENTTYPE (EventTypeName, Compensation) VALUES ('Other', 30);

INSERT INTO GRADINGFORMAT (FormatType) VALUES ('Grade');
INSERT INTO GRADINGFORMAT (FormatType) VALUES ('Presentation');


INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('johncena@trumpets.com', 6, 2, 0, 10.5, TO_DATE('01/30/2018', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (1, 'Late New Years Party', 'Revature Office', 'Mandatory Attendence');

INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('johncena@trumpets.com', 5, 1, 0, 903, TO_DATE('05/30/2050', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (2, 'The Future', 'The Year 2050', 'It''s the future man');

INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('woof@gmail.com', 6, 1, 4, 5039, TO_DATE('02/07/2020', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (3, 'Hurriance Expected', 'Florida', 'I work there');
    
INSERT INTO NOTE (ManagerEmail, RequestId, TimeActedOn, NewAmount, Reason)
    VALUES ('down@smash.com', 3, TO_DATE('01/12/2018', 'mm/dd/yyyy'), 953, 'You can''t ask for that much');

INSERT INTO ATTACHMENT (Filename, RequestId, ApprovedAtState, AttachmentDesc)
    VALUES ('2018secrets.txt', 1, 2, 'Approval hidden inside the secret message');
    
INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('woof@gmail.com', 6, 1, 4, 1000, TO_DATE('02/07/2020', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (4, 'Hurriance Expected', 'Florida', 'I work there');

INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('woof@gmail.com', 6, 1, 4, 732, TO_DATE('02/07/2020', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (5, 'Hurriance Expected', 'Florida', 'I work there');

INSERT INTO REQUEST(EMAIL, EVENTTYPEID, GRADINGFORMATID, STATE, REQUESTCOST, EVENTDATE)
    VALUES ('woof@gmail.com', 6, 1, 4, 227, TO_DATE('02/07/2020', 'mm/dd/yyyy'));
INSERT INTO INFO (RequestId, InfoDesc, InfoLocation, Justification)
    VALUES (6, 'Hurriance Expected', 'Florida', 'I work there');

COMMIT;