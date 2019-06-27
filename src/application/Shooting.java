package application;

import java.util.Random;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Shooting extends Application {
	public void start (Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene (root,800,600);
		scene.setCursor(Cursor.CROSSHAIR);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Shooting");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		Random randomizer = new Random();
		
		Circle target = new Circle(20);
		target.setFill(Color.RED);
		target.setTranslateX(target.getRadius());
		target.setTranslateY(target.getRadius());
		Group playground = new Group();
		Image backgroundImage = new Image(getClass().getResourceAsStream("/resources/forest.jpg"));
		ImageView background = new ImageView(backgroundImage);
		Label exitText = new Label ("Press ESC to exit");
		exitText.setTextFill(Color.WHITE);
		exitText.setTranslateY(scene.getHeight()-15);
		playground.getChildren().addAll(background,target,exitText);
		
		root.getChildren().add(playground);
		primaryStage.show();
		
		VBox winRoot = new VBox();
		winRoot.setAlignment(Pos.CENTER);
		Scene winScene = new Scene(winRoot,800,600);
		winScene.getStylesheets().add("/application/application.css");
		Label winLabel = new Label("Congratulations!");
		winLabel.setId("winLabel");
		Button winButton = new Button("Go next");
		winRoot.getChildren().addAll(winLabel,winButton);
		
		target.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent e) {
				target.setRadius(target.getRadius()*0.90);
				target.setTranslateX(randomizer.nextDouble()*(scene.getWidth()-target.getRadius()*2)+target.getRadius());
				target.setTranslateY(randomizer.nextDouble()*(scene.getHeight()-target.getRadius()*2)+target.getRadius());
				if (target.getRadius()<2)
					primaryStage.setScene(winScene);
			}
		});
		
		background.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent e) {
				target.setRadius(target.getRadius()*1.10);
				if (target.getRadius()>27) {
					Platform.runLater(new Runnable() {
						public void run () {
							new Main().start(primaryStage);
						}						
					});
				}
				
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
						new NoTouch().start(primaryStage);
					}						
				});
			}
		});
		
	}
	
	public static void main (String args[]) {launch(args);}

}
