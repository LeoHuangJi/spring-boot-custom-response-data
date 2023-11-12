package com.bezkoder.spring.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bezkoder.spring.oracle.model.Category;

public interface  CategoryRepository extends JpaRepository<Category, Long> {
	
}
