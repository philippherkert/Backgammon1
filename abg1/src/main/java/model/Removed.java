/**
 * 
 */
package model;

/**
 * @author philipp
 *
 */
public class Removed extends Place {

	Player player;
	public Removed(Player player)
	{
		this.player = player;
	}

	@Override
	public Player getOwner()
	{
		return player;
	}
}
