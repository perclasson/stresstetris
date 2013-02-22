package tetris.components;

import java.util.ArrayList;

import resources.BlockInfo;
import resources.Colors;
import resources.Measurements;


public class OBlock extends Block {

	private final int color = Colors.RED;
	private float speed; 
	
	/**
	 * Create a new O-shaped block
	 * @param x The starting x-coordinate for the block's main square
	 * @param y The starting y-coordinate for the block's main square
	 * @param speed The speed with which the block will be falling
	 */
	public OBlock(float x, float y, float speed) {
		super();
		this.speed = speed;
		setSquares(generateSquares(x,y));
	}
	
	public void setOnTop() {
		setSquares(generateSquares(276 + 4 * BlockInfo.SIZE,
				Measurements.GRID_YSTART - BlockInfo.SIZE));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y, speed));
		squares.add(new Square(color, x + BlockInfo.SIZE, y, speed));
		squares.add(new Square(color, x, y + BlockInfo.SIZE, speed));
		squares.add(new Square(color, x + BlockInfo.SIZE, y
				+ BlockInfo.SIZE, speed));

		return squares;
	}

}
