package edu.du.sb1101.fileUploadBoard.board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
	
	private int idx;
	
	private int boardIdx;
	
	private String originalFileName;
	
	private String storedFilePath;
	
	private long fileSize;

	private String creatorId;  // 추가된 필드
}
