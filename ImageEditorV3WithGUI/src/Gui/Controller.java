package Gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.net.URL;

import ImageEditorV3.packWork.EditorMain;

public class Controller implements Initializable {
    @FXML
    private Button exitButton, startButton;
    @FXML
    private AnchorPane backgrAP;
    @FXML
    private Label inputLabel, currentVallueLabel, outputLabel, watermarkLabel;
    @FXML
    private TextField inputNameTextField, outputNameTextField, waterMarkTextField;
    @FXML
    private CheckBox keepImgCheckBox, deleteMetadataCheckBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider valueSlider;
    @FXML
    private ComboBox<String> placementComboBox;
    @FXML
    private RadioButton greyScale1Button, greyScale2Button, sepiaButton, mirrorByYButton, paintButton, brightnessButton, saturationButton, gammaButton, contrastButton, redFilterButton, greenFilterButton, blueFilterButton, invertedSepiaButton, cloudyButton, invertButton, mirrorByXButton, mirrorByXYButton, limitColorsButton, outlineButton, wmKeepColorButton, wmBlendIn1Button, wmBlendIn2Button, wmStandOutButton;

    Stage stage;
    private String inputName;
    private String outputName;
    private int sliderVal = 0;

    public void startAction(ActionEvent e) {
        System.out.println("Controller: Starting image conversion.");
        inputName = inputNameTextField.getText();
        outputName = outputNameTextField.getText();
        int inputFilter = this.getFilterNr();
        boolean keepMetadata;
        System.out.println("Controller: input file name: " + inputName + " | output file name: " + outputName);
        Path newPath = null;
        if (deleteMetadataCheckBox.isSelected())
            keepMetadata = false;
        else
            keepMetadata = true;
        if (!keepImgCheckBox.isSelected())
            newPath = Paths.get("src/ImageEditorV3/Images/Input/" + inputName);
        else
            newPath = Paths.get("src/ImageEditorV3/Images/Output/" + outputName);
        if (Files.exists(newPath) && newPath.toString().endsWith(".jpg")) {
            Image startImg = null;
            if (!keepImgCheckBox.isSelected()) {
                startImg = new Image("ImageEditorV3/Images/Input/" + inputName);
                inputName = "src/ImageEditorV3/Images/Input/" + inputName;
                outputName = "src/ImageEditorV3/Images/Output/" + outputName;
            } else {
                startImg = new Image("ImageEditorV3/Images/Output/" + outputName);
                outputName = "src/ImageEditorV3/Images/Output/" + outputName;
                inputName = outputName;
            }
            imageView.setImage(startImg);
            System.out.println("Controller: Found path for input image: " + newPath);
            inputLabel.setText("Image found.");
            inputLabel.setTextFill(Color.web("#005b96"));
            if (outputName.contains(".jpg")) {
                EditorMain editorMain = new EditorMain();
                try {
                    if (inputFilter > 19 && inputFilter < 24) {
                        Path watermarkPath = Paths.get("src/ImageEditorV3/Images/Watermark/" + waterMarkTextField.getText());
                        if (Files.exists(watermarkPath) && watermarkPath.toString().endsWith(".png")) {
                            watermarkLabel.setText("Watermark found");
                            watermarkLabel.setTextFill(Color.web("#005b96"));
                            editorMain.runApp(inputName, outputName, 20, sliderVal, keepMetadata, "src/ImageEditorV3/Images/Watermark/" + waterMarkTextField.getText(), this.getPlacement(), inputFilter - 19, imageView);
                        } else {
                            watermarkLabel.setTextFill(Color.FIREBRICK);
                            if (!watermarkPath.toString().endsWith(".png"))
                                watermarkLabel.setText("I prefer .png files");
                            else
                                watermarkLabel.setText("Watermark not found");
                        }
                    } else
                        editorMain.runApp(inputName, outputName, inputFilter, sliderVal, keepMetadata, "", 0, 0, imageView);

                } catch (Exception exception) {
                    System.out.println("Controller: startButton exception: " + exception);
                }
                outputLabel.setText("File format accepted.");
                outputLabel.setTextFill(Color.web("#005b96"));
            } else {
                outputLabel.setText("I prefer .jpg files.");
                outputLabel.setTextFill(Color.FIREBRICK);
            }

        } else {
            inputLabel.setText("Error: image not found!");
            inputLabel.setTextFill(Color.FIREBRICK);
        }
    }

    
    public void exitAction(ActionEvent e) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("You're about to exit");
        alert.setTitle("Exit");
        alert.setContentText("Are you sure?");
        alert.setGraphic(new ImageView(new Image(Main.class.getResource("guiImages/ques.png").toExternalForm(), 48, 48, true, true)));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("imgEditorStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        dialogPane.setPrefHeight(190);
        alert.initOwner(exitButton.getScene().getWindow());
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) backgrAP.getScene().getWindow();
            System.out.println("Exiting app");
            stage.close();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        placementComboBox.setItems(FXCollections.observableArrayList("Top left", "Center", "Bottom right"));
        valueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                sliderVal = (int) valueSlider.getValue();
                currentVallueLabel.setText(Integer.toString(sliderVal));
            }
        });
    }

    private int getPlacement() {
        System.out.println("placementComboBox.getValue() = " + placementComboBox.getValue());
        if (placementComboBox.getValue() == "Bottom right")
            return 3;
        else if (placementComboBox.getValue() == "Center")
            return 2;
        else
            return 1;
    }

    private int getFilterNr() {
        if (this.invertButton.isSelected())
            return 1;
        else if (this.greyScale1Button.isSelected())
            return 2;
        else if (this.greyScale2Button.isSelected())
            return 3;
        else if (this.brightnessButton.isSelected())
            return 4;
        else if (this.saturationButton.isSelected())
            return 5;
        else if (this.gammaButton.isSelected())
            return 6;
        else if (this.sepiaButton.isSelected())
            return 7;
        else if (this.contrastButton.isSelected())
            return 8;
        else if (this.mirrorByYButton.isSelected())
            return 9;
        else if (this.paintButton.isSelected())
            return 10;
        else if (this.redFilterButton.isSelected())
            return 11;
        else if (this.greenFilterButton.isSelected())
            return 12;
        else if (this.blueFilterButton.isSelected())
            return 13;
        else if (this.invertedSepiaButton.isSelected())
            return 14;
        else if (this.cloudyButton.isSelected())
            return 15;
        else if (this.mirrorByXButton.isSelected())
            return 16;
        else if (this.mirrorByXYButton.isSelected())
            return 17;
        else if (this.limitColorsButton.isSelected())
            return 18;
        else if (this.outlineButton.isSelected())
            return 19;
        else if (this.wmKeepColorButton.isSelected())
            return 20;
        else if (this.wmBlendIn1Button.isSelected())
            return 21;
        else if (this.wmBlendIn2Button.isSelected())
            return 22;
        else if (this.wmStandOutButton.isSelected())
            return 23;
        return 1;
    }
}
