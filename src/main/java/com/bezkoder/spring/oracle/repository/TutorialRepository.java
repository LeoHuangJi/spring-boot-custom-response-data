package com.bezkoder.spring.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bezkoder.spring.oracle.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	List<Tutorial> findByPublished(boolean published);

	List<Tutorial> findByTitleContaining(String title);

	@Query(value = "SELECT count(1) from TUTORIALS where code=?", nativeQuery = true)
	long findByCode(@Param("code") String code);
}
