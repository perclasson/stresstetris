package Tetris.Components;

import java.util.ArrayList;

public class TBlock extends Block {

	private final int color = Codes.RED;
	ArrayList<Square> squares = new ArrayList<Square>();
	private Square mainSquare;

	public TBlock(int position, float x, float y) {
		super(position);
		setSquares(generateSquares(x, y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		squares.add(new Square(color, x, y));
		mainSquare = new Square(color, x, y + Codes.BLOCK_SIZE);
		squares.add(mainSquare);
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));
		squares.add(new Square(color, x - Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));

		return squares;
	}
	public void rotate() {
		editPosition(getPosition()+1 % 4);
	}
	
	public void reverseRotate() {
		editPosition(getPosition()-1 % 4);
	}
	
	private void editPosition(int i) {
		switch (i) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}
}
