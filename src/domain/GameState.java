package domain;

public enum GameState
{
    idle,                // waiting for a new game to start
    choosePiece,         // waiting for player to choose a piece to move
    chooseDestination    // waiting for player to choose a destination
}
