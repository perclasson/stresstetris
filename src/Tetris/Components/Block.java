package Tetris.Components;

import java.util.ArrayList;

public class Block {
	private ArrayList<Square> squares;
	private int rotation;
	private boolean isMoving;

	public Block(int rotation) {
		this.rotation = rotation;
		isMoving = true;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void halt() {
		isMoving = false;
	}

	public void rotate() {

	}

	public void reverseRotate() {

	}

	public void setPosition(float x, float y) {

	}

	protected void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void draw() {
		for (Square square : squares) {
			square.draw();
		}
	}

	public void moveDown() {
		if (canMoveDown() && isMoving()) {
			for (Square square : squares) {
				square.moveDown();
			}
		}
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void moveRight() {
		if (canMoveRight() && isMoving()) {
			for (Square square : squares) {
				square.moveRight();
			}
		}
	}

	public void moveRight(float dist) {
		for (Square square : squares) {
			square.moveRight(dist);
		}

	}

	public void moveLeft() {
		if (canMoveLeft() && isMoving()) {
			for (Square square : squares) {
				square.moveLeft();
			}
		}
	}

	public void moveLeft(float dist) {
		for (Square square : squares) {
			square.moveLeft(dist);
		}

	}

	public void moveDown(float dist) {
		for (Square square : squares) {
			square.moveDown(dist);
		}
	}

	public boolean canMoveLeft() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveLeft();
			if (!canMove)
				return false;
		}
		return true;
	}

	public boolean canMoveRight() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveRight();
			if (!canMove)
				return false;
		}
		return true;
	}

	public boolean canMoveUp() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveUp();
			if (!canMove)
				return false;
		}
		return true;
	}

	public boolean canMoveDown() {
		boolean canMove;
		for (Square square : squares) {
			canMove = square.canMoveDown();
			if (!canMove) {
				halt();
					float distanceLeft = (float)((square.getY() + Values.SIZE)-(Values.GRID_YSTART+Values.GRID_HEIGHT));
					if(distanceLeft < 0) 
						distanceLeft = distanceLeft*-1;
					moveDown(distanceLeft);
					
				return false;
			}
		}
		return true;
	}

}
