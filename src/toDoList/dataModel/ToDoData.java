package toDoList.dataModel;

import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class ToDoData {
    private static ToDoData instance1 = new ToDoData();
    private static String fileName = "todoItems.txt";
    private List<ToDoItem> todoItems;
    private DateTimeFormatter formatter;

    public static ToDoData getInstance(){
        return instance1;
    }

    private ToDoData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public List<ToDoItem> getTodoItems() {
        return todoItems;
    }

//    public void setTodoItems(List<ToDoItem> todoItems) {
//        this.todoItems = todoItems;
//    }

    public void loadToDoItems() throws IOException{
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = bufferedReader.readLine())!= null){
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);

                ToDoItem toDoItem = new ToDoItem(shortDescription, details, date);
                todoItems.add(toDoItem);

            }
        } finally {
            if (bufferedReader != null){
                bufferedReader.close();
            }
        }
    }

    public void storeToDoItems() throws IOException{
        Path path = Paths.get(fileName);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        try {
            Iterator<ToDoItem> itemIterator = todoItems.iterator();
            while (itemIterator.hasNext()){
                ToDoItem item = itemIterator.next();
                bufferedWriter.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getDetails(), item.getDeadLine().format(formatter)));
                bufferedWriter.newLine();
            }
        }finally {
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
        }
    }

}
