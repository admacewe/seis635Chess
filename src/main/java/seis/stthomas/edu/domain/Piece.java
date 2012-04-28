package seis.stthomas.edu.domain;

import java.util.List;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public abstract class Piece {

    @ManyToOne
    private Board board;

    private boolean isWhite;

    private boolean hasMoved;

    private int pieceValue;

    public Piece(boolean isWhite, int value) {
        this.isWhite = isWhite;
        this.hasMoved = false;
        this.pieceValue = value;
    }

    public abstract String getMoveStrategy();

    public abstract List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> getAvailableMoves();
}
