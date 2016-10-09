/**
 * 
 */
package model;

import java.util.List;

/**
 * @author philipp
 *
 */

import Controller.*;


public class AI extends Player {
	
	public AI(Board board, GameEngine engine)
	{
		super(board, engine);
	}

	@Override
	public Dice rollDices()
	{
		return engine.rollDices();
	}

	@Override
	public Dice rollSingleDice()
	{
		return engine.rollSingleDice();
	}
	

	@Override
	public Place chooseStart() {
		// TODO Add AI
		List<Place> places = engine.getLegalStartPlaces();
		if(places != null)
		{
			return places.get(0);
		}
		// TODO Add valid code
		return null;
	}
	

	@Override
	public Place chooseEnd() {
		// TODO Add AI
		List<Place> places = engine.getLegalEndPlaces();
		if(places != null)
		{
			return places.get(0);
		}
		return null;
	}
	
	
	
	
	private float x;
	private float y;
	private float speed = 10f;
	private float rotation = 0f;
	private float damage = 2f;

	public AI(float x, float y) {
		super(x, y);
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
