package com.example.connect4game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
/*Commented */


public class HelloApplication extends Application {

    public HelloController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       GridPane rootNode=fxmlLoader.load();
        controller=fxmlLoader.getController();
        controller.createPlayground();

        MenuBar m=createmenu();
        m.prefWidthProperty().bind(stage.widthProperty());
       Pane pane=(Pane) rootNode.getChildren().get(0);
        pane.getChildren().add(m);


        Scene scene = new Scene(rootNode);
        stage.setTitle("CONNECT 4 GAME");
        stage.setScene(scene);
        stage.getIcons().add(new Image("C4G.png"));

        stage.setResizable(false);
        stage.show();
    }

    private MenuBar createmenu() {
        Menu menu1=new Menu("File");
        MenuItem m1=new MenuItem("New Game");
        m1.setOnAction(e-> {
                    controller.resetGame();
                    controller.clear();});

        MenuItem m2=new MenuItem("Reset Game");
        m2.setOnAction(e-> controller.resetGame());

        SeparatorMenuItem sm=new SeparatorMenuItem();
        MenuItem m3=new MenuItem("Exit");
        m3.setOnAction(e-> exit());
        menu1.getItems().addAll(m1,m2,sm,m3);

        Menu menu2=new Menu("Help");
        MenuItem h1=new MenuItem("About Game");
        h1.setOnAction(e-> about());

        SeparatorMenuItem sm2=new SeparatorMenuItem();
        MenuItem h2=new MenuItem("About me");
        h2.setOnAction(e-> aboutme());
        menu2.getItems().addAll(h1,sm2,h2);

        MenuBar mb=new MenuBar();
        mb.getMenus().addAll(menu1,menu2);
        return mb;

    }

    private void aboutme() {
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("About Me");
        a.setHeaderText("I'm Alex");
        a.setContentText("Nothing to say right now!!!");
        a.show();
    }

    private void about() {
        Alert a=new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("About Game");
        a.setHeaderText("How to play?");
        a.setContentText("Connect Four is a two-player connection game in which the players first" +
                " choose a color and then take turns dropping colored discs from the top into a seven-column, " +
                "six-row vertically suspended grid. The pieces fall straight down, occupying the next available space " +
                "within the column. The objective of the game is to be the first to form a horizontal, vertical, or " +
                "diagonal line of four of one's own discs. " +
                "Connect Four is a solved game. The first player can always win by playing the right moves.");
        a.show();
    }

    private void exit() {
        Platform.exit();
        System.exit(0);
    }

    private void reset() {

    }

    public static void main(String[] args) {
        launch();
    }
}
