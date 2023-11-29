package com.ssafy.goatrip.model.dto;


public class BoardDTO {
	String article_id;
	String title;
	int category;
	String writer;
	String user_name;
	String content;
	int hit;
	String create_date;
	String comment_num;

	public BoardDTO() {
	}

	public BoardDTO(String article_id, String title, int category, String writer, String user_name, String content,
			int hit, String create_date, String comment_num) {
		this.article_id = article_id;
		this.title = title;
		this.category = category;
		this.writer = writer;
		this.user_name = user_name;
		this.content = content;
		this.hit = hit;
		this.create_date = create_date;
		this.comment_num = comment_num;
	}
//////////////////////////

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getComment_num() {
		return comment_num;
	}

	public void setComment_num(String comment_num) {
		this.comment_num = comment_num;
	}

	@Override
	public String toString() {
		return "BoardDTO [article_id=" + article_id + ", title=" + title + ", category=" + category + ", writer="
				+ writer + ", user_name=" + user_name + ", content=" + content + ", hit=" + hit + ", create_date="
				+ create_date + ", comment_num=" + comment_num + "]";
	}
	
}
