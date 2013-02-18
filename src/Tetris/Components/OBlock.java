package Tetris.Components;

import java.util.ArrayList;

public class OBlock extends Block {

	private final int color = Values.RED;
	public OBlock(int position, float x, float y) {
		super(position);
		ArrayList<Square> squares = generateSquares(x, y);
		setSquares(squares);
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y));
		squares.add(new Square(color, x + Values.BLOCK_SIZE, y));
		squares.add(new Square(color, x, y + Values.BLOCK_SIZE));
		squares.add(new Square(color, x + Values.BLOCK_SIZE, y
				+ Values.BLOCK_SIZE));

		return squares;
	}

}
