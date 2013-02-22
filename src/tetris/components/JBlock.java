package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Colors;
import resources.Measurements;
import tetris.CollisionHandler;

public class JBlock extends Block{

	private final int color = Colors.PURPLE;
	ArrayList<Square> squares;
	private float speed;

	/**
	 * Create a new T-shaped block
	 * @param x The starting x-coordinate for the block's main square
	 * @param y The starting y-coordinate for the block's main square
	 * @param speed The speed with which the block will be falling
	 */
	public JBlock(float x, float y, float speed) {
		super();
		this.speed = speed;
		setSquares(generateSquares(x, y));
	}
	
	public float getMainSquareXPos() {
		return squares.get(0).getX();
	}
	
	public float getMainSquareYPos() {
		return squares.get(0).getY();

	}

	public void setOnTop() {
		setSquares(generateSquares(276 + 4 * BlockInfo.SIZE,
				Measurements.GRID_YSTART - BlockInfo.SIZE));
	}
	
	public ArrayList<Square> generateSquares(float x, float y) {
		squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y, speed)); // main square
		squares.add(new Square(color, x-BlockInfo.SIZE, y - BlockInfo.SIZE, speed));
		squares.add(new Square(color, x-BlockInfo.SIZE, y, speed));
		squares.add(new Square(color, x+ BlockInfo.SIZE, y,speed ));

		return squares;
	}

	public void setPosition(float x, float y) {
		setSquares(generateSquares(x, y));
	}
	
	/**
	 * Rotates the block clockwise. Id the block will collide with other 
	 * squares when rotation, nothing will happen.
	 */
	public void rotate() {
		if(canRotate(1)) {
			editPosition((getRotation() + 1) % 4);
		}
	}

	/**
	 * Rotates the block clockwise. Id the block will collide with other 
	 * squares when rotation, nothing will happen.
	 */
	public void reverseRotate() {
		if(canRotate(3)) {
			editPosition((getRotation() + 3) % 4);
		}
	}

	private void editPosition(int i) {
		float x = squares.get(0).getX();
		float y = squares.get(0).getY();
		switch (i) {
		case 0:
			squares.get(1).setY(y - BlockInfo.SIZE).setX(x-BlockInfo.SIZE);
			squares.get(2).setY(y).setX(x - BlockInfo.SIZE);
			squares.get(3).setY(y).setX(x + BlockInfo.SIZE);
			break;
		case 1:
			squares.get(1).setY(y-BlockInfo.SIZE).setX(x);
			squares.get(2).setY(y+BlockInfo.SIZE).setX(x);
			squares.get(3).setY(y - BlockInfo.SIZE).setX(x+BlockInfo.SIZE);
			break;
		case 2:
			squares.get(1).setY(y).setX(x- BlockInfo.SIZE);
			squares.get(2).setY(y).setX(x+ BlockInfo.SIZE);
			squares.get(3).setY(y + BlockInfo.SIZE).setX(x+BlockInfo.SIZE);
			break;
		case 3:
			squares.get(1).setY(y+BlockInfo.SIZE).setX(x);
			squares.get(2).setY(y - BlockInfo.SIZE).setX(x);
			squares.get(3).setY(y + BlockInfo.SIZE).setX(x-BlockInfo.SIZE);
			break;
		}
		setRotation(i);
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
		JBlock block = new JBlock(getMainSquareXPos(), getMainSquareYPos(), 0);
		block.editPosition((getRotation()+dir) % 4);
		System.out.println(CollisionHandler.isColliding(block));
		if(CollisionHandler.isColliding(block)) {
			block = null;
			return false;
		}
		block = null;
		return true;
	}
}
