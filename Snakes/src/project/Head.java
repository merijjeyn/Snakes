package project;

import java.awt.Color;

import game.Drawable;
import ui.GridPanel;

/**
 * Head class for the snake. 
 * Implements Drawable to be able to draw, extends Body to be able to get in the LinkedList.
 *
 */
public class Head extends Body implements Drawable {
	
	/**
	 * Constructs the Head.
	 * @param X x coordinate of the head
	 * @param Y y coordinate of the head
	 */
	public Head(int X, int Y) {
		super(X,Y);
	}
	
	/**
	 * Draws the square red, denoting the head of the snake.
	 */
	@Override
	public void draw(GridPanel panel) {
		panel.drawSquare(X, Y, Color.RED);
	}

}
