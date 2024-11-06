package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.RoomFormDto;
import com.example.sample2.repository.model.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 *  강의실 repository
 */

@Mapper
public interface RoomRepository {
	public int insert(RoomFormDto roomFormDto);
	public List<Room> selectByRoomDto();
	public int deleteById(String id);
}
