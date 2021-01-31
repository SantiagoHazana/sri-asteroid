//package gerson;
//
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import models.Rock;
//import models.Ship;
//import models.Sprite;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        primaryStage.setTitle("Prueba Asteroid");
//        BorderPane root = new BorderPane();
//        Scene mainScene = new Scene(root);
//        primaryStage.setScene(mainScene);
//
//        //explicacion
//        Canvas canvas = new Canvas(1600,900);
//        GraphicsContext context = canvas.getGraphicsContext2D();
//        root.setCenter(canvas);
//
//        //fondo -- Cambiar a mas resolucion
//
//        Sprite background = new Sprite("Images/background900p.png");
//        background.position.set(470,450);
//
//
//        //Keys
//
//        ArrayList<String> keyPressedList = new ArrayList<String>();
//
//        mainScene.setOnKeyPressed(
//                (KeyEvent event) ->{
//                    String keyName = event.getCode().toString();
//                    //Para evitar teclas duplicadas en la lista
//                    if (!keyPressedList.contains(keyName)) {
//                        keyPressedList.add(keyName);
//                    }
//                }
//        );
//
//        mainScene.setOnKeyReleased(
//                (KeyEvent event) ->{
//                    String keyName = event.getCode().toString();
//                    //Para evitar teclas duplicadas en la lista
//                    if (keyPressedList.contains(keyName)) {
//                        keyPressedList.remove(keyName);
//                    }
//                }
//        );
//
//        //nave
////        Sprite spaceship = new Sprite("sample/Images/spaceship.png");
////        spaceship.position.set(400,300);
////        spaceship.rotation -=90;
//
//        // nave 2
//
//        Ship ship = new Ship("Images/spaceship.png");
//        ship.position.set(820,400);
//        ship.rotation -=90;
//
//        //Asteroides
//       // Rock rock = new Rock("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big2.png");
//        ArrayList<Rock> rockList = new ArrayList<Rock>();
//        //hacer meto devuelva imagen aleatoria
//
//        int rockCount = 1;
//        for (int i = 0; i < rockCount ; i++) {
//            Rock rock = new Rock(Rock.getImages());
//            rockList.add(rock);
//        }
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Rock rock = new Rock(Rock.getImages());
//                rockList.add(rock);
//            }
//        },0,3*1000);
////        timer.cancel();
//        AnimationTimer gameloop = new AnimationTimer() {
//            @Override
//            public void handle(long nanotime) {
//
//                //movimientos
//                if (keyPressedList.contains("A")){
//                    ship.rotation -=3;
//                }
//                if (keyPressedList.contains("D")){
//                    ship.rotation +=3;
//                }
//                if (keyPressedList.contains("W")){
//                    ship.velocity.setLength(150); //velocidad inicial
//                    ship.velocity.setAngle(ship.rotation);
//                }else{
//                    // cuando se suelta la tecla --- meter desaceleracion
//                    if (ship.velocity.getLength()==0) {
//                        ship.velocity.setLength(0); //velocidad 0 al inicio
//                    }else {
//                        ship.velocity.setLength(50);
//                    }
//                }
//                //tambien probado en el for render
//                for (Rock rock: rockList){
//                    if (rock.overlaps(ship)) {
//                        rockList.remove(rock);
//                    }else{
//                        rock.update(1/60.0D);
//                    }
//                }
//                ship.update(1/60.0D);
//
//                background.render(context);
//                for (Rock rock: rockList){
////                    if (rock.overlaps(ship)) {
//////                        rock = null;
////                        rockList.remove(rock);
////                    }
//                    rock.render(context);
//                }
//                ship.render(context);
//            }
//        };
//        gameloop.start();
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
