package main;

import java.awt.EventQueue;

import player.SnakeSimulator;
import project.Snake;
import ui.ApplicationWindow;

/**
 * The main class of the project.
 */
public class Main {

    /**
     * Main entry point for the application.
     *
     * @param args application arguments
     * @throws InterruptedException 
     */
    public static void main(String[] args)  {
    	EventQueue.invokeLater(() -> {
            try {
            	// Create game
                // You can change the world width and height, size of each grid square in pixels or the game speed
            	SnakeSimulator game = new SnakeSimulator(25,25,20,10);
            	
            	// Create the snake
            	game.addCreature(new Snake(4,1));
            	
            	// Create application window that contains the game panel
                ApplicationWindow window = new ApplicationWindow(game.getGamePanel());
                window.getFrame().setVisible(true);
                
                // Start game
                game.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
            
    	
    }

}
