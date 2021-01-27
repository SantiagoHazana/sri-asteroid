package controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Laser;
import models.Rock;
import models.Ship;
import models.Sprite;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Prueba Asteroid");
        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);

        //explicacion
        Canvas canvas = new Canvas(1600,900);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        ArrayList<Laser> lasers = new ArrayList<>();

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

        Ship enemy = new Ship("Images/Space Shooter Visual Assets/PNG/Enemies/enemyGreen1.png");
        enemy.position.set(300, 300);

        //Asteroides
        // Rock rock = new Rock("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big2.png");
        ArrayList<Rock> rockList = new ArrayList<Rock>();
        //hacer meto devuelva imagen aleatoria

        int rockCount = 1;
        for (int i = 0; i < rockCount ; i++) {
            Rock rock = new Rock(Rock.getImages());
            rockList.add(rock);
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Rock rock = new Rock(Rock.getImages());
                rockList.add(rock);
            }
        },0,3*1000);

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
                //movimientos
                if (keyPressedList.contains("A")){
                    ship.move("A");
                }

                if (keyPressedList.contains("D")){
                    ship.move("D");
                }

                if (keyPressedList.contains("W")){
                    ship.move("W");
                }else{
                    ship.move("");
                }
                if (keyJustPressedList.contains("SPACE")){
                    lasers.add(ship.pium());
                }

                keyJustPressedList.clear();

                if (ship.overlaps(enemy) && enemy.isAlive()){
                    System.out.println("Ship touched the other ship");
                    ship.hit();
                    enemy.die();
                }

                background.render(context);

                for (Rock rock: rockList){
                    if (rock.isAlive()){
                        rock.render(context);
                        rock.update(1/60.0D);
                    }else{
                        rockList.remove(rock);
                        rock = null;
                        break;
                    }
                    if (rock.overlaps(ship)) {
                        rockList.remove(rock);
                        ship.hit();
                        rock.die();
                    }
                }
                System.out.println("Player points: " + ship.getPoints());

                //spaceship.update(1/60.0); //fps -- cambiar otro update
                ship.update(1/60.0);
                enemy.update(1/60f);

                //spaceship.render(context);
                ship.render(context);
                if (enemy.isAlive())
                    enemy.render(context);


                for (Laser l : lasers) {
                    if (l.isAlive()){
                        l.render(context);
                        l.update(1/60f);
                    }else{
                        lasers.remove(l);
                        l=null;
                        break;
                    }
                    if (l.overlaps(enemy) && enemy.isAlive()){
                        enemy.die();
                        ship.addPoints(150);
                        l.die();
                    }
                    for (Rock r : rockList) {
                        if (l.overlaps(r)){
                            l.die();
                            ship.addPoints(100);
                            rockList.remove(r);
                            r = null;
                            break;
                        }
                    }
                }
            }
        };
        gameloop.start();
        primaryStage.show();
    }
}
