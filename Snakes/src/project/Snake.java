package project;

import java.util.LinkedList;
import game.Direction;

/**
 * Class of the snakes of the game. Implements the move and grow methods.
 */

public class Snake  {
	public LinkedList<Body> snake = new LinkedList<Body>();
	
	/**
	 * Creates a new snake with the head at the desired position. Used for the first snake.
	 * @param X x coordinate of the head of the snake
	 * @param Y y coordinate of the head of the snake
	 */
	public Snake(int X, int Y) {	
		snake.add(new Head(X,Y));
		for (int i=1; i<4 ; i++) {
			snake.add(new Body(X-i,Y));
		}
	}
	
	/**
	 * Creates a new snake as a result of reproduction.
	 * @param baby
	 */
	private Snake(LinkedList<Body> baby) {
		this.snake = baby;
	}
	
	/**
	 * Moves the snake in desired direction
	 * @param direction desired direction
	 * @return Body the positions of the last part of the snake. (needed in case of consumption	)
	 */
	public Body move(Direction direction) {
		int tempx = snake.peek().X;
		int tempy = snake.peek().Y;
		if (direction == Direction.DOWN) snake.peek().Y++;
		if (direction == Direction.UP) snake.peek().Y--;
		if (direction == Direction.LEFT) snake.peek().X--;
		if (direction == Direction.RIGHT) snake.peek().X++;

		for (int i = 1; i < snake.size(); i++)  {
			Body element = snake.get(i);
			int temp2x = element.X;
			int temp2y = element.Y;
			element.X = tempx;
			element.Y = tempy;
			tempx=temp2x;
			tempy=temp2y;
		}
		
		return new Body(tempx,tempy);
		
	}
	
	/**
	 * adds a new Body at the tail of the snake
	 * @param tempx x coordinates of the newly added Body(comes from move method)
	 * @param tempy y coordinates of the newly added Body(comes from move method)
	 */
	public void grow(int tempx,int tempy) {
		snake.offerLast(new Body(tempx,tempy));
	}
	
	/**
	 * produces a new snake at the tail of the parent snake.
	 * @return new snake
	 */
	public Snake reproduce() {
		LinkedList<Body> baby= new LinkedList<Body>();
		Body temp=snake.removeLast();
		baby.add(new Head(temp.X,temp.Y));
		for (int i=0; i<3; i++)
			baby.add(snake.removeLast());
		
		return new Snake(baby);
	}
}
