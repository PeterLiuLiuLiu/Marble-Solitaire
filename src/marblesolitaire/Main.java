package marblesolitaire;

import marblesolitaire.controller.Controller;
import marblesolitaire.model.MarbleSolitaireModelImpl;
import marblesolitaire.view.JFrameView;

/**
 * This is the main class, it initializes the model, the view and pass them all the controller
 * It then pass the controller into the view to achieve interaction between view and controller
 */
public class Main {
    public static void main(String []args){
        MarbleSolitaireModelImpl model = new MarbleSolitaireModelImpl();
        JFrameView view = new JFrameView("Peter's Marble Solitaire");
        Controller controller = new Controller(model, view);
        view.setController(controller);
    }
}
