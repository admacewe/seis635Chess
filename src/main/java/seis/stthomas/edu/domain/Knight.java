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
public class Knight extends Piece implements Serializable {

    private static final long serialVersionUID = 1887051257141793527L;

    private final String moveStrategy = "setSingleSquareUnlessColorMatch";

    public Knight(boolean isWhite) {
        super(isWhite, 3);
    }

    public List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> getAvailableMoves() {
        return Arrays.asList(new ImmutablePair<Integer, Integer>(-2, -1), new ImmutablePair<Integer, Integer>(-2, 1), new ImmutablePair<Integer, Integer>(-1, -2), new ImmutablePair<Integer, Integer>(-1, 2), new ImmutablePair<Integer, Integer>(1, -2), new ImmutablePair<Integer, Integer>(1, 2), new ImmutablePair<Integer, Integer>(2, -1), new ImmutablePair<Integer, Integer>(2, 1));
    }

    public String getMoveStrategy() {
        return moveStrategy;
    }
}
