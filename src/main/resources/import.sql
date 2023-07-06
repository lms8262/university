alter table department auto_increment = 101;
alter table student auto_increment = 23000001;
alter table professor auto_increment = 230001;
alter table staff auto_increment = 2301;
alter table lecture auto_increment = 10001;

insert into grade_score (grade, score) values('A+', 4.5);
insert into grade_score (grade, score) values('A0', 4.0);
insert into grade_score (grade, score) values('B+', 3.5);
insert into grade_score (grade, score) values('B0', 3.0);
insert into grade_score (grade, score) values('C+', 2.5);
insert into grade_score (grade, score) values('C0', 2.0);
insert into grade_score (grade, score) values('F', 1.5);

insert into college (name, college_code) values('인문대학', 'A');
insert into college (name, college_code) values('상경대학', 'B');
insert into college (name, college_code) values('공과대학', 'C');
insert into college (name, college_code) values('사회과학대학', 'D');

insert into department (name, college_id) values('일어일문학과', 1);
insert into department (name, college_id) values('경영학과', 2);
insert into department (name, college_id) values('컴퓨터공학과', 3);
insert into department (name, college_id) values('사회복지학과', 4);

insert into student (entrance_date, department_id) values(now(), 101);
insert into user (id, password, role, name, gender, birth_date, address, email, tel) values (23000001, '$2a$12$Wq1J0hZZfYOryKHgYtu4nudF.lxnJzhAhGQC8HUBLRx2nMaTXOyxq', 'STUDENT', '이문수', '남성', '1994-06-30', '인천시 연수구', 'lyczang4@naver.com', '010-4932-8262');

insert into professor (hire_date, department_id) values(now(), 101);
insert into user (id, password, role, name, gender, birth_date, address, email, tel) values (230001, '$2a$12$OalzWbYZ0Et0/xGzEbDgs.Ui.bDovJEiYd4meiBwlvXs8PJtI3EmS', 'PROFESSOR', '이은미', '여성', '1972-04-15', '서울시 서대문구', 'eunmi77@naver.com', '010-1234-5678');

insert into staff (hire_date) values(now());
insert into user (id, password, role, name, gender, birth_date, address, email, tel) values (2301, '$2a$12$ws91Y7qSX.zExcL8u2ARoOTea3TDmB7xYHjo0mEstD3tYRvDWAbK2', 'STAFF', '이지수', '남성', '1996-07-07', '인천시 연수구', 'zz11@naver.com', '010-2345-6789');

