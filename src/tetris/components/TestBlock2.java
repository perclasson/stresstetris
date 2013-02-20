package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Colors;

	public class TestBlock2 extends Block{
	private final int color = Colors.YELLOW;
	private float speed; 
	public TestBlock2(float x, float y, float speed) {
		super();
		this.speed = speed;
		setSquares(generateSquares(x,y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
			squares.add(new Square(color, x, y, speed));

		return squares;
	}
}
