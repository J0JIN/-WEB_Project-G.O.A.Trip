package com.ssafy.goatrip.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.goatrip.model.dto.UserDTO;
import com.ssafy.goatrip.model.service.UserService;
import com.ssafy.goatrip.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Slf4j
public class UserController {

	private UserService userService;
	private JWTUtil jwtUtil;

	public UserController(UserService userService, JWTUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

////////////////////////////////////////////////////////////////////

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO user) {
		System.out.println(user);
		log.debug("login user : {}", user);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;

		UserDTO loginUser;
		try {
			loginUser = userService.login(user);
			if (loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUser_id());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUser_id());
				log.debug("access toekn : {}", accessToken);
				log.debug("refresh token :{}", refreshToken);

				// 발급받은 refresh token을 db에 저장.
				userService.saveRefreshToken(loginUser.getUser_id(), refreshToken);

				// json으로 token 전달.
				resultMap.put("access-token", accessToken);

				// cookie 변경
				resultMap.put("refresh-token", refreshToken);

				// 상태를 created로 바꿈
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("msg", "아이디 또는 패스워드를 확인해주세요");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("msg", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}// login

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/register")
	public ResponseEntity<?> Register(@RequestBody UserDTO user) {
		System.out.println(user);
		log.debug("register user : {}", user);
		HttpStatus status = HttpStatus.CREATED;
		String msg = "회원가입에 성공했습니다.";
		try {
			userService.regist(user);
			return new ResponseEntity<String>(msg, status);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}// login

	@ApiOperation(value = "로그아웃", notes = "비밀번호, 이름, 이메일을 수정.")
	@PostMapping("/modify")
	public ResponseEntity<?> Modify(@RequestBody UserDTO user) {
		System.out.println("\n수정하러 왔다\n");
		System.out.println(user);
		log.debug("modify user : {}", user);
		HttpStatus status = HttpStatus.OK;
		String msg = "회원 정보 수정에 성공했습니다.";
		try {
			userService.modify(user);
			return new ResponseEntity<String>(msg, status);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}// login

	@ApiOperation(value = "회원탈퇴", notes = "유저 삭제")
	@GetMapping("/delete/{user_id}")
	public ResponseEntity<?> userDelete(@PathVariable("user_id") String user_id) {
		System.out.println("\n" + user_id + "을 탈퇴하러왔다\n");
		log.debug("delete user : {}", user_id);
		HttpStatus status = HttpStatus.OK;
		String msg = "회원탈퇴에 성공했습니다.";
		try {
			userService.delete(user_id);
			return new ResponseEntity<String>(msg, status);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}// login

	@GetMapping("/info/{user_id}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("user_id") String user_id,
			HttpServletRequest request) {
		System.out.println("in controller id : " + user_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!");
			try {
				UserDTO udto = userService.selectOne(user_id);
				resultMap.put("userInfo", udto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("msg", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능한 토큰입니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{user_id}")
	public ResponseEntity<?> removeToken(@PathVariable("user_id") String user_id) {
		System.out.println("로그아웃 하려는 유저의 id : " + user_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(user_id);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("msg", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDTO udto, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {} , UserDTO : {}", token, udto);

		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(udto.getUser_id()))) {
				String accessToken = jwtUtil.createAccessToken(udto.getUser_id());
				log.debug("token: {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급 되었습니다.");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("refresh토큰이 사용 불가능합니다.");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
