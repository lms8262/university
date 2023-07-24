CREATE DATABASE shop DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

show databases;

use shop;

update member set role = 'ADMIN' where member_id = 1;

commit;

select * from member;
select * from item;
select * from item_img;
select * from orders;
select * from order_item;
select * from item_img_seq;
select * from member_seq;

-- order 상태인 아이템들의 주문횟수(cnt)를 구한다

select @rownum:=@rownum+1 num, groupdata.* from
(select order_item.item_id, count(*) cnt
from order_item
inner join orders
on (order_item.order_id = orders.order_id)
where orders.order_status = 'ORDER'
group by order_item.item_id order by cnt desc) groupdata,
(select @rownum:=0) r
limit 6;

select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn 
            from item 
			inner join item_img on (item.item_id = item_img.item_id)
			inner join (select @ROWNUM:=@ROWNUM+1 num, groupdata.* from
			            (select order_item.item_id, count(*) cnt
			              from order_item
			              inner join orders on (order_item.order_id = orders.order_id)
			              where orders.order_status = 'ORDER'
			             group by order_item.item_id order by cnt desc) groupdata,
                          (SELECT @ROWNUM:=0) R 
                          limit 6) data
			on (item.item_id = data.item_id)
			where item_img.repimg_yn = 'Y'
			order by num;



drop database university;

CREATE DATABASE university DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

select * from staff;
select * from professor;
select * from student;
select * from user;
select * from grade_score;
select * from college;
select * from department;
select * from lecture_room;
select * from lecture;
select * from lecture_registration;
select * from student_lecture;
select * from lecture_code;

use university;

-- 교수 테이블에 있는 학과번호로 학과명 가져오기
select name 학과명 from department where id = (select department_id from professor where id = 230003);

-- 강의 번호로 교수 번호 가져오기
select professor_id from lecture where id = 10001;

-- *학과별 교수번호
select id, department_id from professor where department_id = 101;
select id, department_id from professor where department_id = 102;
select id, department_id from professor where department_id = 103;
select id, department_id from professor where department_id = 104;

-- *학과별 강의실번호
select id from lecture_room where college_id = (select college_id from department where id = 101);
select id from lecture_room where college_id = (select college_id from department where id = 102);
select id from lecture_room where college_id = (select college_id from department where id = 103);
select id from lecture_room where college_id = (select college_id from department where id = 104);

-- *강의 번호로 학과명 가져오기
select name 학과명 from department where id = (select department_id from professor where id = (select professor_id from lecture where id = 10001));

-- *강의 번호로 교수명 가져오기
select name 교수명 from user where id = (select professor_id from lecture where id = 10001);

-- 검색 조건에 맞는 강의 목록 불러오기
select department.name 개설학과, lecture.id 강의번호, lecture.type 강의구분, lecture.name 강의명, user.name 담당교수, lecture.credit 학점, lecture.day 요일, 
lecture.start_time 시작시간, lecture.end_time 종료시간, lecture.num_of_student 현재인원, lecture.capacity 정원
from lecture, department, user, lecture_room
where lecture.department_id = department.id
and lecture.professor_id = user.id
and lecture.lecture_room_id = lecture_room.id
and lecture.type = '전공'
and lecture.department_id = 101
and lecture.name like '%전공%'
order by lecture.id asc;

-- 수강신청 가능 강의 
select department.name 개설학과, lecture.id 강의번호, lecture.type 강의구분, lecture.name 강의명, user.name 담당교수, lecture.credit 학점, lecture.day 요일, 
lecture.start_time 시작시간, lecture.end_time 종료시간, lecture.num_of_student 현재인원, lecture.capacity 정원
from lecture, department, user, lecture_room
where lecture.department_id = department.id
and lecture.professor_id = user.id
and lecture.lecture_room_id = lecture_room.id
and (lecture.department_id = 101 or lecture.type = '교양')
order by lecture.type desc;

-- 학생 id로 수강신청 총 신청 학점 가져오기
select coalesce(sum(lecture.credit), 0) totalCredit from lecture_registration, lecture where lecture_registration.student_id = 23000001;

select lecture.* from lecture_registration, lecture where lecture_registration.student_id = 23000001;

insert into lecture_registration(student_id, lecture_id) values(23000012, 10002);
commit;
delete from lecture_registration where lecture_id = 10009;
delete from lecture_registration where student_id = 23000012;

select * from lecture_registration;
select * from lecture;
delete from lecture where id = 10002;
update lecture set num_of_student = 0 where num_of_student = 1;

select year, semester from lecture
where professor_id = 230001
group by year, semester;

select * from student where department_id = 102;
select * from lecture where professor_id = 230001;
select lr.student_id, lr.lecture_id from lecture_registration lr join lecture l on lr.lecture_id = l.id;

use university;

SELECT AUTO_INCREMENT
FROM information_schema.tables
WHERE table_name = 'lecture'
AND table_schema = DATABASE();

insert into lecture(name, department_id, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time ,lecture_code_id) values('회계원리', 102, 230001, 'B201', 3, 30, '전공', 2023, 2, '월', 9, 12, 6);
insert into lecture(name, department_id, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time ,lecture_code_id) values('사회복지개론', 104, 230018, 'D401', 3, 30, '전공', 2023, 2, '월', 9, 12, 16);
commit;