package com.bezkoder.spring.oracle.dto;

import java.util.List;

import com.bezkoder.spring.oracle.model.Category;
import com.bezkoder.spring.oracle.model.Tutorial;

public class TutorialBulkInsert {
private Category category;
private List<Tutorial>  tutorial;
public TutorialBulkInsert(Category category, List<Tutorial> tutorial) {
	super();
	this.category = category;
	this.tutorial = tutorial;
}
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
public List<Tutorial> getTutorial() {
	return tutorial;
}
public void setTutorial(List<Tutorial> tutorial) {
	this.tutorial = tutorial;
}
}
