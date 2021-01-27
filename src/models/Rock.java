package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Rock extends Sprite{

    public ArrayList<String> rockImagesList;
    private boolean alive;
    private double timer;
    private int timeToGenerateRock;

    public Rock(String imageFileName){
        double x = Math.random()*1300+300;
        double y = Math.random()*800+100;
        double angle = Math.random()*360;
        this.position.set(x,y);
        this.velocity.setLength(((int)(Math.random()*50+50)));
        this.velocity.setAngle(angle);
        alive = true;
        timer = 0;
        timeToGenerateRock = 3;
        setImage(imageFileName);
    }

    @Override
    public void render(GraphicsContext context) {
        this.rotation +=1;
        super.render(context);
    }

    @Override
    public void update(double deltaTime){

        //actualizacion de la posicion acorde a la velocidad
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        this.wrap(1600,900);
    }

    public boolean isAlive() {
        return alive;
    }

    public void die(){
        alive = false;
    }

    public static String getImages(){
        ArrayList<String> rockImagesList = new ArrayList<String>();
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big1.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorGrey_big2.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_med1.png");
        rockImagesList.add("Images/Space Shooter Visual Assets/PNG/Meteors/meteorBrown_med3.png");
        return rockImagesList.get((int)(Math.random()*4));
    }
}
