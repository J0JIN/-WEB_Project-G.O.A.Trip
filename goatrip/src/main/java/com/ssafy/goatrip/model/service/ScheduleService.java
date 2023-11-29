package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.ScheduleDTO;

public interface ScheduleService {
	// 내 여행에 들어가서 일정 목록 조회할 때 나올 일정 리스트
	List<ScheduleDTO> getScheduleList(String user_id) throws SQLException;

	// 일정 등록
	void registerSchedule(Map<String, Object> map, List<AttractionDTO> stopover) throws SQLException;

	// 일정 수정
	int modifySchedule(Map<String, Object> map) throws SQLException;

	// 일정 삭제
	void deleteSchedule(int sch_id) throws SQLException;

}
