package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import java.util.Random;

public class Find extends Application {
	
	int tries=0;
	
	public void start (Stage primaryStage) {
		
		Random randomizer = new Random();
		Group root = new Group();
		Scene scene = new Scene(root,800,600,Color.BLACK);
		scene.getStylesheets().add("/application/application.css");
		primaryStage.setScene(scene);
		
		Group circles = createCircles(30);
		Rectangle gradient = new Rectangle(scene.getWidth(),scene.getHeight());
		gradient.setFill(new LinearGradient(0,1,1,0,true,CycleMethod.NO_CYCLE,new Stop[] {
			new Stop(0,Color.web("blue")),
			new Stop(0.25,Color.web("green")),
			new Stop(0.50,Color.web("yellow")),
			new Stop(0.75,Color.web("green")),
			new Stop(1,Color.web("blue"))
			}));
		gradient.widthProperty().bind(scene.widthProperty());
		gradient.heightProperty().bind(scene.heightProperty());
		Label napis = new Label();
		napis.setText(Character.toString((char)(randomizer.nextInt(26)+65))+Character.toString((char)(randomizer.nextInt(26)+65))+Character.toString((char)(randomizer.nextInt(26)+65)));
		napis.setId("password");
		Label exitText = new Label ("Press ESC to exit");
		exitText.setTextFill(Color.WHITE);
		exitText.setTranslateY(scene.getHeight()-15);
		napis.setTranslateX(randomizer.nextDouble()*(scene.getWidth()-100)+50);
		napis.setTranslateY(randomizer.nextDouble()*(scene.getHeight()-100)+50);
		Group blendMode = new Group(new Group(new Rectangle(scene.getWidth(),scene.getHeight(),Color.BLACK),circles),napis,gradient);
		gradient.setBlendMode(BlendMode.OVERLAY);
		napis.setBlendMode(BlendMode.OVERLAY);
		TextField field = new TextField();
		field.setMaxWidth(50);
		field.setTranslateY(scene.getHeight()-25);
		field.setTranslateX(scene.getWidth()/2);

		
		root.getChildren().addAll(blendMode,exitText,field);
		

		primaryStage.show();
		
		VBox winRoot = new VBox();
		winRoot.setAlignment(Pos.CENTER);
		Scene winScene = new Scene(winRoot,800,600);
		winScene.getStylesheets().add("/application/application.css");
		Label winLabel = new Label("Congratulations!");
		winLabel.setId("winLabel");
		Button winButton = new Button("Go next");
		winRoot.getChildren().addAll(winLabel,winButton);
		
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				circles.setTranslateX(event.getSceneX());
				circles.setTranslateY(event.getSceneY());
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
		
		field.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle (KeyEvent e) {
				if(e.getCode()==KeyCode.ENTER) {
					if (field.getText().toUpperCase().equals(napis.getText().toUpperCase()))
						primaryStage.setScene(winScene);
					else
						tries++;
					field.clear();
					if (tries>=3) {
						Platform.runLater(new Runnable() {
							public void run () {
								new Main().start(primaryStage);
							}						
						});
					}
				}
			}
		});
	
		
		winButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent e) {
				Platform.runLater(new Runnable() {
					public void run () {
						new Shooting().start(primaryStage);
					}						
				});
			}
		});
		
	}		
		
	
	
	public static void main (String args[]) {launch(args);}
	
	public Group createCircles(int qty) {
		Group circles = new Group();
		for (int i=0;i<qty;i++) {
			Circle circle = new Circle();
			circle.setRadius(50);
			circle.setFill(Color.web("white",0.02));
			circle.setStrokeType(StrokeType.OUTSIDE);
			circle.setStroke(Color.web("white",0.16));
			circle.setStrokeWidth(5);
			circles.getChildren().add(circle);
		}
		return circles;
	}
}