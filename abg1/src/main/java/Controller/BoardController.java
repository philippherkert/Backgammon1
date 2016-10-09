/**
 * 
 */
package Controller;


import model.*;

/**
 * @author philipp
 *
 */
public class BoardController {
	private static final int unit = 32;
	private Board board;
	
	/** the target cell **/
	private float targetX, targetY;
	/** true if the droid moves **/
	private boolean moving = false;
	
	public BoardController(Board board) {
		this.board = board;
	}
	
	public void update(float delta) {
		Human human = board.getHuman();
		if (moving) {
			// move on X
			int bearing = 1;
			if (human.getX() > targetX) {
				bearing = -1;
			} 
			if (human.getX() != targetX) {
				human.setX(human.getX() + bearing * human.getSpeed() * delta);
				// check if arrived
				if ((human.getX() < targetX && bearing == -1)
						|| (human.getX() > targetX && bearing == 1)) human.setX(targetX);
			}
			// move on Y
			bearing = 1;
			if (human.getY() > targetY) {
				bearing = -1;
			} 
			if (human.getY() != targetY) {
				human.setY(human.getY() + bearing * human.getSpeed() * delta);
				// check if arrived
				if ((human.getY() < targetY && bearing == -1)
						|| (human.getY() > targetY && bearing == 1)) human.setY(targetY);
			}
			// check if arrived
			if (human.getX() == targetX && human.getY() == targetY) 
				moving = false;
		}
	}
	
	// * Input events ----------
	
	/** triggered with the coordinates every click **/
	public boolean onClick(int x, int y) {
		targetX = 0; //x / unit;
		targetY = 0; //y / unit;
		if (board.getGrid()[(int) targetY][(int) targetX] == null) {
			// start moving the droid towards the target
			moving = true;
			return true;
		}
		return false;
	}
}
