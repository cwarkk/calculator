package sample.controller;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import sample.Main;
import sample.utils.EvaluateString;

import java.io.IOException;
import java.util.ArrayList;


public class CalculatorController {

    @FXML
    private Label expression;

    @FXML
    private Label result;

    private ArrayList<String> calculation_history = new ArrayList<>();

    public void insertNumber(String number) {
        expression.setText(expression.getText() + number);
    }

    public void insertOperator(String operator) {
        expression.setText(expression.getText() + " " + operator + " ");
    }

    public void clearExpression() {
        expression.setText("");
    }

    public Label getExpression() {
        return expression;
    }

    public Label getResult() {
        return result;
    }

    public void setResult(String newResult) {
        this.result.setText("= " + newResult);
    }

    public void insertAnswer(String answer){
        expression.setText(expression.getText() + answer);
    }

    public void deleteLast(){
        if (!getExpression().getText().isEmpty()) {
            StringBuilder text = new StringBuilder(getExpression().getText());
            text.deleteCharAt(text.length() - 1);
            getExpression().setText(text.toString());
        }
    }

    public void openHistoryWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/resources/history.fxml"));
            Parent root = loader.load();

            Main.getHistoryStage().setScene(new Scene(root));

            HistoryController historyController = loader.getController();
            historyController.iniyializeCalculations(calculation_history);

            Main.getHistoryStage().show();
        }catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void addCalcilation(String expression, String result) {
        this.calculation_history.add(expression + " = " + result);
    }



    public void onMouseClick(MouseEvent mouseEvent){

        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();


        switch (buttonText){
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                insertNumber(buttonText);
                break;
            case "+":
            case "-":
            case "/":
            case "*":
                insertOperator(buttonText);
                break;
            case "Clear":
                clearExpression();
                break;
            case "=":
                int result = EvaluateString.evaluate(this.getExpression().getText());
                addCalcilation(this.getExpression().getText(), String.valueOf(result));
                setResult(String.valueOf(result));
                break;
            case "Answer":
                insertAnswer(getResult().getText().substring(2));
                break;
            case "Delete":
                deleteLast();
                break;
            case "History":
                openHistoryWindow();


        }
    }


}
