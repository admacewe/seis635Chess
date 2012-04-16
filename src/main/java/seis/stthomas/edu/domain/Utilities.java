/**
 * Utilities - This class provides common utilities to be used by all
 *  classes in the domain.
 */

package seis.stthomas.edu.domain;

import org.apache.log4j.Logger;

public class Utilities
{
	
	private static final Logger LOG = Logger.getLogger(Utilities.class
			.getName());
	
    /**
     * validSquare - This method checks if the row and column numbers
     *  passed are between 0 and 7 (inclusive).  This indicates whether
     *  or not the coordinates represent a valid square on the board.
     */
    public static boolean validSquare(int row, int col)
    {
        boolean valid = false;

        if((row >= 0) && (row <= 7) &&
           (col >= 0) && (col <= 7))
        {
            valid = true;
        }
        if (!valid){
        	LOG.debug("about to return an invlaid square!~");
        }
    	LOG.debug("returning :: " + valid);

        return valid;
    }
}
