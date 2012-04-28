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
import seis.stthomas.edu.domain.Game;
import seis.stthomas.edu.domain.GameDataOnDemand;
import seis.stthomas.edu.domain.GameState;
import seis.stthomas.edu.service.GameService;

privileged aspect GameDataOnDemand_Roo_DataOnDemand {
    
    declare @type: GameDataOnDemand: @Component;
    
    private Random GameDataOnDemand.rnd = new SecureRandom();
    
    private List<Game> GameDataOnDemand.data;
    
    @Autowired
    GameService GameDataOnDemand.gameService;
    
    public Game GameDataOnDemand.getNewTransientGame(int index) {
        Game obj = new Game();
        setActivePlayerIsWhite(obj, index);
        setBoard(obj, index);
        setIsOpponentCPU(obj, index);
        setName(obj, index);
        setState(obj, index);
        return obj;
    }
    
    public void GameDataOnDemand.setActivePlayerIsWhite(Game obj, int index) {
        Boolean activePlayerIsWhite = true;
        obj.setActivePlayerIsWhite(activePlayerIsWhite);
    }
    
    public void GameDataOnDemand.setBoard(Game obj, int index) {
        Board board = null;
        obj.setBoard(board);
    }
    
    public void GameDataOnDemand.setIsOpponentCPU(Game obj, int index) {
        Boolean isOpponentCPU = true;
        obj.setIsOpponentCPU(isOpponentCPU);
    }
    
    public void GameDataOnDemand.setName(Game obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public void GameDataOnDemand.setState(Game obj, int index) {
        GameState state = null;
        obj.setState(state);
    }
    
    public Game GameDataOnDemand.getSpecificGame(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Game obj = data.get(index);
        Long id = obj.getId();
        return gameService.findGame(id);
    }
    
    public Game GameDataOnDemand.getRandomGame() {
        init();
        Game obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return gameService.findGame(id);
    }
    
    public boolean GameDataOnDemand.modifyGame(Game obj) {
        return false;
    }
    
    public void GameDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = gameService.findGameEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Game' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Game>();
        for (int i = 0; i < 10; i++) {
            Game obj = getNewTransientGame(i);
            try {
                gameService.saveGame(obj);
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
