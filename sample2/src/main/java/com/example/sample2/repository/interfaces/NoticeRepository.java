package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.NoticeFormDto;
import com.example.sample2.dto.NoticePageFormDto;
import com.example.sample2.repository.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 *  공지 repository
 */

@Mapper
public interface NoticeRepository {
	public int insert(NoticeFormDto noticeFormDto);
	public List<Notice> selectByNoticeDto(NoticePageFormDto noticePageFormDto);
	public Notice selectById(Integer id);
	public int updateByNoticeDto(NoticeFormDto noticeFormDto);
	public int deleteById(Integer id);
	
	// 파일
	public int insertFile(NoticeFormDto noticeFormDto);
	public int selectLimit(NoticeFormDto noticeFormDto);
	
	// 페이징
	public List<Notice> selectByNoticeDtoOrderBy();
	public Integer selectNoticeCount(NoticePageFormDto noticePageFormDto);
	
	// 검색
	public List<Notice> selectNoticeByKeyword(NoticePageFormDto noticePageFormDto);
	public List<Notice> selectNoticeByTitle(NoticePageFormDto noticePageFormDto);
	public Integer selectNoticeCountByTitle(NoticePageFormDto noticePageFormDto);
	public Integer selectNoticeCountByKeyword(NoticePageFormDto noticePageFormDto);
	
	// 조회수
	public Integer updateViews(Integer id);
	
	// 메인 페이지에 사용할 최신글 5개
	public List<NoticeFormDto> selectLimit5();
}
