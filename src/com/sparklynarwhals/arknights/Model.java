package com.sparklynarwhals.arknights;

import java.util.ArrayList;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Model {

    public ObservableList<Operator> availableOperators;
    private ArrayList<Operator> selectedOperators;
    public ObservableList<Operator> chosenOperators;
    public ObservableList<Operator> allOperators;
    public int numOperators;

    public ObservableList<String> availableStages;
    public SimpleStringProperty chosenStage;
    public ObservableList<String> allStages;

    public ObservableList<String> availableChallenges;
    public ObservableList<String> allChallenges;
    public SimpleStringProperty chosenChallenge;

    public SimpleStringProperty difficulty;

    private boolean easyMode;
    private boolean noMedic;
    private boolean noGuard;
    private boolean noDefender;
    private boolean noCaster;
    private boolean noSniper;
    private boolean noVanguard;
    private boolean noHighStar;

    public Model (ObservableList<Operator> chosenOperators, ObservableList<Operator> allOperators, ObservableList<Operator> availableOperators,
                  SimpleStringProperty chosenStage, ObservableList<String> allStages, ObservableList<String> availableStages,
                  SimpleStringProperty chosenChallenge, ObservableList<String> allChallenges, ObservableList<String> availableChallenges,
                  SimpleStringProperty difficulty)
    {
        this.chosenOperators = chosenOperators;
        this.allOperators = allOperators;
        this.availableOperators = availableOperators;
        this.selectedOperators  = new ArrayList<Operator>();

        this.numOperators = 12;
        this.availableStages = availableStages;
        this.allStages = allStages;
        this.chosenStage = chosenStage;

        this.availableChallenges = availableChallenges;
        this.allChallenges = allChallenges;
        this.chosenChallenge = chosenChallenge;

        this.difficulty = difficulty;

        allChallenges.add("Soul Link");
        allChallenges.add("Permadeath");
        allChallenges.add("No Pause");
        allChallenges.add("2x Speed Only");
        allChallenges.add("No Active Skills");
        availableChallenges.add("Soul Link");
        availableChallenges.add("Permadeath");
        availableChallenges.add("No Pause");
        availableChallenges.add("2x Speed Only");
        availableChallenges.add("No Active Skills");

        this.easyMode = false;
        this.noMedic = false;
        this.noDefender = false;
        this.noSniper = false;
        this.noCaster = false;
        this.noGuard = false;
        this.noVanguard = false;
        this.noHighStar = false;

        loadOperators();
        loadStages();
    }

    public void randomize()
    {
        Random r = new Random();
        Alert operatorAlert = new Alert(Alert.AlertType.ERROR);
        operatorAlert.setTitle("Randomization Error");
        operatorAlert.setHeaderText("Not enough Operators");
        operatorAlert.setContentText("There were not enough operators who fit the options given!");

        Alert stageAlert = new Alert(Alert.AlertType.ERROR);
        stageAlert.setTitle("Randomization Error");
        stageAlert.setHeaderText("Not enough Stages");
        stageAlert.setContentText("There were not enough stages who fit the options given!");

        Alert challengeAlert = new Alert(Alert.AlertType.ERROR);
        challengeAlert.setTitle("Randomization Error");
        challengeAlert.setHeaderText("Not enough Challenges");
        challengeAlert.setContentText("There were not enough challenges who fit the options given!");

        Collections.shuffle(selectedOperators);
        chosenOperators.clear();
        if (!easyMode) {
            for (int i = 0; i < selectedOperators.size(); i++) {
                if(chosenOperators.size() == numOperators)
                {
                    break;
                }
                Operator operator = selectedOperators.get(i).clone();
                if(operator.getPosition().contains("Guard") && noGuard) continue;
                if(operator.getPosition().contains("Medic") && noMedic) continue;
                if(operator.getPosition().contains("Vanguard") && noVanguard) continue;
                if(operator.getPosition().contains("Sniper") && noSniper) continue;
                if(operator.getPosition().contains("Defender") && noDefender) continue;
                if(operator.getPosition().contains("Caster") && noCaster) continue;
                if((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) continue;
                if (operator.getNumSkills() == -1) {
                    operator.setSelectedSkill(-1);
                } else {
                    operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                }
                chosenOperators.add(operator);
            }
        } else {
            boolean hasMedic = false;
            boolean hasDefender = false;
            boolean hasVanguard = false;
            boolean hasGuard = false;
            boolean hasCaster = false;
            boolean hasSniper = false;

            ArrayList<Operator> outliers = new ArrayList<Operator>();

            for (int i = 0; i < selectedOperators.size(); i++)
            {
                Operator operator = selectedOperators.get(i).clone();
                String type = operator.getPosition();
                if (type.contains("Guard")) {
                    if (hasGuard) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasGuard = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else if (type.contains("Medic")) {
                    if (hasMedic) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasMedic = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else if (type.contains("Defender")) {
                    if (hasDefender) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasDefender = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else if (type.contains("Vanguard")) {
                    if (hasVanguard) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasVanguard = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else if (type.contains("Caster")) {
                    if (hasCaster) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasCaster = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else if (type.contains("Sniper")) {
                    if (hasSniper) {
                        outliers.add(operator);
                    } else if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                        continue;
                    } else {
                        hasSniper = true;
                        if (operator.getNumSkills() == -1) {
                            operator.setSelectedSkill(-1);
                        } else {
                            operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                        }
                        chosenOperators.add(operator);
                    }
                } else {
                    outliers.add(operator);
                }
            }
            for (int i = 0; i < outliers.size(); i++)
            {
                if(chosenOperators.size() == numOperators)
                {
                    break;
                }
                Operator operator = outliers.get(i);
                if ((operator.getStars() == 5 || operator.getStars() == 6) && noHighStar) {
                    continue;
                }
                if (operator.getNumSkills() == -1) {
                    operator.setSelectedSkill(-1);
                } else {
                    operator.setSelectedSkill(r.nextInt(operator.getNumSkills()) + 1);
                }
                chosenOperators.add(operator);
            }
        }
        if (chosenOperators.size() != numOperators) {
            operatorAlert.showAndWait();
        }
        if (availableChallenges.size() < 1)
        {
            challengeAlert.showAndWait();
        }
        if (availableStages.size() < 1)
        {
            stageAlert.showAndWait();
        }
        try {
            chosenChallenge.set(availableChallenges.get(r.nextInt(availableChallenges.size())));
            chosenStage.set(availableStages.get(r.nextInt(availableStages.size())));
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        checkDifficulty();
    }

    private void checkDifficulty() {
        int difficulty = 0;
        for (int i = 0; i < chosenOperators.size(); i++)
        {
            if (chosenOperators.get(i).getStars() == 1 || chosenOperators.get(i).getStars() == 2) {
                difficulty += 5;
            } else if (chosenOperators.get(i).getStars() == 3) {
                difficulty += 4;
            } else if (chosenOperators.get(i).getStars() == 4) {
                difficulty += 3;
            } else if (chosenOperators.get(i).getStars() == 5) {
                difficulty += 2;
            } else if (chosenOperators.get(i).getStars() == 6) {
                difficulty += 1;
            }
        }
        difficulty += (12-chosenOperators.size())*6;

        String stageParts[] = chosenStage.getValue().split("-");
        if (stageParts[0].equals("CE") || stageParts[0].equals("AP") || stageParts[0].equals("SK")
                || stageParts[0].equals("CA") || stageParts[0].equals("LS")) {
            if (stageParts[1].equals("2") || stageParts[1].equals("3"))
            {
                difficulty += 5;
            } else if (stageParts[1].equals("4") || stageParts[1].equals("4"))
            {
                difficulty += 10;
            }
        } else if (stageParts[0].equals("PR")) {
            if (stageParts[1].equals("1"))
            {
                difficulty += 5;
            } else if (stageParts[1].equals("2"))
            {
                difficulty += 10;
            }
        } else if (stageParts[0].equals("Annihilation 1")) {
            difficulty += 10;
        } else if (stageParts[0].equals("Annihilation 2")) {
            difficulty += 30;
        } else if (stageParts[0].equals("Annihilation 3")) {
            difficulty += 35;
        } else {
            if (stageParts[0].contains("2") || stageParts[0].contains("3"))
            {
                difficulty += 5;
            } else if (stageParts[0].contains("4") || stageParts[0].contains("5"))
            {
                difficulty += 10;
            } else if (stageParts[0].contains("6"))
            {
                difficulty += 15;
            }
        }

        if (chosenChallenge.getValue().equals("Soul Link"))
        {
            difficulty  += 5;
        } else if (chosenChallenge.getValue().equals("Permadeath"))
        {
            difficulty  += 10;
        } else if (chosenChallenge.getValue().equals("No Pause"))
        {
            difficulty  += 3;
        } else if (chosenChallenge.getValue().equals("2x Speed Only"))
        {
            difficulty  += 2;
        } else if (chosenChallenge.getValue().equals("No Active Skills"))
        {
            difficulty  += 10;
        }

        if (this.noDefender)
        {
            difficulty += 10;
        }
        if (this.noCaster)
        {
            difficulty += 10;
        }
        if (this.noSniper)
        {
            difficulty += 10;
        }
        if (this.noVanguard)
        {
            difficulty += 10;
        }
        if (this.noGuard)
        {
            difficulty += 10;
        }
        if (this.noMedic)
        {
            difficulty += 10;
        }

        if (this.easyMode)
        {
            difficulty = Math.round(difficulty * .75f);
        }

        this.difficulty.setValue("" + difficulty);
    }

    private void loadStages()
    {
        try
        {
            File inputFile = new File("src/com/sparklynarwhals/arknights/stages.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("stage");

            for (int i = 0; i < nList.getLength(); i++)
            {
                Node stageNode = nList.item(i);
                if (stageNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element stageElement = (Element) stageNode;
                    String stageName = stageElement.getElementsByTagName("name").item(0).getTextContent();
                    allStages.add(stageName);
                    availableStages.add(stageName);
                }
            }
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    private void loadOperators()
    {
        try
        {
            File inputFile = new File("src/com/sparklynarwhals/arknights/operators.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("operator");

            for (int i = 0; i < nList.getLength(); i++)
            {
                Node operatorNode = nList.item(i);
                if (operatorNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element operatorElement = (Element) operatorNode;
                    String operatorName = operatorElement.getElementsByTagName("name").item(0).getTextContent();
                    int operatorNumSkill = Integer.parseInt(
                            operatorElement.getElementsByTagName("numSkill").item(0).getTextContent());
                    int operatorStars = Integer.parseInt(
                            operatorElement.getElementsByTagName("stars").item(0).getTextContent());
                    String operatorPosition = operatorElement.getElementsByTagName("position").item(0).getTextContent();
                    String operatorDamage = operatorElement.getElementsByTagName("damage").item(0).getTextContent();
                    Operator operator = new Operator(operatorName, operatorNumSkill, operatorStars, operatorPosition, operatorDamage);
                    allOperators.add(operator);
                    availableOperators.add(operator);
                    selectedOperators.add(operator);
                }
            }
            FXCollections.sort(availableOperators, Comparator.comparing(Operator::getName));
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    public boolean addAvailableOperator(String operatorName)
    {
        for(int i = 0; i < allOperators.size(); i++)
        {
            if (allOperators.get(i).getName().trim().toLowerCase().equals(operatorName.toLowerCase().trim()))
            {
                availableOperators.add(allOperators.get(i));
                selectedOperators.add(allOperators.get(i));
                FXCollections.sort(availableOperators, Comparator.comparing(Operator::getName));
                return true;
            }
        }
        return false;
    }

    public boolean removeAvailableOperator(String operatorName)
    {
        for(int i = 0; i < availableOperators.size(); i++)
        {
            if (availableOperators.get(i).getName().trim().toLowerCase().equals(operatorName.toLowerCase().trim()))
            {
                selectedOperators.remove(availableOperators.get(i));
                availableOperators.remove(i);
                FXCollections.sort(availableOperators, Comparator.comparing(Operator::getName));
                return true;
            }
        }
        return false;
    }

    public boolean addAvailableStage(String stageName)
    {
        for(int i = 0; i < allStages.size(); i++)
        {
            if (allStages.get(i).trim().toLowerCase().equals(stageName.toLowerCase().trim()))
            {
                availableStages.add(allStages.get(i));
                FXCollections.sort(availableStages);
                return true;
            }
        }
        return false;
    }

    public boolean removeAvailableStage(String stageName)
    {
        for(int i = 0; i < availableStages.size(); i++)
        {
            if (availableStages.get(i).trim().toLowerCase().equals(stageName.toLowerCase().trim()))
            {
                availableStages.remove(i);
                FXCollections.sort(availableStages);
                return true;
            }
        }
        return false;
    }

    public boolean addAvailableChallenge(String challengeName)
    {
        for(int i = 0; i < allChallenges.size(); i++)
        {
            if (allChallenges.get(i).trim().toLowerCase().equals(challengeName.toLowerCase().trim()))
            {
                availableChallenges.add(allChallenges.get(i));
                FXCollections.sort(availableChallenges);
                return true;
            }
        }
        return false;
    }

    public boolean removeAvailableChallenge(String challengeName)
    {
        for(int i = 0; i < availableChallenges.size(); i++)
        {
            if (availableChallenges.get(i).trim().toLowerCase().equals(challengeName.toLowerCase().trim()))
            {
                availableChallenges.remove(i);
                FXCollections.sort(availableChallenges);
                return true;
            }
        }
        return false;
    }

    public void setEasyMode(boolean easyMode)
    {
        this.easyMode = easyMode;
    }

    public void setNumOperators(int numOperators)
    {
        this.numOperators = numOperators;
    }
    public void setNoMedic(boolean noMedic)
    {
        this.noMedic = noMedic;
    }
    public void setNoGuard(boolean noGuard)
    {
        this.noGuard = noGuard;
    }
    public void setNoDefender(boolean noDefender)
    {
        this.noDefender = noDefender;
    }
    public void setNoCaster(boolean noCaster)
    {
        this.noCaster = noCaster;
    }
    public void setNoSniper(boolean noSniper)
    {
        this.noSniper = noSniper;
    }
    public void setNoVanguard(boolean noVanguard)
    {
        this.noVanguard = noVanguard;
    }
    public void setHighStar(boolean noHighStar)
    {
        this.noHighStar = noHighStar;
    }
}
