package seis.stthomas.edu.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Bishop extends Piece implements Serializable {

    private static final long serialVersionUID = -7043646288736586788L;

    private final String moveStrategy = "setSquaresOnVector";

    public Bishop(boolean isWhite) {
        super(isWhite, 3);
    }

    @Override
    public String getMoveStrategy() {
        return moveStrategy;
    }

    @Override
    public List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> getAvailableMoves() {
        return Arrays.asList(new ImmutablePair<Integer, Integer>(-1, -1), new ImmutablePair<Integer, Integer>(-1, 1), new ImmutablePair<Integer, Integer>(1, -1), new ImmutablePair<Integer, Integer>(1, 1));
    }
}
