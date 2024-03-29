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
import seis.stthomas.edu.domain.Rook;
import seis.stthomas.edu.domain.RookDataOnDemand;

privileged aspect RookDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RookDataOnDemand: @Component;
    
    private Random RookDataOnDemand.rnd = new SecureRandom();
    
    private List<Rook> RookDataOnDemand.data;
    
    @Autowired
    private BoardDataOnDemand RookDataOnDemand.boardDataOnDemand;
    
    public Rook RookDataOnDemand.getNewTransientRook(int index) {
        Rook obj = new Rook();
        setBoard(obj, index);
        setHasMoved(obj, index);
        setIsWhite(obj, index);
        setPieceValue(obj, index);
        return obj;
    }
    
    public void RookDataOnDemand.setBoard(Rook obj, int index) {
        Board board = boardDataOnDemand.getRandomBoard();
        obj.setBoard(board);
    }
    
    public void RookDataOnDemand.setHasMoved(Rook obj, int index) {
        Boolean hasMoved = true;
        obj.setHasMoved(hasMoved);
    }
    
    public void RookDataOnDemand.setIsWhite(Rook obj, int index) {
        Boolean isWhite = true;
        obj.setIsWhite(isWhite);
    }
    
    public void RookDataOnDemand.setPieceValue(Rook obj, int index) {
        int pieceValue = index;
        obj.setPieceValue(pieceValue);
    }
    
    public Rook RookDataOnDemand.getSpecificRook(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Rook obj = data.get(index);
        Long id = obj.getId();
        return Rook.findRook(id);
    }
    
    public Rook RookDataOnDemand.getRandomRook() {
        init();
        Rook obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Rook.findRook(id);
    }
    
    public boolean RookDataOnDemand.modifyRook(Rook obj) {
        return false;
    }
    
    public void RookDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Rook.findRookEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Rook' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Rook>();
        for (int i = 0; i < 10; i++) {
            Rook obj = getNewTransientRook(i);
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
