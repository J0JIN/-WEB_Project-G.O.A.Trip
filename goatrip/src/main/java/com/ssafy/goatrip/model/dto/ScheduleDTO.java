package com.ssafy.goatrip.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ScheduleDTO {
	private int sch_id;
	private String sch_name;
	private String user_id;
	private String create_date;
	private double start_lat;
	private double start_long;
	private List<AttractionDTO> stopover;

	public ScheduleDTO(int sch_id, String sch_name, String user_id, String create_date, double start_lat,
			double start_long, List<AttractionDTO> stopover) {
		super();
		this.sch_id = sch_id;
		this.sch_name = sch_name;
		this.user_id = user_id;
		this.create_date = create_date;
		this.start_lat = start_lat;
		this.start_long = start_long;
		this.stopover = stopover;
	}

	public ScheduleDTO() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int getSch_id() {
		return sch_id;
	}

	public void setSch_id(int sch_id) {
		this.sch_id = sch_id;
	}

	public String getSch_name() {
		return sch_name;
	}

	public void setSch_name(String sch_name) {
		this.sch_name = sch_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public double getStart_lat() {
		return start_lat;
	}

	public void setStart_lat(double start_lat) {
		this.start_lat = start_lat;
	}

	public double getStart_long() {
		return start_long;
	}

	public void setStart_long(double start_long) {
		this.start_long = start_long;
	}

	public List<AttractionDTO> getStopover() {
		return stopover;
	}

	public void setStopover(List<AttractionDTO> stopover) {
		this.stopover = stopover;
	}

	@Override
	public String toString() {
		return "ScheduleDTO [sch_id=" + sch_id + ", sch_name=" + sch_name + ", user_id=" + user_id + ", create_date="
				+ create_date + ", start_lat=" + start_lat + ", start_long=" + start_long + ", stopover=" + stopover
				+ "]";
	}

}
