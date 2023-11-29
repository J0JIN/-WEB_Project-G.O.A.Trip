package com.ssafy.goatrip.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.goatrip.model.dao.UserDAO;
import com.ssafy.goatrip.model.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired // (required=true)
	private UserDAO userDao;

	@Override
	public int regist(UserDTO user) {
		return userDao.regist(user);
	}

	@Override
	public int delete(String id) {
		return userDao.delete(id);
	}

	@Override
	public int modify(UserDTO user) {
		return userDao.modify(user);
	}

	@Override
	public UserDTO selectOne(String id) {
		System.out.println("selectOne search by id : " + id);
		return userDao.selectOne(id);
	}

	@Override
	public UserDTO login(UserDTO udto) throws Exception {
		return userDao.login(udto);
	}

	@Override
	public void saveRefreshToken(String user_id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("token", refreshToken);
		userDao.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String user_id) throws Exception {
		return userDao.getRefreshToken(user_id);
	}

	@Override
	public void deleteRefreshToken(String user_id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("token", null);
		userDao.deleteRefreshToken(map);
	};

}
