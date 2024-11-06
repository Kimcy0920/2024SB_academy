package edu.du.kcy1101.board.validator;

import edu.du.kcy1101.board.entity.Board;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class BoardValidator implements Validator {

    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public BoardValidator() {
        pattern = Pattern.compile(emailRegExp);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Board board = (Board) target;

        if (board.getTitle() == null || board.getTitle().isBlank()) {
            errors.rejectValue("title", "NotBlank.boardCommand.title", "제목은 필수 입력입니다.");
        }

        if (board.getContents() == null || board.getContents().isEmpty()) {
            errors.rejectValue("contents", "NotEmpty.boardCommand.contents", "내용을 입력해주세요.");
        }
    }
}
