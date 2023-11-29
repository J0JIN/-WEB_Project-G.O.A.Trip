package com.ssafy.goatrip.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.ScheduleDTO;
import com.ssafy.goatrip.model.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/schedule")
@CrossOrigin("*")
public class ScheduleController {
	private ScheduleService sservice;

	public ScheduleController(ScheduleService sservice) {
		this.sservice = sservice;
	}

	// 리스트 불러오기
	@GetMapping("/{user_id}")
	public ResponseEntity<?> getScheduleList(@PathVariable String user_id) throws SQLException {
		System.out.println(user_id);
		List<ScheduleDTO> result = sservice.getScheduleList(user_id);
		return new ResponseEntity<List<ScheduleDTO>>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{sch_id}")
	public ResponseEntity<?> deleteScheduleList(@PathVariable int sch_id) {
		System.out.println("삭제할 스케쥴");
		String msg = "삭제 성공~~";
		try {
			sservice.deleteSchedule(sch_id);
			return new ResponseEntity<String>("성공", HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}

	// 일정 등록하기
	@PostMapping("/register")
	public ResponseEntity<?> registerSchedule(@RequestBody ObjectNode obj)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		String sch_name = obj.get("sch_name").toString().substring(1, obj.get("sch_name").toString().length() - 1);
		map.put("sch_name", sch_name);
		System.out.println("이거 큰 따옴표가 들어갔나? " + sch_name);
		String user_id = obj.get("user_id").toString().substring(1, obj.get("user_id").toString().length() - 1);
		map.put("user_id", user_id);
		map.put("start_lat", obj.get("start_lat").asDouble());
		map.put("start_long", obj.get("start_long").asDouble());
		String jsonStr = obj.get("stopover").toString();

		// stopover를 array로 받아서 처리한다.
		ArrayNode array = (ArrayNode) mapper.readTree(jsonStr);
		List<AttractionDTO> attr_list = new LinkedList<AttractionDTO>();
		if (array.isArray()) {
			for (JsonNode jsonNode : array) {
				AttractionDTO attr = mapper.treeToValue(jsonNode, AttractionDTO.class);
				attr_list.add(attr);
			}
		}
//		System.out.println("여기 리스트 전체 "+attr_list);

//		map.put("stopover", attr_list);

//		log.info("scheduleRegister map - {}", map);
		String msg = "일정 등록에 성공했습니다.";
		System.out.println(map);
		int result = 0;
		System.out.println("map : " + map);
		System.out.println("list : " + attr_list);
		try {
			sservice.registerSchedule(map, attr_list);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
