package Tetris.Components;

import java.util.ArrayList;

public class OBlock extends Block {

	private final int color = Values.RED;
	private float speed; 
	
	public OBlock(int position, float x, float y, float speed) {
		super(position);
		this.speed = speed;
		setSquares(generateSquares(x,y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y, speed));
		squares.add(new Square(color, x + Values.SIZE, y, speed));
		squares.add(new Square(color, x, y + Values.SIZE, speed));
		squares.add(new Square(color, x + Values.SIZE, y
				+ Values.SIZE, speed));

		return squares;
	}

}
