/**
 * 
 */
package Controller;

import java.awt.Event;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.scene.canvas.GraphicsContext;
import model.*;
import view.*;

/**
 * @author philipp
 *
 */

abstract class Call<T> implements Callable<T>
{
	abstract T call(double x, double y);
}

public class GameEngine {

	private Player currentPlayer;
	private State currentState;
	private Dice dice;
	
	private Dice HumanDice;
	private Place HumanStartPlace;
	private Place HumanEndPlace;
	
	private Place StartPlace;
	private Place EndPlace;
	
	private Call<Integer> onClick;
	
	
	private Board board;
	private Human human;
	private Renderer renderer;
	public BoardController controller;
	
	public GameEngine() {
		board = new Board(this);
		currentPlayer = board.getPlayers().get(0);
		currentState = new Start1(this);
		dice = new Dice();
		
		
		
		
		
		
		
		
		
		
		
		human = new Human(2, 2);
		// position droid in the middle
		//Person.setX(Board.WIDTH / 2);
		//Person.setY(Board.HEIGHT / 2);
		//board = new Board(human);
		
		// setup renderer (view)
		renderer = new SimpleBoardRenderer(board);
		// setup controller
		controller = new BoardController(board);
	}
	
	public Dice getDice()
	{
		return dice;
	}
	
	public void nextPlayer()
	{
		int currentPlayerIndex = board.getPlayers().indexOf(currentPlayer);
		int numberOfPlayers = board.getPlayers().size();
		currentPlayer = board.getPlayers().get((currentPlayerIndex + 1) % numberOfPlayers);
	}
	
	public Player getPlayer()
	{
		return currentPlayer;
	}
	
	public Integer onClick(double x, double y)
	{
		try {
			return onClick.call(x, y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Integer onClickRoleDice()
	{
		dice.role();
		HumanDice = dice;
		System.out.println("roling!");
		Checker c = board.getPlaces().get(5).removeChecker();
		board.getPlaces().get(1).addChecker(c);
		return 0;
	}
	public Integer onClickRoleSingleDice()
	{
		dice.roleSingleNumber();
		HumanDice = dice;
		System.out.println("roleSingleNumber!");
		return 0;
	}
	
	public Integer onClickChooseStart(double x, double y)
	{
		HumanStartPlace = board.getPlaces().get(0);
		System.out.println("start!");
		return 0;
	}
	public Integer onClickChooseEnd(double x, double y)
	{
		HumanEndPlace = board.getPlaces().get(6);
		System.out.println("end!");
		return 0;
	}
	
	/** handle the Event passed from the main applet **/
	public boolean handleEvent(Event e) {
		switch (e.id) {
		case Event.KEY_PRESS:
		case Event.KEY_ACTION:
			// key pressed
			break;
		case Event.KEY_RELEASE:
			// key released
			break;
		case Event.MOUSE_DOWN:
			// mouse button pressed
			break;
		case Event.MOUSE_UP:
			// mouse button released
			controller.onClick(e.x, e.y);
			break;
		case Event.MOUSE_MOVE:
			// mouse is being moved
			break;
		case Event.MOUSE_DRAG:
			// mouse is being dragged (button pressed)
			break;
		}
		return false;
	}
	
	
	public Dice rollDices()
	{
		dice.role();
		return dice;
	}

	public Dice rollSingleDice()
	{
		dice.roleSingleNumber();
		return dice;
	}
	
	public Dice requestHumanRollDice()
	{
		onClick = new Call<Integer>(){
			public Integer call(double x, double y) {
				return onClickRoleDice();
			}
			public Integer call(){
				return onClickRoleDice();
			};
			};
		Dice temp = HumanDice;
		HumanDice = null;
		return temp;
	}
	
	public Dice requestHumanRollSingleDice()
	{
		onClick = new Call<Integer>(){
			public Integer call(double x, double y) {
				return onClickRoleSingleDice();
			}
			public Integer call(){
				return onClickRoleSingleDice();
			};
			};
		Dice temp = HumanDice;
		HumanDice = null;
		return temp;
	}
	
	public Place requestHumanChooseStart()
	{
		onClick = new Call<Integer>(){
			public Integer call(double x, double y) {
				return onClickChooseStart(x, y);
			}
			public Integer call(){
				return onClickRoleDice();
			};
			};
		Place temp = HumanStartPlace;
		HumanStartPlace = null;
		return temp;
	}
	
	public Place requestHumanChooseEnd()
	{
		onClick = new Call<Integer>(){
			public Integer call(double x, double y) {
				return onClickChooseEnd(x, y);
			}
			public Integer call(){
				return onClickRoleDice();
			};
			};
		Place temp = HumanEndPlace;
		HumanEndPlace = null;
		return temp;
	}
	
	public void setState(State state)
	{
		currentState = state;
	}
	
	
	public boolean allCheckersInHomeField()
	{
		boolean allCheckersAreHome = true;
		List<Checker> c = board.getCheckers();
		for(int i = 0; i < c.size(); i++)
		{
			if(c.get(i).getPlayer() == currentPlayer)
			{
				if(c.get(i).getPlace() instanceof Bar)
				{
					allCheckersAreHome = false;
				}
				else if(c.get(i).getPlace() instanceof Point) 
				{
					if(currentPlayer == board.getPlayers().get(0))
					{
						if(i > 5)
						{
							allCheckersAreHome = false;
						}
					}
					else if(currentPlayer == board.getPlayers().get(1))
					{
						if(i < 18)
						{
							allCheckersAreHome = false;
						}
					}
				}
			}
		}
		return allCheckersAreHome;
	}
	
	
	public List<Place> getLegalStartPlaces()
	{
		// TODO gültige Startplätze für aktuellen Spieler ermmitteln
		List<Place> result = new ArrayList<Place>();
		List<Place> p = board.getPlaces();
		int sign = -1;
		if(currentPlayer == board.getPlayers().get(0))
		{
			sign = 1;
		}
		boolean allHome = allCheckersInHomeField();
		for(int i = 0; i < p.size(); i++)
		{
			if(p.get(i) instanceof Point)
			{
				Point point = (Point) p.get(i);
				if(point.size() > 0 && point.getOwner() == currentPlayer)
				{
					if(i + sign * dice.getValue(0).intValue() < 24 && 
							i + sign * dice.getValue(0).intValue() > 0)
					{
						if(isLegalEndPlace(p.get(i + sign * dice.getValue(0).intValue())))
						{
							result.add(p.get(i + sign * dice.getValue(0).intValue()));
						}
					}
					if(i + sign * dice.getValue(1).intValue() < 24 && 
							i + sign * dice.getValue(1).intValue() > 0)
					{
						if(isLegalEndPlace(p.get(i + sign * dice.getValue(1).intValue())))
						{
							result.add(p.get(i + sign * dice.getValue(1).intValue()));
						}
						// TODO Diese methode komplett überarbeiten
					}
				}
			}
		}
		return null;
	}
	
	public boolean isLegalEndPlace(Place place)
	{
		return false;
	}

	public List<Place> getLegalEndPlaces()
	{
		return getLegalEndPlaces(StartPlace);
	}
	
	public List<Place> getLegalEndPlaces(Place startPlace)
	{
		// TODO gültige Endplätze für aktuellen Spieler ermmitteln
		return null;
	}
	
	public boolean setStartPlace(Place p)
	{
		List<Place> places = getLegalStartPlaces();
		// Der gewählte Platz ist gültig
		if(places != null && places.contains(p))
		{
			StartPlace = p;
			return true;
		}
		return false;
	}

	public boolean setEndPlace(Place p)
	{
		List<Place> places = getLegalEndPlaces();
		// Der gewählte Platz ist gültig
		if(places != null && places.contains(p))
		{
			StartPlace = p;
			return true;
		}
		return false;
	}
	
	
	/** the update method with the deltaTime in seconds **/
	public void update(float deltaTime) {
		currentState.nextAction();
		controller.update(deltaTime);
	}
	
	/** this will render the whole world **/
	public void render(GraphicsContext g) {
		renderer.render(g);
	}

	public void executeMove() {
		// TODO Auto-generated method stub
		
	}

	public boolean won() {
		// TODO Auto-generated method stub
		return false;
	}

	public void finishGame() {
		// TODO Auto-generated method stub
		
	}

	public boolean diceLeft() {
		// TODO Auto-generated method stub
		return false;
	}
}
