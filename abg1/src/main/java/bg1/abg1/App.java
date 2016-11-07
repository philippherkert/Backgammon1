package bg1.abg1;


//import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AI;
import model.Dice;
import Controller.*;

import javafx.scene.paint.Color;

public class App extends Application /*implements Runnable*/ {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage pS) {
    	
    	// load the image
        Image image = new Image("file:res/sample_7.png");

        // simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);

        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        ImageView iv2 = new ImageView();
        iv2.setImage(image);
        iv2.setFitWidth(100);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        // defines a viewport into the source image (achieving a "zoom" effect) and
        // displays it rotated
        ImageView iv3 = new ImageView();
        iv3.setImage(image);
        Rectangle2D viewportRect = new Rectangle2D(40, 35, 110, 110);
        iv3.setViewport(viewportRect);
        iv3.setRotate(90);
    	
        iv2.setMouseTransparent(true);
        iv2.setX(-100);
    	
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

        Circle circle = new Circle(200, 100, 50, new Color(0.3, 0.5, 0.7, 1));
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
        	    180.0, 60.0,
        	    300.0, 120.0,
        	    180.0, 120.0 });
        
        circle.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                            //engine.onClick(e.getX(), e.getY());
                    	System.out.println("I was hit!");
                    }
                });
        
        Boolean mouseMove = new Boolean(false);
        
        
        class h{
        	public boolean b = false;
        }
        final h mm = new h();
        
        polygon.setOnMousePressed(
        		new EventHandler<MouseEvent>()
        		{
        			public void handle(MouseEvent e)
        			{
        				mm.b = true;
        				circle.setEffect(new BoxBlur(10, 10, 3));
        			}
        		});

        polygon.setOnMouseReleased(
        		new EventHandler<MouseEvent>()
        		{
        			public void handle(MouseEvent e)
        			{
        				mm.b = false;
        				circle.setEffect(new BoxBlur(0, 0, 0));
        			}
        		});
        
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillOval(30, 60, 100, 200);

        
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, // set start position at 0
                new KeyValue(circle.translateXProperty(), 0),
                new KeyValue(circle.translateYProperty(), 0)
            ),
            new KeyFrame(new Duration(40000), // set end position at 40s
                new KeyValue(circle.translateXProperty(), -100),
                new KeyValue(circle.translateYProperty(), -100)
            )
        );
        // play 40s of animation
        timeline.play();
        
        
        
        
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
        
        root.getChildren().add(circle);
        root.getChildren().add(polygon);
        
        root.getChildren().add(iv2);
        
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