package edu.du.sb1101.fileUploadBoard.board.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BoardDto {
	
	private int boardIdx;

	@Size(max = 50, message = "제목은 50자 이내로 작성해주세요.")
	private String title;

	@Size(max = 1000, message = "내용은 1000자 이내로 작성해주세요.")
	private String contents;
	
	private int hitCnt;
	
	private String creatorId;
	
	private String createdDatetime;
	
	private String updaterId;
	
	private String updatedDatetime;
	
	private List<BoardFileDto> fileList;
}
