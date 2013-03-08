package tetris;

import resources.BlockInfo;
import resources.Measurements;
import tetris.components.*;

public class CollisionHandler {
	// private GamePlayState gamePS;
	private static Square[][] squares;

	/**
	 * The collision handler handles collisions between squares in the game. It
	 * can check if a block is colliding with squares in the game grid or if a
	 * block will collide with the squares in the grid while moving in a certain
	 * direction.
	 * 
	 * @param gps
	 *            The game play state where the grid squares are stored and all
	 *            logic in the game executed.
	 */
	public CollisionHandler(GamePlayState gps) {
		// gamePS = gps;
		squares = gps.getGridSquares();
	}

	/**
	 * Checks if the block will collide with squares in the game grid if it
	 * moves left. If a collision will happen, the method will return true.
	 * Otherwise, it will return false.
	 * 
	 * @param block
	 *            The block which movement is to be examined
	 * @return true if a collision will happen, false otherwise
	 */
	public boolean willCollideLeft(Block block) {
		int gridWidth = Measurements.GRID_WIDTH / BlockInfo.SIZE;
		int gridHeight = Measurements.GRID_HEIGHT / BlockInfo.SIZE;
		/*
		 * The check is done in the following way: for each square in the block,
		 * we will walk through every single square in the grid to see if moving
		 * the square in the block will cause a collision with the current grid
		 * square.
		 */
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getY() <= square.getY() + BlockInfo.SIZE
								&& gridSquare.getY() + BlockInfo.SIZE >= square
										.getY() + BlockInfo.SIZE
								|| gridSquare.getY() <= square.getY()
								&& gridSquare.getY() + BlockInfo.SIZE >= square
										.getY()) {
							if (gridSquare.getX() + BlockInfo.SIZE == square
									.getX()) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a block is colliding with either the edge of the grid or a
	 * square in the grid.
	 * 
	 * @param block
	 *            The block which is to be examined
	 * @return true if the block is colliding with elements in the grid, false
	 *         otherwise
	 */
	public static boolean isColliding(Block block) {
		int gridWidth = Measurements.GRID_WIDTH / BlockInfo.SIZE;
		int gridHeight = Measurements.GRID_HEIGHT / BlockInfo.SIZE;
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (square.getX() >= gridSquare.getX()-1 && square.getX() <= gridSquare.getX()+1) {
							if (square.getY() <= gridSquare.getY()
									+ BlockInfo.SIZE
									&& square.getY() >= gridSquare.getY()
									|| square.getY() + BlockInfo.SIZE <= gridSquare
											.getY() + BlockInfo.SIZE
									&& square.getY() + BlockInfo.SIZE >= gridSquare
											.getY())
								return true;

						}
					}
				}
			}
			if (square.getX() < Measurements.GRID_XSTART
					|| square.getX() + BlockInfo.SIZE > Measurements.GRID_XSTART
							+ Measurements.GRID_WIDTH) {
				return true;
			} else if (square.getY() < Measurements.GRID_YSTART
					|| square.getY() + BlockInfo.SIZE > Measurements.GRID_YSTART
							+ Measurements.GRID_HEIGHT) {
				return true;
			}

		}

		return false;
	}

	/**
	 * Checks if the block will collide with squares in the game grid if it
	 * moves right. If a collision will happen, the method will return true.
	 * Otherwise, it will return false.
	 * 
	 * @param block
	 *            The block which movement is to be examined
	 * @return true if a collision will happen, false otherwise
	 */
	public boolean willCollideRight(Block block) {
		int gridWidth = Measurements.GRID_WIDTH / BlockInfo.SIZE;
		int gridHeight = Measurements.GRID_HEIGHT / BlockInfo.SIZE;
		/*
		 * The check is done in the following way: for each square in the block,
		 * we will walk through every single square in the grid to see if moving
		 * the square in the block will cause a collision with the current grid
		 * square.
		 */
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getY() <= square.getY() + BlockInfo.SIZE
								&& gridSquare.getY() + BlockInfo.SIZE >= square
										.getY() + BlockInfo.SIZE
								|| gridSquare.getY() <= square.getY()
								&& gridSquare.getY() + BlockInfo.SIZE >= square
										.getY()) {
							if (gridSquare.getX() == square.getX()
									+ BlockInfo.SIZE) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the block will collide with squares in the game grid if it
	 * moves down. If a collision will happen, the distance left until the block
	 * collides with the square/squares below it will be returned. Otherwise, we
	 * will return 0.
	 * 
	 * @param block
	 *            The block which movement is to be examined
	 * @return 0 if no collision will occur, otherwise it will return the
	 *         distance left to the collision.
	 */
	public float willCollideDown(Block block) {
		int gridWidth = Measurements.GRID_WIDTH / BlockInfo.SIZE;
		int gridHeight = Measurements.GRID_HEIGHT / BlockInfo.SIZE;
		/*
		 * The check is done in the following way: for each square in the block,
		 * we will walk through every single square in the grid to see if moving
		 * the square in the block will cause a collision with the current grid
		 * square.
		 */
		for (Square square : block.getSquares()) {
			for (int i = 0; i < gridWidth; i++) {
				for (int j = 0; j < gridHeight; j++) {
					if (squares[i][j] != null) {
						Square gridSquare = squares[i][j];
						if (gridSquare.getX() == square.getX()
								&& !(gridSquare.getY() < square.getY())) {
							if (gridSquare.getY()-1 <= (square.getY()
									+ BlockInfo.SIZE + block.getSpeed())) {
								float distanceLeft = (gridSquare.getY() - (square
										.getY() + BlockInfo.SIZE));
								if (distanceLeft > 0)
									return distanceLeft;
								else
									return distanceLeft * -1;

							}
						}
					}
				}
			}
		}
		return 0f;
	}

}
