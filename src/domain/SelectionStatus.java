/**
 * SelectionStatus - This enumeration indicates a status to the UI.
 *  Each time the user selects a square on the UI, a SelectionStatus is
 *  returned which allows the UI to appropriately update the display.
 */

package domain;

public enum SelectionStatus
{
    // These apply to piece selection
    pieceNull,         // there is no piece on the square selected
    pieceWrongColor,   // the piece selected has the wrong color
    pieceValid,        // selection is valid
    
    // These apply to destination selection
    destSelfInCheck,   // move would place self in check
    destOutOfRange,    // destination is outside the selected piece's range
    destValidNoCheck,  // destination is valid; does not place opponent in check
    destValidCheck,    // destination is valid; opponent is in check
    destValidCheckMate, // destination is valid; opponent is in checkmate
    destValidDraw,     // destination is valid; game is a draw
    
    // Return an error if a piece or destination was not being selected
    invalidState       // game was not expecting a square to be pressed
}
