package com.ssafy.goatrip.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.ScheduleDTO;

public interface ScheduleDAO {
	// 내 여행에 들어가서 일정 목록 조회할 때 나올 일정 리스트
		List<ScheduleDTO> getScheduleList(String user_id) throws SQLException;
		
		List<AttractionDTO> getStopoverList(int sch_id) throws SQLException;

		// 일정 등록
		int registerSchedule(ScheduleDTO sdto) throws SQLException;
		int registerStopover(ScheduleDTO sdto) throws SQLException;

		// 일정 수정
		int modifySchedule(ScheduleDTO sdto) throws SQLException;

		// 일정 삭제
		int deleteStopover(int sch_id) throws SQLException;
		int deleteSchedule(int sch_id) throws SQLException;
}
