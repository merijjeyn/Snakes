package project;

import java.awt.Color;

import game.Drawable;
import ui.GridPanel;
/**
 * 
 * Class for the food snakes are trying to eat. Implements drawable interface. 
 */
public class Food implements Drawable{
	
	private int X;
	private int Y;
	
	/**
	 * Draws the small square yellow.
	 */
	@Override
	public void draw(GridPanel panel) {
		panel.drawSmallSquare(X, Y, Color.YELLOW);
	}
	
	/**
	 * Getter method for the X coordinate of the food.
	 * @return X coordinate
	 */
	public int getX() {
		return X;
	}

	/**
	 * Getter method for the Y coordinate of the food.
	 * @return Y coordinate
	 */
	public int getY() {
		return Y;
	}

	/**
	 * Setter method for the X coordinate of the food.
	 * @param x desired X coordinate
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * Setter method for the Y coordinate of the food.
	 * @param y desired Y coordinate
	 */
	public void setY(int y) {
		Y = y;
	}

}
