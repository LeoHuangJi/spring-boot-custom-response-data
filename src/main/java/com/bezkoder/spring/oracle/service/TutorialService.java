package com.bezkoder.spring.oracle.service;

import java.util.List;

import com.bezkoder.spring.oracle.dto.ResponseData;
import com.bezkoder.spring.oracle.model.Tutorial;

public interface TutorialService {

	ResponseData getAll();
	ResponseData getAll2(String ten)  throws Exception;

	ResponseData save(long id,Tutorial obj) throws Exception;

	ResponseData delete(long id);

	ResponseData findById(long id);

	long findByCode(String code);
	 ResponseData saveV2(List<Tutorial> body) throws Exception;

}