package edu.du.sb1101.fileUploadBoard.board.controller;

import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardDto board = (BoardDto) target;

        if (board.getTitle() == null || board.getTitle().trim().isEmpty()) {
            errors.rejectValue("title", "NotEmpty", "제목을 입력해주세요.");
        }
        if (board.getContents() == null || board.getContents().trim().isEmpty()) {
            errors.rejectValue("contents", "NotEmpty", "내용을 입력해주세요.");
        }
    }
}