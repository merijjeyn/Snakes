package project;

import java.awt.Color;

import game.Drawable;
import ui.GridPanel;

public class Body implements Drawable {
	//X and Y coordinates of the Body
	protected int X;
	protected int Y;
	
	/**
	 * Constructs the Body. Body is the one square part of the snake.
	 * @param X x coordinate
	 * @param Y y coordinate
	 */
	public Body(int X, int Y) {
		this.X=X;
		this.Y=Y;
	}
	
	/**
	 * Draws the square blue denoting a normal Body
	 */
	@Override
	public void draw(GridPanel panel) {
		panel.drawSquare(X, Y, Color.BLUE);
	}
	
	/**
	 * Getter method for the X coordinate.
	 * @return
	 */
	public int getX() {
		return X;
	}
	
	/**
	 * Setter method for the Y coordinate.
	 * @param x
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * Getter method for the X coordinate.
	 * @return
	 */
	public int getY() {
		return Y;
	}
	
	/**
	 * Setter method for the Y coordinate.
	 * @param y
	 */
	public void setY(int y) {
		Y = y;
	}

}
