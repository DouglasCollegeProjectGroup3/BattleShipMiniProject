package sample.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.FXMLDocumentController;

public class View {





//    private GridPane gridPane;

    private double firstNum;
    private double secondNum;

//    @FXML
//    private Label messageLabel;

    public double getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(double firstNum) {
        this.firstNum = firstNum;
    }

    public double getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(double secondNum) {
        this.secondNum = secondNum;
    }

    //View
    public void displayHit(GridPane gridPane) {
        final HBox pictureRegionForShip = new HBox();
        final ImageView imv2 = new ImageView();
        final Image shipImage = new Image(FXMLDocumentController.class.getResourceAsStream("images/ship.png"), 100, 100, true, true);
        imv2.setImage(shipImage);
        imv2.setFitHeight(25);
        imv2.setFitWidth(60);
        pictureRegionForShip.getChildren().add(imv2);
        gridPane.add(pictureRegionForShip, (int)secondNum, (int)firstNum);
    }

    //View
    public void displayMiss(GridPane gridPane) {
        final HBox pictureRegionForMiss = new HBox();
        final ImageView imv = new ImageView();

        final Image missImage = new Image(FXMLDocumentController.class.getResourceAsStream("images/miss.png"), 100, 100, true, true);
        imv.setImage(missImage);
        imv.setFitHeight(25);
        imv.setFitWidth(60);
        pictureRegionForMiss.getChildren().add(imv);
        gridPane.add(pictureRegionForMiss, (int)secondNum, (int)firstNum);



    }


    //View
    public void updateMessageLabel(String message ,Label messageLabel) {
        messageLabel.setText(message);
    }


}
