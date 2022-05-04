package marblesolitaire.controller;

import marblesolitaire.model.MarbleSolitaireModelImpl;
import marblesolitaire.model.SlotState;
import marblesolitaire.view.JFrameView;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller class which bridges the model and view
 * takes in output from model and feed into view for display, or
 * takes in move data from view and feed into model
 */

public class Controller {
    private final MarbleSolitaireModelImpl model;
    private final JFrameView view;

    private List<Integer> moveList;

    /**
     * Constructor for the controller class, takes in model and view as parameters,
     * calls the methods in view to initialize the board buttons
     * @param model, model of Marble Solitaire
     * @param view, model view for Marble Solitaire
     */
    public Controller(MarbleSolitaireModelImpl model, JFrameView view){
        this.model = model;
        this.view = view;
        this.view.initializeText(this.model.getScore());
        this.view.initializeBoard(this.model.getBoard());
        this.moveList = new ArrayList<>();
    }

    /**
     * This method is the core that connects the model with view
     * view calls makeMove method for this controller from its own class,
     * It then sends the to and from coordinate to model, and the model's getBoard method
     * returns a copy of the board if the command is enough to make move.
     * If the command is not yet finished, return null
     * @param row, row coordinate
     * @param col, col coordinate
     * @return a copy of SloteSlate 2D board if move is successful,
     * or null if the move is not yet done
     */
    public SlotState[][] makeMove(int row, int col){
        this.moveList.add(row);
        this.moveList.add(col);
        if(this.moveList.size() == 4){
            int fromRow = this.moveList.get(0);
            int fromCol = this.moveList.get(1);
            int toRow = this.moveList.get(2);
            int toCol = this.moveList.get(3);
            try{
                this.model.move(fromRow, fromCol, toRow, toCol);
            }catch(IllegalArgumentException iae){}
            this.moveList.clear();
            return this.model.getBoard();
        }
        return null;
    }

    /**
     * Returns this model's score, directly calling the method from the model
     * @return an int, this model's score
     */
    public int getScore(){return this.model.getScore();}

    /**
     * Returns from model if the game is over
     * @return a boolean, if the model is game overed
     */
    public boolean isGameOver(){return this.model.isGameOver();}
}
