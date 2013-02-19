package tetris;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.BlockInfo;
import resources.Colors;
import resources.Measurements;
import tetris.components.*;


public class GamePlayState extends BasicGameState {
	private int stateID = -1;
	private Block block;
	private Image frame, grid;
	private Square[][] gridSquares;
	private float blockSpeed;
	private CollisionHandler collisionHandler;
	private static final int gridWidth = Measurements.GRID_WIDTH / BlockInfo.SIZE;
	private static final int gridHeight = Measurements.GRID_HEIGHT / BlockInfo.SIZE;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
		gridSquares = new Square[gridWidth][gridHeight]; //A matrix containing all squares in the grid
		blockSpeed = 2f;//The speed with which all blocks will be falling
		collisionHandler = new CollisionHandler(this);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println(blockSpeed);
		//testFill();
		block = new TBlock(276 + 4 * BlockInfo.SIZE, 26, blockSpeed);
		/*
		 * From left: 276 px From top: 26 px Height: 22 blocks = 550 px Width:
		 * 10 blocks = 250px
		 */
		frame = new Image("images/frame.png");
		grid = new Image("images/grid.png");
	}
	
	/**
	 * Fills the right and left borders with squares (for testing purposes)
	 */
	public void testFill() {
		for(int i = 0; i < 22; i++) {
			gridSquares[0][i] = new Square(Colors.RED, Measurements.GRID_XSTART, Measurements.GRID_YSTART+i*25, 0);
		}
		for(int j = 0; j < 22; j++) {
			gridSquares[9][j] = new Square(Colors.ORANGE, Measurements.GRID_XSTART+9*BlockInfo.SIZE, +Measurements.GRID_YSTART+j*25, 0);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		grid.draw(276, 26);
		drawGridSquares();
		block.draw();
		frame.draw(0, 0);
	}

	/**
	 * Moves the active block across the grid. The block can only move right, left or down if
	 * it will not collide with squares already in the grid. If the block will collide while
	 * moving down, it is halted and all of it's squares are added to the grid on the 
	 * positions they stopped moving. A new block is generated after the active one has
	 * stopped.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (!block.isMoving()) {
			addSquares(block);
			block = new TBlock(276 + 4 * BlockInfo.SIZE, 26, blockSpeed);
		}
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			if (!collisionHandler.willCollideLeft(block)) {
				block.moveLeft();
			}
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			if (!collisionHandler.willCollideRight(block)) {
				block.moveRight();
			}
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			block.rotate();
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			block.reverseRotate();
		} else if (input.isKeyPressed(Input.KEY_R)) {
			block.setPosition(276, 26);
		}
		
		float collisionDistance = collisionHandler.willCollideDown(block);
		if (collisionDistance > 0) {
			block.moveDown(collisionDistance); //The block is moved down the distance left to collision
			block.halt();
		} else {
			block.moveDown();
		}
	}

	/**
	 * Draws all the squares on the grid.
	 */
	public void drawGridSquares() {
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				if (gridSquares[i][j] != null) {
					gridSquares[i][j].draw();
				}
			}
		}
	}

	/**
	 * Adds squares from a block to the grid. The grid is a 10x22 matrix, and all the squares
	 * are added to appropriate coordinates using the size of the grid and the size of a square.
	 * @param block The block which squares will be added
	 */
	public void addSquares(Block block) {
		for (Square square : block.getSquares()) {
			int xIndex = (int) (square.getX() - Measurements.GRID_XSTART)
					/ BlockInfo.SIZE;
			int yIndex = (int) (square.getY() - Measurements.GRID_YSTART)
					/ BlockInfo.SIZE;

			gridSquares[xIndex][yIndex] = square;
		}
	}

	public void removeRow(int i) {

	}

	public Square[][] getGridSquares() {
		return gridSquares;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public float getBlockSpeed() {
		return blockSpeed;
	}
}
