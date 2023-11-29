package com.ssafy.goatrip.model.dao;

import java.util.Map;

import com.ssafy.goatrip.model.dto.UserDTO;

//@Mapper
public interface UserDAO {

	// 입력과 같은 user객체 반환
	public UserDTO login(UserDTO udto);

	// user 등록
	public int regist(UserDTO user);

	// user 제거
	public int delete(String id);

	// user 정보 수정
	public int modify(UserDTO user);

	public UserDTO selectOne(String id);

	// jwt token 함수
	void saveRefreshToken(Map<String, String> map) throws Exception;

	Object getRefreshToken(String user_id) throws Exception;

	void deleteRefreshToken(Map<String, String> map) throws Exception;
}
