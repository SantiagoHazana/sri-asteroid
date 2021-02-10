package controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {

    AnimationTimer gameLoop;
    Stage stage;
    Parent root;
    Scene mainScene;
    private final int frameRate = 60;
    private boolean game = false;

    @FXML
    public Button btn;
    @FXML
    public TextField playerName;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dioretsa");
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        mainScene = new Scene(root, 1600, 900);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void game() throws IOException {
        root = FXMLLoader.load(getClass().getResource("game.fxml")); //cambiar
        stage = (Stage) btn.getScene().getWindow();
        mainScene = new Scene(root, 1600, 900);
        stage.setTitle("Dioretsa");
        stage.setScene(mainScene);
        startGame((BorderPane) root, stage, mainScene);
        ((Stage) btn.getScene().getWindow()).close();
    }

    private void startGame(BorderPane root, Stage primaryStage, Scene mainScene){

        Canvas canvas = new Canvas(1600,900);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<Laser> lasers = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        //fondo -- Cambiar a mas resolucion

        Sprite background = new Sprite("Images/background900p.png");
        background.position.set(470,450);


        //Keys

        ArrayList<String> keyPressedList = new ArrayList<>();
        ArrayList<String> keyJustPressedList = new ArrayList<>();

        mainScene.setOnKeyPressed(
                (KeyEvent event) ->{
                    String keyName = event.getCode().toString();
                    //Para evitar teclas duplicadas en la lista
                    if (!keyPressedList.contains(keyName)) {
                        keyPressedList.add(keyName);
                        keyJustPressedList.add(keyName);
                    }
                }
        );

        mainScene.setOnKeyReleased(
                (KeyEvent event) ->{
                    String keyName = event.getCode().toString();
                    //Para evitar teclas duplicadas en la lista
                    if (keyPressedList.contains(keyName)) {
                        keyPressedList.remove(keyName);
                    }
                }
        );

        Ship ship = new Ship(playerName.getText(),"Images/Space Shooter Visual Assets/PNG/playerShip1_blue.png");
        ship.position.set(820,400);
        ship.rotation -=90;

        //Asteroides
        // Rock rock = new Rock("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big2.png");
        ArrayList<Rock> rockList = new ArrayList<Rock>();
        //hacer meto devuelva imagen aleatoria

        //PowerUps
        ArrayList<PowerUp> powerUps = new ArrayList<>();

        int rockCount = 4;
        for (int i = 0; i < rockCount ; i++) {

            Rock rock = new Rock(Rock.getRockImage());
            double dist =  ship.getDistance(rock);
            while (dist < 100) {
                rock.newPosition();
                dist = ship.getDistance(rock);
            }
            rockList.add(rock);
        }

        Timer timerNewRock = new Timer();
        timerNewRock.schedule(new TimerTask() {
            @Override
            public void run() {
                Rock rock = new Rock(Rock.getRockImage());
                double dist =  ship.getDistance(rock);
                while (dist < 300) {
                    rock.newPosition();
                    dist = ship.getDistance(rock);
                }
                rockList.add(rock);
            }
        },0,1500);

        Timer timerNewEnemy = new Timer();
        timerNewEnemy.schedule(new TimerTask() {
            @Override
            public void run() {
                Enemy enemy = new Enemy("Images/Space Shooter Visual Assets/PNG/Enemies/enemyGreen1.png");
                double dist =  ship.getDistance(enemy);
                while (dist < 300) {
                    enemy.newPosition();
                    dist = ship.getDistance(enemy);
                }
                enemies.add(enemy);
            }
        },0,6000);

        Timer timerNewPowerUp = new Timer();
        timerNewPowerUp.schedule(new TimerTask() {
            @Override
            public void run() {
                int r = (int)(Math.random()*3);
                PowerUp powerUp = new PowerUp(PowerUp.getImages(r), r);
                double dist =  ship.getDistance(powerUp);
                while (dist < 300) {
                    powerUp.newPosition();
                    dist = ship.getDistance(powerUp);
                }
                powerUps.add(powerUp);
            }
        }, 5000, 5000);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
                if (ship.isAlive()) {
                    //movimientos
                    if (keyPressedList.contains("A")) {
                        ship.move("A");
                    }

                    if (keyPressedList.contains("D")) {
                        ship.move("D");
                    }

                    if (keyPressedList.contains("SPACE")){
                        if (ship.rapidFireActive())
                            lasers.add(ship.pium());
                    }

                    if (keyPressedList.contains("W")) {
                        ship.move("W");
                    } else {
                        ship.move("");
                    }
                    if (keyJustPressedList.contains("SPACE")) {
                        lasers.add(ship.pium());
                    }
                }

                keyJustPressedList.clear();
                background.render(context);

                for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
                    Enemy e = iterator.next();
                    if (e.isAlive()){
                        e.render(context);
                        e.update(1/60f);
                        e.checkIfLaserHitShip(ship);
                    }else{
                        iterator.remove();
                        e = null;
                        break;
                    }
                    if (e.overlaps(ship) && ship.isAlive()){
                        e.die();
                        if (!ship.isShieldActive())
                            ship.hit();
                    }
                }

                for (Iterator<Rock> iterator = rockList.iterator(); iterator.hasNext();){
                    Rock rock = iterator.next();
                    if (rock.isAlive()){
                        rock.render(context);
                        rock.update(1f/frameRate);
                    }else{
                        iterator.remove();
                        rock = null;
                        break;
                    }
                    if (rock.overlaps(ship) && ship.isAlive()) {
                        iterator.remove();
                        rock.die();
                        if (!ship.isShieldActive())
                            ship.hit();
                    }
                }

                //spaceship.update(1/60.0); //fps -- cambiar otro update
                ship.update(1f/frameRate);


                if (ship.isAlive())
                    ship.render(context);
                else{
                    timerNewRock.cancel();
                    timerNewEnemy.cancel();
                    timerNewPowerUp.cancel();
                    try {
                        Ranking.addRankingPoints(ship.getPlayerName(), ship.getPoints());
                        gameOver();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for (Iterator<PowerUp> iterator = powerUps.iterator(); iterator.hasNext();){
                    PowerUp p = iterator.next();
                    if (p.isActive() && p.getTimeAlive()<7){
                        p.render(context);
                        p.update(1f/frameRate);
                    }else{
                        iterator.remove();
                        p = null;
                        break;
                    }
                    if (ship.isAlive() && p.isActive() && p.overlaps(ship)){
                        ship.powerUp(p);
                        p.die();
                    }
                }

                for (Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext();){
                    Laser l = iterator.next();
                    if (l.isAlive()){
                        l.render(context);
                        l.update(1f/frameRate);
                    }else{
                        lasers.remove(l);
                        l=null;
                        break;
                    }

                    for (Enemy enemy : enemies) {
                        if (l.overlaps(enemy) && enemy.isAlive()){
                            enemy.die();
                            ship.addPoints(150);
                            l.die();
                        }
                    }

                    for (Rock r : rockList) {
                        if (l.overlaps(r)){
                            l.die();
                            ship.addPoints(100);
                            if (!r.isTiny()) {
                                int  nTinyRock = (int)(Math.random()*2+2);
                                for (int i = 0; i < nTinyRock ; i++) {
                                    Rock tiny = new Rock();
                                    tiny.position.set(r.position.x, r.position.y);
                                    rockList.add(tiny);
                                }
                            }

                            rockList.remove(r);
                            r = null;
                            break;
                        }
                    }
                }


                context.setFill(Color.WHITE);
                context.setStroke(Color.GREEN);
                context.setFont(new Font("Arial Black", 42));
                context.setLineWidth(3);
                context.fillText("Vidas: " + ship.getLives() + "| Puntaje: " + ship.getPoints(), 990, 40);
                context.strokeText("Vidas: " + ship.getLives() + "| Puntaje: " + ship.getPoints(), 990, 40);
            }
        };

        gameLoop.start();
        primaryStage.show();
    }

    private void gameOver() throws IOException {
        gameLoop.stop();
        root = FXMLLoader.load(getClass().getResource("gameOver.fxml")); //cambiar
        mainScene = new Scene(root, 1600, 900);
        stage.setTitle("Ranking");
        stage.setScene(mainScene);
        stage.show();
    }
}
