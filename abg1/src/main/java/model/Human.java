/**
 * 
 */
package model;

import Controller.GameEngine;

/**
 * @author philipp
 *
 */
public class Human extends Player{
	
	
	public Human(Board board, GameEngine engine)
	{
		super(board, engine);
	}
	
	public Dice rollDices()
	{
		return engine.requestHumanRollDice();
	}	
	
	public Dice rollSingleDice()
	{
		return engine.requestHumanRollSingleDice();
	}

	@Override
	public Place chooseStart() {
		return engine.requestHumanChooseStart();
	}
	
	@Override
	public Place chooseEnd() {
		return engine.requestHumanChooseEnd();
	}
	
	
	
	private float x;
	private float y;
	private float speed = 10f;
	private float rotation = 0f;
	private float damage = 2f;

	public Human(float x, float y) {
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
