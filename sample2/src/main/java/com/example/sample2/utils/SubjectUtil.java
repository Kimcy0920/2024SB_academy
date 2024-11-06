package com.example.sample2.utils;


import com.example.sample2.dto.SubjectFormDto;
import com.example.sample2.repository.model.Subject;

import java.util.List;

/**
 * 
 * @author 강의 입력 관련 유틸
 *
 */
public class SubjectUtil {

	public boolean calculate(SubjectFormDto subjectFormDto, List<Subject> subjectList) {
		for (int i = 0; i < subjectList.size(); i++) {
			if ((subjectList.get(i).getStartTime() <= subjectFormDto.getStartTime()
					&& subjectFormDto.getStartTime() < subjectList.get(i).getEndTime())
					|| (subjectList.get(i).getStartTime() < subjectFormDto.getEndTime()
							&& subjectFormDto.getEndTime() <= subjectList.get(i).getEndTime())) {
				return false;
			}
		}
		return true;
	}
}
