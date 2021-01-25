package gerson;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

        //fondo -- Cambiar a mas resolucion

        Sprite background = new Sprite("gerson/Images/background900p.png");
        background.position.set(470,450);


        //Keys

        ArrayList<String> keyPressedList = new ArrayList<String>();

        mainScene.setOnKeyPressed(
                (KeyEvent event) ->{
                    String keyName = event.getCode().toString();
                    //Para evitar teclas duplicadas en la lista
                    if (!keyPressedList.contains(keyName)) {
                        keyPressedList.add(keyName);
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

        Ship ship = new Ship("gerson/Images/spaceship.png");
        ship.position.set(820,400);
        ship.rotation -=90;




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
                    ship.velocity.setLength(300); //velocidad inicial
                    ship.velocity.setAngle(ship.rotation);
                }else{
                    // cuando se suelta la tecla --- meter desaceleracion

                    if (ship.velocity.getLength()==0) {
                        ship.velocity.setLength(0); //velocidad 0 al inicio
                    }else {
                        ship.velocity.setLength(100);
                    }
                }

                //spaceship.update(1/60.0); //fps -- cambiar otro update
                ship.update(1/60.0);


                background.render(context);
                //spaceship.render(context);
                ship.render(context);
            }
        };
        gameloop.start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
