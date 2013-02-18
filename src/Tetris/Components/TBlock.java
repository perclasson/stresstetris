package Tetris.Components;

import java.util.ArrayList;

public class TBlock extends Block {

	private final int color = Values.GREEN;
	ArrayList<Square> squares;

	public TBlock(int position, float x, float y) {
		super(position);
		setSquares(generateSquares(x, y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		squares = new ArrayList<Square>();
		squares.add(new Square(color, x, y));
		squares.add(new Square(color, x, y + Values.SIZE)); // main square
		squares.add(new Square(color, x + Values.SIZE, y + Values.SIZE));
		squares.add(new Square(color, x - Values.SIZE, y + Values.SIZE));

		return squares;
	}

	public void setPosition(float x, float y) {
		setSquares(generateSquares(x, y));
	}

	public void rotate() {
		if(canRotate()) {
			editPosition((getRotation() + 1) % 4);
		}
	}

	public void reverseRotate() {
		if(canRotate()) {
			editPosition((getRotation() + 3) % 4);
		}
	}

	private void editPosition(int i) {
		float x = squares.get(1).getX();
		float y = squares.get(1).getY();
		switch (i) {
		case 0:
			squares.get(0).setY(y).setX(x - Values.SIZE);
			squares.get(2).setY(y - Values.SIZE).setX(x);
			squares.get(3).setY(y).setX(x + Values.SIZE);
			break;
		case 1:
			squares.get(0).setY(y - Values.SIZE).setX(x);
			squares.get(2).setY(y).setX(x + Values.SIZE);
			squares.get(3).setY(y + Values.SIZE).setX(x);
			break;
		case 2:
			squares.get(0).setY(y).setX(x - Values.SIZE);
			squares.get(2).setY(y).setX(x + Values.SIZE);
			squares.get(3).setY(y + Values.SIZE).setX(x);
			break;
		case 3:
			squares.get(0).setY(y).setX(x - Values.SIZE);
			squares.get(2).setY(y + Values.SIZE).setX(x);
			squares.get(3).setY(y - Values.SIZE).setX(x);
			break;
		}
		setRotation(i);
	}
	
	public boolean canRotate() {
		int touching = 0;
		for(Square square : squares) {
			float x = square.getX();
			float y = square.getY();
			if(x + Values.SIZE == Values.GRID_XSTART+Values.GRID_WIDTH) {
				touching++;
			}
			if(x == Values.GRID_XSTART) {
				touching++;
			}
			if(y > Values.GRID_YSTART+Values.GRID_HEIGHT) {
				touching++;
			}
		}
		if(touching < 3) {
			return true;
		}
		return false;
	}
}
