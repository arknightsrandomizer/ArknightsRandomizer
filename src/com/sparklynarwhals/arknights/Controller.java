package com.sparklynarwhals.arknights;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    Model model;

    private ObservableList<Operator> chosenOperators;
    private ObservableList<Operator> allOperators;
    private ObservableList<Operator> availableOperators;

    private SimpleStringProperty chosenStage;
    private ObservableList<String> allStages;
    private ObservableList<String> availableStages;

    private SimpleStringProperty chosenChallenge;
    private ObservableList<String> allChallenges;
    private ObservableList<String> availableChallenges;

    private SimpleStringProperty difficulty;

    @FXML
    Button randomizeButton;
    @FXML
    ListView operatorList;
    @FXML
    Label operationLabel;
    @FXML
    Label chosenChallengeLabel;
    @FXML
    Button howToButton;
    @FXML
    Label diffLabel;

    @FXML
    ListView allOperatorList;
    @FXML
    ListView availableOperatorList;
    @FXML
    Button operatorButton;
    @FXML
    TextField operatorTextField;

    @FXML
    ListView allStagesList;
    @FXML
    ListView availableStagesList;
    @FXML
    Button stageButton;
    @FXML
    TextField stageTextField;

    @FXML
    ListView allChallengesList;
    @FXML
    ListView availableChallengesList;
    @FXML
    Button challengeButton;
    @FXML
    TextField challengeTextField;

    @FXML
    CheckBox easyMode;
    @FXML
    Spinner<Integer> operatorCountSpinner;
    @FXML
    CheckBox noMedic;
    @FXML
    CheckBox noGuard;
    @FXML
    CheckBox noDefender;
    @FXML
    CheckBox noSniper;
    @FXML
    CheckBox noCaster;
    @FXML
    CheckBox noVanguard;
    @FXML
    CheckBox noHighStar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // OPERATOR AND STAGE SELECTION
        this.chosenOperators = FXCollections.observableArrayList();
        this.allOperators = FXCollections.observableArrayList();
        this.availableOperators = FXCollections.observableArrayList();

        this.chosenStage = new SimpleStringProperty("");
        this.allStages = FXCollections.observableArrayList();
        this.availableStages = FXCollections.observableArrayList();

        this.chosenChallenge = new SimpleStringProperty("");
        this.allChallenges = FXCollections.observableArrayList();
        this.availableChallenges = FXCollections.observableArrayList();

        this.difficulty = new SimpleStringProperty("");

        this.model = new Model(chosenOperators, allOperators, availableOperators, chosenStage, allStages, availableStages,
                chosenChallenge, allChallenges, availableChallenges, difficulty);

        operatorList.setItems(this.chosenOperators);
        allOperatorList.setItems(this.allOperators);
        Tooltip listViewTooltip = new Tooltip ("Double-click to select");
        allOperatorList.setTooltip(listViewTooltip);
        allOperatorList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    Operator operator = (Operator) allOperatorList.getSelectionModel().getSelectedItem();
                    operatorTextField.setText(operator.getName());
                }
            }
        });
        availableOperatorList.setItems(this.availableOperators);
        availableOperatorList.setTooltip(listViewTooltip);
        availableOperatorList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    Operator operator = (Operator) availableOperatorList.getSelectionModel().getSelectedItem();
                    operatorTextField.setText(operator.getName());
                }
            }
        });

        allStagesList.setItems(this.allStages);
        allStagesList.setTooltip(listViewTooltip);
        allStagesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    stageTextField.setText((String) allStagesList.getSelectionModel().getSelectedItem());
                }
            }
        });
        availableStagesList.setItems(this.availableStages);
        availableStagesList.setTooltip(listViewTooltip);
        availableStagesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    stageTextField.setText((String) availableStagesList.getSelectionModel().getSelectedItem());
                }
            }
        });

        allChallengesList.setItems(this.allChallenges);
        allChallengesList.setTooltip(listViewTooltip);
        allChallengesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    challengeTextField.setText((String) allChallengesList.getSelectionModel().getSelectedItem());
                }
            }
        });
        availableChallengesList.setItems(this.availableChallenges);
        availableChallengesList.setTooltip(listViewTooltip);
        availableChallengesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                {
                    challengeTextField.setText((String) availableChallengesList.getSelectionModel().getSelectedItem());
                }
            }
        });

        operationLabel.setText("Operation: " + this.chosenStage.getValue());
        this.chosenStage.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                operationLabel.setText("Operation: " + newValue);
            }
        });

        diffLabel.setText("Difficulty Rating: " + this.difficulty.getValue());
        this.difficulty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                diffLabel.setText("Difficulty Rating: " + newValue);
            }
        });

        chosenChallengeLabel.setText("Challenge: " + this.chosenChallenge.getValue());
        this.chosenChallenge.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                chosenChallengeLabel.setText("Challenge: " + newValue);
            }
        });

        Tooltip howToTooltip = new Tooltip ("Click here for help on how to use this application");
        howToButton.setTooltip(howToTooltip);
        howToButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //YOU GOTTA DO SOMETHING HERE
            }
        });
        Tooltip buttonTooltip = new Tooltip("Remove if in available list, add if not");
        randomizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.randomize();
            }
        });
        operatorButton.setTooltip(buttonTooltip);
        operatorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String operatorName = operatorTextField.getText();
                if(!model.removeAvailableOperator(operatorName))
                {
                    if(model.addAvailableOperator(operatorName))
                    {
                        operatorTextField.setText("");
                    }
                } else {
                    operatorTextField.setText("");
                }
            }
        });
        stageButton.setTooltip(buttonTooltip);
        stageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String stageName = stageTextField.getText();
                if(!model.removeAvailableStage(stageName)) {
                    if(model.addAvailableStage(stageName)) {
                        stageTextField.setText("");
                    }
                } else {
                    stageTextField.setText("");
                }
            }
        });
        challengeButton.setTooltip(buttonTooltip);
        challengeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String challengeName = challengeTextField.getText();
                if(!model.removeAvailableChallenge(challengeName))
                {
                    if(model.addAvailableChallenge(challengeName))
                    {
                        challengeTextField.setText("");
                    }
                } else {
                    challengeTextField.setText("");
                }
            }
        });

        operatorTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String operatorName = operatorTextField.getText();
                if(!model.removeAvailableOperator(operatorName))
                {
                    if(model.addAvailableOperator(operatorName))
                    {
                        operatorTextField.setText("");
                    }
                } else {
                    operatorTextField.setText("");
                }
            }
        });
        stageTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String stageName = stageTextField.getText();
                if(!model.removeAvailableStage(stageName)) {
                    if(model.addAvailableStage(stageName)) {
                        stageTextField.setText("");
                    }
                } else {
                    stageTextField.setText("");
                }
            }
        });
        challengeTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String challengeName = challengeTextField.getText();
                if(!model.removeAvailableChallenge(challengeName))
                {
                    if(model.addAvailableChallenge(challengeName))
                    {
                        challengeTextField.setText("");
                    }
                } else {
                    challengeTextField.setText("");
                }
            }
        });

        // EXTRA OPTIONS
        Tooltip easyModeTooltip = new Tooltip("Guarantees 1 of each operator type");
        easyMode.setTooltip(easyModeTooltip);
        easyMode.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setEasyMode(newValue);
                if(newValue) {
                    noMedic.selectedProperty().setValue(false);
                    noDefender.selectedProperty().setValue(false);
                    noSniper.selectedProperty().setValue(false);
                    noCaster.selectedProperty().setValue(false);
                    noGuard.selectedProperty().setValue(false);
                    noVanguard.selectedProperty().setValue(false);
                }
            }
        });

        Tooltip operatorCountTooltip = new Tooltip("Set the number of operators");
        operatorCountSpinner.setTooltip(operatorCountTooltip);
        operatorCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12));
        operatorCountSpinner.getValueFactory().setValue(12);
        operatorCountSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                model.setNumOperators(newValue);
            }
        });

        noMedic.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoMedic(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noVanguard.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoVanguard(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noGuard.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoGuard(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noCaster.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoCaster(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noSniper.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoSniper(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noDefender.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setNoDefender(newValue);
                if(newValue)
                {
                    easyMode.selectedProperty().setValue(false);
                }
            }
        });
        noHighStar.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                model.setHighStar(newValue);
            }
        });
    }

}
