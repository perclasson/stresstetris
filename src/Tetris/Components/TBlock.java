package Tetris.Components;

import java.util.ArrayList;

public class TBlock extends Block{
	
	private final int color = Codes.RED;
	
	public TBlock(int position, float x, float y) {
		super(position);
		ArrayList<Square> squares = generateSquares(x, y);
		setSquares(squares);
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y));
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y));
		squares.add(new Square(color, x + 2*Codes.BLOCK_SIZE, y));
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));

		return squares;
	}
	
	@Override
	public void rotate() {
		
	}
	
	@Override
	public void reverseRotate() {
		
	}

}

