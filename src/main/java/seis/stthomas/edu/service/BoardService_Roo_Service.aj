// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.service;

import java.util.List;
import seis.stthomas.edu.domain.Board;
import seis.stthomas.edu.service.BoardService;

privileged aspect BoardService_Roo_Service {
    
    public abstract long BoardService.countAllBoards();    
    public abstract void BoardService.deleteBoard(Board board);    
    public abstract Board BoardService.findBoard(Long id);    
    public abstract List<Board> BoardService.findAllBoards();    
    public abstract List<Board> BoardService.findBoardEntries(int firstResult, int maxResults);    
    public abstract void BoardService.saveBoard(Board board);    
    public abstract Board BoardService.updateBoard(Board board);    
}
