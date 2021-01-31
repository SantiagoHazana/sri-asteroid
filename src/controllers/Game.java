package controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {

    AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dioretsa");
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);

        //explicacion
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

        Ship ship = new Ship("Images/Space Shooter Visual Assets/PNG/playerShip1_blue.png");
        ship.position.set(820,400);
        ship.rotation -=90;

        //Asteroides
        // Rock rock = new Rock("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big2.png");
        ArrayList<Rock> rockList = new ArrayList<Rock>();
        //hacer meto devuelva imagen aleatoria

        //PowerUps
        ArrayList<PowerUp> powerUps = new ArrayList<>();

        int rockCount = 1;
        for (int i = 0; i < rockCount ; i++) {
            Rock rock = new Rock(Rock.getImages());
            rockList.add(rock);
        }

        Timer timerNewRock = new Timer();
        timerNewRock.schedule(new TimerTask() {
            @Override
            public void run() {
                Rock rock = new Rock(Rock.getImages());
                rockList.add(rock);
            }
        },0,1500);

        Timer timerNewEnemy = new Timer();
        timerNewEnemy.schedule(new TimerTask() {
            @Override
            public void run() {
                Enemy enemy = new Enemy("Images/Space Shooter Visual Assets/PNG/Enemies/enemyGreen1.png");
                enemies.add(enemy);
            }
        },0,6000);

        Timer timerNewPowerUp = new Timer();
        timerNewPowerUp.schedule(new TimerTask() {
            @Override
            public void run() {
                int r = (int)(Math.random()*3);
                PowerUp powerUp = new PowerUp(PowerUp.getImages(r), r);
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
                        rock.update(1/60.0D);
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
                ship.update(1/60.0);


                if (ship.isAlive())
                    ship.render(context);
                else{
                    timerNewRock.cancel();
                    timerNewEnemy.cancel();
                    timerNewPowerUp.cancel();
                    gameOver();
                }

                for (Iterator<PowerUp> iterator = powerUps.iterator(); iterator.hasNext();){
                    PowerUp p = iterator.next();
                    if (p.isActive()){
                        p.render(context);
                        p.update(1/60f);
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
                        l.update(1/60f);
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
                                Rock tiny = new Rock();
                                Rock tiny2 = new Rock();
                                Rock tiny3 = new Rock();
                                Rock tiny4 = new Rock();

                                //probe a meterlas en un for para no tener tanto codigo pero no funciono
                                tiny.position.set(r.position.x, r.position.y);
                                tiny2.position.set(r.position.x, r.position.y);
                                tiny3.position.set(r.position.x, r.position.y);
                                tiny4.position.set(r.position.x, r.position.y);

                                rockList.add(tiny);
                                rockList.add(tiny2);
                                rockList.add(tiny3);
                                rockList.add(tiny4);
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

    private void gameOver() {
        gameLoop.stop();
    }
}
