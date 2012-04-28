package seis.stthomas.edu.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import seis.stthomas.edu.ai.CPUPlayer;
import seis.stthomas.edu.ai.PieceMove;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Game implements Serializable {

    private static final long serialVersionUID = -2907694147398436613L;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Board board = new Board();

    public GameState state = GameState.choosePiece;

    @Value("true")
    private boolean activePlayerIsWhite;
    
	boolean isOpponentCPU; // indicates if opponent is a CPU player or not

	@Transient
	PieceMove cpuMove; // most recent CPU move
	@Transient
	CPUPlayer cpuPlayer; // reference to a CPU player in case a game is played
	@Transient
    private int pieceRow;
	@Transient
    private int pieceCol;

    String name;

    private static final Logger LOG = Logger.getLogger(Game.class.getName());

    public Game() {
        this.setActivePlayerIsWhite(true);
        LOG.info("this.isActivePlayerIsWhite() :: " + this.isActivePlayerIsWhite());
        LOG.info("Leaving the game constructor!");
    }

    public Game(boolean playVsCPU, int difficulty) {
        this.setActivePlayerIsWhite(true);
        this.cpuPlayer = new CPUPlayer();
        this.cpuPlayer.setDifficulty(difficulty);
        this.setIsOpponentCPU(true);
        LOG.info("this.isActivePlayerIsWhite() :: " + this.isActivePlayerIsWhite());
        LOG.info("Leaving the game constructor!");
        
	}

	public SelectionStatus squareSelected(int row, int col) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectionStatus status;
        if (this.state == GameState.choosePiece) {
            status = pieceSelected(row, col);
        } else if (this.state == GameState.chooseDestination) {
            status = destinationSelected(row, col);
        } else {
            status = SelectionStatus.invalidState;
            LOG.warn("Returning an invalid state for some reason!");
        }
        return status;
    }

    private SelectionStatus pieceSelected(int row, int col) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Piece piece;
        SelectionStatus status;
        piece = this.board.getPiece(row, col);
        LOG.info("Row selected was : " + row);
        LOG.info("Column selected was : " + col);
        if (piece == null) {
            LOG.warn("The piece selected is null!");
            status = SelectionStatus.pieceNull;
        } else if (piece.isIsWhite() != this.activePlayerIsWhite) {
            status = SelectionStatus.pieceWrongColor;
        } else {
            status = SelectionStatus.pieceValid;
            this.pieceRow = row;
            this.pieceCol = col;
            this.board.clearSquaresInRange();
            this.board.updateSquaresInRange(row, col, piece);
            this.state = GameState.chooseDestination;
        }
        return status;
    }

    private SelectionStatus destinationSelected(int destRow, int destCol) {
        SelectionStatus status = null;
        boolean opponent;
        this.state = GameState.choosePiece;
        if (this.board.getSquareInRange(destRow, destCol) == false) {
            status = SelectionStatus.destOutOfRange;
        } else try {
            if (this.board.tryMove(this.pieceRow, this.pieceCol, destRow, destCol, true, true) == false) {
                status = SelectionStatus.destSelfInCheck;
            } else {
                opponent = (this.activePlayerIsWhite == true) ? false : true;
                if (this.board.playerHasValidMove(opponent)) {
                    if (this.board.playerInCheck(opponent)) {
                        status = SelectionStatus.destValidCheck;
                    } else {
                        status = SelectionStatus.destValidNoCheck;
                    }
                    this.activePlayerIsWhite = opponent;
					// If it is the CPU player's turn
					if ((this.activePlayerIsWhite == false) && isOpponentCPU) {
						state = GameState.cpuTurn;
					}
                } else {
                    this.state = GameState.idle;
                    if (this.board.playerInCheck(opponent)) {
                        status = SelectionStatus.destValidCheckMate;
                    } else {
                        status = SelectionStatus.destValidDraw;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return status;
    }

    public SelectionStatus determineCPUMove() throws InterruptedException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        SelectionStatus status;
        
		cpuMove = cpuPlayer.selectMove(board);

		
        if (board.playerHasValidMove(true)) {
            if (board.playerInCheck(true)) {
                status = SelectionStatus.destValidCheck;
            } else {
                status = SelectionStatus.destValidNoCheck;
            }
            activePlayerIsWhite = true;
            state = GameState.choosePiece;
        } else {
            state = GameState.idle;
            if (board.playerInCheck(true)) {
                status = SelectionStatus.destValidCheckMate;
            } else {
                status = SelectionStatus.destValidDraw;
            }
        }
        return status;
    }

    public Piece getPiece(int row, int col) {
        return this.board.getPiece(row, col);
    }

    public boolean getSquareInRange(int row, int col) {
        return this.board.getSquareInRange(row, col);
    }
}
