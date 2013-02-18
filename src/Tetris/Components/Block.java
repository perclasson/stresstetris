package Tetris.Components;

import java.util.ArrayList;

public class Block {
	private ArrayList<Square> squares;
	private int rotation;

	public Block(int rotation) {
		this.rotation = rotation;
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
		if (canMoveDown()) {
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
		if (canMoveRight()) {
			for (Square square : squares) {
				square.moveRight();
			}
		}
	}

	public void moveLeft() {
		if (canMoveLeft()) {
			for (Square square : squares) {
				square.moveLeft();
			}
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
			if (!canMove)
				return false;
		}
		return true;
	}

}
