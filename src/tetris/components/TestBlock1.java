package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Colors;

	public class TestBlock1 extends Block{
	private final int color = Colors.PURPLE;
	private float speed; 
	public TestBlock1(float x, float y, float speed) {
		super();
		this.speed = speed;
		setSquares(generateSquares(x,y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		for(int i = 0; i < 10; i++) {
			squares.add(new Square(color, x+25*i, y, speed));
			squares.add(new Square(color, x+25*i, y+25, speed));
			squares.add(new Square(color, x+25*i, y+50, speed));
		}

		return squares;
	}
}
