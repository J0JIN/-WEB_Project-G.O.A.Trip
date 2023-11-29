package com.ssafy.goatrip.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.goatrip.model.dto.BoardDTO;

public interface BoardDAO {
	public int createArticle(BoardDTO article) throws SQLException;

	public int deleteByIdArticle(String article_id) throws SQLException;

	public int modifyArticle(BoardDTO article) throws SQLException;

	public BoardDTO selectByIdArticle(int article_id) throws SQLException;

	public List<BoardDTO> selectByCategoryBoard(Map<String, Object> param) throws SQLException;
	
	int getTotalArticleCount(Map<String, Object> param) throws SQLException;
}
