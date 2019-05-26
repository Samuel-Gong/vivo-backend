package nju.edu.cn.backend.dao;

import nju.edu.cn.backend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * 讲座数据访问
 * <p>
 *
 * @author Shenmiu
 */
@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long>, QuerydslPredicateExecutor<Lecture> {
}
