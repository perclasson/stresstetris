package tetris.components;

import java.util.Random;

import resources.BlockInfo;
import resources.Measurements;
import tetris.GamePlayState;

public class BlockBuilder {
	private float blockSpeed;
	private float x, y;
	public BlockBuilder(GamePlayState gps) {
		blockSpeed = gps.getBlockSpeed();
		x = Measurements.GRID_XSTART+Measurements.GRID_WIDTH + 4*BlockInfo.SIZE;
		y = Measurements.GRID_YSTART + 4*BlockInfo.SIZE;
	}

	public Block generateBlock() {
		Random random = new Random();
		int block = random.nextInt(7);
		switch (block) {
		case 0:
			return new IBlock(x,y-10, blockSpeed);
		case 1:
			return new JBlock(x+10,y, blockSpeed);
		case 2:
			return new LBlock(x+10,y, blockSpeed);
		case 3:
			return new OBlock(x,y-BlockInfo.SIZE, blockSpeed);
		case 4:
			return new SBlock(x-10,y-BlockInfo.SIZE, blockSpeed);
		case 5:
			return new TBlock(x+10,y, blockSpeed);
		case 6:
			return new ZBlock(x+10,y-BlockInfo.SIZE, blockSpeed);
		}
		return null;
	}
}
