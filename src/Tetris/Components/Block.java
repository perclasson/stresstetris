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
		for (Square square : squares) {
			square.moveDown();
		}
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void moveRight() {
		for (Square square : squares) {
			square.moveRight();
		}
	}

	public void moveLeft() {
		for (Square square : squares) {
			square.moveLeft();
		}
	}
	
	
}
