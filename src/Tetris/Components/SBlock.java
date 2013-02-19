package Tetris.Components;

import java.util.ArrayList;

public class SBlock extends Block {
	private final int color = Values.ORANGE;
	private ArrayList<Square> squares;
	private Square mainSquare;
	private float speed;

	public SBlock(int position, float x, float y, float speed) {
		super(position);
		this.speed = speed;
		setSquares(generateSquares(x, y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		squares = new ArrayList<Square>();
		mainSquare = new Square(color, x + Values.SIZE, y, speed);
		squares.add(mainSquare);
		squares.add(new Square(color, x + 2 * Values.SIZE, y, speed));
		squares.add(new Square(color, x, y + Values.SIZE, speed));
		squares.add(new Square(color, x + Values.SIZE, y
				+ Values.SIZE, speed));
		return squares;
	}
	
	public void setPosition(float x, float y) {
		setSquares(generateSquares(x, y));
	}

	public void editPosition(int newPosition) {
		float x = mainSquare.getX();
		float y = mainSquare.getY();

		if (newPosition == 0) {
			squares.get(1).setX(x + Values.SIZE).setY(y);
			squares.get(2).setX(x - Values.SIZE)
					.setY(y + Values.SIZE);
			squares.get(3).setX(x).setY(y + Values.SIZE);
		} else if (newPosition == 1) {
			squares.get(1).setX(x).setY(y + Values.SIZE);
			squares.get(2).setX(x - Values.SIZE)
					.setY(y - Values.SIZE);
			squares.get(3).setX(x - Values.SIZE).setY(y);

		} else if (newPosition == 2) {
			squares.get(1).setX(x - Values.SIZE).setY(y);
			squares.get(2).setX(x + Values.SIZE)
					.setY(y - Values.SIZE);
			squares.get(3).setX(x).setY(y - Values.SIZE);

		} else if (newPosition == 3) {
			squares.get(1).setX(x).setY(y - Values.SIZE);
			squares.get(2).setX(x + Values.SIZE)
					.setY(y + Values.SIZE);
			squares.get(3).setX(x + Values.SIZE).setY(y);
		}

		setRotation(newPosition);
	}

	public void rotate() {
		editPosition((getRotation() + 1) % 4);
	}

	public void reverseRotate() {
		editPosition((getRotation() + 3) % 4);
	}

}
