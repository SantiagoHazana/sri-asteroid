package gerson;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Laser;
import models.Ship;
import models.Sprite;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
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

        //nave
//        Sprite spaceship = new Sprite("sample/Images/spaceship.png");
//        spaceship.position.set(400,300);
//        spaceship.rotation -=90;

        // nave 2

        Ship ship = new Ship("Images/Space Shooter Visual Assets/PNG/playerShip1_blue.png");
        ship.position.set(820,400);
        ship.rotation -=90;

        Ship enemy = new Ship("Images/Space Shooter Visual Assets/PNG/Enemies/enemyGreen1.png");
        enemy.position.set(300, 300);



        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
                //movimientos
                if (keyPressedList.contains("A")){
                    ship.rotation -=3;
                }

                if (keyPressedList.contains("D")){
                    ship.rotation +=3;
                }

                if (keyPressedList.contains("W")){
                    ship.velocity.setLength(200); //velocidad inicial
                    ship.velocity.setAngle(ship.rotation);
                }else{
                    // cuando se suelta la tecla --- meter desaceleracion
                    if (ship.velocity.getLength()==0) {
                        ship.velocity.setLength(0); //velocidad 0 al inicio
                    }else {
                        ship.velocity.setLength(50);
                    }
                }
                if (keyJustPressedList.contains("SPACE")){
                    Laser laser = new Laser("Images/Space Shooter Visual Assets/PNG/Lasers/laserBlue02.png");
                    laser.position.set(ship.position.x, ship.position.y);
                    laser.velocity.setLength(400);
                    laser.velocity.setAngle(ship.rotation);
                    laser.rotation = ship.rotation;
                    lasers.add(laser);
                    System.out.println("Piummmm");
                }

                keyJustPressedList.clear();

                if (ship.overlaps(enemy) && enemy.isAlive()){
                    System.out.println("Ship touched the other ship");
                    enemy.die();
                }


                //spaceship.update(1/60.0); //fps -- cambiar otro update
                ship.update(1/60.0);
                enemy.update(1/60f);

                background.render(context);
                //spaceship.render(context);
                ship.render(context);
                if (enemy.isAlive())
                    enemy.render(context);


                for (Laser l : lasers) {
                    l.update(1/60f);
                    if (l.isAlive())
                        l.render(context);
                    else{
                        l=null;
                        lasers.remove(l);
                    }
                }
            }
        };
        gameloop.start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
