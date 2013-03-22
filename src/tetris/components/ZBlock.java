package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Colors;
import resources.Measurements;
import tetris.CollisionHandler;


public class ZBlock extends Block {
	private final int color = Colors.ORANGE;
	private ArrayList<Square> squares;
	private Square mainSquare;
	private float speed;

	/**
	 * Create a new S-shaped block
	 * @param x The starting x-coordinate for the block's main square
	 * @param y The starting y-coordinate for the block's main square
	 * @param speed The speed with which the block will be falling
	 */
	public ZBlock(float x, float y, float speed) {
		super();
		this.speed = speed;
		setSquares(generateSquares(x, y));
	}
	
	/**
	 * Get the x-coordinate of the main square in this block.
	 * @return The x-coordinate of the main square
	 */
	public float getMainSquareXPos() {
		return mainSquare.getX();
	}
	
	public void setOnTop() {
		setSquares(generateSquares(276 + 4 * BlockInfo.SIZE,
				Measurements.GRID_YSTART - BlockInfo.SIZE));
	}
	
	/**
	 * Get the y-coordinate of the main square in this block.
	 * @return The y-coordinate of the main square
	 */
	public float getMainSquareYPos() {
		return mainSquare.getY();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ArrayList<Square> generateSquares(float x, float y) {
		squares = new ArrayList<Square>();
		mainSquare = new Square(color, x, y, speed);
		squares.add(mainSquare);
		squares.add(new Square(color, x -BlockInfo.SIZE, y, speed));
		squares.add(new Square(color, x, y + BlockInfo.SIZE, speed));
		squares.add(new Square(color, x + BlockInfo.SIZE, y
				+ BlockInfo.SIZE, speed));
		return squares;
	}
	
	public void setPosition(float x, float y) {
		setSquares(generateSquares(x, y));
	}

	public void editPosition(int newPosition) {
		float x = mainSquare.getX();
		float y = mainSquare.getY();

		if (newPosition == 0) {
			squares.get(1).setX(x - BlockInfo.SIZE).setY(y);
			squares.get(2).setX(x)
					.setY(y + BlockInfo.SIZE);
			squares.get(3).setX(x+ BlockInfo.SIZE).setY(y + BlockInfo.SIZE);
		} else if (newPosition == 1) {
			squares.get(1).setX(x- BlockInfo.SIZE).setY(y);
			squares.get(2).setX(x)
					.setY(y - BlockInfo.SIZE);
			squares.get(3).setX(x - BlockInfo.SIZE).setY(y+BlockInfo.SIZE);

		} else if (newPosition == 2) {
			squares.get(1).setX(x + BlockInfo.SIZE).setY(y);
			squares.get(2).setX(x)
					.setY(y - BlockInfo.SIZE);
			squares.get(3).setX(x- BlockInfo.SIZE).setY(y - BlockInfo.SIZE);

		} else if (newPosition == 3) {
			squares.get(1).setX(x+ BlockInfo.SIZE).setY(y);
			squares.get(2).setX(x)
					.setY(y + BlockInfo.SIZE);
			squares.get(3).setX(x + BlockInfo.SIZE).setY(y- BlockInfo.SIZE);
		}

		setRotation(newPosition);
	}

	/**
	 * Rotates the block clockwise. Id the block will collide with other 
	 * squares when rotation, nothing will happen.
	 */
	public void rotate() {
		if(canRotate(1))
			editPosition((getRotation() + 1) % 4);
	}

	/**
	 * Rotates the block clockwise. Id the block will collide with other 
	 * squares when rotation, nothing will happen.
	 */
	public void reverseRotate() {
		if(canRotate(3))
			editPosition((getRotation() + 3) % 4);
	}
	
	/**
	 * Checks if the block can do a rotation from it's current position.
	 * It creates a new temporary block which is rotated to the desired rotation, and
	 * then checks if this new block's position collides with any squares in the grid. This
	 * is done by the collision handler.
	 * 
	 * @param dir 1 if the rotation is to be clockwise, otherwise 3 (reverse rotation)
	 */
	public boolean canRotate(int dir) {
		ZBlock block = new ZBlock(getMainSquareXPos(), getMainSquareYPos(), 0);
		block.editPosition((getRotation()+dir) % 4);
		if(CollisionHandler.isColliding(block)) {
			block = null;
			return false;
		}
		block = null;
		return true;
	}

}
