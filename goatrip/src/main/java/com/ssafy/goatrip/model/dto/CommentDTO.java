package com.ssafy.goatrip.model.dto;

public class CommentDTO {
	String comment_id;
	String board_id;
	String writer;
	String com_content;
	String createdate;

	public CommentDTO() {
	}

	public CommentDTO(String comment_id, String board_id, String writer, String com_content, String createdate) {
		super();
		this.comment_id = comment_id;
		this.board_id = board_id;
		this.writer = writer;
		this.com_content = com_content;
		this.createdate = createdate;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getBoard_id() {
		return board_id;
	}

	public void setBoard_id(String board_id) {
		this.board_id = board_id;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getCom_content() {
		return com_content;
	}

	public void setCom_content(String com_content) {
		this.com_content = com_content;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "CommentDTO [comment_id=" + comment_id + ", board_id=" + board_id + ", writer=" + writer
				+ ", com_content=" + com_content + ", createdate=" + createdate + "]";
	}

}
