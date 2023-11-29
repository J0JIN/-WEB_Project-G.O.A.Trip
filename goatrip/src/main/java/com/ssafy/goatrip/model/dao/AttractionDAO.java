package com.ssafy.goatrip.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.RequestAttraction;

public interface AttractionDAO {
	//attraction 도착 장소 뽑기(리스트로 만들어서 Service에 넘기고 service에서 그 값으로 하나를 랜덤으로 뽑아서 넘긴다.)
	public List<AttractionDTO> getEndAttr(RequestAttraction radto) throws SQLException;
	
	//attraction 출발 - 도착 경유지 리스트 뽑기
	public List<AttractionDTO> getMidAttr(double midlong, double midlat, double radius) throws SQLException;
	
}
