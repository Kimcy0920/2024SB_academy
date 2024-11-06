package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.ScheduleDto;
import com.example.sample2.dto.ScheduleFormDto;
import com.example.sample2.repository.model.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheuleRepository { // todo ScheduleRepository로 변경

// 기본 기능
public int insertSchoeduleFormDto(Schedule schedule);
public int updateSchoeduleFormDtoBycontent(ScheduleFormDto scheduleFormDto);
public int deleteSchoeduleFormDtoByStaffIdAndId(Integer id);
public List<Schedule> selectSchodule();
public List<ScheduleDto> selectSchoduleMouth();


// 학사일정 조회 (디테일)
public ScheduleDto selectScheduleById(Integer id);

// 월별 학사일정 조회
public List<Schedule> selectListByMonth(Integer month);

}
