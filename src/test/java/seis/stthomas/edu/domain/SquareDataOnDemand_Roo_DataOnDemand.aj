// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seis.stthomas.edu.domain.Board;
import seis.stthomas.edu.domain.BoardDataOnDemand;
import seis.stthomas.edu.domain.Square;
import seis.stthomas.edu.domain.SquareDataOnDemand;

privileged aspect SquareDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SquareDataOnDemand: @Component;
    
    private Random SquareDataOnDemand.rnd = new SecureRandom();
    
    private List<Square> SquareDataOnDemand.data;
    
    @Autowired
    private BoardDataOnDemand SquareDataOnDemand.boardDataOnDemand;
    
    public Square SquareDataOnDemand.getNewTransientSquare(int index) {
        Square obj = new Square();
        setBoard(obj, index);
        setInRange(obj, index);
        return obj;
    }
    
    public void SquareDataOnDemand.setBoard(Square obj, int index) {
        Board board = boardDataOnDemand.getRandomBoard();
        obj.setBoard(board);
    }
    
    public void SquareDataOnDemand.setInRange(Square obj, int index) {
        Boolean inRange = true;
        obj.setInRange(inRange);
    }
    
    public Square SquareDataOnDemand.getSpecificSquare(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Square obj = data.get(index);
        Long id = obj.getId();
        return Square.findSquare(id);
    }
    
    public Square SquareDataOnDemand.getRandomSquare() {
        init();
        Square obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Square.findSquare(id);
    }
    
    public boolean SquareDataOnDemand.modifySquare(Square obj) {
        return false;
    }
    
    public void SquareDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Square.findSquareEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Square' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Square>();
        for (int i = 0; i < 10; i++) {
            Square obj = getNewTransientSquare(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
