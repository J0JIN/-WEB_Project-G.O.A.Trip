package com.ssafy.goatrip.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.RequestAttraction;
import com.ssafy.goatrip.model.service.AttractionService;
import com.ssafy.goatrip.model.service.AttractionServiceImpl;

@Controller
@RequestMapping("/trip")
@CrossOrigin("*")
public class AttractionController {
	@Autowired
	public AttractionService aservice;

	@PostMapping("/getend")
	public ResponseEntity<?> getEndAttr(@RequestBody RequestAttraction radto) throws SQLException {
//		System.out.println("여기옴??");
		System.out.println(radto);
		// 시간 * 이동방식 디폴드 값
		double mint = radto.getMin_time();
		double maxt = radto.getMax_time();
		if (radto.getWalk_car().equals("car")) {
			radto.setMin_dist(mint * 40000);
			radto.setMax_dist(maxt * 40000);
		} else if (radto.getWalk_car().equals("walk")) {
			radto.setMin_dist(mint * 2000);
			radto.setMax_dist(maxt * 2000);
		}
		System.out.println(radto);

		AttractionDTO result = aservice.getEndAttr(radto);
		System.out.println("도착 지점 : " + result);
		return new ResponseEntity<AttractionDTO>(result, HttpStatus.OK);
	}

	@PostMapping("/getstopover")
	public ResponseEntity<?> getMidAttr(@RequestBody ObjectNode obj)
			throws SQLException, JsonProcessingException, IllegalArgumentException {
		ObjectMapper mapper = new ObjectMapper();

//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		for(String k:reqMap.keySet()) {
//			map.put(k, reqMap.get(k));
//			System.out.println("key 값 : "+ k);
//			System.out.println("value 타입 : "+ map.get(k).getClass().getTypeName()+" 값 : "+reqMap.get(k));
//		}

		AttractionDTO startdto = mapper.treeToValue(obj.get("startPoint"), AttractionDTO.class);

		AttractionDTO enddto = mapper.treeToValue(obj.get("endPoint"), AttractionDTO.class);

		int stopover = obj.get("stopoverNum").asInt();
		System.out.println("시작 지점 : " + startdto);
		System.out.println("도착 지점 : " + enddto);
		List<AttractionDTO> result = aservice.getMidAttr(startdto, enddto, stopover);
		System.out.println("경유지 : " + result);
		return new ResponseEntity<List>(result, HttpStatus.OK);
	}

}
