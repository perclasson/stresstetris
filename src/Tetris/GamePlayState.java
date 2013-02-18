package Tetris;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Tetris.Components.TBlock;
import Tetris.Components.Values;

public class GamePlayState extends BasicGameState {
	private int stateID = -1;
	private TBlock block;
	private Image frame, grid;

	public GamePlayState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		block = new TBlock(0, 276+4*Values.SIZE, 26);
		/* From left: 276 px
		   From top: 26 px
		   Height: 22 blocks = 550 px
		   Width: 10 blocks = 250px
		 */
		frame = new Image("images/frame.png");
		grid = new Image("images/grid.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		grid.draw(276,26);
		block.draw();
		frame.draw(0,0);
		
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
		} else if (input.isKeyPressed(Input.KEY_R)) {
			block.setPosition(276, 26);
		}
		block.moveDown();
	}

	@Override
	public int getID() {
		return stateID;
	}

}
