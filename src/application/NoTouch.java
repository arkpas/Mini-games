package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class NoTouch extends Application {
	boolean circleMovable=true;
	
	public void start (Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root,800,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Can't touch this");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		
		
		Rectangle rect = new Rectangle(scene.getWidth()/2-8,200);
		rect.setTranslateX(scene.getWidth()-rect.getWidth());
		rect.setTranslateY(scene.getHeight()/2-rect.getHeight()/2);
		
		Rectangle rect2 = new Rectangle(scene.getWidth()/2-8,200);
		rect2.setTranslateX(0);
		rect2.setTranslateY(scene.getHeight()/2-rect2.getHeight()/2);
		
		Rectangle winRect = new Rectangle(50,50);
		winRect.setFill(Color.GREEN);
		winRect.setLayoutX(scene.getWidth()/2-winRect.getWidth()/2);
		winRect.setLayoutY(scene.getHeight()-winRect.getHeight());
		
		Circle circle = new Circle(5);
		circle.setFill(Color.GREEN);
		circle.setLayoutX(scene.getWidth()/2);
		circle.setLayoutY(20);
		
		Label exitText = new Label ("Press ESC to exit");
		exitText.setTextFill(Color.BLACK);
		exitText.setTranslateY(scene.getHeight()-15);
		
		root.getChildren().addAll(rect,rect2,winRect,circle,exitText);
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
				Duration.millis(1),
				new EventHandler<ActionEvent>() {
					public void handle (ActionEvent e) {
						if(circle.getBoundsInParent().intersects(rect.getBoundsInParent())||circle.getBoundsInParent().intersects(rect2.getBoundsInParent())) {
							circle.setLayoutX(scene.getWidth()/2);
							circle.setLayoutY(20);
							circleMovable=false;
						}
							
						else if (circle.getBoundsInParent().intersects(winRect.getBoundsInParent())) {
							primaryStage.setScene(winScene);
							timeline.stop();
						}

					
					}
				}
				));
		timeline.playFromStart();
		
		
		circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (circleMovable) {
				scene.setCursor(Cursor.NONE);
				circle.setLayoutX(e.getSceneX());
				circle.setLayoutY(e.getSceneY());
				}
			}
		});
		
		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				scene.setCursor(Cursor.DEFAULT);
				circleMovable=true;
			}
			});
		
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
						new Catch().start(primaryStage);
					}						
				});
			}
		});
		
		
	}
	
	public static void main (String args[]) {launch(args);}

	
}
