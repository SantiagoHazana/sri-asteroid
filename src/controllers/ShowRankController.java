package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Rank;
import models.Ranking;

public class ShowRankController {

    @FXML
    public ListView<Rank> rankView;

    @FXML
    public void initialize(){
//        finalScoreLabel.setText("Puntaje final: " + score);
        rankView.getItems().setAll(Ranking.getRank());
    }

    public void closeGame(){
        System.exit(0);
    }

}
