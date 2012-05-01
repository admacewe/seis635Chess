package seis.stthomas.edu.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyClass;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
@Transactional
public abstract class Piece {
//
//    @ManyToOne(targetEntity=seis.stthomas.edu.domain.Board.class, 
//    		cascade = CascadeType.ALL, fetch=FetchType.EAGER)


	@ManyToOne(targetEntity=seis.stthomas.edu.domain.Board.class,fetch=FetchType.EAGER)
    @JoinColumn(name="board", insertable=false,updatable=false, referencedColumnName="id")

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
