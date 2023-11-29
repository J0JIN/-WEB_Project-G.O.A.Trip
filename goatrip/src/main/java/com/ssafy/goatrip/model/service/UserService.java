package com.ssafy.goatrip.model.service;

import com.ssafy.goatrip.model.dto.UserDTO;

public interface UserService {
	// 입력과 같은 user객체 반환
	UserDTO login(UserDTO udto) throws Exception;

	// id 기반 user객체 반환
	UserDTO selectOne(String user_id) throws Exception;

	// user 등록
	int regist(UserDTO udto) throws Exception;

	// user 제거
	int delete(String user_id) throws Exception;

	// user 정보 수정
	int modify(UserDTO udto) throws Exception;

	// jwt token 함수
	void saveRefreshToken(String user_id, String refreshToken) throws Exception;

	Object getRefreshToken(String user_id) throws Exception;

	void deleteRefreshToken(String user_id) throws Exception;

}
