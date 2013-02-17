package Tetris.Components;

import java.util.ArrayList;

public class Block {
	private ArrayList<Square> squares;
    private int position;
    
	public Block(int position) {
		this.position = position;
	}
	
	protected void setSquares(ArrayList<Square> squares) {
		this.squares = squares;
	}

	public void draw() {
		for (Square square : squares) {
			square.draw();
		}
	}

	public void rotate() {

	}

	public void moveDown() {
		for (Square square : squares) {
			square.moveDown();
		}
	}
	
	public int getPosition() {
		return position;
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
