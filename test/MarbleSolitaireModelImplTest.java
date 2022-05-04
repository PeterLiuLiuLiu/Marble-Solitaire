import marblesolitaire.model.MarbleSolitaireModelImpl;
import marblesolitaire.model.SlotState;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * This is a test for the MarbleSolitaireModelImpl, on all its public constructors and methods
 */
public class MarbleSolitaireModelImplTest {

    MarbleSolitaireModelImpl test;

    /**
     * This tests if the first constructor, the getScore and the getGameState method
     */
    @Test
    public void testNormalFirstCon(){
        this.test = new MarbleSolitaireModelImpl();
        assertEquals(32, this.test.getScore());
        assertEquals("    O O O    \n"+
                    "    O O O    \n"+
                    "O O O O O O O\n"+
                    "O O O _ O O O\n"+
                    "O O O O O O O\n"+
                    "    O O O    \n"+
                    "    O O O    ", this.test.getGameState());
    }

    /**
     * This tests if the second constructor, the getScore and the getGameState method
     */
    @Test
    public void testNormalSecondCon(){
        this.test = new MarbleSolitaireModelImpl(2,2);
        assertEquals(32, this.test.getScore());
        assertEquals("    O O O    \n"+
                "    O O O    \n"+
                "O O _ O O O O\n"+
                "O O O O O O O\n"+
                "O O O O O O O\n"+
                "    O O O    \n"+
                "    O O O    ", this.test.getGameState());
    }

    /**
     * This tests if the third constructor, the getScore and the getGameState method
     */
    @Test
    public void testNormalSThirdCon(){
        this.test = new MarbleSolitaireModelImpl(5);
        assertEquals(84, this.test.getScore());
        assertEquals("      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O O O _ O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      ", this.test.getGameState());
    }

    /**
     * This tests if the last constructor, the getScore and the getGameState method
     */
    @Test
    public void testNormalSFourthCon(){
        this.test = new MarbleSolitaireModelImpl(5, 6, 6);
        assertEquals(84, this.test.getScore());
        assertEquals("      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O _ O O O O\n"+
                "O O O O O O O O O O O\n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      ", this.test.getGameState());
    }

    /**
     * This tests if the second constructor throws exception
     * The empty cell is outside the entire board
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSecondConException_1(){
        this.test = new MarbleSolitaireModelImpl(10,2);
    }

    /**
     * This tests if the second constructor throws exception
     * The empty cell is in the OfB area
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSecondConException_2(){
        this.test = new MarbleSolitaireModelImpl(1,1);
    }

    /**
     * This tests if the third constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testThirdConException_1(){
        this.test = new MarbleSolitaireModelImpl(1);
    }

    /**
     * This tests if the third constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testThirdConException_2(){
        this.test = new MarbleSolitaireModelImpl(10);
    }


    /**
     * This tests if the third constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testThirdConException_3(){
        this.test = new MarbleSolitaireModelImpl(-3);
    }

    /**
     * This tests if the fourth constructor throws exception
     * Valid arm value but invalid empty cell
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNormalFourthConException_1(){
        this.test = new MarbleSolitaireModelImpl(3, 10, 3);
    }

    /**
     * This tests if the fourth constructor throws exception
     * Valid arm value but invalid empty cell
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFourthConException_2(){
        this.test = new MarbleSolitaireModelImpl(3, 6, 6);
    }

    /**
     * This tests if the fourth constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFourthConException_3(){
        this.test = new MarbleSolitaireModelImpl(-3, 6, 6);
    }

    /**
     * This tests if the fourth constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFourthConException_4(){
        this.test = new MarbleSolitaireModelImpl(6, 6, 6);
    }

    /**
     * This tests if the fourth constructor throws exception
     * Invalid arm value
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFourthConException_5(){
        this.test = new MarbleSolitaireModelImpl(1, 6, 6);
    }

    /**
     * This tests the move method, if it can move correct in valid input
     * This tests the gameState method as well after the move
     */
    @Test
    public void testNormalMove_1(){
        this.test = new MarbleSolitaireModelImpl(5);
        this.test.move(5, 3, 5, 5);
        assertEquals(83, this.test.getScore());
        assertEquals("      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O _ _ O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "O O O O O O O O O O O\n"+
                "      O O O O O      \n"+
                "      O O O O O      \n"+
                "      O O O O O      ", this.test.getGameState());
    }

    /**
     * This tests the move method, if it can move correct in valid input
     * This tests the gameState method as well after the move
     */
    @Test
    public void testNormalMove_2(){
        this.test = new MarbleSolitaireModelImpl(2,2);
        this.test.move(0, 2, 2, 2);
        assertEquals(31, this.test.getScore());
        assertEquals("    _ O O    \n"+
                "    _ O O    \n"+
                "O O O O O O O\n"+
                "O O O O O O O\n"+
                "O O O O O O O\n"+
                "    O O O    \n"+
                "    O O O    ", this.test.getGameState());
    }

    /**
     * This tests if the move method throws exception if invalid entries are provided
     * move distance == 1
     */
    @Test (expected = IllegalArgumentException.class)
    public void testMoveException_1(){
        this.test = new MarbleSolitaireModelImpl(5);
        this.test.move(5, 4, 5, 5);
    }

    /**
     * This tests if the move method throws exception if invalid entries are provided
     * diagonal move
     */
    @Test (expected = IllegalArgumentException.class)
    public void testMoveException_2(){
        this.test = new MarbleSolitaireModelImpl(5);
        this.test.move(3, 3, 5, 5);
    }

    /**
     * This tests if the move method throws exception if invalid entries are provided
     * move distance > 2
     */
    @Test (expected = IllegalArgumentException.class)
    public void testMoveException_3(){
        this.test = new MarbleSolitaireModelImpl(5);
        this.test.move(5, 2, 5, 5);
    }

    /**
     * This tests if the move method throws exception if invalid entries are provided
     * No empty slot
     */
    @Test (expected = IllegalArgumentException.class)
    public void testMoveException_4(){
        this.test = new MarbleSolitaireModelImpl(5);
        this.test.move(5, 2, 5, 4);
    }

    /**
     * This tests if the isGameOver method returns the correct state of game
     */
    @Test
    public void testIsGameOverFalse(){
        this.test = new MarbleSolitaireModelImpl(2,2);
        this.test.move(0, 2, 2, 2);
        assertFalse(this.test.isGameOver());
    }

    /**
     * This tests if the isGameOver method returns the correct state of game
     */
    @Test
    public void testIsGameOverTrue(){
        this.test = new MarbleSolitaireModelImpl();
        this.test.move(1, 3, 3, 3);
        this.test.move(2, 1, 2, 3);
        this.test.move(2, 4, 2, 2);
        this.test.move(2, 6, 2, 4);
        this.test.move(4, 6, 2, 6);
        this.test.move(4, 1, 2, 1);
        this.test.move(4, 5, 2, 5);
        this.test.move(4, 3, 2, 3);
        this.test.move(6, 3, 4, 3);
        this.test.move(4, 3, 4, 5);
        this.test.move(2, 4, 4, 4);
        assertFalse(this.test.isGameOver());
        this.test.move(4, 4, 4, 6);
        this.test.move(6, 4, 4, 4);
        this.test.move(0, 4, 2, 4);
        this.test.move(0, 2, 0, 4);
        this.test.move(2, 2, 0, 2);
        this.test.move(4, 2, 2, 2);
        this.test.move(6, 2, 4, 2);
//        System.out.println(this.test.getGameState());
        assertEquals(14, this.test.getScore());
        assertTrue(this.test.isGameOver());
    }

    /**
     * This method tests the getBoard method and see if it returns a copy o the board,
     * changing the board value in test should not change the original 2D array
     */
    @Test
    public void testGetBoard(){
        this.test = new MarbleSolitaireModelImpl();
        this.test.move(1, 3, 3, 3);
        SlotState[][] copy = this.test.getBoard();

        // Mutate the original test board by making a move,
        // checking if the copied board, should not be mutated
        this.test.move(2, 1, 2, 3);
        String copyBoard = Arrays.stream(copy).map(
                row -> Arrays.stream(row).map(
                        e -> e.toString()).collect(Collectors.joining(" "))
        ).collect(Collectors.joining("\n"));
        assertEquals("    O O O    \n"+
                "    O _ O    \n"+
                "O O O _ O O O\n"+
                "O O O O O O O\n"+
                "O O O O O O O\n"+
                "    O O O    \n"+
                "    O O O    ", copyBoard);

        // Modify the copied array, make sure the mutation
        // does not affect the original board
        copy[3][3] = SlotState.Empty;
        String updatedCopyBoard = Arrays.stream(copy).map(
                row -> Arrays.stream(row).map(
                        e -> e.toString()).collect(Collectors.joining(" "))
        ).collect(Collectors.joining("\n"));
        assertEquals("    O O O    \n"+
                "    O _ O    \n"+
                "O O O _ O O O\n"+
                "O O O _ O O O\n"+
                "O O O O O O O\n"+
                "    O O O    \n"+
                "    O O O    ", updatedCopyBoard);
        assertEquals("    O O O    \n"+
                "    O _ O    \n"+
                "O _ _ O O O O\n"+
                "O O O O O O O\n"+
                "O O O O O O O\n"+
                "    O O O    \n"+
                "    O O O    ", this.test.getGameState());


    }
}
