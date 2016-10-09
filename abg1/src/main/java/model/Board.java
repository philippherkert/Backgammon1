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
import java.util.Random;

import Controller.*;


public class Board {
	private List<Checker> checkers = new ArrayList<Checker>();
	private List<Place> places = new ArrayList<Place>();
	private List<Player> players = new ArrayList<Player>();
	
	private GameEngine engine;
	
	public Board(GameEngine engine)
	{
		this.engine = engine;
		initPlayers();
		initPlaces();
		initCheckers();
	}

	private void initPlaces()
	{
		for(int i = 0; i < 24; i++)
		{
			places.add(new Point());
		}
		places.add(new Bar(players.get(0)));
		places.add(new Bar(players.get(1)));
		places.add(new Removed(players.get(0)));
		places.add(new Removed(players.get(1)));
	}
	
	private void initPlayers()
	{
		players.add(new Human(this, engine));
		players.add(new AI(this, engine));
	}
	
	private void initCheckers()
	{
		placeNewCheckers(places.get(0), players.get(1), 2);
		placeNewCheckers(places.get(5), players.get(0), 5);
		placeNewCheckers(places.get(7), players.get(0), 3);
		placeNewCheckers(places.get(11), players.get(1), 5);
		placeNewCheckers(places.get(12), players.get(0), 5);
		placeNewCheckers(places.get(16), players.get(1), 3);
		placeNewCheckers(places.get(18), players.get(1), 5);
		placeNewCheckers(places.get(23), players.get(0), 2);
	}
	
	private void placeNewCheckers(Place place, Player player, int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			Checker c = new Checker(player, place);
			place.addChecker(c);
		}
	}

	public List<Checker> getCheckers() {
		return checkers;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public List<Player> getPlayers() {
		return players;
	}
	
	
	
	
	
	
	
	
	
	public static final int WIDTH = 480 / 32;
	public static final int HEIGHT = 320 / 32;
	
	private static Random random = new Random(System.currentTimeMillis());

	private Object[][] grid;
	private List<Point>	points = new ArrayList<Point>();
	//private List<Checker>		checkers = new ArrayList<Checker>();
	private Human human;
	
	public Board(Human human) {
		
		
		
		
		
		
		
		this.human = human;

		grid = new Object[HEIGHT][WIDTH];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				grid[j][i] = null;
			}
		}
		// add the human
		grid[(int)human.getY()][(int) human.getX()] = human;
		
		// add 5 obstacles and 5 enemies at random positions
		for (int i = 0; i < 5; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			while (grid[y][x] != null) {
				x = random.nextInt(WIDTH);
				y = random.nextInt(HEIGHT);
			}
			grid[y][x] = new Point(x, y);
			points.add((Point) grid[y][x]);
			while (grid[y][x] != null) {
				x = random.nextInt(WIDTH);
				y = random.nextInt(HEIGHT);
			}
			grid[y][x] = new Checker(x, y);
			checkers.add((Checker) grid[y][x]);
		}
	}

	public List<Point> getPointes() {
		return points;
	}
	public Human getHuman() {
		return human;
	}

	public Object[][] getGrid() {
		return grid;
	}
}
