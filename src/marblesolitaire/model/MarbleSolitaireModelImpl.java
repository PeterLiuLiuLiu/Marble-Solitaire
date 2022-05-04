package marblesolitaire.model;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The model for marble solitaire, implements the interface MarbleSolitaireModel which gives
 * basic operations for the game to function, move, isGameOver, getGameState and getScore
 * This class only implements the square marble solitaire model with arms
 */
public class MarbleSolitaireModelImpl implements MarbleSolitaireModel{

    private SlotState[][] board;
    private final int arm;
    private int score = 0;

    /**
     * First constructor of the class, take no parameters,
     * and initialize the game board as shown above (arm thickness 3 with the empty slot at the center)
     */
    public MarbleSolitaireModelImpl(){
        this.arm = 3;
        this.initializeBoard(this.arm, this.arm);
    }

    /**
     * Second constructor of the class, take (sRow, sCol) a parameter which
     * initialize the game board as shown above (arm thickness 3 with the designated empty slot)
     * @param sRow empty spot row coordinate
     * @param sCol empty spot col coordinate
     * @throws IllegalArgumentException if empty cell position inputted is invalid
     */
    public MarbleSolitaireModelImpl(int sRow, int sCol) throws IllegalArgumentException{
        this.arm = 3;
        if(!this.initializeBoard(sRow, sCol)){
            throw new IllegalArgumentException(String.format("Invalid empty cell position (%d,%d)", sRow, sCol));
        };
    }

    /**
     * Third constructor of the class, take arm as parameter which
     * initialize the game board as shown above (arm thickness arm with the empty slot at the center)
     * @param arm arm thickness of the board
     * @throws IllegalArgumentException if arm thickness inputted is invalid
     */
    public MarbleSolitaireModelImpl(int arm) throws IllegalArgumentException{
        if(arm <= 1 || arm % 2 == 0) {
            throw new IllegalArgumentException(String.format("Invalid arm value %d, must be positive odd number larger than 1", arm));
        }
        this.arm = arm;
        this.initializeBoard(this.arm, this.arm);
    }

    /**
     * Fourth constructor of the class, take arm, (sRow, sCol) parameters which
     * initialize the game board as shown above (arm thickness arm with the designated empty slot)
     * @param arm arm thickness of the board
     * @param sRow empty spot row coordinate
     * @param sCol empty spot col coordinate
     * @throws IllegalArgumentException if arm thickness and/or (sRow, sCol) inputted is invalid
     */
    public MarbleSolitaireModelImpl(int arm, int sRow, int sCol) throws IllegalArgumentException{
        if(arm <= 1 || arm % 2 == 0) {
            throw new IllegalArgumentException(String.format("Invalid arm value %d, must be positive odd number larger than 1", arm));
        }
        this.arm = arm;
        if(!this.initializeBoard(sRow, sCol)){
            throw new IllegalArgumentException(String.format("Invalid empty cell position (%d,%d)", sRow, sCol));
        };
    }

    /**
     * This method initialize the 2D array the field board, populate it
     * called after checking the parameters are valid from constructors
     * @param sRow the empty row for starting position
     * @param sCol the empty col for starting position
     * @return true if the sRow, sCol is correct and board can be initialized, false if otherwise
     */
    private boolean initializeBoard(int sRow, int sCol){
        int dim = this.arm * 2 + 1;
        if(sRow < 0 || sRow >= dim ||
                sCol < 0 || sCol >= dim){
            return false;
        }
        this.board = new SlotState[dim][dim];
        for(int r = 0; r < dim; r++){
            for(int c = 0; c < dim; c++){
                // middle vertical strip
                if(c >= (this.arm+1)/2 && c < (this.arm+1)/2 + this.arm){
                    this.board[r][c] = SlotState.Marble;
                    this.score++;
                }
                // middle horizontal strip
                else if(r >= (this.arm+1)/2 && r < (this.arm+1)/2 + this.arm) {
                    this.board[r][c] = SlotState.Marble;
                    this.score++;
                }
                // out of bounds
                else this.board[r][c] = SlotState.OfB;
            }
        }
        // Check if the empty slot is at the marble
        if(this.board[sRow][sCol] != SlotState.Marble){
            return false;
        }
        this.board[sRow][sCol] = SlotState.Empty;
        this.score--;
        return true;
    }

    @Override
    public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
        if(!this.isValidMove(fromRow, fromCol, toRow, toCol)){
            throw new IllegalArgumentException(String.format("Invalid move from (%d,%d) to (%d,%d)", fromRow, fromCol, toRow, toCol));
        }
        // if successful, make change on board
        int middleRow = (fromRow + toRow) / 2;
        int middleCol = (fromCol + toCol) / 2;
        this.board[middleRow][middleCol] = SlotState.Empty;
        this.board[fromRow][fromCol] = SlotState.Empty;
        this.board[toRow][toCol] = SlotState.Marble;
        this.score--;
    }

    @Override
    public boolean isGameOver() {
        for(int r = 0; r < this.board.length; r++){
            for(int c = 0; c < this.board[0].length; c++){
                if(this.hasValidMove(r, c)) return false;
            }
        }
        return true;
    }

    @Override
    public String getGameState() {
        return Arrays.stream(this.board).map(
                row -> Arrays.stream(row).map(
                        e -> e.toString()).collect(Collectors.joining(" "))
                ).collect(Collectors.joining("\n"));
    }

    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * This method returns a copy of the board, changes made on this board
     * would not be reflected on this.board
     * @return a copy of the board
     */
    public SlotState[][] getBoard(){
        return Arrays.stream(this.board).map(SlotState[]::clone).toArray(SlotState[][]::new);
    }


    /**
     * This method checks if the given slot (row, col) has any valid moves
     * @param row row
     * @param col column
     * @return if the given slot (row, col) has any valid move
     */
    private boolean hasValidMove(int row, int col){
        //  out of bounds for the whole board even outside the OfB slots
        if(row < 0 || row >= this.board.length || col < 0 || col >=  this.board[0].length) return false;

        // out of bounds for the OfB slots
        if(this.board[row][col] != SlotState.Marble) return false;

        // check 4 directions to see if there is at least one valid move
        if(this.isValidMove(row, col, row, col - 2) ||
                this.isValidMove(row, col, row, col + 2) ||
                this.isValidMove(row, col, row - 2, col) ||
                this.isValidMove(row, col, row + 2, col)
        ) return true;

        return false;
    }


    /**
     * This method tests if the move from (fromRow, fromCol) to (toRow, toCol) is valid or not
     * @param fromRow source row location
     * @param fromCol source column location
     * @param toRow destination row location
     * @param toCol destination column location
     * @return if the move from (fromRow, fromCol) to (toRow, toCol) is valid or not
     */
    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol){
        // out of bounds for the whole board even outside the OfB slots
        if(fromRow < 0 || fromRow >=  this.board.length || fromCol < 0 || fromCol >=  this.board[0].length) return false;
        if(toRow < 0 || toRow >=  this.board.length || toCol < 0 || toCol >= this.board[0].length) return false;

        // out of bounds for the OfB slots
        if(this.board[fromRow][fromCol] != SlotState.Marble) return false;
        if(this.board[toRow][toCol] != SlotState.Empty) return false;

        // from and to must have 2 nos. of spacing, no diagonal allowed
        if(!((Math.abs(fromRow - toRow) == 2 && (fromCol == toCol)) || (Math.abs(fromCol - toCol) == 2 && (fromRow == toRow)))) return false;

        int middleRow = (fromRow + toRow) / 2;
        int middleCol = (fromCol + toCol) / 2;
        // the slot in between from and to must be marble
        if(this.board[middleRow][middleCol] != SlotState.Marble) return false;

        // return true if passes all the conditional testing
        return true;
    }
}
