package Tetris.Components;

import java.util.ArrayList;

public class OBlock extends Block {

	private final int color = Codes.RED;
	public OBlock(int position, float x, float y) {
		super(position);
		ArrayList<Square> squares = generateSquares(x, y);
		setSquares(squares);
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y));
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y));
		squares.add(new Square(color, x, y + Codes.BLOCK_SIZE));
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));

		return squares;
	}

}
