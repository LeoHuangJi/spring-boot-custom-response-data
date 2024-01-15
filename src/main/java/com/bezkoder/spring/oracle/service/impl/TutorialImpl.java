package com.bezkoder.spring.oracle.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bezkoder.spring.oracle.constant.Message;
import com.bezkoder.spring.oracle.dto.ResponseData;
import com.bezkoder.spring.oracle.dto.TutorialBulkInsert;
import com.bezkoder.spring.oracle.dto.TutorialSearchResultDTO;
import com.bezkoder.spring.oracle.exception.InvalidEmailException;
import com.bezkoder.spring.oracle.model.Category;
import com.bezkoder.spring.oracle.model.Tutorial;
import com.bezkoder.spring.oracle.repository.CategoryRepository;
import com.bezkoder.spring.oracle.repository.TutorialRepository;
import com.bezkoder.spring.oracle.service.TutorialService;
import com.bezkoder.spring.oracle.util.EntityParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TutorialImpl implements TutorialService {
	@Autowired
	private TutorialRepository tutorialRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntityManager _entityManager;;

	public ResponseData getAll() {
		List<Tutorial> lst = tutorialRepository.findAll();
		if (!ObjectUtils.isEmpty(lst))
			return new ResponseData(HttpStatus.OK, true, Message.OK, lst);
		return new ResponseData(HttpStatus.NOT_FOUND, false, Message.NOT_FOUND, null);
	}

	public ResponseData getAll2(String ten) throws Exception {

		StoredProcedureQuery query = _entityManager.createStoredProcedureQuery("get_all_tutorial");
		// Set parameters if needed

		// String encodedValue = URLEncoder.encode(ten,
		// StandardCharsets.UTF_8.toString());

		query.registerStoredProcedureParameter("ten", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("o_c_dbuser", void.class, ParameterMode.REF_CURSOR);
		query.setParameter("ten", ten);
		query.execute();

		ResultSet rs = (ResultSet) query.getOutputParameterValue("o_c_dbuser");

		/*
		 * while (rs.next()) { String uid = rs.getString("TITLE"); // String fname =
		 * rs.getString("FirstName");
		 * 
		 * // users.add(u); System.out.println(uid); }
		 */

		List<TutorialSearchResultDTO> jedis = EntityParser.resultSetToList(rs, TutorialSearchResultDTO.class);

		// List<TutorialSearchResultDTO> d= EntityParser.parseResultSet(resultSet,
		// TutorialSearchResultDTO.class);

		// List<TutorialSearchResultDTO> results = query.getResultList();

		return new ResponseData(HttpStatus.OK, true, Message.OK, jedis);
		/// return new ResponseData(HttpStatus.NOT_FOUND, false, Message.NOT_FOUND,
		/// null);
	}

	public ResponseData save(long id, Tutorial obj) throws Exception {
		if (id > 0) {
			Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
			if (tutorialData.isPresent()) {
				Tutorial tutorial = tutorialData.get();
				tutorial.setCategory_id(obj.getCategory_id());
				tutorial.setCode(obj.getCode());
				tutorial.setDescription(obj.getDescription());
				/// tutorial.setPublished(obj.get);
				tutorial.setTitle(obj.getTitle());
				return new ResponseData(HttpStatus.OK, true, Message.OK, tutorialRepository.save(tutorial));
			}

		}
		// check mã
		/*
		 * if (!ObjectUtils.isEmpty(obj.getCode())) { if
		 * (tutorialRepository.findByCode(obj.getCode()) > 0) return new
		 * ResponseData(HttpStatus.NOT_ACCEPTABLE, false, Message.NOT_ACCEPTABLE, null);
		 * }
		 */
		Tutorial tutorialInserted = tutorialRepository.save(obj);
		if (!ObjectUtils.isEmpty(tutorialInserted))
			return new ResponseData(HttpStatus.CREATED, true, Message.CREATED, tutorialInserted);
		return new ResponseData(HttpStatus.BAD_REQUEST, false, Message.BAD_REQUEST, null);
	}

	public ResponseData saveV2(List<Tutorial> body) throws Exception {

		try {
			

			
			return new ResponseData(HttpStatus.OK, true, Message.OK, tutorialRepository.saveAll(body));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseData(HttpStatus.BAD_REQUEST, false, Message.BAD_REQUEST, null);
	}

	public ResponseData bulkInsert(TutorialBulkInsert obj) throws Exception {

		Category cat = categoryRepository.save(obj.getCategory());
		if (cat != null) {

			obj.getTutorial().stream().parallel().forEach(i -> i.setCategory_id(cat.getId()));
			tutorialRepository.saveAll(obj.getTutorial());
		}

		return new ResponseData(HttpStatus.OK, true, Message.OK, obj);
	}

	public ResponseData delete(long id) {
		tutorialRepository.deleteById(id);
		return new ResponseData(HttpStatus.OK, true, Message.OK, null);

	}

	public ResponseData findById(long id) {

		/*
		 * ResponseData s = this.findById2(id); if (s.getStatus()) { Tutorial d =
		 * (Tutorial) s.getData(); return new ResponseData(HttpStatus.OK, true,
		 * Message.OK, s.getData());
		 * 
		 * }
		 */

		Optional<Tutorial> obj = tutorialRepository.findById(id);
		if (obj.isPresent())
			return new ResponseData(HttpStatus.OK, true, Message.OK, obj.get());
		return new ResponseData(HttpStatus.NOT_FOUND, false, Message.NOT_FOUND, null);
	}

	public ResponseData findById2(long id) {
		Optional<Tutorial> obj = tutorialRepository.findById(id);
		if (obj.isPresent())
			return new ResponseData(HttpStatus.OK, true, "", obj.get());
		return new ResponseData(HttpStatus.NOT_FOUND, false, Message.NOT_FOUND, null);
	}

	public long findByCode(String code) {
		return tutorialRepository.findByCode(code);
	}
}