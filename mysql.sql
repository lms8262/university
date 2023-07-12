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

select * from lecture_room;

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

-- 일어일문학과(101) 강의 등록
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('전공기초일본어', 230003, 'A101', 3, 30, '전공', 2023, 1, '월', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('전공기초일본어', 230003, 'A101', 3, 30, '전공', 2023, 1, '월', 13, 16);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일어학의 이해', 230011, 'A102', 3, 30, '전공', 2023, 1, '화', 10, 13);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일어학의 이해', 230011, 'A102', 3, 30, '전공', 2023, 1, '화', 14, 17);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일문학의 이해', 230014, 'A103', 3, 30, '전공', 2023, 1, '수', 11, 14);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일문학의 이해', 230014, 'A103', 3, 30, '전공', 2023, 1, '수', 15, 18);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일본문화여행', 230016, 'A104', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('일본문화여행', 230019, 'A105', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('커뮤니케이션 일본어', 230024, 'A104', 3, 30, '전공', 2023, 1, '금', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('커뮤니케이션 일본어', 230024, 'A105', 3, 30, '전공', 2023, 1, '금', 15, 18);

-- 경영학과(102) 강의 등록
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('회계원리', 230001, 'B201', 3, 30, '전공', 2023, 1, '월', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('회계원리', 230001, 'B201', 3, 30, '전공', 2023, 1, '월', 13, 16);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('재무회계', 230002, 'B202', 3, 30, '전공', 2023, 1, '화', 10, 13);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('재무회계', 230002, 'B202', 3, 30, '전공', 2023, 1, '화', 14, 17);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('원가회계', 230006, 'B203', 3, 30, '전공', 2023, 1, '수', 11, 14);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('원가회계', 230006, 'B203', 3, 30, '전공', 2023, 1, '수', 15, 18);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('경상통계학', 230009, 'B204', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('경상통계학', 230013, 'B205', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('인적자원관리', 230020, 'B204', 3, 30, '전공', 2023, 1, '금', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('인적자원관리', 230020, 'B205', 3, 30, '전공', 2023, 1, '금', 15, 18);

-- 컴퓨터공학과(103) 강의 등록
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('이산수학', 230012, 'C301', 3, 30, '전공', 2023, 1, '월', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('이산수학', 230012, 'C301', 3, 30, '전공', 2023, 1, '월', 13, 16);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('컴퓨터 프로그래밍', 230015, 'C302', 3, 30, '전공', 2023, 1, '화', 10, 13);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('컴퓨터 프로그래밍', 230015, 'C302', 3, 30, '전공', 2023, 1, '화', 14, 17);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('자료구조', 230030, 'C303', 3, 30, '전공', 2023, 1, '수', 11, 14);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('자료구조', 230030, 'C303', 3, 30, '전공', 2023, 1, '수', 15, 18);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('컴퓨터과학이 여는 세계', 230035, 'C304', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('컴퓨터과학이 여는 세계', 230036, 'C305', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('알고리즘', 230038, 'C304', 3, 30, '전공', 2023, 1, '금', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('알고리즘', 230038, 'C305', 3, 30, '전공', 2023, 1, '금', 15, 18);

-- 사회복지학과(104) 강의 등록
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('사회복지개론', 230004, 'D401', 3, 30, '전공', 2023, 1, '월', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('사회복지개론', 230004, 'D401', 3, 30, '전공', 2023, 1, '월', 13, 16);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('사회복지정책', 230005, 'D402', 3, 30, '전공', 2023, 1, '화', 10, 13);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('사회복지정책', 230005, 'D402', 3, 30, '전공', 2023, 1, '화', 14, 17);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('인간행동과 사회환경', 230007, 'D403', 3, 30, '전공', 2023, 1, '수', 11, 14);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('인간행동과 사회환경', 230007, 'D403', 3, 30, '전공', 2023, 1, '수', 15, 18);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('복지국가의 이해', 230008, 'D404', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('복지국가의 이해', 230010, 'D405', 3, 30, '교양', 2023, 1, '목', 12, 15);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('복지국가론', 230017, 'D404', 3, 30, '전공', 2023, 1, '금', 9, 12);
insert into lecture(name, professor_id, lecture_room_id, credit, capacity, type, year, semester, day, start_time, end_time) values('복지국가론', 230017, 'D405', 3, 30, '전공', 2023, 1, '금', 15, 18);



select * from lecture;
delete from lecture where id = 10002;

use university;

SELECT AUTO_INCREMENT
FROM information_schema.tables
WHERE table_name = 'lecture'
AND table_schema = DATABASE();

