/**
 * 
 */
package view;

/**
 * @author philipp
 *
 */
import java.awt.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

public interface Renderer {
	public void render(GraphicsContext g); //Graphics g);
	//public void render(Canvas g); //Graphics g);
}
