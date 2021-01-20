package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Events {

    @FXML
    private TextField nameField;
    @FXML
    private Button helloButton;
    @FXML
    private Button byeButton;
    @FXML
    private CheckBox clearCheckBox;
    @FXML
    private Label ourLabel;

    @FXML
    public void initialize(){
        helloButton.setDisable(true);
        byeButton.setDisable(true);
    }

    @FXML
    public void onButtonClicked(ActionEvent e){
        if (e.getSource().equals(helloButton)){
            System.out.println("Hello, " + nameField.getText());
        }else if (e.getSource().equals(byeButton)){
            System.out.println("Bye, " + nameField.getText());
        }

        Runnable task = () -> {
            try {
                String s = Platform.isFxApplicationThread() ? "UI thread":"Background thread";
                System.out.println("Im going to sleep the: " + s);
                Thread.sleep(10000);
                Platform.runLater(() -> {
                    String s1 = Platform.isFxApplicationThread() ? "UI thread":"Background thread";
                    System.out.println("Im updating the label in the: " + s1);
                    ourLabel.setText("We did something!");
                });
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        };
        new Thread(task).start();

        if (clearCheckBox.isSelected()){
            nameField.clear();
            handleKeyReleased();
        }
    }

    @FXML
    public void handleKeyReleased(){
        String text = nameField.getText();;
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty();
        helloButton.setDisable(disableButtons);
        byeButton.setDisable(disableButtons);
    }

}
