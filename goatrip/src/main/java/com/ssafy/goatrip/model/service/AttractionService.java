package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.RequestAttraction;

public interface AttractionService {
	//도착 장소 하나 정하기 
//	public List<AttractionDTO> getEndAttr(RequestAttraction radto) throws SQLException; 
	public AttractionDTO getEndAttr(RequestAttraction radto) throws SQLException; 
	
	//중간 장소 하나 정하기
	public List<AttractionDTO> getMidAttr(AttractionDTO startdto, AttractionDTO enddto, int stopover) throws SQLException;
}
