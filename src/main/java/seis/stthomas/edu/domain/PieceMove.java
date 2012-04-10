/**
 * PieceMove - Speicifies a move giving the row/column of the starting location
 *  and the row/column of the destination.  This class exists to organize the
 *  data into a single unit, but for simplicity makes all the data public and
 *  includes no methods.
 */

package seis.stthomas.edu.domain;

public class PieceMove
{
    public int startRow;
    public int startCol;
    public int destRow;
    public int destCol;
    public int score;
}
