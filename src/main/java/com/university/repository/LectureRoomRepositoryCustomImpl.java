package com.university.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.LectureRoomDto;
import com.university.dto.QLectureRoomDto;
import com.university.entity.QCollege;
import com.university.entity.QLectureRoom;

import jakarta.persistence.EntityManager;

public class LectureRoomRepositoryCustomImpl implements LectureRoomRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public LectureRoomRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<LectureRoomDto> getLectureRoomList(Pageable pageable) {
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		QCollege college = QCollege.college;
		
		List<LectureRoomDto> content = queryFactory
				.select(
					new QLectureRoomDto(
							lectureRoom.id, 
							college.name
							)
						)
				.from(lectureRoom)
				.join(lectureRoom.college, college)
				.orderBy(lectureRoom.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(lectureRoom)
				.join(lectureRoom.college, college)
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
}
