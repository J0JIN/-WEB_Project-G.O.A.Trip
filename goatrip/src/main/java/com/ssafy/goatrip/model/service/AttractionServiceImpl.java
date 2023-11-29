package com.ssafy.goatrip.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.goatrip.model.dao.AttractionDAO;
import com.ssafy.goatrip.model.dto.AttractionDTO;
import com.ssafy.goatrip.model.dto.RequestAttraction;

@Service
public class AttractionServiceImpl implements AttractionService {
	@Autowired
	public AttractionDAO adao;

	// 도착지점 1개 따로
	@Override
	public AttractionDTO getEndAttr(RequestAttraction radto) throws SQLException {
		Random random = new Random();
		List<AttractionDTO> attr_list = new ArrayList<AttractionDTO>();
		attr_list = adao.getEndAttr(radto);
		System.out.println("범위 안의 여행지 개수 : " + attr_list.size());
		if (attr_list.size() > 0) {
			AttractionDTO result = attr_list.get(random.nextInt(attr_list.size()));
			return result;
		} else {
			return null;
		}
	}

//	@Override
//	public List<AttractionDTO> getEndAttr(RequestAttraction radto) throws SQLException {
//		Random random = new Random();
//		List<AttractionDTO> attr_list = new ArrayList<AttractionDTO>();
//		attr_list = adao.getEndAttr(radto);
//		System.out.println("범위 안의 여행지 개수 : " + attr_list.size());
//		List<AttractionDTO> result = new ArrayList<AttractionDTO>();
//		//도착지점 
//		if (attr_list.size() > 0) {
//			while(!attr_list.isEmpty()) {
//				result.add(attr_list.get(random.nextInt(attr_list.size())));
//				if(result.size()>radto.getStopover()) {
//					break;
//				}
//			}
//			
//			System.out.println(attr_list.size());
//			return result;
//		} else {
//			return null;
//		}
//	}
//	private static boolean exists(LinkedList<Integer> randnums, int index) {
//		for (int i = 0; i < .length; i++) {
//			if (n[i] == index)
//				return true;
//		}
//		return false;
//	}

	// 시작 지점과 도착지점 정보 받아서 그 사이에 있는 장소 하나 뽑아내기 => stopever 개수만큼 리스트 만들어서 리턴
	public List<AttractionDTO> getMidAttr(AttractionDTO startdto, AttractionDTO enddto, int stopover)
			throws SQLException {
		// 중심 위좌표 얻기
		double[] mid = startdto.getC(enddto);
		System.out.println("중심 좌표 구한 뒤 반지름 거리 구하기" + Arrays.toString(mid));
		// 중심 위좌표, 반지름 건네주기
		System.out.println(Arrays.toString(mid));
		List<AttractionDTO> attr_list = adao.getMidAttr(mid[0], mid[1], mid[2]);
		System.out.println("범위 안의 여행지 개수 : " + attr_list.size());

		if (attr_list.size() > 0) {
			Random random = new Random();
			long newSeed = System.currentTimeMillis();
			random.setSeed(newSeed);

			// set 에 넣어서 set의 값이 stopover 개수와 같다면? 탈출하는 while문
			List<Integer> randnums = new LinkedList<Integer>();

			List<AttractionDTO> result = new LinkedList<AttractionDTO>();
			if (attr_list.size() >= stopover) {
				//중복되지 않게 난수 넣기
				int index =0;
				for(int i=0;i<stopover;i++) {
					do {
						index = (int)random.nextInt(attr_list.size());
					}while(randnums.contains(index));
					randnums.add(index);
				}
				
				System.out.println(randnums);
				for (int i = 0; i < randnums.size(); i++) {
					result.add(attr_list.get(randnums.get(i)));
				}
				
				System.out.println(result);
				return result;
			} else {
				// 만약 스톱오버보다 적은 결과가 나온 경우 이미 0개보다 많다고 했으니 1~stopover-1 짜리 list가 결과로 반환된다.
				return attr_list;
			}
//			AttractionDTO result = attr_list.get(random.nextInt(attr_list.size()));

		} else {
			return null;
		}
	}

}
