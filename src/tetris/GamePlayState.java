package tetris;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.BlockInfo;
import resources.Measurements;
import tetris.components.Block;
import tetris.components.BlockBuilder;
import tetris.components.Square;

public class GamePlayState extends BasicGameState {
	private int stateID = 1;
	private int score;
	private Block block, nextBlock;
	private Image frame, grid, gameover;
	private Square[][] gridSquares;
	private float blockSpeed;
	private boolean paused;
	private BlockBuilder builder;
	private CollisionHandler collisionHandler;
	private static final int gridWidth = Measurements.GRID_WIDTH
			/ BlockInfo.SIZE;
	private static final int gridHeight = Measurements.GRID_HEIGHT
			/ BlockInfo.SIZE;
	private UnicodeFont font, bigFont;
	private int timeSinceGameOver;
	private boolean viewGameOverText;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
		gridSquares = new Square[gridWidth][gridHeight];
		blockSpeed = 1f;// The speed with which all blocks will be falling
		collisionHandler = new CollisionHandler(this);
		builder = new BlockBuilder(this);
		timeSinceGameOver = 0;
		viewGameOverText = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		paused = false;
		score = 0;
		block = builder.generateBlock();
		block.setOnTop();
		nextBlock = builder.generateBlock();
		frame = new Image("images/frame.png");
		grid = new Image("images/grid.png");
		gameover = new Image("images/gameover.png");

		font = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 20));
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addNeheGlyphs();
		font.loadGlyphs();

		bigFont = new UnicodeFont(new java.awt.Font("Verdana", Font.BOLD, 40));
		bigFont.getEffects().add(new ColorEffect(java.awt.Color.gray));
		bigFont.addNeheGlyphs();
		bigFont.loadGlyphs();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		grid.draw(276, 26);
		drawGridSquares();
		block.draw();
		frame.draw(0, 0);
		nextBlock.draw();
		font.drawString(592, 205, Integer.toString(score));

		if (viewGameOverText) {
			if (timeSinceGameOver > 1500) {
				gameover.setAlpha(((timeSinceGameOver - 1500) / 100) * 0.1f);
				gameover.draw();
			}
		}
	}

	/**
	 * Moves the active block across the grid. The block can only move right,
	 * left or down if it will not collide with squares already in the grid. If
	 * the block will collide while moving down, it is halted and all of it's
	 * squares are added to the grid on the positions they stopped moving. A new
	 * block is generated after the active one has stopped.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_P)) {
			pause();
		}
		if (!paused) {
			if (!block.isMoving()) {
				if (block.isInsideGrid()) {
					prepareNextBlock();
				} else {
					// Game is lost, view game over text for 3 seconds
					// and then go to next game state
					viewGameOverText = true;
					timeSinceGameOver += delta;
					if (timeSinceGameOver >= 3000) {
						game.enterState(Game.GAMEOVERSTATE);
					}
				}
			}
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
				/*
				 * } else if (input.isKeyPressed(Input.KEY_DOWN)) {
				 * block.reverseRotate();
				 */
			} else if (input.isKeyPressed(Input.KEY_R)) {
				block.setPosition(276, 26);
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				block.setSpeed(10);
			} else {
				block.setSpeed(getBlockSpeed());
			}

			float collisionDistance = collisionHandler.willCollideDown(block);
			if (collisionDistance > 0) {
				block.moveDown(collisionDistance); // The block is moved down
													// the
													// distance left to
													// collision
				block.halt();
			} else {
				block.moveDown();
			}

		}
	}

	public void prepareNextBlock() {
		addSquares(block);
		int fullRows = editRows();
		updateScore(fullRows);
		nextBlock.setOnTop();
		block = nextBlock;
		nextBlock = builder.generateBlock();
	}

	public void updateScore(int fullRow) {
		if (fullRow != 0)
			score += (100 * fullRow) + (Math.pow(10, fullRow - 1));
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

	public void pause() {
		if (paused) {
			paused = false;
		} else {
			paused = true;
		}
	}

	/**
	 * Adds squares from a block to the grid. The grid is a 10x22 matrix, and
	 * all the squares are added to appropriate coordinates using the size of
	 * the grid and the size of a square.
	 * 
	 * @param block
	 *            The block which squares will be added
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

	public int editRows() {
		boolean isFull = true;
		int numberOfFull = 0;

		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridWidth; j++) {
				if (gridSquares[j][i] == null) {
					isFull = false;
					break;
				}
			}
			if (isFull) {
				removeRow(i);
				moveSquares(i, 0);
				numberOfFull++;
			}
			isFull = true;
		}
		return numberOfFull;
	}

	public void removeRow(int row) {
		for (int i = 0; i < 10; i++) {
			gridSquares[i][row] = null;
		}
	}

	public void removeRows(ArrayList<Integer> rows) {
		for (Integer row : rows) {
			for (int i = 0; i < 10; i++) {
				gridSquares[i][row] = null;
			}
		}
	}

	public void moveSquares(int start, int stop) {
		for (int j = start; j >= stop; j--) {
			for (int i = 0; i < gridWidth; i++) {
				if (j >= 21)
					continue;
				if (gridSquares[i][j] != null) {
					gridSquares[i][j + 1] = gridSquares[i][j];
					gridSquares[i][j + 1].setY(gridSquares[i][j].getY()
							+ BlockInfo.SIZE);
					gridSquares[i][j] = null;
				}

			}
		}
	}

	public void moveAllSquaresDown() {
		for (int i = gridWidth - 1; i >= 0; i--) {
			for (int j = gridHeight - 2; j >= 0; j--) {
				if (gridSquares[i][j] != null && gridSquares[i][j + 1] != null) {
					gridSquares[i][j] = gridSquares[i][j + 1];
					gridSquares[i][j + 1].setY(gridSquares[i][j + 1].getY()
							+ BlockInfo.SIZE);
				}
			}
		}
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
