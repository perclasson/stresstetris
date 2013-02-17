package Tetris;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Tetris.Components.SBlock;

public class GamePlayState extends BasicGameState {
	private int stateID = -1;
	private SBlock block;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		block = new SBlock(0, 100, 100);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		block.draw();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			block.moveLeft();
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			block.moveRight();
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			block.rotate();
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			block.reverseRotate();
		}
		block.moveDown();
	}

	@Override
	public int getID() {
		return stateID;
	}

}
