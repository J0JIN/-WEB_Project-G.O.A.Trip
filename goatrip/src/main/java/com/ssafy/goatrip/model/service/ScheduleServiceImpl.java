package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.goatrip.model.dao.ScheduleDAO;
import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.ScheduleDTO;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleDAO sdao;

	@Override
	public List<ScheduleDTO> getScheduleList(String user_id) throws SQLException {
		// 먼저 스케쥴 뽑아오고 스케쥴 뽑은 것에서 각각의 스케쥴마다 경유지와 도착지점의 정보를 불러온다.
		// 스케쥴 리스트 뽑기
		List<ScheduleDTO> schList = sdao.getScheduleList(user_id);
		System.out.println(schList);
		for (int i = 0; i < schList.size(); i++) {
			// 각 일정마다 stopover에 리스트를 넣어준다.
			// 현재의 sch_id를 뽑아낸다.
			int nowsch = schList.get(i).getSch_id();
			List<AttractionDTO> attr_list = new ArrayList<AttractionDTO>();
			
			attr_list.add(new AttractionDTO("내 위치", schList.get(i).getStart_lat(),schList.get(i).getStart_long() ));
			attr_list.addAll(sdao.getStopoverList(nowsch));
			schList.get(i).setStopover(attr_list);
		}
		// 각 일정마다 경유지와 도착지점에 대한 정보를 넣은 뒤 반환한다.

		// test 출력
		System.out.println(schList);

		return schList;
	}

	@Override
	public void registerSchedule(Map<String, Object> map, List<AttractionDTO> stopover) throws SQLException {
		// map 을 분해해서 받기
//		System.out.println(map);

		ScheduleDTO sdto = new ScheduleDTO();
		sdto.setSch_name((String) map.get("sch_name"));
		sdto.setUser_id((String) map.get("user_id"));
		sdto.setCreate_date((String) map.get("create_date"));
		sdto.setStart_lat((double) map.get("start_lat"));
		sdto.setStart_long((double) map.get("start_long"));
		sdto.setStopover(stopover);
//		sdto.setStopover(stopover);
		System.out.println(sdto);
		// 일정 먼저 등록 후 일정안에 있는 attraction 등록
		sdao.registerSchedule(sdto);
		List<AttractionDTO> attr_list = sdto.getStopover();
		System.out.println("??"+attr_list);
		if (attr_list != null && !attr_list.isEmpty()) {
			sdao.registerStopover(sdto);
		}
	}

	@Override
	public int modifySchedule(Map<String, Object> map) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteSchedule(int sch_id) throws SQLException {
		sdao.deleteStopover(sch_id);
		sdao.deleteSchedule(sch_id);
	}

}
