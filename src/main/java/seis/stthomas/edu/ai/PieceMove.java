/**
 * PieceMove - Speicifies a move giving the row/column of the starting location
 *  and the row/column of the destination.  This class exists to organize the
 *  data into a single unit, but for simplicity makes all the data public and
 *  includes no methods.
 */

package seis.stthomas.edu.ai;

public class PieceMove
{
    private int startRow;
    private int startCol;
    private int destRow;
    private int destCol;
    private int score;
	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}
	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/**
	 * @return the startCol
	 */
	public int getStartCol() {
		return startCol;
	}
	/**
	 * @param startCol the startCol to set
	 */
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	/**
	 * @return the destRow
	 */
	public int getDestRow() {
		return destRow;
	}
	/**
	 * @param destRow the destRow to set
	 */
	public void setDestRow(int destRow) {
		this.destRow = destRow;
	}
	/**
	 * @return the destCol
	 */
	public int getDestCol() {
		return destCol;
	}
	/**
	 * @param destCol the destCol to set
	 */
	public void setDestCol(int destCol) {
		this.destCol = destCol;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
    
    
    
    
    
    
    
}
