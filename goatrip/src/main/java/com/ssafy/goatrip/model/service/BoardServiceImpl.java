package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.goatrip.model.dao.BoardDAO;
import com.ssafy.goatrip.model.dto.BoardDTO;
import com.ssafy.goatrip.model.dto.BoardListDto;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	public BoardDAO bdao;



	@Override
	public BoardListDto selectByCategoryBoard(Map<String, String> map) throws Exception {

		Map<String, Object> param = new HashMap<String, Object>();
		//카테고리는 반드시 전달받음
		param.put("category", map.get("category"));

		param.put("word", map.get("word") == null ? "" : map.get("word"));

		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		
		param.put("key", key == null ? "" : key);
		if ("writer".equals(key))
			param.put("key", key == null ? "" : "b.writer");
		
		
		//list를 db에서 뽑아옴
		List<BoardDTO> list = bdao.selectByCategoryBoard(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "writer");
		
		int totalArticleCount = bdao.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}



	@Override
	public int createArticle(BoardDTO article) throws SQLException {
		return bdao.createArticle(article);
	}



	@Override
	public int deleteByIdArticle(String article_id) throws SQLException {
		return bdao.deleteByIdArticle(article_id);
	}



	@Override
	public int modifyArticle(BoardDTO article) throws SQLException {
		return bdao.modifyArticle(article);
	}



	@Override
	public BoardDTO selectByIdArticle(int article_id) throws SQLException {
		return bdao.selectByIdArticle(article_id);
	}

}
