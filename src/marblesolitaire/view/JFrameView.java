package marblesolitaire.view;

import marblesolitaire.controller.Controller;
import marblesolitaire.model.SlotState;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the view class, uses Swing to display the marble solitaire
 * It treats the whole board as a 2D array of buttons, with different
 * SlotSlate displaying different information on button.
 * The button listen to clicks by user, and aggregate the move in controller,
 * if valid move is clicked, and responses were obtained from model through
 * the controller, the updated view would be displayed on the board.
 */
public class JFrameView implements ActionListener {

    private final JFrame frame = new JFrame();

    private final JLabel textLabel = new JLabel();
    private final JPanel labelPanel = new JPanel();

    private final JPanel buttonPanel = new JPanel();
    private JButton[][] marbleButtons;

    private final String MARBLE = "O";

    private int ROW;
    private int COL;

    private Controller controller;

    /**
     * Constructor of the class, set the basic frame of display, size, title etc
     * @param title, title of JFrame
     */
    public JFrameView(String title){
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 550);
        this.frame.setLocationRelativeTo(null);
//        this.frame.getContentPane().setBackground(new Color(255, 255, 255));
        this.frame.setTitle(title);
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);
    }

    /**
     * Called by the controller during controller's constructor to initialize the board
     * It displays the marble from the final field MARBLE
     * @param board, 2D array of enum SlotSlate
     */
    public void initializeBoard(SlotState[][] board) {
        this.ROW = board.length;
        this.COL = board[0].length;

        this.buttonPanel.setLayout(new GridLayout(this.ROW, this.COL));
//        this.buttonPanel.setBackground(new Color(0, 0, 0));
        this.marbleButtons = new JButton[board.length][board[0].length];
        for(int r = 0; r < this.ROW; r++){
            for(int c = 0; c < this.COL; c++){
                this.marbleButtons[r][c] = new JButton();
                this.marbleButtons[r][c].setFocusable(false);
                if(board[r][c] == SlotState.OfB){
                    this.marbleButtons[r][c].setBackground(new Color(0, 0, 0));
                    this.marbleButtons[r][c].setEnabled(false);
                }else if(board[r][c] == SlotState.Marble){
                    this.marbleButtons[r][c].setText(MARBLE);
                    this.marbleButtons[r][c].setBackground(new Color(255, 255, 255));
                    this.marbleButtons[r][c].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 40));
                }else{
                    this.marbleButtons[r][c].setBackground(new Color(255, 255, 255));
                    this.marbleButtons[r][c].setFont(new Font("Franklin Gothic Medium", Font.BOLD, 40));
                }
                this.marbleButtons[r][c].addActionListener(this);
                this.buttonPanel.add(this.marbleButtons[r][c]);
            }
        }
        this.labelPanel.add(this.textLabel);
        this.frame.add(this.labelPanel, BorderLayout.NORTH);
        this.frame.add(this.buttonPanel);
        this.frame.setVisible(true);
    }

    /**
     * called during the controller's contructor, to initialize the text
     * on the textfield above the board display for scoring
     * @param score
     */
    public void initializeText(int score) {
        this.textLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 40));
        this.textLabel.setHorizontalAlignment(JLabel.CENTER);
        this.labelPanel.setLayout(new BorderLayout());
        this.labelPanel.setBounds(0, 0, 500, 100);
        this.resetText(score, false);
        this.frame.add(this.textLabel);
    }

    /**
     * Pass the controller into this class and set as field, call as player clicks button
     * @param controller, the controller class of MVC
     */
    public void setController(Controller controller){
        this.controller = controller;
    }

    /**
     * This method is called when the button is clicked,
     * calls the getResponse method in this class to
     * pass the button coordinate to controller class
     * for model, and call repaintBoard method to refresh board,
     * And update score
     * @param e, action event (button information) triggered by button clicking
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SlotState[][] res = this.getResponse(e);
        if(res != null){
            this.repaintBoard(res, this.controller.isGameOver());
            this.resetText(this.controller.getScore(), this.controller.isGameOver());
        }
    }

    /**
     * This method is called to refresh the screen, if gameOver,
     * disable the buttons
     * @param board 2D  SlotSlate array to be iterated and refreshed
     * @param isGameOver, a boolean if the game is over
     */
    private void repaintBoard(SlotState[][] board, boolean isGameOver) {
        for(int r = 0; r < this.ROW; r++){
            for(int c = 0; c < this.COL; c++){
                if(board[r][c] == SlotState.OfB){
                    this.marbleButtons[r][c].setBackground(new Color(0, 0, 0));
                    this.marbleButtons[r][c].setEnabled(false);
                }else if(board[r][c] == SlotState.Marble){
                    this.marbleButtons[r][c].setText(MARBLE);
                    this.marbleButtons[r][c].setEnabled(!isGameOver);
                }else{
                    this.marbleButtons[r][c].setText("");
                    this.marbleButtons[r][c].setEnabled(!isGameOver);
                }
            }
        }
    }

    /**
     * This method resets the text of textfield (scoreboard)
     * @param score an int,  the score of the game extracted from the model
     * @param isGameOver a boolean, if the game is over or not
     */
    private void resetText(int score, boolean isGameOver){
        if(!isGameOver) this.textLabel.setText(String.format("Score: %d", score));
        else this.textLabel.setText(String.format("Game Over! Score: %d", score));
    }

    /**
     * This method is called to pass the button coordinate to the controller
     * and get its response, return back for refreshing
     * @param e, action event (button information) triggered by button clicking
     * @return, the response from the controller extracted from the model
     *          null, if no move made (not 2 button clicked)
     *          2D SlotState board, if the move is complete with 2 buttons clicked
     */
    private SlotState[][] getResponse(ActionEvent e){
        SlotState[][] res = null;
        for(int r = 0; r < this.ROW; r++){
            for(int c = 0; c < this.COL; c++){
                if(e.getSource() == this.marbleButtons[r][c]){
                    res = this.controller.makeMove(r, c);
                }
            }
        }
        return res;
    }

}
