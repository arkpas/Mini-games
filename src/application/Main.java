package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	public void start (Stage primaryStage) {
		
		VBox root = new VBox();
		root.setPrefSize(800,600);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root,800,600);
		scene.getStylesheets().add("/application/application.css");
		primaryStage.setTitle("Funny apps");
		primaryStage.setScene(scene);
		
		VBox buttons = new VBox(10);
		Label start = menuLabel("START");
		Label exit = menuLabel("EXIT");
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(start,exit);
		
		
		root.getChildren().add(buttons);
		primaryStage.show();
		
		start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent e) {
				Platform.runLater(new Runnable() {
					public void run () {
						new Find().start(primaryStage);
					}
				});
			}
		});
		
		
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle (MouseEvent e) {
				Platform.exit();
			}
		});
	}
	
	public static void main (String args[]) {launch(args);}

	public Label menuLabel (String name) {
		Label label = new Label(name);
		label.setId("menuLabel");
		
		label.setOnMouseEntered (new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				label.setId("menuLabelHover");
			}
		});
		
		label.setOnMouseExited (new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				label.setId("menuLabel");
			}
		});
		return label;
	}
	
	
}
