package Tetris;

import Tetris.Components.*;

public class CollisionHandler {
	private GamePlayState gamePS;
	private Square[][] squares;

	public CollisionHandler(GamePlayState gps) {
		gamePS = gps;
		squares = gps.getGridSquares();
	}

	public int willCollideLeft(Block block) {
		int gridWidth = Values.GRID_WIDTH / Values.SIZE;
		int gridHeight = Values.GRID_HEIGHT / Values.SIZE;
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getY() <= square.getY()+Values.SIZE && gridSquare.getY()+Values.SIZE >= square.getY()+Values.SIZE
								|| gridSquare.getY() <= square.getY() && gridSquare.getY()+Values.SIZE >= square.getY()) {
							if (gridSquare.getX()+Values.SIZE == square.getX()) {
								return Values.SIZE;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public int willCollideRight(Block block) {
		int gridWidth = Values.GRID_WIDTH / Values.SIZE;
		int gridHeight = Values.GRID_HEIGHT / Values.SIZE;
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getY() <= square.getY()+Values.SIZE && gridSquare.getY()+Values.SIZE >= square.getY()+Values.SIZE
								|| gridSquare.getY() <= square.getY() && gridSquare.getY()+Values.SIZE >= square.getY()) {
							if (gridSquare.getX() == square.getX()
									+ Values.SIZE) {
								return Values.SIZE;
							}
						}
					}
				}
			}
		}
		return 0;
	}

	public float willCollideDown(Block block) {
		int gridWidth = Values.GRID_WIDTH / Values.SIZE;
		int gridHeight = Values.GRID_HEIGHT / Values.SIZE;
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getX() == square.getX() && !(gridSquare.getY() < square.getY())) {
							if (gridSquare.getY() <= (square.getY()+Values.SIZE
									+ gamePS.getBlockSpeed())) {
								float distanceLeft = (float)(gridSquare.getY()-(square.getY()+Values.SIZE));
								if(distanceLeft >0)
									return distanceLeft;
								else
									return distanceLeft*-1;
								
										
							}
						}
					}
				}
			}
		}
		return 0f;
	}

}
