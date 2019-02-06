package player;

import java.util.ArrayList;
import java.util.List;
import game.Direction;
import game.GridGame;
import project.Body;
import project.Food;
import project.Snake;

/**
 * Class that implements the game logic for Snakes!.
 * 
 */
public class SnakeSimulator extends GridGame {
	
	private List<Snake> snakes;
    private Body[][] bodiesMap;
    private Food food;
    
    /**
     * Constructs the SnakeSimulator. Creates the food.
     * @param gridWidth number of grid squares along the width
     * @param gridHeight number of grid squares along the height
     * @param gridSquareSize size of a grid square in pixels
     * @param frameRate frame rate (number of timer ticks per second)
     */
	public SnakeSimulator(int gridWidth, int gridHeight, int gridSquareSize, int frameRate) {
		super(gridWidth, gridHeight, gridSquareSize, frameRate);
		food = new Food();
		firstFood(gridWidth, gridHeight);
		addDrawable(food);
		bodiesMap = new Body[gridWidth][gridHeight];
		snakes = new ArrayList<>();
        
	}
	/**
	 * The main loop running the game.
	 */
	@Override
	protected void timerTick() {
        // Determine and execute actions for all snakes
		ArrayList<Snake> snakesCopy = new ArrayList<>(snakes);
        for (Snake creature : snakesCopy) {
        	
        	//whereToGo is the direction snake will move in, determined by the goToFood method
        	Direction whereToGo = goToFood(creature);
        	if (whereToGo!=null) {
        		
        		//Resets current snakes map positions (will get marked again after actions)
        		updateCreaturesMap(creature,true);   
        		
        		//In case of a consumption, the new part of the snake will be at the tail of the snake. So we keep a temporary Body.
        		Body temp = creature.move(whereToGo);
        		
        		//Checks if the head of the snake is at the foods position.
        		if (creature.snake.peek().getX()==food.getX() && creature.snake.peek().getY()==food.getY()) {
        			Body newborn = new Body(temp.getX(),temp.getY());
        			creature.grow(newborn.getX(),newborn.getY());
            		addDrawable(creature.snake.get(creature.snake.size()-1));
            		
            		//Update bodiesMap here so the new food location wont overlap the snakes part.
            		updateCreaturesMap(creature,false); 
            		
            		//The food is eaten, refresh the position.
            		locateFood(getGridWidth(),getGridHeight());
        		}
        		
        		//If the size of the snake reaches 8, snakes tail will become a new head and they will be divided down the middle.
        		if (creature.snake.size()==8) {
            		removeDrawable(creature.snake.get(creature.snake.size()-1));
        			addCreature(creature.reproduce());
        		} 
        		//After the movement, snakes map position gets updated.
        		updateCreaturesMap(creature,false); 
        	}
        }
	}
	/**
	 * Creates a new snake, updates the map, draws snakes elements on grid.
	 * @param creature New snake
	 */
	public void addCreature(Snake creature) {
        snakes.add(creature);
		for (Body element : creature.snake) {
		    addDrawable(element);
		    bodiesMap[element.getX()][element.getY()]=element;
		}           
	}
	
	/**
	 * Returns free directions of that particular snake. Used in goToFood method.
	 * @param creature snake
	 * @return ArrayList of free directions
	 */
	private ArrayList<Direction> canIMove(Snake creature) {
		ArrayList<Direction> possibleDirection = new ArrayList<>();
		
		int xCoordinate=creature.snake.peek().getX();
		int yCoordinate=creature.snake.peek().getY();
		
		if (isInside(xCoordinate+1,yCoordinate))
			if (bodiesMap[xCoordinate+1][yCoordinate]==null)
				possibleDirection.add(Direction.RIGHT);
    	if (isInside(xCoordinate,yCoordinate-1)) 
    		if (bodiesMap[xCoordinate][yCoordinate-1]==null)
    			possibleDirection.add(Direction.UP);
    	if (isInside(xCoordinate,yCoordinate+1)) 
        	if (bodiesMap[xCoordinate][yCoordinate+1]==null)
        		possibleDirection.add(Direction.DOWN);
    	if (isInside(xCoordinate-1,yCoordinate)) 
    		if (bodiesMap[xCoordinate-1][yCoordinate]==null)
    			possibleDirection.add(Direction.LEFT);

    		return possibleDirection;
	}
	
	/**
	 * Updates bodiesMap[][].
	 * @param creature the snake whose map is updated.
	 * @param delete if true resets the map, else marks again.
	 */
	private void updateCreaturesMap(Snake creature,boolean delete) {
		if (delete) {
			for (Body element : creature.snake) {
			    bodiesMap[element.getX()][element.getY()]=null;
			}
			return;
		}
		for (Body element : creature.snake) {
		    bodiesMap[element.getX()][element.getY()]=element;
		}    
    }
	private boolean isInside(int x, int y) {
		return  (x >= 0 && x < getGridWidth()) && (y >= 0 && y < getGridHeight());
	}
	
	/**
	 * AI the snake is using. According to the difference of coordinates of food and snake's head, it guides the snake.
	 * Makes an ArrayList of the available Directions and returns one of them randomly.
	 * If snake cannot move in that direction it returns one of the free directions(free directions are provided by canIMove method).
	 * @param creature
	 * @return Direction snake will move in, null if there is no free directions.
	 */
	private Direction goToFood(Snake creature) {
		
		ArrayList<Direction> possibleDirection = new ArrayList<>();

		int xCoordinate=creature.snake.peek().getX();
		int yCoordinate=creature.snake.peek().getY();
		
		//Compare snake's and food's positions. Depending on their difference,if the desired direction is empty, add direction to list.
		if(xCoordinate<food.getX()&&isInside(xCoordinate+1,yCoordinate))
			if (bodiesMap[xCoordinate+1][yCoordinate]==null)
				possibleDirection.add(Direction.RIGHT);
		if(xCoordinate>food.getX()&&isInside(xCoordinate-1,yCoordinate))
			if (bodiesMap[xCoordinate-1][yCoordinate]==null)
				possibleDirection.add(Direction.LEFT);
		if(yCoordinate<food.getY()&&isInside(xCoordinate,yCoordinate+1))
			if (bodiesMap[xCoordinate][yCoordinate+1]==null)
				possibleDirection.add(Direction.DOWN);
		if(yCoordinate>food.getY()&&isInside(xCoordinate,yCoordinate-1))
			if (bodiesMap[xCoordinate][yCoordinate-1]==null)
				possibleDirection.add(Direction.UP);
		
		//If all the desired directions are occupied by other snakes, add free directions to list.
		if (possibleDirection.size()==0) 
    		possibleDirection.addAll(canIMove(creature));
		//If there are no free directions, return null
		if (possibleDirection.size()==0)
			return null;
		
		return possibleDirection.get((int)(possibleDirection.size()*Math.random()));
	}
	
	/**
	 * Changes the position of the food after it is eaten.
	 * Rerandoms if the randomed position is occupied by one of snakes part.
	 * @param x gridWidth
	 * @param y gridHeight
	 */
	private void locateFood(int x, int y) {
		int X = (int) (x*Math.random());
		int Y = (int) (y*Math.random());
		
		while (bodiesMap[X][Y]!=null) {
			X = (int) (x*Math.random());
			Y = (int) (y*Math.random());
		}
		food.setX(X);
		food.setY(Y);
		
	}
	/**
	 * Locates the first food. It is a separate method because the initial position of
	 * the first snake is hardcoded and the first food gets 
	 * created before bodiesMap[][] get updated (map is empty at that moment).
	 * if the initial position of the first snake gets changed, program will run just fine
	 * but the first food might be on the same square as the snake. (very unlikely to happen).
	 * @param x gridWidth
	 * @param y gridHeight
	 */
	private void firstFood(int x, int y) {
		int X = (int) (x*Math.random());
		int Y = (int) (y*Math.random());
		
		while ((X==1 && Y==1) || (X==2 && Y==1) || (X==3 && Y==1) || (X==4 && Y==1) ) {
			X = (int) (x*Math.random());
			Y = (int) (y*Math.random());
		}
		food.setX(X);
		food.setY(Y);
	}

	
}
