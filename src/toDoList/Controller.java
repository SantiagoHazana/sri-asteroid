package toDoList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import toDoList.dataModel.ToDoData;
import toDoList.dataModel.ToDoItem;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {

    private List<ToDoItem> toDoItems;
    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea detailsTextArea;
    @FXML
    private Label dueDateLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){

        toDoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
                detailsTextArea.setText(item.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("d MMMM, yyyy");
                dueDateLabel.setText(df.format(item.getDeadLine()));
            }
        });

        toDoListView.getItems().setAll(ToDoData.getInstance().getTodoItems());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());

        try {
            Parent root = FXMLLoader.load(getClass().getResource("todoitemDialog.fxml"));
            dialog.getDialogPane().setContent(root);
        } catch (IOException e){
            System.out.println("Could not load the dialog");
            e.printStackTrace();
            return;
        }
    }

    @FXML
    public void handleItemClick(){
        ToDoItem item = toDoListView.getSelectionModel().getSelectedItem();
        detailsTextArea.setText(item.getDetails());
        dueDateLabel.setText(item.getDeadLine().toString());
    }

}
