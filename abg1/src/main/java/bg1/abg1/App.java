package bg1.abg1;


//import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import Controller.*;



public class App extends Application /*implements Runnable*/ {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage pS) {
    	primaryStage = pS;
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        
        int width = 600;
        int height = 600;

        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillOval(30, 60, 100, 200);

        new AnimationTimer()
        {
    		long delta = 0l;
    		long lastTime =0;
        	public void handle(long currentNanoTime)
        	{
    			//long lastTime;// = currentNanoTime;

    			// Update the state (convert to seconds)
    			engine.update((float)(delta / 1000000000.0));
    			// Render the world
    			engine.render(gc);
    			
    			// Draw the entire results on the screen.
    			//appletGraphics.drawImage(screen, 0, 0, null);
    			//gc.drawImage(screen, 0, 0, null);

    			// Lock the frame rate
    			delta = currentNanoTime - lastTime;
    			lastTime = currentNanoTime;
//    			if (delta < 20000000L) {
 //   				try {
  //  					Thread.sleep((20000000L - delta) / 1000000L);
 //   				} catch (Exception e) {
    					// It's an interrupted exception, and nobody cares
 //  				}
   // 			}
        	}
        }.start();
        
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        root.getChildren().add(btn);
        Scene scene = new Scene(root, width, height);
        scene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        if ( e.getX() < 800 && e.getY() < 800  )
                        {
                            double x = 50 + 400 * Math.random(); 
                            double y = 50 + 400 * Math.random();
                            //engine.controller.onClick((int)e.getSceneX(),(int) e.getSceneY());
                            engine.onClick(e.getX(), e.getY());
                        
                        }
                        else;
                    }
                });

        primaryStage.setScene(scene);
        primaryStage.show();
        

		//new Thread(this).start();
    }

    private Stage primaryStage;
    private GraphicsContext gc;
    
    private GameEngine engine = new GameEngine();


	//public void run() {
		
		//setSize(480, 320); // For AppletViewer, remove later.

		// Set up the graphics stuff, double-buffering.
/*		BufferedImage screen = new BufferedImage(480, 320, BufferedImage.TYPE_INT_RGB);
		Graphics g = screen.getGraphics();
		//Graphics appletGraphics = gc; //lbl.getGraphics();

		long delta = 0l;
		
		// Game loop.
		while (true) {
			long lastTime = System.nanoTime();

			g.setColor(Color.black);
			g.fillRect(0, 0, 480, 320);

			// Update the state (convert to seconds)
			engine.update((float)(delta / 1000000000.0));
			// Render the world
			engine.render(g);
			
			// Draw the entire results on the screen.
			//appletGraphics.drawImage(screen, 0, 0, null);
			//gc.drawImage(screen, 0, 0, null);

			// Lock the frame rate
			delta = System.nanoTime() - lastTime;
			if (delta < 20000000L) {
				try {
					Thread.sleep((20000000L - delta) / 1000000L);
				} catch (Exception e) {
					// It's an interrupted exception, and nobody cares
				}
			}

			//if (!isActive()) {
			//	return;
			//}
		}*/
	//}

	public boolean handleEvent(Event e) {
		return engine.handleEvent(e);
	}
}