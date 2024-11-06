package edu.du.sb1011_th.service;

import edu.du.sb1011_th.entity.Board;

import java.util.List;

public interface BoardService {

    List<Board> selectBoardList() throws Exception;

    void insertBoard(Board board) throws Exception;

    Board selectBoardDetail(int boardIdx) throws Exception;

    void updateBoard(Board board) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    void updateHitCount(int boardIdx) throws Exception;
}