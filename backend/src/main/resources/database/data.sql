-- Provide SQL scripts here
insert into employee(ID, FIRST_NAME, LAST_NAME, EMAIL, STATUS, HIRE_DATE, VERSION)
values(10001,'Amy', 'Peng', 'apeng@tekmetric.com', 'ACTIVE', PARSEDATETIME('2017/09/01','yyyy/MM/dd'), 0);
insert into employee(ID, FIRST_NAME, LAST_NAME, EMAIL, STATUS, HIRE_DATE, VERSION)
values(10002,'John', 'Doe', 'jdoe@tekmetric.com', 'PASSIVE', PARSEDATETIME('2015/03/03','yyyy/MM/dd'), 0);