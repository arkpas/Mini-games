package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Catch extends Application {
	public void start (Stage primaryStage) {
		Pane root = new Pane();
		Scene scene = new Scene (root,800,600);
		scene.setCursor(Cursor.NONE);
		primaryStage.setTitle("Pong the ball");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		
		Circle ball = new Circle(15);
		ball.setFill(Color.BLUE);
		ball.setLayoutX(scene.getWidth()/2);
		ball.setLayoutY(scene.getHeight()/4);
		
		Rectangle platform = new Rectangle(100,10);
		platform.setLayoutY(scene.getHeight()-50);
		
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent me) {
				platform.setLayoutX(me.getSceneX()-platform.getWidth()/2);
			}
		});
		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent me) {
				platform.setLayoutX(me.getSceneX()-platform.getWidth()/2);
			}
		});
		
		Label exitText = new Label ("Press ESC to exit");
		exitText.setTextFill(Color.BLACK);
		exitText.setTranslateY(scene.getHeight()-15);
		
		root.getChildren().addAll(ball,platform,exitText);
		primaryStage.show();
		
		VBox winRoot = new VBox();
		winRoot.setAlignment(Pos.CENTER);
		Scene winScene = new Scene(winRoot,800,600);
		winScene.getStylesheets().add("/application/application.css");
		Label winLabel = new Label("Congratulations!");
		winLabel.setId("winLabel");
		Button winButton = new Button("Go next");
		winRoot.getChildren().addAll(winLabel,winButton);
		
		
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(
				Duration.millis(20),
				new EventHandler<ActionEvent>() {
					double px=7.5;
					double py=5.5;
					int counter = 0;
					public void handle(ActionEvent e) {
						ball.setLayoutX(ball.getLayoutX()+px);
						ball.setLayoutY(ball.getLayoutY()+py);
						
						//bouncing from walls
						if (ball.getLayoutX()<=ball.getRadius() || 
								ball.getLayoutX()>=scene.getWidth()-ball.getRadius()) {
							
							px=-px;
						}
						
						if (ball.getLayoutY()<=root.getBoundsInLocal().getMinY()+ball.getRadius()) {
							
							py=-py;
						} 
						
						//now we will go with bouncing from platform, as you can see hitbox is bigger by 15 from both sides to make it little bit easier and smooth
						
						if (ball.getLayoutY()+ball.getRadius()>=platform.getLayoutY() &&
								ball.getLayoutX()>=platform.getLayoutX()-15 &&
									ball.getLayoutX()<=platform.getLayoutX()+platform.getWidth()+15) {
								
								py=-py;
								px=px*1.2;
								py=py*1.1;
								counter++;
								System.out.println(counter);
								
								switch (counter) {
								case 1: {platform.setFill(Color.RED);break;}
								case 2: {platform.setFill(Color.GREEN);break;}
								case 3: {platform.setFill(Color.HOTPINK);break;}
								case 4: {platform.setFill(Color.ORANGE);break;}
								case 5: {platform.setFill(Color.PURPLE);break;}
								case 6: {platform.setFill(Color.GREENYELLOW);break;}
								case 7: {platform.setFill(Color.LIGHTSTEELBLUE);break;}
								case 8: {primaryStage.setScene(winScene); break;}
								}
								
						}
						
						//going under platform resets the ball
					
						if (ball.getLayoutY()+ball.getRadius()>=platform.getLayoutY()+platform.getHeight()) { 
							ball.setLayoutX(scene.getWidth()/2);
							ball.setLayoutY(scene.getHeight()/4);
							counter=0;
							px=8;
							py=6;
							platform.setFill(Color.BLACK);
						}
						
						
					}	
				})
		);
		timeline.playFromStart();
		
		scene.setOnKeyPressed (new EventHandler<KeyEvent>() {
			public void handle (KeyEvent e) {
				if (e.getCode()==KeyCode.ESCAPE)
					Platform.runLater(new Runnable() {
						public void run () {
							new Main().start(primaryStage);
						}						
					});
			}
		});
		
		winButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent e) {
				Platform.runLater(new Runnable() {
					public void run () {
						new SpaceInvaders().start(primaryStage);
					}						
				});
			}
		});
		
		
	}
	public static void main (String args[]) {launch(args);}
}
