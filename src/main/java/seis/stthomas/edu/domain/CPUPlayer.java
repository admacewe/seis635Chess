package seis.stthomas.edu.domain;

import java.util.ArrayList;
import java.util.Random;

public class CPUPlayer
{
    private Random rand;
    private int difficulty;
    private final int INVALID_SCORE = -1000000;
    private final int MAX_DIFFICULTY = 10;
    
    public CPUPlayer()
    {
        rand = new Random();
    }
    
    public void setDifficulty(int difficultySetting)
    {
        if(difficultySetting > MAX_DIFFICULTY)
        {
            difficulty = MAX_DIFFICULTY;
        }
        else
        {
            difficulty = difficultySetting;
        }
    }
    
    public PieceMove selectMove(Board board)
    {
        PieceMove move = pickBestMove(board, ChessColor.black, 0, difficulty-1);
        
        if(move != null)
        {
            board.tryMove(move.startRow, move.startCol, move.destRow, move.destCol, true, true);
        }
        
        System.out.printf("move from %d, %d to %d, %d: score = %d\n",
                move.startRow, move.startCol, move.destRow, move.destCol, move.score);
        
        return move;
    }
    
    /**
     * Select the best move for a player based on the current score and
     * moves remaining.
     */
    private PieceMove pickBestMove(Board board, 
         ChessColor playerColor, int currentScore, int movesRemaining)
    {
        int startRow;
        int startCol;
        int destRow;
        int destCol;
        Piece piece;
        ArrayList<PieceMove> moveCandidates = new ArrayList<PieceMove>();
        PieceMove move;
        int score;
        int bestScore = INVALID_SCORE;
        boolean copySquaresInRange[][] = new boolean[8][8];
        
        // loop over all the squares on the board to find available pieces to move
        for(startRow = 0; startRow < 8; startRow++)
        {
            for(startCol = 0; startCol < 8; startCol++)
            {
                // if there is a piece of the player's color, check where it can move
                piece = board.getPiece(startRow, startCol);
                if((piece != null) && (piece.getColor() == playerColor))
                {
                    board.clearSquaresInRange();
                    piece.updateSquaresInRange(startRow, startCol);
                    
                    // create a copy of the squaresInRange array
                    for(destRow = 0; destRow < 8; destRow++)
                    {
                        for(destCol = 0; destCol < 8; destCol++)
                        {
                            copySquaresInRange[destRow][destCol] = 
                                board.getSquareInRange(destRow, destCol);
                        }
                    }
                    
                    // for each square in range, get the score of that move
                    for(destRow = 0; destRow < 8; destRow++)
                    {
                        for(destCol = 0; destCol < 8; destCol++)
                        {
                            if(copySquaresInRange[destRow][destCol])
                            {                                
                                score = getMoveScore(board, startRow, startCol, destRow, destCol,
                                        playerColor, movesRemaining, currentScore);
                                //System.out.printf("pickBestMove: trying a move from %d, %d to %d, %d: score=%d\n",
                                //        startRow, startCol, destRow, destCol, score);
                                if(score >= bestScore)
                                {
                                    // if this is the highest score, update the high score
                                    // and create a new list of move candidates
                                    if(score > bestScore)
                                    {
                                        bestScore = score;
                                        moveCandidates = new ArrayList<PieceMove>();
                                    }

                                    // add this move to the candidate list
                                    move = new PieceMove();
                                    move.startRow = startRow;
                                    move.startCol = startCol;
                                    move.destCol = destCol;
                                    move.destRow = destRow;
                                    move.score = score;
                                    moveCandidates.add(move);
                                }                                
                            }
                        }
                    }
                }
            }
        }
        
        // if there is a valid move, return a random selection from the candidates
        if(moveCandidates.size() > 0)
        {
            move = moveCandidates.get(rand.nextInt(moveCandidates.size()));
        }
        
        // otherwise, return null to indicate no valid moves
        else
        {
            move = null;
        }

        return move;
    }
    
    /**
     * getMoveScore - This method attempts a move, and asks the opponent to try all possible
     *  moves and return the best possible score assuming the given move is made.
     */
    private int getMoveScore(Board board, int startRow, int startCol, int destRow, int destCol,
            ChessColor playerColor, int movesRemaining, int currentScore)
    {
        int score;     
        Piece startPiece;
        Piece destPiece;
        ChessColor opponentColor = (playerColor == ChessColor.white) ?
                ChessColor.black : ChessColor.white;
        PieceMove move;
                        
        // set references to the pieces at the start and destination squares, so these
        // can be restored later
        startPiece = board.getPiece(startRow, startCol);
        destPiece = board.getPiece(destRow, destCol);
        
        // get the value of the piece that is captured, if any
        if(destPiece != null)
        {
            currentScore += destPiece.getValue();
        }
        
        // update the board with the new move
        if(board.tryMove(startRow, startCol, destRow, destCol, true, false))
        {
            // if the move was valid, check if there are more moves to evaluate
            if(movesRemaining <= 0)
            {
                score = currentScore;
            }
            else
            {
                // Request the opponent to pick their best move.  The score is inverted when
                // passed to the opponent, and when returned, because a high score indicates
                // a favorable outcome for the active player.
                move = pickBestMove(board, opponentColor, -1*currentScore, movesRemaining-1);
                score = -1 * move.score;
            }
            
            // After finding the best score, restore the state of the board
            board.setPiece(startRow, startCol, startPiece);
            board.setPiece(destRow, destCol, destPiece);
        }
        else
        {
            // move placed self in check - return the invalid move score
            score = INVALID_SCORE;
            
            // no need to undo the move here, because tryMove already undid it
        }
        
        return score;
    }
}
