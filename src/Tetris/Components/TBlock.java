package Tetris.Components;

import java.util.ArrayList;

public class TBlock extends Block {

	private final int color = Codes.RED;
	ArrayList<Square> squares = new ArrayList<Square>();
	private Square mainSquare;

	public TBlock(int position, float x, float y) {
		super(position);
		setSquares(generateSquares(x, y));
	}

	public ArrayList<Square> generateSquares(float x, float y) {
		squares.add(new Square(color, x, y));
		squares.add(new Square(color, x, y + Codes.BLOCK_SIZE)); //main square
		squares.add(new Square(color, x + Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));
		squares.add(new Square(color, x - Codes.BLOCK_SIZE, y
				+ Codes.BLOCK_SIZE));

		return squares;
	}
	public void rotate() {
		editPosition((getPosition()+1) % 4);
	}
	
	public void reverseRotate() {
		editPosition((getPosition()+3) % 4);
	}
	
	private void editPosition(int i) {
		float x = squares.get(1).getX();
		float y = squares.get(1).getY();
		System.out.println(i);
	    switch(i) {
		    case 0:
				squares.get(0).setY(y).setX(x- Codes.BLOCK_SIZE);
			    squares.get(2).setY(y- Codes.BLOCK_SIZE).setX(x);
			    squares.get(3).setY(y).setX(x+ Codes.BLOCK_SIZE);
			  break;
		    case 1:
				squares.get(0).setY(y- Codes.BLOCK_SIZE).setX(x);
			    squares.get(2).setY(y).setX(x+ Codes.BLOCK_SIZE);
			    squares.get(3).setY(y+ Codes.BLOCK_SIZE).setX(x);
			  break;
		    case 2:
				squares.get(0).setY(y).setX(x- Codes.BLOCK_SIZE);
			    squares.get(2).setY(y).setX(x+ Codes.BLOCK_SIZE);
			    squares.get(3).setY(y+Codes.BLOCK_SIZE).setX(x);
		   	 break;
		    case 3:
				squares.get(0).setY(y).setX(x-Codes.BLOCK_SIZE);
			    squares.get(2).setY(y+Codes.BLOCK_SIZE).setX(x);
			    squares.get(3).setY(y-Codes.BLOCK_SIZE).setX(x);
			 break;
	    }
	    setPosition(i);
	}
}
