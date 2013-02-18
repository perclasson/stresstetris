package Tetris.Components;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Square {
	private Image image;
	private float x, y;

	public Square(int color, float x, float y) {
		try {
			Image allColors = new Image("images/sprites.png");
			image = allColors.getSubImage(color*Values.SIZE, 0, Values.SIZE, Values.SIZE); 
			
			this.x = x;
			this.y = y;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Square setX(float x) {
		this.x = x;
		return this;
	}

	public Square setY(float y) {
		this.y = y;
		return this;
	}

	public void draw() {
		image.draw(x, y);
	}

	public void moveLeft() {
		x -= Values.SIZE;
	}

	public void moveRight() {
		x += Values.SIZE;
	}

	public void moveDown() {
		y += 0.5f;
	}

	public void moveUp() {
		y -= 3;
	}
	
	public boolean canMoveUp() {
		if(y - Values.SIZE < Values.GRID_YSTART) {
			return false;
		}
		return true;
	}
	public boolean canMoveDown() {
		if(y + Values.SIZE > Values.GRID_YSTART+Values.GRID_HEIGHT) {
			return false;
		}
		return true;
	}
	public boolean canMoveRight() {
		if(x + 2*Values.SIZE > Values.GRID_XSTART+Values.GRID_WIDTH) {
			return false;
		}
		return true;
	}
	public boolean canMoveLeft() {
		if(x - Values.SIZE < Values.GRID_XSTART) {
			return false;
		}
		return true;
	}
}
