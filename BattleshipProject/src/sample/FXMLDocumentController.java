/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import com.sun.glass.ui.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Battleship;
import sample.Model.Model;
import sample.Model.Ship;
import sample.View.View;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author
 */
public class FXMLDocumentController implements Initializable {
    private static final String MESSAGE_ENTER_LETTER_AND_NUMBER_ON_THE_BOARD = "Oops, please enter a letter and a number on the board.";
    private static final String MESSAGE_NOT_ON_THE_BOARD = "Oops, that isn't on the board.";
    private static final String MESSAGE_OFF_THE_BOARD = "Oops, that's off the board!";

    private View view = new View();
    private Model model = new Model();
    private Battleship battleship = new Battleship();


    @FXML
    private Button fireButton;

    @FXML
    private Button pauseButton;


   @FXML Button resetButton;


    @FXML
    private GridPane gridPane;

    @FXML
    AnchorPane anchorPane;



    @FXML
    private TextField guessTextField;
    @FXML
    private Label messageLabel;


    @FXML
    Stage stage = new Stage();




    private int shipsSunk;


    private int guesses;


    private static final Integer STARTTIME = 0;
    private Timeline timeline;

    @FXML
    private Label timerLabel = new Label();
    @FXML
    private Label timerLabelSeconds = new Label();
    @FXML
    private Label timerLabelMinutes = new Label();
    private Integer timeSeconds = STARTTIME;
    private Integer timeMinutes = 0;

    boolean started = true;







    @FXML
    public void fireEvent() {


        timerLabel.setText("Timer");
        pauseButton.setDisable(false);


        if(started){
            Timer();
            started = false;

        }

        String guess = guessTextField.getText().trim();
        String message = checkValidInput(guess);
        if(!"".equals(message)) {
            Alert a = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
            a.show();
            return;
        }
        String location = view.getFirstNum()+""+view.getSecondNum();

        this.guesses++;
        boolean hit = fire(location);
        if (hit && this.shipsSunk == model.getNumShips()) {

            timeline.stop();
            //Timer will be stopped here

            view.updateMessageLabel("You sank all my battleships, in " + this.guesses + " guesses" , messageLabel);
        }
        guessTextField.setText("");
    }



    private void Timer(){
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = STARTTIME;

        // update timerLabel
       // timerLabelSeconds.setText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {

                                if(timeSeconds<=59 && timeMinutes==0){
                                    timerLabelMinutes.setText("00:");
                                    if(timeSeconds<10){
                                        timerLabelSeconds.setText("0"+timeSeconds);
                                    }else{
                                        timerLabelSeconds.setText(timeSeconds.toString());
                                    }
                                    timeSeconds++;
                                }else if(timeSeconds>=59){
                                    timeSeconds=0;

                                    timeMinutes=timeMinutes+1;
                                    if(timeMinutes<10){
                                        timerLabelMinutes.setText("0"+timeMinutes.toString()+":");
                                        if(timeSeconds<10){
                                            timerLabelSeconds.setText("0"+timeSeconds.toString());
                                        }else{
                                            timerLabelSeconds.setText(timeSeconds.toString());
                                        }

                                    }else{
                                        timerLabelMinutes.setText(timeMinutes.toString()+":");
                                        if(timeSeconds<10){
                                            timerLabelSeconds.setText("0"+timeSeconds.toString());
                                        }else{
                                            timerLabelSeconds.setText(timeSeconds.toString());
                                        }
                                    }
                                    timeSeconds++;
                                }else {

                                    // update timerLabel
                                    if(timeMinutes<10){
                                        timerLabelMinutes.setText("0"+timeMinutes.toString()+":");
                                        if(timeSeconds<10){
                                            timerLabelSeconds.setText("0"+timeSeconds.toString());
                                        }else{
                                            timerLabelSeconds.setText(timeSeconds.toString());
                                        }

                                    }else{
                                        timerLabelMinutes.setText(timeMinutes.toString()+":");
                                        if(timeSeconds<10){
                                            timerLabelSeconds.setText("0"+timeSeconds.toString());
                                        }else{
                                            timerLabelSeconds.setText(timeSeconds.toString());
                                        }
                                    }
                                    timeSeconds++;
                                }
                            }
                        }));
        timeline.playFromStart();
    }
    @FXML
    public void pauseEvent(){
       // timeline.pause();
        if(pauseButton.getText().equals("Pause Game")){
            timeline.pause();
            fireButton.setDisable(true);
            pauseButton.setText("Resume Game");
        }else if(pauseButton.getText().equals("Resume Game")){
            timeline.play();
            fireButton.setDisable(false);
            pauseButton.setText("Pause Game");
        }

    }
    @FXML
    public void resetEvent() throws Exception {

        Stage stage = (Stage) resetButton.getScene().getWindow();

        stage.close();
       // battleship.stop(stage);
        battleship.start(this.stage);
    }


    private boolean fire(String guess) {
        for (int i = 0; i < this.model.getNumShips(); i++) {
            Ship ship = this.model.getShips()[i];

            int index = ship.getLocations().indexOf(guess);

            if (ship.getHits() != null && index != -1 && "hit".equals(ship.getHits().get(index))) {
                view.updateMessageLabel("Oops, you already hit that location!" , messageLabel);
                return true;
            } else if (index >= 0) {
                List<String> hits = ship.getHits();
                if(hits == null) {
                    hits = new ArrayList<>();
                    hits.add("");
                    hits.add("");
                    hits.add("");
                }
                hits.set(index, "hit");
                ship.setHits(hits);
                view.displayHit(gridPane);
                view.updateMessageLabel("HIT!" , messageLabel);
                if (this.model.isSunk(ship)) {
                    view.updateMessageLabel("You sank my battleship!" , messageLabel);
                    this.shipsSunk++;
                }
                return true;
            }
        }
        view.displayMiss(gridPane);
        view.updateMessageLabel("You missed." , messageLabel);
        return false;
    }

    private String checkValidInput(String guess) {
        guess = guess.toUpperCase();
        String message = "";
        if(guess.isEmpty() || "".equals(guess) || guess.length() != 2) {
            return MESSAGE_ENTER_LETTER_AND_NUMBER_ON_THE_BOARD;
        }
        List<Character> chars = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G');
        char ch = guess.charAt(0);
        char ch2 = guess.charAt(1);

        int num;
        try {
            num = Integer.parseInt(String.valueOf(ch2));
        } catch (Exception e) {
            return MESSAGE_NOT_ON_THE_BOARD;
        }
        if(num > 6) {
            return MESSAGE_OFF_THE_BOARD;
        }
        if(!chars.contains(ch)) {
            return MESSAGE_OFF_THE_BOARD;
        }
        view.setFirstNum(chars.indexOf(ch));
        view.setSecondNum(num);
        return message;
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.model.setBoardSize(7);
        this.model.setShipLength(3);
        this.model.setNumShips(3);
        this.model.generateShipLocations();
//        for (int i = 0; i < ships.length; i++) {
//            Ship ship = ships[i];
//            for(String loc:ship.getLocations()) {
//                System.out.println(loc);
//            }
////            for(String hit:ship.getHits()) {
////                System.out.println(hit);
////            }
//        }
    }
}

