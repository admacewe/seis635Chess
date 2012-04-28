package seis.stthomas.edu.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import seis.stthomas.edu.utility.Utilities;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooSerializable
@RooJson
public class Board implements Serializable {

    private static final long serialVersionUID = -4695614365620956689L;

    @OneToOne
    private Game game;

  
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Map<Integer, Piece> pieces = new HashMap<Integer, Piece>(64);

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Map<Integer, Square> squareInRange = new HashMap<Integer, Square>(64);

    private static final Logger LOG = Logger.getLogger(Board.class.getName());

    private static final Class[] parameterTypes = { Integer.TYPE, Integer.TYPE, Piece.class, List.class };

    public Board() {
        initPieces();
        initSquaresInRange();
    }

    public boolean tryMove(int curRow, int curCol, int destRow, int destCol, boolean updateState, boolean updateHasMoved) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean valid = true;
        Piece tempSourceRef = pieces.get(curRow * 8 + curCol);
        Piece tempDestRef = pieces.get(destRow * 8 + destCol);
        pieces.put(destRow * 8 + destCol, pieces.get(curRow * 8 + curCol));
        pieces.put(curRow * 8 + curCol, null);
        if ((pieces.get(destRow * 8 + destCol).getClass() == Pawn.class) && ((destRow == 0) || (destRow == 7))) {
            pieces.put(destRow * 8 + destCol, new Queen((pieces.get(destRow * 8 + destCol)).isIsWhite()));
        }
        if (playerInCheck(tempSourceRef.isIsWhite())) {
            valid = false;
        }
        if ((!valid) || (!updateState)) {
            pieces.put(destRow * 8 + destCol, tempDestRef);
            pieces.put(curRow * 8 + curCol, tempSourceRef);
        }
        if (valid && updateState && updateHasMoved) {
            pieces.get(destRow * 8 + destCol).setHasMoved(true);
        }
        return valid;
    }

    public boolean playerInCheck(boolean activePlayerIsWhite) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int kingRow = -1;
        int kingCol = -1;
        int row;
        int col;
        Piece piece;
        boolean check = false;
        for (row = 0; (row < 8) && (kingRow < 0); row++) {
            for (col = 0; (col < 8) && (kingRow < 0); col++) {
                piece = getPiece(row, col);
                if (piece != null) {
                    if ((piece.getClass() == King.class) && (piece.isIsWhite() == activePlayerIsWhite)) {
                        kingRow = row;
                        kingCol = col;
                    }
                }
            }
        }
        for (row = 0; (row < 8) && (!check); row++) {
            for (col = 0; (col < 8) && (!check); col++) {
                piece = getPiece(row, col);
                if (piece != null) {
                    if (piece.isIsWhite() != activePlayerIsWhite) {
                        clearSquaresInRange();
                        this.updateSquaresInRange(row, col, piece);
                        if (getSquareInRange(kingRow, kingCol)) {
                            check = true;
                        }
                    }
                }
            }
        }
        return check;
    }

    public boolean playerHasValidMove(boolean activePlayerIsWhite) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int row;
        int col;
        Piece piece;
        boolean validMove = false;
        for (row = 0; (row < 8) && (!validMove); row++) {
            for (col = 0; (col < 8) && (!validMove); col++) {
                piece = getPiece(row, col);
                if (piece != null) {
                    if (piece.isIsWhite() == activePlayerIsWhite) {
                        validMove = pieceHasValidMove(piece, row, col);
                    }
                }
            }
        }
        return validMove;
    }

    public void clearSquaresInRange() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squareInRange.get(row * 8 + col).setInRange(false);
            }
        }
    }

	public void setSquareInRange(int row, int col) {
        squareInRange.get(row * 8 + col).setInRange(Utilities.validSquare(row, col));
    }

    public boolean getSquareInRange(int row, int col) {
        return squareInRange.get(row * 8 + col).isInRange();
    }

    public Piece getPiece(int row, int col) {
        Piece piece = null;
        if (Utilities.validSquare(row, col)) {
            piece = pieces.get(row * 8 + col);
        }
        return piece;
    }

    public void setPiece(int row, int col, Piece pieceSetting) {
        pieces.put(row * 8 + col, pieceSetting);
    }

    private boolean pieceHasValidMove(Piece piece, int pieceRow, int pieceCol) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean[] tryDestRange = new boolean[64];
        int row;
        int col;
        boolean validMove = false;
        clearSquaresInRange();
        this.updateSquaresInRange(pieceRow, pieceCol, piece);
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {
                tryDestRange[row * 8 + col] = squareInRange.get(row * 8 + col).isInRange();
            }
        }
        for (row = 0; (row < 8) && (!validMove); row++) {
            for (col = 0; (col < 8) && (!validMove); col++) {
                if (tryDestRange[row * 8 + col]) {
                    validMove = tryMove(pieceRow, pieceCol, row, col, false, false);
                }
            }
        }
        return validMove;
    }

    protected void setSquaresOnVector(int startRow, int startCol, Piece incomingPiece, List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> movesList) {
        Iterator<ImmutablePair<Integer, Integer>> iterator = movesList.iterator();
        ImmutablePair<Integer, Integer> move;
        while (iterator.hasNext()) {
            move = iterator.next();
            setSquaresOnVectorInternal(startRow, startCol, move.getLeft().intValue(), move.getRight().intValue(), incomingPiece);
        }
    }

    protected void setSquaresOnVectorInternal(int startRow, int startCol, int rowDelta, int colDelta, Piece incomingPiece) {
        boolean done = false;
        int row = startRow;
        int col = startCol;
        Piece piece;
        while (!done) {
            row += rowDelta;
            col += colDelta;
            if (Utilities.validSquare(row, col)) {
                piece = this.getPiece(row, col);
                if (piece == null) {
                    this.setSquareInRange(row, col);
                } else {
                    done = true;
                    if (piece.isIsWhite() != incomingPiece.isIsWhite()) {
                        this.setSquareInRange(row, col);
                    }
                }
            } else {
                done = true;
            }
        }
    }

    protected void pawnMoveStrategy(int row, int col, Piece incomingPiece, List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> movesList) {
        setSingleSquareIfColorMismatch(row, col, incomingPiece);
    }

    protected void setSingleSquareIfColorMismatchWithColor(int row, int col, boolean isWhite) {
        if (Utilities.validSquare(row, col)) {
            Piece piece = this.getPiece(row, col);
            if (piece != null) {
                if (piece.isIsWhite() != isWhite) {
                    this.setSquareInRange(row, col);
                }
            }
        }
    }

    public void setSingleSquareIfColorMismatch(int currentRow, int currentCol, Piece incomingPiece) {
        LOG.debug("current Row is : " + currentRow);
        LOG.debug("current Col is : " + currentCol);
        int dir = (incomingPiece.isIsWhite() == false) ? 1 : -1;
        LOG.debug("Is White : " + incomingPiece.isIsWhite());
        LOG.debug("direction is : " + dir);
        LOG.debug("currentRow+dir" + +(currentRow + dir));
        if (this.getPiece(currentRow + dir, currentCol) == null) {
            this.setSquareInRange(currentRow + dir, currentCol);
            LOG.debug("Can move forward one square");
            if ((!incomingPiece.isHasMoved()) && (this.getPiece(currentRow + (2 * dir), currentCol) == null)) {
                this.setSquareInRange(currentRow + (2 * dir), currentCol);
            }
        }
        setSingleSquareIfColorMismatchWithColor(currentRow + dir, currentCol - 1, incomingPiece.isIsWhite());
        setSingleSquareIfColorMismatchWithColor(currentRow + dir, currentCol + 1, incomingPiece.isIsWhite());
    }

    protected void setSingleSquareUnlessColorMatch(int startRow, int startCol, Piece incomingPiece, List<org.apache.commons.lang3.tuple.ImmutablePair<java.lang.Integer, java.lang.Integer>> movesList) {
        Iterator<ImmutablePair<Integer, Integer>> iterator = movesList.iterator();
        ImmutablePair<Integer, Integer> move;
        while (iterator.hasNext()) {
            move = iterator.next();
            setSingleSquareUnlessColorMatchInternal(startRow + move.getLeft().intValue(), startCol + move.getRight().intValue(), incomingPiece);
        }
    }

    protected void setSingleSquareUnlessColorMatchInternal(int row, int col, Piece incomingPiece) {
        Piece piece;
        if (Utilities.validSquare(row, col)) {
            piece = this.getPiece(row, col);
            if (piece == null) {
                this.setSquareInRange(row, col);
            } else if (piece.isIsWhite() != incomingPiece.isIsWhite()) {
                this.setSquareInRange(row, col);
            }
        }
    }

    public void updateSquaresInRange(int currentRow, int currentCol, Piece incomingPiece) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        List<ImmutablePair<Integer, Integer>> moveList = incomingPiece.getAvailableMoves();
        LOG.debug("method type for this peice is : " + incomingPiece.getMoveStrategy());
        this.setPrivateMethodsAccessiableForReflectionOnly(incomingPiece.getMoveStrategy());
        Method method = this.getClass().getDeclaredMethod(incomingPiece.getMoveStrategy(), parameterTypes);
        Object[] args = { currentRow, currentCol, incomingPiece, moveList };
        method.invoke(this, args);
    }

    private void setPrivateMethodsAccessiableForReflectionOnly(String methodName) throws SecurityException, NoSuchMethodException {
        Method method = Board.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
    }

    private void initPieces() {
        pieces.put(0, new Rook(false));
        pieces.put(1, new Knight(false));
        pieces.put(2, new Bishop(false));
        pieces.put(3, new Queen(false));
        pieces.put(4, new King(false));
        pieces.put(5, new Bishop(false));
        pieces.put(6, new Knight(false));
        pieces.put(7, new Rook(false));
        for (int col = 0; col < 8; col++) {
            pieces.put(1 * 8 + col, new Pawn(false));
        }
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                pieces.put(row * 8 + col, null);
            }
        }
        for (int col = 0; col < 8; col++) {
            pieces.put(6 * 8 + col, new Pawn(true));
        }
        pieces.put(7 * 8 + 0, new Rook(true));
        pieces.put(7 * 8 + 1, new Knight(true));
        pieces.put(7 * 8 + 2, new Bishop(true));
        pieces.put(7 * 8 + 3, new Queen(true));
        pieces.put(7 * 8 + 4, new King(true));
        pieces.put(7 * 8 + 5, new Bishop(true));
        pieces.put(7 * 8 + 6, new Knight(true));
        pieces.put(7 * 8 + 7, new Rook(true));
        LOG.info("Finished with initPieces");
    }

    private void initSquaresInRange() {
        for (int i = 0; i < 64; i++) {
            this.squareInRange.put(i, new Square());
        }
        LOG.info("Finished with initSquaresInRange");
    }
}
