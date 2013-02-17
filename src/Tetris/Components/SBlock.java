package Tetris.Components;

import java.util.ArrayList;

public class SBlock extends Block {
	private final int color = Codes.RED;
	private ArrayList<Square> squares;
	private Square mainSquare;

	public SBlock(int position, float x, float y) {
		super(position);
		setSquares(generateSquares(x, y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		squares = new ArrayList<Square>();
		mainSquare = new Square(color, x + Codes.BLOCK_SIZE, y);
		squares.add(mainSquare);
		squares.add(new Square(color, x + 2 * Codes.BLOCK_SIZE, y));
		squares.add(new Square(color, x, y + Codes.BLOCK_SIZE));
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));
		return squares;
	}

	public void editPosition(int newPosition) {
		float x = mainSquare.getX();
		float y = mainSquare.getY();

		if (newPosition == 0) {
			squares.get(1).setX(x + Codes.BLOCK_SIZE).setY(y);
			squares.get(2).setX(x - Codes.BLOCK_SIZE)
					.setY(y + Codes.BLOCK_SIZE);
			squares.get(3).setX(x).setY(y + Codes.BLOCK_SIZE);
		} else if (newPosition == 1) {
			squares.get(1).setX(x).setY(y + Codes.BLOCK_SIZE);
			squares.get(2).setX(x - Codes.BLOCK_SIZE)
					.setY(y - Codes.BLOCK_SIZE);
			squares.get(3).setX(x - Codes.BLOCK_SIZE).setY(y);

		} else if (newPosition == 2) {
			squares.get(1).setX(x - Codes.BLOCK_SIZE).setY(y);
			squares.get(2).setX(x + Codes.BLOCK_SIZE)
					.setY(y - Codes.BLOCK_SIZE);
			squares.get(3).setX(x).setY(y - Codes.BLOCK_SIZE);

		} else if (newPosition == 3) {
			squares.get(1).setX(x).setY(y - Codes.BLOCK_SIZE);
			squares.get(2).setX(x + Codes.BLOCK_SIZE)
					.setY(y + Codes.BLOCK_SIZE);
			squares.get(3).setX(x + Codes.BLOCK_SIZE).setY(y);
		}

		setPosition(newPosition);
	}

	public void rotate() {
		editPosition((getPosition() + 1) % 4);
	}

	public void reverseRotate() {
		System.out.println(Math.abs(getPosition() - 1) % 4);
		editPosition(Math.abs(getPosition() - 1) % 4);
	}

}
