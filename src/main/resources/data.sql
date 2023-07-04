insert into grade_score (grade, score) values('A+', 4.5);
insert into grade_score (grade, score) values('A0', 4.0);
insert into grade_score (grade, score) values('B+', 3.5);
insert into grade_score (grade, score) values('B0', 3.0);
insert into grade_score (grade, score) values('C+', 2.5);
insert into grade_score (grade, score) values('C0', 2.0);
insert into grade_score (grade, score) values('F', 1.5);

insert into staff (id, name, gender, birth_date, address, email, tel, hire_date) 
values((select next_val from id_sequences where sequence_name = 'staff'), '이문수', '남성', '1994-06-30', '인천시 연수구', 'lyczang4@naver.com', '010-4932-8262', now());
insert into user (id, password, role) values 
((select next_val from id_sequences where sequence_name = 'staff'), '$2a$12$ws91Y7qSX.zExcL8u2ARoOTea3TDmB7xYHjo0mEstD3tYRvDWAbK2', 'STAFF');
update id_sequences set next_val = next_val + 1 where sequence_name = 'staff';