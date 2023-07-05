
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


insert into department (id, name, college_id) values(101, '일어일문학과', 1);
insert into department (id, name, college_id) values(102, '경영학과', 2);
insert into department (id, name, college_id) values(103, '컴퓨터공학과', 3);
insert into department (id, name, college_id) values(104, '사회복지학과', 4);
update id_sequences set next_val = next_val + 4 where sequence_name = 'department';


insert into student (id, name, gender, birth_date, address, email, tel, entrance_date, department_id) values(23000001, '이문수', '남성', '1994-06-30', '인천시 연수구', 'lyczang44@naver.com', '010-4932-8161', now(), 101);
insert into user (id, password, role) values (23000001, '$2a$12$Wq1J0hZZfYOryKHgYtu4nudF.lxnJzhAhGQC8HUBLRx2nMaTXOyxq', 'STUDENT');
update id_sequences set next_val = next_val + 1 where sequence_name = 'student';


insert into professor (id, name, gender, birth_date, address, email, tel, hire_date, department_id) values(230001 ,'이은미', '여성', '1972-04-15', '서울시 서대문구', 'eunmi77@naver.com', '010-1234-5678', now(), 101);
insert into user (id, password, role) values (230001, '$2a$12$OalzWbYZ0Et0/xGzEbDgs.Ui.bDovJEiYd4meiBwlvXs8PJtI3EmS', 'PROFESSOR');
update id_sequences set next_val = next_val + 1 where sequence_name = 'professor';


insert into staff (id, name, gender, birth_date, address, email, tel, hire_date) values(2301 ,'이문수', '남성', '1994-06-30', '인천시 연수구', 'lyczang4@naver.com', '010-4932-8262', now());
insert into user (id, password, role) values (2301, '$2a$12$ws91Y7qSX.zExcL8u2ARoOTea3TDmB7xYHjo0mEstD3tYRvDWAbK2', 'STAFF');
update id_sequences set next_val = next_val + 1 where sequence_name = 'staff';

