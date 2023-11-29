package com.ssafy.goatrip.model.dto;

import java.util.HashMap;
import java.util.Map;

public class RequestAttraction {
	// 시작 좌표, 최소~최대 시간, 경유지 개수, 도보인지 차량인지,
	double start_long;
	double start_lat;
	double min_time;
	double max_time;
	int stopover;
	String walk_car;
	double min_dist;
	double max_dist;

	public RequestAttraction() {
	}

	public RequestAttraction(double start_long, double start_lat, double min_time, double max_time, int stopover,
			String walk_car) {
		this.start_long = start_long;
		this.start_lat = start_lat;
		this.min_time = min_time;
		this.max_time = max_time;
		this.stopover = stopover;
		this.walk_car = walk_car;
	}

public RequestAttraction(double start_long, double start_lat, double min_time, double max_time, int stopover,
			String walk_car, double min_dist, double max_dist) {
		this.start_long = start_long;
		this.start_lat = start_lat;
		this.min_time = min_time;
		this.max_time = max_time;
		this.stopover = stopover;
		this.walk_car = walk_car;
		this.min_dist = min_dist;
		this.max_dist = max_dist;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// 현재 점과 다른 점이 주어졌을 때 타원의 초점 구하는 함수
	public double[] getC(RequestAttraction o) {
		// 1. 한변의 길이 구하기
//		double r = Math.pow(this.getStart_lat(), o.getStart_lat()) + Math.pow(this.getStart_long(), o.getStart_long());
//
//		r = Math.sqrt(r);

		// 2. 중심 위좌표 구하기
		double[] mid = new double[3];
		mid[0] = this.getStart_long() + (this.getStart_long() - o.getStart_long()) / 2;
		mid[1] = this.getStart_lat() + (this.getStart_lat() - o.getStart_lat()) / 2;

		// 3. 중심을 기준으로 한 원의 반지름 구하기 start ~ 중심 위좌표
		double EARTH_RADIUS = 6371;
		double dLat = Math.toRadians(mid[1] - this.getStart_lat());
		double dLon = Math.toRadians(mid[0] - this.getStart_long());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(this.getStart_lat()))
				* Math.cos(Math.toRadians(mid[1])) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = EARTH_RADIUS * c * 1000; // Distance in m
		mid[2] = d;
		return mid;
	}

/////////////////////////////////////////////////////////////////////////////////////
	public double getStart_long() {
		return start_long;
	}

	public void setStart_long(double start_long) {
		this.start_long = start_long;
	}

	public double getStart_lat() {
		return start_lat;
	}

	public void setStart_lat(double start_lat) {
		this.start_lat = start_lat;
	}

	public double getMin_time() {
		return min_time;
	}

	public void setMin_time(double min_time) {
		this.min_time = min_time;
	}

	public double getMax_time() {
		return max_time;
	}

	public void setMax_time(double max_time) {
		this.max_time = max_time;
	}

	public int getStopover() {
		return stopover;
	}

	public void setStopover(int stopover) {
		this.stopover = stopover;
	}

	public String getWalk_car() {
		return walk_car;
	}

	public void setWalk_car(String walk_car) {
		this.walk_car = walk_car;
	}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double min_dist) {
		this.min_dist = min_dist;
	}

	public double getMax_dist() {
		return max_dist;
	}

	public void setMax_dist(double max_dist) {
		this.max_dist = max_dist;
	}

	@Override
	public String toString() {
		return "RequestAttraction [start_long=" + start_long + ", start_lat=" + start_lat + ", min_time=" + min_time
				+ ", max_time=" + max_time + ", stopover=" + stopover + ", walk_car=" + walk_car + ", min_dist="
				+ min_dist + ", max_dist=" + max_dist + "]";
	}

}
