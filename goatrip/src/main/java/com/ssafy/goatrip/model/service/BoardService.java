package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.goatrip.model.dto.BoardDTO;
import com.ssafy.goatrip.model.dto.BoardListDto;

public interface BoardService {
	public int createArticle(BoardDTO article) throws SQLException;

	public int deleteByIdArticle(String article_id) throws SQLException;

	public int modifyArticle(BoardDTO article) throws SQLException;

	public BoardDTO selectByIdArticle(int article_id) throws SQLException;

	BoardListDto selectByCategoryBoard(Map<String, String> map) throws Exception;
}
