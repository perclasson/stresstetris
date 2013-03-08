package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Measurements;

public class Block {
	private ArrayList<Square> squares;
	private int rotation;
	private boolean isMoving; // As long as this is set to true, the block will
								// be able to move

	/**
	 * Crates a new block. By default, the <code>isMoving</code> and
	 * <code>rotation</code> variables are <code>true</code> and <code>0</code>.
	 */
	public Block() {
		rotation = 0;
		isMoving = true;
	}

	/**
	 * Returns true if the block "is moving" (is able to move)
	 * 
	 * @return true if the block can move, false otherwise
	 */
	public boolean isMoving() {
		return isMoving;
	}

	public float getSpeed() {
		return squares.get(0).getSpeed();
	}

	public void setSpeed(float speed) {
		for (Square square : squares) {
			square.setSpeed(speed);
		}
	}
	
	/**
	 * Stops all movement of the block.
	 */
	public void halt() {
		isMoving = false;
	}

	/**
	 * Rotates the block clockwise. Only implemented in subclasses of block.
	 */
	public void rotate() {

	}

	/**
	 * Rotates the block counter clockwise. Only implemented in subclasses of
	 * block.
	 */
	public void reverseRotate() {

	}

	/**
	 * Set's the x and y coordinates of the block. Only implemented in
	 * subclasses of block.
	 */
	public void setPosition(float x, float y) {

	}

	/**
	 * Takes an ArrayList of squares and sets this as the square list of the
	 * block (the squares the block is built from).
	 * 
	 * @param squares
	 *            A new list of squares which the block will be built from
	 */
	public void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	/**
	 * Draws the block (this is done by drawing all if it's squares)
	 */
	public void draw() {
		for (Square square : squares) {
			square.draw();
		}
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	/**
	 * Moves the block right. The distance moved will be equal to the
	 * <code>speed</code> of the block.
	 */
	public void moveRight() {
		if (canMoveRight() && isMoving()) {
			for (Square square : squares) {
				square.moveRight();
			}
		}
	}

	/**
	 * Moves the block right a specified distance
	 * 
	 * @param dist
	 *            The distance the block is to be moved right
	 */
	public void moveRight(float dist) {
		for (Square square : squares) {
			square.moveRight(dist);
		}

	}

	/**
	 * Moves the block left. The distance moved will be equal to the
	 * <code>speed</code> of the block.
	 */
	public void moveLeft() {
		if (canMoveLeft() && isMoving()) {
			for (Square square : squares) {
				square.moveLeft();
			}
		}
	}

	/**
	 * Moves the block left a specified distance
	 * 
	 * @param dist
	 *            The distance the block is to be moved left
	 */
	public void moveLeft(float dist) {
		for (Square square : squares) {
			square.moveLeft(dist);
		}

	}

	/**
	 * Moves the block down. The distance moved will be equal to the
	 * <code>speed</code> of the block.
	 */
	public void moveDown() {
		if (canMoveDown() && isMoving()) {
			for (Square square : squares) {
				square.moveDown();
			}
		}
	}

	/**
	 * Moves the block down a specified distance
	 * 
	 * @param dist
	 *            The distance the block is to be moved down
	 */
	public void moveDown(float dist) {
		for (Square square : squares) {
			square.moveDown(dist);
		}
	}

	/**
	 * For each square in the block, check if it can move left without exiting
	 * the grid.
	 * 
	 * @return true if the block can move left, false otherwise.
	 */
	public boolean canMoveLeft() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveLeft();
			if (!canMove)
				return false;
		}
		return true;
	}

	/**
	 * For each square in the block, check if it can move right without exiting
	 * the grid.
	 * 
	 * @return true if the block can move right, false otherwise.
	 */
	public boolean canMoveRight() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveRight();
			if (!canMove)
				return false;
		}
		return true;
	}

	/**
	 * For each square in the block, check if it can move up without exiting the
	 * grid.
	 * 
	 * @return true if the block can move up, false otherwise.
	 */
	public boolean canMoveUp() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveUp();
			if (!canMove)
				return false;
		}
		return true;
	}

	/**
	 * For each square in the block, check if it can move up without exiting the
	 * grid. If the block will be exiting the grid with it's next move, the
	 * block is halted and moved down to the edge of the grid.
	 * 
	 * @return true if the block can move down, false otherwise.
	 */
	public boolean canMoveDown() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveDown();
			if (!canMove) {
				halt();
				float distanceLeft = (float) ((square.getY() + BlockInfo.SIZE) - (Measurements.GRID_YSTART + Measurements.GRID_HEIGHT));
				if (distanceLeft < 0)
					distanceLeft = distanceLeft * -1;
				moveDown(distanceLeft);

				return false;
			}
		}
		return true;
	}

	public boolean isInsideGrid() {
		for (Square square : squares) {
			if (square.getY() < (BlockInfo.SIZE + Measurements.GRID_YSTART)) {
				return false;
			}
		}
		return true;
	}

	public void setOnTop() {
	}

}
