/**
 * 
 */
package view;

/**
 * @author philipp
 *
 */
//import java.awt.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleBoardRenderer implements Renderer {

	private Board board;
	
	public SimpleBoardRenderer(Board board) {
		this.board = board;
	}
	
	private void drawPoint(GraphicsContext g, double x, double y, double width, double height, Color c)
	{
		g.setFill(c);
		g.fillPolygon(new double[]{x, x + width, x + 0.5 * width}, new double[]{y, y, height}, 3);
	}
	private void drawSixPoints(
			GraphicsContext g, 
			double x, 
			double y, 
			double width, 
			double height, 
			Color c1, 
			Color c2
			)
	{
		Color c = c1;
		for(int i = 0; i < 6; i++)
		{
			drawPoint(g, x + i*width/6, y, width/6, height, c);
			if(c == c1)
				c = c2;
			else 
				c = c1;
		}
	}
	
	private void drawGrid(
			GraphicsContext g, 
			double width, 
			double height, 
			double thickness, 
			double offArea, 
			Color c
			)
	{
		g.setFill(c);
		g.fillRect(0, height/2-thickness/2, width - offArea, thickness);
		g.fillRect((width-offArea)/2-thickness/2, 0, thickness, height);
		g.fillRect(width-offArea, 0, offArea, height);
	}
	
	private double getPointXPosition(
			int index, 
			double width, 
			double height, 
			double thickness, 
			double offArea
			)
	{
		double triangleWidth = (width-offArea-thickness)/12;
		if(index >= 0 && index < 6)
			return width - offArea - triangleWidth/2 - triangleWidth * index;
		else if(index > 5 && index < 12)
			return triangleWidth/2 + triangleWidth * (11 - index);
		else if(index > 11 && index < 18)
			return triangleWidth/2 + triangleWidth * (index - 12);
		else if(index > 17 && index < 24)
			return width - offArea - triangleWidth/2 - triangleWidth * (23 - index);
		else return 0;
	}
	private double getPointYPosition(
			int index, 
			int i, 
			int n, 
			double width, 
			double height, 
			double thickness, 
			double offArea, 
			double triangleHeight, 
			double checkerSize
			)
	{
		if(index >= 0 && index < 12){
			if(n*checkerSize > triangleHeight && n > 1)
			{
				return height - (triangleHeight - checkerSize) / (n - 1) * i - 
					checkerSize;
			}
			else
			{
				return height - checkerSize * (i + 1);
			}
		}
		else if(index > 11 && index < 24)
		{
			if(n*checkerSize > triangleHeight && n > 1)
			{
				return (triangleHeight - checkerSize) / (n - 1) * i;
			}
			else
			{
				return checkerSize * i;
			}
		}
		else return 0;
	}
	
	private void drawCheckerAtPoint(
			GraphicsContext g, 
			Point p,
			int index, 
			double width, 
			double height, 
			double thickness, 
			double offArea, 
			double triangleHeight, 
			double checkerSize,
			Color c
			)
	{
		g.setFill(c);
		for(int i = 0; i < p.size(); i++)
		{
			g.fillOval(
					getPointXPosition(
							index, 
							width, 
							height, 
							thickness, 
							offArea
							) - checkerSize/2, 
					getPointYPosition(
							index, 
							i, 
							p.size(), 
							width, 
							height, 
							thickness, 
							offArea, 
							triangleHeight, 
							checkerSize
							), 
					checkerSize, checkerSize);
		}
	}
	
	//@Override
	public void render(GraphicsContext g) {
		double width = g.getCanvas().getWidth();
		double height = g.getCanvas().getHeight();

		double thickness = 10;
		double offArea = 100;
		double middle = 80;
		double checkerSize = 40;
		double triangleHeight = height * 0.3;
		g.setFill(new Color(0.19f, 0.14f, 0.0f, 1));
		g.fillRect(0, 0, width, height);
		// render the Points
		//g.setFill(new Color(0.7f, 0.5f, 0.1f, 1));
		//g.fillPolygon(new double[]{0,50,25}, new double[]{0,0,150}, 3);
		// Links unten
		drawSixPoints(g, 0, height, (width-offArea-thickness)/2, height-height*0.3, new Color(0.7f, 0.5f, 0.1f, 1), new Color(0.1f, 0.7f, 0.5f, 1));
		// Rechts unten
		drawSixPoints(g, (width-offArea+thickness)/2, height, (width-offArea-thickness)/2, height-height*0.3, new Color(0.7f, 0.5f, 0.1f, 1), new Color(0.1f, 0.7f, 0.5f, 1));
		// Links oben
		drawSixPoints(g, 0, 0, (width-offArea-thickness)/2, height*0.3, new Color(0.7f, 0.5f, 0.1f, 1), new Color(0.1f, 0.7f, 0.5f, 1));
		// Rechts oben
		drawSixPoints(g, (width-offArea+thickness)/2, 0, (width-offArea-thickness)/2, height*0.3, new Color(0.7f, 0.5f, 0.1f, 1), new Color(0.1f, 0.7f, 0.5f, 1));
		drawGrid(g, width, height, thickness, offArea, new Color(0.57f, 0.42f, 0.0f, 1));
		

		g.setFill(new Color(0.19f, 0.14f, 0.0f, 1));
		// Oberer Checker Behälter
		g.fillRect(width - offArea/2 - checkerSize/2, thickness, checkerSize, height/2 - 2*thickness - middle/2);
		// Unterer Checker Behälter
		g.fillRect(width - offArea/2 - checkerSize/2, height/2 + thickness + middle/2, checkerSize, height/2 - 2*thickness - middle/2);
		// Würfel Bereich
		g.fillRect(width - offArea + thickness, height/2 - middle/2, offArea - 2*thickness, middle);
		
		// Checker zeichnen
		List<Place> places = board.getPlaces();
		Color p1 = new Color(0.9, 0.9, 0.96, 1);
		Color p2 = new Color(0.04, 0.04, 0.1, 1);
		for(int i = 0; i < places.size(); i++)
		{
			Color c = p2;
			if(places.get(i).getOwner() == board.getPlayers().get(0))
			{
				c = p1;
			}
			if(places.get(i) instanceof Point)
			{
				Point p = (Point)places.get(i);
				drawCheckerAtPoint(
						g,
						p,
						i,
						width, 
						height, 
						thickness, 
						offArea, 
						triangleHeight, 
						checkerSize,
						c);
			}
		}
		
		
		
		
		/*		// render the grid
		int cellSize = 32; // hard coded
		g.setFill(new Color(0, 0.5f, 0, 0.75f));
		for (int i = 0; i <= 300; i++) {
			g.fillRect(i * cellSize, 0, i * cellSize, 300 * cellSize);
			if (i <= 300)
				g.fillRect(0, i * cellSize, 300 * cellSize, i * cellSize);
		}
		
		// render the Points
		g.setFill(new Color(0f, 0f, 1f, 1));
		for (Point points : board.getPointes()) {
			int x = (int) (points.getX() * cellSize) + 2;
			int y = (int) (points.getY() * cellSize) + 2;
			g.fillRect(x, y, cellSize - 4, cellSize - 4);
		}
		
		// render the Checker
		g.setFill(new Color(1f, 0, 0, 1));
		for (Checker checker : board.getCheckers()) {
			int x = (int) (checker.getX() * cellSize);
			int y = (int) (checker.getY() * cellSize);
			g.fillOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
		}
		
		// render player human
		g.setFill(new Color(0, 1f, 0, 1f));
		Human human = board.getHuman();
		int x = (int) (human.getX() * cellSize);
		int y = (int) (human.getY() * cellSize);
		g.fillOval(x + 2, y + 2, cellSize - 4, cellSize - 4);
		// render square on droid
		//g.setColor(new Color(0.7f, 0.5f, 0f));
		g.fillRect(x + 10, y + 10, cellSize - 20, cellSize - 20);
*/
	}
}
