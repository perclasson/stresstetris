package Tetris;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Tetris.Components.*;

public class GamePlayState extends BasicGameState {
	private int stateID = -1;
	private Block block;
	private Image frame, grid;
	private Square[][] gridSquares;
	private float blockSpeed;
	private CollisionHandler collisionHandler;
	private static final int gridWidth = Values.GRID_WIDTH / Values.SIZE;
	private static final int gridHeight = Values.GRID_HEIGHT / Values.SIZE;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
		gridSquares = new Square[gridWidth][gridHeight];
		blockSpeed = 3f;
		collisionHandler = new CollisionHandler(this);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println(blockSpeed);
		//testFill();
		block = new SBlock(0, 276 + 4 * Values.SIZE, 26, blockSpeed);
		/*
		 * From left: 276 px From top: 26 px Height: 22 blocks = 550 px Width:
		 * 10 blocks = 250px
		 */
		frame = new Image("images/frame.png");
		grid = new Image("images/grid.png");
	}
	
	public void testFill() {
		for(int i = 0; i < 22; i++) {
			gridSquares[0][i] = new Square(Values.RED, Values.GRID_XSTART, Values.GRID_YSTART+i*25, 0);
		}
		for(int j = 0; j < 22; j++) {
			gridSquares[9][j] = new Square(Values.ORANGE, Values.GRID_XSTART+9*Values.SIZE, +Values.GRID_YSTART+j*25, 0);
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

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (!block.isMoving()) {
			System.out.println("blocket rörde sig inte...");
			addSquares(block);
			block = new SBlock(0, 276 + 4 * Values.SIZE, 26, blockSpeed);
		}
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			if (collisionHandler.willCollideLeft(block) == 0) {
				block.moveLeft();
			}
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			if (collisionHandler.willCollideRight(block) == 0) {
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
		System.out.println(collisionDistance);
		if (collisionDistance > 0) {
			block.moveDown(collisionDistance);
			block.halt();
		} else {
			block.moveDown();
		}
		//block.moveDown();
	}

	public void drawGridSquares() {
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				if (gridSquares[i][j] != null) {
					gridSquares[i][j].draw();
				}
			}
		}
	}

	public void addSquares(Block block) {
		for (Square square : block.getSquares()) {
			int xIndex = (int) (square.getX() - Values.GRID_XSTART)
					/ Values.SIZE;
			int yIndex = (int) (square.getY() - Values.GRID_YSTART)
					/ Values.SIZE;
			/*System.out.println(xIndex);
			System.out.println(yIndex);

			//square.setX(xIndex*Values.SIZE+Values.GRID_XSTART);
			//square.setY(yIndex*Values.SIZE+Values.GRID_YSTART);
			System.out.println(square.getX());
			System.out.println(square.getY());*/
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
