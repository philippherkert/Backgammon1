/**
 * 
 */
package model;

/**
 * @author philipp
 *
 */


import java.util.ArrayList;
import java.util.List;



public class Checker {
	private Place place;
	private Player player;
	
	
	public Checker(Player player, Place place)
	{
		this.player = player;
		this.place = place;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Place getPlace()
	{
		return place;
	}
	
	
	
	
	
	
	
	
	
	
	private float x;
	private float y;
	private float speed = 10f;
	private float rotation = 0f;
	private float damage = 2f;
	
	public Checker(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}
}
