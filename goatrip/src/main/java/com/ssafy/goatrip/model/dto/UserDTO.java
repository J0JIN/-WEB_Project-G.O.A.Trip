package com.ssafy.goatrip.model.dto;

//@Setter
//@Getter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class UserDTO {
	
	private String user_id;
	private String user_pw;
	private String user_name;
	private String user_email;
	private String user_email_domain;
	private String user_joindate;
	
	public UserDTO() {}
	
	public UserDTO(String user_id, String user_pw, String user_name, String user_email, String user_email_domain,
			String user_joindate) {
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_name = user_name;
		this.user_email = user_email;
		this.user_email_domain = user_email_domain;
		this.user_joindate = user_joindate;
	}
///////////////////////////////////////////////////////////////////////////////	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_email_domain() {
		return user_email_domain;
	}
	public void setUser_email_domain(String user_email_domain) {
		this.user_email_domain = user_email_domain;
	}
	public String getUser_joindate() {
		return user_joindate;
	}
	public void setUser_joindate(String user_joindate) {
		this.user_joindate = user_joindate;
	}
	@Override
	public String toString() {
		return "UserDTO [user_id=" + user_id + ", user_pw=" + user_pw + ", user_name=" + user_name + ", user_email="
				+ user_email + ", user_email_domain=" + user_email_domain + ", user_joindate=" + user_joindate + "]";
	}
	
}
