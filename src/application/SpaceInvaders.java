package application;



import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;



public class SpaceInvaders extends Application {
	int px=0;
	int bulletSpeed = 0;
	boolean reload = false;
	boolean left,right,space;
	
	public void start (Stage primaryStage) {
		
		Pane root = new Pane();
		Scene scene = new Scene(root,800,600);
		primaryStage.setTitle("Space invaders");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		
		Polygon ship = new Polygon();
		ship.getPoints().addAll(new Double[] {
	            0.0, 0.0,
	            -25.0, 50.0,
	            25.0, 50.0
		});
		ship.setFill(Color.BLUE);
		ship.setLayoutX(scene.getWidth()/2);
		ship.setLayoutY(scene.getHeight()-ship.getLayoutBounds().getHeight()-15);
		
		Rectangle bullet = new Rectangle (5,20);
		bullet.setFill(Color.RED);
		bullet.setVisible(false);
		
		Circle target = new Circle(20);
		target.setFill(Color.RED);
		target.setLayoutX(scene.getWidth()/2);
		target.setLayoutY(50);
		
		Label exitText = new Label ("Press ESC to exit");
		exitText.setTextFill(Color.BLACK);
		exitText.setTranslateY(scene.getHeight()-15);
		
		root.getChildren().addAll(ship,bullet,target,exitText);
		
		
		VBox winRoot = new VBox();
		winRoot.setAlignment(Pos.CENTER);
		Scene winScene = new Scene(winRoot,800,600);
		winScene.getStylesheets().add("/application/application.css");
		Label winLabel = new Label("Congratulations!");
		winLabel.setId("winLabel");
		Button winButton = new Button("Go next");
		winRoot.getChildren().addAll(winLabel,winButton);
		
		primaryStage.show();
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyevent) {
				switch(keyevent.getCode()) {
				case LEFT: left=true; break;
				case RIGHT: right=true; break;
				case SPACE: space=true; break;
				case ESCAPE: 
					Platform.runLater(new Runnable() {
					public void run () {
						new Main().start(primaryStage);
					}						
				});
				default: break;
				}
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case LEFT: left=false; break;
				case RIGHT: right=false; break;
				case SPACE: space=false; break;
				default: break;
				}
			
			}
		});
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(
				Duration.millis(16),
				new EventHandler<ActionEvent>() {
					double reloadTime=0.8;
					double targetPx=4;
					public void handle (ActionEvent a) {
						if (left)//checks if left is pressed
							px=-5;
						if (right) //checks if right is pressed
							px=5;
						if (space && !reload) { //checks if space is pressed
							bullet.setLayoutX(ship.getLayoutX()-bullet.getWidth()/2);
							bullet.setLayoutY(ship.getLayoutY()-bullet.getHeight());
							bullet.setVisible(true);
							bulletSpeed = 9;
							reload=true;
						}
						if (!left && !right) //when any of left and right are not pressed, ship stops
							px=0;
						
						if (ship.getLayoutX()<=ship.getLayoutBounds().getWidth()/2 && px<0) //checks if ship touches left edge
							px=0;
						if (ship.getLayoutX()>=scene.getWidth()-ship.getLayoutBounds().getWidth()/2 && px>0) //and right edge
							px=0;
						ship.setLayoutX(ship.getLayoutX()+px); // moves ship
						if (reload) {							//shoots
							bullet.setLayoutY(bullet.getLayoutY()-bulletSpeed);
							reloadTime=reloadTime - 0.010;
							if (reloadTime<=0) {
								reloadTime=0.8;
								reload=false;
							}
						}
						target.setLayoutX(target.getLayoutX()+targetPx);
						if (target.getLayoutX()<=target.getRadius() || target.getLayoutX()>=scene.getWidth()-target.getRadius())
							targetPx=-targetPx;
			
						if (bullet.getBoundsInParent().intersects(target.getBoundsInParent())) {
							bullet.setVisible(false);
							bullet.setLayoutY(-50);
							bulletSpeed=0;
							targetPx=targetPx*1.15;
						}
						if (targetPx>=10) {
							timeline.stop();
							primaryStage.setScene(winScene);
						
						}
					}
				}));
		timeline.play();
		
		winButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent e) {
				Platform.runLater(new Runnable() {
					public void run () {
						new Main().start(primaryStage);
					}						
				});
			}
		});
		
	}
	
	
	public static void main (String args[]) {launch(args);}
}
