package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image("Gui/guiImages/Logo.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setWidth(900);
            primaryStage.setHeight(830);
            primaryStage.setResizable(false);
            String cssStyle = this.getClass().getResource("imgEditorStyle.css").toExternalForm();
            scene.getStylesheets().add(cssStyle);
            primaryStage.setTitle("Editor");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                exitAction(primaryStage);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void exitAction(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure?");
        alert.setGraphic(new ImageView(new Image(Main.class.getResource("guiImages/ques.png").toExternalForm(), 48, 48, true, true)));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("imgEditorStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        dialogPane.setPrefHeight(190);
        alert.initOwner(stage);
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exiting app");
            stage.close();
        }
    }
}