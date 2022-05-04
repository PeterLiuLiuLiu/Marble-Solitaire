package marblesolitaire.model;

/**
 * This enum class contains 3 possible states of the game, out of bounds, marble, empty slot
 * It also contains a toString method for each state for printing the board
 */
public enum SlotState {
    OfB(" "), Marble("O"), Empty("_");

    private final String dis;

    private SlotState(String dis) {
        this.dis = dis;
    }

    @Override
    public String toString(){
        return this.dis;
    }
}
