package com.university.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 더미데이터 생성하는 query문 만드는 기능
public class DataGenerator {
	
	public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public static String staffQuery = "insert into staff(hire_date) values(now());";
	public static String professorQuery = "insert into professor(hire_date, department_id) values(now(), %d);";
	public static String studentQuery = "insert into student(entrance_date, department_id) values(%s, %d);";
	public static String userQuery = "insert into user(id, password, role, name, gender, birth_date, address, email, tel) values(%d, %s, %s, %s, %s, %s, %s, %s, %s);";
	
	public static List<Integer> 학과 = Arrays.asList(101, 102, 103, 104);
	
	public static List<String> 년1 = Arrays.asList("2000","2001","2002","2003","2004");
	public static List<String> 년2 = Arrays.asList("1960","1961","1962","1963","1964","1965","1966","1967","1968","1969","1970","1971","1972","1973",
			"1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985");
	public static List<String> 년3 = Arrays.asList("1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988",
			"1989","1990","1991","1992","1993","1994","1995","1996","1997");
	public static List<String> 월 = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12");
	public static List<String> 일1 = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29","30");
	public static List<String> 일2 = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28","29","30","31");
	public static List<String> 일3 = Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19",
			"20","21","22","23","24","25","26","27","28");
	
	public static List<String> 주소 = Arrays.asList("인천시 연수구", "인천시 남동구", "인천시 부평구", "인천시 서구", "인천시 미추홀구", "인천시 계양구", "인천시 중구", 
			"인천시 동구", "서울시 남대문구", "서울시 은평구", "서울시 서대문구", "서울시 마포구", "서울시 영등포구", "서울시 동대문구", "서울시 구로구", "서울시 용산구", "서울시 종로구", 
			"서울시 강남구", "서울시 강서구", "서울시 송파구", "서울시 강동구", "서울시 서초구", "서울시 강북구", "서울시 도봉구", "서울시 금천구");
	
	public static List<String> 메일 = Arrays.asList("@gmail.com", "@naver.com");
	
	public static String name() {
		List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안",
		        "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심", "노", "정", "하", "곽", "성", "차", "주",
		        "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄", "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양",
		        "변", "여", "추", "노", "도", "소", "신", "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금",
		        "옥", "육", "인", "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용");
		List<String> 이름 = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
		        "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
		        "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
		        "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
		        "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
		        "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
		        "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
		        "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
		        "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
		        "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향", "울", "련");
		Collections.shuffle(성);
	    Collections.shuffle(이름);
	    return "'" + 성.get(0) + 이름.get(0) + 이름.get(1) + "'";
	}
	
	public static String password() {
		return "'" +  passwordEncoder.encode("1234") + "'";
	}
	
	//role은 만들때 직접 넣기
	
	public static String gender() {
		List<String> 성별 = Arrays.asList("남성", "여성");
		Collections.shuffle(성별);
		return "'" + 성별.get(0) + "'";
	}
	
	public static String studentBirthDate() {
		Collections.shuffle(년1);
		Collections.shuffle(월);
		
		String birthDate = 년1.get(0) + "-" + 월.get(0);
		String month = birthDate.substring(birthDate.indexOf("-") + 1);
		
		switch (month) {
		case "02":
			Collections.shuffle(일3);
			birthDate += "-" + 일3.get(0);
			break;
		case "01", "03", "05", "07", "08", "10", "12":
			Collections.shuffle(일2);
			birthDate += "-" + 일2.get(0);
			break;
		case "04", "06", "09", "11":
			Collections.shuffle(일1);
			birthDate += "-" + 일1.get(0);			
			break;
		}
		
		return "'" + birthDate + "'";
	}
	
	public static String professorBirthDate() {
		Collections.shuffle(년2);
		Collections.shuffle(월);

		String birthDate = 년2.get(0) + "-" + 월.get(0);
		String month = birthDate.substring(birthDate.indexOf("-") + 1);
		
		switch (month) {
		case "02":
			Collections.shuffle(일3);
			birthDate += "-" + 일3.get(0);
			break;
		case "01", "03", "05", "07", "08", "10", "12":
			Collections.shuffle(일2);
			birthDate += "-" + 일2.get(0);
			break;
		case "04", "06", "09", "11":
			Collections.shuffle(일1);
			birthDate += "-" + 일1.get(0);
			break;
		}
		
		return "'" + birthDate + "'";
	}
	
	public static String staffBirthDate() {
		Collections.shuffle(년3);
		Collections.shuffle(월);

		String birthDate = 년3.get(0) + "-" + 월.get(0);
		String month = birthDate.substring(birthDate.indexOf("-") + 1);
		
		switch (month) {
		case "02":
			Collections.shuffle(일3);
			birthDate += "-" + 일3.get(0);
			break;
		case "01", "03", "05", "07", "08", "10", "12":
			Collections.shuffle(일2);
			birthDate += "-" + 일2.get(0);
			break;
		case "04", "06", "09", "11":
			Collections.shuffle(일1);
			birthDate += "-" + 일1.get(0);
			break;
		}
		
		return "'" + birthDate + "'";
	}
	
	public static String address() {
		Collections.shuffle(주소);
		return "'" + 주소.get(0) + "'";
	}
	
	public static String email() {
		String email = "";
		String ran = "abcdefghijklmnopqrstuvwxyz";
		
		for(int i = 0; i < 6; i++) {
			email += ran.charAt((int)(Math.random() * ran.length())); 
		}
		
		email += (int)(Math.random() * 99)+1;
		Collections.shuffle(메일);
		email += 메일.get(0);
		
		return "'" + email + "'";
	}
	
	public static String tel() {
		String tel = "010-";
		tel += (int)(Math.random() * 8999)+1000+"-";
		tel += (int)(Math.random() * 8999)+1000;
		return "'" + tel + "'";
	}
	
	public static int department() {
		Collections.shuffle(학과);
		return 학과.get(0);
	}
	
	public static void staffQueryMethod() {
		for(int i=2301; i<2351; i++) {
			System.out.println(staffQuery);
			System.out.printf(userQuery, i, password(), "'STAFF'", name(), gender(), staffBirthDate(), address(), email(), tel());
			System.out.println();
		}
	}
	
	public static void professorQueryMethod() {
		for(int i=230001; i<230051; i++) {
			System.out.printf(professorQuery, department());
			System.out.println();
			System.out.printf(userQuery, i, password(), "'PROFESSOR'", name(), gender(), professorBirthDate(), address(), email(), tel());
			System.out.println();
		}
	}
	
	public static void studentQueryMethod() {
		for(int i=23000001; i<23000201; i++) {
			System.out.printf(studentQuery, "'2023-03-02'", department());
			System.out.println();
			System.out.printf(userQuery, i, password(), "'STUDENT'", name(), gender(), studentBirthDate(), address(), email(), tel());
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		staffQueryMethod();
	}

}
