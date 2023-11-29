package com.ssafy.goatrip.model.dto;

public class AttractionDTO {
	int content_id;
	String title;
	String addr1;
	String first_image;
	double latitude;
	double longitude;
	String overview;

	public AttractionDTO(int content_id, String title, String addr1, String first_image, double latitude,
			double longitude, String overview) {
		this.content_id = content_id;
		this.title = title;
		this.addr1 = addr1;
		this.first_image = first_image;
		this.latitude = latitude;
		this.longitude = longitude;
		this.overview = overview;
	}

	public AttractionDTO(String title, double latitude, double longitude) {
		this.title = title;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public AttractionDTO() {
	}

/////////////////////////////////////////////////////////////////////////////////////// 현재 점과 다른 점이 주어졌을 때 타원의 초점 구하는 함수
	public double[] getC(AttractionDTO o) {
		// 1. 한변의 길이 구하기

		// 2. 중심 위좌표 구하기
		double[] mid = new double[3];
		mid[0] = this.getLongitude() + (o.getLongitude() - this.getLongitude()) / 2;
		mid[1] = this.getLatitude() + (o.getLatitude() - this.getLatitude()) / 2;

		// 3. 중심을 기준으로 한 원의 반지름 구하기 start ~ 중심 위좌표
		double EARTH_RADIUS = 6371000;
		double dLat = Math.toRadians(mid[1] - this.getLatitude());
		double dLon = Math.toRadians(mid[0] - this.getLongitude());

//		double theta = dLon - this.getLongitude();
//		double dist = Math.sin(deg2rad(dLat) * Math.sin(this.getLatitude())
//				+ Math.cos(deg2rad(dLat)) * Math.cos(deg2rad(this.getLatitude())) * Math.cos(deg2rad(theta)));
//		dist = Math.acos(dist);
//		dist = rad2deg(dist);
//		dist = 60 * dist * 1.1515 * 1609.344; // Distance in m

		
		// 위도 및 경도를 라디안으로 변환
        double lat1Rad = Math.toRadians(mid[1]);
        double lon1Rad = Math.toRadians(mid[0]);
        double lat2Rad = Math.toRadians(this.getLatitude());
        double lon2Rad = Math.toRadians(this.getLongitude());

        // 두 지점 간의 위도 및 경도 차이 계산
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
		
		
		mid[2] = distance;
		return mid;
	}

	double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

/////////////////////////////////////////////////////////////////////////////////////
	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getFirst_image() {
		return first_image;
	}

	public void setFirst_image(String first_image) {
		this.first_image = first_image;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "AttractionDTO [content_id=" + content_id + ", title=" + title + ", addr1=" + addr1 + ", first_image="
				+ first_image + ", latitude=" + latitude + ", longitude=" + longitude + ", overview=" + overview + "]";
	}

}
