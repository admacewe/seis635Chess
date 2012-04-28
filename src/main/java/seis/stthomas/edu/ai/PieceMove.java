package seis.stthomas.edu.ai;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class PieceMove {

    private int startRow;

    private int startCol;

    private int destRow;

    private int destCol;

    private int score;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getDestRow() {
        return destRow;
    }

    public void setDestRow(int destRow) {
        this.destRow = destRow;
    }

    public int getDestCol() {
        return destCol;
    }

    public void setDestCol(int destCol) {
        this.destCol = destCol;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
