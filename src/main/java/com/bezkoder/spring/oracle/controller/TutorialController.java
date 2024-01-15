package com.bezkoder.spring.oracle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.oracle.dto.ResponseData;
import com.bezkoder.spring.oracle.model.Tutorial;
import com.bezkoder.spring.oracle.repository.TutorialRepository;
import com.bezkoder.spring.oracle.service.TutorialService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

	@Autowired
	TutorialRepository tutorialRepository;
	@Autowired
	private TutorialService tutorialService;

	@GetMapping("/tutorials")
	public ResponseEntity<ResponseData> getAllTutorials(@RequestParam(required = false) String title) {
		ResponseData res = tutorialService.getAll();
		return new ResponseEntity<>(res, res.getHttpStatus());
	}
	
	@GetMapping("/tutorials2")
	public ResponseEntity<ResponseData> getAllTutorials2(@RequestParam(required = false) String title) throws Exception {
		ResponseData res = tutorialService.getAll2(title);
		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Object> findById(@PathVariable("id") long id) {
		ResponseData res = tutorialService.findById(id);
		return new ResponseEntity<>(res, res.getHttpStatus());

	}

	@PostMapping("/tutorials")
	public ResponseEntity<ResponseData> createTutorial(@RequestBody Tutorial tutorial) throws Exception {
		ResponseData res = tutorialService.save(0,tutorial);
		return new ResponseEntity<>(res, res.getHttpStatus());
	}
	
	@PostMapping("/tutorials2")
	public ResponseEntity<ResponseData> createTutorial2(@RequestBody List<Tutorial> body) throws Exception {
		ResponseData res = tutorialService.saveV2(body);
		return new ResponseEntity<>(res, res.getHttpStatus());
	}


	@PutMapping("/tutorials/{id}")
	public ResponseEntity<ResponseData> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) throws Exception {
		
		ResponseData res = tutorialService.save(id,tutorial);
		return new ResponseEntity<>(res, res.getHttpStatus());
		
		
	}

	@DeleteMapping("/tutorials/{id}")
	public ResponseEntity<ResponseData> deleteTutorial(@PathVariable("id") long id) {
		ResponseData res = tutorialService.delete(id);
		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	

	
}
